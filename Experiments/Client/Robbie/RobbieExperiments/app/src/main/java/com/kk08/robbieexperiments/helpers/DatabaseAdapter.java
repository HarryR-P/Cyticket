package com.kk08.robbieexperiments.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseAdapter {
    SimpleDBHelper dbHelper;

    public DatabaseAdapter(Context context) {
        dbHelper = new SimpleDBHelper(context);
    }

    // Creates a ContentValues object that stores the data to be placed in the table
    public long insertData(String author, String quote) {
        SQLiteDatabase dbb = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.NAME, author);
        contentValues.put(dbHelper.QUOTE, quote);
        long id = dbb.insert(dbHelper.TABLE_NAME, null, contentValues);

        //return a value based on success
        return id;
    }

    // Method to easily retrieve the data
    public String getData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columns = {dbHelper.AID, dbHelper.NAME, dbHelper.QUOTE};
        Cursor cursor = db.query(dbHelper.TABLE_NAME, columns, null, null, null, null, null);
        StringBuffer buffer = new StringBuffer();

        while(cursor.moveToNext()) {
            int cid = cursor.getInt(cursor.getColumnIndex(dbHelper.AID));
            String name = cursor.getString(cursor.getColumnIndex(dbHelper.NAME));
            String quote = cursor.getString(cursor.getColumnIndex(dbHelper.QUOTE));
            buffer.append(cid + "   " + name + "   " + quote + " \n");
        }

        return buffer.toString();
    }

    public int delete(String name) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] whereArgs = {name};

        int count = db.delete(dbHelper.TABLE_NAME, dbHelper.NAME + " = ?", whereArgs);
        return count;
    }

    public int updateAuthor(String oldName, String newName) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.NAME, newName);
        String[] whereArgs = {oldName};
        int count = db.update(dbHelper.TABLE_NAME, contentValues, dbHelper.NAME + " = ?", whereArgs);
        return count;
    }


    // Simple class to help create the tables required
    static class SimpleDBHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "simpleDatabase";
        private static final String TABLE_NAME = "buyersTable";
        private static final int DATABASE_Version = 1;
        private static final String AID = "_id";       // Column 1 (Primary Key)
        private static final String NAME = "Buyer";   // Column 2
        private static final String QUOTE = "Message";  // Column 3
        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + AID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " VARCHAR(255) ," + QUOTE + " VARCHAR(255))";
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        private Context context;

        // Constructor
        public SimpleDBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context = context;
        }

        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TABLE);
            } catch (Exception e) {
                Message.message(context, "" + e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                Message.message(context, "OnUpgrade");
                db.execSQL(DROP_TABLE);
                onCreate(db);
            } catch (Exception e) {
                Message.message(context, "" + e);
            }
        }


    }
}
