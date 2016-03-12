package com.example.deeknut.buzzmovie.models;

import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Singleton class that provides database functionality and should serve as the ultimate
 * interfacer for the application.
 * TODO: Use DatabaseModel instead of MemoryModel in BMModelActivity
 */
public class DatabaseModel implements Model {

    /** The collection of users, movies, and recs, keyed by name.
     * Should be removed later once the database functionality is added.
     */
    private Map<String, User> users;
    private Map<String, Movie> movies;
    private Map<String, Recommendation> recommendations;
    /**
     The below fields will permanently stay as part of the DatabaseModel.
     Once we switch to database, these will NOT get replaced.
     */
    private User currUser;
    private static Model singleton;

    /**
     * Makes a new model
     */
    private DatabaseModel() {
        //TODO: Change to Database connection calls/etc
        users = new HashMap<>();
        movies = new HashMap<>();
        recommendations =  new HashMap<>();
    }

    @Override
    public boolean checkUser(final String email, final String password) {
        //TODO: Replace with DB Query
        User s = users.get(email);
        return s != null && password.equals(s.getPass());
    }

    @Override
    public boolean isUser(final String email) {
        //TODO: Replace with DB Query
        User s = users.get(email);
        return s != null;
    }

    @Override
    public ArrayList<User> listUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User getUserByEmail(final String email) {
        //TODO: Replace with DB Query
        return users.get(email);
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
        //TODO: Replace with DB Query
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
        //TODO: Replace with DB Query
        movies.put(id, new Movie(id, title, description, rating));
    }

    @Override
    public void addRecommendation(final String userEmail, final String movieID, final String movieTitle,
                                  final String description, double rating) {
        //TODO: Replace with DB Query
        recommendations.put(userEmail + ":" + movieID, new Recommendation(userEmail, movieID, movieTitle, description, rating));
    }

    @Override
    public Recommendation getRecommendationByUserAndMovie(User user, Movie movie) {
        //TODO: Replace with DB Query
        return recommendations.get(user.getEmail() + ":" + movie.getMovieID());
    }

    @Override
    public User getCurUser() {
        //TODO: Replace with DB Query
        return currUser;
    }

    @Override
    public void setCurUser(String email, String pass) {
        //TODO: Replace with DB Query
        if(isUser(email)) {
            currUser = users.get(email);
        } else {
            currUser = new User(email, pass);
            users.put(email, currUser);
        }
    }

    public static Model getInstance() {
        if(singleton == null) {
            singleton = new DatabaseModel();
        }
        return singleton;
    }
}
