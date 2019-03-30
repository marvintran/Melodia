package comp3350.melodia.persistence;

import java.util.List;
import comp3350.melodia.objects.Playlist;

public interface PlaylistPersistence {

    List<Playlist> getAllPlaylists();

    void insertPlaylist(String playlistName);

    void updatePlaylist(int playlistID, int songID);

    void deletePlaylist(int playlistID);

}
