package com.example.deeknut.buzzmovie;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Jayanth Devanathan on 4/4/16.
 */
public class ProvideEmailErrorsTest extends BMRegisterActivity{

    @Test
    public void testProvideEmailErrors() {
        getMemoryModel().setCurUser("jeremysenpai@google.com", "senpai");
        //email already exists, can't register again
        assertEquals(provideEmailError("jeremysenpai@google.com"), R.string.error_pre_existing_email);
        //invalid email format
        assertEquals(provideEmailError("we love you jeremy senpai."), R.string.error_invalid_email);
        assertEquals(provideEmailError("jeremysenpai@senpai"), R.string.error_invalid_email);
        //need to enter in an actual email
        assertEquals(provideEmailError(""), R.string.error_field_required);
        //Email is valid!
        assertEquals(provideEmailError("dopetests@google.com"), -1);

    }
}
