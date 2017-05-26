package id.firman.app.wisata.model;

import io.realm.RealmObject;

/**
 * Created by Firman on 5/23/2017.
 */


public class LatLngModel extends RealmObject {
    private double lat;
    private double lng;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}

