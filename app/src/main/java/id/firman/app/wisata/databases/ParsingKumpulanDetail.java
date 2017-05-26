package id.firman.app.wisata.databases;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Firman on 5/22/2017.
 */

public class ParsingKumpulanDetail {
    @SerializedName("hasil")
    public List<KumpulanDetail>kumpulanDetails;

    public class KumpulanDetail {

        @SerializedName("id_kota_kabupaten")
        public String id_kota_kabupaten;
        @SerializedName("popular_wisata")
        public String popular_wisata;
        @SerializedName("id_wisata")
        public String id_wisata;
        @SerializedName("id_kategori_wisata")
        public String id_kategori_wisata;
        @SerializedName("judul_wisata")
        public String judul_wisata;
        @SerializedName("isi_wisata")
        public String isi_wisata;
        @SerializedName("gambar_wisata")
        public String gambar_wisata;
        @SerializedName("id_gambar")
        public String id_gambar;
        @SerializedName("nama_gambar")
        public String nama_gambar;
        @SerializedName("url_gambar")
        public String url_gambar;


    }
}
