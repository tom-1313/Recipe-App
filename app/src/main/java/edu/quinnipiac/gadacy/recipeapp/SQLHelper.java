package edu.quinnipiac.gadacy.recipeapp;
/**
 Thomas Gadacy
 Professor Ruby ElKharboutly
 Recipe App
 **/

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//This class creates the database with two tables.
public class SQLHelper extends SQLiteOpenHelper {
    public static final String TABLE_INGREDIENT = "ingredient";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_QUANTITY = "quantity";

    public static final String TABLE_RECIPE = "recipe";
    public static final String COLUMN_RECIPE = "recipe";
    public static final String COLUMN_INGREDIENTS = "ingredients";
    public static final String COLUMN_INSTRUCTIONS = "instructions";

    private static final String RECIPE_TABLE_CREATE = "create table "
            + TABLE_RECIPE + "(" + COLUMN_RECIPE
            + " text not null primary key, " + COLUMN_INGREDIENTS
            + " text not null, " + COLUMN_INSTRUCTIONS
            + " text not null);";

    private static final String INGREDIENT_TABLE_CREATE = "create table "
            + TABLE_INGREDIENT + "(" + COLUMN_NAME
            + " text not null primary key, " + COLUMN_QUANTITY
            + " text not null);";

    public static final String DATABASE_NAME = "recipes.db";
    private static final int DATABASE_VERSION = 1;

    public SQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(RECIPE_TABLE_CREATE);
        db.execSQL(INGREDIENT_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE);
        onCreate(db);
    }
}
