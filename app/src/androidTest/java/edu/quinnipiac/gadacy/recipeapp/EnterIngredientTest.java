package edu.quinnipiac.gadacy.recipeapp;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class EnterIngredientTest {

    @Test
    public void enterIngredientTest() {

        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);

        //Click on the add ingredient image
        onView(withId(R.id.add_ingredient_image)).perform(click());

        //Check that the fragment being displayed is EnterIngredient
        onView(withId(R.id.fragment_enter_ingredient)).check(matches(isDisplayed()));

        //Enter in the information
        onView(withId(R.id.add_ingredientName_editText)).perform(typeText("Bacon"), closeSoftKeyboard());
        onView(withId(R.id.add_quantity_editText)).perform(typeText("1"), closeSoftKeyboard());
        onView(withId(R.id.add_ingredient_button)).perform(click());

        //Check that the fragment being displayed is the HomeScreen
        onView(withId(R.id.fragment_homeScreen)).check(matches(isDisplayed()));

    }

}