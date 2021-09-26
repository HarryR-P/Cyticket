package com.example.cyticketclient.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.cyticketclient.R;
import com.example.cyticketclient.app.AppController;
import com.example.cyticketclient.interfaces.IWebSocketListener;
import com.example.cyticketclient.interfaces.ListenerInterface;
import com.example.cyticketclient.logic.ReviewAdapter;
import com.example.cyticketclient.net_utils.Const;
import com.example.cyticketclient.networking.ServerCall;
import com.example.cyticketclient.networking.WebSocketCall;

import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;

public class SpecificPost extends AppCompatActivity implements ListenerInterface, IWebSocketListener {

    private ListView reviewList;
    private ProgressDialog pDialog;
    private EditText newMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_post);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        Intent in = getIntent();
        final String AuthorUUID = in.getStringExtra("com.example.cyticketclient.AuthorID");
        String userName = in.getStringExtra("com.example.cyticketclient.PostUsername");
        String Text = in.getStringExtra("com.example.cyticketclient.PostBody");
        String Price = in.getStringExtra("com.example.cyticketclient.PostPrice");
        String Title = in.getStringExtra("com.example.cyticketclient.PostTitle");
        int PostId = in.getIntExtra("com.example.cyticketclient.PostID",-1);
        float postRating = in.getFloatExtra("com.example.cyticketclient.PostRating",0);

        if(AppController.getInstance().getForumSocket() == null)
        {
            WebSocketCall call = new WebSocketCall();
            String URL = Const.URL_FORUM_WEBSOCKET + AuthorUUID;
            AppController.getInstance().setForumSocket(call.getWebSocketClient(URL,this));
            AppController.getInstance().getForumSocket().connect();
        }

        TextView specificPostUserName = (TextView) findViewById(R.id.specificPostUserName);
        TextView specificPostText = (TextView) findViewById(R.id.specificPostText);
        TextView specificPostPrice = (TextView) findViewById(R.id.specificPostPrice);
        TextView specificPostTitle = (TextView) findViewById(R.id.specificPostTitle);
        ImageView specificPostProfilePic = (ImageView) findViewById(R.id.specificPostProfilePic);
        RatingBar specificPostRating = (RatingBar) findViewById(R.id.specificPostRating);
        reviewList = (ListView) findViewById(R.id.SpecificReviewLayout);
        newMessage = (EditText) findViewById(R.id.newMessage);
        Button addReviewBtn = (Button) findViewById(R.id.specificPostAddRatingBtn);
        Button backBtn = (Button) findViewById(R.id.specificPostBackBtn);
        Button sendBtn = (Button) findViewById(R.id.specificPostDMBtn);

        addReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toAddReview = new Intent(getApplicationContext(),AddRatingActivity.class);
                toAddReview.putExtra("com.example.cyticketclient.SpecificPost.AuthorID", AuthorUUID);
                startActivity(toAddReview);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toMain = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(toMain);
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppController.getInstance().getForumSocket().send(AuthorUUID + "~" + newMessage.getText().toString());
            }
        });

        specificPostUserName.setText(userName);
        specificPostText.setText(Text);
        specificPostPrice.setText(Price);
        specificPostTitle.setText(Title);
        specificPostRating.setRating(postRating);

        pDialog.show();
        ServerCall call = new ServerCall();
        call.get(Const.URL_RATING_GET_TARGET + AuthorUUID,this);



    }

    @Override
    public void onSuccess(JSONArray response) {
        ReviewAdapter adapter = new ReviewAdapter(this,response);
        reviewList.setAdapter(adapter);
        pDialog.hide();
    }

    @Override
    public void onSuccess(String response) {
    }

    @Override
    public void onFailure(VolleyError error) {
        pDialog.hide();
    }

    @Override
    public void onOpen(ServerHandshake ServerHandshake) {

    }

    @Override
    public void onMessage(String msg) {

    }

    @Override
    public void onClose(int errorCode, String reason, boolean remote) {

    }

    @Override
    public void onError(Exception error) {

    }
}