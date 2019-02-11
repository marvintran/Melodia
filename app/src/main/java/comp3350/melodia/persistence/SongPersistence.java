package comp3350.melodia.persistence;

import java.util.List;
import comp3350.melodia.objects.Song;

public interface SongPersistence {

    List<Song> getAllSongs();

    Song insertSong(Song currentSong);

    Song updateSong(Song currentSong);

    void deleteSong(Song currentSong);

}
