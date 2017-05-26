package id.firman.app.wisata.databases;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Firman on 5/22/2017.
 */

public class ParsingKumpulanKota {
    @SerializedName("hasil")
    public List<KumpulanKota>kumpulanKotas;

    public class KumpulanKota {
        @SerializedName("id_kota_kabupaten")
        public String id_kota_kabupaten;
        @SerializedName("nama_kota_kabupaten")
        public String nama_kota_kabupaten;
        @SerializedName("gambar_kota_kabupaten")
        public String gambar_kota_kabupaten;
    }
}
