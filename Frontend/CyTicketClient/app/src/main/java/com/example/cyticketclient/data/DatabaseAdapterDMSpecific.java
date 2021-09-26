package com.example.cyticketclient.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.cyticketclient.interfaces.ListenerInterface;
import com.example.cyticketclient.net_utils.Const;
import com.example.cyticketclient.networking.ServerCall;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;


public class DatabaseAdapterDMSpecific implements ListenerInterface {

    SimpleDBHelper dbHelper;
    ServerCall server;
    private int messagesIndex;
    private Response.Listener<JSONArray> listener;
    private String TAG = DatabaseAdapterDMSpecific.class.getSimpleName();
    private ArrayList<Integer> seen;

    public DatabaseAdapterDMSpecific(Context context) {
        dbHelper = new SimpleDBHelper(context);
    }

    // Requests the Springboot server to get all of the forum posts
    public void refreshDatabase() {
        messagesIndex = 0;
        //final String[] values = new String[6]; //rowId, postId, title, body, userId, tags;
        seen = new ArrayList<>();

        ServerCall call = new ServerCall();
        call.get(Const.URL_DM_GET_ALL_REQUEST, this);

    }

    /***
     * Returns a list of message objects from the database
     * @return
     */
    public List<MessageObject> getStoredMessages() {
        List<MessageObject> messageList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columns = {dbHelper.DM_ID, dbHelper.USER_ID_1, dbHelper.USER_ID_2, dbHelper.MESSAGES, dbHelper.DATE};
        Cursor cursor = db.query(dbHelper.TABLE_NAME, columns, null, null, null, null, null); // Maybe add ordering

        MessageObject temp;

        while(cursor.moveToNext()) {
            temp = new MessageObject();
            temp.setMessageId(cursor.getInt(cursor.getColumnIndex(dbHelper.DM_ID)));
            temp.setSender(cursor.getString(cursor.getColumnIndex(dbHelper.USER_ID_1)));
            temp.setReceiver(cursor.getString(cursor.getColumnIndex(dbHelper.USER_ID_2)));
            temp.setMessage(cursor.getString(cursor.getColumnIndex(dbHelper.MESSAGES)));
            temp.setDate(cursor.getString(cursor.getColumnIndex(dbHelper.DATE)));
            messageList.add(temp);
        }

        //Log.d(TAG, "" + postList.size());
        db.close();

        return messageList;
    }

    /***
     * Delete a post that is no longer in the database. Mostly used in refresh()
     */
    public void deleteStoredMessage(int messageId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] whereArgs = {"" + messageId};

        db.delete(dbHelper.TABLE_NAME, dbHelper.DM_ID + " = ?", whereArgs);
        db.close();
        Log.d(TAG, "Deleted message:" + whereArgs[0]);
    }

    public void clear(){
        ArrayList<Integer> posts = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columns = new String[7];
        columns[0] = dbHelper.DM_ID;

        // First read all of the forum posts stored in the database, and make a list of all the ids
        Cursor cursor = db.query(dbHelper.TABLE_NAME, columns, null, null, null, null, dbHelper.DM_ID);

        while(cursor.moveToNext()) {
            int postID = cursor.getInt(cursor.getColumnIndex(dbHelper.DM_ID));
            posts.add(postID);
        }

        for(int i : posts)
        {
            deleteStoredMessage(i);
        }
    }

    @Override
    public void onSuccess(JSONArray response) {
        //Log.d(TAG, "" + response.length());
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String[] columns = new String[6];
            columns[0] = dbHelper.DM_ID;

            // First read all of the forum posts stored in the database, and make a list of all the ids
            Cursor cursor = db.query(dbHelper.TABLE_NAME, columns, null, null, null, null, dbHelper.DM_ID);

            while(cursor.moveToNext()) {
                int postID = cursor.getInt(cursor.getColumnIndex(dbHelper.DM_ID));
                seen.add(postID);
            }

            Log.d(TAG, "Seen = " + seen.size());

            ContentValues values = new ContentValues();
            int add = 0;

            // Request all of the posts and store them in the database
            for (int i = 0; i < response.length(); i++) {
                // Prevents the addition of posts that are already in the database
                if (!seen.contains(i)) {
                    values.put(dbHelper.DM_ID, response.getJSONObject(i).getString("dmid"));
                    values.put(dbHelper.USER_ID_1, response.getJSONObject(i).getString("userId1")) ;
                    values.put(dbHelper.USER_ID_2, response.getJSONObject(i).getString("userId2"));
                    values.put(dbHelper.MESSAGES, response.getJSONObject(i).getString("msg"));
                    values.put(dbHelper.DATE, response.getJSONObject(i).getString("date"));

                    db.insert(dbHelper.TABLE_NAME, null, values);
                    add++;
                }

            }

            Log.d(TAG, "Added " + add + " new entries");

            columns[0] = dbHelper.ROW_ID;
            columns[1] = dbHelper.DM_ID;
            columns[2] = dbHelper.USER_ID_1;
            columns[3] = dbHelper.USER_ID_2;
            columns[4] = dbHelper.MESSAGES;
            columns[5] = dbHelper.DATE;


            cursor = db.query(dbHelper.TABLE_NAME, columns, null, null, null, null, null);

            StringBuffer buffer = new StringBuffer();

            // Loop through the database and basically get all the posts
            while(cursor.moveToNext()) {
                int rowId = cursor.getInt(cursor.getColumnIndex(dbHelper.ROW_ID));
                int dmId = cursor.getInt(cursor.getColumnIndex(dbHelper.DM_ID));
                String userId1 = cursor.getString(cursor.getColumnIndex(dbHelper.USER_ID_1));
                String userId2 = cursor.getString(cursor.getColumnIndex(dbHelper.USER_ID_2));
                String messages = cursor.getString(cursor.getColumnIndex(dbHelper.MESSAGES));
                String date = cursor.getString(cursor.getColumnIndex(dbHelper.DATE));
                buffer.append(rowId + "   " + dmId + "   " + userId1 + "   " + userId2 + "   " + messages + "   " + date + " \n");
            }

            Log.d(TAG, buffer.toString());
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertMessage() {

    }

    @Override
    public void onSuccess(String response) {

    }

    @Override
    public void onFailure(VolleyError error) {
    }

    /**
    public int updateAuthor(String oldName, String newName) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.NAME, newName);
        String[] whereArgs = {oldName};
        int count = db.update(dbHelper.TABLE_NAME, contentValues, dbHelper.NAME + " = ?", whereArgs);
        return count;
    }
     **/


    // Simple class to help create the tables required
    static class SimpleDBHelper extends SQLiteOpenHelper {
        // Setup the forum post table
        private static final String DATABASE_NAME = "dmDatabase";
        private static final String TABLE_NAME = "dmTable";
        private static final int DATABASE_Version = 1;
        private static final String ROW_ID = "_id";       // Column 1 (Primary Key)
        private static final String DM_ID = "DMId";   // Column 2
        private static final String USER_ID_1 = "userId1";  // Column 3
        private static final String USER_ID_2 = "userId2";
        private static final String MESSAGES = "MESSAGES";
        private static final String DATE = "date";
        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DM_ID + " VARCHAR(255) ," + USER_ID_1 + " VARCHAR(255) , "
                + USER_ID_2 + " VARCHAR(255) , " + MESSAGES + " VARCHAR(255) , " + DATE + " VARCHAR(255))";
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
                //Message.message(context, "" + e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                //Message.message(context, "OnUpgrade");
                db.execSQL(DROP_TABLE);
                onCreate(db);
            } catch (Exception e) {
                //Message.message(context, "" + e);
            }
        }


    }
}
