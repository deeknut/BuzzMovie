package com.example.deeknut.buzzmovie.models;

/**
 * Created by Jay on 3/4/16.
 */
public class Recommendation {
    private double rating;
    private String description;
    private String userEmail;
    private String movieTitle;

    /*
    Constructor for movie.
    @param title of movie
    @param description of movie
    @param rating of movie
     */
    public Recommendation(String userEmail, String movieTitle, String description, double rating) {
        this.rating = rating;
        this.userEmail = userEmail;
        this.movieTitle = movieTitle;
        this.description = description;
    }
    /*
    @return description of movie.
     */
    public String getDescription() {
        return description;
    }
    /*
    @return title of movie.
     */
    public String getTitle() {
        return movieTitle;
    }
    /*
    @return rating of movie.
     */
    public double getRating() {
        return rating;
    }
    /*
    @param rating to update movie with.
     */
    public void updateRating(double rating) {
        this.rating = rating;
    }
    /*
    @return string representation of movie.
     */
    public String toString() {

        return "Recommendation" + userEmail + movieTitle;
    }

    public String getUserEmail() {
        return userEmail;
    }
}
