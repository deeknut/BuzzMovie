package com.example.deeknut.buzzmovie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.deeknut.buzzmovie.models.DatabaseModel;
import com.example.deeknut.buzzmovie.models.MemoryModel;
import com.example.deeknut.buzzmovie.models.Model;
import com.firebase.client.Firebase;

/**
 * Abstract superclass that provides functionality for dealing with Model.
 * Allows other activities to not import MemoryModel and Model in order to get relevant data.
 */
public abstract class BMModelActivity extends AppCompatActivity {
    /**
     *
     */
    private Model model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        model = DatabaseModel.getInstance();
    }

    /**
     * Returns current model containing saved information.
     * @return model with saved information.
     */
    public Model getModel() {
        return model;
    }

    /**
     * Returns a memory model associated with the activity. Used for testing.
     * @return Memory model associated with BMModelActivity.
     */
    public Model getMemoryModel() {
        return MemoryModel.getInstance();
    }
}
