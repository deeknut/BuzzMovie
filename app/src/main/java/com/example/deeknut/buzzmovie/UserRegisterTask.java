package com.example.deeknut.buzzmovie;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

/**
 * Represents an asynchronous registration task used to authenticate
 * the user.
 */
public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

    /**
     * Field that holds email
     */
    private final String mEmail;
    /**
     * Field that holds password
     */
    private final String mPassword;
    /**
     * Activity for register
     */
    private final BMRegisterActivity register;
    /**
     * Editable password view
     */
    private final EditText mPasswordView;
    /**
     * Creates a new Task to register a user.
     * @param email of user
     * @param password of user
     * @param reg of user
     * @param passView of user
     */
    public UserRegisterTask(String email, String password, BMRegisterActivity reg, EditText passView) {
        mEmail = email;
        mPassword = password;
        register = reg;
        mPasswordView = passView;
    }

    /**
     * {@inheritDoc}
     * Runs in background during registration task
     */
    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            // Simulate network access.
            final int time = 2000;
            Thread.sleep(time);
        } catch (InterruptedException e) {
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     * Go to application screen or show error after finished task
     */
    @Override
    protected void onPostExecute(final Boolean success) {
        register.showProgress(false);

        if (success) {
            Log.d("Process", "Process finished.");
            register.getModel().setCurUser(mEmail, mPassword);
            register.startActivity(new Intent(register, BMAppActivity.class));
            register.finish();
        } else {
            mPasswordView.setError(register.getString(R.string.error_incorrect_password));
            mPasswordView.requestFocus();
        }
    }

    /**
     * {@inheritDoc}
     * Handle cancelled register while loading
     */
    @Override
    protected void onCancelled() {
        register.showProgress(false);
    }
}
