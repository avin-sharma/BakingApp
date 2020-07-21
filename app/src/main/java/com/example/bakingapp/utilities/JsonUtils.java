package com.example.bakingapp.utilities;

import android.util.Log;

import com.example.bakingapp.models.Ingredient;
import com.example.bakingapp.models.Recipe;
import com.example.bakingapp.models.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {
    public static Recipe[] convertJsonStringToRecipes(String json) {
        Recipe[] recipes = null;
        try {
            JSONArray jsonRecipes = new JSONArray(json);
            recipes = new Recipe[jsonRecipes.length()];
            for (int i = 0; i < jsonRecipes.length(); i++) {
                recipes[i] = convertJsonToRecipe(jsonRecipes.getJSONObject(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recipes;
    }

    private static Recipe convertJsonToRecipe(JSONObject jsonRecipe) throws JSONException {
        int id = jsonRecipe.getInt("id");
        String name = jsonRecipe.getString("name");
        JSONArray ingredientsJsonArray = jsonRecipe.getJSONArray("ingredients");
        Ingredient[] ingredients = convertJsonToIngredients(ingredientsJsonArray);
        JSONArray stepsJsonArray = jsonRecipe.getJSONArray("steps");
        Step[] steps = convertJsonToSteps(stepsJsonArray);
        int servings = jsonRecipe.getInt("servings");
        String image = jsonRecipe.getString("image");

        return new Recipe(id, name, ingredients, steps, servings, image);
    }

    private static Ingredient[] convertJsonToIngredients(JSONArray ingredientsJsonArray) throws JSONException {
        Ingredient[] ingredients = new Ingredient[ingredientsJsonArray.length()];

        for (int i = 0; i < ingredientsJsonArray.length(); i++) {
            JSONObject ingredientJsonObject = ingredientsJsonArray.getJSONObject(i);
            int quantity = ingredientJsonObject.getInt("quantity");
            String measure = ingredientJsonObject.getString("measure");
            String ingredient = ingredientJsonObject.getString("ingredient");
            ingredients[i] = new Ingredient(quantity, measure, ingredient);
        }
        return ingredients;
    }

    private static Step[] convertJsonToSteps(JSONArray stepsJsonArray) throws JSONException {
        Step[] steps = new Step[stepsJsonArray.length()];

        for (int i = 0; i < stepsJsonArray.length(); i++) {
            JSONObject stepJsonObject = stepsJsonArray.getJSONObject(i);
            int id = stepJsonObject.getInt("id");
            String shortDescription = stepJsonObject.getString("shortDescription");
            String description = stepJsonObject.getString("description");
            String videoURL = stepJsonObject.getString("videoURL");
            String thumbnailURL = stepJsonObject.getString("thumbnailURL");
            steps[i] = new Step(id, shortDescription, description, videoURL, thumbnailURL);
        }
        return steps;
    }
}
