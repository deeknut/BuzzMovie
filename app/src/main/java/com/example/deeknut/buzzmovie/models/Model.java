package com.example.deeknut.buzzmovie.models;

/**
 * Created by Jay on 3/4/16.
 */

import java.util.Collection;
import java.util.List;

/**
 * Interface that all modeling interfacers must implement.
 */
public interface Model {
    boolean checkUser(final String email, final String password);
    boolean isUser(final String email);
    User getUserByEmail(final String email);
    Collection<User> getUsers();
    void registerUser(String email, String pass);
    Movie getMovieById(final String id);
    void addMovie(String id, String title, String desc, double rating);
    void addRecommendation(final String userEmail, final String movieID, final String movieTitle,
                           final String description, double rating);
    Recommendation getRecommendationByUserAndMovie(final User user, final Movie movie);
    User getCurrUser();
    void setCurrUser(String email, String pass);
    boolean hasMovie(String id);
    List<Recommendation> getRecommendationsByMajor(String major);

}
