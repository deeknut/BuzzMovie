package com.example.deeknut.buzzmovie;

import junit.framework.Assert;

import org.junit.Test;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 * Author: Brian Wang
 */
public class CheckPassTest extends BMRegisterActivity {

    @Test
    public void testCheckPass() {
        Assert.assertFalse(isPasswordValid("gg")); //too small
        Assert.assertFalse(isPasswordValid("ggg")); //too small
        Assert.assertTrue(isPasswordValid("gggg")); //just right
        Assert.assertTrue(isPasswordValid("gggggggg")); //greater, should also work

    }
}