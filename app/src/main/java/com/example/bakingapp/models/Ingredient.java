package com.example.bakingapp.models;

import java.io.Serializable;

public class Ingredient implements Serializable {
    private int quantity;
    private String measure;
    private String ingredients;

    public Ingredient (int quantity, String measure, String ingredients) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredients = ingredients;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredients() {
        return ingredients;
    }
}
