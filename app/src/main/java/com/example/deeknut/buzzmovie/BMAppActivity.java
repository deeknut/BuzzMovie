package com.example.deeknut.buzzmovie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.deeknut.buzzmovie.models.Model;
import com.example.deeknut.buzzmovie.models.User;
import com.firebase.client.Firebase;

public class BMAppActivity extends BMModelActivity {

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

        Button recButton = (Button) findViewById(R.id.button_rec);
        recButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptGoToRecPage();
            }
        });

        Model m = getModel();
        User u  = m.getCurUser();
        if (u.isAdmin()) {
            RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relative_layout);

            Button manageUsersBt = new Button(this);
            manageUsersBt.setText("Manage Users");
            //manageUsersBt.setLayoutParams(new RelativeLayout.LayoutParams(?));
            manageUsersBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    attemptGoToManageUsers();
                }
            });
            relativeLayout.addView(manageUsersBt);
        }
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

    /**
     * Attempts to go to recommendation page
     */
    private void attemptGoToRecPage() {
        Intent recActivityIntent = new Intent(this, BMRecommendationsActivity.class);
        startActivity(recActivityIntent);
    }

    /**
     * Attempts to go to the Manage Users page
     */
    private void attemptGoToManageUsers() {
        Intent manageUsersIntent = new Intent(this, BMManageUsersActivity.class);
        startActivity(manageUsersIntent);
    }
}
