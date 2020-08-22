package com.example.bakingapp.models;

import java.io.Serializable;

public class Ingredient implements Serializable {
    private float quantity;
    private String measure;
    private String ingredient;

    public Ingredient (float quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public float getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }
}
