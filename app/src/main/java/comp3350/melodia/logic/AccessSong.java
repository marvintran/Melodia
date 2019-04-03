package comp3350.melodia.logic;

import java.util.Collections;
import java.util.List;

import comp3350.melodia.application.Services;
import comp3350.melodia.objects.Song;
import comp3350.melodia.persistence.SongPersistence;

public class AccessSong {
    private SongPersistence songPersistence;
    private List<Song> songs;

    public AccessSong(){
        this(Services.getSongPersistence());
    }

    public AccessSong(SongPersistence songPersistence) {
        this.songPersistence=songPersistence;
    }

    public List<Song> getSongsSortedTrackName() {
        songs = songPersistence.getSongsSortedTrackName();
        return Collections.unmodifiableList(songs);
    }

    public List<Song> getPlaylistSongs(int playlistID) {
        songs = songPersistence.getPlaylistSongs(playlistID);
        return Collections.unmodifiableList(songs);
    }

    public List<Song> getSongsSortedArtist() {
        songs = songPersistence.getSongsSortedArtist();
        return Collections.unmodifiableList(songs);
    }
}
