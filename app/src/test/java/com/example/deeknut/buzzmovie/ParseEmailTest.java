package com.example.deeknut.buzzmovie;

import com.example.deeknut.buzzmovie.models.User;

import junit.framework.Assert;

import org.junit.Test;

/**
 * JUnit for testing if parseEmail for storage in Firebase works properly.
 * Author: Jay Devanathan
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
