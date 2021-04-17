package edu.quinnipiac.gadacy.recipeapp;
/**
Thomas Gadacy & Sadjell Mamon
 Professor Ruby ElKharboutly
 Recipe App Iteration 1
 **/

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

//This class is where a list of recipes is displayed in a recycleview for the user
public class FindRecipe extends Fragment {
    private RecyclerView mRecycleView;
    private LinkedList<String> mRecipeList = new LinkedList<>();
    private RecipeListAdapter mAdapter;

    NavController navController = null;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        View view = inflater.inflate(R.layout.fragment_find_recipe, container, false);
        //get the database and add recipes to the linkedlist
        RecipeDataSource dataSource = new RecipeDataSource(getActivity());
        dataSource.open();
        List<Recipe> recipes = dataSource.getAllRecipes();
        for (int i = 0; i < recipes.size(); i++) {
            mRecipeList.addLast(recipes.get(i).getRecipe());
        }
        //method required to get all of the recipes names
        mRecycleView = view.findViewById(R.id.recyclerView);
        mAdapter = new RecipeListAdapter(mRecipeList, getContext(), navController, this);
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    public void navigateToDetails(String clickedRecipe) {
        Bundle bundle = new Bundle();
        bundle.putString("recipe", clickedRecipe);
        navController.navigate(R.id.action_findRecipe_to_recipeDetails, bundle);
    }
}