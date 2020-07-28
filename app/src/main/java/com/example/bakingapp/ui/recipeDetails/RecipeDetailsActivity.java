package com.example.bakingapp.ui.recipeDetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.example.bakingapp.R;
import com.example.bakingapp.models.Recipe;
import com.example.bakingapp.models.Step;

public class RecipeDetailsActivity extends AppCompatActivity implements StepsListFragment.StepsListFragmentInterface, StepsListAdapter.ListItemClickListener{

    private Recipe mRecipe;
    private static final String TAG = RecipeDetailsActivity.class.getSimpleName();
    private FrameLayout container;
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get recipe clicked in the MainActivity
        mRecipe = (Recipe) getIntent().getSerializableExtra("Recipe");

        setContentView(R.layout.activity_recipe_details);

        // find container and if its null we are on a single pane layout
        container = findViewById(R.id.fl_recipe_details_container);
        mTwoPane = container != null;
    }

    @Override
    public String[] getStepsNames() {
        Step[] steps = mRecipe.getSteps();
        String[] stepNames = new String[steps.length + 1];
        stepNames[0] = "Ingredients";
        for (int i = 0; i < steps.length; i++){
            stepNames[i + 1] = steps[i].getShortDescription();
        }
        return stepNames;
    }

    @Override
    public void onListItemClick(int position) {
        Log.d(TAG, "step clicked: " + position);
    }
}