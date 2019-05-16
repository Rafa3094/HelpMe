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
import com.helpme.Entities.User;
import com.helpme.R;

import java.util.ArrayList;

public class listAdapter extends BaseAdapter {


    private static LayoutInflater inflater = null;

    Context contexto;
    ArrayList<User> contactsList;

    public listAdapter(Context contexto, ArrayList<User> contactsList) {
        this.contexto = contexto;
        this.contactsList = contactsList;

        inflater = (LayoutInflater)contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View getView(int e, View convertView, ViewGroup parent) {
        final int i = e;
        final View view = inflater.inflate(R.layout.contact_list_element, null);

        TextView name = (TextView) view.findViewById(R.id.textView4);
        TextView phone = (TextView) view.findViewById(R.id.textView5);
        ImageView editImage = (ImageView) view.findViewById(R.id.imageView3);
        ImageView deleteImage = (ImageView) view.findViewById(R.id.imageView2);

        name.setText(contactsList.get(i).getName());
        phone.setText(contactsList.get(i).getPhoneNumber());
        //name.setText(contactList[i][0]);
        //phone.setText(contactList[i][1]);
        editImage.setImageResource(R.drawable.edit);
        deleteImage.setImageResource(R.drawable.delete);
        editImage.setTag(i);
        deleteImage.setTag(i);
        deleteImage = deleteImageAction(deleteImage, i, view);
        editImage = editImageAction(editImage, i, view);
        return view;
    }



    public ImageView deleteImageAction(ImageView deleteImage, final int i, final View view) {
        deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(view.getContext());
                View mView = inflater.inflate(R.layout.dialog_killcontact,null);
                final TextView mName = (TextView) mView.findViewById(R.id.textViewNombre);
                final TextView mPhone = (TextView) mView.findViewById(R.id.textViewTelefono);
                mName.setText(contactsList.get(i).getName());
                mPhone.setText(contactsList.get(i).getPhoneNumber());
                //mName.setText(contactList[i][0]);
                //mPhone.setText(contactList[i][1]);
                Button mButtonCancel = (Button) mView.findViewById(R.id.buttonCancel);
                Button mButtonSave = (Button) mView.findViewById(R.id.buttonOk);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                mButtonSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // if true está por si existe validaciones futuras
                        if(true){
                            Toast.makeText(v.getContext(),"Contacto eliminado correctamente",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(v.getContext(),"Se han guardado los cambios",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                mButtonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.hide();
                    }
                });
                dialog.show();
            }
        });
        return deleteImage;
    }



    public ImageView editImageAction(ImageView editImage, final int i, final View view) {
        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(view.getContext());
                View mView = inflater.inflate(R.layout.dialog_addcontact,null);
                final EditText mName = (EditText) mView.findViewById(R.id.editTextNombre);
                final EditText mPhone = (EditText) mView.findViewById(R.id.editTextTelefono);
                Button mButtonCancel = (Button) mView.findViewById(R.id.buttonCancel);
                Button mButtonSave = (Button) mView.findViewById(R.id.buttonOk);
                mName.setText(contactsList.get(i).getName());
                mPhone.setText(contactsList.get(i).getPhoneNumber());
                //mName.setText(contactList[i][0]);
                //mPhone.setText(contactList[i][1]);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
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
                mButtonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.hide();
                    }
                });
                dialog.show();
            }
        });
        return editImage;


    }
    @Override
    public int getCount() {
        return contactsList.size();
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
