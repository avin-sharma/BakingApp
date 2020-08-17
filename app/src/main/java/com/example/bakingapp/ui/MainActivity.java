package com.example.bakingapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.idling.CountingIdlingResource;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.bakingapp.MainViewModel;
import com.example.bakingapp.MainViewModelFactory;
import com.example.bakingapp.R;
import com.example.bakingapp.models.Recipe;
import com.example.bakingapp.ui.recipeDetails.RecipeDetailsActivity;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.ListItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recipeRecyclerView;
    private Recipe[] mRecipes;
    private CountingIdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerIdlingResource();

        recipeRecyclerView = findViewById(R.id.rv_recipe_list);
        final RecipeAdapter adapter = new RecipeAdapter(this);
        recipeRecyclerView.setAdapter(adapter);

        MainViewModelFactory factory = new MainViewModelFactory(mIdlingResource);
        MainViewModel viewModel = new ViewModelProvider(this, factory).get(MainViewModel.class);
        viewModel.getRecipes().observe(this, new Observer<Recipe[]>() {
            @Override
            public void onChanged(Recipe[] recipes) {
                adapter.setmRecipe(recipes);
                mRecipes = recipes;
            }
        });
    }


    @Override
    public void onListItemClick(int position) {
        // Start RecipeDetailsActivity
        Intent intent = new Intent(this, RecipeDetailsActivity.class);
        intent.putExtra("Recipe", mRecipes[position]);
        startActivity(intent);
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