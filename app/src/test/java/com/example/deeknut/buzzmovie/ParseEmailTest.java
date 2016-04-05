package com.example.deeknut.buzzmovie;

import com.example.deeknut.buzzmovie.models.User;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Created by Jay on 4/4/16.
 */
public class ParseEmailTest extends User{

    @Test
    public void testParseEmail() {
        Assert.assertEquals(parseEmail("g@."), "g"); //smallest example
        Assert.assertEquals(parseEmail("Jeremy is bae."), "Jeremy is bae"); //just period
        Assert.assertEquals(parseEmail("J@J"), "JJ"); //just @
        Assert.assertEquals(parseEmail("jeremy@gmail.com"), "jeremygmailcom");//typical example
    }
}
