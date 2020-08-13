package com.example.bakingapp.ui.recipeDetails;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

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
        StepsListAdapter.ListItemClickListener{

    private static final String TAG = RecipeDetailsActivity.class.getSimpleName();
    private FrameLayout container;
    private boolean mTwoPane;
    private Step[] mSteps;
    private Ingredient[] mIngredients;
    public static final String INGREDIENTS_EXTRA = "ingredients-extra";
    public static final String STEPS_EXTRA = "steps-extra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get recipe clicked in the MainActivity
        // We do this before setContentView because it inflates the fragment
        // which requires steps and ingredients
        Recipe recipe = (Recipe) getIntent().getSerializableExtra("Recipe");
        mSteps = recipe.getSteps();
        mIngredients = recipe.getIngredients();
        setContentView(R.layout.activity_recipe_details);

        // find container and if its null we are on a single pane layout
        container = findViewById(R.id.fl_recipe_details_container);
        mTwoPane = container != null;

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
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
    }

    @Override
    public String[] getStepsNames() {
        String[] stepNames = new String[mSteps.length + 1];
        stepNames[0] = "Ingredients";
        for (int i = 0; i < mSteps.length; i++){
            stepNames[i + 1] = mSteps[i].getShortDescription();
        }
        return stepNames;
    }

    @Override
    public void selectFirstItemOnStartIfDualPane(RecyclerView recyclerView) {
        Log.d(TAG, String.valueOf(recyclerView.findViewHolderForAdapterPosition(0) != null));
        if (mTwoPane) {
            StepsListAdapter.StepViewHolder view = (StepsListAdapter.StepViewHolder) recyclerView.findViewHolderForAdapterPosition(0);
            view.itemView.performClick();
            Log.d(TAG,"Why is this not working?");
        }
    }

    @Override
    public void onListItemClick(int position) {
        Log.d(TAG, "step clicked: " + position);
        // Handling UI for tablet layout
        if (mTwoPane) {
            Fragment fragment;
            if (position == 0) {
                // If we click on ingredients
                fragment = IngredientsFragment.newInstance(mIngredients);
            }
            else {
                // If we click on steps
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
}