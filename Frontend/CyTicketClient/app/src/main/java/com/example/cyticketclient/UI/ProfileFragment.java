package com.example.cyticketclient.UI;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.cyticketclient.R;
import com.example.cyticketclient.app.AppController;


public class ProfileFragment extends Fragment {

    private View view;

    private TextView pUserName;
    private TextView profileEmail;
    private TextView pDescription;
    private TextView firstName;
    private TextView lastName;

    public ProfileFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        pUserName = (TextView) view.findViewById(R.id.pUserName);
        profileEmail = (TextView) view.findViewById(R.id.profileEmail);
        ImageView pProfilePic = (ImageView) view.findViewById(R.id.pProfilePic);
        RatingBar profileRating = (RatingBar) view.findViewById(R.id.profileRating);
        pDescription = (TextView) view.findViewById(R.id.pDescription);
        firstName = (TextView) view.findViewById(R.id.firstName);
        lastName = (TextView) view.findViewById(R.id.lastName);
        Button reviewBtn = (Button) view.findViewById(R.id.profileReviewsBtn);

        reviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toReviews = new Intent(getContext(),ReviewsActivity.class);
                startActivity(toReviews);
            }
        });

        pUserName.setText(AppController.getInstance().getUser().getUserName());
        profileEmail.setText(AppController.getInstance().getUser().getEmail());
        firstName.setText(AppController.getInstance().getUser().getFirstName());
        lastName.setText(AppController.getInstance().getUser().getLastName());
        profileRating.setRating(AppController.getInstance().getUser().getAvgRating());


        return view;
    }

}