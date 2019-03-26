package Objects.Persistence;

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

        songs.add(0, new Song(1,
                "All that",
                175,
                new Artist(1,
                        "Benjamin Tissot",
                        new ArrayList<Album>()),
                new Album(1,
                        "bensound",
                        new ArrayList<Song>(),
                        new Genre("Jazz")),
                5,
                "bensound_allthat.mp3"));
        songs.add(1, new Song(2,
                "Dance",
                177,
                new Artist(2,
                        "Benjamin Tissot",
                        new ArrayList<Album>()),
                new Album(2,
                        "bensound",
                        new ArrayList<Song>(),
                        new Genre("Electronic")),
                1,
                "bensound_dance.mp3"));
        songs.add(2, new Song(3,
                "fan",
                225,
                new Artist(3,
                        "epik high",
                        new ArrayList<Album>()),
                new Album(3,
                        "remapping the soul",
                        new ArrayList<Song>()),
                2,
                ""));
        songs.add(3, new Song(4,
                "God's plan",
                239,
                new Artist(4,
                        "Drake",
                        new ArrayList<Album>()),
                new Album(4,
                        "Scorpion",
                        new ArrayList<Song>(),
                        new Genre("Rap")),
                5,
                ""));
        songs.add(4, new Song(5,
                "Nice For What",
                220,
                new Artist(5,
                        "Drake",
                        new ArrayList<Album>()),
                new Album(5,
                        "Scorpion",
                        new ArrayList<Song>(),
                        new Genre("Rap")),
                4,
                ""));
        songs.add(5, new Song(6,
                "24K Magic",
                226,
                new Artist(6,
                        "Bruno Mars",
                        new ArrayList<Album>()),
                new Album(6,
                        "24k Magic",
                        new ArrayList<Song>(),
                        new Genre("Pop")),
                1,
                ""));
        songs.add(6, new Song(7,
                "That's What I Like",
                207,
                new Artist(7,
                        "Bruno Mars",
                        new ArrayList<Album>()),
                new Album(7,
                        "24k Magic",
                        new ArrayList<Song>(),
                        new Genre("Pop")),
                4,
                ""));
        songs.add(7, new Song(8,
                "Finesse",
                191,
                new Artist(8,
                        "Bruno Mars",
                        new ArrayList<Album>()),
                new Album(8,
                        "24k Magic",
                        new ArrayList<Song>(),
                        new Genre("Pop")),
                8,
                ""));
        songs.add(8, new Song(9,
                "Chunky",
                187,
                new Artist(9,
                        "Bruno Mars",
                        new ArrayList<Album>()),
                new Album(9,
                        "24k Magic",
                        new ArrayList<Song>(),
                        new Genre("Pop")),
                2,
                ""));
        songs.add(9, new Song(10,
                "Often",
                249,
                new Artist(10,
                        "The Weeknd",
                        new ArrayList<Album>()),
                new Album(10,
                        "Beauty Behind The Madness",
                        new ArrayList<Song>(),
                        new Genre("Pop")),
                4,
                ""));
        songs.add(10, new Song(11,
                "Earned It",
                246,
                new Artist(11,
                        "The Weeknd",
                        new ArrayList<Album>()),
                new Album(11,
                        "Beauty Behind The Madness",
                        new ArrayList<Song>(),
                        new Genre("Pop")),
                9,
                ""));
        songs.add(10, new Song(12,
                "Can't Feel My Face",
                209,
                new Artist(12,
                        "The Weeknd",
                        new ArrayList<Album>()),
                new Album(12,
                        "Beauty Behind The Madness",
                        new ArrayList<Song>(),
                        new Genre("Pop")),
                7,
                ""));
        songs.add(11, new Song(13,
                "The Hills",
                242,
                new Artist(13,
                        "The Weeknd",
                        new ArrayList<Album>()),
                new Album(13,
                        "Beauty Behind The Madness",
                        new ArrayList<Song>(),
                        new Genre("Pop")),
                5,
                ""));
        songs.add(12, new Song(14,
                "Yikes",
                189,
                new Artist(14,
                        "Kanye West",
                        new ArrayList<Album>()),
                new Album(14,
                        "ye",
                        new ArrayList<Song>(),
                        new Genre("Rap")),
                2,
                ""));
        songs.add(12, new Song(15,
                "All Mine",
                146,
                new Artist(15,
                        "Kanye West",
                        new ArrayList<Album>()),
                new Album(15,
                        "ye",
                        new ArrayList<Song>(),
                        new Genre("Rap")),
                3,
                ""));
        songs.add(13, new Song(16,
                "Wouldn't Leave",
                206,
                new Artist(16,
                        "Kanye West",
                        new ArrayList<Album>()),
                new Album(16,
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


