package com.example.cyticketclient.interfaces;

import com.android.volley.VolleyError;

import org.json.JSONArray;

public interface IUserListener {

    void onUserSuccess(JSONArray response);

    void onUserFail(VolleyError error);
}
