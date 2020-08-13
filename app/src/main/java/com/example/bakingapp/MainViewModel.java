package com.example.bakingapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.test.espresso.idling.CountingIdlingResource;

import com.example.bakingapp.models.Recipe;

public class MainViewModel extends ViewModel {
    private LiveData<Recipe[]> mRecipes;

    public MainViewModel(CountingIdlingResource mIdlingResource) {
        mRecipes = new RecipeRepository(mIdlingResource).getRecipes();
    }

    public LiveData<Recipe[]> getRecipes() {
        return mRecipes;
    }
}
