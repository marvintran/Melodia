package com.example.melodia.Persistence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comp3350.melodia.objects.Song;
import comp3350.melodia.persistence.SongPersistence;

public class SongPersistenceStub implements SongPersistence {
    private List<Song> songs;

    public SongPersistenceStub() {
        this.songs = new ArrayList<>();

        songs.add(0, new Song.Builder()
                .withSongID(1)
                .withSongName("All that")
                .withSongTime(177)
                .withAlbumID(1)
                .withAlbumName("bensound")
                .withArtistID(1)
                .withArtistName("Benjamin Tissot")
                .withTrackNumber(3)
                .withSongData("bensound_allthat.mp3")
                .build());
        songs.add(1, new Song.Builder()
                .withSongID(2)
                .withSongName("dance")
                .withSongTime(175)
                .withAlbumID(1)
                .withAlbumName("bensound")
                .withArtistID(1)
                .withArtistName("Benjamin Tissot")
                .withTrackNumber(3)
                .withSongData("bensound_dance.mp3")
                .build());
        songs.add(2, new Song.Builder()
                .withSongID(3)
                .withSongName("halfmoon")
                .withSongTime(200)
                .withAlbumID(4)
                .withAlbumName("halfmoon")
                .withArtistID(2)
                .withArtistName("Dean")
                .withTrackNumber(3)
                .build());
    }

    @Override
    public List<Song> getSongsSortedTrackName() {
        return Collections.unmodifiableList(songs);
    }

    @Override
    public List<Song> getSongsSortedArtist() {
        return songs;
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

    @Override
    public List<Song> getPlaylistSongs(int playlistID) {
        return songs;
    }
}



