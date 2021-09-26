package com.example.cyticketclient.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.cyticketclient.R;
import com.example.cyticketclient.app.AppController;
import com.example.cyticketclient.data.DatabaseAdapterDMSpecific;
import com.example.cyticketclient.data.MessageObject;
import com.example.cyticketclient.interfaces.IWebSocketListener;
import com.example.cyticketclient.logic.ChatAdapter;
import com.example.cyticketclient.net_utils.Const;
import com.example.cyticketclient.networking.WebSocketCall;

import org.java_websocket.handshake.ServerHandshake;

import java.util.Arrays;
import java.util.List;

/**
 * Class for displaying a single message.
 * @author Logan Kroeze
 */
public class SpecificMessage extends AppCompatActivity implements IWebSocketListener {

    private View view;
    private DatabaseAdapterDMSpecific data;
    private ListView chatList;
    private String longMessage;
    private String longSenders;
    private List<String> bodies;
    private List<String> senders;
    private List<MessageObject> messages;
    private EditText newMessage;
    private String conversation;
    private final String CHAT_RESPONSE_MSG = "UPDATE_READY";

    /**
     * Displays a specific message.
     *
     * @param savedInstanceState Message Bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_message);
        chatList = (ListView) view.findViewById(R.id.messagesList);

        // sets new Websocket if the global messages Websocket equals null
        if(AppController.getInstance().getForumSocket() == null)
        {
            WebSocketCall call = new WebSocketCall();
            String URL = Const.URL_FORUM_WEBSOCKET + conversation;
            AppController.getInstance().setForumSocket(call.getWebSocketClient(URL,this));
            AppController.getInstance().getForumSocket().connect();
        }

        Intent in = getIntent();
        conversation = in.getStringExtra("com.example.cyticketclient.conversation");
        longMessage = in.getStringExtra("com.example.cyticketclient.messages");
        longSenders = in.getStringExtra("com.example.cyticketclient.senders");
        bodies = Arrays.asList(longMessage.split("~"));
        senders = Arrays.asList(longSenders.split("~"));
        for (int i = 0; i < bodies.size(); i++) {
            messages.add(new MessageObject());
            messages.get(i).setMessage(bodies.get(i));
            messages.get(i).setSender(senders.get(i));
        }

        TextView specificMessageSender = (TextView) findViewById(R.id.specificMessageSender);
        ImageView specificMessageProfilePic = (ImageView) findViewById(R.id.specificMessageProfilePic);
        Button sendBtn = (Button) findViewById(R.id.sendBtn);
        specificMessageSender.setText(conversation);
        ChatAdapter chatAdapter = new ChatAdapter(null, messages);
        chatList.setAdapter(chatAdapter);
        newMessage = (EditText) findViewById(R.id.newMessage);
        sendBtn.setOnClickListener((View.OnClickListener) this);
    }

    public void onClick() {
        AppController.getInstance().getForumSocket().send(conversation + "~" + newMessage.getText().toString());
    }

    @Override
    public void onOpen(ServerHandshake ServerHandshake) {

    }

    @Override
    public void onMessage(final String msg) {
        runOnUiThread(new Runnable() {
        @Override
        public void run() {
            if(msg.equals(CHAT_RESPONSE_MSG)) {
                data.clear();
                data.refreshDatabase();
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