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
    List<Song> mockSongs;
    Song songA;
    Song songZ;

    @Before
    public void setup() {
        songPersistence = mock(SongPersistence.class);
        accessSong = new AccessSong(songPersistence);
        mockSongs = new ArrayList<>();

        songA = new Song.Builder()
                .withSongID(1)
                .withSongName("A Song")
                .withSongTime(177)
                .withAlbumID(1)
                .withAlbumName("A Album")
                .withArtistID(1)
                .withArtistName("A Artist")
                .withTrackNumber(1)
                .withSongData("a_song.mp3")
                .build();
        songZ = new Song.Builder()
                .withSongID(2)
                .withSongName("Z Song")
                .withSongTime(178)
                .withAlbumID(2)
                .withAlbumName("Z Album")
                .withArtistID(2)
                .withArtistName("Z Artist")
                .withTrackNumber(1)
                .withSongData("z_song.mp3")
                .build();
        mockSongs.add(songA);
        mockSongs.add(songZ);
    }

    @Test
    public void getSongsSortedTrackNameTest() {
        final List<Song> songs;

        System.out.println("\nStarting test getSongsSortedTrackNameTest");
        when(songPersistence.getSongsSortedTrackName()).thenReturn((mockSongs));

        songs = accessSong.getSongsSortedTrackName();
        assertNotNull("The list of songs should not be null", songs);
        assertEquals("'A Song' was supposed to be the first in the list",
                "A Song", songs.get(0).getSongName());
        assertEquals("'Z Song' was supposed to be the second in the list",
                "Z Song", songs.get(1).getSongName());

        verify(songPersistence).getSongsSortedTrackName();

        System.out.println("Finished test getSongsSortedTrackNameTest");
    }

    @Test
    public void getSongsSortedArtistTest() {
        final List<Song> songs;

        System.out.println("\nStarting test getSongsSortedArtistTest");
        when(songPersistence.getSongsSortedArtist()).thenReturn((mockSongs));

        songs = accessSong.getSongsSortedArtist();
        assertNotNull("The list of songs should not be null", songs);
        assertEquals("'A Artist' was supposed to be the first in the list",
                "A Artist", songs.get(0).getArtistName());
        assertEquals("'Z Artist' was supposed to be the second in the list",
                "Z Artist", songs.get(1).getArtistName());

        verify(songPersistence).getSongsSortedArtist();

        System.out.println("Finished test getSongsSortedArtistTest");
    }

    @Test
    public void getPlaylistSongsTest() {
        final List<Song> songs;

        System.out.println("\nStarting test getPlaylistSongsTest");
        when(songPersistence.getPlaylistSongs(1)).thenReturn((mockSongs));

        songs = accessSong.getPlaylistSongs(1);
        assertNotNull("The list of songs should not be null", songs);
        assertEquals("'A Song' was supposed to be the first in the list",
                "A Song", songs.get(0).getSongName());
        assertEquals("'Z Song' was supposed to be the second in the list",
                "Z Song", songs.get(1).getSongName());

        verify(songPersistence).getPlaylistSongs(1);

        System.out.println("Finished test getPlaylistSongsTest");
    }
}
