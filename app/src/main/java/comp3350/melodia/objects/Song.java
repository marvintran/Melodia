package comp3350.melodia.objects;

public class Song {
    private String songName;
    private String artistName;
    private int songTime;

    public Song(String newSongName, String newArtistName, int newSongTime){
        this.songName = newSongName;
        this.artistName = newArtistName;
        this.songTime = newSongTime;
    }

    public String getSongName() { return songName; }

    public String getArtistName() {return artistName; }

    public int getSongTime() { return songTime; }

    public String toString(){
        return (songName + " by: " + artistName + " is " + songTime + "s");
    }
}
