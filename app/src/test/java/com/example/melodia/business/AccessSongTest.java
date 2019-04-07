package com.example.melodia.business;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import comp3350.melodia.logic.AccessSong;
import comp3350.melodia.objects.Song;
import comp3350.melodia.persistence.SongPersistence;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AccessSongTest {
    private AccessSong accessSong;
    private SongPersistence songPersistence;

    @Before
    public void setup(){
        songPersistence = mock(SongPersistence.class);
        accessSong = new AccessSong(songPersistence);
    }

    @Test
    public void accessSongTest(){
        final List<Song> songs;

        System.out.println("\nStarting test AccessSong");
        List<Song> mockSongs = new ArrayList<>();
        Song song1 = new Song.Builder()
                .withSongID(1)
                .withSongName("All that")
                .withSongTime(177)
                .withAlbumID(1)
                .withAlbumName("bensound")
                .withArtistID(1)
                .withArtistName("Benjamin Tissot")
                .withTrackNumber(3)
                .withSongData("bensound_allthat.mp3")
                .build();
        mockSongs.add(song1);
        when(songPersistence.getSongsSortedTrackName()).thenReturn((mockSongs));

        songs = accessSong.getSongsSortedTrackName();
        assertNotNull("The list of songs should not be null", songs);
        assertEquals("All that", songs.get(0).getSongName());

        verify(songPersistence).getSongsSortedTrackName();

        System.out.println("Finished test AccessSong");
    }

}
