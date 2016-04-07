package com.example.deeknut.buzzmovie;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.deeknut.buzzmovie.models.Movie;

public class BMMovieActivity extends BMModelActivity implements RatingBar.OnRatingBarChangeListener {
    /**
     *
     */
    private Movie movie;
    /**
     *
     */
    private RatingBar rating;
    /**
     * {@inheritDoc}
     * Called when login activity instance is started
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        movie = (Movie) getIntent().getSerializableExtra("DAT_MOVIE_DOE");
        Log.d("MovieActivity", movie.toString());
        final TextView title = (TextView)findViewById(R.id.title);
        title.setText(movie.getTitle());
        final TextView desc = (TextView)findViewById(R.id.desc);
        desc.setText(movie.getDescription());

        rating = (RatingBar)findViewById(R.id.rating);
        rating.setRating((float) movie.getRating());
        rating.setOnRatingBarChangeListener(this);

        final Button backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBackToSearch();
            }
        });

        final Button recButton = (Button) findViewById(R.id.rec_button);
        recButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRecClicked();
            }
        });
    }
    /**
    Goes back to movie search screen.
     */
    private void goBackToSearch() {
        final Intent searchScreenIntent = new Intent(this, BMSearchActivity.class);
        startActivity(searchScreenIntent);
        finish();
    }
    /**
     * {@inheritDoc}
     * Called when rating is changed for a ratingBar.
     */
    @Override
    public void onRatingChanged(RatingBar ratingBar, float ratingVal, boolean fromUser) {
        if(fromUser) {
            movie.updateRating(ratingVal);
            getModel().addMovie(movie.getMovieID(), movie.getTitle(), movie.getDescription(), movie.getRating());
            this.rating.setRating((float) movie.getRating());
        }
    }
    /**
    Goes to recommendation update screen when recommendation button is clicked.
     */
    private void onRecClicked() {
        final Intent updateRecIntent = new Intent(this, BMRecActivity.class);
        updateRecIntent.putExtra("DAT_MOVIE_DOE", movie);
        updateRecIntent.putExtra("DAT_USER_DOE", getModel().getCurUser());
        Log.d("DAT_USER_DOE", getModel().getCurUser().toString());
        updateRecIntent.putExtra("isEditable", true);
        startActivity(updateRecIntent);
    }
}
