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
    public User(String email, String pass) {
        this.email = email;
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }
}
