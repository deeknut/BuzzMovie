package com.example.deeknut.buzzmovie.models;

import java.io.Serializable;

/**
 * Created by Jay on 2/28/16.
 */
public class Movie implements Serializable {
    private double rating;
    private String title;
    private String description;
    private int counter;


    public Movie(String title, String description, double rating) {
        this.rating = rating;
        this.title = title;
        this.description = description;
        this.counter = 1;
    }
    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public double getRating() {
        return rating;
    }

    public void updateRating(double rating) {
        this.rating = rating;
        counter++;
    }

    public String toString() {
        return title;
    }
}
