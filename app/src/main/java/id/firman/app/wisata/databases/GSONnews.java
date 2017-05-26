package id.firman.app.wisata.databases;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Firman on 5/23/2017.
 */

public class GSONnews {
    @SerializedName("hasil")
    public List<KumpulanBerita> kumpulanBeritas;


    public class KumpulanBerita {
        @SerializedName("id_news")
        public String id_news;
        @SerializedName("judul_news")
        public String judul_news;
        @SerializedName("url_gambar")
        public String url_gambar;
    }
}

