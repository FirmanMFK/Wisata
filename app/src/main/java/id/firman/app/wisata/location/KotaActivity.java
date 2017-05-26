package id.firman.app.wisata.location;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import id.firman.app.wisata.databases.ParsingKumpulanKota;
import id.firman.app.wisata.databases.Url;

/**
 * Created by Firman on 5/22/2017.
 */

public class KotaActivity extends AppCompatActivity {

    RequestQueue queueKota;
    StringRequest stringRequestKota;
    RecyclerView recyclerViewKota;
    ParsingKumpulanKota parsingKumpulanKota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kota);

        recyclerViewKota = (RecyclerView) findViewById(R.id.recycler_kota);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerViewKota.setLayoutManager(llm);

        String id_provinsi = getIntent().getStringExtra("id_provinsi");

        String ababa = (Url.URL_KOTA + id_provinsi);
        queueKota = Volley.newRequestQueue(this);
        stringRequestKota = new StringRequest(Request.Method.GET, ababa, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response", response);
                try {


                    GsonBuilder builder = new GsonBuilder();
                    Gson gson22 = builder.create();
                    parsingKumpulanKota = gson22.fromJson(response, ParsingKumpulanKota.class);

                    AdapterKota kota = new AdapterKota(KotaActivity.this, parsingKumpulanKota.kumpulanKotas);
                    recyclerViewKota.setAdapter(kota);
                } catch (NullPointerException e) {
                    Toast.makeText(KotaActivity.this, "Maaf Konten Masih Kosong", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(KotaActivity.this, "Maaf Internet Lambat", Toast.LENGTH_SHORT).show();
            }
        });
        queueKota.add(stringRequestKota);

    }
}
