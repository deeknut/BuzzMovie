package com.example.deeknut.buzzmovie.models;

/**
 * Singleton class that serves as an interfacer for all stored data.
 */

import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MemoryModel implements Model {

    /** The collection of users, movies, and recs, keyed by name.
     * Currently our structurers.*/
    private Map<String, User> users;
    private Map<String, Movie> movies;
    private Map<String, Recommendation> recommendations;
    /**
    Current user and singleton object for MemoryModel.
     */
    private User currUser;
    private static Model singleton;
    /**
     * Makes a new model
     */
    private MemoryModel() {
        users = new HashMap<>();
        movies = new HashMap<>();
        recommendations =  new HashMap<>();
    }

    @Override
     public boolean checkUser(final String email, final String password) {
        User s = users.get(email);
        return s != null && password.equals(s.getPass());
    }

    @Override
    public boolean isUser(final String email) {
        User s = users.get(email);
        return s != null;
    }

    public ArrayList<User> listUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User getUserByEmail(final String email) {
        return users.get(email);
    }
    @Override
    public Movie getMovieById(final String id) {
        return movies.get(id);
    }
    @Override
    public boolean hasMovie(final String id) {
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
    public void addRecommendation(String userEmail, Movie movie) {
        recommendations.put(userEmail + ":" + movie.getMovieID(),
                new Recommendation(userEmail, movie.getMovieID(), movie.getTitle(),
                        movie.getDescription(), movie.getRating()));
    }

    @Override
    public Recommendation getRecommendationByUserAndMovie(User user, Movie movie) {
        return recommendations.get(user.getEmail() + ":" + movie.getMovieID());
    }

    @Override
    public Recommendation getRecommendationByUserAndMovie(String userEmail, Movie movie) {
        return recommendations.get(userEmail + ":" + movie.getMovieID());
    }

    @Override
    public User getCurUser() {
        return currUser;
    }

    @Override
    public void setCurUser(String email, String pass) {
        if(isUser(email)) {
            currUser = users.get(email);
        } else {
            currUser = new User(email, pass);
            users.put(email, currUser);
        }
    }

    public static Model getInstance() {
        if(singleton == null) {
            singleton = new MemoryModel();
        }
        return singleton;
    }

}
