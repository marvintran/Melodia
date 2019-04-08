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
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class PlaylistManagementTest {
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void addSongToPlaylist() {

        // Create the playlist.
        onView(withId(R.id.playlistNav)).perform(click());
        onView(withId(R.id.new_playlist)).perform(click());
        onView(withId(R.id.playlist_title))
                .perform(typeText("Playlist 2"));
        onView(withText("Create")).perform(click());

        // Verify that it was added.
        onView(withId(R.id.playlist_recycler_view))
                .check(matches(hasDescendant(withText("Playlist 2"))));

        // Go to library and add song to the playlist.
        onView(withId(R.id.homeNav)).perform(click());
        onView(withId(R.id.library_recycler_view))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText("All that")), longClick()));
        onView(withText("Add to Playlist")).perform(click());
        onView(withText("Playlist 2")).perform(click());

        // Verify that it was added.
        onView(withId(R.id.playlistNav)).perform(click());
        onView(withId(R.id.playlist_recycler_view))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText("Playlist 2")), click()));
        onView(withId(R.id.library_recycler_view))
                .check(matches(hasDescendant(withText("All that"))));
    }

    @Test
    public void removeSongsFromPlaylist() {

        // Delete a song from Playlist 1.
        onView(withId(R.id.playlistNav)).perform(click());
        onView(withId(R.id.playlist_recycler_view))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText("Playlist 1")), click()));
        onView(withId(R.id.playlist_songs_recycler_view))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText("All that")), longClick()));
        onView(withText("Remove from Playlist")).perform(click());


        // Verify that it was removed.
        onView(withId(R.id.playlist_songs_recycler_view))
                .check(matches(not(hasDescendant(withText("All that")))));
    }
}