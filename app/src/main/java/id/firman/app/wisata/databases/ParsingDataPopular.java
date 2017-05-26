package id.firman.app.wisata.databases;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Firman on 5/22/2017.
 */

public class ParsingDataPopular {
    @SerializedName("hasil")
    public List<DataPopular>dataPopulars;


    public class DataPopular {

        @SerializedName("id_wisata")
        public String id_wisata2;
        @SerializedName("judul_wisata")
        public String judul_wisata2;
        @SerializedName("popular_wisata")
        public String popular_wisata;
        @SerializedName("url_gambar")
        public String url_gambar2;
        @SerializedName("nama_kota_kabupaten")
        public String nama_kota_kabupaten;
        @SerializedName("nama_kategori_wisata")
        public String nama_kategori_wisata;

    }
}
