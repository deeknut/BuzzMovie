package com.example.deeknut.buzzmovie;

import com.example.deeknut.buzzmovie.models.Recommendation;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Jayanth Devanathan on 4/4/16.
 */
public class GetRecsTest extends BMModelActivity {

    @Test
    public void testGetRecsSort() {
        List<Recommendation> recs = new ArrayList<Recommendation>();
        getMemoryModel().setCurUser("jeremysenpai@gmail.com", "senpai");
        getMemoryModel().setCurUser("first@first.com", "senpai");
        getMemoryModel().setCurUser("second@second.com", "senpai");
        getMemoryModel().setCurUser("third@third.com", "senpai");
        getMemoryModel().setCurUser("fourth@fourth.com", "senpai");

        getMemoryModel().getUserByEmail("first@first.com").setMajor("CS");
        getMemoryModel().getUserByEmail("third@third.com").setMajor("CS");
        getMemoryModel().getUserByEmail("jeremysenpai@gmail.com").setMajor("Memes");
        getMemoryModel().addRecommendation("third@third.com", "3", "Third Movie", "Movie 3", 2.0);
        getMemoryModel().addRecommendation("first@first.com", "1", "First Movie", "Movie 1", 4.0);
        getMemoryModel().addRecommendation("fourth@fourth.com", "4", "Fourth Movie", "Movie 4", 1.5);
        getMemoryModel().addRecommendation("second@second.com", "2", "Second Movie", "Movie 2", 3.5);
        getMemoryModel().addRecommendation("jeremysenpai@gmail.com", "1.5", "Senpai", "Movie 1.5", 3.9);

        recs.add(new Recommendation("first@first.com", "1", "First Movie", "Movie 1", 4.0));
        recs.add(new Recommendation("jeremysenpai@gmail.com", "1.5", "Senpai", "Movie 1.5", 3.9));
        recs.add(new Recommendation("second@second.com", "2", "Second Movie", "Movie 2", 3.5));
        recs.add(new Recommendation("third@third.com", "3", "Third Movie", "Movie 3", 2.0));
        recs.add(new Recommendation("fourth@fourth.com", "4", "Fourth Movie", "Movie 4", 1.5));


        List<Recommendation> recs2 = getMemoryModel().getRecommendationsByMajor("All Majors");
        for(int x = 0; x < recs2.size(); x++) {
            assertEquals(recs.get(x), recs2.get(x));
        }
        /* testing major functionality */
        recs2 = getMemoryModel().getRecommendationsByMajor("Memes");
        assertEquals(recs2.get(0), recs.get(1)); //jeremy senpai is the only entry
        recs2 = getMemoryModel().getRecommendationsByMajor("CS");
        assertEquals(recs2.get(0), recs.get(0)); //first is first entry, third is second entry
        assertEquals(recs2.get(1), recs.get(3));
    }
}
