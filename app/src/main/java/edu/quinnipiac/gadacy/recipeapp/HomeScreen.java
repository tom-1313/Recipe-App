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
import android.widget.ImageView;
import android.widget.Toast;

//This class displays the Home screen of the app where the user is presented with three options to choose from
public class HomeScreen extends Fragment implements View.OnClickListener {
    NavController navController = null;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.add_recipe_image).setOnClickListener(this);
        view.findViewById(R.id.find_recipe_image).setOnClickListener(this);
        view.findViewById(R.id.add_ingredient_image).setOnClickListener(this);
        navController = Navigation.findNavController(view);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_screen, container, false);
    }

    //When the user clicks an image on the Home Screen it brings them to the specified fragment
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_recipe_image:
                navController.navigate(R.id.action_homeScreen_to_enterRecipe2);
                break;
            case R.id.find_recipe_image:
                navController.navigate(R.id.action_homeScreen_to_findRecipe);
                break;
            case R.id.add_ingredient_image:
                navController.navigate(R.id.action_homeScreen_to_enterIngredient);
                break;

        }
    }
}