package com.example.bakingapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.test.espresso.idling.CountingIdlingResource;

import com.example.bakingapp.models.Recipe;
import com.example.bakingapp.utilities.JsonUtils;
import com.example.bakingapp.utilities.NetworkUtils;

public class RecipeRepository {
    private CountingIdlingResource mIdlingResource;
    private MutableLiveData<Recipe[]> recipes = new MutableLiveData<>();

    public RecipeRepository(CountingIdlingResource mIdlingResource) {
        this.mIdlingResource = mIdlingResource;
    }

    public LiveData<Recipe[]> getRecipes() {
        mIdlingResource.increment();
        new Thread(new Runnable() {
            @Override
            public void run() {
                recipes.postValue(JsonUtils.convertJsonStringToRecipesGSON(NetworkUtils.fetchRecipesJson()));
                mIdlingResource.decrement();
            }
        }).start();
        return recipes;
    }
}
