package comp3350.melodia.objects;

import java.util.Collection;

public class Artist {
    private String artistName;
    private Collection<Album> albums;

    public Artist(String artistName, Collection<Album> albums) {
        this.artistName = artistName;
        this.albums = albums;
    }

    public Artist(String artistName){
        this.artistName = artistName;
    }
    public String getArtistName(){
        return artistName;
    }
    public Collection<Album> getAlbums(){
        return albums;
    }
}

