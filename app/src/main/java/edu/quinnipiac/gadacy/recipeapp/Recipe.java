package edu.quinnipiac.gadacy.recipeapp;
/**
 Thomas Gadacy & Sadjell Mamon
 Professor Ruby ElKharboutly
 Recipe App Iteration 1
 **/

//This class holds information about a recipe
public class Recipe {
    private String recipe, ingredients, instructions;

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

}
