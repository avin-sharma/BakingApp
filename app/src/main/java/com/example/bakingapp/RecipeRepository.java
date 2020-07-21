package com.example.bakingapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bakingapp.models.Recipe;

public class RecipeRepository {
    MutableLiveData<Recipe[]> recipes;

    public LiveData<Recipe[]> getRecipes() {

    }
}
