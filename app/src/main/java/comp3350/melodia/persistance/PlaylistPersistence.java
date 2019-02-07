package comp3350.melodia.persistance;

import java.util.List;
import comp3350.melodia.objects.Playlist;

public interface PlaylistPersistence {

    List<Playlist> getPlaylistSequential();

    List<Playlist> getPlaylistRandom(Playlist currentPlaylist);

    Playlist insertPlaylist(Playlist currentPlaylist);

    Playlist updatePlaylist(Playlist currentPlaylist);

    void deletePlaylist(Playlist currentPlaylist);

}
