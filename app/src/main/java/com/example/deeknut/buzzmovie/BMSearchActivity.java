package com.example.deeknut.buzzmovie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Search screen that allows users to search and filter for movies
 */
public class BMSearchActivity extends AppCompatActivity {

    private final String baseUrl ="http://api.rottentomatoes.com/api/public/v1.0/";
    private final String apiParam = "?apikey=yedukp76ffytfuy24zsqk7f5";
    private RequestQueue queue;
    private ListView results;
    private EditText searchInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmsearch);

        queue = Volley.newRequestQueue(this);
        results = (ListView) findViewById(R.id.results_listView);
        searchInput = (EditText) findViewById(R.id.editText_search);

        Button recentMoviesButton = (Button) findViewById(R.id.button_recent_movies);
        recentMoviesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptGetRecentMovies();
            }
        });

        Button recentDvdsButton = (Button) findViewById(R.id.button_recent_dvds);
        recentDvdsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptGetRecentDvds();
            }
        });

        Button searchButton = (Button) findViewById(R.id.button_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSearch(searchInput.getText().toString());
            }
        });
    }

    /**
     * Gets recent movies using helper method
     */
    private void attemptGetRecentMovies() {
        this.showApiResults("lists/movies/opening.json");
    }

    /**
     * Gets recent dvds using helper method
     */
    private void attemptGetRecentDvds() {
        this.showApiResults("lists/dvds/new_releases.json");
    }

    /**
     * Search movies using helper method
     * @param searchQuery query to search for
     */
    private void attemptSearch(String searchQuery) {
        this.showApiResults("movies.json", "&q=" + searchQuery);
    }

    /**
     * Search movies using helper method
     * @param endpoint endpoint to grab Rotten Tomatoes API data from
     */
    private void showApiResults(String endpoint) {
        this.showApiResults(endpoint, "");
    }

    /**
     * Shows Rotten Tomato API results in results list view
     * @param endpoint endpoint to grab Rotten Tomatoes API data from
     * @param params parameters to Rotten Tomatoes API
     */
    private void showApiResults(String endpoint, String params) {
        this.removeSearchInputFocus();
        params = params.replaceAll(" ", "%20");

        String url = baseUrl + endpoint + apiParam + params;

        JsonObjectRequest jsonRequest = new JsonObjectRequest (url, null,
                    new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray movies = response.getJSONArray("movies");
                    int moviesSize = movies.length();
                    ArrayAdapter adapter;
                    if (moviesSize > 0) {
                        String[] movieTitles = new String[moviesSize];
                        for (int i = 0; i < moviesSize; i++) {
                            movieTitles[i] = movies.getJSONObject(i).getString("title");
                        }
                        adapter = new ArrayAdapter<>(BMSearchActivity.this,
                                android.R.layout.simple_list_item_1, movieTitles);
                    } else {
                        String[] noMovieTitles = {"No movies found"};
                        adapter = new ArrayAdapter<>(BMSearchActivity.this,
                                android.R.layout.simple_list_item_1, noMovieTitles);
                    }

                    // save results to results listView
                    results.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        queue.add(jsonRequest);
    }

    private void removeSearchInputFocus() {
        // close keyboard
        searchInput.setEnabled(false);
        searchInput.setEnabled(true);
        // remove focus
        searchInput.clearFocus();
    }
}

