package com.example.melodia.business;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import comp3350.melodia.persistence.PlaylistPersistence;
import comp3350.melodia.persistence.hsqldb.PlaylistPersistenceHSQLDB;
import comp3350.melodia.logic.AccessPlaylist;
import comp3350.melodia.objects.Playlist;
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
    public void getPlaylistsIT() {
        final List<Playlist> allPlaylists;

        System.out.println("\nStarting test getPlaylistsIT");
        allPlaylists = accessPlaylist.getPlaylists();
        assertNotNull("The list of playlists should not be null.", allPlaylists);

        boolean sawQueuePlaylist = false;
        int count = 0;
        while(!sawQueuePlaylist && count < allPlaylists.size())
        {
            Playlist currentPlaylist = allPlaylists.get(count);
            if(currentPlaylist.getPlaylistID() == 0) {
                sawQueuePlaylist = true;
            }
            count++;
        }
        assertFalse("We should not get the playlist queue when we get all playlists",
                sawQueuePlaylist);

        System.out.println("Finished test getPlaylistsIT");
    }

    @Test
    public void getSpecificPlaylistIT() {
        final Playlist playlistQueue;
        final Playlist playlistOne;

        System.out.println("\nStarting test getSpecificPlaylistIT");

        playlistQueue = accessPlaylist.getSpecificPlaylist(0);
        assertNotNull("The queue playlist should exist", playlistQueue);
        assertEquals("queue", playlistQueue.getPlaylistName());

        playlistOne = accessPlaylist.getSpecificPlaylist(1);
        assertNotNull("Playlist 1 should exist", playlistOne);
        assertEquals("Playlist 1", playlistOne.getPlaylistName());

        System.out.println("Finished test getSpecificPlaylistIT");
    }

    @Test
    public void insertPlaylistIT() {
        List<Playlist> allPlaylists;
        int sizeBeforeInsert;
        int sizeAfterInsert;

        String playlistTwoName = "Playlist 2";

        System.out.println("\nStarting test insertPlaylistIT");
        allPlaylists = accessPlaylist.getPlaylists();
        sizeBeforeInsert = allPlaylists.size();
        assertEquals(1, sizeBeforeInsert);

        accessPlaylist.insertPlaylist(playlistTwoName);
        allPlaylists = accessPlaylist.getPlaylists();
        sizeAfterInsert = allPlaylists.size();
        assertEquals("The size of allPlaylists should have increased by 1",
                sizeBeforeInsert+1, sizeAfterInsert);

        System.out.println("Finished test insertPlaylistIT");
    }

    @Test
    public void deletePlaylistIT() {
        List<Playlist> allPlaylists;
        int playlistOneID = 1;
        int sizeBeforeInsert;
        int sizeAfterInsert;

        System.out.println("\nStarting test deletePlaylistIT");
        allPlaylists = accessPlaylist.getPlaylists();
        sizeBeforeInsert = allPlaylists.size();
        assertEquals(1, sizeBeforeInsert);

        accessPlaylist.deletePlaylist(playlistOneID);
        allPlaylists = accessPlaylist.getPlaylists();
        sizeAfterInsert = allPlaylists.size();
        assertEquals("We should have removed the only playlist: Playlist 1",
                sizeBeforeInsert-1, sizeAfterInsert);

        System.out.println("Finished test deletePlaylistIT");
    }

    @After
    public void tearDown() {
        this.tempDB.delete();
    }

}

