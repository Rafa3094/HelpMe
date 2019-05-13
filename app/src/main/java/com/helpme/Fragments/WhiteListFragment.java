package com.helpme.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.helpme.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WhiteListFragment extends Fragment /*AppCompatDialogFragment*/ {

    public WhiteListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_white_list, container, false);
        v = callAddContact(v);
        v = callKillContact(v);
        return v; //inflater.inflate(R.layout.fragment_white_list, container, false);
    }

    /**
     * Importante:
     * Este metodo se usa para llamar a la ventana insertar/editar contacto, se usaria dentro del metodo del boton.
     */
    private View callAddContact(View v){

        Button mShowDialog = (Button) v.findViewById(R.id.btnAddContact);

        mShowDialog.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(view.getContext());
                View mView = getLayoutInflater().inflate(R.layout.dialog_addcontact,null);
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
        return v;
    }

    private View callKillContact(View v){

        Button mShowDialog = (Button) v.findViewById(R.id.btnKillContact);

        mShowDialog.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(view.getContext());
                View mView = getLayoutInflater().inflate(R.layout.dialog_killcontact,null);
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
        return v;
    }


}
