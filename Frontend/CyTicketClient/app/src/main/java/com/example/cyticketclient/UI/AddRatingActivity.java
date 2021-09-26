package com.example.cyticketclient.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

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

public class AddRatingActivity extends AppCompatActivity implements ListenerInterface, View.OnClickListener, IDatabaseListener {

    private EditText title;
    private EditText comment;
    private RatingBar rating;
    private String targetUUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rating);

        Intent in = getIntent();
        targetUUID = in.getStringExtra("com.example.cyticketclient.SpecificPost.AuthorID");

        title = (EditText) findViewById(R.id.addReviewTitle);
        comment = (EditText) findViewById(R.id.addReviewComment);
        rating = (RatingBar) findViewById(R.id.addReviewRating);
        final Button ratingBtn = (Button) findViewById(R.id.addReviewSubmitBtn);

        ratingBtn.setOnClickListener(this);
    }

    @Override
    public void onSuccess(JSONArray response) {
    }

    @Override
    public void onSuccess(String response) {
        DatabaseAdapter forumData = new DatabaseAdapter(this);
        forumData.refreshRatings(this);
    }

    @Override
    public void onFailure(VolleyError error) {
    }

    @Override
    public void onClick(View view) {
        HashMap<String,String> params = new HashMap<>();
        params.put("targetUUID",targetUUID);
        params.put("authorUUID", AppController.getInstance().getUser().getUUID());
        params.put("title",title.getText().toString());
        params.put("comment",comment.getText().toString());
        params.put("rateValue",String.valueOf(rating.getRating()));

        ServerCall call = new ServerCall();
        call.post(Const.URL_RATING_POST,params,null,this);
    }

    @Override
    public void onPostDataSuccess(JSONArray response) {
    }

    @Override
    public void onRatingDataSuccess(JSONArray response) {
        Intent toSpecificPost = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(toSpecificPost);
    }

    @Override
    public void onDataFail(VolleyError error) {

    }
}