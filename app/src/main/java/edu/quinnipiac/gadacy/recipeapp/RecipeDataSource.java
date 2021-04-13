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


    public RecipeDataSource(Context context) {
        dbHelper = new SQLHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }

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

    private Recipe cursorToRecipe(Cursor cursor) {
        Recipe recipe = new Recipe();
        recipe.setRecipe(cursor.getString(0));
        recipe.setIngredients(cursor.getString(1));
        recipe.setInstructions(cursor.getString(2));
        return recipe;
    }

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

    public void close(){
        dbHelper.close();
    }

}
