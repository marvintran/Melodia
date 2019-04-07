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

    private Song(Builder builder) {
        this.songID = builder.songID;
        this.songName = builder.songName;
        this.songTime = builder.songTime;
        this.albumID = builder.albumID;
        this.albumName = builder.albumName;
        this.artistID = builder.artistID;
        this.artistName = builder.artistName;
        this.trackNumber = builder.trackNumber;
        this.songData = builder.songData;
    }

    public static class Builder {
        private int songID;
        private String songName;
        private int songTime;
        private int albumID;
        private String albumName;
        private int artistID;
        private String artistName;
        private int trackNumber;
        private File songData;

        public Builder() {

        }

        public Builder withSongID(int songID) {
            this.songID = songID;
            return this;
        }

        public Builder withSongName(String songName) {
            this.songName = songName;
            return this;
        }

        public Builder withSongTime(int songTime) {
            this.songTime = songTime;
            return this;
        }

        public Builder withAlbumID(int albumID) {
            this.albumID = albumID;
            return this;
        }

        public Builder withAlbumName(String albumName) {
            this.albumName = albumName;
            return this;
        }

        public Builder withArtistID(int artistID) {
            this.artistID = artistID;
            return this;
        }

        public Builder withArtistName(String artistName) {
            this.artistName = artistName;
            return this;
        }

        public Builder withTrackNumber(int trackNumber) {
            this.trackNumber = trackNumber;
            return this;
        }

        public Builder withSongData(String songLocation) {
            this.songData = new File(songLocation);
            return this;
        }

        public Song build() {
            return new Song(this);
        }
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

    public int getHours(){return  (getSongTime() / 3600);}

    public int getMinutes(){return ((getSongTime() % 3600) / 60);}

    public int getSeconds(){return (getSongTime() % 60);}


    public File getSongData() {
        return songData;
    }

}


