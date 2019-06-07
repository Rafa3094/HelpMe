package com.helpme.Fragments;

import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
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
        view = callImportContact(view);

        list = view.findViewById(R.id.contactListView);

        list.setAdapter(new listAdapter(this.getContext(), getContactsList(), fragment));

        return view;
    }


    private View callAddContact(View v) {
        Button mShowDialog = (Button) v.findViewById(R.id.btnAddContact);
        mShowDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(view.getContext());
                View mView = getLayoutInflater().inflate(R.layout.dialog_addcontact, null);
                final EditText mName = (EditText) mView.findViewById(R.id.editTextNombre);
                final EditText mPhone = (EditText) mView.findViewById(R.id.editTextTelefono);
                Button mButtonCancel = (Button) mView.findViewById(R.id.buttonCancel);
                Button mButtonSave = (Button) mView.findViewById(R.id.buttonOk);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                mButtonSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mName.getText().toString().isEmpty() || mPhone.getText().toString().isEmpty()) {
                            Toast.makeText(v.getContext(), "can not leave empty spaces", Toast.LENGTH_SHORT).show();
                        } else {
                            Contact cont = new Contact(mName.getText().toString(), mPhone.getText().toString());
                            addContact(cont);
                            dialog.dismiss();
                            Toast.makeText(v.getContext(), "The changes have been saved", Toast.LENGTH_SHORT).show();
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


    private View callImportContact(View v) {
        Button mShowDialog = (Button) v.findViewById(R.id.btnImportContact);
        mShowDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_DENIED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CONTACTS}, 1);
                    } else {
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(view.getContext());
                        View mView = getLayoutInflater().inflate(R.layout.import_contact, null);
                        ListView listView = (ListView) mView.findViewById(R.id.contactsList);
                        mBuilder.setView(mView);
                        final AlertDialog dialog = mBuilder.create();
                        //parte para llamar los contactos y adaptarlos al listview

                        //final String[] myProjection = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone._ID};

                        final Cursor cursor = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
                        getActivity().startManagingCursor(cursor);
                        String[] from = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone._ID};
                        int[] to = {android.R.id.text1, android.R.id.text2};
                        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(mView.getContext(), android.R.layout.simple_expandable_list_item_2, cursor, from, to);
                        listView.setAdapter(simpleCursorAdapter);
                        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                cursor.moveToPosition(position);
                                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                Contact contact = new Contact(name, number);
                                dialog.dismiss();
                                importContact(contact);
                                //Toast.makeText(view.getContext(), name + " " + number, Toast.LENGTH_LONG).show();

                            }
                        });
                        dialog.show();
                    }
                } catch (Exception e) {
                    Toast.makeText(view.getContext(), "ERROR: " + e, Toast.LENGTH_LONG).show();
                }
            }
        });
        return v;
    }


    public void importContact(Contact contact) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(view.getContext());
        View mView = getLayoutInflater().inflate(R.layout.dialog_addcontact, null);
        final EditText mName = (EditText) mView.findViewById(R.id.editTextNombre);
        final EditText mPhone = (EditText) mView.findViewById(R.id.editTextTelefono);
        mName.setText(contact.getName());
        mPhone.setText(contact.getPhoneNumber());
        Button mButtonCancel = (Button) mView.findViewById(R.id.buttonCancel);
        Button mButtonSave = (Button) mView.findViewById(R.id.buttonOk);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mName.getText().toString().isEmpty() || mPhone.getText().toString().isEmpty()) {
                    Toast.makeText(v.getContext(), "can not leave empty spaces", Toast.LENGTH_SHORT).show();
                } else {
                    Contact cont = new Contact(mName.getText().toString(), mPhone.getText().toString());
                    addContact(cont);
                    dialog.dismiss();
                    Toast.makeText(v.getContext(), "The changes have been saved", Toast.LENGTH_SHORT).show();
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


    public void addContact(Contact c) {
        ConnectionSQLiteHelper connectionSQLiteHelper = new ConnectionSQLiteHelper(this.getContext(), "HelpMe", null, 1);
        SQLiteDatabase db = connectionSQLiteHelper.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("name", c.getName());
        content.put("phoneNumber", c.getPhoneNumber());
        Long idResult = db.insert("contacts", null, content);
        list.setAdapter(new listAdapter(this.getContext(), getContactsList(), fragment));
    }

    public ArrayList<Contact> getContactsList() {
        ArrayList<Contact> contactsList = new ArrayList();
        try {
            ConnectionSQLiteHelper connectionSQLiteHelper = new ConnectionSQLiteHelper(this.getContext(), "HelpMe", null, 1);
            SQLiteDatabase db = connectionSQLiteHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT id, name, phoneNumber FROM contacts", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    int id = cursor.getInt(0);
                    String name = cursor.getString(1);
                    String phone = cursor.getString(2);
                    contactsList.add(new Contact(id, name, phone));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Toast.makeText(this.getContext(), "ERROR: " + e, Toast.LENGTH_LONG).show();
        }
        return contactsList;
    }

    //No usa actualmente
    public Contact getContact(int id) {
        Contact contact = new Contact();
        try {
            ConnectionSQLiteHelper connectionSQLiteHelper = new ConnectionSQLiteHelper(this.getContext(), "HelpMe", null, 1);
            SQLiteDatabase db = connectionSQLiteHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT id, name, phoneNumber FROM contacts WHERE id = " + id, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                int idCon = cursor.getInt(0);
                String name = cursor.getString(1);
                String phone = cursor.getString(2);
                contact = new Contact(idCon, name, phone);
            }
        } catch (Exception e) {
            Toast.makeText(this.getContext(), "ERROR: " + e, Toast.LENGTH_LONG).show();
        }
        return contact;
    }

    public void editContact(Contact c) {
        ConnectionSQLiteHelper connectionSQLiteHelper = new ConnectionSQLiteHelper(this.getContext(), "HelpMe", null, 1);
        SQLiteDatabase db = connectionSQLiteHelper.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("name", c.getName());
        content.put("phoneNumber", c.getPhoneNumber());
        db.update("contacts", content, "id=" + c.getId(), null);
        list.setAdapter(new listAdapter(this.getContext(), getContactsList(), fragment));
    }

    public void deleteContact(int id) {
        ConnectionSQLiteHelper connectionSQLiteHelper = new ConnectionSQLiteHelper(this.getContext(), "HelpMe", null, 1);
        SQLiteDatabase db = connectionSQLiteHelper.getWritableDatabase();
        db.delete("contacts", "id=" + id, null);
        list.setAdapter(new listAdapter(this.getContext(), getContactsList(), fragment));
    }

}
