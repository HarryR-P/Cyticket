package com.example.cyticketclient.logic;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cyticketclient.R;
import com.example.cyticketclient.data.PostObject;

import java.util.List;

public class PostAdapter extends BaseAdapter {

    private Context context;
    private List<PostObject> posts;
    private LayoutInflater postInflater;

    public PostAdapter(Context c, List<PostObject> posts)
    {
        this.posts = posts;
        this.context = c;
        postInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Object getItem(int i) {
        return posts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return posts.get(i).getPostId();
    }



    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        // todo implement viewHolder pattern

        View v = postInflater.inflate(R.layout.post_layout, null);

        TextView userName = (TextView) v.findViewById(R.id.userName);
        TextView description = (TextView) v.findViewById(R.id.description);
        TextView price = (TextView) v.findViewById(R.id.price);
        TextView title = (TextView) v.findViewById(R.id.postTitle);
        RatingBar ratingBar = (RatingBar) v.findViewById(R.id.ratingBar);

        userName.setText(posts.get(i).getUserName());
        // Limiting the number of lines of the description to two
        if(posts.get(i).getBody().length() > 66 ) {
            description.setText(posts.get(i).getBody().substring(0, 66));
        }else
        {
            description.setText(posts.get(i).getBody());
        }
        price.setText(posts.get(i).getPrice());
        ratingBar.setRating(posts.get(i).getPostRating());
        title.setText(posts.get(i).getTitle());

        return v;
    }
}
