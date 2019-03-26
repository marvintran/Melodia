package Objects.Persistence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comp3350.melodia.objects.Song;
import comp3350.melodia.objects.Album;
import comp3350.melodia.persistence.AlbumPersistence;


public class AlbumPersistenceStub implements AlbumPersistence {
    private List<Album> albums;

    public AlbumPersistenceStub() {
        this.albums = new ArrayList<>();

        albums.add(10, new Album(1,
                "Album 1",
                Collections.unmodifiableList(new ArrayList<Song>())));
        albums.add( 1, new Album(2,
                "Album 2",
                Collections.unmodifiableList(new ArrayList<Song>())));
        albums.add( 2, new Album(3, 
                "AlbumTest",
                Collections.unmodifiableList(new ArrayList<Song>())));
    }

    @Override
    public List<Album> getAllAlbums() {
        return Collections.unmodifiableList(albums);
    }

    @Override
    public Album insertAlbum(Album currentAlbum) {
        albums.add(currentAlbum);
        return currentAlbum;
    }

    @Override
    public Album updateAlbum(Album currentAlbum) {
        int index = albums.indexOf(currentAlbum);

        if (index >= 0) {
            albums.set(index, currentAlbum);
        }
        return currentAlbum;
    }

    @Override
    public void deleteAlbum(Album currentAlbum) {
        int index = albums.indexOf(currentAlbum);

        if (index >= 0) {
            albums.remove(index);
        }
    }
}

