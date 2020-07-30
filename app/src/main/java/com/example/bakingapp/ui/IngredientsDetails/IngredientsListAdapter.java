package com.example.bakingapp.ui.IngredientsDetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.R;
import com.example.bakingapp.models.Ingredient;

public class IngredientsListAdapter extends RecyclerView.Adapter<IngredientsListAdapter.IngredientsListAdapterViewHolder>{

    private final Ingredient[] mIngredients;

    public IngredientsListAdapter(Ingredient[] ingredients) {
        this.mIngredients = ingredients;
    }

    public class IngredientsListAdapterViewHolder extends RecyclerView.ViewHolder {

        public final TextView tvMeasureAndQuantity;
        public final TextView tvIngredient;

        public IngredientsListAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMeasureAndQuantity = itemView.findViewById(R.id.tv_quantity_and_measure);
            tvIngredient = itemView.findViewById(R.id.tv_ingredient);
        }

        private void bind(Ingredient ingredient) {
            tvMeasureAndQuantity.setText(ingredient.getQuantity() + " " + ingredient.getMeasure());
            tvIngredient.setText(ingredient.getIngredients());
        }
    }

    @NonNull
    @Override
    public IngredientsListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ingredients_list_item, parent, false);
        return new IngredientsListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsListAdapterViewHolder holder, int position) {
        holder.bind(mIngredients[position]);
    }

    @Override
    public int getItemCount() {
        return mIngredients.length;
    }
}
