package com.example.cyticketclient.interfaces;

import com.android.volley.VolleyError;

import org.json.JSONArray;

public interface ListenerInterface {

    /**
     * Method to run after the serverCall get gets a response.
     * @param response
     */
    void onSuccess(JSONArray response);

    /**
     * Method to run after the ServerCall post method gets a response.
     * @param response
     */
    void onSuccess(String response);

    /**
     * Method to run after after ServerCall fails.
     * @param error
     */
    void onFailure(VolleyError error);
}
