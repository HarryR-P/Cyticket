package com.example.cyticketclient.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.example.cyticketclient.R;
import com.example.cyticketclient.app.AppController;
import com.example.cyticketclient.data.DatabaseAdapter;
import com.example.cyticketclient.data.RatingObject;
import com.example.cyticketclient.interfaces.IDatabaseListener;
import com.example.cyticketclient.interfaces.ListenerInterface;
import com.example.cyticketclient.logic.PostAdapter;
import com.example.cyticketclient.logic.ReviewAdapter;
import com.example.cyticketclient.net_utils.Const;
import com.example.cyticketclient.networking.ServerCall;

import org.json.JSONArray;

import java.util.List;

public class ReviewsActivity extends AppCompatActivity implements IDatabaseListener, View.OnClickListener {

    private ProgressDialog pDialog;
    private ListView reviewList;
    private DatabaseAdapter database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        reviewList = (ListView) findViewById(R.id.reviewList);
        Button backBtn = (Button) findViewById(R.id.reviewBackBtn);
        Button refreshBtn = (Button) findViewById(R.id.ratingRefreshBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toMain = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(toMain);
            }
        });

        database = new DatabaseAdapter(this);
        List<RatingObject> ratings = database.getStoredRatings();
        ReviewAdapter adapter = new ReviewAdapter(this,ratings);
        reviewList.setAdapter(adapter);
        refreshBtn.setOnClickListener(this);
//        ServerCall call = new ServerCall();
//        call.get(Const.URL_RATING_GET_AUTHOR + AppController.getInstance().getUser().getUUID(),this);
    }

    @Override
    public void onPostDataSuccess(JSONArray response) {
    }

    @Override
    public void onRatingDataSuccess(JSONArray response) {
        List<RatingObject> posts = database.getStoredRatings();
        ReviewAdapter postAdapter = new ReviewAdapter(this,posts);
        reviewList.setAdapter(postAdapter);
        pDialog.hide();
    }

    @Override
    public void onDataFail(VolleyError error) {
        pDialog.hide();
    }

    @Override
    public void onClick(View view) {
        pDialog.show();
        database.clearRatings();
        database.refreshRatings(this);
    }
}