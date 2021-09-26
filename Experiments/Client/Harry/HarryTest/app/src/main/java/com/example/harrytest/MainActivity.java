package com.example.harrytest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final float[] list =  new float[9];
        for(int i = 0; i <9; i++)
    {
        list[i] = (float) 2.5;
    }
    final TextView ratingResult = (TextView) findViewById(R.id.ratingResult);

        final float[] avg = {average(list)};
        ratingResult.setText("avg: " + String.format("%.2f", avg[0]));

    final RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
        @Override
        public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

            list[0] = v;
            avg[0] = average(list);
            ratingResult.setText("avg: " + String.format("%.2f", avg[0]));
        }
    });

    final RatingBar ratingBar2 = (RatingBar) findViewById(R.id.ratingBar2);
        ratingBar2.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
        @Override
        public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

            list[1] = v;
            avg[0] = average(list);
            ratingResult.setText("avg: " + String.format("%.2f", avg[0]));
        }
    });

    final RatingBar ratingBar3 = (RatingBar) findViewById(R.id.ratingBar3);
        ratingBar3.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
        @Override
        public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

            list[2] = v;
            avg[0] = average(list);
            ratingResult.setText("avg: " + String.format("%.2f", avg[0]));
        }
    });

    final RatingBar ratingBar4 = (RatingBar) findViewById(R.id.ratingBar4);
        ratingBar4.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
        @Override
        public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

            list[3] = v;
            avg[0] = average(list);
            ratingResult.setText("avg: " + String.format("%.2f", avg[0]));
        }
    });

    final RatingBar ratingBar5 = (RatingBar) findViewById(R.id.ratingBar5);
        ratingBar5.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
        @Override
        public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

            list[4] = v;
            avg[0] = average(list);
            ratingResult.setText("avg: " + String.format("%.2f", avg[0]));
        }
    });

    final RatingBar ratingBar6 = (RatingBar) findViewById(R.id.ratingBar6);
        ratingBar6.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
        @Override
        public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

            list[5] = v;
            avg[0] = average(list);
            ratingResult.setText("avg: " + String.format("%.2f", avg[0]));
        }
    });

    final RatingBar ratingBar7 = (RatingBar) findViewById(R.id.ratingBar7);
        ratingBar7.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
        @Override
        public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

            list[6] = v;
            avg[0] = average(list);
            ratingResult.setText("avg: " + String.format("%.2f", avg[0]));
        }
    });

    final RatingBar ratingBar8 = (RatingBar) findViewById(R.id.ratingBar8);
        ratingBar8.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
        @Override
        public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

            list[7] = v;
            avg[0] = average(list);
            ratingResult.setText("avg: " + String.format("%.2f", avg[0]));
        }
    });

    final RatingBar ratingBar9 = (RatingBar) findViewById(R.id.ratingBar9);
        ratingBar9.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
        @Override
        public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

            list[8] = v;
            avg[0] = average(list);
            ratingResult.setText("avg: " + String.format("%.2f", avg[0]));
        }
    });

        Button surpriseButton = (Button) findViewById(R.id.surpriseButton);
        surpriseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), SurpriseActivity.class);
                startIntent.putExtra("com.example.harrytest.averagerating",avg[0]);
                startActivity(startIntent);
            }
        });




}

    private float average(float[] list)
    {
        float avg = 0;
        for(int i = 0; i < list.length; i++)
        {
            avg = avg + list[i];
        }
        avg = avg/list.length;
        return avg;
    }
}