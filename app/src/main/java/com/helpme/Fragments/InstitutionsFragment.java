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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.helpme.ConnectionSQLiteHelper;
import com.helpme.Entities.EmergencyNumber;
import com.helpme.R;

import java.util.ArrayList;
import com.helpme.GeneralFunctions;

/**
 * A simple {@link Fragment} subclass.
 */
public class InstitutionsFragment extends Fragment {

    View view;
    ListView list;
    InstitutionsFragment inst;
    GeneralFunctions functions= new GeneralFunctions();


    public InstitutionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        inst = this;
        view = inflater.inflate(R.layout.fragment_institutions, container, false);
        view = callAddNumber(view);
        list = view.findViewById(R.id.emergency_list);
        list.setAdapter(new institutionListAdapter(this.getContext(), getCallList(), inst));
        return view;
    }


    private View callAddNumber(View v) {
        Button mShowDialog = (Button) v.findViewById(R.id.button);
        mShowDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(view.getContext());
                View mView = getLayoutInflater().inflate(R.layout.dialog_addcontact, null);
                TextView title = (TextView) mView.findViewById(R.id.textViewTitle);
                title.setText("Institution information");
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
                            EmergencyNumber num = new EmergencyNumber(mName.getText().toString(), mPhone.getText().toString());
                            addNumber(num);
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

    public void addNumber(EmergencyNumber c) {
        ConnectionSQLiteHelper connectionSQLiteHelper = new ConnectionSQLiteHelper(this.getContext(), "HelpMe", null, 1);
        SQLiteDatabase db = connectionSQLiteHelper.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("name", functions.cuttext(10,c.getName()));
        content.put("phoneNumber", c.getPhone());
        Long idResult = db.insert("numbers", null, content);
        list.setAdapter(new institutionListAdapter(this.getContext(), getCallList(), inst));
    }

    private ArrayList<EmergencyNumber> getCallList() {
        ArrayList<EmergencyNumber> list = new ArrayList();
        try {
            ConnectionSQLiteHelper connectionSQLiteHelper = new ConnectionSQLiteHelper(this.getContext(), "HelpMe", null, 1);
            SQLiteDatabase db = connectionSQLiteHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT id, name, phoneNumber FROM numbers", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    int id = cursor.getInt(0);
                    String name = cursor.getString(1);
                    String phone = cursor.getString(2);
                    list.add(new EmergencyNumber(id, name, phone));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {

        }
        return list;
    }

    public void editNumber(EmergencyNumber c) {
        ConnectionSQLiteHelper connectionSQLiteHelper = new ConnectionSQLiteHelper(this.getContext(), "HelpMe", null, 1);
        SQLiteDatabase db = connectionSQLiteHelper.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("name", functions.cuttext(10,c.getName()));
        content.put("phoneNumber", c.getPhone());
        db.update("numbers", content, "id=" + c.getId(), null);
        list.setAdapter(new institutionListAdapter(this.getContext(), getCallList(), inst));
    }

    public void deleteNumber(int id) {
        ConnectionSQLiteHelper connectionSQLiteHelper = new ConnectionSQLiteHelper(this.getContext(), "HelpMe", null, 1);
        SQLiteDatabase db = connectionSQLiteHelper.getWritableDatabase();
        db.delete("numbers", "id=" + id, null);
        list.setAdapter(new institutionListAdapter(this.getContext(), getCallList(), inst));
    }
}

