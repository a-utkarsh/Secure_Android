package com.example.utkarsh.secureandroid;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "contactsManager";
    private static final String TABLE_CONTACTS = "contacts";
    private static final String Password = "id";
    LockModal contact;
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + Password + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    // code to add the new contact
    void addContact(LockModal contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Password, contact.getPassword()); // Contact Name

        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        //2nd argument is String containing nullColumnHack
        // Closing database connection
    }

    // code to get the single contact
        /*Contact getContact(int id) {
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                            KEY_NAME, KEY_PH_NO }, KEY_ID + "=?",
                    new String[] { String.valueOf(id) }, null, null, null, null);
            if (cursor != null)
                cursor.moveToFirst();

            Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1), cursor.getString(2));
            // return contact
            return contact;
        }*/

    // code to get all contacts in a list view
    public String getAllContacts() {
        List<LockModal> contactList = new ArrayList<LockModal>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {

            contact = new LockModal();
            contact.setPassword(cursor.getString(0));

            //contactList.add(contact);

        }
        return contact.getPassword();
    }
}

            // return contact list


            // code to update the single contact
        /*public int updateContact(Contact contact) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_NAME, contact.getName());
            values.put(KEY_PH_NO, contact.getPhoneNumber());

            // updating row
            return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                    new String[] { String.valueOf(contact.getID()) });
        }*/

            // Deleting single contact
        /*public void deleteContact(Contact contact) {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                    new String[] { String.valueOf(contact.getID()) });
            db.close();
        }

        // Getting contacts Count
        public int getContactsCount() {
            String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(countQuery, null);
            cursor.close();

            // return count
            return cursor.getCount();
        }*/






