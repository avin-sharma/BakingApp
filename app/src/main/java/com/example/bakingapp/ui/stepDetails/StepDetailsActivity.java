package com.example.bakingapp.ui.stepDetails;

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
import com.example.bakingapp.models.Step;
import com.example.bakingapp.ui.recipeDetails.RecipeDetailsActivity;

import java.util.Objects;

public class StepDetailsActivity extends AppCompatActivity implements StepDetailsFragment.FragmentLoadStatus{

    private CountingIdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);
        String recipeName = (String) Objects.requireNonNull(getIntent().getExtras()).getString(RecipeDetailsActivity.RECIPE_NAME_EXTRA);
        Step step = (Step) Objects.requireNonNull(getIntent()
                .getExtras())
                .getSerializable(RecipeDetailsActivity.STEPS_EXTRA);

        registerIdlingResource();
        mIdlingResource.increment();

        Fragment fragment = StepDetailsFragment.newInstance(step, false);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_recipe_details_container, fragment).commit();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(recipeName);
        }
    }

    /**
     * Register idling resource to wait for async calls in UI tests
     */
    private void registerIdlingResource() {
        mIdlingResource = new CountingIdlingResource("MainViewModelCalls");
        IdlingRegistry.getInstance().register(mIdlingResource);
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
    protected void onStop() {
        super.onStop();
        IdlingRegistry.getInstance().unregister(mIdlingResource);
    }

    @Override
    public void isNowIdle() {
        mIdlingResource.decrement();
    }
}