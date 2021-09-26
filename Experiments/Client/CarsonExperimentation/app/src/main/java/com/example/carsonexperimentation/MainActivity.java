package com.example.carsonexperimentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_USER = "com.example.carsonexperimentation.EXTRA_USER";
    public static final String EXTRA_PASS = "com.example.carsonexperimentation.EXTRA_PASS";
    public static final String EXTRA_CHECK = "com.example.carsonexperimentation.EXTRA_CHECK";

    //test alteration

    private Button changeActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        changeActivity = findViewById(R.id.Button1);
        changeActivity.setOnClickListener(new View.OnClickListener() { 
            @Override
            public void onClick(View view) {

                openSecondActivity();

            }
        });

    }

    private void openSecondActivity() {

        EditText name = (EditText) findViewById(R.id.PersonName);
        String username = name.getText().toString();

        EditText pass = (EditText) findViewById(R.id.Password);
        String passphrase = pass.getText().toString();

        CheckBox remember = (CheckBox) findViewById(R.id.checkBox);
        Boolean remembered = remember.isChecked();

        Intent intent = new Intent(MainActivity.this, SecondActivity.class);

        intent.putExtra(EXTRA_USER, username);
        intent.putExtra(EXTRA_PASS, passphrase);
        intent.putExtra(EXTRA_CHECK, remembered);

        startActivity(intent);

    }

}