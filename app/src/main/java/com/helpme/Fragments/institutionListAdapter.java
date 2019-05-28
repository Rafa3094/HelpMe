package com.helpme.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.helpme.Entities.EmergencyNumber;
import com.helpme.R;

import java.util.ArrayList;

public class institutionListAdapter extends BaseAdapter {


    private static LayoutInflater inflater = null;
    Context contexto;
    ArrayList<EmergencyNumber> emergencyList;
    InstitutionsFragment inst;


    public institutionListAdapter(Context contexto, ArrayList<EmergencyNumber> emergencyList, InstitutionsFragment inst) {
        this.contexto = contexto;
        this.emergencyList = emergencyList;
        this.inst = inst;
        inflater = (LayoutInflater)contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int e, View convertView, ViewGroup parent) {
        final int i = e;
        final View view = inflater.inflate(R.layout.institution_element, null);

        TextView name = (TextView) view.findViewById(R.id.textView7);
        TextView phone = (TextView) view.findViewById(R.id.textView8);
        ImageView callImage = (ImageView) view.findViewById(R.id.imageView2);

        name.setText(emergencyList.get(i).getName());
        phone.setText(emergencyList.get(i).getPhone());
        callImage.setImageResource(R.drawable.call);
        callImage.setTag(i);
        callImage = callImageAction(callImage, i, view);
        return view;
    }


    public ImageView callImageAction(ImageView callImage, final int i, final View view) {
        callImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int index = Integer.parseInt(v.getTag().toString());
                if(ContextCompat.checkSelfPermission(contexto, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED){
                    ActivityCompat.requestPermissions(inst.getActivity(), new String[] {Manifest.permission.CALL_PHONE}, 1);
                }
                if(ContextCompat.checkSelfPermission(contexto, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    String phoneNumber = emergencyList.get(i).getPhone();
                    Intent intent = new Intent(inst.getActivity(), InstitutionsFragment.class);
                    inst.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber)));
                }
            }
        });
        return callImage;
    }

    @Override
    public int getCount() {
        int e = 0;
        try{
            e = emergencyList.size();
        } catch(Exception ex) {

        }
        return e;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
