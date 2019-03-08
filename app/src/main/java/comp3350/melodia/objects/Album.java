package comp3350.melodia.objects;

import java.util.Collection;

public class Album{
    private String albumName;
    private Collection<Song> songs;
    private Genre genreName = null;

    public Album(String albumName, Collection<Song> songs){
        this.albumName = albumName;
        this.songs = songs;
    }

    //added second constructor if the album has a genre
    public Album(String albumName, Collection<Song> songs, Genre genreName){
        this(albumName, songs);
        this.genreName = genreName;
    }

    public Album(String albumName){
        this.albumName = albumName;
        this.songs = songs;
    }

    public String getAlbumName(){
        return albumName;
    }

    public void setAlbumName(String albumName){
        this.albumName = albumName;
    }

    public Collection<Song> getSongs(){
        return songs;
    }

    public Genre getAlbumGenre(){
        return genreName;
    }
    public void setAlbumGenre(Genre genreName){
        this.genreName = genreName;
    }
}
