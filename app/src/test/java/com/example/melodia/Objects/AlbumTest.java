package com.example.melodia.Objects;

import org.junit.Test;
import java.util.ArrayList;

import comp3350.melodia.objects.Album;
import comp3350.melodia.objects.Genre;
import comp3350.melodia.objects.Song;

import static org.junit.Assert.*;

public class AlbumTest
{
    @Test
    public void AlbumTest1()
    {
        Album album;

        System.out.println("\nStarting AlbumTest");

        album = new Album("AlbumName", new ArrayList<Song>(), new Genre("Pop"));
        assertNotNull(album);
        assertEquals("AlbumName", (album.getAlbumName()));
        assertNotNull(album.getAlbumGenre());
        assertNotNull(album.getSongs());

        System.out.println("Finished AlbumTest");
    }
}

