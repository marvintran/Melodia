package comp3350.melodia.objects;

public class Playlist{

    private int playlistID;
    private String playlistName;
    private int numberOfSongs;

    public Playlist(int playlistID, String playlistName, int numberOfSongs) {
        this.playlistID = playlistID;
        this.playlistName = playlistName;
        this.numberOfSongs = numberOfSongs;
    }

    public int getPlaylistID() { return playlistID;}

    public String getPlaylistName() {
        return playlistName;
    }

    public int getNumberOfSongs() {
        return numberOfSongs;
    }
}



