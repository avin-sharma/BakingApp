package com.example.bakingapp.ui.IngredientsDetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.bakingapp.R;
import com.example.bakingapp.models.Ingredient;
import com.example.bakingapp.ui.recipeDetails.RecipeDetailsActivity;

public class IngredientsDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients_details);
        Ingredient[] ingredients = (Ingredient[]) getIntent()
                .getExtras()
                .getSerializable(RecipeDetailsActivity.INGREDIENTS_EXTRA);

        Fragment fragment = IngredientsFragment.newInstance(ingredients);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_recipe_details_container, fragment).commit();
    }
}