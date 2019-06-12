package com.helpme.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
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
        ImageView editImage = (ImageView) view.findViewById(R.id.imageView6);
        ImageView deleteImage = (ImageView) view.findViewById(R.id.imageView5);

        name.setText(emergencyList.get(i).getName());
        phone.setText(emergencyList.get(i).getPhone());
        callImage.setImageResource(R.drawable.call);
        editImage.setImageResource(R.drawable.edit);
        deleteImage.setImageResource(R.drawable.delete);
        callImage.setTag(i);
        editImage.setTag(i);
        deleteImage.setTag(i);
        callImage = callImageAction(callImage, i, view);
        deleteImage = deleteImageAction(deleteImage, i, view);
        editImage = editImageAction(editImage, i, view);
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


    public ImageView deleteImageAction(ImageView deleteImage, final int i, final View view) {
        deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int index = Integer.parseInt(v.getTag().toString());
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(v.getContext());
                View mView = inflater.inflate(R.layout.dialog_killcontact,null);
                TextView title = (TextView) mView.findViewById(R.id.textViewTitle);
                title.setText("   Institution information");
                final TextView mName = (TextView) mView.findViewById(R.id.textViewNombre);
                final TextView mPhone = (TextView) mView.findViewById(R.id.textViewTelefono);
                mName.setText(emergencyList.get(index).getName());
                mPhone.setText(emergencyList.get(index).getPhone());
                Button mButtonCancel = (Button) mView.findViewById(R.id.buttonCancel);
                Button mButtonSave = (Button) mView.findViewById(R.id.buttonOk);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                mButtonSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        inst.deleteNumber(emergencyList.get(index).getId());
                        Toast.makeText(v.getContext(),"Institution properly removed",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
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
                final int index = Integer.parseInt(v.getTag().toString());
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(v.getContext());
                View mView = inflater.inflate(R.layout.dialog_addcontact,null);
                TextView title = (TextView) mView.findViewById(R.id.textViewTitle);
                title.setText("Institution information");
                final EditText mName = (EditText) mView.findViewById(R.id.editTextNombre);
                final EditText mPhone = (EditText) mView.findViewById(R.id.editTextTelefono);
                Button mButtonCancel = (Button) mView.findViewById(R.id.buttonCancel);
                Button mButtonSave = (Button) mView.findViewById(R.id.buttonOk);
                mName.setText(emergencyList.get(index).getName());
                mPhone.setText(emergencyList.get(index).getPhone());
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                mButtonSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mName.getText().toString().isEmpty() || mPhone.getText().toString().isEmpty()){
                            Toast.makeText(v.getContext(),"can not leave empty spaces",Toast.LENGTH_SHORT).show();
                        } else {
                            EmergencyNumber number = new EmergencyNumber(emergencyList.get(index).getId() ,mName.getText().toString(), mPhone.getText().toString());
                            inst.editNumber(number);
                            Toast.makeText(v.getContext(),"The changes have been saved",Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
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
