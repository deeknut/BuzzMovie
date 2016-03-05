package com.example.deeknut.buzzmovie.models;

/**
 * Created by Jay on 3/4/16.
 */

import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Singleton class that serves as an interfacer for all stored data.
 */
public class MemoryModel implements Model {
    private final String TAG = "MemoryModel";

    /** the collection of users, movies, and recs, keyed by name */
    private Map<String, User> users;
    private Map<String, Movie> movies;
    private Map<String, Recommendation> recommendations;
    private User currUser;
    private static Model singleton;
    /**
     * Makes a new model
     */
    public MemoryModel() {
        users = new HashMap<>();
        movies = new HashMap<>();
        recommendations =  new HashMap<>();
    }

    @Override
     public boolean checkUser(final String email, final String password) {
        //TODO: Replace with DB Query
        User s = users.get(email);
        if (s == null) return false;
        if (!password.equals(s.getPass())) return false;
        return true;
    }

    @Override
    public boolean isUser(final String email) {
        //TODO: Replace with DB Query
        User s = users.get(email);
        if (s == null) return false;
        return true;
    }

    @Override
    public User getUserByEmail(final String email) {
        //TODO: Replace with DB Query
        return users.get(email);
    }
    @Override
    public void registerUser(String email, String pass) {
        //TODO: Replace with DB Query
        users.put(email, new User(email, pass));
    }
    @Override
    public Movie getMovieById(final String id) {
        //TODO: Replace with DB Query
        return movies.get(id);
    }
    @Override
    public boolean hasMovie(final String id) {
        //TODO: Replace with DB Query
        return movies.get(id) != null;
    }

    @Override
    public List<Recommendation> getRecommendationsByMajor(String major) {
        List<Recommendation> list = new ArrayList<>();
        for(Recommendation rec : recommendations.values()) {
            Log.d("Email", rec.getUserEmail());
            if (major.equals("All Majors") ||
                    major.equals(getUserByEmail(rec.getUserEmail()).getMajor())) {
                list.add(rec);
            }
        }
        Collections.sort(list, new Comparator<Recommendation>() {
            @Override
            public int compare(Recommendation r1, Recommendation r2) {
                return (r1.getRating() - r2.getRating()) < 0 ? 1 : 0;
            }
        });
        return list;
    }

    @Override
    public void addMovie(final String id, final String title, final String description, double rating) {
        movies.put(id, new Movie(id, title, description, rating));
    }

    @Override
    public void addRecommendation(final String userEmail, final String movieID, final String movieTitle,
                                   final String description, double rating) {
        recommendations.put(userEmail + ":" + movieID, new Recommendation(userEmail, movieID, movieTitle, description, rating));
    }

    @Override
    public Recommendation getRecommendationByUserAndMovie(User user, Movie movie) {
        //TODO: Replace with DB Query
        return recommendations.get(user.getEmail() + ":" + movie.getMovieID());
    }


    @Override
    public Collection<User> getUsers() {
        return users.values();
    }

    @Override
    public User getCurrUser() {
        return currUser;
    }

    @Override
    public void setCurrUser(String email, String pass) {

        currUser = new User(email, pass);
        users.put(email, currUser);
    }

    public static Model getInstance() {
        if(singleton == null) {
            singleton = new MemoryModel();
        }
        return singleton;
    }

}
