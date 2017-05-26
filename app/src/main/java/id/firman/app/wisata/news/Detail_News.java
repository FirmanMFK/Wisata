package id.firman.app.wisata.news;

/**
 * Created by Firman on 5/23/2017.
 */

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import id.firman.app.wisata.R;
import id.firman.app.wisata.databases.Url;

public class Detail_News extends AppCompatActivity {

    private WebView wvContent;
    ImageView image_detail;
    private CollapsingToolbarLayout collaptoolDetail;
    String judul, isi, gambar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        collaptoolDetail = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        image_detail = (ImageView) findViewById(R.id.image_detail_news);
        wvContent = (WebView) findViewById(R.id.detail_news);
        wvContent.getSettings().setJavaScriptEnabled(true);

        String tangkap_id_news = getIntent().getStringExtra("id_news");
        RequestDetailServer(Url.URL_DETAIL_NEWS + tangkap_id_news);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void RequestDetailServer(String s) {
        RequestQueue queueDetail = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, s, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonRespons = new JSONObject(response);
                    JSONObject dataDetailArticel = jsonRespons.getJSONObject("hasil");


                    judul = dataDetailArticel.getString("judul_news");
                    isi = dataDetailArticel.getString("isi_news");
                    gambar = dataDetailArticel.getString("url_gambar");


                    setContent(judul, isi, gambar);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Detail_News.this, "Maaf jbhdfgd", Toast.LENGTH_SHORT).show();
            }
        });
        queueDetail.add(request);
    }

    private void setContent(String judul, String isi, String gambar) {
        Glide.with(getApplicationContext())
                .load(gambar)
                .crossFade()
                .placeholder(R.drawable.ic_photo_size_select_actual_black_24dp)
                .into(image_detail);
        collaptoolDetail.setTitle(judul);
        String contentwvContent = "<html><body>" + isi + "</body></html>";
        wvContent.loadData(contentwvContent, "text/html", null);

    }

}

