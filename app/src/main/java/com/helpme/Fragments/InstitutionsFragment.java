package com.helpme.Fragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.helpme.Entities.EmergencyNumber;
import com.helpme.MainActivity;
import com.helpme.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class InstitutionsFragment extends Fragment {

    View view;
    InstitutionsFragment fragment = this;

    public InstitutionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.fragment_institutions, container, false);

        ListView lv = (ListView) view.findViewById(R.id.emergency_list);

        final ArrayAdapter adapter = new ArrayAdapter<EmergencyNumber>(getActivity(), android.R.layout.simple_list_item_1 , getArrayOfArrayList());

        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED){
                    ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CALL_PHONE}, 1);
                }
                if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    String phoneNumber = "" + ((EmergencyNumber) parent.getAdapter().getItem(position)).getPhone();
                    Intent intent = new Intent(getActivity(), InstitutionsFragment.class);
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber)));
                }
            }
        });
        return view;
    }

    private EmergencyNumber[] getArrayOfArrayList(){
        EmergencyNumber[] stringArray = {
                new EmergencyNumber("Emergency to Allan",888763595),
                new EmergencyNumber("Emergency to Estiven",89693541),
                new EmergencyNumber("Emergency to Kevin", 61516547),
                new EmergencyNumber("Emergency to Rafa", 89040990),
                new EmergencyNumber("Emergency to Sean", 87755608)
        };
        return stringArray;
    }

}
