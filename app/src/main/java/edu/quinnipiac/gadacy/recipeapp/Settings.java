package edu.quinnipiac.gadacy.recipeapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

//This fragment contains the settings for the app
public class Settings extends Fragment implements View.OnClickListener {
    private RecipeDataSource dataSource;
    private Switch ingredientSearch;
    private SwitchListener mainActivty;

    //This interface allows the MainActivity to keep track of the switch's current state (on/off)
    public interface SwitchListener {
        void setSwitch(boolean state);

        boolean getSwitch();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivty = (SwitchListener) context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataSource = new RecipeDataSource(getActivity());
        view.findViewById(R.id.clear_ingredients_button).setOnClickListener(this);
        view.findViewById(R.id.clear_recipes_button).setOnClickListener(this);

        ingredientSearch = view.findViewById(R.id.search_by_ingredients_switch);
        ingredientSearch.setOnClickListener(this);

        if (mainActivty.getSwitch()) {
            ingredientSearch.setChecked(true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        dataSource.open();
        switch (id) {
            case R.id.clear_ingredients_button:
                dataSource.clearIngredients();
                break;
            case R.id.clear_recipes_button:
                dataSource.clearRecipes();
                break;
            case R.id.search_by_ingredients_switch:
                if (mainActivty.getSwitch()) {
                    mainActivty.setSwitch(false);
                } else {
                    mainActivty.setSwitch(true);
                }
                break;
        }
        dataSource.close();
    }
}