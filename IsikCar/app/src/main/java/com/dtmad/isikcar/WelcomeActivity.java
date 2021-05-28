package com.dtmad.isikcar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void signUpClickedWelcome(View view){

        Intent intentToSignUp = new Intent(this,SignUpActivity.class);
        startActivity(intentToSignUp);

    }

    public void loginClickedWelcome(View view){

        Intent intentToLogin = new Intent(this,LoginActivity.class);
        startActivity(intentToLogin);
    }
}