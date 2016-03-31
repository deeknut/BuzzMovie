package com.example.deeknut.buzzmovie.models;

/**
 * Singleton class that serves as an interfacer for all stored data.
 */

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MemoryModel implements Model {

    /** The collection of users, movies, and recs, keyed by name.
     * Currently our structurers.*/
    public Map<String, User> users;
    private Map<String, Movie> movies;
    private Map<String, Recommendation> recommendations;
    /**
    Current user and singleton object for MemoryModel.
     */
    private User currUser;
    private static Model singleton;
    ObjectInputStream userObjectInputStream;
    ObjectInputStream movieObjectInputStream;
    ObjectInputStream recObjectInputStream;

    ObjectOutputStream userObjectOutputStream;
    ObjectOutputStream movieObjectOutputStream;
    ObjectOutputStream recObjectOutputStream;

    /**
     * Makes a new model
     * @param c Context used to find file directory
     */
    private MemoryModel(Context c) {
        try {
            userObjectInputStream = new ObjectInputStream(new FileInputStream(new File(c.getFilesDir(), "users.txt")));
            users = (HashMap) userObjectInputStream.readObject();
            Log.d("users map", users.toString());
        } catch (Exception e) {
            Log.d("user no pesistence", "fdsfa");
            users = new HashMap<>();
        }
        try {
            movieObjectInputStream = new ObjectInputStream(new FileInputStream(new File(c.getFilesDir(), "movies.txt")));
            movies = (HashMap) movieObjectInputStream.readObject();
        } catch (Exception e) {
            Log.d("movie no pesistence", "fdsfa");
            movies = new HashMap<>();
        }
        try {
            recObjectInputStream = new ObjectInputStream(new FileInputStream(new File(c.getFilesDir(), "recs.txt")));
            recommendations = (HashMap) recObjectInputStream.readObject();
        } catch (Exception e) {
            Log.d("rec no pesistence", "fdsfa");
            recommendations = new HashMap<>();
        }
        try {
            userObjectOutputStream = new ObjectOutputStream(new FileOutputStream(new File(c.getFilesDir(), "users.txt")));
        } catch (Exception e) {
            File f = new File(c.getFilesDir(), "users.txt");
            try {
                f.createNewFile();
            } catch (Exception ec) {
                System.out.println("create users failed");
                System.out.println(ec.getMessage());
            }
        }
        try {
            movieObjectOutputStream = new ObjectOutputStream(new FileOutputStream(new File(c.getFilesDir(), "movies.txt")));
        } catch (Exception e) {
            File f = new File(c.getFilesDir(), "movies.txt");
            try {
                f.createNewFile();
                movieObjectOutputStream = new ObjectOutputStream(new FileOutputStream(new File(c.getFilesDir(), "movies.txt")));
            } catch (Exception ec) {
                System.out.println("create movies failed");
            }
        }
        try {
            recObjectOutputStream = new ObjectOutputStream(new FileOutputStream(new File(c.getFilesDir(), "recs.txt")));
        } catch (Exception e) {
            File f = new File(c.getFilesDir(), "recs.txt");
            try {
                f.createNewFile();
                recObjectOutputStream = new ObjectOutputStream(new FileOutputStream(new File(c.getFilesDir(), "recs.txt")));
            } catch (Exception ec) {
                System.out.println("create recs failed");
            }
        }
    }

    @Override
     public boolean checkUser(final String email, final String password) {
        User s = users.get(email); return s != null && password.equals(s.getPass());
    }

    @Override
    public boolean isUser(final String email) {
        User s = users.get(email);
        return s != null;
    }

    public ArrayList<User> listUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User getUserByEmail(final String email) {
        return users.get(email);
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
            Log.d("Email", rec.getUserEmail());
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
        write(movies, movieObjectOutputStream);
    }

    @Override
    public void addRecommendation(final String userEmail, final String movieID, final String movieTitle,
                                   final String description, double rating) {
        recommendations.put(userEmail + ":" + movieID, new Recommendation(userEmail, movieID, movieTitle, description, rating));
        write(recommendations, recObjectOutputStream);
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
        if(isUser(email)) {
            currUser = users.get(email);
        } else {
            currUser = new User(email, pass);
            users.put(email, currUser);
            write(users, userObjectOutputStream);
        }
    }

    public static Model getInstance(Context c) {
        if(singleton == null) {
            singleton = new MemoryModel(c);
        }
        return singleton;
    }

    /**
     * writes a map to  a textfile
     * @param m map to write
     * @param s stream to write with
     */
    public void write(Map m, ObjectOutputStream s) {
        try {
            s.writeObject(m);
            s.flush();
        } catch (Exception e) {
            Log.d("write failed", e.getMessage());
        }
    }

    /**
     * write the users map
     */
    public void writeUsers() {
        Log.d("my major", users.get("j@.").getMajor());
        write(users, userObjectOutputStream);
    }
}
