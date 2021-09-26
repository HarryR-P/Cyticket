package com.example.carsonexperimentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    private Button goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();
        String username = intent.getStringExtra(MainActivity.EXTRA_USER);
        String password = intent.getStringExtra(MainActivity.EXTRA_PASS);
        Boolean remember = intent.getBooleanExtra(MainActivity.EXTRA_CHECK, false);

        TextView textViewUser = (TextView) findViewById(R.id.textView5);
        TextView textViewPass = (TextView) findViewById(R.id.textView6);
        TextView textViewRem = (TextView) findViewById(R.id.textView7);

        textViewUser.setText(username);
        textViewPass.setText(password);

        if(remember == true) {

            textViewRem.setText("You will be remembered");

        }
        else {

            textViewRem.setText("You won't be remembered");

        }

        goBack = findViewById(R.id.Button2);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openFirstActivity();

            }
        });
    }

    public void openFirstActivity() {

        Intent intent = new Intent(SecondActivity.this, MainActivity.class);
        startActivity(intent);

    }

}