package com.example.deeknut.buzzmovie.models;

import java.io.Serializable;

/**
 * Created by Jay on 3/4/16.
 */
public class Recommendation implements Serializable {
    private double rating;
    private String description;
    private String userEmail;
    private String movieTitle;
    private String movieID;

    /**
    Constructor for Recommendation.
     @param movieID
    @param movieTitle title of movie
    @param description of movie
    @param rating of movie
     */
    public Recommendation(String userEmail, String movieID, String movieTitle, String description, double rating) {
        this.rating = rating;
        this.userEmail = userEmail;
        this.movieTitle = movieTitle;
        this.description = description;
        this.movieID = movieID;
    }
    /**
    @return description of recommendation.
     */
    public String getDescription() {
        return description;
    }
    /**
    @return title of movie.
     */
    public String getTitle() {
        return movieTitle;
    }
    /**
    @return rating of movie.
     */
    public double getRating() {
        return rating;
    }
    /**
    @return string representation of recommendation.
     */
    public String toString() {

        return movieTitle + ", Rated by: " + userEmail + ", Rating: "+ rating;
    }

    /**
     * Returns email for current user.
     * @return user email
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * Returns movie ID for recommendation.
     * @return movie ID
     */
    public String getMovieID() {
        return movieID;
    }
}
