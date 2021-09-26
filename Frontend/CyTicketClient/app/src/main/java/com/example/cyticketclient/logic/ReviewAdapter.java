package com.example.cyticketclient.logic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.cyticketclient.R;
import com.example.cyticketclient.data.RatingObject;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class ReviewAdapter extends BaseAdapter {

    private LayoutInflater reviewInflater;
    private Context context;
    private JSONArray JSONRatings;
    private List<RatingObject> ObjectRatings;
    private boolean JSON;

    public ReviewAdapter(Context c, JSONArray ratings){
        this.JSONRatings = ratings;
        context = c;
        reviewInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        JSON = true;
    }

    public ReviewAdapter(Context c, List<RatingObject> ratings){
        this.ObjectRatings = ratings;
        context = c;
        reviewInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        JSON = false;
    }

    @Override
    public int getCount() {
        if(JSON)
        {
            return JSONRatings.length();
        } else
        {
            return ObjectRatings.size();
        }
    }

    @Override
    public Object getItem(int i) {
        try {
            if(JSON)
            {
                return JSONRatings.getJSONObject(i).getString("title");
            }else
            {
                return ObjectRatings.get(i).getTitle();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = reviewInflater.inflate(R.layout.review_layout, null);

        TextView title = (TextView) v.findViewById(R.id.reviewTitle);
        TextView description = (TextView) v.findViewById(R.id.reviewDescription);
        RatingBar rating = (RatingBar) v.findViewById(R.id.reviewRating);

        try {
            if(JSON)
            {
                description.setText(JSONRatings.getJSONObject(i).getString("comment"));
                title.setText(JSONRatings.getJSONObject(i).getString("title"));
                rating.setRating(Float.parseFloat(JSONRatings.getJSONObject(i).getString("rateValue")));
            }else
            {
                description.setText(ObjectRatings.get(i).getComment());
                title.setText(ObjectRatings.get(i).getTitle());
                rating.setRating(Float.parseFloat( ObjectRatings.get(i).getRateValue() + ""));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return v;
    }
}
