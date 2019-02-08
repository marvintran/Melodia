package comp3350.melodia.presentation;
import comp3350.melodia.R;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;

import comp3350.melodia.objects.Song;
import comp3350.melodia.objects.Artist;
import comp3350.melodia.objects.Album;

public class LibraryActivity extends AppCompatActivity {

    ArrayList<Song> songLibrary;

    RecyclerView myRecyclerView;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager myLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        // hardcode some songs to display for now
        Song[] songs = new Song[]{
                new Song(
                        "Nice For What", 180,
                        new Artist("Drake", new ArrayList<Album>()),
                        new Album("Name", new ArrayList<Song>()), 1, " "),
                new Song( "no tears left to cry", 181,
                        new Artist( "Ariana Grande", new ArrayList<Album>()),
                        new Album("SomeName", new ArrayList<Song>()), 2, " "),
                new Song( "Honey", 183,
                        new Artist("Drake", new ArrayList<Album>()),
                        new Album("nifivn", new ArrayList<Song>()), 3, ""),
                new Song( "Honey", 183,
                        new Artist("Drake", new ArrayList<Album>()),
                        new Album("ngirjvn", new ArrayList<Song>()), 4, ""),
                new Song( "Honey", 190,
                        new Artist("Robyn", new ArrayList<Album>()),
                        new Album(" fecnuic", new ArrayList<Song>()), 5, ""),
                new Song( "Sicko Mode", 183,
                        new Artist("Travis Scott", new ArrayList<Album>()),
                        new Album(" nfcic", new ArrayList<Song>()), 6, ""),
                new Song( "Make Me Feel", 187,
                        new Artist("Janelle Monáe", new ArrayList<Album>()),
                        new Album("Name", new ArrayList<Song>()), 7, ""),
                new Song( "Shallow", 186,
                        new Artist("Lady Gaga and Bradley Cooper", new ArrayList<Album>()),
                        new Album("Born to be a star...", new ArrayList<Song>()), 8, ""),
                new Song( "This Is America",186,
                        new Artist("Childish Gambino", new ArrayList<Album>()),
                        new Album("nfjciuvf", new ArrayList<Song>()), 9, ""),
                new Song( "I Like It", 189,
                        new Artist("Cardi B", new ArrayList<Album>()),
                        new Album("nevoenv", new ArrayList<Song>()), 10, ""),
                new Song( "Love It If We Made It", 187,
                        new Artist("The 1975", new ArrayList<Album>()),
                        new Album("necionnv", new ArrayList<Song>()), 11, "")
        };

        songLibrary = new ArrayList<Song>(songLibrary);


        // obtain a handle to the recyclerView
        myRecyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
        // make sure the size does not change for improved performance
        myRecyclerView.setHasFixedSize(true);

        // making the RecyclerView look like a ListView
        myLinearLayout = new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(myLinearLayout);

        // define the adapter that will communicate between the dataset and the RecycleView
        myAdapter = new RecyclerViewAdapter(songLibrary);
        myRecyclerView.setAdapter(myAdapter);
    }

}

