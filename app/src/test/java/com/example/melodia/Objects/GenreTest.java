package com.example.melodia.Objects;

import org.junit.Test;

import comp3350.melodia.objects.Genre;

import static org.junit.Assert.*;

public class GenreTest {
    @Test
    public void GenreTest1() {
        Genre genre;

        System.out.println("\nStarting GenreTest");

        //added a genre for tests
        genre = new Genre("Pop");

        //asserts
        assertNotNull(genre);
        assertEquals("Pop", genre.getGenreName());

        System.out.println("Finished GenreTest");
    }
}
