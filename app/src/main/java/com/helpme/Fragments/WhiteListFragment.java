package com.helpme.Fragments;



import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
//import java.util.ArrayList;

import com.helpme.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WhiteListFragment extends Fragment {


    //ArrayList<contact> contactList = new ArrayList();
    View view;
    ListView list;
    String[][]  contactList = {
            {"Angela", "89654712"},
            {"Tony","61457856"},
            {"Michael","75412569"}
    };


    public WhiteListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = inflater.inflate(R.layout.fragment_white_list, container, false);

        list = view.findViewById(R.id.contactListView);

        list.setAdapter(new listAdapter(this.getContext(),contactList));

        return view;
    }

}
