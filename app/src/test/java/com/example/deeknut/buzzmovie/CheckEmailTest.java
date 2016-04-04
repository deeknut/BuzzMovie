package com.example.deeknut.buzzmovie;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class CheckEmailTest extends BMRegisterActivity {

    @Test
    public void testCheckEmail() {
        assertTrue(isEmailValid("g@.")); //has both
        assertFalse(isEmailValid("Jeremy is bae.")); //no @
        assertFalse(isEmailValid("j@google")); //no "."

    }
}