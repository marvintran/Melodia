package Objects.Persistence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comp3350.melodia.objects.Album;
import comp3350.melodia.objects.Artist;
import comp3350.melodia.objects.Genre;
import comp3350.melodia.objects.Playlist;
import comp3350.melodia.objects.Song;
import comp3350.melodia.persistence.PlaylistPersistence;

public class PlaylistPersistenceStub implements PlaylistPersistence {
    private List<Playlist> playlists;

    public PlaylistPersistenceStub() {
        this.playlists = new ArrayList<>();

        List<Song> someSongs = new ArrayList<>();

        Song someSong = new Song(1,
                "halfmoon",
                229,
                new Artist(1,
                        "Dean",
                        new ArrayList<Album>()),
                new Album(1,
                        "130 mood: TRBL",
                        new ArrayList<Song>(),
                        new Genre("R&B")),
                5,
                "");

        someSongs.add(someSong);

        playlists.add(0, new Playlist(1,
                "Playlist 1",
                2400,
                10,
                someSongs,
                ""));
        playlists.add(1, new Playlist(2,
                "Playlist 2",
                480,
                2,
                someSongs,
                ""));
        playlists.add(2, new Playlist(3,
                "Playlist Test",
                1200,
                5,
                someSongs,
                ""));
    }

    @Override
    public List<Playlist> getAllPlaylists() {
        return Collections.unmodifiableList(playlists);
    }

    @Override
    public Playlist insertPlaylist(Playlist currentPlaylist) {
        playlists.add(currentPlaylist);
        return currentPlaylist;
    }

    @Override
    public Playlist updatePlaylist(Playlist currentPlaylist) {
        int index = playlists.indexOf(currentPlaylist);

        if (index >= 0) {
            playlists.set(index, currentPlaylist);
        }
        return currentPlaylist;
    }

    @Override
    public void deletePlaylist(Playlist currentPlaylist) {
        int index = playlists.indexOf(currentPlaylist);

        if (index >= 0) {
            playlists.remove(index);
        }
    }
}

