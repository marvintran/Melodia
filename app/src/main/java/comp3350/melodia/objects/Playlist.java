package comp3350.melodia.objects;

import java.io.File;
import java.util.List;

public class Playlist{

    private int playlistID;
    private String playlistName;
    private int playlistTime;
    private int numberOfSongs;
    private List<Song> songs;
    private File playlistData;

    public Playlist(int playlistID, String playlistName, int playlistTime,
                    int numberOfSongs, List<Song> songs, File playlistData) {
        this.playlistID = playlistID;
        this.playlistName = playlistName;
        this.playlistTime = playlistTime;
        this.numberOfSongs = numberOfSongs;
        this.songs = songs;
        this.playlistData = playlistData;
    }

    public Playlist(int playlistID, String playlistName, int playlistTime, int numberOfSongs,
                    List<Song> songs, String playlistLocation) {
        this(playlistID, playlistName, playlistTime, numberOfSongs, songs,
                new File(playlistLocation));
    }

    public int getPlaylistID() { return playlistID;}

    public String getPlaylistName() {
        return playlistName;
    }

    public int getPlaylistTime() {
        return playlistTime;
    }

    public int getNumberOfSongs() {
        return numberOfSongs;
    }

    public List<Song> getSongs() {
        return songs;
    }
    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
}



