package com.example.deeknut.buzzmovie.models;

/**
 * Created by Jay on 3/4/16.
 */
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MemoryModel implements Model {
    private final String TAG = "MemoryModel";

    /** the collection of students, keyed by name */
    private Map<String, User> users;
    private Map<String, Movie> movies;
    /**
     * Makes a new model
     */
    public MemoryModel() {
        users = new HashMap<String, User>();
        //users.put("Bob", new User("Bob Waters", 123));
    }

    @Override
    public boolean isUser(final String name) {
        User s = users.get(name);
        if (s == null) return false;
        return true;
    }

    @Override
    public User getUserByName(final String name) {
        User s = users.get(name);
        //Log.d(TAG, "Finding student :" + name);

        return s;
    }

    @Override
    public Collection<User> getUsers() {
        return users.values();
    }
}
