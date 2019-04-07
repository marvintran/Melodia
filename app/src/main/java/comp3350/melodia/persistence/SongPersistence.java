package comp3350.melodia.persistence;

import java.util.List;
import comp3350.melodia.objects.Song;

public interface SongPersistence {

    List<Song> getSongsSortedTrackName();

    List<Song> getSongsSortedArtist();

    List<Song> getPlaylistSongs(int playlistID);

    void insertPlaylistSong(int playlistID, int songID, int position);

    void deletePlaylistSong(int playlistID, int position);

    Song insertSong(Song currentSong);

    Song updateSong(Song currentSong);

    void deleteSong(Song currentSong);
}
