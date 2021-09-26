package com.example.cyticketclient.interfaces;

import com.android.volley.VolleyError;

import org.json.JSONArray;

public interface IDatabaseListener {

    /**
     * Method to run after DatabaseAdapter refreshPosts gets a response.
     * @param response
     */
    void onPostDataSuccess(JSONArray response);

    void onRatingDataSuccess(JSONArray response);

    /**
     * Method to run after DatabaseAdapter refreshPosts fails.
     * @param error
     */
    void onDataFail(VolleyError error);
}
