package comp3350.melodia.persistance;

import java.util.List;
import comp3350.melodia.objects.Artist;

public interface ArtistPersistence {

    List<Artist> getAllArtists();

    Artist insertArtist(Artist currentPlaylist);

    Artist updateArtist(Artist currentPlaylist);

    void deleteArtist(Artist currentPlaylist);

}