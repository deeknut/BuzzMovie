package com.example.deeknut.buzzmovie.models;

import android.util.Log;

import com.firebase.client.Firebase;

import java.io.Serializable;

/**
 * Created by Jay on 2/29/16.
 */
public class User implements Serializable {
    private String email;
    private String pass;
    private String major;
    private String interests;
    private Boolean isBanned;
    private Boolean isLocked;
    private int badLoginAttempts;
    private final Boolean isAdmin;
    //private static Firebase firebase;
    //private static final String baseUrl = "https://shining-heat-1721.firebaseio.com";

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
        this.isLocked = false;
        this.isBanned = false;
        //TODO change when we have a real admin account
        this.isAdmin = email.equals("@dmin.");
        //firebase = new Firebase(baseUrl);
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
        //firebase.child("users").child(email.replace("@", "").replace(".", "")).child("interests").setValue(interests);
        ((MemoryModel) MemoryModel.getInstance(null)).writeUsers();
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
        //firebase.child("users").child(email.replace("@", "").replace(".", "")).child("major").setValue(major);
        Log.d("my major", ((MemoryModel) MemoryModel.getInstance(null)).users.get("j@.").getMajor());
        ((MemoryModel) MemoryModel.getInstance(null)).writeUsers();
    }
    /**
     Checks whether the user is an admin.
     */
    public Boolean isLocked() {
        return isLocked;
    }
    /**
     * unlocks user account after bad login attempts
     */
    public void unlock() {
        badLoginAttempts = 0;
        isLocked = false;
        //firebase.child("users").child("isLocked").setValue(false);
        //firebase.child("users").child("badLoginAttempts").setValue(0);
        ((MemoryModel) MemoryModel.getInstance(null)).writeUsers();
    }
    /**
     * Reset bad login attempts
     */
    public void restoreLoginAttempts() {

        badLoginAttempts = 0;
        //firebase.child("users").child("badLoginAttempts").setValue(0);
        ((MemoryModel) MemoryModel.getInstance(null)).writeUsers();
    }
    /**
     * Count a new bad login attempt
     */
    public void newBadLoginAttempt() {
        //TODO badLoginAttempts not changing
        badLoginAttempts += 1;
        isLocked = badLoginAttempts > 3;
        //firebase.child("users").child("badLoginAttempts").setValue(badLoginAttempts);
        //firebase.child("users").child("isLocked").setValue(isLocked);
        ((MemoryModel) MemoryModel.getInstance(null)).writeUsers();
    }

    /**
     * Checks whether a user is banned. Admins cannot be banned.
     * @return whether user is banned
     */
    public Boolean isBanned() {
        return isBanned && !isAdmin();
    }
    /**
     Sets unlocks user account
     */
    public void setIsBanned(Boolean b) {

        isBanned = b;
        //firebase.child("users").child("isBanned").setValue(isBanned);
        ((MemoryModel) MemoryModel.getInstance(null)).writeUsers();
    }
    /**
     Checks whether the user is an admin.
     */
    public Boolean isAdmin() { return isAdmin; }

    public String toString() {
        String toStr = getEmail() + " is";

        if (isBanned()) { toStr += " BANNED"; }
        if (isLocked()) { toStr += " LOCKED"; }
        if (!isBanned() && !isLocked()) { toStr += " CLEAN"; }
        return toStr;
    }
}
