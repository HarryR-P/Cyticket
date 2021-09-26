package com.example.harrytest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class SurpriseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surprise);


        if(getIntent().hasExtra("com.example.harrytest.averagerating"))
        {
            TextView textView = (TextView) findViewById(R.id.textView2);
            float text = getIntent().getExtras().getFloat("com.example.harrytest.averagerating");
            textView.setText("avg: " + String.format("%.2f", text));
        }

        //Rickroll Btn
        Button superBtn = (Button) findViewById(R.id.superSupriseBtn);
        superBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rick = "https://youtu.be/dQw4w9WgXcQ";
                Uri webaddress = Uri.parse(rick);

                Intent goToRick = new Intent(Intent.ACTION_VIEW, webaddress);
                startActivity(goToRick);

            }
        });
    }
}