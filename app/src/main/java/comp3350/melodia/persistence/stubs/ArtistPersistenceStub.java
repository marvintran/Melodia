package comp3350.melodia.persistence.stubs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comp3350.melodia.objects.Album;
import comp3350.melodia.objects.Artist;
import comp3350.melodia.persistence.ArtistPersistence;

public class ArtistPersistenceStub implements ArtistPersistence {
    private List<Artist> artists;

    public ArtistPersistenceStub() {
        this.artists = new ArrayList<>();

        artists.add(0, new Artist("Dean", new ArrayList<Album>()));
        artists.add(1, new Artist("Nico Touches the Wall", new ArrayList<Album>()));
        artists.add(2, new Artist("epik high", new ArrayList<Album>()));
    }

    @Override
    public List<Artist> getAllArtists() {
        return Collections.unmodifiableList(artists);
    }

    @Override
    public Artist insertArtist(Artist currentArtist) {
        artists.add(currentArtist);
        return currentArtist;
    }

    @Override
    public Artist updateArtist(Artist currentArtist) {
        int index = artists.indexOf(currentArtist);

        if (index >= 0) {
            artists.set(index, currentArtist);
        }
        return currentArtist;
    }

    @Override
    public void deleteArtist(Artist currentArtist) {
        int index = artists.indexOf(currentArtist);

        if (index >= 0) {
            artists.remove(index);
        }
    }
}

