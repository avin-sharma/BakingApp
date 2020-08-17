package com.example.bakingapp;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import com.example.bakingapp.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;

@RunWith(AndroidJUnit4ClassRunner.class)
public class MainActivityBasicTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickRecipeListItem_checkRecipeActivityIsLaunched() {
        onView(withId(R.id.rv_recipe_list)).perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.fragment_step_list)).check(matches(isDisplayed()));
    }
}
