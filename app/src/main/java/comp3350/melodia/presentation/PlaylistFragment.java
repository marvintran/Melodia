package comp3350.melodia.presentation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import comp3350.melodia.R;
import comp3350.melodia.objects.Album;
import comp3350.melodia.objects.Artist;
import comp3350.melodia.objects.Playlist;
import comp3350.melodia.objects.Song;

public class PlaylistFragment extends Fragment {

    ArrayList<Playlist> allPlaylists;

    RecyclerView myRecyclerView;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager myLinearLayout;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_playlists_list, container, false);

        Button newPlaylist = (Button) v.findViewById(R.id.new_playlist);
        newPlaylist.setOnClickListener(mButtonClickListener);
        return v;
    }

    public static PlaylistFragment newInstance() {
        PlaylistFragment fragment = new PlaylistFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Song: String songName, int songTime, Artist artist, Album album, int trackNumber, File songData
        // Playlist: String playlistName, int playlistTime, int numberOfSongs, Collection<Song> songs, File playlistData

        // hardcode some songs to be put into the playlists for now
        ArrayList<Song> onlyOneSong = new ArrayList<Song>();
        ArrayList<Song> lotsOfSongs = new ArrayList<Song>();


        // the song put into onlyOneSong
        Song oneSong = new Song( "Awesome Song", 186,
                new Artist("Great Artist", new ArrayList<Album>()),
                new Album("Amazing Album", new ArrayList<Song>()), 1, "");

        // the songs put into lotsOfSongs
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

        // add oneSong into onlyOneSong ArrayList
        onlyOneSong.add(oneSong);

        // add all the songs into the lotsOfSongs ArrayList
        for(int i = 0; i<songs.length; i++)
        {
            lotsOfSongs.add(songs[i]);
        }

        allPlaylists = new ArrayList<Playlist>();

        // hardcode some playlists to display for now
        Playlist workout = new Playlist("Workout", 123, lotsOfSongs.size(), lotsOfSongs, "");
        Playlist oneSongPlaylist = new Playlist("Playlist 1", 180, onlyOneSong.size(), onlyOneSong, "");

        // add the playlists to the allPlaylists ArrayList
        allPlaylists.add(workout);
        allPlaylists.add(oneSongPlaylist);




        // obtain a handle to the recyclerView
        myRecyclerView = (RecyclerView)getView().findViewById(R.id.my_recycler_view);
        // make sure the size does not change for improved performance
        myRecyclerView.setHasFixedSize(true);

        // making the RecyclerView look like a ListView
        myLinearLayout = new LinearLayoutManager(getActivity());
        myRecyclerView.setLayoutManager(myLinearLayout);

        // define the adapter that will communicate between the dataset and the RecycleView
        myAdapter = new PlaylistRecyclerViewAdapter(allPlaylists);
        myRecyclerView.setAdapter(myAdapter);
    }

    // https://stackoverflow.com/questions/25251876/onclick-fragments-could-not-find-method
    private View.OnClickListener mButtonClickListener = new View.OnClickListener() {
        // Alert dialog
        // http://android.pcsalt.com/create-alertdialog-with-custom-layout-using-xml-layout/
        public void onClick(View v) {
            LayoutInflater inflater = getLayoutInflater();
            View alertLayout = inflater.inflate(R.layout.new_playlist_dialog, null);
            final EditText playlistTitle = alertLayout.findViewById(R.id.playlist_title);

            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
            alert.setTitle("Create new Playlist");
            alert.setView(alertLayout);

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            alert.setPositiveButton("Create", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String title = playlistTitle.getText().toString();
                    // print out Toast message to give user feedback
                    Toast.makeText(getActivity(), "Created Playlist: " + title, Toast.LENGTH_SHORT).show();

                    // create a new playlist with this playlist title
                    ArrayList<Song> emptyList = new ArrayList<Song>();
                    Playlist emptyPlaylist = new Playlist(title, 0, 0, emptyList, "");
                    allPlaylists.add(emptyPlaylist);
                }
            });
            AlertDialog dialog = alert.create();
            dialog.show();
        }
    };
}