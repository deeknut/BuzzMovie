package com.example.deeknut.buzzmovie;

import com.example.deeknut.buzzmovie.models.Movie;
import com.example.deeknut.buzzmovie.models.Recommendation;

import org.junit.Test;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by lucas on 4/4/16.
 */
public class GetRecommendationByUserAndMovieTest extends BMModelActivity {

    @Test
    public void getRecommendationByUserAndMovieTest() {
        String userEmail1 = "coolJeremy@Senpai.ugu";
        Movie m1 = new Movie(
                "489",
                "Jeremy Senpai's Amazing Adventure",
                "Watch Jeremy as he takes an adventure through a 2340 group's gross app",
                5.0);
        String userEmail2 = "greatJerry@swag.mf";
        Movie m2 = new Movie(
                "6969",
                "Great Jerry Evades The Cops",
                "Great Jerry decides to evade the cops, and chill at his crib running JUnits tests",
                5.0);

        getMemoryModel().addRecommendation(userEmail1, m1);
        getMemoryModel().addRecommendation(userEmail2, m2);

        Recommendation compRec1 = new Recommendation(userEmail1, m1, 5.0);
        Recommendation compRec2 = new Recommendation(userEmail2, m2, 5.0);

        Recommendation realRec1 = getMemoryModel().getRecommendationByUserAndMovie(userEmail1, m1);
        Recommendation realRec2 = getMemoryModel().getRecommendationByUserAndMovie(userEmail2, m2);

        assertEquals(compRec1.getDescription(), realRec1.getDescription());
        assertEquals(compRec1.getUserEmail(), realRec1.getUserEmail());
        assertEquals(compRec1.getMovieID(), realRec1.getMovieID());
        assertEquals(compRec1.getRating(), realRec1.getRating());
        assertEquals(compRec1.getTitle(), realRec1.getTitle());

        assertEquals(compRec2.getDescription(), realRec2.getDescription());
        assertEquals(compRec2.getUserEmail(), realRec2.getUserEmail());
        assertEquals(compRec2.getMovieID(), realRec2.getMovieID());
        assertEquals(compRec2.getRating(), realRec2.getRating());
        assertEquals(compRec2.getTitle(), realRec2.getTitle());
    }
}
