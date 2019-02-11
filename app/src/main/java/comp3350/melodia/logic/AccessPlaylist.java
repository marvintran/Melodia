package comp3350.melodia.logic;

import java.util.Collections;
import java.util.List;

import comp3350.melodia.application.Services;
import comp3350.melodia.objects.Playlist;
import comp3350.melodia.persistence.PlaylistPersistence;

public class AccessPlaylist {
    private PlaylistPersistence playlistPersistence;
    private List<Playlist> playlists;
    private Playlist song;

    public AccessPlaylist(){
        playlistPersistence = Services.getPlaylistPersistence();
        playlists = null;
        song = null;
    }

    public List<Playlist> getPlaylists(){
        playlists = playlistPersistence.getAllPlaylists();
        return Collections.unmodifiableList(playlists);
    }

    public Playlist insertPlaylist(Playlist newPlaylist){
        return playlistPersistence.insertPlaylist(newPlaylist);
    }

}
