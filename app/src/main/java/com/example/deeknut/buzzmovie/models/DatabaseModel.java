package com.example.deeknut.buzzmovie.models;

import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Singleton class that provides database functionality and should serve as the ultimate
 * interfacer for the application.
 *
 */
public class DatabaseModel implements Model {

    /** The collection of users, movies, and recs, keyed by name.
     * Used to cache all changes that happen in database for program access.
     */
    private Map<String, User> users;
    private Map<String, Movie> movies;
    private Map<String, Recommendation> recommendations;
    /**
     The below fields will permanently stay as part of the DatabaseModel.
     Once we switch to database, these will NOT get replaced.
     */
    private User currUser;
    private static Model singleton;
    private static Firebase firebase;
    private static final String baseUrl = "https://deeknut.firebaseio.com";//"https://shining-heat-1721.firebaseio.com";

    /**
     * Makes a new model
     */
    private DatabaseModel() {
        users = new HashMap<>();
        movies = new HashMap<>();
        recommendations =  new HashMap<>();
        firebase = new Firebase(baseUrl);
        firebase.addChildEventListener(new ChildEventListener() {
            /**
             * Updates local hashmaps with updates from firebase.
             * @param snapshot describes data to be updated
             */
            private void updateMaps(DataSnapshot snapshot) {
                if(snapshot.getKey().equals("users")) {
                    for (DataSnapshot userSnap : snapshot.getChildren()) {
                        Map map = (Map) userSnap.getValue();
                        users.put(userSnap.getKey(), new User(userSnap.getKey(), (String) map.get("pass"),
                                map.get("interests").toString(), map.get("major").toString(),
                                (boolean) map.get("banned")));
                    }
                } else if(snapshot.getKey().equals("movies")) {
                    for (DataSnapshot userSnap : snapshot.getChildren()) {
                        Map map = (Map) userSnap.getValue();
                        movies.put(userSnap.getKey(), new Movie(userSnap.getKey(), (String) map.get("title"),
                                map.get("description").toString(), (double) map.get("rating")));
                    }
                } else if(snapshot.getKey().equals("recommendations")) {
                    for (DataSnapshot userSnap : snapshot.getChildren()) {
                        Map map = (Map) userSnap.getValue();
                        recommendations.put(userSnap.getKey(), new Recommendation(map.get("userEmail").toString(),
                                map.get("movieID").toString(), map.get("title").toString(), map.get("description").toString(),
                                (double) map.get("rating")));
                    }
                }
            }

            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                Log.d("ADDED TO SNAPSHOT", snapshot.getKey());
                updateMaps(snapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot snapshot, String s) {
                Log.d("CHANGED IN SNAPSHOT", snapshot.getKey());
                updateMaps(snapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot snapshot) {
                Log.d("REMOVED FROM SNAPSHOT", snapshot.getKey());
                updateMaps(snapshot);
            }

            @Override
            public void onChildMoved(DataSnapshot snapshot, String s) {
                Log.d("MOVED IN SNAPSHOT", snapshot.getKey());
                updateMaps(snapshot);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("FIREBASE ERROR", firebaseError.toString());
            }
        });
    }

    private String parseEmail(String email) {
        return email.replace("@", "").replace(".", "");
    }

    @Override
    public boolean checkUser(final String email, final String password) {
        User s = users.get(parseEmail(email));
        return s != null && password.equals(s.getPass());
    }

    @Override
    public boolean isUser(final String email) {
        User s = users.get(parseEmail(email));
        return s != null;
    }

    @Override
    public ArrayList<User> listUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User getUserByEmail(final String email) {
        System.out.println(users);
        System.out.println(users.get(parseEmail(email)));
        return users.get(parseEmail(email));
    }
    @Override
    public Movie getMovieById(final String id) {
        return movies.get(id);
    }
    @Override
    public boolean hasMovie(final String id) {
        return movies.get(id) != null;
    }

    @Override
    public List<Recommendation> getRecommendationsByMajor(String major) {
        List<Recommendation> list = new ArrayList<>();
        for(Recommendation rec : recommendations.values()) {
            if (major.equals("All Majors") ||
                    major.equals(getUserByEmail(rec.getUserEmail()).getMajor())) {
                list.add(rec);
            }
        }
        Collections.sort(list, new Comparator<Recommendation>() {
            @Override
            public int compare(Recommendation r1, Recommendation r2) {
                return (r1.getRating() - r2.getRating()) < 0 ? 1 : 0;
            }
        });
        return list;
    }

    @Override
    public void addMovie(final String id, final String title, final String description, double rating) {
        movies.put(id, new Movie(id, title, description, rating));
        firebase.child("movies").child(id).setValue(new Movie(id, title, description, rating));
    }

    @Override
    public void addRecommendation(final String userEmail, final String movieID, final String movieTitle,
                                  final String description, double rating) {
        //recommendations.put(userEmail + ":" + movieID, new Recommendation(userEmail, movieID, movieTitle, description, rating));
        firebase.child("recommendations").child(userEmail + ":" + movieID).
                setValue(new Recommendation(userEmail, movieID, movieTitle, description, rating));
    }

    @Override
    public Recommendation getRecommendationByUserAndMovie(User user, Movie movie) {
        return recommendations.get(user.getEmail() + ":" + movie.getMovieID());
    }

    @Override
    public User getCurUser() {
        return currUser;
    }

    @Override
    public void setCurUser(String email, String pass) {
        currUser = users.get(email.replace("@", "").replace(".", ""));
        if(currUser == null) {
            currUser = new User(email, pass);
            users.put(email, currUser);
            firebase.child("users").child(parseEmail(email)).setValue(currUser);
        }

    }

    /**
     * Gets current instance of model. Creates new instance if not done so already.
     * @return current instance of model
     */
     public static Model getInstance() {
        if(singleton == null) {
            singleton = new DatabaseModel();
        }
        return singleton;
    }
}
