package com.example.melodia.business;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import comp3350.melodia.persistence.PlaylistPersistence;
import comp3350.melodia.persistence.hsqldb.PlaylistPersistenceHSQLDB;
import comp3350.melodia.logic.AccessPlaylist;
import comp3350.melodia.objects.Playlist;
import comp3350.melodia.objects.Song;
import com.example.melodia.Util.TestUtil;
import java.io.File;
import java.io.IOException;

public class AccessPlaylistIT {
    private AccessPlaylist accessPlaylist;
    private File tempDB;

    @Before
    public void setUp() throws IOException {
        this.tempDB = TestUtil.copyDB();
        final PlaylistPersistence persistence = new PlaylistPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        this.accessPlaylist = new AccessPlaylist(persistence);
    }

    @Test
    public void testGetPlaylists() {
        accessPlaylist.insertPlaylist("playlistName");
        accessPlaylist.insertPlaylist("name");
        accessPlaylist.insertPlaylist("test");

        assertEquals(3, accessPlaylist.getPlaylists().size());

        System.out.println("Finished test AccessPlaylist");


    }

    @Test
    public void testInsertPlaylist() {
        final Playlist p = new Playlist(4,"newName", 0 );
        accessPlaylist.insertPlaylist(p.getPlaylistName());

        assertEquals("newName", accessPlaylist.getSpecificPlaylist(1).getPlaylistName());
    }

    @Test
    public void testGetSpecificPlaylist() {
        accessPlaylist.insertPlaylist("playlistName");

        assertEquals("playlistName", accessPlaylist.getSpecificPlaylist(1).getPlaylistName());
    }

    @Test
    public void testDeletePlaylist() {
        accessPlaylist.insertPlaylist("newPlaylist");
        accessPlaylist.insertPlaylist("test");
        accessPlaylist.insertPlaylist("deletePlaylist");
        accessPlaylist.deletePlaylist(1);

        assertEquals(2, accessPlaylist.getPlaylists().size());
    }

    @Test
    public void testInsertPlaylistSong() {
        final Playlist p = new Playlist(1,"newName", 0 );

        accessPlaylist.insertPlaylist(p.getPlaylistName());
        accessPlaylist.insertPlaylistSong(1, 1, 0);

        assertEquals(1,accessPlaylist.getSpecificPlaylist(1).getNumberOfSongs());
    }

    @Test
    public void testDeletePlaylistSong() {
        final Playlist p = new Playlist(1,"newName", 0 );

        accessPlaylist.insertPlaylist(p.getPlaylistName());
        accessPlaylist.insertPlaylistSong(1, 1, 0);
        accessPlaylist.deletePlaylistSong(1, 0);

        assertEquals(0,accessPlaylist.getSpecificPlaylist(1).getNumberOfSongs());
    }

    @Test
    public void testReplaceQueueWithPlaylist() {
        List<Song> newQueueSongs = new ArrayList<>();
        List<Song> queueSongs = new ArrayList<>();
        final Playlist p = new Playlist(1,"newName", 0 );


        newQueueSongs.add(0, new Song.Builder()
                .withSongID(1)
                .withSongName("All that")
                .withSongTime(177)
                .withAlbumID(1)
                .withAlbumName("bensound")
                .withArtistID(1)
                .withArtistName("Benjamin Tissot")
                .withTrackNumber(3)
                .withSongData("bensound_allthat.mp3")
                .build());
        newQueueSongs.add(1, new Song.Builder()
                .withSongID(2)
                .withSongName("dance")
                .withSongTime(175)
                .withAlbumID(1)
                .withAlbumName("bensound")
                .withArtistID(1)
                .withArtistName("Benjamin Tissot")
                .withTrackNumber(3)
                .withSongData("bensound_dance.mp3")
                .build());
        newQueueSongs.add(2, new Song.Builder()
                .withSongID(2)
                .withSongName("dance")
                .withSongTime(175)
                .withAlbumID(1)
                .withAlbumName("bensound")
                .withArtistID(1)
                .withArtistName("Benjamin Tissot")
                .withTrackNumber(3)
                .withSongData("bensound_dance.mp3")
                .build());

        queueSongs.add(0, new Song.Builder()
                .withSongID(2)
                .withSongName("dance")
                .withSongTime(175)
                .withAlbumID(1)
                .withAlbumName("bensound")
                .withArtistID(1)
                .withArtistName("Benjamin Tissot")
                .withTrackNumber(3)
                .withSongData("bensound_dance.mp3")
                .build());
        queueSongs.add(1, new Song.Builder()
                .withSongID(1)
                .withSongName("All that")
                .withSongTime(177)
                .withAlbumID(1)
                .withAlbumName("bensound")
                .withArtistID(1)
                .withArtistName("Benjamin Tissot")
                .withTrackNumber(3)
                .withSongData("bensound_allthat.mp3")
                .build());

        accessPlaylist.insertPlaylist(p.getPlaylistName());
        accessPlaylist.insertPlaylistSong(p.getPlaylistID(),1, 0);
        accessPlaylist.insertPlaylistSong(p.getPlaylistID(), 2,1);
        accessPlaylist.replaceQueueWithPlaylist(newQueueSongs, queueSongs);

        assertEquals(2,accessPlaylist.getSpecificPlaylist(p.getPlaylistID()).getNumberOfSongs());
    }

    @Test
    public void testUpdateOrder() {
        final Playlist p = new Playlist(1,"newName", 0 );
        List<Song> songs = new ArrayList<>();

        accessPlaylist.insertPlaylist(p.getPlaylistName());
        accessPlaylist.insertPlaylistSong(p.getPlaylistID(), 1, 0);
        accessPlaylist.insertPlaylistSong(p.getPlaylistID(),2, 1);
        accessPlaylist.updateOrder(p.getPlaylistID(), 0,1);

        assertNotNull(accessPlaylist.getSpecificPlaylist(1));
    }

    @After
    public void tearDown() {
        // resets the database
        this.tempDB.delete();
    }

}

