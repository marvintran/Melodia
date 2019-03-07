package comp3350.melodia.objects;

import java.io.File;
import java.util.List;

public class Playlist{
    private String playlistName;
    private int playlistTime;
    private int numberOfSongs;
    private List<Song> songs;
    private File playlistData; // get the data for the playlist

    public Playlist(String playlistName, int playlistTime, int numberOfSongs, List<Song> songs, File playlistData) {
        this.playlistName = playlistName;
        this.playlistTime = playlistTime;
        this.numberOfSongs = numberOfSongs;
        this.songs = songs;
        this.playlistData = playlistData;
    }

    public Playlist(String playlistName, int playlistTime, int numberOfSongs, List<Song> songs, String playlistLocation) {
        this(playlistName, playlistTime, numberOfSongs, songs, new File(playlistLocation));
    }

    public String getPlaylistName(){
        return playlistName;
    }

    public void setPlaylistName(String playlistName){
        this.playlistName = playlistName;
    }

    public int getPlaylistTime(){
        return playlistTime;
    }

    public void setPlaylistTime(int playlistTime){
        this.playlistTime = playlistTime;
    }

    public int getNumberOfSongs(){
        return numberOfSongs;
    }
    public void setNumberOfSongs(int numberOfSongs){
        this.numberOfSongs = numberOfSongs;
    }

    public List<Song> getSongs(){
        return songs;
    }
    public void setSongs(List<Song> songs){
        this.songs = songs;
    }
}



