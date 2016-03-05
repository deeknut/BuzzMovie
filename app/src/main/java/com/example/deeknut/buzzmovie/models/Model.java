package com.example.deeknut.buzzmovie.models;

/**
 * Created by Jay on 3/4/16.
 */
import java.util.Collection;

/**
 * Created by robertwaters on 2/24/16.
 */
public interface Model {
    boolean isUser(final String name);
    User getUserByName(final String name);
    Collection<User> getUsers();
}
