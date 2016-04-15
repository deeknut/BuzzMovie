package com.example.deeknut.buzzmovie;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Jayanth Devanathan on 4/4/16.
 */
public class ProvidePasswordErrorsTest extends BMRegisterActivity{

    @Test
    public void testProvidePasswordErrors() {
        //Passwords don't match
        assertEquals(providePassError("pass1", "pass2"), R.string.error_non_matching_password);
        //Passwords don't match BEFORE invalid password, regardless of length
        assertEquals(providePassError("p1", "p2"), R.string.error_non_matching_password);
        assertEquals(providePassError("p1", "pass2"), R.string.error_non_matching_password);
        //Invalid(matching) passwords(less than 4)
        assertEquals(providePassError("p1", "p1"), R.string.error_invalid_password);
        //Valid combination!(equal to 4)
        assertEquals(providePassError("pas1", "pas1"), -1);
        //Valid combination!(greater than 4)
        assertEquals(providePassError("pass1", "pass1"), -1);
    }
}
