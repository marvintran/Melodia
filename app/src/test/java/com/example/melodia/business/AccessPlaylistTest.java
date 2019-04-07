package com.example.melodia.business;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import comp3350.melodia.logic.AccessPlaylist;
import comp3350.melodia.objects.Playlist;
import comp3350.melodia.persistence.PlaylistPersistence;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AccessPlaylistTest {

    private AccessPlaylist accessPlaylist;
    private PlaylistPersistence playlistPersistence;

    @Before
    public void setup() {
        playlistPersistence = mock(PlaylistPersistence.class);
        accessPlaylist = new AccessPlaylist(playlistPersistence);
    }

    @Test
    public void test1()
    {
        final List<Playlist> playlists;

        System.out.println("\nStarting test AccessPlaylistTest");
        List<Playlist> mockPlaylists = new ArrayList<>();
        Playlist playlist1 = new Playlist(2, "Playlist 1", 0);
        mockPlaylists.add(playlist1);
        when(playlistPersistence.getAllPlaylists()).thenReturn((mockPlaylists));

        playlists = accessPlaylist.getPlaylists();
        assertNotNull("The list of playlists should not be null", playlists);
        assertTrue(playlists.get(0).getPlaylistName().equals("Playlist 1"));

        verify(playlistPersistence).getAllPlaylists();

        System.out.println("Finished test AccessPlaylist");
    }
}