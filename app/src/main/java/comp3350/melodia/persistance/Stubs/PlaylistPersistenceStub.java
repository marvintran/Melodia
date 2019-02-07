package comp3350.melodia.persistence.Stubs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comp3350.melodia.objects.Playlist;
import comp3350.melodia.objects.Song;
import comp3350.melodia.persistance.PlaylistPersistence;

public class PlaylistPersistenceStub implements PlaylistPersistence {
    private List<Playlist> playlists;

    public PlaylistPersistenceStub() {
        this.playlists = new ArrayList<>();

        playlists.add(0, new Playlist(
                "Playlist 1",
                2400,
                10,
                new ArrayList<Song>(),
                ""));
        playlists.add(1, new Playlist(
                "Playlist 2",
                480,
                2,
                new ArrayList<Song>(),
                ""));
    }

    @Override
    public List<Playlist> getPlaylistSequential() {
        return Collections.unmodifiableList(playlists);
    }

    @Override
    public List<Playlist> getPlaylistRandom(Playlist currentPlaylist) {
        List<Playlist> newPlaylists = new ArrayList<>();
        int index;

        index = playlists.indexOf(currentPlaylist);
        if (index >= 0)
        {
            newPlaylists.add(playlists.get(index));
        }
        return newPlaylists;
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
