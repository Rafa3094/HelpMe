package com.helpme.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.helpme.Entities.EmergencyNumber;
import com.helpme.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class InstitutionsFragment extends Fragment {

    View view;
    InstitutionsFragment fragment = this;
    ListView list;
    InstitutionsFragment inst;

    public InstitutionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        inst = this;
        view = inflater.inflate(R.layout.fragment_institutions, container, false);
        list = view.findViewById(R.id.emergency_list);
        list.setAdapter(new institutionListAdapter(this.getContext(),getCallList(), inst));

        return view;
    }

    private ArrayList<EmergencyNumber> getCallList() {
        ArrayList<EmergencyNumber> list = new ArrayList();
        list.add(new EmergencyNumber("Red Cross","888763595"));
        list.add(new EmergencyNumber("Firefighters","89693541"));
        list.add(new EmergencyNumber("OIJ", "61516547"));
        list.add(new EmergencyNumber("COSEVI", "89040990"));
        list.add(new EmergencyNumber("Hospital", "87755608"));
        return list;
    }
}

