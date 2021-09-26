package com.example.cyticketclient.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.example.cyticketclient.R;
import com.example.cyticketclient.app.AppController;
import com.example.cyticketclient.data.DatabaseAdapter;
import com.example.cyticketclient.interfaces.IDatabaseListener;
import com.example.cyticketclient.interfaces.ListenerInterface;
import com.example.cyticketclient.net_utils.Const;
import com.example.cyticketclient.networking.ServerCall;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class AddPostActivity extends AppCompatActivity implements ListenerInterface, View.OnClickListener, IDatabaseListener {

    private EditText addPostTitle;
    private EditText addPostPrice;
    private EditText addPostTag;
    private EditText addPostBody;
    private final String SEND_MSG = "UPDATE_REQUEST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        addPostTitle = (EditText) findViewById(R.id.addPostTitle);
        addPostPrice = (EditText) findViewById(R.id.addPostPrice);
        addPostTag = (EditText) findViewById(R.id.addPostTag);
        addPostBody = (EditText) findViewById(R.id.addPostBody);

        Button postSubmitBtn = (Button) findViewById(R.id.postSubmitBtn);

        postSubmitBtn.setOnClickListener(this);
    }

    @Override
    public void onSuccess(JSONArray response) {
    }

    @Override
    public void onSuccess(String response) {
        DatabaseAdapter data = new DatabaseAdapter(this);
        data.refreshDatabase(this);
    }

    @Override
    public void onFailure(VolleyError error) {
    }

    @Override
    public void onClick(View view) {

        Map<String,String> params = new HashMap<>();
        params.put("title",addPostTitle.getText().toString());
        params.put("postBody",addPostBody.getText().toString());
        params.put("userId", AppController.getInstance().getUser().getUUID());
        params.put("tag",addPostTag.getText().toString());
        params.put("price",addPostPrice.getText().toString());
        ServerCall call = new ServerCall();
        call.post(Const.URL_FORUM_POST_REQUEST,params,null,this);
    }

    @Override
    public void onPostDataSuccess(JSONArray response) {
        AppController.getInstance().getForumSocket().send(SEND_MSG);
        Intent goToMain = new Intent(getApplication(), MainActivity.class);
        startActivity(goToMain);
    }

    @Override
    public void onRatingDataSuccess(JSONArray response) {
    }

    @Override
    public void onDataFail(VolleyError error) {

    }
}