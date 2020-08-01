package com.example.bakingapp.ui.recipeDetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.example.bakingapp.R;
import com.example.bakingapp.models.Ingredient;
import com.example.bakingapp.models.Recipe;
import com.example.bakingapp.models.Step;
import com.example.bakingapp.ui.IngredientsDetails.IngredientsDetailsActivity;
import com.example.bakingapp.ui.IngredientsDetails.IngredientsFragment;
import com.example.bakingapp.ui.stepDetails.StepDetailsActivity;
import com.example.bakingapp.ui.stepDetails.StepDetailsFragment;

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
    public void onListItemClick(int position) {
        Log.d(TAG, "step clicked: " + position);
        if (mTwoPane) {
            // Handling UI for tablet layout
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

//    @Override
//    public Step getClickedStep() {
//        if (selectionPosition == 0){
//            // This should never happen because we launch
//            // StepDetailsFragment gets launched only for steps
//            // which means selected position is never 0, we have
//            // ingredients at 0.
//            return null;
//        }
//
//        return mSteps[selectionPosition - 1];
//
//    }
}