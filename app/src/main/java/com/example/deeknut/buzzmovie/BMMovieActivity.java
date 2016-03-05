package com.example.deeknut.buzzmovie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.deeknut.buzzmovie.models.Movie;

public class BMMovieActivity extends Activity implements RatingBar.OnRatingBarChangeListener {
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
        rating.setRating((float) movie.getRating());
        rating.setOnRatingBarChangeListener(this);

        Button backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBackToSearch();
            }
        });

        Button recButton = (Button) findViewById(R.id.rec_button);
        //JAVA 8
        recButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRecClicked();
            }
        });;
    }

    private void goBackToSearch() {
        Intent searchScreenIntent = new Intent(this, BMSearchActivity.class);
        startActivity(searchScreenIntent);
        finish();
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        if(fromUser) {
            Log.d("tag", "" + rating);
            movie.updateRating(rating);
            BMSearchActivity.prevMovies.put(movie.getTitle(), movie);
            this.rating.setRating((float) movie.getRating());
        }
    }

    private void onRecClicked() {
        Intent updateRecIntent = new Intent(this, BMRecActivity.class);
        updateRecIntent.putExtra("DAT_MOVIE_DOE", movie);
        startActivity(updateRecIntent);
    }
}
