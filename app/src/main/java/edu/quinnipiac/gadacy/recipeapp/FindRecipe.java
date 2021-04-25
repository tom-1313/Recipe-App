package edu.quinnipiac.gadacy.recipeapp;
/**
 * Thomas Gadacy
 * Professor Ruby ElKharboutly
 * Recipe App Iteration 1
 **/

import android.content.Context;
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
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

//This class is where a list of recipes is displayed in a recycleview for the user
public class FindRecipe extends Fragment {
    private RecyclerView mRecycleView;
    private LinkedList<String> mRecipeList = new LinkedList<>();
    private RecipeListAdapter mAdapter;
    private SwitchStatus mainActivity;
    private RecipeDataSource dataSource;
    NavController navController = null;

    public interface SwitchStatus {
        boolean switchStatus();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity = (SwitchStatus) context;
    }

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
        dataSource = new RecipeDataSource(getActivity());
        dataSource.open();

        mRecipeList.clear();
        if (mainActivity.switchStatus()) {
            Toast.makeText(getActivity(), "Filtered", Toast.LENGTH_SHORT).show();
            getFiltered();
        } else {
            Toast.makeText(getActivity(), "Unfiltered", Toast.LENGTH_SHORT).show();
            getUnfiltered();
        }
        dataSource.close();
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

    public void getFiltered() {
        List<Recipe> recipes = dataSource.getFilteredRecipes();
        for (int i = 0; i < recipes.size(); i++) {
            mRecipeList.addLast(recipes.get(i).getRecipe());
        }
    }

    public void getUnfiltered() {
        List<Recipe> recipes = dataSource.getAllRecipes();
        for (int i = 0; i < recipes.size(); i++) {
            mRecipeList.addLast(recipes.get(i).getRecipe());
        }
    }
}