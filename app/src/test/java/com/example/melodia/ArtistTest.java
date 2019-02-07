package com.example.melodia;

import org.junit.Test;
import java.util.ArrayList;

import comp3350.melodia.objects.Album;
import comp3350.melodia.objects.Artist;


import static org.junit.Assert.*;

public class ArtistTest
{
    @Test
    public void ArtistTest1()
    {
        Artist artist;

        System.out.println("\nStarting ArtistTest");

        artist = new Artist("Dean", new ArrayList<Album>());
        assertNotNull(artist);
        assertEquals("Dean", (artist.getArtistName()));
        assertNotNull(artist.getAlbums());

        System.out.println("Finished AlbumTest");
    }
}
