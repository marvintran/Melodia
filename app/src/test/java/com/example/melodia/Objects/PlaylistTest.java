package com.example.melodia.Objects;
import org.junit.Test;
import comp3350.melodia.objects.Playlist;
import static org.junit.Assert.*;

public class PlaylistTest{
    @Test
    public void playlistTest() {
        Playlist playlist;

        System.out.println("\nStarting playlistTest");

        playlist = new Playlist(1, "Playlist5",  8);

        assertNotNull(playlist);
        assertNotNull(playlist.getPlaylistID());
        assertEquals("Playlist5", (playlist.getPlaylistName()));
        assertEquals(8, playlist.getNumberOfSongs());

        System.out.println("Finished playlistTest");
    }
}
