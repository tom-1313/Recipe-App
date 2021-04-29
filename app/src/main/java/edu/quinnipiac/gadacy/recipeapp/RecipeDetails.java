package edu.quinnipiac.gadacy.recipeapp;
/**
 * Thomas Gadacy
 * Professor Ruby ElKharboutly
 * Recipe App
 **/

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

//This fragment is responsible for displaying the recipe details
public class RecipeDetails extends Fragment implements View.OnClickListener {
    private TextView instructions, ingredients, recipe;
    NavController navController = null;
    private String clickedRecipe;
    private CurrentRecipeListener mainActivity;
    private boolean noneMissing;

    public interface CurrentRecipeListener {
        void currentRecipe(String recipe, String ingredients, String instructions);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity = (CurrentRecipeListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clickedRecipe = getArguments().getString("recipe");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recipe = view.findViewById(R.id.recipe_details_recipe);
        navController = Navigation.findNavController(view);
        view.findViewById(R.id.recipe_details_button).setOnClickListener(this);
        recipe.setText(clickedRecipe);

        //Search the database for the recipe
        SQLiteOpenHelper recipeDatabaseHelper = new SQLHelper(getActivity());
        try {
            SQLiteDatabase db = recipeDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query(SQLHelper.TABLE_RECIPE,
                    new String[]{SQLHelper.COLUMN_RECIPE, SQLHelper.COLUMN_INGREDIENTS, SQLHelper.COLUMN_INSTRUCTIONS},
                    SQLHelper.COLUMN_RECIPE + " = ? ", new String[]{clickedRecipe},
                    null, null, null);

            if (cursor.moveToFirst()) {
                String ingredientsText = cursor.getString(1);
                String instructionsText = cursor.getString(2);

                //Set the TextViews in fragment_recipe_details to the proper instructions/ingredients
                instructions = view.findViewById(R.id.recipe_details_instructions);
                ingredients = view.findViewById(R.id.recipe_details_ingredients);
                ingredients.setText(ingredientsText);
                instructions.setText(instructionsText);
            }
            cursor.close();
        } catch (SQLiteException e) {
            Toast.makeText(getActivity(), "ERROR: DATABASE UNAVAILABLE", Toast.LENGTH_SHORT).show();
        }
        mainActivity.currentRecipe(recipe.getText().toString(), ingredients.getText().toString(), instructions.getText().toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_details, container, false);
    }


    //Method that sends the user the ingredients they are missing
    @Override
    public void onClick(View v) {
        RecipeDataSource dataSource = new RecipeDataSource(getActivity());
        dataSource.open();
        List<Ingredient> userIngredients = dataSource.getAllIngredients();
        dataSource.close();

        StringBuilder sb = new StringBuilder("Missing Ingredients:\n");
        String[] splitIngredients = ingredients.getText().toString().split(", ");
        noneMissing = true;

        ArrayList<String> userIngred = new ArrayList<>();
        for (int i = 0; i < userIngredients.size(); i++) {
            Log.i("add", "Adding: " + userIngredients.get(i).getIngredient().toLowerCase());
            userIngred.add(userIngredients.get(i).getIngredient().toLowerCase());
        }

        //For all the recipe's ingredients, check to see if the user has the ingredient
        if (userIngredients.size() > 0) {   //if the user has ingredients check to see what they are missing
            for (int i = 0; i < splitIngredients.length; i++) {
                Log.i("contains", "Status: " + userIngred.contains(splitIngredients[i].toLowerCase()));
                if (!userIngred.contains(splitIngredients[i].toLowerCase())) {
                    sb.append(splitIngredients[i] + "\n");
                    noneMissing = false;
                }
            }
        } else if (userIngredients.size() == 0) {    //if the user has no ingredients, add all recipe ingredients
            for (int i = 0; i < splitIngredients.length; i++) {
                sb.append(splitIngredients[i] + "\n");
            }
            noneMissing = false;
        }

        if (noneMissing) {
            Toast.makeText(getActivity(), "You have everything you need!", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, sb.toString());
            startActivity(intent);
        }
    }
}