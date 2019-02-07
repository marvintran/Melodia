package comp3350.melodia.objects;

public class Genre {
    private String genreName;

    public Genre(String genreName) {
        this.genreName = genreName;
    }
    public String getGenreName(){
        return genreName;
    }
    public void setGenreName(String genreName){
        this.genreName = genreName;
    }
}
