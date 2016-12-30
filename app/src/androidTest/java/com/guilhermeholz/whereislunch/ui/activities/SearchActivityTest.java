package com.guilhermeholz.whereislunch.ui.activities;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.guilhermeholz.whereislunch.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.Matchers.allOf;

/*
 * Not working yet, need to figure out the whole rxjava + espresso thing,
 * probably something with idling resources.
 */
@RunWith(AndroidJUnit4.class)
public class SearchActivityTest {

    @Rule
    public ActivityTestRule<SearchActivity> mActivityTestRule = new ActivityTestRule<>(SearchActivity.class);

    @Test
    public void searchActivityTest() {
        ViewInteraction recyclerView = onView(allOf(withId(R.id.restaurantsList),
                withParent(allOf(withId(R.id.activity_main),
                        withParent(withId(android.R.id.content)))), isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(0, click()));
        pressBack();
    }
}
