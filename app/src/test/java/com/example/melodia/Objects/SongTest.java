package com.example.melodia.Objects;

import org.junit.Test;

import java.util.ArrayList;

import comp3350.melodia.objects.Album;
import comp3350.melodia.objects.Artist;
import comp3350.melodia.objects.Song;
import static org.junit.Assert.*;

public class SongTest
{
    @Test
    public void SongTest1()
    {
        Song song;

        System.out.println("\nStarting SongTest");

        song = new Song(
                "1234",
                222,
                new Artist(
                        "asdf",
                        new ArrayList<Album>()),
                new Album(
                        "adfs",
                        new ArrayList<Song>()),
                3,
                "");

        assertNotNull(song);
        assertEquals("1234", (song.getSongName()));
        assertEquals(222, song.getSongTime());
        assertNotNull(song.getArtist());
        assertNotNull(song.getAlbum());
        assertEquals(3,  song.getTrackNumber());

        System.out.println("Finished SongTest");

    }
}
