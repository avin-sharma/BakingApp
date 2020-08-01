package com.example.bakingapp.ui.stepDetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.bakingapp.R;
import com.example.bakingapp.models.Step;
import com.example.bakingapp.ui.recipeDetails.RecipeDetailsActivity;

public class StepDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);
        Step step = (Step) getIntent()
                .getExtras()
                .getSerializable(RecipeDetailsActivity.STEPS_EXTRA);


        Fragment fragment = StepDetailsFragment.newInstance(step, false);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_recipe_details_container, fragment).commit();
    }
}