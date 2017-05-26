package id.firman.app.wisata;

/**
 * Created by Firman on 5/23/2017.
 */

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddMapActivity extends FragmentActivity implements GoogleMap.OnMapClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private double lat, lng;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private Location location;
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_map);
        ButterKnife.inject(this);
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
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapz))
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
    }

    @OnClick(R.id.fab)
    public void onFabClick() {
        Bundle data = new Bundle();
        data.putDouble("lat", lat);
        data.putDouble("lng", lng);
        Intent resultIntent = new Intent();
        resultIntent.putExtras(data);
        setResult(RESULT_OK, resultIntent);
        finish();
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
}
