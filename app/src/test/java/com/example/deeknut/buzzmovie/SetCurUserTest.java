package com.example.deeknut.buzzmovie;

import com.firebase.client.Firebase;

import junit.framework.Assert;

import org.junit.Test;

/**
 * JUnit for testing if parseEmail for storage in Firebase works properly.
 * Author: Lucas Della Bella
 */
public class SetCurUserTest extends BMModelActivity {

    @Test
    public void testSetUser() {
        Firebase.setAndroidContext(this);
        getMemoryModel().setCurUser("jeremyisbae@gmail.com", "ggggg"); //new user
        Assert.assertEquals(getMemoryModel().getCurUser().getEmail(), "jeremyisbae@gmail.com");
        getMemoryModel().setCurUser("jeremyisnotbae@gmail.com", "ggggg"); //new user
        Assert.assertEquals(getMemoryModel().getCurUser().getEmail(), "jeremyisnotbae@gmail.com");
        getMemoryModel().setCurUser("jeremyisbae@gmail.com", "ggggg"); //Already existing user
        Assert.assertEquals(getMemoryModel().getCurUser().getEmail(), "jeremyisbae@gmail.com");

    }
}
