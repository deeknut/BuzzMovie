package com.example.deeknut.buzzmovie;

import android.os.Bundle;

import com.firebase.client.Firebase;

import org.junit.Test;

import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by joe on 4/4/16.
 */
public class AddMovieTest extends BMModelActivity {

    @Test
    public void testAddMovie() {
        String movieId = "489";
        String movieTitle = "Jeremy Senpai's Amazing Adventure";
        String movieDesc = "Watch Jeremy as he takes an adventure through a 2340 group's gross app";
        double rating = 5.0;
        getMemoryModel().addMovie(movieId, movieTitle, movieDesc, rating);
        assertTrue(getMemoryModel().hasMovie(movieId));
        assertEquals(getMemoryModel().getMovieById(movieId).getTitle(), movieTitle);
        assertEquals(getMemoryModel().getMovieById(movieId).getDescription(), movieDesc);
        assertEquals(getMemoryModel().getMovieById(movieId).getRating(), rating);
    }
}
