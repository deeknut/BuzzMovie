package com.example.deeknut.buzzmovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

public class BMProfileActivity extends AppCompatActivity {

    private TextView mUsername;
    private TextView mPassword;
    private EditText mInterests;
    private EditText mMajor;
    private Button   mSave;
    private String curUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmprofile);

        //TODO Put the toolbar and fab back!?
        /*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        //GET REFERENCES TO ALL BOXES IN PROFILE ACTIVITY
        mUsername = (TextView) findViewById(R.id.profile_username_disp);
        mPassword = (TextView) findViewById(R.id.profile_password_disp);
        mInterests = (EditText) findViewById(R.id.profile_interests_inp);
        mMajor = (EditText) findViewById(R.id.profile_major_inp);
        mSave  = (Button) findViewById(R.id.profile_save);

        //SET CONTENTS OF BOXES IN PROFILE ACTIVITY
        curUser = BMLoginActivity.currentUser;
        mUsername.setText(curUser);
        mPassword.setText(BMRegisterActivity.userToPassMap.get(curUser));

        if (!BMRegisterActivity.userInfoMap.containsKey(curUser)) {
            HashMap<String, String> newUserInfo = new HashMap<>();
            newUserInfo.put("Major", "");
            newUserInfo.put("Interests", "");
            BMRegisterActivity.userInfoMap.put(curUser, newUserInfo);
        }

        mMajor.setText(BMRegisterActivity.userInfoMap.get(curUser).get("Major"));
        mInterests.setText(BMRegisterActivity.userInfoMap.get(curUser).get("Interests"));
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserInfo();
                returnToAppActivity();
            }
        });
    }
    /*
    Saves user information.
     */
    private void saveUserInfo() {
        //Add User's Info To Map
        BMRegisterActivity.userInfoMap.get(curUser)
                .put("Major", mMajor.getText().toString());
        BMRegisterActivity.userInfoMap.get(curUser)
                .put("Interests", mInterests.getText().toString());

    }
    /*
    Returns to original application.
     */
    private void returnToAppActivity() {
        Intent appScreenIntent = new Intent(this, BMAppActivity.class);
        startActivity(appScreenIntent);
        finish();
    }
}
