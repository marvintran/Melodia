package comp3350.melodia.persistance.stubs;

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
                        new Genre("R&B")),
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
        songs.add(2, new Song(
                "fan",
                225,
                new Artist(
                        "epik high",
                        new ArrayList<Album>()),
                new Album(
                        "remapping the soul",
                        new ArrayList<Song>()),
                2,
                ""));
    }

    @Override
    public List<Song> getAllSongs() {
        return Collections.unmodifiableList(songs);
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

