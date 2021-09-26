package com.example.cyticketclient.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.cyticketclient.app.AppController;
import com.example.cyticketclient.interfaces.IDatabaseListener;
import com.example.cyticketclient.interfaces.ListenerInterface;
import com.example.cyticketclient.networking.ServerCall;
import com.example.cyticketclient.net_utils.Const;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DatabaseAdapter implements ListenerInterface {

    SimpleDBHelper dbHelper;
    ServerCall server;
    private int classifier;  // 1 means forum mode, 2 means rating mode
    private String TAG = DatabaseAdapter.class.getSimpleName();
    private ArrayList<Integer> forumSeen, ratingSeen;
    private IDatabaseListener listener;

    public DatabaseAdapter(Context context) {
        dbHelper = new SimpleDBHelper(context);
        classifier = 1;
    }

    // Requests the Springboot server to get all of the forum posts
    public void refreshDatabase(IDatabaseListener listener) {
        this.listener = listener;
        //final String[] values = new String[6]; //rowId, postId, title, body, userId, tags;
        forumSeen = new ArrayList<>();

        ServerCall call = new ServerCall();
        classifier = 1;
        call.get(Const.URL_FORUM_GET_ALL_REQUEST, this);

    }

    // GlobalUser must be set before calling method
    public void refreshRatings(IDatabaseListener listener) {
        this.listener = listener;
        ratingSeen = new ArrayList<>();
        ServerCall call = new ServerCall();
        classifier = 2;
        call.get(Const.URL_RATING_GET_AUTHOR + AppController.getInstance().getUser().getUUID(), this);
    }

    /***
     * Returns a list of forum objects from the database
     * @return
     */
    public List<PostObject> getStoredPosts() {
        List<PostObject> postList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columns = {dbHelper.POST_ID, dbHelper.TITLE, dbHelper.BODY, dbHelper.USER_ID, dbHelper.TAGS, dbHelper.PRICE, dbHelper.USERNAME, dbHelper.RATING};
        Cursor cursor = db.query(dbHelper.TABLE_NAME, columns, null, null, null, null, null); // Maybe add ordering

        PostObject temp;

        while(cursor.moveToNext()) {
            temp = new PostObject();
            temp.setPostId(cursor.getInt(cursor.getColumnIndex(dbHelper.POST_ID)));
            temp.setTitle(cursor.getString(cursor.getColumnIndex(dbHelper.TITLE)));
            temp.setBody(cursor.getString(cursor.getColumnIndex(dbHelper.BODY)));
            temp.setPrice(cursor.getString(cursor.getColumnIndex(dbHelper.PRICE)));
            temp.setAuthorId(UUID.fromString(cursor.getString(cursor.getColumnIndex(dbHelper.USER_ID)))); //can't be null
            temp.setUserName(cursor.getString(cursor.getColumnIndex(dbHelper.USERNAME)));
            temp.setPostRating(Float.parseFloat(cursor.getString(cursor.getColumnIndex(dbHelper.RATING))));
            postList.add(temp);
        }

        //Log.d(TAG, "" + postList.size());
        db.close();

        return postList;
    }

    public List<RatingObject> getStoredRatings() {
        List<RatingObject> ratingList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columns = {dbHelper.RATING_ID, dbHelper.TARGET_ID, dbHelper.AUTHOR_ID, dbHelper.RATE_TITLE, dbHelper.COMMENT, dbHelper.RATE_VALUE};
        Cursor cursor = db.query(dbHelper.RATING_NAME, columns, null, null, null, null,null);

        RatingObject temp;

        while(cursor.moveToNext()) {
            temp = new RatingObject();
            temp.setRatingId(cursor.getInt(cursor.getColumnIndex(dbHelper.RATING_ID)));
            temp.setTitle(cursor.getString(cursor.getColumnIndex(dbHelper.RATE_TITLE)));
            temp.setComment(cursor.getString(cursor.getColumnIndex(dbHelper.COMMENT)));
            temp.setRateValue(cursor.getString(cursor.getColumnIndex(dbHelper.RATE_VALUE)));
            temp.setAuthorUUID(UUID.fromString(cursor.getString(cursor.getColumnIndex(dbHelper.AUTHOR_ID)))); //can't be null
            temp.setTargetUUID(UUID.fromString(cursor.getString(cursor.getColumnIndex(dbHelper.TARGET_ID))));
            ratingList.add(temp);
        }

        db.close();

        return ratingList;

    }

    /***
     * Delete a post that is no longer in the database. Mostly used in refresh()
     */
    public void deleteStoredPost(int postId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] whereArgs = {"" + postId};

        db.delete(dbHelper.TABLE_NAME, dbHelper.POST_ID + " = ?", whereArgs);
        db.close();
        Log.d(TAG, "Deleted post:" + whereArgs[0]);
    }

    public void deleteStoredRating(int ratingId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] whereArgs = {"" + ratingId};

        db.delete(dbHelper.RATING_NAME, dbHelper.RATING_ID + " = ?", whereArgs);
        db.close();
        Log.d(TAG, "Deleted Rating:" + whereArgs[0]);
    }

    public void clear(){
        ArrayList<Integer> posts = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columns = new String[9];
        columns[0] = dbHelper.POST_ID;

        // First read all of the forum posts stored in the database, and make a list of all the ids
        Cursor cursor = db.query(dbHelper.TABLE_NAME, columns, null, null, null, null, dbHelper.POST_ID);

        while(cursor.moveToNext()) {
            int postID = cursor.getInt(cursor.getColumnIndex(dbHelper.POST_ID));
            posts.add(postID);
        }

        for(int i : posts)
        {
            deleteStoredPost(i);
        }
    }

    public void clearRatings(){
        ArrayList<Integer> ratings = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columns = new String[9];
        columns[0] = dbHelper.RATING_ID;

        // First read all of the forum posts stored in the database, and make a list of all the ids
        Cursor cursor = db.query(dbHelper.RATING_NAME, columns, null, null, null, null, dbHelper.RATING_ID);

        while(cursor.moveToNext()) {
            int ratingId = cursor.getInt(cursor.getColumnIndex(dbHelper.RATING_ID));
            ratings.add(ratingId);
        }

        for(int i : ratings)
        {
            deleteStoredRating(i);
        }
    }

    @Override
    public void onSuccess(JSONArray response) {
        //Log.d(TAG, "" + response.length());
        try {
            if (classifier == 1) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                String[] forumColumns = new String[9];
                forumColumns[0] = dbHelper.POST_ID;

                // First read all of the forum posts stored in the database, and make a list of all the ids
                Cursor cursor = db.query(dbHelper.TABLE_NAME, forumColumns, null, null, null, null, dbHelper.POST_ID);

                while (cursor.moveToNext()) {
                    int postID = cursor.getInt(cursor.getColumnIndex(dbHelper.POST_ID));
                    forumSeen.add(postID);
                }

                Log.d(TAG, "Seen = " + forumSeen.size());

                ContentValues values = new ContentValues();
                int add = 0;

                // Request all of the posts and store them in the database
                for (int i = 0; i < response.length(); i++) {
                    int currentPost = response.getJSONObject(i).getInt("postId");
                    // Prevents the addition of posts that are already in the database
                    if (!forumSeen.contains(currentPost)) {
                        values.put(dbHelper.POST_ID, response.getJSONObject(i).getString("postId"));
                        values.put(dbHelper.TITLE, response.getJSONObject(i).getString("title"));
                        values.put(dbHelper.BODY, response.getJSONObject(i).getString("postBody"));
                        values.put(dbHelper.USER_ID, response.getJSONObject(i).getString("userId"));
                        values.put(dbHelper.TAGS, response.getJSONObject(i).getString("tag"));
                        values.put(dbHelper.PRICE, response.getJSONObject(i).getString("price"));
                        values.put(dbHelper.USERNAME, response.getJSONObject(i).getJSONObject("forumId").getString("userName"));
                        values.put(dbHelper.RATING, response.getJSONObject(i).getJSONObject("forumId").getString("averageRating"));

                        Log.d(TAG, values.toString());

                        db.insert(dbHelper.TABLE_NAME, null, values);
                        add++;
                    }

                }

                Log.d(TAG, "Added " + add + " new entries");

                /*
                forumColumns[0] = dbHelper.ROW_ID;
                forumColumns[1] = dbHelper.POST_ID;
                forumColumns[2] = dbHelper.TITLE;
                forumColumns[3] = dbHelper.BODY;
                forumColumns[4] = dbHelper.USER_ID;
                forumColumns[5] = dbHelper.TAGS;
                forumColumns[6] = dbHelper.PRICE;
                forumColumns[7] = dbHelper.USERNAME;
                forumColumns[8] = dbHelper.RATING;


                cursor = db.query(dbHelper.TABLE_NAME, forumColumns, null, null, null, null, null);

                StringBuffer buffer = new StringBuffer();

                // Loop through the database and basically get all the posts
                while (cursor.moveToNext()) {
                    int rowId = cursor.getInt(cursor.getColumnIndex(dbHelper.ROW_ID));
                    int postId = cursor.getInt(cursor.getColumnIndex(dbHelper.POST_ID));
                    String title = cursor.getString(cursor.getColumnIndex(dbHelper.TITLE));
                    String body = cursor.getString(cursor.getColumnIndex(dbHelper.BODY));
                    String userId = cursor.getString(cursor.getColumnIndex(dbHelper.USER_ID));
                    String tags = cursor.getString(cursor.getColumnIndex(dbHelper.TAGS));
                    String price = cursor.getString(cursor.getColumnIndex(dbHelper.PRICE));
                    String userName = cursor.getString(cursor.getColumnIndex(dbHelper.USERNAME));
                    float averageRating = cursor.getFloat(cursor.getColumnIndex(dbHelper.RATING));
                    buffer.append(rowId + "   " + postId + "   " + title + "   " + body + "   " + userId + "   " + tags + "   " + price + "   " + userName + "   " + averageRating + " \n");
                }

                Log.d(TAG, buffer.toString());

                 */
                listener.onPostDataSuccess(response);
                db.close();
            } else {
                // Now, do the same thing with the ratings
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                String[] ratingColumns = new String[9];
                ratingColumns[0] = dbHelper.RATING_ID;

                Cursor cursor = db.query(dbHelper.RATING_NAME, ratingColumns, null, null, null, null, dbHelper.RATING_ID);

                while(cursor.moveToNext()) {
                    int ratingId = cursor.getInt(cursor.getColumnIndex(dbHelper.RATING_ID));
                    ratingSeen.add(ratingId);
                }

                //Log.d(TAG, "Seen = " + ratingSeen.size());

                Log.d(TAG, " LENGTH IS ============= " + response.length());

                ContentValues values = new ContentValues();
                int add = 0;

                // Request all of the posts and store them in the database
                for (int i = 0; i < response.length(); i++) {
                    int currentPost = response.getJSONObject(i).getInt("ratingId");
                    // Prevents the addition of posts that are already in the database
                    if (!ratingSeen.contains(currentPost)) {
                        values.put(dbHelper.TARGET_ID, response.getJSONObject(i).getString("targetUUID"));
                        values.put(dbHelper.AUTHOR_ID, response.getJSONObject(i).getString("authorUUID"));
                        values.put(dbHelper.RATE_TITLE, response.getJSONObject(i).getString("title"));
                        values.put(dbHelper.COMMENT, response.getJSONObject(i).getString("comment"));
                        values.put(dbHelper.RATE_VALUE, response.getJSONObject(i).getString("rateValue"));
                        values.put(dbHelper.RATING_ID, response.getJSONObject(i).getString("ratingId"));

                        Log.d(TAG, values.toString());

                        db.insert(dbHelper.RATING_NAME, null, values);
                        add++;
                    }

                }

                Log.d(TAG, "Added " + add + " new entries");

                /*
                ratingColumns[0] = dbHelper.ROW_ID;
                ratingColumns[1] = dbHelper.POST_ID;
                ratingColumns[2] = dbHelper.TITLE;
                ratingColumns[3] = dbHelper.BODY;
                ratingColumns[4] = dbHelper.USER_ID;
                ratingColumns[5] = dbHelper.TAGS;
                ratingColumns[6] = dbHelper.PRICE;
                ratingColumns[7] = dbHelper.USERNAME;
                ratingColumns[8] = dbHelper.RATING;


                cursor = db.query(dbHelper.RATING_NAME, ratingColumns, null, null, null, null, null);

                StringBuffer buffer = new StringBuffer();

                // Loop through the database and basically get all the posts
                while(cursor.moveToNext()) {
                    int rowId = cursor.getInt(cursor.getColumnIndex(dbHelper.ROW_ID));
                    int postId = cursor.getInt(cursor.getColumnIndex(dbHelper.POST_ID));
                    String title = cursor.getString(cursor.getColumnIndex(dbHelper.TITLE));
                    String body = cursor.getString(cursor.getColumnIndex(dbHelper.BODY));
                    String userId = cursor.getString(cursor.getColumnIndex(dbHelper.USER_ID));
                    String tags = cursor.getString(cursor.getColumnIndex(dbHelper.TAGS));
                    String price = cursor.getString(cursor.getColumnIndex(dbHelper.PRICE));
                    String userName = cursor.getString(cursor.getColumnIndex(dbHelper.USERNAME));
                    float averageRating = cursor.getFloat(cursor.getColumnIndex(dbHelper.RATING));
                    buffer.append(rowId + "   " + postId + "   " + title + "   " + body + "   " + userId + "   " + tags + "   " + price + "   " + userName + "   " + averageRating + " \n");
                }

                Log.d(TAG, buffer.toString());

                 */

                db.close();
                listener.onRatingDataSuccess(response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onSuccess(String response) {
    }

    @Override
    public void onFailure(VolleyError error) {
        listener.onDataFail(error);
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
        private static final String DATABASE_NAME = "offlineDatabase";
        private static final String TABLE_NAME = "forumnTable";
        private static final int DATABASE_Version = 1;
        private static final String ROW_ID = "_id";       // Column 1 (Primary Key)
        private static final String POST_ID = "postId";   // Column 2
        private static final String TITLE = "title";  // Column 3
        private static final String BODY = "postBody";
        private static final String USER_ID = "userId";
        private static final String TAGS = "tags";
        private static final String PRICE = "price";
        private static final String USERNAME = "forumId";
        private static final String RATING = "averageRating";
        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + POST_ID + " VARCHAR(255) ," + TITLE + " VARCHAR(255) , "
                + BODY + " VARCHAR(255) , " + USER_ID + " VARCHAR(255) , " + TAGS + " VARCHAR(255) ," + PRICE + " VARCHAR(255) ," + USERNAME + " VARCHAR(255) ," + RATING + " FLOAT(1))";
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        private static final String RATING_NAME = "ratingTable";
        private static final String RATE_ROW_ID = "_id";       // Column 1 (Primary Key)
        private static final String RATING_ID = "ratingId";
        private static final String TARGET_ID = "targetUUID";
        private static final String AUTHOR_ID = "authorUUID";
        private static final String RATE_TITLE = "title";
        private static final String COMMENT = "comment";
        private static final String RATE_VALUE = "rateValue";
        private static final String CREATE_RATING_TABLE = "CREATE TABLE " + RATING_NAME + " (" + RATE_ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + RATING_ID + " VARCHAR(255) , " + TARGET_ID + " VARCHAR(255) ," + AUTHOR_ID + " VARCHAR(255) , "
                + RATE_TITLE + " VARCHAR(255) , " + COMMENT + " VARCHAR(255) , " + RATE_VALUE + " FLOAT(1))";
        private static final String DROP_RATING_TABLE = "DROP TABLE IF EXISTS " + RATING_NAME;


        private Context context;


        // Constructor
        public SimpleDBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context = context;
        }

        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TABLE);
                db.execSQL(CREATE_RATING_TABLE);
            } catch (Exception e) {

            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                //Message.message(context, "OnUpgrade");
                db.execSQL(DROP_TABLE);
                db.execSQL(DROP_RATING_TABLE);
                onCreate(db);
            } catch (Exception e) {
                //Message.message(context, "" + e);
            }
        }


    }
}
