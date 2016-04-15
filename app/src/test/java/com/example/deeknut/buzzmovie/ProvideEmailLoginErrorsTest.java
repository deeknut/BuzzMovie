package com.example.deeknut.buzzmovie;

import com.example.deeknut.buzzmovie.models.User;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Jayanth Devanathan on 4/4/16.
 */
public class ProvideEmailLoginErrorsTest extends BMLoginActivity {

    @Test
    public void testProvideEmailLoginErrors() {
        //invalid email format
        assertEquals(provideEmailLoginError("we love you jeremy senpai."), R.string.error_invalid_email);
        assertEquals(provideEmailLoginError("jeremysenpai@senpai"), R.string.error_invalid_email);
        //need to enter in an actual email
        assertEquals(provideEmailLoginError(""), R.string.error_field_required);
        //Email is valid!
        assertEquals(provideEmailLoginError("dopetests@google.com"), -1);

        getMemoryModel().setCurUser("jeremysenpaiBanned@google.com", "senpai");
        User jeremy = getMemoryModel().getUserByEmail("jeremysenpaiBanned@google.com");
        jeremy.setIsBanned(true);
        assertEquals(provideEmailLoginError("jeremysenpaiBanned@google.com"), R.string.error_banned_account);
        jeremy.setIsBanned(false);
        for(int i = 0; i < 4; i++) {
           jeremy.newBadLoginAttempt();
        }
        assertEquals(provideEmailLoginError("jeremysenpaiBanned@google.com"), R.string.error_locked_account);

    }
}
