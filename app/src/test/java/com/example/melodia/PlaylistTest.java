package com.example.melodia;

import org.junit.Test;
import java.util.ArrayList;

import comp3350.melodia.objects.Song;
import comp3350.melodia.objects.Playlist;


import static org.junit.Assert.*;

public class PlaylistTest
{
    @Test
    public void PlaylistTest1()
    {
        Playlist playlist;

        System.out.println("\nStarting PlaylistTest");

        playlist = new Playlist("Playlist5", 2000, 8, new ArrayList<Song>(),"");
        assertNotNull(playlist);
        assertEquals("Playlist5", (playlist.getPlaylistName()));
        assertEquals(2000, playlist.getPlaylistTime());
        assertEquals(8, playlist.getNumberOfSongs());
        assertNotNull(playlist.getSongs());

        System.out.println("Finished PlaylistTest");
    }
}
