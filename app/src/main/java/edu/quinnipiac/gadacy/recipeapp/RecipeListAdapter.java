package edu.quinnipiac.gadacy.recipeapp;
/**
 Thomas Gadacy & Sadjell Mamon
 Professor Ruby ElKharboutly
 Recipe App Iteration 1
 **/

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

//This class is where the adapter for the recycleview is created
public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {

    private final LayoutInflater mInflater;
    private final LinkedList<String> mRecipeList;
    private Context context;
    private Activity activity;

    public RecipeListAdapter(LinkedList<String> mRecipeList, Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.mRecipeList = mRecipeList;
        this.context = context;
        this.activity = (Activity) context;
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

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
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
            String element = mRecipeList.get(mPosition);
            //navigate to RecipeDetails
            Toast.makeText(activity, "Recipe Details", Toast.LENGTH_SHORT).show();
        }
    }
}
