package com.example.cyticketclient.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.VolleyError;
import com.example.cyticketclient.R;
import com.example.cyticketclient.app.AppController;
import com.example.cyticketclient.data.DatabaseAdapter;
import com.example.cyticketclient.data.DatabaseAdapterDMSpecific;
import com.example.cyticketclient.data.MessageObject;
import com.example.cyticketclient.interfaces.ListenerInterface;
import com.example.cyticketclient.logic.MessageAdapter;
import com.example.cyticketclient.logic.PostAdapter;
import com.example.cyticketclient.net_utils.Const;
import com.example.cyticketclient.networking.ServerCall;
import com.example.cyticketclient.networking.WebSocketCall;
import com.example.cyticketclient.interfaces.IDatabaseListener;
import com.example.cyticketclient.interfaces.IWebSocketListener;

import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;

import java.util.List;

/**
 * Creates the Messages Fragment.
 * @author Logan Kroeze
 */
public class MessagesFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener, IDatabaseListener {

    private View view;
    private ListView messagesList;
    private List<MessageObject> messages;
    private DatabaseAdapterDMSpecific data;
    private ProgressDialog pDialog;
    private TextView newMessage;
    private final String MESSAGES_RESPONSE_MSG = "UPDATE_READY";
    private List<String> conversations;
    private String longMessage = "";
    private String longSenders = "";

    /**
     * MessagesFragment constructor.
     */
    public MessagesFragment() {
    }

    /**
     * Loads Messages Fragment.
     *
     * @param savedInstanceState Messages Bundle.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Displays Messages.
     *
     * @param inflater           Messages LayoutInflater.
     * @param container          Messages ViewGroup.
     * @param savedInstanceState Messages Bundle.
     * @return Messages View.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_messages, container, false);

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        messagesList = (ListView) view.findViewById(R.id.messagesList);
        Button refreshMessagesBtn = view.findViewById(R.id.refreshPostBtn);

        data = new DatabaseAdapterDMSpecific(getActivity());

        refreshMessagesBtn.setOnClickListener(this);

        messages = data.getStoredMessages();
        for (int i = 0; i < messages.size(); i++) {
            if (messages.get(i).getSender() == AppController.getInstance().getUser().getUUID() || messages.get(i).getReceiver() == AppController.getInstance().getUser().getUUID()) {
                if (conversations.contains(messages.get(i).getSender()) == false) {
                    conversations.add(messages.get(i).getSender());
                }
                if (conversations.contains(messages.get(i).getReceiver()) == false) {
                    conversations.add(messages.get(i).getReceiver());
                }
            }
        }
        if(conversations != null) {
            if(conversations.contains(AppController.getInstance().getUser().getUUID())) {
                conversations.remove(AppController.getInstance().getUser().getUUID());
            }
        }

        MessageAdapter messageAdapter = new MessageAdapter(getActivity(), conversations);
        messagesList.setAdapter(messageAdapter);

        messagesList.setOnItemClickListener(this);

        return view;
    }

    /**
     * Refreshes Messages fragment.
     *
     * @param view Messages View.
     */
    @Override
    public void onClick(View view) {
        data.clear();
        data.refreshDatabase();
    }

    /**
     * Displays a SpecificMessage when clicked.
     *
     * @param adapterView Messages AdapterView.
     * @param view        Messages View.
     * @param i           Message index.
     * @param l
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent showSpecificMessage = new Intent(getActivity(), SpecificMessage.class);
        showSpecificMessage.putExtra("com.example.cyticketclient.conversation", conversations.get(i));
        for (int j = 0; j < messages.size(); j++) {
            if (messages.get(j).getSender() == conversations.get(i) || messages.get(j).getReceiver() == conversations.get(i)) {
                longMessage = longMessage + messages.get(j).getMessage() + "~";
            }
        }
        showSpecificMessage.putExtra("com.example.cyticketclient.messages", longMessage);
        for (int j = 0; j < messages.size(); j++) {
            if (messages.get(j).getSender() == conversations.get(i) || messages.get(j).getReceiver() == conversations.get(i)) {
                longSenders = longSenders + messages.get(j).getSender() + "~";
            }
        }
        showSpecificMessage.putExtra("com.example.cyticketclient.senders", longSenders);
        startActivity(showSpecificMessage);
    }

    @Override
    public void onPostDataSuccess(JSONArray response) {
        messages = data.getStoredMessages();
        for (int i = 0; i < messages.size(); i++) {
            if (messages.get(i).getSender() == AppController.getInstance().getUser().getUUID() || messages.get(i).getReceiver() == AppController.getInstance().getUser().getUUID()) {
                if (conversations.contains(messages.get(i).getSender()) == false) {
                    conversations.add(messages.get(i).getSender());
                }
                if (conversations.contains(messages.get(i).getReceiver()) == false) {
                    conversations.add(messages.get(i).getReceiver());
                }
            }
        }
        conversations.remove(AppController.getInstance().getUser().getUUID());
        MessageAdapter messageAdapter = new MessageAdapter(getActivity(), conversations);
        messagesList.setAdapter(messageAdapter);
        pDialog.hide();
    }

    @Override
    public void onRatingDataSuccess(JSONArray response) {
    }

    @Override
    public void onDataFail(VolleyError error) {
        pDialog.hide();
    }
}
