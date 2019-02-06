package comp3350.melodia.presentation;
import comp3350.melodia.R;
import comp3350.melodia.objects.Song;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class LibraryActivity extends AppCompatActivity {

    ArrayList<Song> songLibrary = new ArrayList<Song>();

    RecyclerView myRecyclerView;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager myLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        // hardcode some songs to display for now
        Song song1 = new Song( "Nice For What", "Drake",180);
        Song song2 = new Song( "no tears left to cry", "Ariana Grande", 181);
        Song song3 = new Song( "Honey", "Drake", 183);
        Song song4 = new Song( "Honey", "Robyn", 190);
        Song song5 = new Song( "Sicko Mode", "Travis Scott", 188);
        Song song6 = new Song( "Make Me Feel", "Janelle Monáe", 187);
        Song song7 = new Song( "Shallow", "Lady Gaga and Bradley Cooper", 186);
        Song song8 = new Song( "This Is America", "Childish Gambino", 186);
        Song song9 = new Song( "I Like It", "Cardi B", 189);
        Song song10 = new Song( "Love It If We Made It", "The 1975", 187);

        songLibrary.add(song1);
        songLibrary.add(song2);
        songLibrary.add(song3);
        songLibrary.add(song4);
        songLibrary.add(song5);
        songLibrary.add(song6);
        songLibrary.add(song7);
        songLibrary.add(song8);
        songLibrary.add(song9);
        songLibrary.add(song10);
        songLibrary.add(song1);
        songLibrary.add(song2);
        songLibrary.add(song3);
        songLibrary.add(song4);
        songLibrary.add(song5);
        songLibrary.add(song6);
        songLibrary.add(song7);
        songLibrary.add(song8);
        songLibrary.add(song9);
        songLibrary.add(song10);

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

