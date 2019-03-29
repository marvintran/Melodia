package Objects;

import org.junit.Test;
import java.util.ArrayList;

import comp3350.melodia.objects.Album;
import comp3350.melodia.objects.Genre;
import comp3350.melodia.objects.Song;

import static org.junit.Assert.*;

public class AlbumTest {
    @Test
    public void AlbumTest1() {
        Album album;

        System.out.println("\nStarting AlbumTest");

        //hardcoded album for tests
        album = new Album(1, "AlbumName", new ArrayList<Song>(), new Genre("Pop"));

        //
        assertNotNull(album);
        assertNotNull(album.getAlbumID());
        assertEquals("AlbumName", (album.getAlbumName()));
        assertNotNull(album.getAlbumGenre());
        assertNotNull(album.getSongs());

        System.out.println("Finished AlbumTest");
    }
}

