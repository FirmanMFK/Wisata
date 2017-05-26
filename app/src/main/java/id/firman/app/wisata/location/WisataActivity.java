package id.firman.app.wisata.location;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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
import id.firman.app.wisata.databases.ParsingDaftarWisata;

/**
 * Created by Firman on 5/22/2017.
 */

public class WisataActivity extends AppCompatActivity {

    RequestQueue queueWisata;
    StringRequest stringRequestWisata;
    RecyclerView recyclerViewWisata;
    ParsingDaftarWisata parsingDaftarWisata;
    SwipeRefreshLayout refreshWisata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wisata);

        recyclerViewWisata = (RecyclerView) findViewById(R.id.recycler_wisata);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerViewWisata.setLayoutManager(llm);

        refreshWisata = (SwipeRefreshLayout)findViewById(R.id.swipewisata);

        refreshWisata.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshWisata.setRefreshing(false);

                Proses();

            }
        });

        Proses();

    }

    private void  Proses (){
        queueWisata = Volley.newRequestQueue(this);

        String tangkap_id_kota_kabupaten = getIntent().getStringExtra("id_kota_kabupaten_1");
        String tangkap_id_kategori_wisata = getIntent().getStringExtra("id_kategori_wisata");

        String url = "http://wisata.uphero.com/server_wisata/list_wisata.php?id_kota_kabupaten=" + tangkap_id_kota_kabupaten + "&id_kategori_wisata=" + tangkap_id_kategori_wisata;
        //     String url = (KumpulanUrl.URL_WISATA);
        Log.d("url",url);
        stringRequestWisata = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("responsess", response);
                try {

                    GsonBuilder builder = new GsonBuilder();
                    Gson gson242 = builder.create();
                    parsingDaftarWisata = gson242.fromJson(response, ParsingDaftarWisata.class);
                    AdapterWisata wisata = new AdapterWisata(WisataActivity.this, parsingDaftarWisata.daftarWisatas);
                    recyclerViewWisata.setAdapter(wisata);
                } catch (NullPointerException e) {
                    Toast.makeText(WisataActivity.this, "No Data", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(WisataActivity.this, "No Data", Toast.LENGTH_SHORT).show();
            }
        });
        queueWisata.add(stringRequestWisata);

    }
}
