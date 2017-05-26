package id.firman.app.wisata;

/**
 * Created by Firman on 5/23/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import id.firman.app.wisata.databases.Location;


public class AddRouteActivity extends BaseActivity implements GoogleMap.OnMapClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    public static final int ADD_MAP_KEY = 1;

    @InjectView(R.id.etFrom)
    EditText etFrom;
    @InjectView(R.id.spinnerDestination)
    Spinner spDestination;

    private ArrayAdapter<Location> locationArrayAdapter;
    private List<Location> locations;
    private double lat, lng;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private android.location.Location location;
    private GoogleApiClient googleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_route);
        ButterKnife.inject(this);

        getSupportActionBar().setTitle("Rute Wisata");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        locations = new ArrayList<>();
        generateLocationDummy();
        locationArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, locations);
        spDestination.setAdapter(locationArrayAdapter);

        setUpMapIfNeeded();


        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this) // connection callback
                .addOnConnectionFailedListener(this) // when connection failed
                .addApi(LocationServices.API) // called api
                .build();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        mMap.setOnMapClickListener(this);
        mMap.setMyLocationEnabled(true);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        mMap.clear();
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latLng.latitude, latLng.longitude))
                .title("Lokasi awal")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker))
        );
        lat = latLng.latitude;
        lng = latLng.longitude;

        Bundle data = new Bundle();
        data.putDouble("lat", lat);
        data.putDouble("lng", lng);
        Intent resultIntent = new Intent();
        resultIntent.putExtras(data);
        setResult(RESULT_OK, resultIntent);
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (location == null) {
            // get last location device
            location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (mMap != null) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));
                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(location.getLatitude(), location.getLongitude()))
                        .title("Lokasi awal")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker))
                );
                lat = location.getLatitude();
                lng = location.getLongitude();

            }
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


    private void generateLocationDummy() {
        locations.add(new Location("Alun Alun Bandung", -6.9215209, 107.6037936));
        locations.add(new Location("Gedung Sate", -6.9025104, 107.6165933));
        locations.add(new Location("Gedung Merdeka", -6.9211116, 107.6070245));
        locations.add(new Location("Taman Film", -6.8985687, 107.6054563));
        locations.add(new Location("Monumen Perjuangan", -6.8934648, 107.6163494));
    }



    @OnClick(R.id.btnAddMap)
    public void bntAddMap() {
        startActivityForResult(new Intent(this, id.firman.app.wisata.AddMapActivity.class), ADD_MAP_KEY);
    }
    @OnClick(R.id.mps)
    public void mps() {
        startActivityForResult(new Intent(this, id.firman.app.wisata.AddMapActivity.class), ADD_MAP_KEY);
    }
    @OnClick(R.id.etFrom)
    public void etFrom() {
        startActivityForResult(new Intent(this, id.firman.app.wisata.AddMapActivity.class), ADD_MAP_KEY);
    }


    @OnClick(R.id.btnGetRoute)
    public void getRoute() {
        if (!TextUtils.isEmpty(etFrom.getText())) {
            Location loc = locations.get(spDestination.getSelectedItemPosition());
            Bundle data = new Bundle();
            data.putDouble("latAwal", lat);
            data.putDouble("lngAwal", lng);
            data.putDouble("latTujuan", loc.getLat());
            data.putDouble("lngTujuan", loc.getLng());
            data.putInt("status", History.FROM_NET);
            data.putString("tujuan", loc.getName());

            startActivity(new Intent(this, id.firman.app.wisata.ViewRouteActivity.class).putExtras(data));
        } else {
            Toast.makeText(this, "Tentukan lokasi anda!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_MAP_KEY) {
            if (resultCode == RESULT_OK) {
                lat = data.getExtras().getDouble("lat");
                lng = data.getExtras().getDouble("lng");
                etFrom.setText("" + lat + "," + lng);
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (item.getItemId() == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


}
