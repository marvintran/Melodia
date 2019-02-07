package comp3350.melodia.persistance;

import java.util.List;
import comp3350.melodia.objects.Song;

public interface SongPersistence {

    List<Song> getSongSequential();

    List<Song> getSongRandom(Song currentSong);

    Song insertSong(Song currentSong);

    Song updateSong(Song currentSong);

    void deleteSong(Song currentSong);

}
