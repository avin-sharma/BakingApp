package com.example.bakingapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.bakingapp.models.Recipe;

public class MainViewModel extends AndroidViewModel {
    private LiveData<Recipe[]> recipes = new RecipeRepository().getRecipes();

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Recipe[]> getRecipes() {
        return recipes;
    }
}
