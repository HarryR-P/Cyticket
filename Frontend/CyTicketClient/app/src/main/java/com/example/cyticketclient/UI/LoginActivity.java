package com.example.cyticketclient.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cyticketclient.R;
import com.example.cyticketclient.logic.LoginLogic;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText loginUserName;
    private EditText loginPassword;
    private TextView errorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginUserName = (EditText) findViewById(R.id.loginUserName);
        loginPassword = (EditText) findViewById(R.id.loginPassword);
        errorView = (TextView) findViewById(R.id.loginError);

        Button loginBtn = (Button) findViewById(R.id.loginBtn);
        Button newUserBtn = (Button) findViewById(R.id.newUserBtn);

        loginBtn.setOnClickListener(this);
        newUserBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.loginBtn:
                String username = loginUserName.getText().toString();
                String password = loginPassword.getText().toString();
                LoginLogic logic = new LoginLogic();
                logic.checkUser(username,password,this, errorView);
                loginUserName.setText("");
                loginPassword.setText("");
                break;
            case R.id.newUserBtn:
                Intent goToNewProfileActivity = new Intent(this,NewProfile.class);
                startActivity(goToNewProfileActivity);
                break;
        }

    }
}