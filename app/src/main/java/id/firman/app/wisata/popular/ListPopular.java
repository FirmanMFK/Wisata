package id.firman.app.wisata.popular;

import android.widget.ImageView;

/**
 * Created by Firman on 5/22/2017.
 */

public class ListPopular {
    private String namaWisata;
    private String lokasiWisata;
    private String jenisWisata;

    private ImageView imgWisata;

    public ListPopular(String namaWisata, String lokasiWisata, String jenisWisata, ImageView imgWisata) {
        this.namaWisata = namaWisata;
        this.lokasiWisata = lokasiWisata;
        this.jenisWisata = jenisWisata;
        this.imgWisata = imgWisata;
    }

    public String getNamaWisata() {
        return namaWisata;
    }

    public String getLokasiWisata() {
        return lokasiWisata;
    }

    public String getJenisWisata() {
        return jenisWisata;
    }

    public ImageView getImgWisata() {
        return imgWisata;
    }
}
