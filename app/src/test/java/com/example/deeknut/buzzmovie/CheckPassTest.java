package com.example.deeknut.buzzmovie;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class CheckPassTest extends BMRegisterActivity {

    @Test
    public void testCheckPass() {
        assertFalse(isPasswordValid("gg")); //too small
        assertFalse(isPasswordValid("ggg")); //too small
        assertTrue(isPasswordValid("gggg")); //just right
        assertTrue(isPasswordValid("gggggggg")); //greater, should also work

    }
}