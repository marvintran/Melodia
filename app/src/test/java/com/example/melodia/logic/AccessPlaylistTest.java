package com.example.melodia.logic;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import comp3350.melodia.logic.AccessPlaylist;
import comp3350.melodia.objects.Playlist;
import comp3350.melodia.persistence.stubs.PlaylistPersistenceStub;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;

public class AccessPlaylistTest {

    private AccessPlaylist accessPlaylist;
    @Before
    public void setUp() {
        accessPlaylist = new AccessPlaylist(new PlaylistPersistenceStub());
    }

    @Test
    public void test1()
    {
        List<Playlist> playlists = accessPlaylist.getPlaylists();

        System.out.println("\nStarting test AccessPlaylistTest");

        assertNotNull("The list of playlists should not be null", playlists);
        assertTrue(playlists.get(0).getPlaylistName().equals("Playlist 1"));

        System.out.println("Finished test AccessPlaylist");
    }
}
