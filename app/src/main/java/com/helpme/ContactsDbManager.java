package com.helpme;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.helpme.Entities.User;
import java.util.ArrayList;


public class ContactsDbManager {


    private SQLiteDatabase dataBase;
    ArrayList<User> contactsList;
    Context con;

    public ContactsDbManager() {
        ConnectionSQLiteHelper connection = new ConnectionSQLiteHelper(con, "HelpMe Database", null, 1);
    }




    public ArrayList<User> getContactsList() {
        ArrayList<User> contactsList = new ArrayList();

        return contactsList;
    }

}
