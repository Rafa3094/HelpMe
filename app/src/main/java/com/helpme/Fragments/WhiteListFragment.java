package com.helpme.Fragments;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.ListView;
import com.helpme.ConnectionSQLiteHelper;
import com.helpme.Contact;
import com.helpme.R;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class WhiteListFragment extends Fragment {


    View view;
    ListView list;
    WhiteListFragment fragment = this;

    public WhiteListFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = inflater.inflate(R.layout.fragment_white_list, container, false);

        view = callAddContact(view);

        list = view.findViewById(R.id.contactListView);

        list.setAdapter(new listAdapter(this.getContext(),getContactsList(), fragment));

        return view;
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
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                mButtonSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mName.getText().toString().isEmpty() || mPhone.getText().toString().isEmpty()){
                            Toast.makeText(v.getContext(),"can not leave empty spaces",Toast.LENGTH_SHORT).show();
                        } else {
                            Contact cont = new Contact(mName.getText().toString(), mPhone.getText().toString());
                            addContact(cont);
                            dialog.dismiss();
                            Toast.makeText(v.getContext(),"The changes have been saved",Toast.LENGTH_SHORT).show();
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
        return v;
    }



    public void addContact (Contact c) {
        ConnectionSQLiteHelper connectionSQLiteHelper = new ConnectionSQLiteHelper(this.getContext(), "HelpMe", null, 1);
        SQLiteDatabase db = connectionSQLiteHelper.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("name", c.getName());
        content.put("phoneNumber", c.getPhoneNumber());
        Long idResult = db.insert("contacts",null, content);
        list.setAdapter(new listAdapter(this.getContext(),getContactsList(), fragment));
    }

    public ArrayList<Contact> getContactsList() {
        ArrayList<Contact> contactsList = new ArrayList();
        try {
            ConnectionSQLiteHelper connectionSQLiteHelper = new ConnectionSQLiteHelper(this.getContext(), "HelpMe", null, 1);
            SQLiteDatabase db = connectionSQLiteHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT id, name, phoneNumber FROM contacts", null);
            if (cursor != null || cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    int id = cursor.getInt(0);
                    String name = cursor.getString(1);
                    String phone = cursor.getString(2);
                    contactsList.add(new Contact(id, name, phone));
                } while (cursor.moveToNext());
            }
        } catch(Exception e) {

        }
        return contactsList;
    }

    public Contact getContact(int id) {
        Contact contact = new Contact();
        ConnectionSQLiteHelper connectionSQLiteHelper = new ConnectionSQLiteHelper(this.getContext(), "HelpMe", null, 1);
        SQLiteDatabase db = connectionSQLiteHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, name, phoneNumber FROM contacts WHERE id = "+id, null);
        if (cursor != null || cursor.getCount() > 0) {
            cursor.moveToFirst();
                int idCon =  cursor.getInt(0);
                String name = cursor.getString(1);
                String phone = cursor.getString(2);
                contact = new Contact(idCon, name, phone);
        }
        return contact;
    }

    public void editContact (Contact c) {
        ConnectionSQLiteHelper connectionSQLiteHelper = new ConnectionSQLiteHelper(this.getContext(), "HelpMe", null, 1);
        SQLiteDatabase db = connectionSQLiteHelper.getWritableDatabase();
        ContentValues content= new ContentValues();
        content.put("name", c.getName());
        content.put("phoneNumber", c.getPhoneNumber());
        db.update("contacts", content, "id="+c.getId(), null);
        list.setAdapter(new listAdapter(this.getContext(),getContactsList(), fragment));
    }

    public void deleteContact(int id) {
        ConnectionSQLiteHelper connectionSQLiteHelper = new ConnectionSQLiteHelper(this.getContext(), "HelpMe", null, 1);
        SQLiteDatabase db = connectionSQLiteHelper.getWritableDatabase();
        db.delete("contacts", "id="+id, null);
        list.setAdapter(new listAdapter(this.getContext(),getContactsList(), fragment));
    }

}
