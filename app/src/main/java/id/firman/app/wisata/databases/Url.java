package id.firman.app.wisata.databases;

/**
 * Created by Firman on 5/22/2017.
 */

public class Url {
    private static  String URL_SERVER = "http://wisata.uphero.com/server_wisata/";
    private static  String URL_SERVER_GAMBAR = "http://wisata.uphero.com/server_wisata/";
    public static  final String URL_PROVINSI = URL_SERVER + "output_kategori.php";
    public static  final String URL_KATEGORI_WISATA = URL_SERVER + "output_kategori_wisata.php";
    public static final String URL_KOTA = URL_SERVER + "output_kota.php?id_provinsi=";
    public static final String URL_POPULAR = URL_SERVER + "list_popular.php";
    public static final String URL_DETAIL = URL_SERVER + "detail_wisata.php?send_id_wisata=";
    public static final String URL_DETAIL_NEWS = URL_SERVER + "detail_news.php?send_id_news=";
    public static final String URL_WISATA = URL_SERVER + "list_wisata.php";
    public static final String URL_NEWS = URL_SERVER + "list_news.php";


    public static  final  String URL_GAMBAR = URL_SERVER_GAMBAR + "uplod/";

}
