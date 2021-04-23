package edu.quinnipiac.gadacy.recipeapp;

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

//Fragment that allows the user to enter ingredients
public class EnterIngredient extends Fragment implements View.OnClickListener {
    private EditText name, quantity;
    NavController navController = null;
    private RecipeDataSource dataSource;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        view.findViewById(R.id.add_ingredient_button).setOnClickListener(this);
        name = view.findViewById(R.id.add_ingredientName_editText);
        quantity = view.findViewById(R.id.add_quantity_editText);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_enter_ingredient, container, false);
    }

    //Adds the ingredient to the database after the user clicks the button
    public void onClick(View view) {
        dataSource = new RecipeDataSource(getActivity());
        dataSource.open();
        dataSource.createIngredient(name.getText().toString(), quantity.getText().toString());
        dataSource.close();
        Toast.makeText(getActivity(), "Ingredient Added!", Toast.LENGTH_SHORT).show();
        navController.navigate(R.id.action_enterIngredient_to_homeScreen);
    }
}