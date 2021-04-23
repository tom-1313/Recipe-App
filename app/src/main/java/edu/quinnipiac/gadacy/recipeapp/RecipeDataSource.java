package edu.quinnipiac.gadacy.recipeapp;
/**
 Thomas Gadacy & Sadjell Mamon
 Professor Ruby ElKharboutly
 Recipe App Iteration 1
 **/

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

//This class allows access to the database to modify and retrieve data from it.
public class RecipeDataSource {

    private SQLiteDatabase database;
    private SQLHelper dbHelper;
    private String[] recipeColumns = {SQLHelper.COLUMN_RECIPE, SQLHelper.COLUMN_INGREDIENTS, SQLHelper.COLUMN_INSTRUCTIONS};
    private String[] ingredientColumns = {SQLHelper.COLUMN_NAME, SQLHelper.COLUMN_QUANTITY};


    public RecipeDataSource(Context context) {
        dbHelper = new SQLHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }


    //Creates and Recipe and adds it to the Recipe table
    public Recipe createRecipe(String recipe, String ingredients, String instructions) {
        ContentValues values = new ContentValues();
        values.put(SQLHelper.COLUMN_RECIPE, recipe);
        values.put(SQLHelper.COLUMN_INGREDIENTS, ingredients);
        values.put(SQLHelper.COLUMN_INSTRUCTIONS, instructions);
        database.insert(SQLHelper.TABLE_RECIPE, null, values);
        Cursor cursor = database.query(SQLHelper.TABLE_RECIPE, recipeColumns, null, null, null, null, null);
        cursor.moveToFirst();
        Recipe newRecipe = cursorToRecipe(cursor);
        cursor.close();
        return newRecipe;

    }

    //Creates a Recipe object from the recipe table
    private Recipe cursorToRecipe(Cursor cursor) {
        Recipe recipe = new Recipe();
        recipe.setRecipe(cursor.getString(0));
        recipe.setIngredients(cursor.getString(1));
        recipe.setInstructions(cursor.getString(2));
        return recipe;
    }

    //Returns all the recipes in the recipe table
    public List<Recipe> getAllRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        Cursor cursor = database.query(SQLHelper.TABLE_RECIPE, null, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Recipe recipe = cursorToRecipe(cursor);
            recipes.add(recipe);
            cursor.moveToNext();
        }
        cursor.close();
        return recipes;
    }

    //Creates and Ingredient and adds it to the ingredient table
    public Ingredient createIngredient(String name, String quantity) {
        ContentValues values = new ContentValues();
        values.put(SQLHelper.COLUMN_NAME, name);
        values.put(SQLHelper.COLUMN_QUANTITY, quantity);
        database.insert(SQLHelper.TABLE_INGREDIENT, null, values);
        Cursor cursor = database.query(SQLHelper.TABLE_INGREDIENT, ingredientColumns, null, null, null, null, null);
        cursor.moveToFirst();
        Ingredient newIngredient = cursorToIngredient(cursor);
        cursor.close();
        return newIngredient;
    }

    //Creates a Ingredient object from the ingredient table
    public Ingredient cursorToIngredient(Cursor cursor) {
        Ingredient ingredient = new Ingredient();
        ingredient.setIngredient(cursor.getString(0));
        ingredient.setQuantity(cursor.getString(1));
        return ingredient;
    }

    //Returns all the ingredients in the ingredients table
    public List<Ingredient> getAllIngredients() {
        List<Ingredient> ingredients = new ArrayList<>();
        Cursor cursor = database.query(SQLHelper.TABLE_INGREDIENT, null, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Ingredient ingredient = cursorToIngredient(cursor);
            ingredients.add(ingredient);
            cursor.moveToNext();
        }
        cursor.close();
        return ingredients;

    }
    public void close(){
        dbHelper.close();
    }

}

