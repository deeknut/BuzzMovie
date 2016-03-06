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

import com.example.deeknut.buzzmovie.models.MemoryModel;
import com.example.deeknut.buzzmovie.models.Model;
import com.example.deeknut.buzzmovie.models.Recommendation;

import java.util.List;

/**
 * Recommendations screen that allows users to search and filter for recommendations
 */
public class BMRecommendationsActivity extends AppCompatActivity {
    private ListView recList;
    Recommendation[] r;
    //public static HashMap<String, Recommendation> prevRecs = new HashMap<>();
    private Intent recIntent;
    private Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmrecommendations);
        model = MemoryModel.getInstance();
        recIntent = new Intent(this, BMRecActivity.class);

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
        recList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                recIntent.putExtra("DAT_MOVIE_DOE",
                        model.getMovieById(r[position].getMovieID()));
                recIntent.putExtra("DAT_USER_DOE", model.getUserByEmail(r[position].getUserEmail()));
                recIntent.putExtra("isEditable", false);
                startActivity(recIntent);
            }
        });
    }

    private void updateRecommendations(Recommendation[] recs) {
        ArrayAdapter<Recommendation> recsAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, recs);
        recList.setAdapter(recsAdapter);
    }

    private Recommendation[] getRecommendations(String major) {
        List<Recommendation> list = model.getRecommendationsByMajor(major);
        r = new Recommendation[list.size()];
        for (int i = 0; i < list.size(); i++) {
            r[i] = list.get(i);
        }
        return r;
    }
}
