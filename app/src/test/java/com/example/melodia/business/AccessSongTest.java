package com.example.melodia.business;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import comp3350.melodia.logic.AccessSong;
import comp3350.melodia.objects.Song;
import com.example.melodia.Persistence.SongPersistenceStub;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

public class AccessSongTest {
    private AccessSong accessSong;

    @Before
    public void setUp(){
        accessSong = new AccessSong(new SongPersistenceStub());
    }

    @Test
    public void accessSongTest(){
        List<Song> songs = accessSong.getSongsSortedTrackName();
        System.out.println("\nStarting test AccessSong");
        assertNotNull("The list of songs should not be null", songs);
        assertEquals("All that", songs.get(0).getSongName());
        System.out.println("Finished test AccessSong");
    }

}
