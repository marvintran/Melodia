package com.example.melodia.Objects;
import org.junit.Test;
import comp3350.melodia.objects.Song;
import static org.junit.Assert.*;

public class SongTest {
    @Test
    public void songTest() {

        System.out.println("\nStarting songTest");

        Song song = new Song.Builder()
                .withSongID(1)
                .withSongName("1234")
                .withSongTime(222)
                .withAlbumID(12)
                .withAlbumName("album")
                .withArtistID(2)
                .withArtistName("artist")
                .withTrackNumber(3)
                .build();

        assertEquals(1, song.getSongID());
        assertNotNull(song);
        assertEquals("1234", (song.getSongName()));
        assertEquals(222, song.getSongTime());
        assertEquals(2, song.getArtistID());
        assertEquals("artist", song.getArtistName());
        assertEquals(12, song.getAlbumID());
        assertEquals("album", song.getAlbumName());
        assertEquals(3,  song.getTrackNumber());

        System.out.println("Finished songTest");

    }
}
