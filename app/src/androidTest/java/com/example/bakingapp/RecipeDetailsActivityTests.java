package com.example.bakingapp;

import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import com.example.bakingapp.ui.MainActivity;
import com.example.bakingapp.utilities.DisplayUtils;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4ClassRunner.class)
public class RecipeDetailsActivityTests {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickIngredientListItem_checkIngredientsListIsDisplayed() {

        if (DisplayUtils.isTablet(activityTestRule.getActivity())) {
            onView(withId(R.id.rv_recipe_list)).perform(actionOnItemAtPosition(0, click()));
            ViewInteraction recyclerView2 = onView(
                    allOf(withId(R.id.fragment_step_list),
                            childAtPosition(
                                    withClassName(is("android.widget.LinearLayout")),
                                    0)));
            recyclerView2.perform(actionOnItemAtPosition(0, click()));

            ViewInteraction recyclerView3 = onView(
                    allOf(withId(R.id.rv_ingredients_list),
                            childAtPosition(
                                    withClassName(is("android.widget.FrameLayout")),
                                    0)));
            recyclerView3.perform(actionOnItemAtPosition(0, click()));
        }
        else {
            ViewInteraction recyclerView = onView(
                    allOf(withId(R.id.rv_recipe_list),
                            childAtPosition(withId(android.R.id.content), 0)));
            recyclerView.perform(actionOnItemAtPosition(0, click()));

            ViewInteraction recyclerView2 = onView(
                    allOf(withId(R.id.fragment_step_list),
                            childAtPosition(
                                    withId(android.R.id.content),
                                    0)));
            recyclerView2.perform(actionOnItemAtPosition(0, click()));

            ViewInteraction recyclerView3 = onView(
                    allOf(withId(R.id.rv_ingredients_list),
                            childAtPosition(
                                    allOf(withId(R.id.fl_recipe_details_container),
                                            childAtPosition(
                                                    withId(android.R.id.content),
                                                    0)),
                                    0),
                            isDisplayed()));
            recyclerView3.check(matches(isDisplayed()));
        }
    }

    @Test
    public void clickStepListItem_checkStepIsDisplayed() {

        if (DisplayUtils.isTablet(activityTestRule.getActivity())) {
            onView(withId(R.id.rv_recipe_list)).perform(actionOnItemAtPosition(0, click()));
            ViewInteraction recyclerView2 = onView(
                    allOf(withId(R.id.fragment_step_list),
                            childAtPosition(
                                    withClassName(is("android.widget.LinearLayout")),
                                    0)));
            recyclerView2.perform(actionOnItemAtPosition(1, click()));

//            SystemClock.sleep(2000);
            onView(withId(R.id.tv_step_description)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        }
        else {
            ViewInteraction recyclerView = onView(
                    allOf(withId(R.id.rv_recipe_list),
                            childAtPosition(withId(android.R.id.content), 0)));
            recyclerView.perform(actionOnItemAtPosition(0, click()));

            ViewInteraction recyclerView2 = onView(
                    allOf(withId(R.id.fragment_step_list),
                            childAtPosition(
                                    withId(android.R.id.content),
                                    0)));
            recyclerView2.perform(actionOnItemAtPosition(1, click()));

            ViewInteraction textView = onView(
                    allOf(childAtPosition(
                                    allOf(withId(R.id.fl_recipe_details_container),
                                            childAtPosition(
                                                    withId(android.R.id.content),
                                                    0)),
                                    0),
                            isDisplayed()));
            textView.check(matches(isDisplayed()));
        }
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
