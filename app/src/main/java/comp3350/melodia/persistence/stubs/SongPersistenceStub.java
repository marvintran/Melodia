package comp3350.melodia.persistence.stubs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comp3350.melodia.objects.Genre;
import comp3350.melodia.objects.Song;
import comp3350.melodia.objects.Artist;
import comp3350.melodia.objects.Album;
import comp3350.melodia.persistence.SongPersistence;

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
        songs.add(3, new Song(
                "God's plan",
                239,
                new Artist(
                        "Drake",
                        new ArrayList<Album>()),
                new Album(
                        "Scorpion",
                        new ArrayList<Song>(),
                        new Genre("Rap")),
                5,
                ""));
        songs.add(4, new Song(
                "Nice For What",
                220,
                new Artist(
                        "Drake",
                        new ArrayList<Album>()),
                new Album(
                        "Scorpion",
                        new ArrayList<Song>(),
                        new Genre("Rap")),
                4,
                ""));
        songs.add(5, new Song(
                "24K Magic",
                226,
                new Artist(
                        "Bruno Mars",
                        new ArrayList<Album>()),
                new Album(
                        "24k Magic",
                        new ArrayList<Song>(),
                        new Genre("Pop")),
                1,
                ""));
        songs.add(6, new Song(
                "That's What I Like",
                207,
                new Artist(
                        "Bruno Mars",
                        new ArrayList<Album>()),
                new Album(
                        "24k Magic",
                        new ArrayList<Song>(),
                        new Genre("Pop")),
                4,
                ""));
        songs.add(7, new Song(
                "Finesse",
                191,
                new Artist(
                        "Bruno Mars",
                        new ArrayList<Album>()),
                new Album(
                        "24k Magic",
                        new ArrayList<Song>(),
                        new Genre("Pop")),
                8,
                ""));
        songs.add(8, new Song(
                "Chunky",
                187,
                new Artist(
                        "Bruno Mars",
                        new ArrayList<Album>()),
                new Album(
                        "24k Magic",
                        new ArrayList<Song>(),
                        new Genre("Pop")),
                2,
                ""));
        songs.add(9, new Song(
                "Often",
                249,
                new Artist(
                        "The Weeknd",
                        new ArrayList<Album>()),
                new Album(
                        "Beauty Behind The Madness",
                        new ArrayList<Song>(),
                        new Genre("Pop")),
                4,
                ""));
        songs.add(10, new Song(
                "Earned It",
                246,
                new Artist(
                        "The Weeknd",
                        new ArrayList<Album>()),
                new Album(
                        "Beauty Behind The Madness",
                        new ArrayList<Song>(),
                        new Genre("Pop")),
                9,
                ""));
        songs.add(10, new Song(
                "Can't Feel My Face",
                209,
                new Artist(
                        "The Weeknd",
                        new ArrayList<Album>()),
                new Album(
                        "Beauty Behind The Madness",
                        new ArrayList<Song>(),
                        new Genre("Pop")),
                7,
                ""));
        songs.add(11, new Song(
                "The Hills",
                242,
                new Artist(
                        "The Weeknd",
                        new ArrayList<Album>()),
                new Album(
                        "Beauty Behind The Madness",
                        new ArrayList<Song>(),
                        new Genre("Pop")),
                5,
                ""));
        songs.add(12, new Song(
                "Yikes",
                189,
                new Artist(
                        "Kanye West",
                        new ArrayList<Album>()),
                new Album(
                        "ye",
                        new ArrayList<Song>(),
                        new Genre("Rap")),
                2,
                ""));
        songs.add(12, new Song(
                "All Mine",
                146,
                new Artist(
                        "Kanye West",
                        new ArrayList<Album>()),
                new Album(
                        "ye",
                        new ArrayList<Song>(),
                        new Genre("Rap")),
                3,
                ""));
        songs.add(13, new Song(
                "Wouldn't Leave",
                206,
                new Artist(
                        "Kanye West",
                        new ArrayList<Album>()),
                new Album(
                        "ye",
                        new ArrayList<Song>(),
                        new Genre("Rap")),
                4,
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

