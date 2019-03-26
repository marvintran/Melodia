package comp3350.melodia.objects;

import java.util.Collection;

public class Artist {
    private int artistID;
    private String artistName;
    private Collection<Album> albums;

    public Artist(int artistID, String artistName, Collection<Album> albums) {
        this.artistID = artistID;
        this.artistName = artistName;
        this.albums = albums;
    }

    public int getArtistID(){
        return artistID;
    }

    public String getArtistName(){
        return artistName;
    }
    public Collection<Album> getAlbums(){
        return albums;
    }
}

