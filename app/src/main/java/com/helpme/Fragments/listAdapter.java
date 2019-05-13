package com.helpme.Fragments;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.helpme.R;

public class listAdapter extends BaseAdapter {


    private static LayoutInflater inflater = null;

    Context contexto;
    String[][] contactList;

    public listAdapter(Context contexto, String[][] contactList) {
        this.contexto = contexto;
        this.contactList = contactList;

        inflater = (LayoutInflater)contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        final View view = inflater.inflate(R.layout.contact_list_element, null);

        TextView name = (TextView) view.findViewById(R.id.textView4);
        TextView phone = (TextView) view.findViewById(R.id.textView5);
        ImageView editImage = (ImageView) view.findViewById(R.id.imageView3);
        ImageView deleteImage = (ImageView) view.findViewById(R.id.imageView2);
        name.setText(contactList[i][0]);
        phone.setText(contactList[i][1]);
        editImage.setImageResource(R.drawable.edit);
        deleteImage.setImageResource(R.drawable.delete);
        editImage.setTag(i);
        //deleteImage.setTag(i);

        /*editImage.setOnClickListener(new View.onClickListener() {
        @Override
        public void onClick(View v) {


        }
        });*/
        return view;
    }

    @Override
    public int getCount() {
        return contactList.length;
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
