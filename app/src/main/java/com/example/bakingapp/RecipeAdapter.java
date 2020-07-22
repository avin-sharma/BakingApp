package com.example.bakingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bakingapp.models.Recipe;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private Recipe[] mRecipe;

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        ImageView recipeImage;
        TextView recipeName;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeImage = itemView.findViewById(R.id.iv_recipe_image);
            recipeName = itemView.findViewById(R.id.tv_recipe_name);
        }

        private void bind(Recipe recipe){
            String name = recipe.getName();
            String image = recipe.getImage();

            if (!image.equals("")){
                Glide.with(itemView).load(image).into(recipeImage);
            }
            recipeName.setText(name);
        }
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recipe_list_item, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.bind(mRecipe[position]);
    }

    @Override
    public int getItemCount() {
        if (mRecipe == null) return 0;
        return mRecipe.length;
    }

    public void setmRecipe(Recipe[] recipes){
        mRecipe = recipes;
        notifyDataSetChanged();
    }
}
