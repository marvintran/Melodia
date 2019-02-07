package comp3350.melodia.persistence.stubs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comp3350.melodia.objects.Genre;
import comp3350.melodia.objects.Song;
import comp3350.melodia.objects.Artist;
import comp3350.melodia.objects.Album;
import comp3350.melodia.persistance.SongPersistence;

public class SongPersistenceStub implements SongPersistence {
    private List<Song> songs;

    public SongPersistenceStub() {
        this.songs = new ArrayList<>();

        songs.add(0, new Song(
                "halfmoon",
                229,
                new Artist(
                        "Dean",
                        new ArrayList<Album>()),
                new Album(
                        "130 mood: TRBL",
                        new ArrayList<Song>(),
                        new Genre("JPOP")),
                5,
                ""));
        songs.add(1, new Song(
                "diver",
                251,
                new Artist(
                        "Nico Touches the Walls",
                        new ArrayList<Album>()),
                new Album(
                        "Single",
                        new ArrayList<Song>(),
                        new Genre("JPOP")),
                1,
                ""));
    }

    @Override
    public List<Song> getSongSequential() {
        return Collections.unmodifiableList(songs);
    }

    @Override
    public List<Song> getSongRandom(Song currentSong) {
        List<Song> newSongs = new ArrayList<>();
        int index;

        index = songs.indexOf(currentSong);
        if (index >= 0)
        {
            newSongs.add(songs.get(index));
        }
        return newSongs;
    }

    @Override
    public Song insertSong(Song currentSong) {
        songs.add(currentSong);
        return currentSong;
    }

    @Override
    public Song updateSong(Song currentSong) {
        int index = songs.indexOf(currentSong);

        if (index >= 0) {
            songs.set(index, currentSong);
        }

        return currentSong;
    }

    @Override
    public void deleteSong(Song currentSong) {
        int index = songs.indexOf(currentSong);

        if (index >= 0) {
            songs.remove(index);
        }
    }
}

