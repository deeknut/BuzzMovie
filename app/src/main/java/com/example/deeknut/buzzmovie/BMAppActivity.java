package com.example.deeknut.buzzmovie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BMAppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
    }

    private void attemptLogout() {
        Intent welcomeScreenIntent = new Intent(this, BMWelcomeActivity.class);
        startActivity(welcomeScreenIntent);
    }
}