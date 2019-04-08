package com.example.melodia;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import comp3350.melodia.R;
import comp3350.melodia.presentation.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LibraryManagementTest {
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void changeSorting() {
        // Go to the library and filter by artist.
        onView(withId(R.id.homeNav)).perform(click());
        onView(withId(R.id.spinner)).perform(click());
        onView(withText("Artist")).perform(click());

        // Verify songs are sorted by artist.
        onView(withId(R.id.library_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
        MainActivity activity = activityRule.getActivity();
        onView(withText("Queued Summer")).
                inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).
                check(matches(isDisplayed()));

        onView(withId(R.id.library_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(4, click()));
        onView(withText("Queued Night Owl")).
                inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).
                check(matches(isDisplayed()));

        // Filter by track name.
        onView(withId(R.id.spinner)).perform(click());
        onView(withText("Track Name")).perform(click());

        // Verify songs are sorted by track name.
        onView(withId(R.id.library_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withText("Queued All that")).
                inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).
                check(matches(isDisplayed()));

        onView(withId(R.id.library_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        onView(withText("Queued Broke Reality")).
                inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).
                check(matches(isDisplayed()));
    }
}
