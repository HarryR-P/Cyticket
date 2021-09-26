package com.example.cyticketclient.UI;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.cyticketclient.app.AppController;
import com.example.cyticketclient.data.DatabaseAdapter;
import com.example.cyticketclient.data.PostObject;
import com.example.cyticketclient.interfaces.IDatabaseListener;
import com.example.cyticketclient.interfaces.IWebSocketListener;
import com.example.cyticketclient.interfaces.ListenerInterface;
import com.example.cyticketclient.logic.PostAdapter;
import com.example.cyticketclient.R;
import com.example.cyticketclient.net_utils.Const;
import com.example.cyticketclient.networking.ServerCall;
import com.example.cyticketclient.networking.WebSocketCall;

import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class ForumFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener, IDatabaseListener, IWebSocketListener {

    private View view;
    private ListView forumList;
    private List<PostObject> posts;
    private DatabaseAdapter data;
    private ProgressDialog pDialog;
    private TextView newPost;
    private final String FORUM_RESPONSE_MSG = "UPDATE_READY";

    public ForumFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_forum, container, false);

        // sets new Websocket if the global messages Websocket equals null
        if(AppController.getInstance().getForumSocket() == null)
        {
            WebSocketCall call = new WebSocketCall();
            String URL = Const.URL_FORUM_WEBSOCKET + AppController.getInstance().getUser().getUUID();
            AppController.getInstance().setForumSocket(call.getWebSocketClient(URL,this));
            AppController.getInstance().getForumSocket().connect();
        }


        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        forumList = (ListView) view.findViewById(R.id.forumList);
        Button refreshPostBtn = view.findViewById(R.id.refreshPostBtn);
        Button addForumPostBtn = view.findViewById(R.id.addForumPostBtn);
        newPost = view.findViewById(R.id.newPostMsg);

        addForumPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toAddPost = new Intent(getContext(),AddPostActivity.class);
                startActivity(toAddPost);
            }
        });

        data = new DatabaseAdapter(getActivity());

        refreshPostBtn.setOnClickListener(this);

        posts = inverseList(data.getStoredPosts());
        PostAdapter postAdapter = new PostAdapter(getActivity(),posts);
        forumList.setAdapter(postAdapter);

        forumList.setOnItemClickListener(this);

        return view;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        // passes post info to specificPost
        Intent showSpecificPost = new Intent(getActivity(), SpecificPost.class);
        showSpecificPost.putExtra("com.example.cyticketclient.AuthorID", posts.get(i).getAuthorId().toString());
        showSpecificPost.putExtra("com.example.cyticketclient.PostBody", posts.get(i).getBody());
        showSpecificPost.putExtra("com.example.cyticketclient.PostTitle", posts.get(i).getTitle());
        showSpecificPost.putExtra("com.example.cyticketclient.PostID", posts.get(i).getPostId());
        showSpecificPost.putExtra("com.example.cyticketclient.PostRating",posts.get(i).getPostRating());
        showSpecificPost.putExtra("com.example.cyticketclient.PostUsername", posts.get(i).getUserName());
        showSpecificPost.putExtra("com.example.cyticketclient.PostPrice",posts.get(i).getPrice());
        startActivity(showSpecificPost);
    }

    // refreshes forum
    @Override
    public void onClick(View view) {
        pDialog.show();
        data.clear();
        data.refreshDatabase(this);
        newPost.setText("");
//        AppController.getInstance().getForumSocket().send("UPDATE_REQUEST");
    }

    // sets new posts on refresh success
    @Override
    public void onPostDataSuccess(JSONArray response) {
        posts = inverseList(data.getStoredPosts());
        PostAdapter postAdapter = new PostAdapter(getActivity(),posts);
        forumList.setAdapter(postAdapter);
        pDialog.hide();
    }

    @Override
    public void onRatingDataSuccess(JSONArray response) {
    }

    @Override
    public void onDataFail(VolleyError error) {
        pDialog.hide();
    }

    private List<PostObject> inverseList(List<PostObject> list){
        List<PostObject> returnList = new ArrayList<PostObject>();
        int listSize = list.size()-1;
        if(listSize <= 0)
        {
            return list;
        }
        for(int i= listSize ;i >= 0;i--)
        {
            returnList.add(list.get(i));
        }
        return returnList;
    }

    @Override
    public void onOpen(ServerHandshake ServerHandshake) {
    }

    @Override
    public void onMessage(final String msg) {
        // sets
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(msg.equals(FORUM_RESPONSE_MSG)) {
                    newPost.setText("Reload for new posts.");
                }
            }
        });


    }

    @Override
    public void onClose(int errorCode, String reason, boolean remote) {
    }

    @Override
    public void onError(Exception error) {
    }
}