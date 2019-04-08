package comp3350.melodia.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comp3350.melodia.objects.Song;
import comp3350.melodia.persistence.SongPersistence;

public class AccessSong {
    private SongPersistence songPersistence;
    private List<Song> songs;

    public AccessSong(SongPersistence songPersistence) {
        this.songPersistence = songPersistence;
    }

    public List<Song> getSongsSortedTrackName() {
        songs = songPersistence.getSongsSortedTrackName();
        return Collections.unmodifiableList(songs);
    }

    public List<Song> getSongsSortedArtist() {
        songs = songPersistence.getSongsSortedArtist();
        return Collections.unmodifiableList(songs);
    }

    public List<Song> getPlaylistSongs(int playlistID) {
        songs = songPersistence.getPlaylistSongs(playlistID);
        return Collections.unmodifiableList(songs);
    }

    public void insertPlaylistSong(int playlistID, int songID, int position) {
        songPersistence.insertPlaylistSong(playlistID, songID, position);
    }

    public void deletePlaylistSong(int playlistID, int position) {
        songPersistence.deletePlaylistSong(playlistID, position);
    }

    public void replaceQueueWithPlaylist(List<Song> songsReplacingQueue,
                                         List<Song> queueSongs) {

        for(int i = 0; i < queueSongs.size(); i++) {
            deletePlaylistSong(0, 0);
        }


        for(int i = 0; i < songsReplacingQueue.size(); i++) {
            insertPlaylistSong(0, songsReplacingQueue.get(i).getSongID(), i);
        }
    }

    public void updateOrder(int playlistID, int fromPosition, int toPosition) {
        List<Song> playlistSongs = getPlaylistSongs(playlistID);
        Song songToAdd = playlistSongs.get(fromPosition);
        int songID = songToAdd.getSongID();

        deletePlaylistSong(playlistID, fromPosition);
        insertPlaylistSong(playlistID, songID, toPosition);
    }
    public void shuffleQueue() {
        List<Song> queueSongs = new ArrayList(getPlaylistSongs(0));

        for(int i = 0; i < queueSongs.size(); i++) {
            deletePlaylistSong(0, 0);
        }

        Collections.shuffle(queueSongs);

        for(int i = 0; i < queueSongs.size(); i++) {
            insertPlaylistSong(0, queueSongs.get(i).getSongID(), 0);
        }
    }
}
