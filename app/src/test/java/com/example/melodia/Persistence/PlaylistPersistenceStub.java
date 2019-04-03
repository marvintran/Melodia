package com.example.melodia.Persistence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comp3350.melodia.objects.Playlist;
import comp3350.melodia.persistence.PlaylistPersistence;

public class PlaylistPersistenceStub implements PlaylistPersistence {
    private List<Playlist> playlists;

    public PlaylistPersistenceStub() {
        this.playlists = new ArrayList<>();

        playlists.add(0, new Playlist(1,
                "Playlist 1",
                10));
        playlists.add(1, new Playlist(2,
                "Playlist 2",
                2));
        playlists.add(2, new Playlist(3,
                "Playlist Test",
                5));
    }

    @Override
    public List<Playlist> getAllPlaylists() {
        return Collections.unmodifiableList(playlists);
    }

    @Override
    public void insertPlaylist(String playlistName){
        playlists.add(new Playlist(1, playlistName, 0));
    }

    @Override
    public void insertPlaylistSong(int playlistID, int songID, int position){
    }

    @Override
    public Playlist getSpecificPlaylist(int playlistID){
        int index = 0;
        for(int i = 0; index < playlists.size(); i++){
            if (playlists.get(i).getPlaylistID() == playlistID){
                index = i;
            }
        }
        return playlists.get(index);
    }

    @Override
    public void deletePlaylist(int playlistID) {
        int index = playlists.indexOf(playlistID);

        if (index >= 0) {
            playlists.remove(index);
        }
    }

    @Override
    public void deletePlaylistSong(int playlistID, int position){
    }

}



