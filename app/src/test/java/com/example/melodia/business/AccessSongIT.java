package com.example.melodia.business;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import comp3350.melodia.persistence.SongPersistence;
import comp3350.melodia.persistence.hsqldb.SongPersistenceHSQLDB;
import comp3350.melodia.logic.AccessSong;
import comp3350.melodia.objects.Song;
import com.example.melodia.Util.TestUtil;
import java.io.File;
import java.io.IOException;

public class AccessSongIT {
    private AccessSong accessSong;
    private File tempDB;

    @Before
    public void setUp() throws IOException {
            this.tempDB = TestUtil.copyDB();
            final SongPersistence persistence = new SongPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
            this.accessSong = new AccessSong(persistence);
    }

    @Test
    public void getSongsSortedTrackNameIT() {
        String sortedSongOneName = "All that";
        String sortedSongTwoName = "dance";

        final List<Song> allSongs;
        String songOneName;
        String songTwoName;

        System.out.println("\nStarting test getSongsSortedTrackNameIT");
        allSongs = accessSong.getSongsSortedTrackName();

        songOneName = allSongs.get(0).getSongName();
        assertEquals("Song 'All that' should be first",
                sortedSongOneName, songOneName);

        songTwoName = allSongs.get(1).getSongName();
        assertEquals("Song 'dance' should be second",
                sortedSongTwoName, songTwoName);

        System.out.println("Finished test getSongsSortedTrackNameIT");
    }

    @Test
    public void getSongsSortedArtistIT() {
        String sortedArtistOneName = "Benjamin Tissot";
        String sortedArtistTwoName = "Benjamin Tissot";

        final List<Song> allSongs;
        String artistOneName;
        String artistTwoName;

        System.out.println("\nStarting test getSongsSortedArtistIT");
        allSongs = accessSong.getSongsSortedArtist();

        artistOneName = allSongs.get(0).getArtistName();
        assertEquals("Artist 'Benjamin Tissot' should be first",
                sortedArtistOneName, artistOneName);

        artistTwoName = allSongs.get(1).getArtistName();
        assertEquals("Artist 'Benjamin Tissot; should be second",
                sortedArtistTwoName, artistTwoName);

        System.out.println("Finished test getSongsSortedArtistIT");
    }

    @Test
    public void getPlaylistSongsIT() {
        String songPositionOne = "All that";
        String songPositionTwo = "dance";

        final List<Song> playlistOneSongs;
        String playlistSongPositionOne;
        String playlistSongPositionTwo;

        System.out.println("\nStarting test getPlaylistSongsIT");
        playlistOneSongs = accessSong.getPlaylistSongs(1);

        playlistSongPositionOne = playlistOneSongs.get(0).getSongName();
        assertEquals("Song 'All that' should be first",
                songPositionOne, playlistSongPositionOne);

        playlistSongPositionTwo = playlistOneSongs.get(1).getSongName();
        assertEquals("Song 'dance' should be second",
                songPositionTwo, playlistSongPositionTwo);

        System.out.println("Finished test getPlaylistSongsIT");
    }

    @Test
    public void insertPlaylistSongIT() {
        List<Song> playlistOneSongs;
        int sizeBeforeInsert;
        int sizeAfterInsert;

        String songNameAdded;

        System.out.println("\nStarting test insertPlaylistSongIT");
        playlistOneSongs = accessSong.getPlaylistSongs(1);
        sizeBeforeInsert = playlistOneSongs.size();
        assertEquals(2, sizeBeforeInsert);

        accessSong.insertPlaylistSong(1, 1, playlistOneSongs.size());
        playlistOneSongs = accessSong.getPlaylistSongs(1);
        sizeAfterInsert = playlistOneSongs.size();
        assertEquals("The size of Playlist 1 should have increased by 1",
                        sizeBeforeInsert+1, sizeAfterInsert);

        // The song at the last position added is at index sizeAfterInsert - 1.
        songNameAdded = playlistOneSongs.get(sizeAfterInsert - 1).getSongName();
        assertEquals("The song added should be 'All that'",
                songNameAdded, "All that");

        System.out.println("Finished test insertPlaylistSongIT");
    }

    @Test
    public void deletePlaylistSongIT() {
        List<Song> playlistOneSongs;
        int sizeBeforeDelete;
        int sizeAfterDelete;

        String songNameRemaining;

        System.out.println("\nStarting test deletePlaylistSongIT");
        playlistOneSongs = accessSong.getPlaylistSongs(1);
        sizeBeforeDelete = playlistOneSongs.size();
        assertEquals(2, sizeBeforeDelete);

        // The song "All that" is at position 0.
        accessSong.deletePlaylistSong(1, 0);
        playlistOneSongs = accessSong.getPlaylistSongs(1);
        sizeAfterDelete = playlistOneSongs.size();
        assertEquals("The size of Playlist 1 should have decreased by 1",
                sizeBeforeDelete-1, sizeAfterDelete);

        // The song at the last position is at index sizeAfterInsert - 1.
        songNameRemaining = playlistOneSongs.get(sizeAfterDelete - 1).getSongName();
        assertEquals("The song remaining after delete should be 'dance'",
                songNameRemaining, "dance");

        System.out.println("Finished test deletePlaylistSongIT");
    }

    @Test
    public void replaceQueueWithPlaylistIT() {
        List<Song> queueSongs = accessSong.getPlaylistSongs(0);
        List<Song> songsReplacingQueue = accessSong.getPlaylistSongs(1);

        int queueSizeBefore;
        int queueSizeAfter;

        System.out.println("\nStarting test replaceQueueWithPlaylistIT");
        queueSizeBefore = queueSongs.size();
        assertEquals(0, queueSizeBefore);

        accessSong.replaceQueueWithPlaylist(songsReplacingQueue, queueSongs);
        queueSongs = accessSong.getPlaylistSongs(0);
        queueSizeAfter = queueSongs.size();
        assertEquals("The queue size should have changed",
                queueSizeAfter, songsReplacingQueue.size());

        assertEquals("The first song in the queue should be 'All that'",
                        queueSongs.get(0).getSongName(), songsReplacingQueue.get(0).getSongName());
        assertEquals("The second song in the queue should be 'dance'",
                queueSongs.get(1).getSongName(), songsReplacingQueue.get(1).getSongName());

        System.out.println("Finished test replaceQueueWithPlaylistIT");
    }

    @Test
    public void updateOrderIT() {
        List<Song> playlistOneSongs = accessSong.getPlaylistSongs(1);
        List<Song> newOrderPlaylistOne;

        System.out.println("\nStarting test updateOrderIT");
        accessSong.updateOrder(1, 0,1);
        newOrderPlaylistOne = accessSong.getPlaylistSongs(1);
        assertEquals("Changing song order should not have changed playlist length",
                playlistOneSongs.size(), newOrderPlaylistOne.size());

        assertEquals("Song 'All that' should have moved to position 1",
                playlistOneSongs.get(0).getSongName(), newOrderPlaylistOne.get(1).getSongName());
        assertEquals("Song 'dance' should have moved to position 0",
                playlistOneSongs.get(1).getSongName(), newOrderPlaylistOne.get(0).getSongName());

        System.out.println("Finished test updateOrderIT");
    }

    @After
    public void tearDown() {
        this.tempDB.delete();
    }
}
