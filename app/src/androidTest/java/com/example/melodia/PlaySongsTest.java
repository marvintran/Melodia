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
public class PlaySongsTest {
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void addToQueuePlaySong() {
        // Go to the library and queue a song.
        onView(withId(R.id.homeNav)).perform(click());
        onView(withId(R.id.library_recycler_view))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText("Night Owl")), click()));

        // Go to the queue.
        onView(withId(R.id.songNav)).perform(click());
        onView(withText("QUEUE")).check(matches(isDisplayed()));
        onView(withId(R.id.queue_button)).perform(click());
        onView(withText("BACK TO PLAYER")).check(matches(isDisplayed()));

        // Check if the song is in the queue.
        onView(withId(R.id.queue_recycler_view))
                .check(matches(hasDescendant(withText("Night Owl"))));

        // Play the song.
        onView(withId(R.id.queue_recycler_view))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText("Night Owl")), click()));

        // Check if the song is playing on the player screen.
        onView(withId(R.id.player)).perform(click());
        onView(withId(R.id.textSongName)).check(matches(withText("Night Owl")));
    }

    @Test
    public void playSongsFromPlaylist() {
        // Go a playlist and click on a song.
        onView(withId(R.id.playlistNav)).perform(click());
        onView(withId(R.id.playlist_recycler_view))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText("Playlist 1")), click()));
        onView(withId(R.id.playlist_songs_recycler_view))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText("Dance")), click()));

        // Verify that we moved to the playing page.
        onView(withText("QUEUE")).check(matches(isDisplayed()));

        // Verify that the song we clicked is playing.
        onView(withId(R.id.textSongName)).check(matches(withText("Dance")));
    }

    @Test
    public void playNextPrevSong() {
        // Go to player screen.
        onView(withId(R.id.songNav)).perform(click());

        // Play the next song in the queue and verify it's playing.
        onView(withId(R.id.buttonNext)).perform(click());
        onView(withId(R.id.textSongName)).check(matches(withText("Night Owl")));

        // Play the previous song in the queue and verify it's playing.
        onView(withId(R.id.buttonPrev)).perform(click());
        onView(withId(R.id.textSongName)).check(matches(withText("Dance")));

    }
}
