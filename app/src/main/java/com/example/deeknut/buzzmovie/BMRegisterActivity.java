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

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A register screen that offers register via email/password.
 */
public class BMRegisterActivity extends BMModelActivity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;
    /**
     * Intent for appscreen
     */
    private Intent appScreenIntent;
    /**
     * static variable for minimum passwordlength
     */
    private static final int PASSWORD_LENGTH = 3;
    /**
     * Keep track of the register task to ensure we can cancel it if requested.
     */
    private UserRegisterTask mAuthTask = null;

    /**
     * TextView for email
     */
    private AutoCompleteTextView mEmailView;
    /**
     * TextView for password
     */
    private EditText mPasswordView;
    /**
     * TextView for confirmation password
     */
    private EditText mConfirmPasswordView;
    /**
     * View for progress
     */
    private View mProgressView;
    /**
     * Container View for register form
     */
    private View mRegisterFormView;

    /**
     * {@inheritDoc}
     * Called when register activity instance is started
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmregister);
        appScreenIntent = new Intent(this, BMAppActivity.class);
        // Set up the register form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.password || id == EditorInfo.IME_NULL) {
                    attemptRegister();
                    return true;
                }
                return false;
            }
        });

        mConfirmPasswordView = (EditText) findViewById(R.id.confirm_password);
        mConfirmPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.confirm_password || id == EditorInfo.IME_NULL) {
                    attemptRegister();
                    return true;
                }
                return false;
            }
        });


        final Button registerButton = (Button) findViewById(R.id.register_button);
        registerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });

        mRegisterFormView = findViewById(R.id.register_form);
        mProgressView = findViewById(R.id.register_progress);
    }

    /**
     * Populates our auto complete for register fields
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
     * Attempts to register the account specified by the form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual register attempt is made.
     */
    private void attemptRegister() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mConfirmPasswordView.setError(null);

        // Store values at the time of the register attempt.
        final String email = mEmailView.getText().toString();
        final String password = mPasswordView.getText().toString();
        final String confirmPassword = mConfirmPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;
        int err = provideEmailError(email);
        if(err != -1 && !getString(err).isEmpty()) {
            mEmailView.setError(getString(err));
            focusView = mEmailView;
            cancel = true;
        }
        err = providePassError(password, confirmPassword);
        if(err != -1 && !getString(err).isEmpty()) {
            mPasswordView.setError(getString(err));
            focusView = mPasswordView;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt register and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user register attempt.

            showProgress(true);
            BMLoginActivity.setCurrentUser(email);
            mAuthTask = new UserRegisterTask(email, password, this, mPasswordView);
            mAuthTask.execute((Void) null);
        }
    }

    /**
     * Provides required errors for email input.
     * @param email for which to find errors
     * @return error ID for particular email, -1 if no error
     */
    public int provideEmailError(String email) {

        // Check for a valid email address.
        if (TextUtils.isEmpty(email) || email.isEmpty()) {
            return R.string.error_field_required;
        } else if (!isEmailValid(email)) {
            return R.string.error_invalid_email;
        } else if (getModel().isUser(email)) {
            return R.string.error_pre_existing_email;
        }
        return -1;
    }

    /**
     * Provides required errors for all password input.
     * @param password input for error
     * @param confirmPassword input for error
     * @return error ID for particular password, -1 if no error
     */
    public int providePassError(String password, String confirmPassword) {

        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            return R.string.error_non_matching_password;
        }

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            return R.string.error_invalid_password;
        }
        return -1;
    }

    /**
     * @param email email string to from form input
     * @return whether email is valid
     * Checks if email is valid
     */
    public boolean isEmailValid(String email) {
        return email.contains("@") && email.contains(".") && !email.contains(" ");
    }

    /**
     * @param password password string from register form
     * @return whether password is valid
     * Checks if password is valid
     */
    public boolean isPasswordValid(String password) {
        return password.length() > PASSWORD_LENGTH;
    }

    /**
     * @param show Whether to show progress view or not
     * Shows the progress UI and hides the register form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            final int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRegisterFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(
                    new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                        }
                    }
            );

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(
                    new AnimatorListenerAdapter() {
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
            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * {@inheritDoc}
     * Creates loader for register activity
     */
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.getProjection(),

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?",
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
                new ArrayAdapter<>(BMRegisterActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }

}

