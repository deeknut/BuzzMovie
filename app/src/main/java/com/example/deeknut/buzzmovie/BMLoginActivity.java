package com.example.deeknut.buzzmovie;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.deeknut.buzzmovie.models.User;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class BMLoginActivity extends BMModelActivity implements LoaderCallbacks<Cursor> {

    /**
     * ID to identify if contacts can be read.
     */
    private static final int REQUEST_READ_CONTACTS = 0;
    /**
     * Static variable for currentUser.
     */
    private static String currentUser;
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    /**
     * UI for email entry.
     */
    private AutoCompleteTextView mEmailView;
    /**
     * View outlet for password
     */
    private EditText mPasswordView;
    /**
     * View for progress
     */
    private View mProgressView;
    /**
     * View for LoginForm
     */
    private View mLoginFormView;
    /**
     * Intent for appScreen
     */
    private Intent appScreenIntent;
    /**
     * Static variable for password length
     */
    private static final int PASSWORD_LENGTH = 3;


    /**
     * Gets current user.
     * @return current user
     */
    public static String getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets current user of application.
     * @param currUser of application
     */
    public static void setCurrentUser(String currUser) {
        BMLoginActivity.currentUser = currUser;
    }

    /**
     * {@inheritDoc}
     * Called when login activity instance is started
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appScreenIntent = new Intent(this, BMAppActivity.class);
        setContentView(R.layout.activity_bmlogin);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        final Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        final Button mRegisterButton = (Button) findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRegister();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    /**
     * Jumps to register view
     */
    private void goToRegister() {
        final Intent registerScreenIntent = new Intent(this, BMRegisterActivity.class);
        startActivity(registerScreenIntent);
        finish();
    }

    /**
     * Populates auto complete for email field
     */
    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    /**
     * @return whether activity may request contacts or not
     */
    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * {@inheritDoc}
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS &&
               grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            populateAutoComplete();
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        final String email = mEmailView.getText().toString();
        final String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for an account lock
        final User user = getModel().getUserByEmail(email);
        if (user != null && user.isLocked()) {
            mEmailView.setError(getString(R.string.error_locked_account));
            focusView = mEmailView;
            cancel = true;
        }

        // Check for an account ban
        if (user != null && user.isBanned()) {
            mEmailView.setError(getString(R.string.error_banned_account));
            focusView = mEmailView;
            cancel = true;
        }


        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //showProgress(true);
            currentUser = email;
            mAuthTask = new UserLoginTask(email, password, this, mPasswordView);
            mAuthTask.execute((Void) null);
        }
    }

    /**
     * @param email email string from login form
     * @return whether email is valid
     * Checks if email is valid
     */
    private boolean isEmailValid(String email) {
        return email.contains("@") && email.contains(".") && !email.contains(" ");
    }

    /**
     * @param password password string from login form
     * @return whether password is valid
     * Checks if password is valid
     */
    private boolean isPasswordValid(String password) {
        return password.length() > PASSWORD_LENGTH;
    }

    /**
     * @param show Whether to show progress view or not
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            final int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                    }
                }
            );

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                    }
                }
            );
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * {@inheritDoc}
     * Creates loader for login activity
     */
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.getProjection(),

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE + " = ?",
                    new String[]{
                        ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE
                    },

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    /**
     * {@inheritDoc}
     * Stores email in auto complete after load
     */
    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        final List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.getAddress()));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    /**
     * {@inheritDoc}
     * Called when loader is reset
     */
    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
    }

    /**
     * @param emailAddressCollection collection of email addresses in autocomplete
     * Show email addresses in autocomplete list
    */
    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        final ArrayAdapter<String> adapter =
                new ArrayAdapter<>(BMLoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
}

