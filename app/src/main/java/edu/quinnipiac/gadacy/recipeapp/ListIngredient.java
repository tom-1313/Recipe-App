package edu.quinnipiac.gadacy.recipeapp;
/**
 * Thomas Gadacy
 * Professor Ruby ElKharboutly
 * Recipe App
 **/

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

//This class creates a list of ingredients the user has
public class ListIngredient extends Fragment {
    private RecyclerView mRecycleView;
    private LinkedList<Ingredient> mIngredientList = new LinkedList<>();
    private RecipeListAdapter mAdapter;
    private RecipeDataSource dataSource;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        View view = inflater.inflate(R.layout.fragment_list_ingredient, container, false);

        dataSource = new RecipeDataSource(getActivity());
        dataSource.open();
        mIngredientList.clear();
        List<Ingredient> ingredients = dataSource.getAllIngredients();
        for (int i = 0; i < ingredients.size(); i++) {
            mIngredientList.addLast(ingredients.get(i));
        }
        dataSource.close();

        mRecycleView = view.findViewById(R.id.recyclerView_ingredient);
        mAdapter = new RecipeListAdapter(null, getContext(), null, mIngredientList);
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }
}