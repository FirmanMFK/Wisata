package id.firman.app.wisata.news;

/**
 * Created by Firman on 5/22/2017.
 */

public class ListNews {

    private String judul;
    private String id;
    private String kategori;
    private String imageNews;

    public ListNews(String judul, String id, String kategori, String imageNews) {
        this.judul = judul;
        this.id = id;
        this.kategori = kategori;
        this.imageNews = imageNews;
    }

    public String getJudul() {
        return judul;
    }

    public String getId() {
        return id;
    }

    public String getKategori() {
        return kategori;
    }

    public String getImageNews() {
        return imageNews;
    }
}
