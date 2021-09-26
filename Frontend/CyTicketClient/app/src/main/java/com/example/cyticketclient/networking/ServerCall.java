package com.example.cyticketclient.networking;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.cyticketclient.app.AppController;
import com.example.cyticketclient.interfaces.ListenerInterface;
import com.example.cyticketclient.interfaces.ServerCallInterface;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class ServerCall implements ServerCallInterface {

    private String TAG = ServerCall.class.getSimpleName();
    private String tag_post = "jobj_post_req";
    private String tag_profile_get = "profile_get_req";

    public ServerCall(){
    }

    /**
     * Gets info from server at given URL. Must implement ListenerInterface in target class
     * @param URL
     * @param listener
     */
    @Override
    public void get(String URL, final ListenerInterface listener) {

        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, URL,null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        listener.onSuccess(response);
                        Log.d(TAG, response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onFailure(error);
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                    }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req, tag_profile_get);
    }

    /**
     * Posts info to server of given URL. Requires parameters to be put in a Map, and the body
     * in a JSONObject
     * @param URL
     * @param params
     * @param jsonObj
     */
    @Override
    public void post(String URL, final Map<String, String> params, JSONObject jsonObj,final ListenerInterface listener) {
        StringRequest postRequest = new StringRequest( Request.Method.POST , URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        listener.onSuccess(response);
                        Log.d(TAG, response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onFailure(error);
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        }){
            // Enter profile details
            @Override
            protected Map<String, String> getParams() {
                return cloneMap(params);
            }

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(postRequest, tag_post);

    }

    @Override
    public void put(String URL, Map<String, String> params, JSONObject jsonObj) {
    }

    @Override
    public void delete(String URL, Map<String,String> params, JSONObject jsonObj){
    }

    @Override
    public void getPhoto(String URL, Response.Listener<JSONArray> listener) {
    }

    @Override
    public void postPhoto(String URL, Map<String, String> params, JSONObject jsonObj) {
    }

    @Override
    public void putPhoto(String URL, Map<String, String> params, JSONObject jsonObj) {
    }

    @Override
    public void deletePhoto(String URL, Map<String, String> params, JSONObject jsonObj) {
    }

    private Map<String,String> cloneMap(Map<String,String> map){
        return new HashMap<>(map);
    }

}
