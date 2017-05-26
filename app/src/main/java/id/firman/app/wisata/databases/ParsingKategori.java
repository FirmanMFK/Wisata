package id.firman.app.wisata.databases;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Firman on 5/22/2017.
 */

public class ParsingKategori {
    @SerializedName("tb_kategori_wisata")
    public List<KumpulanKategoriWisata> kategoriWisataList;

    public class KumpulanKategoriWisata {
        @SerializedName("id_kategori_wisata")
        public String id_kategori_wisata;
        @SerializedName("nama_kategori_wisata")
        public String nama_kategori_wisata;
        @SerializedName("gambar_kategori_wisata")
        public String gambar_kategori_wisata;
    }
}
