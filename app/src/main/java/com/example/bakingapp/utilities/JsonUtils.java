package com.example.bakingapp.utilities;

import com.example.bakingapp.models.Ingredient;
import com.example.bakingapp.models.Recipe;
import com.example.bakingapp.models.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {
    public static Recipe[] convertJsonStringToRecipes(String json) {
        Recipe[] recipes;
        try {
            JSONArray jsonRecipes = new JSONArray(json);
            recipes = new Recipe[jsonRecipes.length()];
            for (int i = 0; i < jsonRecipes.length(); i++) {
                recipes[i] = convertJsonToRecipe(jsonRecipes.getJSONObject(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

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

    private static Ingredient[] convertJsonToIngredients(JSONArray ingredientsJsonArray) {
        
    }

    private static Step[] convertJsonToSteps(JSONArray stepsJsonArray) {

    }
}
