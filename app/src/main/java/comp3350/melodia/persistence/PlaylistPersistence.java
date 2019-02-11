package comp3350.melodia.persistence;

import java.util.List;
import comp3350.melodia.objects.Playlist;

public interface PlaylistPersistence {

    List<Playlist> getAllPlaylists();

    Playlist insertPlaylist(Playlist currentPlaylist);

    Playlist updatePlaylist(Playlist currentPlaylist);

    void deletePlaylist(Playlist currentPlaylist);

}
