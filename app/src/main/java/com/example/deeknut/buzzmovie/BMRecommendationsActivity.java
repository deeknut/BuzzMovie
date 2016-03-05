package com.example.deeknut.buzzmovie;

import android.content.Intent;
import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.example.deeknut.buzzmovie.models.Recommendation;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Recommendations screen that allows users to search and filter for recommendations
 */
public class BMRecommendationsActivity extends AppCompatActivity {
    private ListView recList;
    Recommendation[] recommendations;
    Intent recScreenIntent;
    public static HashMap<String, Recommendation> prevRecs = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmrecommendations);
        recList = (ListView) findViewById(R.id.recommendations);
        Spinner majorDropdown = (Spinner) findViewById(R.id.major_dropdown);
        // TODO: change hard coded array with real data
        String[] majors = {"Computer Science", "Business", "Memes"};
        ArrayAdapter<String> majorsAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, majors);
        majorDropdown.setAdapter(majorsAdapter);
        updateRecommendations(getRecommendations(""));
    }

    private void updateRecommendations(Recommendation[] recs) {
        ArrayAdapter<Recommendation> recsAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, recs);
        recList.setAdapter(recsAdapter);
    }

    private Recommendation[] getRecommendations(String major) {
        // TODO: change janky hard coded stuff with real data
        Recommendation[] r = new Recommendation[prevRecs.size()];
        int i = 0;
        for (Recommendation rec : prevRecs.values()) {
            r[i++] = rec;
        }
        return r;
    }
}
