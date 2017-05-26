package id.firman.app.wisata.location;

/**
 * Created by Firman on 5/22/2017.
 */


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
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
import id.firman.app.wisata.databases.ParsingProvinsi;
import id.firman.app.wisata.databases.Url;

public class ChooseLocation extends Fragment {
    RecyclerView recylerViewProvinsi;
    RequestQueue queue;
    StringRequest stringRequest;
    ParsingProvinsi parsingProvinsi;
    RelativeLayout relative_normal, relative_error;

    public ChooseLocation() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_location, container, false);

        recylerViewProvinsi = (RecyclerView) view.findViewById(R.id.recycler_provinsi);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recylerViewProvinsi.setLayoutManager(linearLayoutManager);

        relative_error = (RelativeLayout)view.findViewById(R.id.relative_no_internet);
        relative_normal = (RelativeLayout)view.findViewById(R.id.relative_normal);
        relative_normal.setVisibility(View.VISIBLE);
        relative_error.setVisibility(View.GONE);


        Button button_error = (Button)view.findViewById(R.id.button_error);
        button_error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relative_error.setVisibility(View.GONE);
                loadurl();
            }
        });





        // Inflate the layout for this fragment
        loadurl();
        return view;
    }

    private void loadurl() {
        String aa = (Url.URL_PROVINSI);
        queue = Volley.newRequestQueue(getActivity());

        stringRequest = new StringRequest(Request.Method.POST, aa, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response", response);
                try {


                    GsonBuilder builder = new GsonBuilder();
                    Gson gson1 = builder.create();
                    parsingProvinsi = gson1.fromJson(response, ParsingProvinsi.class);
                    AdapterChooseLocation location = new AdapterChooseLocation(getActivity(), parsingProvinsi.dataProvinsis);
                    recylerViewProvinsi.setAdapter(location);
                } catch (NullPointerException e) {
                    Toast.makeText(getActivity(), "Maaf Konten Masih Kosong ", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                relative_error.setVisibility(View.VISIBLE);
                relative_normal.setVisibility(View.GONE);
            }
        });
        queue.add(stringRequest);
    }

}
