package edu.quinnipiac.gadacy.recipeapp;
/**
 Thomas Gadacy
 Professor Ruby ElKharboutly
 Recipe App
 **/

//This class holds information about an ingredient
public class Ingredient {
    private String quantity;
    private String ingredient;

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

}
