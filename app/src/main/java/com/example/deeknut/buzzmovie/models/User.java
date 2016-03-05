package com.example.deeknut.buzzmovie.models;

/**
 * Created by Jay on 2/29/16.
 */
public class User {
    private String email;
    private String pass;
    private String firstName;
    private String lastName;
    private String major;
    private String interests;
    /* Constructor for user.
    @param email for user
    @param password for user
     */
    public User(String email, String pass) {
        this.email = email;
        this.pass = pass;
    }
    /*
    Returns user email.
    @return user email
     */
    public String getEmail() {
        return email;
    }
    /*
    Returns user password.
    @return user password
    */
    public String getPass() {
        return pass;
    }
    /*
    sets user password.
    @param new password to set for user.
     */
    public void setPass(String pass) {
        this.pass = pass;
    }
    /*
    Returns user interests.
    @return user interests
     */
    public String getInterests() {
        return interests;
    }
    /*
    Sets user interests.
    @param new interests for user
     */
    public void setInterests(String interests) {
        this.interests = interests;
    }
    /*
    Gets major of user.
    @return major of user
     */
    public String getMajor() {
        return major;
    }
    /*
    Sets major of user.
    @param new major for user.
     */
    public void setMajor(String major) {
        this.major = major;
    }
}
