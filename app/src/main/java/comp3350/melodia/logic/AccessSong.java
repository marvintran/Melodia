package comp3350.melodia.logic;

import java.util.Collections;
import java.util.List;

import comp3350.melodia.application.Services;
import comp3350.melodia.objects.Song;
import comp3350.melodia.persistence.SongPersistence;

public class AccessSong {
    private SongPersistence songPersistence;
    private List<Song> songs;
    private Song song;

    public AccessSong(){
        songPersistence = Services.getSongPersistence();
        songs = null;
        song = null;
    }

    public List<Song> getSongs(){
        songs = songPersistence.getAllSongs();
        return Collections.unmodifiableList(songs);
    }

}
