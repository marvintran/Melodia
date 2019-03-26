package Objects;

import org.junit.Test;
import java.util.ArrayList;

import comp3350.melodia.objects.Album;
import comp3350.melodia.objects.Artist;

import static org.junit.Assert.*;

public class ArtistTest {
    @Test
    public void ArtistTest1() {
        Artist artist;

        System.out.println("\nStarting ArtistTest");

        //hardcoded an artist for test
        artist = new Artist(1,"Dean", new ArrayList<Album>());

        //asserts check if information is there
        assertNotNull(artist);
        assertNotNull(artist.getArtistID());
        assertEquals("Dean", (artist.getArtistName()));
        assertNotNull(artist.getAlbums());

        System.out.println("Finished AlbumTest");
    }
}
