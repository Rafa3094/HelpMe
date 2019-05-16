package com.helpme;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;


public class ContactsDbManager {


    private SQLiteDatabase dataBase;
    ArrayList<Contact> contactsList;
    Context con;

    public ContactsDbManager() {
        ConnectionSQLiteHelper connection = new ConnectionSQLiteHelper(con, "HelpMe Database", null, 1);
    }




    public ArrayList<Contact> getContactsList() {
        ArrayList<Contact> contactsList = new ArrayList();

        return contactsList;
    }

}
