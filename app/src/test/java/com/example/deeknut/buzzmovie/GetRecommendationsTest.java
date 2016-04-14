package com.example.deeknut.buzzmovie;

import android.os.Bundle;
import android.util.Log;

import com.example.deeknut.buzzmovie.models.Movie;
import com.example.deeknut.buzzmovie.models.Recommendation;
import com.firebase.client.Firebase;

import org.junit.Test;

import java.util.List;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by joe on 4/4/16.
 */
public class GetRecommendationsTest extends BMRecommendationsActivity {

    @Test
    public void testGetAllRecommendations() {
        String email = "jeremy@senpai.com";
        String movieTitle = "Jeremy Senpai's Amazing Adventure";
        String movieDesc = "Watch Jeremy as he takes an adventure through a 2340 group's gross app";
        double rating = 5.0;
        int recNum = 5;
        for (int i = 0; i < recNum; i++) {
            getMemoryModel().addRecommendation(email, new Movie("" + i, movieTitle, movieDesc, rating));
        }
        List<Recommendation> returnRecs = getMemoryModel().getAllRecommendations();
        for (Recommendation rec : returnRecs) {
            assertEquals(rec.getTitle(), movieTitle);
            assertEquals(rec.getDescription(), movieDesc);
            assertEquals(rec.getUserEmail(), email);
            assertEquals(rec.getRating(), rating);
        }
    }
}
