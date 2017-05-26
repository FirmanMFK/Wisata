package id.firman.app.wisata.location;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import id.firman.app.wisata.R;

/**
 * Created by Firman on 5/22/2017.
 */

public class FragmentKota extends Fragment {


    public FragmentKota() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_kota, container, false);
        // Inflate the layout for this fragment
        return v;
    }

}

