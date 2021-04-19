package edu.quinnipiac.gadacy.recipeapp;

import android.content.Context;
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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class RecipeDetails extends Fragment {
    private TextView instructions, ingredients, recipe;
    NavController navController = null;
    private String clickedRecipe;
    private CurrentRecipeListener mainActivity;

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


                instructions = view.findViewById(R.id.recipe_details_instructions);
                ingredients = view.findViewById(R.id.recipe_details_ingredients);
                ingredients.setText("Ingredients: " + ingredientsText);
                instructions.setText("Instructions: " + instructionsText);
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
}