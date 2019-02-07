package com.example.melodia;

import org.junit.Test;

import comp3350.melodia.objects.Genre;

import static org.junit.Assert.*;

public class GenreTest
{
    @Test
    public void GenreTest1()
    {
        Genre genre;

        System.out.println("\nStarting GenreTest");

        genre = new Genre("Pop");
        assertNotNull(genre);
        assertEquals("Pop", (genre.getGenreName()));

        System.out.println("Finished GenreTest");
    }
}
