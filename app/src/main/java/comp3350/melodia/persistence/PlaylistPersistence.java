package comp3350.melodia.persistence;

import java.util.List;
import comp3350.melodia.objects.Playlist;

public interface PlaylistPersistence {

    List<Playlist> getAllPlaylists();

    Playlist getSpecificPlaylist(int playlistID);

    void insertPlaylist(String playlistName);

    void deletePlaylist(int playlistID);
}
