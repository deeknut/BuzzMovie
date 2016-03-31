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
    private Model model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO: Change to DatabaseModel once DB is working
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
}
