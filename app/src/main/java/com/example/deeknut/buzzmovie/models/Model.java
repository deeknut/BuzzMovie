package com.example.deeknut.buzzmovie.models;

/**
 * Created by Jay on 3/4/16.
 */

import java.util.ArrayList;
import java.util.List;

/**
 * Interface that all modeling interfacers must implement.
 */
public interface Model {
    /**
     * Checks if credentials exist for a user in the system
     * @param email username
     * @param password password
     * @return whether user exists in the system
     */
    boolean checkUser(final String email, final String password);

    /**
     * Checks whether there is a user with the email provided
     * @param email username to specify user
     * @return whether user exists in system
     */
    boolean isUser(final String email);

    /**
     * Returns user specified by email.
     * @param email to specify which user to return
     * @return User with email provided
     */
    User getUserByEmail(final String email);

    /**
     * Gets movie with specified id.
     * @param id to specify which movie to return
     * @return Movie with specified id
     */
    Movie getMovieById(final String id);

    /**
     * Adds movie with specified information.
     * @param id to make new movie with
     * @param title of new movie
     * @param desc description of new movie
     * @param rating of new movie
     */
    void addMovie(String id, String title, String desc, double rating);

    /**
     * Adds recommendation with specified credentials.
     * @param userEmail for recommendation
     * @param movieID for recommendation
     * @param movieTitle for recommendation
     * @param description of recommendation
     * @param rating for movie that recommendation is associated with
     */
    void addRecommendation(final String userEmail, final String movieID, final String movieTitle,
                           final String description, double rating);

    /**
     * Gets recommendation by specified user and movie.
     * @param user to specify recommendation with
     * @param movie to specify recommendation with
     * @return Recommendation that links provided user and movie.
     */
    Recommendation getRecommendationByUserAndMovie(final User user, final Movie movie);

    /**
     * Get current user logged in to the application
     * @return current user logged in to application
     */
    User getCurUser();

    /**
     * Sets the current user of the application. Checks to see if user already exists - if not,
     * makes a new user.
     * @param email of current user.
     * @param pass of current user.
     */
    void setCurUser(String email, String pass);

    /**
     * Checks whether movie exists in memory
     * @param id of movie to check
     * @return whether movie exists in memory
     */
    boolean hasMovie(String id);

    /**
     * Gets all the recommendations of a particular major.
     * @param major specifies which recommendations needed
     * @return recommendations for a specific major
     */
    List<Recommendation> getRecommendationsByMajor(String major);

    /**
     * Returns an array list of all users in system
     */
    ArrayList<User> listUsers();

}
