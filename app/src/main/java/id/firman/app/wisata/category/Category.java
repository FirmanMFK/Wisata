package id.firman.app.wisata.category;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import id.firman.app.wisata.R;
import id.firman.app.wisata.databases.ParsingKategori;
import id.firman.app.wisata.databases.Url;

/**
 * Created by Firman on 5/22/2017.
 */

public class Category extends AppCompatActivity {

    RecyclerView recyclerViewKategori;
    RequestQueue queueKoategori;
    StringRequest stringRequestKategori;
    ParsingKategori parsingKategori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        recyclerViewKategori = (RecyclerView) findViewById(R.id.recycler_category);
        GridLayoutManager grid = new GridLayoutManager(this, 2);
        recyclerViewKategori.setLayoutManager(grid);

        final String lempar = getIntent().getStringExtra("id_kota_kabupatens");

        String aaa = (Url.URL_KATEGORI_WISATA);
        queueKoategori = Volley.newRequestQueue(this);
        stringRequestKategori = new StringRequest(Request.Method.POST, aaa, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("responses", response);
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson2 = gsonBuilder.create();
                parsingKategori = gson2.fromJson(response, ParsingKategori.class);
                AdapterCategoryWisata wisata = new AdapterCategoryWisata(Category.this, parsingKategori.kategoriWisataList, lempar);
                recyclerViewKategori.setAdapter(wisata);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Category.this, "Maaf Internet Lambat", Toast.LENGTH_SHORT).show();
            }
        });
        queueKoategori.add(stringRequestKategori);


    }
}
