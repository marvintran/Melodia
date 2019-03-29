package comp3350.melodia.objects;

import java.util.List;

public class Album{
    private int albumID;
    private String albumName;
    private List<Song> songs;
    private Genre genreName = null;

    public Album(int albumID, String albumName, List<Song> songs) {
        this.albumID = albumID;
        this.albumName = albumName;
        this.songs = songs;
    }

    // Added second constructor if the album has a genre.
    public Album(int albumID, String albumName, List<Song> songs, Genre genreName) {
        this(albumID, albumName, songs);
        this.genreName = genreName;
    }

    public int getAlbumID() {
        return albumID;
    }

    public String getAlbumName() {
        return albumName;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public Genre getAlbumGenre() {
        return genreName;
    }
}
