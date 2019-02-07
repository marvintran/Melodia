package comp3350.melodia.objects;

import java.util.Collection;

public class Artist {
    private String artistName;
    private Collection<Album> albums;

    public Artist(String artistName, Collection<Album> albums) {
        this.artistName = artistName;
        this.albums = albums;
    }

    public String getArtistName(){
        return artistName;
    }
    public void setArtistName(String artistName){
        this.artistName = artistName;
    }
    public Collection<Album> getAlbums(){
        return albums;
    }
    public void setAlbums(Collection<Album> albums){
        this.albums = albums;
    }
}

