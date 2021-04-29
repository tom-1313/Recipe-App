package edu.quinnipiac.gadacy.recipeapp;
/**
 Thomas Gadacy
 Professor Ruby ElKharboutly
 Recipe App
 **/

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

//This fragment displays the enter EnterRecipe screen
public class EnterRecipe extends Fragment implements View.OnClickListener {
    NavController navController = null;
    private RecipeDataSource dataSource;
    private EditText recipeName, ingredients, instructions;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        view.findViewById(R.id.add_recipe_button).setOnClickListener(this);

        recipeName = view.findViewById(R.id.recipe_name);
        ingredients = view.findViewById(R.id.recipe_ingredients);
        instructions = view.findViewById(R.id.recipe_instructions);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_enter_recipe, container, false);
    }

    @Override
    public void onClick(View v) {
        dataSource = new RecipeDataSource(getActivity());
        dataSource.open();
        dataSource.createRecipe(recipeName.getText().toString(), ingredients.getText().toString(), instructions.getText().toString());
        dataSource.close();

        Toast.makeText(getActivity(), "Recipe Added!", Toast.LENGTH_SHORT).show();
        navController.navigate(R.id.action_enterRecipe_to_homeScreen);
    }
}