package comp3350.melodia.logic;

import java.util.Collections;
import java.util.List;

import comp3350.melodia.application.Services;
import comp3350.melodia.objects.Playlist;
import comp3350.melodia.persistence.PlaylistPersistence;

public class AccessPlaylist {
    private PlaylistPersistence playlistPersistence;
    private List<Playlist> playlists;
    private Playlist playlist;

    public AccessPlaylist(){
        playlistPersistence = Services.getPlaylistPersistence();
        playlists = null;
        playlist = null;
    }

    public AccessPlaylist(final PlaylistPersistence playlistPersistence) {
        this();
        this.playlistPersistence = playlistPersistence;
    }

    public List<Playlist> getPlaylists(){
        return playlistPersistence.getAllPlaylists();
    }

    public void insertPlaylist(String playlistName){
        playlistPersistence.insertPlaylist(playlistName);
    }

    public void updatePlaylist(int playlistID, int songID) {
        playlistPersistence.updatePlaylist(playlistID, songID);
    }

    public void deletePlaylist(int playlistID) {
        playlistPersistence.deletePlaylist(playlistID);
    }
}
