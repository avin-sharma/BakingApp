package com.example.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.bakingapp.R;
import com.example.bakingapp.models.Ingredient;
import com.google.gson.Gson;

public class IngredientsRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mApplicationContext;
    private Intent mIntent;
    private Ingredient[] mIngredients = new Ingredient[0];
    Gson gson = new Gson();

    public IngredientsRemoteViewFactory(Context applicationContext, Intent intent) {
        mApplicationContext = applicationContext;
        mIntent = intent;
    }

    @Override
    public void onCreate() {
        updateIngredients();
    }

    @Override
    public void onDataSetChanged() {
        updateIngredients();
    }

    @Override
    public void onDestroy() {
        mIngredients = null;
        mApplicationContext = null;
        mIntent = null;
    }

    @Override
    public int getCount() {
        return mIngredients.length;
    }

    @Override
    public RemoteViews getViewAt(int i) {
        // position will always range from 0 to getCount() - 1.
        // We construct a remote views item based on our widget item xml file, and set the
        // text based on the position.
        RemoteViews rv = new RemoteViews(mApplicationContext.getPackageName(), R.layout.ingredients_list_item);
        rv.setTextViewText(R.id.tv_quantity_and_measure, mIngredients[i].getQuantity() + mIngredients[i].getMeasure());
        rv.setTextViewText(R.id.tv_ingredient, mIngredients[i].getIngredients());
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private void updateIngredients() {
        SharedPreferences sharedPref = mApplicationContext.getSharedPreferences(
                mApplicationContext.getString(R.string.preference_last_recipe), Context.MODE_PRIVATE);
        String json = sharedPref.getString("Ingredients", "");
        mIngredients = gson.fromJson(json, Ingredient[].class);
    }
}
