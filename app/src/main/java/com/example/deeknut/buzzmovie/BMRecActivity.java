package com.example.deeknut.buzzmovie;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.deeknut.buzzmovie.models.Movie;
import com.example.deeknut.buzzmovie.models.Recommendation;
import com.example.deeknut.buzzmovie.models.User;


public class BMRecActivity extends BMModelActivity {

    private Recommendation recommendation;
    private RatingBar rating;
    private TextView title;
    private TextView desc;
    private Movie movie;
    private User user;

    @Override
    /**
     * {@inheritDoc}
     * Called when login activity instance is started
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rec);
        movie = (Movie) getIntent().getSerializableExtra("DAT_MOVIE_DOE");
        user = (User) getIntent().getSerializableExtra("DAT_USER_DOE");
        Boolean isEditable = getIntent().getBooleanExtra("isEditable", false);
        recommendation = getModel().getRecommendationByUserAndMovie(user, movie);
        title = (TextView)findViewById(R.id.title);
        desc = (TextView)findViewById(R.id.desc);
        rating = (RatingBar)findViewById(R.id.ratingBar);
        title.setText(movie.getTitle());
        if(recommendation != null) {
            desc.setText(recommendation.getDescription());
            rating.setRating((float) recommendation.getRating());
        }
        desc.setEnabled(isEditable);
        rating.setIsIndicator(!isEditable);

        Button saveButton = (Button) findViewById(R.id.save_button);
        if(!isEditable) {
            saveButton.setText("Back");
        }
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBackToMovie();
            }
        });
    }
    /**
    Goes back to movie view.
     **/
    private void goBackToMovie() {
        getModel().addRecommendation(user.getEmail(), movie.getMovieID(), movie.getTitle(),
                desc.getText().toString(), rating.getRating());
        finish();
    }
}
