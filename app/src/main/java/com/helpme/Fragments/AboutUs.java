package com.helpme.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.helpme.BuildConfig;
import com.helpme.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutUs extends Fragment {

    View view;
    public AboutUs() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_about_us, container, false);
        TextView version= view.findViewById(R.id.tversionapp);
        version.setText("HelpMe! V" + BuildConfig.VERSION_NAME);
        return view;
    }

}
