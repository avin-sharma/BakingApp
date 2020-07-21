package com.example.bakingapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bakingapp.models.Recipe;
import com.example.bakingapp.utilities.JsonUtils;
import com.example.bakingapp.utilities.NetworkUtils;

public class RecipeRepository {

    private MutableLiveData<Recipe[]> recipes = new MutableLiveData<>();

    public LiveData<Recipe[]> getRecipes() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                recipes.postValue(JsonUtils.convertJsonStringToRecipes(NetworkUtils.fetchRecipesJson()));
            }
        }).start();
        return recipes;
    }
}
