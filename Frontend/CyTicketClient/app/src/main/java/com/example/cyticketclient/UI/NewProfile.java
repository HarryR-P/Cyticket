package com.example.cyticketclient.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.cyticketclient.R;
import com.example.cyticketclient.interfaces.ListenerInterface;
import com.example.cyticketclient.networking.ServerCall;
import com.example.cyticketclient.net_utils.Const;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NewProfile extends AppCompatActivity implements ListenerInterface, View.OnClickListener {

    private final String SUCCESS_MSG = "New User has been registered";

    private EditText editFirstName;
    private EditText editLastName;
    private EditText editUsername;
    private EditText editEmail;
    private EditText editPassword;
    private TextView addUserErrorView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_profile);

        editUsername = (EditText) findViewById(R.id.editUsername);
        editEmail = (EditText) findViewById(R.id.editEmail);
        Button submitEditProfileBtn = (Button) findViewById(R.id.submitProfileEditBtn);
        editFirstName = (EditText) findViewById(R.id.editFirstName);
        editLastName = (EditText) findViewById(R.id.editLastName);
        editPassword = (EditText) findViewById(R.id.editPassword);
        addUserErrorView = (TextView) findViewById(R.id.addUserErrorView);

        submitEditProfileBtn.setOnClickListener(this);

    }

    @Override
    public void onSuccess(JSONArray response) {
    }

    @Override
    public void onSuccess(String response) {
        if(response.equals(SUCCESS_MSG))
        {
            Intent returnToProfile = new Intent( getApplicationContext() , LoginActivity.class);
            startActivity(returnToProfile);
        }else
        {
            addUserErrorView.setText(response);
        }
    }

    @Override
    public void onFailure(VolleyError error) {

    }

    @Override
    public void onClick(View view) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("userName", editUsername.getText() + "");
        params.put("email", editEmail.getText() + "");
        params.put("firstName", editFirstName.getText() + "");
        params.put("lastName", editLastName.getText() + "");
        params.put("password",editPassword.getText() + "");
        // Post info to server
        ServerCall call = new ServerCall();
        call.post(Const.URL_USER_POST_REQUEST,params,null,this);

    }

}