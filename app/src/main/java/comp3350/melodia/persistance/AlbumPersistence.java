package comp3350.melodia.persistance;

import java.util.List;
import comp3350.melodia.objects.Album;

public interface AlbumPersistence {

    List<Album> getAlbumSequential();

    List<Album> getAlbumRandom(Album currentAlbum);

    Album insertAlbum(Album currentAlbum);

    Album updateAlbum(Album currentAlbum);

    void deleteAlbum(Album currentAlbum);

}
