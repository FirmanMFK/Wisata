package id.firman.app.wisata.databases;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Firman on 5/22/2017.
 */

public class ParsingProvinsi {
    @SerializedName("tb_provinsi")
    public List<DataProvinsi>dataProvinsis;



    public class DataProvinsi {
        @SerializedName("id_provinsi")
        public String id_provinsi;
        @SerializedName("nama_provinsi")
        public String nama_provinsi;
        @SerializedName("gambar_provinsi")
        public String gambar_provinsi;
    }


}
