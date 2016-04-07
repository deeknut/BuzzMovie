package com.example.deeknut.buzzmovie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BMProfileActivity extends BMModelActivity {

    /**
     * EditText View for user's interests
     */
    private EditText mInterests;
    /**
     * EditText View for users's major
     */
    private EditText mMajor;
    /**
     * {@inheritDoc}
     * Called when login activity instance is started
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView mUsername;
        TextView mPassword;
        Button   mSave;

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
        mUsername.setText(getModel().getCurUser().getEmail());
        mPassword.setText(getModel().getCurUser().getPass());

        mMajor.setText(getModel().getCurUser().getMajor());
        mInterests.setText(getModel().getCurUser().getInterests());
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserInfo();
                returnToAppActivity();
            }
        });
    }
    /**
    Saves user information.
     */
    private void saveUserInfo() {
        //Add User's Info
        getModel().getCurUser().setMajor(mMajor.getText().toString());
        getModel().getCurUser().setInterests(mInterests.getText().toString());

    }
    /**
    Returns to original application.
     */
    private void returnToAppActivity() {
        final Intent appScreenIntent = new Intent(this, BMAppActivity.class);
        startActivity(appScreenIntent);
        finish();
    }
}
