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
    public void getPlaylistsTest() {
        final List<Playlist> playlists;

        System.out.println("\nStarting test getPlaylistsTest");
        List<Playlist> mockPlaylists = new ArrayList<>();
        Playlist playlist1 = new Playlist(1, "Playlist 1", 0);
        mockPlaylists.add(playlist1);
        when(playlistPersistence.getAllPlaylists()).thenReturn((mockPlaylists));

        playlists = accessPlaylist.getPlaylists();
        assertNotNull("The list of playlists should not be null", playlists);
        assertTrue("Playlist 1".equals(playlists.get(0).getPlaylistName()));

        verify(playlistPersistence).getAllPlaylists();

        System.out.println("Finished test getPlaylistsTest");
    }

    @Test
    public void getSpecificPlaylist() {
        final Playlist playlist;

        System.out.println("\nStarting test getSpecificPlaylist");
        Playlist mockPlaylist = new Playlist(1, "Playlist 1", 0);
        when(playlistPersistence.getSpecificPlaylist(1)).thenReturn((mockPlaylist));

        playlist = accessPlaylist.getSpecificPlaylist(1);
        assertNotNull("The returned playlist should not be null", playlist);
        assertTrue("Playlist 1".equals(playlist.getPlaylistName()));

        verify(playlistPersistence).getSpecificPlaylist(1);

        System.out.println("Finished test getSpecificPlaylist");
    }
}