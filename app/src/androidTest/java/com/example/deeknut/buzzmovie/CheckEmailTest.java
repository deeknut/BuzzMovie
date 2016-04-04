package com.example.deeknut.buzzmovie;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Jay on 4/4/16.
 */
public class CheckEmailTest {
    BMRegisterActivity r;

    @Before
    public void setup() {
        r = new BMRegisterActivity();
    }

    @Test
    public void testCheckEmail() {
        assertTrue(!r.isEmailValid("g@.")); //has both
        assertFalse(r.isEmailValid("Jeremy is bae.")); //no @
        assertFalse(r.isEmailValid("j@google")); //no "."

    }
}
