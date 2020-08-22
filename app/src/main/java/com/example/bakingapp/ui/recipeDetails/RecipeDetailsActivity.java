package com.example.bakingapp.ui.recipeDetails;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.idling.CountingIdlingResource;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.bakingapp.R;
import com.example.bakingapp.models.Ingredient;
import com.example.bakingapp.models.Recipe;
import com.example.bakingapp.models.Step;
import com.example.bakingapp.ui.IngredientsDetails.IngredientsDetailsActivity;
import com.example.bakingapp.ui.IngredientsDetails.IngredientsFragment;
import com.example.bakingapp.ui.stepDetails.StepDetailsActivity;
import com.example.bakingapp.ui.stepDetails.StepDetailsFragment;
import com.example.bakingapp.widget.BakingAppWidgetProvider;
import com.google.gson.Gson;

public class RecipeDetailsActivity extends AppCompatActivity implements StepsListFragment.StepsListFragmentInterface,
        StepsListAdapter.ListItemClickListener, StepDetailsFragment.FragmentLoadStatus, IngredientsFragment.FragmentLoadStatus{

    private static final String TAG = RecipeDetailsActivity.class.getSimpleName();
    private FrameLayout container;
    private boolean mTwoPane;
    private Recipe mRecipe;
    private Step[] mSteps;
    private Ingredient[] mIngredients;
    public static final String INGREDIENTS_EXTRA = "ingredients-extra";
    public static final String STEPS_EXTRA = "steps-extra";
    public static final String RECIPE_NAME_EXTRA = "recipe-name-extra";
    private CountingIdlingResource mIdlingResource;
    private int mLastClickPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get recipe clicked in the MainActivity
        // We do this before setContentView because it inflates the fragment
        // which requires steps and ingredients
        mRecipe = (Recipe) getIntent().getSerializableExtra("Recipe");
        mSteps = mRecipe.getSteps();
        mIngredients = mRecipe.getIngredients();
        setContentView(R.layout.activity_recipe_details);

        // find container and if its null we are on a single pane layout
        container = findViewById(R.id.fl_recipe_details_container);
        mTwoPane = container != null;

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(mRecipe.getName());
        }

        // Save last ingredients of the last viewed recipe
        SharedPreferences sharedPref = this.getSharedPreferences(
                getString(R.string.preference_last_recipe), Context.MODE_PRIVATE);

        SharedPreferences.Editor prefsEditor = sharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mIngredients);
        prefsEditor.putString("Ingredients", json);
        prefsEditor.apply();

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int appWidgetIds[] = appWidgetManager.getAppWidgetIds(
                new ComponentName(this, BakingAppWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.lv_ingredients);

        registerIdlingResource();
        if (savedInstanceState != null) {
            mIdlingResource.increment();
        }
    }

    @Override
    public String[] getStepsNames() {
        String[] stepNames = new String[mSteps.length + 1];
        stepNames[0] = getString(R.string.Ingredients);
        for (int i = 0; i < mSteps.length; i++){
            stepNames[i + 1] = mSteps[i].getShortDescription();
        }
        return stepNames;
    }

    @Override
    public int getLastClickedPosition() {
        return mLastClickPosition;
    }


    @Override
    public void onListItemClick(int position) {
        // Handling UI for tablet layout
        mLastClickPosition = position;
        if (mTwoPane) {
            Fragment fragment;
            mIdlingResource.increment();

            // If we click on ingredients
            if (position == 0) {
                fragment = IngredientsFragment.newInstance(mIngredients);
            }
            // If we click on steps
            else {
                fragment = StepDetailsFragment.newInstance(mSteps[position - 1], mTwoPane);
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fl_recipe_details_container, fragment).commit();
        } else {
            // Handling UI for phone layout

            Intent intent;
            if (position == 0) {
                // If we click on ingredients
                intent = new Intent(this, IngredientsDetailsActivity.class);
                intent.putExtra(INGREDIENTS_EXTRA, mIngredients);
            }
            else {
                // If we click on steps
                intent = new Intent(this, StepDetailsActivity.class);
                intent.putExtra(STEPS_EXTRA, mSteps[position - 1]);
            }
            intent.putExtra(RECIPE_NAME_EXTRA, mRecipe.getName());
            startActivity(intent);
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