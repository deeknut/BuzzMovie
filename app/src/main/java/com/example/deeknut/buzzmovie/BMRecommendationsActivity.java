package com.example.deeknut.buzzmovie;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.deeknut.buzzmovie.models.Movie;
import com.example.deeknut.buzzmovie.models.Recommendation;

import java.util.List;

/**
 * Recommendations screen that allows users to search and filter for recommendations
 */
public class BMRecommendationsActivity extends BMModelActivity {
    /**
     * ListView for recommendations
     */
    private ListView recList;
    /**
     * list of recommendations as the model for ListView
     */
    private Recommendation[] r;
    /**
     * Intent for recommendations
     */
    private Intent recIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmrecommendations);
        recIntent = new Intent(this, BMRecActivity.class);

        recList = (ListView) findViewById(R.id.recommendations);
        final Spinner majorDropdown = (Spinner) findViewById(R.id.major_dropdown);
        final String[] majors = {"All Majors", "CS", "Business", "Memes"};
        final ArrayAdapter<String> majorsAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, majors);
        majorDropdown.setAdapter(majorsAdapter);
        updateRecommendations(getRecommendations("All Majors"));
        majorDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Log.d("yo", selectedItemView.toString());
                updateRecommendations(getRecommendations(((TextView) selectedItemView).getText().toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                //Log.d("no", "dsfa");
                //updateRecommendations(getRecommendations(null));
            }
        });
        recList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                recIntent.putExtra("DAT_MOVIE_DOE",
                        getModel().getMovieById(r[position].getMovieID()));
                recIntent.putExtra("DAT_USER_DOE", getModel().getUserByEmail(r[position].getUserEmail()));
                recIntent.putExtra("isEditable", false);
                startActivity(recIntent);
            }
        });
    }

    /**
     * Updates recommendations
     * @param recs for updating recommendations.
     */
    private void updateRecommendations(Recommendation[] recs) {
        final ArrayAdapter<Recommendation> recsAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, recs);
        recList.setAdapter(recsAdapter);
    }

    /**
     * Gets recommendations for display.
     * @param major to filter recommendations to display
     * @return recommendations to display.
     */
    private Recommendation[] getRecommendations(String major) {
        final List<Recommendation> list = getModel().getRecommendationsByMajor(major);
        r = new Recommendation[list.size()];
        for (int i = 0; i < list.size(); i++) {
            r[i] = list.get(i);
        }
        return r;
    }

    /**
     * Gets recommendations for display.
     * @param major to filter recommendations to display
     * @return recommendations to display.
     */
    public Recommendation[] getLocalRecommendations(String major) {
        final List<Recommendation> list = getMemoryModel().getRecommendationsByMajor(major);
        r = new Recommendation[list.size()];
        for (int i = 0; i < list.size(); i++) {
            r[i] = list.get(i);
        }
        return r;
    }
}
