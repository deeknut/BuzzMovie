package com.example.deeknut.buzzmovie;

import android.content.Intent;
import android.os.Bundle;
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

/**
 * Search screen that allows users to search and filter for movies
 */
public class BMSearchActivity extends BMModelActivity {

    /**
     *
     */
    private static final String BASEURL = "http://api.rottentomatoes.com/api/public/v1.0/";
    /**
     *
     */
    private static final String APIPARAM = "?apikey=yedukp76ffytfuy24zsqk7f5";
    /**
     *
     */
    private RequestQueue queue;
    /**
     *
     */
    private ListView results;
    /**
     *
     */
    private EditText searchInput;
    /**
     *
     */
    private Movie[] movieTitles;
    /**
     *
     */
    private Intent movieScreenIntent;

    /**
     * {@inheritDoc}
     * Called when app activity instance is started
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmsearch);
        movieScreenIntent = new Intent(this, BMMovieActivity.class);

        queue = Volley.newRequestQueue(this);
        results = (ListView) findViewById(R.id.results_listView);
        searchInput = (EditText) findViewById(R.id.editText_search);

        final Button recentMoviesButton = (Button) findViewById(R.id.button_recent_movies);
        recentMoviesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptGetRecentMovies();
            }
        });

        final Button recentDvdsButton = (Button) findViewById(R.id.button_recent_dvds);
        recentDvdsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptGetRecentDvds();
            }
        });

        final Button searchButton = (Button) findViewById(R.id.button_search);
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
     * @param paramsOG parameters to Rotten Tomatoes API
     */
    private void showApiResults(String endpoint, String paramsOG) {
        this.removeSearchInputFocus();
        final String params = paramsOG.replaceAll(" ", "%20");

        final String url = BASEURL + endpoint + APIPARAM + params;

        final JsonObjectRequest jsonRequest = new JsonObjectRequest (url, null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    addApiResults(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Exception", error.getMessage());
                }
            }
        );
        queue.add(jsonRequest);
    }

    /**
     * Adds API results to movies array.
     * @param response JSON response passed in because checkstyle was being annoying
     */
    private void addApiResults(JSONObject response) {
        try {
            final JSONArray movies = response.getJSONArray("movies");
            final int moviesSize = movies.length();
            ArrayAdapter adapter;
            if (moviesSize > 0) {
                movieTitles = new Movie[moviesSize];
                for (int i = 0; i < moviesSize; i++) {
                    final JSONObject movie = movies.getJSONObject(i);
                    final double scale = 20.0;
                    if(!getModel().hasMovie(movie.getString("id"))) {
                        getModel().addMovie(movie.getString("id"), movie.getString("title"),
                                movie.getString("synopsis"),
                                movie.getJSONObject("ratings").getInt("critics_score") / scale);

                    }
                    movieTitles[i] = getModel().getMovieById(movie.getString("id"));
                }
                adapter = new ArrayAdapter<>(BMSearchActivity.this,
                        android.R.layout.simple_list_item_1, movieTitles);
            } else {
                final String[] noMovieTitles = {"No movies found"};
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
                }
            });
        } catch (JSONException e) {
            Log.d("Exception", e.getMessage());
        }
    }

    /**
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

