package com.helpme;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConnectionSQLiteHelper extends SQLiteOpenHelper {


    final String CREATE_CONTACT_TABLE="CREATE TABLE IF NOT EXISTS contacts(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, phoneNumber TEXT)";
    //final String CREATE_USER_TABLE="CREATE TABLE IF NOT EXISTS user(id INTEGER, name TEXT, lastName TEXT, phoneNumber TEXT, sufferings TEXT, blood TEXT)";

    public ConnectionSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
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
