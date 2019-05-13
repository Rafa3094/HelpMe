package com.helpme.Fragments;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

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
        deleteImage.setTag(i);



        deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(view.getContext());
                View mView = inflater.inflate(R.layout.dialog_killcontact,null);
                Button mButtonCancel = (Button) mView.findViewById(R.id.buttonCancel);
                Button mButtonSave = (Button) mView.findViewById(R.id.buttonOk);
                mButtonSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(true){
                            Toast.makeText(v.getContext(),"Has matado al contacto, felicidades.",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(v.getContext(),"Se han guardado los cambios",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();

            }
        });



        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(view.getContext());
                View mView = inflater.inflate(R.layout.dialog_addcontact,null);
                final EditText mName = (EditText) mView.findViewById(R.id.editTextNombre);
                final EditText mPhone = (EditText) mView.findViewById(R.id.editTextTelefono);
                Button mButtonCancel = (Button) mView.findViewById(R.id.buttonCancel);
                Button mButtonSave = (Button) mView.findViewById(R.id.buttonOk);
                mButtonSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mName.getText().toString().isEmpty() || mPhone.getText().toString().isEmpty()){
                            Toast.makeText(v.getContext(),"No puede dejar espacios vacíos",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(v.getContext(),"Se han guardado los cambios",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();


            }
        });

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
