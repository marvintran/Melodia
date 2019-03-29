package comp3350.melodia.objects;

import java.io.File;

public class Song {
    private int songID;
    private String songName;
    private int songTime;
    private int albumID;
    private String albumName;
    private int artistID;
    private String artistName;
    private int trackNumber;
    private File songData;

    public Song(int songID, String songName, int songTime,int albumID, String albumName,
                int artistID, String artistName, int trackNumber, File songData) {
        this.songID = songID;
        this.songName = songName;
        this.songTime = songTime;
        this.albumID = albumID;
        this.albumName = albumName;
        this.artistID = artistID;
        this.artistName = artistName;
        this.trackNumber = trackNumber;
        this.songData = songData;
    }

    public Song(int songID, String songName, int songTime,int albumID, String albumName,
                int artistID, String artistName, int trackNumber, String songLocation) {
        this(songID, songName, songTime, albumID, albumName, artistID, artistName, trackNumber,
                new File(songLocation));
    }

    public int getSongID(){return songID;}

    public String getSongName() {
        return songName;
    }

    public int getSongTime() {
        return songTime;
    }

    public int getAlbumID() {
        return albumID;
    }

    public String getAlbumName() {
        return albumName;
    }

    public int getArtistID() {
        return artistID;
    }

    public String getArtistName() {
        return artistName;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public File getSongData() {
        return songData;
    }

}


