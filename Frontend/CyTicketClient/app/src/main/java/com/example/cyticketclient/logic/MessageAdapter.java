package com.example.cyticketclient.logic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cyticketclient.R;
import com.example.cyticketclient.app.AppController;
import com.example.cyticketclient.data.MessageObject;

import java.util.List;

/**
 * Class for displaying Messages.
 * @author Logan Kroeze
 */
public class MessageAdapter extends BaseAdapter {

    private LayoutInflater messageInflater;
    private List<String> conversations;
    private List<MessageObject> messages;

    /**
     * MessageAdapter constructor.
     * @param c Message Context
     * @param conversations list of all messages.
     */
    public MessageAdapter(Context c, List<String> conversations)
    {
        messageInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Returns number of messages.
     * @return Number of messages.
     */
    @Override
    public int getCount() {
        return 0;
    }

    /**
     * Returns a specific message.
     * @param i Message index.
     * @return Specific message object.
     */
    @Override
    public Object getItem(int i) {
        return conversations.get(i);
    }

    /**
     * Returns message ID of a specific message.
     * @param i Message index.
     * @return Message ID of a specific message object.
     */
    @Override
    public long getItemId(int i) {
        return 0;
    }

    /**
     * Returns a view object of a specific message.
     * @param i Message index.
     * @param view Message View.
     * @param viewGroup Message ViewGroup.
     * @return View of specific message object.
     */
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = messageInflater.inflate(R.layout.message_layout, null);

        TextView userName = (TextView) v.findViewById(R.id.userName);

        // todo add username to message object
        userName.setText(conversations.get(i));

        return v;
    }
}