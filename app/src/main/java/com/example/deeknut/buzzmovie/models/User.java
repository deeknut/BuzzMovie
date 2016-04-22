package com.example.deeknut.buzzmovie.models;

import android.util.Log;

import com.firebase.client.Firebase;

import java.io.Serializable;

/**
 * Created by Jay on 2/29/16.
 */
public class User implements Serializable {
    /**
     * Field that holds email
     */
    private String email;
    /**
     * String variable for users table
     */
    private static final String TABLE = "users";
    /**
     * Field that holds pass
     */
    private String pass;
    /**
     * Field that holds major
     */
    private String major;
    /**
     * Field that stores interests
     */
    private String interests;
    /**
     * Represents whether user is banned or not
     */
    private Boolean banned;
    /**
     * Represents whether user's account is locked
     */
    private Boolean locked;
    /**
     * Number of incorrect login attempts
     */
    private int badLoginAttempts;
    /**
     * Represents whether the user is an admin or not
     */
    private final Boolean admin;
    /**
     * Represents the firebase
     */
    private static Firebase firebase;
    /**
     * Field that stores the base URL
     */
    private static final String BASEURL = "https://deeknut.firebaseio.com";
    /**
     * This is swiggums
     */
    private static final int SWIGGUMS = 3;
    /**
     * boolean value that indicates whether to use firebase
     */
    private static boolean isLit = true;

    /**
     * Default constructor for user. Only used for unit testing.
     */
    protected User() {
        admin = false;
    }

    /** Constructor for user.
    @param email for user
    @param pass password for user
     */
    public User(String email, String pass) {
        this.email = email;
        this.pass = pass;
        this.major = "";
        this.interests = "";
        this.badLoginAttempts = 0;
        this.locked = false;
        this.banned = false;
        //TODO change when we have a real admin account
        this.admin = "@dmin.".equals(email);
        firebase = new Firebase(BASEURL);
    }

    /** Constructor for user. Used for MemoryModel so firebase doesn't get instantiated
     @param email for user
     @param pass password for user
     @param isLit if we using firebase
     */
    public User(String email, String pass, boolean isLit) {
        this.email = email;
        this.pass = pass;
        this.major = "";
        this.interests = "";
        this.badLoginAttempts = 0;
        this.locked = false;
        this.banned = false;
        //TODO change when we have a real admin account
        this.admin = "@dmin.".equals(email);
        this.isLit = isLit;
        if(isLit) {
            firebase = new Firebase(BASEURL);
        }
    }

    /**
     * Constructor for user
     * @param email of user
     * @param pass password of user
     * @param interests of user for bio
     * @param major of user for bio and filtering
     * @param banned status of user set by admin
     */
    public User(String email, String pass, String interests, String major, boolean banned) {
        this.email = email;
        this.pass = pass;
        this.major = major;
        this.interests = interests;
        this.badLoginAttempts = 0;
        this.locked = false;
        this.banned = banned;
        //TODO change when we have a real admin account
        this.admin = "@dmin.".equals(email);
        firebase = new Firebase(BASEURL);
    }

    /**
     * Parses user email to work with firebase
     * @param email to parse
     * @return email without @ or .
     */
    public static String parseEmail(String email) {
        return email.replace("@", "").replace(".", "");
    }
    /**
    Returns user email.
    @return user email
     */
    public String getEmail() {
        return email;
    }
    /**
    Returns user password.
    @return user password
    */
    public String getPass() {
        return pass;
    }
    /**
    sets user password.
    @param pass to set for user.
     */
    public void setPass(String pass) {
        this.pass = pass;
    }
    /**
    Returns user interests.
    @return user interests
     */
    public String getInterests() {
        return interests;
    }
    /**
    Sets user interests.
    @param interests for user
     */
    public void setInterests(String interests) {
        this.interests = interests;
        Log.d("SETTING INTERESTS", "DOPLSKDJFL");
        if(isLit) {
            firebase.child(TABLE).child(email.replace("@", "").replace(".", "")).child("interests").setValue(interests);
        }
    }
    /**
    Gets major of user.
    @return major of user
     */
    public String getMajor() {
        return major;
    }
    /**
    Sets major of user.
    @param major for user.
     */
    public void setMajor(String major) {
        this.major = major;
        if(isLit) {
            firebase.child(TABLE).child(email.replace("@", "").replace(".", "")).child("major").setValue(major);
        }
    }
    /**
     Checks whether the user is an admin.
     @return whether current user is locked.
     */
    public Boolean isLocked() {
        return locked;
    }
    /**
     * unlocks user account after bad login attempts
     */
    public void unlock() {
        badLoginAttempts = 0;
        locked = false;
        if(isLit) {
            firebase.child(TABLE).child(parseEmail(email)).child("isLocked").setValue(false);
            firebase.child(TABLE).child(parseEmail(email)).child("badLoginAttempts").setValue(0);
        }
    }
    /**
     * Reset bad login attempts
     */
    public void restoreLoginAttempts() {

        badLoginAttempts = 0;
        if(isLit) {
            firebase.child(TABLE).child(parseEmail(email)).child("badLoginAttempts").setValue(0);
        }
    }
    /**
     * Count a new bad login attempt
     */
    public void newBadLoginAttempt() {
        //TODO badLoginAttempts not changing
        badLoginAttempts += 1;
        locked = badLoginAttempts > SWIGGUMS;
        if(isLit) {
            firebase.child(TABLE).child(parseEmail(email)).child("isLocked").setValue(locked);
            firebase.child(TABLE).child(parseEmail(email)).child("badLoginAttempts").setValue(badLoginAttempts);
        }
    }

    /**
     * Checks whether a user is banned. Admins cannot be banned.
     * @return whether user is banned
     */
    public Boolean isBanned() {
        return banned && !isAdmin();
    }
    /**
     Sets unlocks user account
     @param b whether User is banned or not.
     */
    public void setIsBanned(Boolean b) {

        banned = b;
        if(isLit) {
            firebase.child(TABLE).child(parseEmail(email)).child("isBanned").setValue(banned);
        }
    }
    /**
     Checks whether the user is an admin.
     @return whether user is admin
     */
    public Boolean isAdmin() { return admin; }

    /**
     * Returns a string representation of user.
     * @return string representation of user.
     */
    public String toString() {
        String toStr = getEmail() + " is";

        if (isBanned()) { toStr += " BANNED"; }
        if (isLocked()) { toStr += " LOCKED"; }
        if (!isBanned() && !isLocked()) { toStr += " CLEAN"; }
        return toStr;
    }
}
