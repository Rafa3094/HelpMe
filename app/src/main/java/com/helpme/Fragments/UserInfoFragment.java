package com.helpme.Fragments;


import android.Manifest;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.helpme.ConnectionSQLiteHelper;
import com.helpme.Contact;
import com.helpme.User;

import com.helpme.R;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserInfoFragment extends Fragment implements DatePickerDialog.OnDateSetListener {


    View view;
    User user;
    TextView dateText;
    TextView bloodType;

    public UserInfoFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_info, container, false);
        user = getUser();
        view = fillUser(user, view);
        view = saveUserInfoButton(view);
        dateText = view.findViewById(R.id.myBirthDateBox);
        bloodType = view.findViewById(R.id.myBloodBox);

        view.findViewById(R.id.myBloodBox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBloodList();
            }
        });

        view.findViewById(R.id.myBirthDateBox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        return view;
    }

    private void showBloodList(){
        final String[] bloodTypes = {"A+", "A-", "B+", "B-","O+","O-", "AB+", "AB-", "Unknown"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Select Blood Type");


        final int checkedItem = 0; //this will checked the item when user open the dialog
        builder.setSingleChoiceItems(bloodTypes, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(getContext(), "Position: " + which + " Value: " + bloodTypes[which], Toast.LENGTH_LONG).show();
                String blood = bloodTypes[which];
                bloodType.setText(blood);
            }
        });

        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void showDatePickerDialog(){
        DatePickerDialog dialog = new DatePickerDialog(
                getContext(),
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        dialog.show();
    }

    public View saveUserInfoButton(View v) {
        final Button saveButton = (Button) v.findViewById(R.id.editButton);
        final EditText name = (EditText) v.findViewById(R.id.myNameBox);
        final EditText lastName = (EditText) v.findViewById(R.id.myLastNameBox);
        final EditText personalId = (EditText) v.findViewById(R.id.myIDBox);
        final TextView birthDate = (TextView) v.findViewById(R.id.myBirthDateBox);
        final EditText bloodType = (EditText) v.findViewById(R.id.myBloodBox);
        final EditText sufferings = (EditText) v.findViewById(R.id.mySufferingsBox);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(), "ajá", Toast.LENGTH_SHORT).show();
                if (name.getText().toString().isEmpty() || lastName.getText().toString().isEmpty() || personalId.getText().toString().isEmpty()
                        || birthDate.getText().toString().isEmpty() || bloodType.getText().toString().isEmpty() || sufferings.getText().toString().isEmpty()) {

                  Toast.makeText(v.getContext(), "Please do not leave any empty spaces", Toast.LENGTH_SHORT).show();
                  
                } else {
                    //llamar al metodo de crear o actualizar
                    User us = new User(personalId.getText().toString(), name.getText().toString(), lastName.getText().toString(), birthDate.getText().toString(), sufferings.getText().toString(), bloodType.getText().toString());
                    saveUserInfo(us);
                    Toast.makeText(v.getContext(), "The changes have been saved", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }

    public User getUser() {
        User u = new User();
        try {
            ConnectionSQLiteHelper connectionSQLiteHelper = new ConnectionSQLiteHelper(this.getContext(), "HelpMe", null, 1);
            SQLiteDatabase db = connectionSQLiteHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM user", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                int id = cursor.getInt(0);
                String personalId = cursor.getString(1);
                String name = cursor.getString(2);
                String lastName = cursor.getString(3);
                String birthDate = cursor.getString(4);
                String sufferings = cursor.getString(5);
                String bloodType = cursor.getString(6);
                u = new User(id, personalId, name, lastName, birthDate, sufferings, bloodType);
            }
        } catch (Exception e) {
            Toast.makeText(this.getContext(), "ERROR: " + e, Toast.LENGTH_SHORT).show();
        }
        return u;
    }

    public View fillUser(User u, View v) {
        final EditText name = (EditText) v.findViewById(R.id.myNameBox);
        final EditText lastName = (EditText) v.findViewById(R.id.myLastNameBox);
        final EditText personalId = (EditText) v.findViewById(R.id.myIDBox);
        final TextView birthDate = (TextView) v.findViewById(R.id.myBirthDateBox);
        final EditText bloodType = (EditText) v.findViewById(R.id.myBloodBox);
        final EditText sufferings = (EditText) v.findViewById(R.id.mySufferingsBox);
        try {
            if (u.getId() != 0) {
                name.setText(u.getName());
                lastName.setText(u.getLastName());
                personalId.setText(u.getPersonalId());
                birthDate.setText(u.getBitrhDate());
                bloodType.setText(u.getBlood());
                sufferings.setText(u.getSufferings());
            }
        } catch (Exception e) {
            Toast.makeText(this.getContext(), "ERROR: " + e, Toast.LENGTH_SHORT).show();
        }
        return v;
    }

    public void saveUserInfo(User us) {
        try {
            ConnectionSQLiteHelper connectionSQLiteHelper = new ConnectionSQLiteHelper(this.getContext(), "HelpMe", null, 1);
            SQLiteDatabase db = connectionSQLiteHelper.getWritableDatabase();
            ContentValues content = new ContentValues();
            content.put("personalId", us.getPersonalId());
            content.put("name", us.getName());
            content.put("lastName", us.getLastName());
            content.put("birthDate", us.getBitrhDate());
            content.put("sufferings", us.getSufferings());
            content.put("blood", us.getBlood());
            if (user.getId() != 0 && user.getName() != "") {
                //update
                db.update("user", content, "id=" + user.getId(), null);
                user = us;
            } else {
                //create
                Long idResult = db.insert("user", null, content);
                user = us;
            }
        } catch (Exception e) {
            Toast.makeText(this.getContext(), "ERROR: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month = month + 1;
        String date = month + "/" + dayOfMonth + "/" + year;
        dateText.setText(date);
    }
}