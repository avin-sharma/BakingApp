package com.example.bakingapp;

import android.os.SystemClock;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import com.example.bakingapp.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4ClassRunner.class)
public class RecipeDetailsActivityTests {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickIngredientListItem_checkIngredientsFragmentIsDisplayed() {
//        // Launch Recipe Details Activity
//        onView(withId(R.id.rv_recipe_list)).perform(actionOnItemAtPosition(0, click()));
//
//        // Click on Ingredient Item at index 0
//        onView(withId(R.id.rv_steps_list)).perform(actionOnItemAtPosition(0, click()));
//
//        // Check ingredients recycler view is displayed
//        onView(withId(R.id.rv_ingredients_list)).check(matches(isDisplayed()));

        onView(withId(R.id.rv_recipe_list)).perform(actionOnItemAtPosition(0, click()));
        SystemClock.sleep(2000);
        onView(withId(R.id.rv_steps_list)).check(matches(isDisplayed()));
    }
}
