package id.firman.app.wisata.popular;

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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import id.firman.app.wisata.R;
import id.firman.app.wisata.databases.ParsingDataPopular;
import id.firman.app.wisata.databases.Url;

/**
 * Created by Firman on 5/22/2017.
 */

public class Popular extends Fragment {

    private RecyclerView recyclerViewPopular;
    RequestQueue queuePopular;
    StringRequest stringRequestPopular;
    ParsingDataPopular parsingDataPopular;
    RelativeLayout relative_error, relative_normal;


    public Popular() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_popular, container, false);
        // Inflate the layout for this fragment
        recyclerViewPopular = (RecyclerView) view.findViewById(R.id.recycler_popular);
        recyclerViewPopular.setHasFixedSize(true);
        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(getActivity()));

        relative_error = (RelativeLayout) view.findViewById(R.id.relative_no_internet_2);
        relative_normal = (RelativeLayout) view.findViewById(R.id.relative_normal_2);

        relative_error.setVisibility(View.GONE);
        relative_normal.setVisibility(View.VISIBLE);

        Button button_error = (Button) view.findViewById(R.id.button_error_2);

        button_error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                relative_error.setVisibility(View.GONE);
                load_data();

            }
        });


        load_data();

        return view;
    }

    private void load_data() {
        queuePopular = Volley.newRequestQueue(getActivity());

        String url = (Url.URL_POPULAR);
        stringRequestPopular = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response", response);
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                parsingDataPopular = gson.fromJson(response, ParsingDataPopular.class);
                AdapterPopular popular = new AdapterPopular(getActivity(), parsingDataPopular.dataPopulars);
                recyclerViewPopular.setAdapter(popular);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                relative_error.setVisibility(View.VISIBLE);
                relative_normal.setVisibility(View.GONE);
            }
        });
        queuePopular.add(stringRequestPopular);
    }

}
