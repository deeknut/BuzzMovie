package com.example.deeknut.buzzmovie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.deeknut.buzzmovie.models.MemoryModel;
import com.example.deeknut.buzzmovie.models.Model;
import com.example.deeknut.buzzmovie.models.Movie;
import com.example.deeknut.buzzmovie.models.Recommendation;


public class BMRecActivity extends AppCompatActivity {

    private Recommendation recommendation;
    private RatingBar rating;
    private TextView title;
    private TextView desc;
    private Model model;
    private Movie movie;

    @Override
    /**
     * {@inheritDoc}
     * Called when login activity instance is started
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rec);
        model = MemoryModel.getInstance();
        movie = (Movie) getIntent().getSerializableExtra("DAT_MOVIE_DOE");
        Boolean isEditable = getIntent().getBooleanExtra("isEditable", false);
        recommendation = model.getRecommendationByUserAndMovie(model.getCurrUser(), movie);
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
        //Log.d("TITLE", rec.getTitle());
        model.addRecommendation(model.getCurrUser().getEmail(), movie.getMovieID(), movie.getTitle(),
                desc.getText().toString(), rating.getRating());
        finish();
    }
}
