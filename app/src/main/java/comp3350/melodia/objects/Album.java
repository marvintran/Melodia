package comp3350.melodia.objects;

import java.util.Collection;
import java.util.List;

public class Album{
    private String albumName;
    private List<Song> songs;
    private Genre genreName = null;

    public Album(String albumName, List<Song> songs) {
        this.albumName = albumName;
        this.songs = songs;
    }

    // Added second constructor if the album has a genre.
    public Album(String albumName, List<Song> songs, Genre genreName) {
        this(albumName, songs);
        this.genreName = genreName;
    }

    public Album(String albumName){
        this.albumName = albumName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public Genre getAlbumGenre() {
        return genreName;
    }
    public void setAlbumGenre(Genre genreName) {
        this.genreName = genreName;
    }
}
