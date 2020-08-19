package com.example.bakingapp.ui.IngredientsDetails;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.idling.CountingIdlingResource;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.bakingapp.R;
import com.example.bakingapp.models.Ingredient;
import com.example.bakingapp.ui.recipeDetails.RecipeDetailsActivity;

import java.util.Objects;

public class IngredientsDetailsActivity extends AppCompatActivity implements IngredientsFragment.FragmentLoadStatus {

    private CountingIdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients_details);
        String recipeName = (String) Objects.requireNonNull(getIntent().getExtras()).getString(RecipeDetailsActivity.RECIPE_NAME_EXTRA);
        Ingredient[] ingredients = (Ingredient[]) getIntent()
                .getExtras()
                .getSerializable(RecipeDetailsActivity.INGREDIENTS_EXTRA);

        registerIdlingResource();
        mIdlingResource.increment();

        Fragment fragment = IngredientsFragment.newInstance(ingredients);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_recipe_details_container, fragment).commit();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(recipeName);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void isNowIdle() {
        mIdlingResource.decrement();
    }

    /**
     * Register idling resource to wait for async calls in UI tests
     */
    private void registerIdlingResource() {
        mIdlingResource = new CountingIdlingResource("MainViewModelCalls");
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    @Override
    protected void onStop() {
        super.onStop();
        IdlingRegistry.getInstance().unregister(mIdlingResource);
    }
}