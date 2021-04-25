package edu.quinnipiac.gadacy.recipeapp;
/**
 * Thomas Gadacy
 * Professor Ruby ElKharboutly
 * Recipe App
 **/

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

//This class is where the adapter for the recycleview is created
public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {

    private final LayoutInflater mInflater;
    private final LinkedList<String> mRecipeList;
    private final LinkedList<Ingredient> mIngredientList;
    private FindRecipe findRecipe;

    public RecipeListAdapter(LinkedList<String> mRecipeList, Context context, FindRecipe findRecipe, LinkedList<Ingredient> mIngredientList) {
        this.mInflater = LayoutInflater.from(context);
        this.mRecipeList = mRecipeList;
        this.findRecipe = findRecipe;
        this.mIngredientList = mIngredientList;

    }

    @NonNull
    @Override
    public RecipeListAdapter.RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView;
        if (findRecipe == null) {
            mItemView = mInflater.inflate(R.layout.ingredient_list_item, parent, false);
        } else {
            mItemView = mInflater.inflate(R.layout.list_item, parent, false);
        }
        return new RecipeViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        if (findRecipe != null) {
            String mCurr = mRecipeList.get(position);
            holder.recipeItemView.setText(mCurr);
        } else {
            String mCurrIngred = mIngredientList.get(position).getIngredient();
            String mCurrQuantity = mIngredientList.get(position).getQuantity();
            holder.ingredientItemView.setText(mCurrIngred);
            holder.quantityItemView.setText(mCurrQuantity);
        }
    }

    @Override
    public int getItemCount() {
        if (findRecipe != null) {
            return mRecipeList.size();
        } else {
            return mIngredientList.size();
        }
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final RecipeListAdapter mAdapter;
        public TextView recipeItemView, ingredientItemView, quantityItemView;

        public RecipeViewHolder(@NonNull View itemView, RecipeListAdapter recipeListAdapter) {
            super(itemView);
            mAdapter = recipeListAdapter;
            if (findRecipe != null) {
                recipeItemView = itemView.findViewById(R.id.list_item);
            } else {
                ingredientItemView = itemView.findViewById(R.id.list_ingredient);
                quantityItemView = itemView.findViewById(R.id.list_quantity);
            }
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (findRecipe != null) {
                int mPosition = getLayoutPosition();
                String recipe = mRecipeList.get(mPosition);
                findRecipe.navigateToDetails(recipe);
            }
        }
    }
}
