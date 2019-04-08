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
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ViewMusicInfoTest {
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void viewMusicInfo() {
        // Go to the library and queue a song.
        onView(withId(R.id.homeNav)).perform(click());
        onView(withId(R.id.library_recycler_view))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText("Ghost Dance")), click()));

        // Go to the queue.
        onView(withId(R.id.songNav)).perform(click());
        onView(withText("QUEUE")).check(matches(isDisplayed()));
        onView(withId(R.id.queue_button)).perform(click());
        onView(withText("BACK TO PLAYER")).check(matches(isDisplayed()));

        // Check if the song is in the queue.
        onView(withId(R.id.queue_recycler_view))
                .check(matches(hasDescendant(withText("Ghost Dance"))));

        // Play the song.
        onView(withId(R.id.queue_recycler_view))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText("Ghost Dance")), click()));

        // Check if the song info has been displayed.
        onView(withId(R.id.player)).perform(click());
        onView(withId(R.id.textSongName)).check(matches(withText("Ghost Dance")));
        onView(withId(R.id.textArtist)).check(matches(withText("Kevin MacLeod")));
        onView(withId(R.id.textSongTime)).check(matches(withText("00 : 01 : 13")));
    }
}
