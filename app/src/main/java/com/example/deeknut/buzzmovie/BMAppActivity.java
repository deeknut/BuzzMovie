package com.example.deeknut.buzzmovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class BMAppActivity extends AppCompatActivity {

    /**
     * {@inheritDoc}
     * Called when app activity instance is started
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmapp);

        Button logoutButton = (Button) findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogout();
            }
        });

        Button editProfileButton = (Button) findViewById(R.id.button_edit_profile);
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptEditProfile();
            }
        });

        Button searchButton = (Button) findViewById(R.id.button_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptGoToSearchPage();
            }
        });
    }

    /**
     * Attempts to go to edit profile screen
     */
    private void attemptEditProfile() {
        Intent editProfileIntent = new Intent(this, BMProfileActivity.class);
        startActivity(editProfileIntent);
        finish();
    }

    /**
     * Attempts to go to search screen
     */
    private void attemptGoToSearchPage() {
        Intent searchIntent = new Intent(this, BMSearchActivity.class);
        startActivity(searchIntent);
        //finish();
    }

    /**
     * Attempts to logout, going back to the welcome screen
     */
    private void attemptLogout() {
        Intent welcomeScreenIntent = new Intent(this, BMLoginActivity.class);
        startActivity(welcomeScreenIntent);
        finish();
    }
}
