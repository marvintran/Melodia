package comp3350.melodia.logic;

import java.util.List;

import comp3350.melodia.application.Services;
import comp3350.melodia.objects.Playlist;
import comp3350.melodia.objects.Song;
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

    public Playlist getSpecificPlaylist(int playlistID) {
        return playlistPersistence.getSpecificPlaylist(playlistID);
    }

    public void insertPlaylist(String playlistName){
        playlistPersistence.insertPlaylist(playlistName);
    }

    public void insertPlaylistSong(int playlistID, int songID, int position) {
        playlistPersistence.insertPlaylistSong(playlistID, songID, position);
    }

    public void deletePlaylist(int playlistID) {
        playlistPersistence.deletePlaylist(playlistID);
    }

    public void deletePlaylistSong(int playlistID, int position) {
        playlistPersistence.deletePlaylistSong(playlistID, position);
    }

    public void replaceQueueWithPlaylist(List<Song> songsReplacingQueue,
                                         List<Song> queueSongs) {

        for(int i = 0; i < queueSongs.size(); i++) {
            playlistPersistence.deletePlaylistSong(0, 0);
        }


        for(int i = 0; i < songsReplacingQueue.size(); i++) {
            playlistPersistence.insertPlaylistSong(0, songsReplacingQueue.get(i).getSongID(), i);
        }
    }
}
