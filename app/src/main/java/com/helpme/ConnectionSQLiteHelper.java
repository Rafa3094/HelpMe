package com.helpme;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class ConnectionSQLiteHelper extends SQLiteOpenHelper {


    final String CREATE_CONTACT_TABLE = "CREATE TABLE IF NOT EXISTS contacts(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, phoneNumber TEXT)";
    final String CREATE_EMERGENCY_NUMBERS_TABLE = "CREATE TABLE IF NOT EXISTS numbers(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, phoneNumber TEXT)";
    final String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS user(id INTEGER PRIMARY KEY AUTOINCREMENT, personalId INTEGER, name TEXT, lastName TEXT, birthDate TEXT, sufferings TEXT, blood TEXT)";
    final String CREATE_TUTORIAL_TABLE = "CREATE TABLE IF NOT EXISTS tutorial(id INTEGER PRIMARY KEY AUTOINCREMENT, first INTEGER)";

    public ConnectionSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public ArrayList<HashMap<String, String>> getContactsList(Context context) {
        try {
            ArrayList<HashMap<String, String>> contactList;
            contactList = new ArrayList<>();
            String selectQuery = "SELECT id, name, phoneNumber FROM contacts";
            SQLiteDatabase database = this.getWritableDatabase();
            Cursor cursor = database.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("id", cursor.getString(0));
                    map.put("name", cursor.getString(1));
                    map.put("phoneNumber", cursor.getString(2));
                    contactList.add(map);
                } while (cursor.moveToNext());
            }
            return contactList;
        } catch (Exception e) {
            Toast.makeText(context, "ERROR: " + e, Toast.LENGTH_LONG).show();
            return null;
        }
    }

    public User getUserData(Context context) {
        User u = new User(0,0,"","","","","");
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM user", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                int id = cursor.getInt(0);
                int personalId = cursor.getInt(1);
                String name = cursor.getString(2);
                String lastName = cursor.getString(3);
                String birthDate = cursor.getString(4);
                String sufferings = cursor.getString(5);
                String bloodType = cursor.getString(6);
                u = new User(id, personalId, name, lastName, birthDate, sufferings, bloodType);
            }
        } catch (Exception e) {
            Toast.makeText(context, "ERROR: " + e, Toast.LENGTH_LONG).show();
        }
        return u;
    }


    public int getTutorialConfirmation(Context context) {
        int confirmation = 100;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM tutorial", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                int id = cursor.getInt(0);
                confirmation = cursor.getInt(1);
            }
        } catch (Exception e) {
            Toast.makeText(context, "ERROR: " + e, Toast.LENGTH_LONG).show();
        }
        return confirmation;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CONTACT_TABLE);
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_EMERGENCY_NUMBERS_TABLE);
        db.execSQL(CREATE_TUTORIAL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS contacts");
        db.execSQL("DROP TABLE IF EXISTS numbers");
        db.execSQL("DROP TABLE IF EXISTS tutorial");
        onCreate(db);
    }
}
