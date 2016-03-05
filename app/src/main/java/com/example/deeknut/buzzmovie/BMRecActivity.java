package com.example.deeknut.buzzmovie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.deeknut.buzzmovie.models.Movie;
import com.example.deeknut.buzzmovie.models.Recommendation;


public class BMRecActivity extends AppCompatActivity {

    Recommendation recommendation;
    RatingBar rating;
    TextView title;
    TextView desc;

    @Override
    /**
     * {@inheritDoc}
     * Called when login activity instance is started
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rec);

        Movie movie = (Movie) getIntent().getSerializableExtra("DAT_MOVIE_DOE");
        Boolean isEditable = getIntent().getBooleanExtra("isEditable", false);
        recommendation = BMRecommendationsActivity.prevRecs.get(movie.getTitle());
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
        //Intent searchScreenIntent = new Intent(this, BMSearchActivity.class);
        //startActivity(searchScreenIntent);
        Recommendation rec = new Recommendation(BMLoginActivity.currentUser,title.getText().toString(),
                desc.getText().toString(), rating.getRating());
        Log.d("TITLE", rec.getTitle());
        BMRecommendationsActivity.prevRecs.put(rec.getTitle(), rec);
        finish();
    }
}
