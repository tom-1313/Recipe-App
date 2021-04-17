package edu.quinnipiac.gadacy.recipeapp;
/**
 Thomas Gadacy & Sadjell Mamon
 Professor Ruby ElKharboutly
 Recipe App Iteration 1
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
    private Context context;
    private Activity activity;
    private NavController navController = null;
    private FindRecipe findRecipe;

    public RecipeListAdapter(LinkedList<String> mRecipeList, Context context, NavController navController, FindRecipe findRecipe) {
        this.mInflater = LayoutInflater.from(context);
        this.mRecipeList = mRecipeList;
        this.context = context;
        this.activity = (Activity) context;
        this.navController = navController;
        this.findRecipe = findRecipe;

    }

    @NonNull
    @Override
    public RecipeListAdapter.RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.list_item, parent, false);
        return new RecipeViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        String mCurr = mRecipeList.get(position);
        holder.recipeItemView.setText(mCurr);
    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final RecipeListAdapter mAdapter;
        public TextView recipeItemView;

        public RecipeViewHolder(@NonNull View itemView, RecipeListAdapter recipeListAdapter) {
            super(itemView);
            mAdapter = recipeListAdapter;
            recipeItemView = itemView.findViewById(R.id.list_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int mPosition = getLayoutPosition();
            String recipe = mRecipeList.get(mPosition);
            findRecipe.navigateToDetails(recipe);
            Toast.makeText(activity, recipe, Toast.LENGTH_SHORT).show();
        }
    }
}
