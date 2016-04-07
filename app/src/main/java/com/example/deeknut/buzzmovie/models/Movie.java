package com.example.deeknut.buzzmovie.models;

import java.io.Serializable;

/**
 * Created by Jay on 2/28/16.
 */
public class Movie implements Serializable {
    /**
     *
     */
    private double rating;
    /**
     *
     */
    private String title;
    /**
     *
     */
    private String description;
    /**
     *
     */
    private String id;
    /**
     *
     */
    private int counter;

    /**
    Constructor for movie.
    @param movieID of movie from RottenTomatoes
    @param title of movie
    @param description of movie
    @param rating of movie
     */
    public Movie(String movieID, String title, String description, double rating) {
        this.rating = rating;
        this.title = title;
        this.description = description;
        this.counter = rating > 0 ? 1 : 0;
        id = movieID;
    }
    /**
    @return description of movie.
     */
    public String getDescription() {
        return description;
    }
    /**
    @return title of movie.
     */
    public String getTitle() {
        return title;
    }
    /**
     @return rating of movie.
     */
    public double getRating() {
        return rating;
    }
    /**
     @return ID of movie.
     */
    public String getMovieID() {
        return id;
    }
    /**
    @param rating to update movie with.
     */
    public void updateRating(double rating) {
        this.rating = (rating + this.rating * counter)/++counter;
    }
    /**
    @return string representation of movie.
     */
    public String toString() {
        return title;
    }
}
