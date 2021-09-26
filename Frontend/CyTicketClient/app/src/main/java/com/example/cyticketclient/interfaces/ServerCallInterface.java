package com.example.cyticketclient.interfaces;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.Map;

public interface ServerCallInterface {

    /**
     * Method for sending get request.
     * @param URL
     * @param listener
     */
    void get(String URL, ListenerInterface listener);

    /**
     * Method for sending post request.
     * @param URL
     * @param params
     * @param jsonObj
     * @param listener
     */
    void post(String URL, Map<String,String> params, JSONObject jsonObj, ListenerInterface listener);

    /**
     * Method for sending put request.
     * @param URL
     * @param params
     * @param jsonObj
     */
    void put(String URL, Map<String,String> params, JSONObject jsonObj);

    /**
     * Method for sending delete request.
     * @param URL
     * @param params
     * @param jsonObj
     */
    void delete(String URL, Map<String,String> params, JSONObject jsonObj);

    /**
     * Method for sending get requests for photos.
     * @param URL
     * @param listener
     */
    void getPhoto(String URL, Response.Listener<JSONArray> listener);

    /**
     * Method for sending post requests for photos.
     * @param URL
     * @param params
     * @param jsonObj
     */
    void postPhoto(String URL, Map<String,String> params, JSONObject jsonObj);

    /**
     * Method for sending put requests for photos.
     * @param URL
     * @param params
     * @param jsonObj
     */
    void putPhoto(String URL, Map<String,String> params, JSONObject jsonObj);

    /**
     * Method for sending delete requests for photos.
     * @param URL
     * @param params
     * @param jsonObj
     */
    void deletePhoto(String URL, Map<String,String> params, JSONObject jsonObj);
}
