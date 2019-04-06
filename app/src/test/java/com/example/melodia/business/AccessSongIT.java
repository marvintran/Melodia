package com.example.melodia.business;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

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
    public void testGetSongsSortedTrackName() {
        List<Song> songs = accessSong.getSongsSortedTrackName();
        assertNotNull(songs);

        System.out.println("Finished test AccessSong");

    }

    @Test
    public void testGetPlaylistSongs() {
        List<Song> songs = accessSong.getPlaylistSongs(0);
        assertNotNull(songs);
    }

    @Test
    public void testGetSongsSortedArtist() {
        List<Song> songs = accessSong.getSongsSortedArtist();
        assertNotNull(songs);
    }

    @After
    public void tearDown() {
        // resets the database
        this.tempDB.delete();
    }
}
