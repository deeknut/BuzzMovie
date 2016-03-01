package com.example.deeknut.buzzmovie;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.deeknut.buzzmovie.models.Movie;

public class MovieActivity extends Activity implements RatingBar.OnRatingBarChangeListener {
    Movie movie;
    RatingBar rating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        movie = (Movie) getIntent().getSerializableExtra("DAT_MOVIE_DOE");
        Log.d("MovieActivity", movie.toString());
        TextView title = (TextView)findViewById(R.id.title);
        title.setText(movie.getTitle());
        TextView desc = (TextView)findViewById(R.id.desc);
        desc.setText(movie.getDescription());
        rating = (RatingBar)findViewById(R.id.rating);
        Log.d("Indicator", "" + rating.isIndicator());
        rating.setIsIndicator(false);
        rating.setRating((float) movie.getRating());

        rating.setOnRatingBarChangeListener(this);
    }
    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        if(fromUser) {
            Log.d("tag", "" + rating);
            movie.updateRating(rating);
            this.rating.setRating((float) movie.getRating());
        }
    }
}
