package com.example.deeknut.buzzmovie;

import junit.framework.Assert;

import org.junit.Test;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class CheckEmailTest extends BMRegisterActivity {

    @Test
    public void testCheckEmail() {
        Assert.assertTrue(isEmailValid("g@.")); //has both
        Assert.assertFalse(isEmailValid("Jeremy is bae.")); //no @
        Assert.assertFalse(isEmailValid("j@google")); //no "."

    }
}