package com.kithay;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Noman Sial on 6/11/2018.
 */

public class DBController {
    DBhelper dbhelper;
    private Context context;
    private SQLiteDatabase database;
    public DBController(Context context) {
        dbhelper = new DBhelper(context);
    }

    public void close() {
        dbhelper.close();
    }

    //CRUD Operations

    // Adding new contact
    public void addContact(ContactBO contact) {
        database = dbhelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(DBhelper.KEY_NAME, contact.getName());
        values.put(DBhelper.KEY_PH_NO, contact.getPhone());

        try {
            database.insert(DBhelper.TABLE_CONTACTS, null, values);
            Log.d("Contacts","Contact Added");
        }catch (Exception e){
            Log.d("Insert FAILURE", e.toString());

        }

        database.close();
    }

    //Adding new entry in the History
    public void addHistory(HistoryBO history) {
        database = dbhelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(DBhelper.KEY_NAME, history.getName());
        values.put(DBhelper.KEY_PH_NO, history.getPhone());
        values.put(DBhelper.KEY_LANG, history.getLang());
        values.put(DBhelper.KEY_LAT, history.getLat());
        values.put(DBhelper.KEY_DATE, history.getDate());
        values.put(DBhelper.KEY_TIME, history.gettime());
        values.put(DBhelper.KEY_BATTERY,history.getBattery());
        values.put(DBhelper.KEY_ADDRESS,history.getAddress());

        try {

            long num = database.insert(DBhelper.TABLE_HISTORY, null, values);
            Log.d("History","History Added");
        }catch (Exception e){
            Log.d("Insert FAILURE", e.toString());

        }
        database.close();
    }

    public void addUser(UserBO user) {
        database = dbhelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(DBhelper.KEY_NAME, user.getName());
        values.put(DBhelper.KEY_PH_NO, user.getPhone());
        values.put(DBhelper.KEY_EMAIL, user.getEmail());
        values.put(DBhelper.KEY_PASSWORD, user.getPassword());


        try {

            long num = database.insert(DBhelper.TABLE_USER, null, values);
            Log.d("User","User Added");
        }catch (Exception e){
            Log.d("Insert FAILURE", e.toString());

        }
        database.close();
    }

    public boolean userExists(String email,String password){
        database = dbhelper.getReadableDatabase();
        String fetchuser = "Select email,password from " +DBhelper.TABLE_USER;
        Cursor cursor = database.rawQuery(fetchuser, null);
        String a,b = "not found";
        Log.d("received emailid", email);
        Log.d("Cursor count", String.valueOf(cursor.getCount()));
        if(cursor.moveToFirst()){
            Log.d("Select " , " clause");
            do{
                a= cursor.getString(0);
                Log.d("a " , a);
                if (a.equals(email)){
                    Log.d("emailid  If loop" , a);
                    b = cursor.getString(1);
                    Log.d("b " , b);
                    break;
                }
            }
            while(cursor.moveToNext());
        }
        if (b.equals(password)) {
            Log.d("Returning "," true");
            return true;
        }
        else return false;

    }

    //Get Single Contact
    public ContactBO getContact(int id) {
        database = dbhelper.getReadableDatabase();

        Cursor cursor = database.query(DBhelper.TABLE_CONTACTS,DBhelper.Contacts_Columns ,DBhelper.KEY_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        ContactBO contact = new ContactBO(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));

        return contact;
    }

    //Get Single History
    public HistoryBO getHistory(int id) {
        database = dbhelper.getReadableDatabase();

        Cursor cursor = database.query(DBhelper.TABLE_HISTORY,DBhelper.History_columns, DBhelper.KEY_PH_NO + " = ?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        HistoryBO history = new HistoryBO(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),cursor.getString(7),cursor.getString(8));

        return history;

    }

    //Get All Contacts
    public ArrayList<ContactBO> getAllContacts() {
        ArrayList<ContactBO> contactlist = new ArrayList<ContactBO>();

        SQLiteDatabase db = dbhelper.getWritableDatabase();

        String selectAllContacts = "SELECT * FROM " + DBhelper.TABLE_CONTACTS;

        Cursor cursor = db.rawQuery(selectAllContacts, null);

        if (cursor.moveToFirst()) {
            do {
                ContactBO contact = new ContactBO();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhone(cursor.getString(2));

                contactlist.add(contact);
            } while (cursor.moveToNext());
        }
        return contactlist;
    }


    //Get All History
    public ArrayList<HistoryBO> getAllHistory() {
        ArrayList<HistoryBO> historyList = new ArrayList<HistoryBO>();

        SQLiteDatabase db = dbhelper.getWritableDatabase();

        String selectAllHistory = "SELECT * FROM " + DBhelper.TABLE_HISTORY;

        Cursor cursor = db.rawQuery(selectAllHistory, null);

        if (cursor.moveToFirst()) {
            do {
                HistoryBO history = new HistoryBO();
                history.setID(Integer.parseInt(cursor.getString(0)));
                history.setName(cursor.getString(1));
                history.setPhone(cursor.getString(2));
                history.setLang(cursor.getString(3));
                history.setLat(cursor.getString(4));
                history.setDate(cursor.getString(5));
                history.settime(cursor.getString(6));
                history.setBattery(cursor.getString(7));
                history.setAddress(cursor.getString(8));

// Adding contact to list
                historyList.add(history);
            } while (cursor.moveToNext());
        }
        return historyList;
    }

    // Updating single contact
    public int updateContact(ContactBO contact){
        SQLiteDatabase db=dbhelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(DBhelper.KEY_NAME, contact.getName());
        values.put(DBhelper.KEY_PH_NO, contact.getPhone());

        return db.update(DBhelper.TABLE_CONTACTS,values,DBhelper.KEY_ID + " = ?",new String[]{String.valueOf(contact.getID())});
    }

    // Delete single contact
    public void deleteContact(int id) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        db.delete(DBhelper.TABLE_CONTACTS, DBhelper.KEY_ID + " = ?", new String[] { String.valueOf(id)});
        System.out.println("Record Deleted");

        db.close();
    }

    // Delete single History
    public void deleteHistory(int id) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        db.delete(DBhelper.TABLE_HISTORY, DBhelper.KEY_ID + " = ?", new String[] { String.valueOf(id)});
        db.close();
    }

//    // Getting contacts Count
//    public int getContactsCount() {
//        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(countQuery, null);
//
//        // return count
//        return cursor.getCount();
//    }
//
//    // Getting History Count
//    public int getHistoryCount() {
//        String countQuery = "SELECT  * FROM " + TABLE_HISTORY;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(countQuery, null);
//
//        // return count
//        return cursor.getCount();
//    }
}
