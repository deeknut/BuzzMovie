package com.example.deeknut.buzzmovie

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.deeknut.buzzmovie.models.Recommendation;

import java.util.HashMap;


public class BMRecActivity extends AppCompatActivity {

    Recommendation recommendation;
    RatingBar rating;
    //HashMap<String, Recommendation> recommendations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rec);

        recommendation = (Recommendation) getIntent().getSerializableExtra("DAT_MOVIE_DOE");
        Log.d("RecommendationActivity", recommendation.toString());
        TextView title = (TextView)findViewById(R.id.title);
        title.setText(recommendation.getTitle());
        TextView desc = (TextView)findViewById(R.id.desc);
        desc.setText(recommendation.getDescription());

        rating = (RatingBar)findViewById(R.id.rating);
        rating.setRating((float) recommendation.getRating());
        rating.setOnRatingBarChangeListener(this);

        Button backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBackToSearch();
            }
        });
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
            recommendation.updateRating(rating);
            BMRecomendationsActivity.recommendations.put(recommendation.getTitle(), recommendation);
            this.rating.setRating((float) recommendation.getRating());
        }
    }
}
