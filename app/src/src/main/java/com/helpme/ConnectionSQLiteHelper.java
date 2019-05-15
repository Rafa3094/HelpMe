package com.helpme;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConnectionSQLiteHelper extends SQLiteOpenHelper {


    private final String CREATE_CONTACT_TABLE="CREATE TABLE contact(id INTEGER, name TEXT, lastName TEXT, phoneNumber TEXT)";
    private final String CREATE_USER_TABLE = "CREATE TABLE user(id INTEGER, name TEXT, lastName TEXT, age Datetime2, bloodType TEXT)";

    public ConnectionSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_CONTACT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }
}
