package com.example.deeknut.buzzmovie.models;

import java.io.Serializable;

/**
 * Created by Jay on 3/4/16.
 */
public class Recommendation implements Serializable {
    /**
     * field that holds the rating
     */
    private double rating;
    /**
     * Field that holds the description
     */
    private String description;
    /**
     * Field that holds the user's email
     */
    private String userEmail;
    /**
     * Field that holds the title of the movie
     */
    private String movieTitle;
    /**
     * Field that holds the movie ID
     */
    private String movieID;

    /**
     * Constructor for Recommendation.
     * @param userEmail email of user that made recommendation
     * @param movieID id of movie
     * @param movieTitle title of movie
     * @param description of movie
     * @param rating of movie
     */
    public Recommendation(String userEmail, String movieID, String movieTitle, String description, double rating) {
        this.rating = rating;
        this.userEmail = userEmail;
        this.movieTitle = movieTitle;
        this.description = description;
        this.movieID = movieID;
    }

    /**
     * Constructor for Recommendation.
     * @param userEmail email of user that made recommendation
     * @param movie the movie being recommended
     * @param rating of movie
     */
    public Recommendation(String userEmail, Movie movie, double rating) {
        this.rating = rating;
        this.userEmail = userEmail;
        this.movieTitle = movie.getTitle();
        this.description = movie.getDescription();
        this.movieID = movie.getMovieID();
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

    @Override
    public boolean equals(Object other) {
        if(other == null) { return false; }
        if(this == other) { return true; }
        if(!(other instanceof Recommendation)) { return false; }
        Recommendation that = (Recommendation) other;
        return this.getRating() == that.getRating() && this.getUserEmail().equals(that.getUserEmail());
    }
}
