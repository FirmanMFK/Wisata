package id.firman.app.wisata.databases;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Firman on 5/22/2017.
 */

public class ParsingDaftarWisata {
    @SerializedName("hasil")
    public List<DaftarWisata> daftarWisatas;

    public class DaftarWisata {
        @SerializedName("id_wisata")
        public String id_wisata;
        @SerializedName("judul_wisata")
        public String judul_wisata;
        @SerializedName("url_gambar")
        public String url_gambar;
        @SerializedName("nama_kota_kabupaten")
        public String id_kota_kabupaten;
        @SerializedName("nama_kategori_wisata")
        public String id_kategori_wisata;

    }
}
