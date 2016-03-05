package com.example.deeknut.buzzmovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.deeknut.buzzmovie.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Search screen that allows users to search and filter for movies
 */
public class BMSearchActivity extends AppCompatActivity {

    private final String baseUrl ="http://api.rottentomatoes.com/api/public/v1.0/";
    private final String apiParam = "?apikey=yedukp76ffytfuy24zsqk7f5";
    private RequestQueue queue;
    private ListView results;
    private EditText searchInput;
    Movie[] movieTitles;
    Intent movieScreenIntent;
    public static HashMap<String, Movie> prevMovies = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmsearch);
        movieScreenIntent = new Intent(this, BMMovieActivity.class);

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
                        movieTitles = new Movie[moviesSize];
                        for (int i = 0; i < moviesSize; i++) {
                            JSONObject movie = movies.getJSONObject(i);
                            if(prevMovies.get(movie.getString("title")) == null) {
                               prevMovies.put(movie.getString("title"), new Movie(movie.getString("title"),
                                       movie.getString("synopsis"),
                                       movie.getJSONObject("ratings").getInt("critics_score") / 20.0));

                            } else {
                                Log.d("SLDJFLSK", ""+prevMovies.get(movie.getString("title")).getRating());
                            }
                            movieTitles[i] = prevMovies.get(movie.getString("title"));
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
                    results.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            movieScreenIntent.putExtra("DAT_MOVIE_DOE", movieTitles[position]);
                            startActivity(movieScreenIntent);
                            //Intent intent = new Intent(new MovieActivity(movieTitles[position]));
                        }
                    });
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
    /*
    Closing actions after search is implemented.
     */
    private void removeSearchInputFocus() {
        // close keyboard
        searchInput.setEnabled(false);
        searchInput.setEnabled(true);
        // remove focus
        searchInput.clearFocus();
    }
}

