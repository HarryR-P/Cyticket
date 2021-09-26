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

public class ChatAdapter extends BaseAdapter {

    private LayoutInflater chatInflater;
    private List<MessageObject> messages;

    public ChatAdapter(Context c, List<MessageObject> messages) {
        chatInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return messages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = chatInflater.inflate(R.layout.sent_message, null);

        TextView sender = (TextView) v.findViewById(R.id.sender);
        TextView message = (TextView) v.findViewById(R.id.message);

        sender.setText(messages.get(i).getSender());
        message.setText(messages.get(i).getMessage());

        return v;
    }
}