package id.firman.app.wisata.news;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import id.firman.app.wisata.R;
import id.firman.app.wisata.databases.GSONnews;
import id.firman.app.wisata.databases.Url;

/**
 * Created by Firman on 5/22/2017.
 */

public class News extends AppCompatActivity {
    RecyclerView recyclerViewNews;
    RequestQueue queueNews;
    StringRequest stringRequestNews;
    RelativeLayout relative_error, relative_normal;
    GSONnews gsoNnews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        relative_error = (RelativeLayout)findViewById(R.id.relative_no_internet_3);
        relative_normal = (RelativeLayout)findViewById(R.id.relative_normal_3);

        relative_error.setVisibility(View.GONE);
        relative_normal.setVisibility(View.VISIBLE);

        recyclerViewNews = (RecyclerView)findViewById(R.id.recycler_news);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewNews.setLayoutManager(linearLayoutManager);

        Button button_error = (Button)findViewById(R.id.button_error_3);
        button_error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relative_error.setVisibility(View.GONE);
                relative_normal.setVisibility(View.VISIBLE);
                load_data();

            }
        });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        load_data();
    }

    private void load_data() {
        queueNews = Volley.newRequestQueue(this);

        String url_news = (Url.URL_NEWS);

        stringRequestNews = new StringRequest(Request.Method.GET, url_news, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("berita",response);

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                gsoNnews = gson.fromJson(response,GSONnews.class);

                Adapter_News adapter_news = new Adapter_News(News.this,gsoNnews.kumpulanBeritas);
                recyclerViewNews.setAdapter(adapter_news);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                relative_error.setVisibility(View.VISIBLE);
                relative_normal.setVisibility(View.GONE);
            }
        });

        queueNews.add(stringRequestNews);
    }

}
