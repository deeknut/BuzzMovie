package com.example.deeknut.buzzmovie;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

import com.example.deeknut.buzzmovie.models.User;

public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

    /**
     *
     */
    private final String mEmail;
    /**
     *
     */
    private final String mPassword;
    /**
     *
     */
    private ProgressDialog progressDialog;
    /**
     *
     */
    private BMLoginActivity login;
    /**
     *
     */
    private final EditText mPasswordView;

    /**
     * Creates a user login task
     * @param email of user
     * @param password of user
     * @param act activity for login of user
     * @param passwordView view for password of user
     */
    UserLoginTask(String email, String password,
                  BMLoginActivity act, EditText passwordView) {
        mEmail = email;
        mPassword = password;
        login = act;
        mPasswordView = passwordView;
    }

    /**
     * {@inheritDoc}
     * Runs check in background of task
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
        return login.getModel().checkUser(mEmail, mPassword);
    }

    /**
     * {@inheritDoc}
     * Goes to application view or shows error dialog based on credentials
     */
    @Override
    protected void onPostExecute(final Boolean success) {
        login.showProgress(false);

        if (success && progressDialog.isShowing()) {
            Log.d("Process", "Process finished.");
            login.getModel().setCurUser(mEmail, mPassword);
            login.getModel().getUserByEmail(mEmail).restoreLoginAttempts();
            login.startActivity(new Intent(login, BMAppActivity.class));
            login.finish();
        } else {
            if(!success) {
                final User u = login.getModel().getUserByEmail(mEmail);
                if (u != null) { u.newBadLoginAttempt(); }
                mPasswordView.setError(login.getString(R.string.error_incorrect_password));
            }
            mPasswordView.requestFocus();
            progressDialog.dismiss();
        }
    }

    /**
     * {@inheritDoc}
     * Show progress dialog before task execution
     */
    @Override
    protected void onPreExecute(){
        progressDialog = new ProgressDialog(login);
        progressDialog.setMessage("Logging in...");

        progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener(){
            // Set a click listener for progress dialog cancel button
            @Override
            public void onClick(DialogInterface dialog, int which){
                // dismiss the progress dialog
                dialog.dismiss();
            }
        });
        progressDialog.show();
    }

    /**
     * {@inheritDoc}
     * Cancel task when cancel button clicked
     */
    @Override
    protected void onCancelled() {
        login.showProgress(false);
    }
}

