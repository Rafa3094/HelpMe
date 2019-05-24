package com.helpme;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class ConnectionSQLiteHelper extends SQLiteOpenHelper {


    final String CREATE_CONTACT_TABLE="CREATE TABLE IF NOT EXISTS contacts(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, phoneNumber TEXT)";
    //final String CREATE_USER_TABLE="CREATE TABLE IF NOT EXISTS user(id INTEGER, name TEXT, lastName TEXT, phoneNumber TEXT, sufferings TEXT, blood TEXT)";

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

            // return contact list
            return contactList;
        }catch (Exception e){
            Toast.makeText(context,"ERROR: " + e, Toast.LENGTH_LONG).show();
            return null;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CONTACT_TABLE);
       // db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }
}
