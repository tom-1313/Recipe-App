package edu.quinnipiac.gadacy.recipeapp;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.test.espresso.internal.inject.InstrumentationContext;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

//Tests the database for creating new recipes and ingredients
@RunWith(AndroidJUnit4.class)
public class RecipeDataSourceTest {
    private SQLiteDatabase db;
    private RecipeDataSource ds;

    @Before
    public void setUp() throws Exception {
        InstrumentationRegistry.getInstrumentation().getTargetContext().deleteDatabase(SQLHelper.DATABASE_NAME);
        ds = new RecipeDataSource(InstrumentationRegistry.getInstrumentation().getTargetContext());
        ds.open();
        db = ds.getDatabase();
    }

    @After
    public void tearDown() throws Exception {
        ds.close();
    }

    @Test
    public void createRecipe() {
        ds.createRecipe("Cheese Pizza", "Dough, cheese, tomatoe sauce", "Combine ingredients and put in the oven for 10 minutes");
        List<Recipe> rList = ds.getAllRecipes();
        assertEquals(rList.size(), 1);
        Log.i("test recipe", rList.get(0).getRecipe());
        assertTrue(rList.get(0).getRecipe().equals("Cheese Pizza"));
    }

    @Test
    public void createIngredient() {
        ds.createIngredient("Cheese", "1");
        List<Ingredient> iList = ds.getAllIngredients();
        assertEquals(iList.size(), 1);
        Log.i("test ingredient", iList.get(0).getIngredient());
        assertTrue(iList.get(0).getIngredient().equals("Cheese"));
    }

    @Test
    public void clearIngredients() {
        ds.createIngredient("Cheese", "1");
        ds.createIngredient("Apple", "1");
        ds.clearIngredients();
        List<Ingredient> iList = ds.getAllIngredients();
        assertEquals(iList.size(), 0);
    }

    @Test
    public void clearRecipes() {
        ds.createRecipe("Cheese Pizza", "Dough, cheese, tomatoe sauce", "Combine ingredients and put in the oven for 10 minutes");
        ds.createRecipe("Apple", "Dough, cheese, tomatoe sauce", "Combine ingredients and put in the oven for 10 minutes");
        ds.clearRecipes();
        List<Recipe> rList = ds.getAllRecipes();
        assertEquals(rList.size(), 0);
    }

    @Test
    public void containsIngredient() {
        assertTrue(ds.containsIngredient("cheese, and pizza", "cheese"));
    }

    @Test
    public void getFilteredRecipes() {
        ds.clearRecipes();
        ds.clearIngredients();
        ds.createRecipe("Cheese Pizza", "Dough, cheese, tomatoe sauce", "Combine ingredients and put in the oven for 10 minutes");
        ds.createRecipe("Apple", "apple", "Eat apple");
        ds.createIngredient("Cheese", "1");
        ds.createIngredient("Apple", "1");
        List<Recipe> recipes = ds.getFilteredRecipes();
        assertTrue(recipes.get(0).getRecipe().equals("Cheese Pizza"));

    }
}