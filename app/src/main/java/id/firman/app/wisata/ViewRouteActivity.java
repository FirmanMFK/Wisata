package id.firman.app.wisata;

/**
 * Created by Firman on 5/23/2017.
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import id.firman.app.wisata.Util.Utils;
import id.firman.app.wisata.databases.Legs;
import id.firman.app.wisata.databases.Steps;
import id.firman.app.wisata.model.LatLngModel;
import id.firman.app.wisata.model.LocationModel;
import io.realm.Realm;
import io.realm.RealmResults;

public class ViewRouteActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private double latAwal, latTujuan, lngAwal, lngTujuan;
    private Bundle data;

    static final int tampil_error=1;
    private String tujuan;
    private List<LatLng> latLngList;
    private id.firman.app.wisata.databases.Response response;
    private ProgressDialog progressDialog;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_route);

        if (cek_status(this))
        {
            ButterKnife.inject(this);
            realm = Realm.getInstance(this);
            progressDialog = new ProgressDialog(this);

            latLngList = new ArrayList<>();
            progressDialog.setMessage("Loading...");

            setUpMapIfNeeded();

            data = getIntent().getExtras();
            if (data.getInt("status") == History.FROM_LOCAL) {
                // baca db
                latLngList = readDataFromDB(data.getString("id"));

                // draw
                drawDirectionToMap(latLngList);
            } else {
                latAwal = data.getDouble("latAwal");
                lngAwal = data.getDouble("lngAwal");

                latTujuan = data.getDouble("latTujuan");
                lngTujuan = data.getDouble("lngTujuan");
                tujuan = data.getString("tujuan");

                getDirections(getGoogleDirectionsUrl(latAwal, lngAwal, latTujuan, lngTujuan));
            }
        }
        else
        {
            showDialog(tampil_error);
        }


    }

    public boolean cek_status(Context cek) {
        ConnectivityManager cm = (ConnectivityManager) cek.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        switch (id) {
            case tampil_error:
                AlertDialog.Builder errorDialog = new AlertDialog.Builder(this);
                errorDialog.setTitle("Kesalahan");
                errorDialog.setMessage("Tidak ada koneksi internet");
                errorDialog.setNeutralButton("Reload",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                Intent exit = new Intent(getApplicationContext(), ViewRouteActivity.class);
                                startActivity(exit);
                            }
                        });
                AlertDialog errorAlert = errorDialog.create();
                return errorAlert;
            default:
                break;
        }
        return dialog;
    }


    private List<LatLng> readDataFromDB(String id) {
        RealmResults<LocationModel> result = realm.where(LocationModel.class).equalTo("id", id).findAll();
        List<LatLng> listlatlng = new ArrayList<>();
        for (LatLngModel data : result.get(0).getLatLngRealmList())
            listlatlng.add(new LatLng(data.getLat(), data.getLng()));

        return listlatlng;
    }

    private String getGoogleDirectionsUrl(double latAwal, double lngAwal, double latTujuan, double lngTujuan) {
        return "https://maps.googleapis.com/maps/api/directions/json?origin=" + latAwal + "," + lngAwal + "&destination=" + latTujuan + "," + lngTujuan + "&key=" + Constant.API_KEY;
//        return "https://maps.googleapis.com/maps/api/directions/json?origin=" + latAwal + "," + lngAwal + "&destination=" + latTujuan + "," + lngTujuan;
    }
    @OnClick(R.id.fab)
    public void onFabClick() {
        Intent intent = new Intent(this, History.class);
        startActivity(intent);
    }

    private void getDirections(String URL) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        progressDialog.dismiss();
                        response = new Gson().fromJson(jsonObject.toString(), id.firman.app.wisata.databases.Response.class);
                        Log.d("debug", "" + response.getRouteList().get(0).getLegsList().get(0).getDistance());

                        //  Fetch Directions From Json
                        fetchDirection(response.getRouteList().get(0).getLegsList().get(0).getStepsList());

                        saveToDb(response.getRouteList().get(0).getLegsList().get(0));
                        // TODO: Save to DB
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                        Log.d("error", "" + volleyError.toString());
                    }
                });
        progressDialog.show();
        AppController.getInstance().addToRequestQueue(request, "tag");
    }


    private void saveToDb(Legs legs) {
        realm.beginTransaction();
        LocationModel locationModel = realm.createObject(LocationModel.class);
        locationModel.setId(Utils.getCurrentTimestamp());
        locationModel.setAsal(legs.getStart_address());
        locationModel.setTujuan(tujuan);
        locationModel.setDistance(legs.getDistance().getText());
        locationModel.setDuration(legs.getDuration().getText());

        for (Steps data : legs.getStepsList()) {
            Log.d("tag", "save to db ---");
            // start loc
            LatLngModel latlngStartLoc = realm.createObject(LatLngModel.class);
            latlngStartLoc.setLat(data.getStartLocation().getLat());
            latlngStartLoc.setLng(data.getStartLocation().getLng());
            // add to realmList
            locationModel.getLatLngRealmList().add(latlngStartLoc);

            // polyline
            List<LatLng> decodedPoly = Utils.decodePoly(data.getPolyline().getPoints());
            for (LatLng point : decodedPoly) {
                LatLngModel latlngPoly = realm.createObject(LatLngModel.class);

                latlngPoly.setLat(point.latitude);
                latlngPoly.setLng(point.longitude);
                // add to realmList
                locationModel.getLatLngRealmList().add(latlngPoly);
            }

            // end loc
            LatLngModel latlngEndLoc = realm.createObject(LatLngModel.class);
            latlngEndLoc.setLat(data.getEndLocation().getLat());
            latlngEndLoc.setLng(data.getEndLocation().getLng());
        }

        realm.commitTransaction();
    }

    private void fetchDirection(List<Steps> stepsList) {
        Log.d("debug", "size list awal: " + latLngList.size());
        for (Steps data : stepsList) {
            // add start
            latLngList.add(new LatLng(data.getStartLocation().getLat(), data.getStartLocation().getLng()));

            // decode poly
            List<LatLng> decodedPoly = Utils.decodePoly(data.getPolyline().getPoints());
            for (LatLng point : decodedPoly)
                latLngList.add(new LatLng(point.latitude, point.longitude));

            latLngList.add(new LatLng(data.getEndLocation().getLat(), data.getEndLocation().getLng()));
            // add end
        }
        Log.d("debug", "size list akhir : " + latLngList.size());

        drawDirectionToMap(latLngList);
    }

    private void drawDirectionToMap(List<LatLng> latLngList) {
        PolylineOptions line = new PolylineOptions().width(3).color(Color.BLUE);
        for (int i = 0; i < latLngList.size(); i++) {
            line.add(latLngList.get(i));
        }
        mMap.addPolyline(line);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latLngList.get(0).latitude, latLngList.get(0).longitude), 14));

        // add marker diawal
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latLngList.get(0).latitude, latLngList.get(0).longitude))
                .title("Lokasi awal")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker))
        );

        // add marker di akhir
        int index = latLngList.size() - 1;
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latLngList.get(index).latitude, latLngList.get(index).longitude))
                .title("Lokasi tujuan")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker))
        );

        Toast.makeText(this, "Rute berhasil didapatkan, Selamat Berwisata! ", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }


    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapx))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }


    private void setUpMap() {
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }
}
