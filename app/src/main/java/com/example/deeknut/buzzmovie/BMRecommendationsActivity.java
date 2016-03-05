package com.example.deeknut.buzzmovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.deeknut.buzzmovie.models.Recommendation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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
        String[] majors = {"All Majors", "CS", "Business", "Memes"};
        ArrayAdapter<String> majorsAdapter = new ArrayAdapter<>(
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
    }

    private void updateRecommendations(Recommendation[] recs) {
        ArrayAdapter<Recommendation> recsAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, recs);
        recList.setAdapter(recsAdapter);
    }

    private Recommendation[] getRecommendations(String major) {
        // TODO: change janky hard coded stuff with real data

        List<Recommendation> list = new ArrayList<>();
        for(Recommendation rec : prevRecs.values()) {
            Log.d("rec", BMRegisterActivity.userInfoMap.get(rec.getUserEmail()).get("Major"));
            if (major.equals("All Majors") ||
                BMRegisterActivity.userInfoMap.get(rec.getUserEmail()).get("Major").trim().equals(major)) {
                list.add(rec);
            }
        }
        Collections.sort(list, new Comparator<Recommendation>() {
            @Override
            public int compare(Recommendation r1, Recommendation r2) {
                return (r1.getRating() - r2.getRating()) < 0 ? 1 : 0;
            }
        });
        Recommendation[] r = new Recommendation[list.size()];
        for (int i = 0; i < list.size(); i++) {
            r[i] = list.get(i);
        }
        return r;
    }
}
