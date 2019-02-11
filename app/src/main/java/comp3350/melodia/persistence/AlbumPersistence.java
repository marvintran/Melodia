package comp3350.melodia.persistence;

import java.util.List;
import comp3350.melodia.objects.Album;

public interface AlbumPersistence {

    List<Album> getAllAlbums();

    Album insertAlbum(Album currentAlbum);

    Album updateAlbum(Album currentAlbum);

    void deleteAlbum(Album currentAlbum);

}
