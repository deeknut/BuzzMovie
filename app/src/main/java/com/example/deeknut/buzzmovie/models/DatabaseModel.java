package com.example.deeknut.buzzmovie.models;

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
 */
public final class DatabaseModel implements Model {

    /* The collection of users, movies, and recs, keyed by name.
      Used to cache all changes that happen in database for program access.
     */

    /**
     * Database model for users
     */
    private Map<String, User> users;
    /**
     * Database model for movies
     */
    private Map<String, Movie> movies;
    /**
     * Database model for recommendations
     */
    private Map<String, Recommendation> recommendations;
    /**
     * reference to currentUser
     */
    private User currUser;
    /**
     * Singleton instance
     */
    private static Model singleton;
    /**
     * Firebase reference
     */
    private static Firebase firebase;
    /**
     * base url for Firebase requests
     */
    private static final String BASEURL = "https://deeknut.firebaseio.com";//"https://shining-heat-1721.firebaseio.com";

    /**
     * Makes a new model
     */
    private DatabaseModel() {
        users = new HashMap<>();
        movies = new HashMap<>();
        recommendations =  new HashMap<>();
        firebase = new Firebase(BASEURL);
        firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                updateMaps(snapshot);
            }
            @Override
            public void onChildChanged(DataSnapshot snapshot, String s) {
                updateMaps(snapshot);
            }
            @Override
            public void onChildRemoved(DataSnapshot snapshot) {
                updateMaps(snapshot);
            }
            @Override
            public void onChildMoved(DataSnapshot snapshot, String s) {
                updateMaps(snapshot);
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {}
        });
    }

    /**
     * Updates local hashmaps with updates from firebase.
     * @param snapshot describes data to be updated
     */
    private void updateMaps(DataSnapshot snapshot) {
        if("users".equals(snapshot.getKey())) {
            for (final DataSnapshot userSnap : snapshot.getChildren()) {
                final Map map = (Map) userSnap.getValue();
                users.put(userSnap.getKey(), new User(userSnap.getKey(), (String) map.get("pass"),
                        map.get("interests").toString(), map.get("major").toString(),
                        (boolean) map.get("banned")));
            }
        } else if("movies".equals(snapshot.getKey())) {
            for (final DataSnapshot userSnap : snapshot.getChildren()) {
                final Map map = (Map) userSnap.getValue();
                movies.put(userSnap.getKey(), new Movie(userSnap.getKey(), (String) map.get("title"),
                        map.get("description").toString(), (double) map.get("rating")));
            }
        } else if("recommendations".equals(snapshot.getKey())) {
            for (final DataSnapshot userSnap : snapshot.getChildren()) {
                final Map map = (Map) userSnap.getValue();
                recommendations.put(userSnap.getKey(), new Recommendation(map.get("userEmail").toString(),
                        map.get("movieID").toString(), map.get("title").toString(), map.get("description").toString(),
                        (double) map.get("rating")));
            }
        }
    }

    /**
     * Parses email of user to implement in database.
     * @param email of user
     * @return parsed form of email to use in database.
     */
    public String parseEmail(String email) {
        return email.replace("@", "").replace(".", "");
    }

    @Override
    public boolean checkUser(final String email, final String password) {
        final User s = users.get(parseEmail(email));
        return s != null && password.equals(s.getPass());
    }

    @Override
    public boolean isUser(final String email) {
        final User s = users.get(parseEmail(email));
        return s != null;
    }

    @Override
    public List<User> listUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User getUserByEmail(final String email) {
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
        final List<Recommendation> list = new ArrayList<>();
        for(final Recommendation rec : recommendations.values()) {
            if ("All Majors".equals(major) ||
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
    public List<Recommendation> getAllRecommendations() {
        return (List<Recommendation>) recommendations.values();
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
    public void addRecommendation(final String userEmail, Movie movie) {
        //recommendations.put(userEmail + ":" + movieID, new Recommendation(userEmail, movieID, movieTitle, description, rating));
        firebase.child("recommendations").child(userEmail + ":" + movie.getMovieID()).
                setValue(new Recommendation(userEmail, movie.getMovieID(), movie.getTitle(),
                        movie.getDescription(), movie.getRating()));
    }

    @Override
    public Recommendation getRecommendationByUserAndMovie(User user, Movie movie) {
        return recommendations.get(user.getEmail() + ":" + movie.getMovieID());
    }

    @Override
    public Recommendation getRecommendationByUserAndMovie(String userEmail, Movie movie) {
        return recommendations.get(userEmail + ":" + movie.getMovieID());
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
