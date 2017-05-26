package id.firman.app.wisata.databases;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Firman on 5/23/2017.
 */
public class Route {
    @SerializedName("legs")
    private List<Legs> legsList;

    public List<Legs> getLegsList() {
        return legsList;
    }

    public void setLegsList(List<Legs> legsList) {
        this.legsList = legsList;
    }
}

