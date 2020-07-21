package com.example.bakingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import com.example.bakingapp.models.Recipe;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.getRecipes().observe(this, new Observer<Recipe[]>() {
            @Override
            public void onChanged(Recipe[] recipes) {
                if (recipes != null) {
                    for (Recipe recipe: recipes){
                        Log.d(TAG, recipe.getName());
                    }
                }
            }
        });
    }
}