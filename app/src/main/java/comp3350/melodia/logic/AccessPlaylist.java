package comp3350.melodia.logic;

import java.util.List;

import comp3350.melodia.application.Services;
import comp3350.melodia.objects.Playlist;
import comp3350.melodia.objects.Song;
import comp3350.melodia.persistence.PlaylistPersistence;

public class AccessPlaylist {
    private PlaylistPersistence playlistPersistence;

    public AccessPlaylist(){
        this(Services.getPlaylistPersistence());
    }
    public AccessPlaylist(PlaylistPersistence playlistPersistence){
        this.playlistPersistence=playlistPersistence;
    }

    public List<Playlist> getPlaylists(){
        List<Playlist> allPlaylists = playlistPersistence.getAllPlaylists();
        int count = 0;
        for(Playlist currentPlaylist: allPlaylists) {

            if(currentPlaylist.getPlaylistID() == 0)
                allPlaylists.remove(count);
            count++;
        }
        return allPlaylists;
    }

    public Playlist getSpecificPlaylist(int playlistID) {
        return playlistPersistence.getSpecificPlaylist(playlistID);
    }

    public void insertPlaylist(String playlistName){
        playlistPersistence.insertPlaylist(playlistName);
    }

    public void deletePlaylist(int playlistID) {
        playlistPersistence.deletePlaylist(playlistID);
    }

    public void insertPlaylistSong(int playlistID, int songID, int position) {
        playlistPersistence.insertPlaylistSong(playlistID, songID, position);
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

    public void updateOrder(int playlistID, int fromPosition, int toPosition) {
        AccessSong accessSong = new AccessSong();
        List<Song> playlistSongs = accessSong.getPlaylistSongs(playlistID);
        Song songToAdd = playlistSongs.get(fromPosition);
        int songID = songToAdd.getSongID();

        deletePlaylistSong(playlistID, fromPosition);
        insertPlaylistSong(playlistID, songID, toPosition);
    }
}
