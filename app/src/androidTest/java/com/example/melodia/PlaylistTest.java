package com.example.melodia;

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
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class PlaylistTest {
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void createPlaylist() {
        onView(withId(R.id.playlistNav)).perform(click());

        // create the playlist
        onView(withId(R.id.new_playlist)).perform(click());
        onView(withId(R.id.playlist_title)).perform(typeText("Awesome Playlist"));
        onView(withText("Create")).perform(click());

        // verify that it was added
        onView(withText("Awesome Playlist")).check(matches(isDisplayed()));

        // go to library and add song to the playlist
        onView(withId(R.id.homeNav)).perform(click());


    }
}