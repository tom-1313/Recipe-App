package edu.quinnipiac.gadacy.recipeapp;
/**
 Thomas Gadacy & Sadjell Mamon
 Professor Ruby ElKharboutly
 Recipe App Iteration 1
 **/

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

//This is the MainActivity where the container for the fragments is
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, RecipeDetails.CurrentRecipeListener {
    DrawerLayout drawerLayout;
    NavController navController;
    NavigationView navigationView;
    Toolbar toolbar;
    public String currentRecipe;
    public String currentInstructions;
    public String currentIngredients;
    private ShareActionProvider provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, drawerLayout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        provider = (ShareActionProvider) MenuItemCompat.getActionProvider((MenuItem) menu.findItem(R.id.action_share));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_share:
                if (currentRecipe == null) {
                    Toast.makeText(this, "You have not viewed a recipe to share yet!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, "Recipe: " + currentRecipe + "\nIngredients: " + currentIngredients + "\nInstructions: " + currentInstructions);
                    provider.setShareIntent(intent);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_settings:
                //TODO: navigate to the settings screen
                Toast.makeText(this, "This is the setting button in navigation!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_about:
                navController.navigate(R.id.action_homeScreen_to_about);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void currentRecipe(String recipe, String ingredients, String instructions) {
        //Share the recipe
        this.currentRecipe = recipe;
        this.currentIngredients = ingredients;
        this.currentInstructions = instructions;
    }
}