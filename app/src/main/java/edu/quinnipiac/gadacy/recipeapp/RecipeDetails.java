package edu.quinnipiac.gadacy.recipeapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RecipeDetails extends Fragment {
    private TextView instructions, ingredients, recipe;
    NavController navController = null;
    private RecipeDataSource dataSource;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        instructions = view.findViewById(R.id.recipe_instructions);
        ingredients = view.findViewById(R.id.recipe_ingredients);
        recipe = view.findViewById(R.id.recipe_details_recipe);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_details, container, false);
    }
}