package comp3350.melodia.presentation;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import comp3350.melodia.R;
import comp3350.melodia.logic.AccessPlaylist;
import comp3350.melodia.logic.AccessSong;
import comp3350.melodia.objects.Playlist;
import comp3350.melodia.objects.Song;
import comp3350.melodia.application.Main;


public class LibrarySongsFragment
        extends Fragment
        implements LibrarySongsAdapter.OnSongClickedListener,
                   LibrarySongsAdapter.OnSongLongClickedListener,
                   View.OnCreateContextMenuListener{

    private List<Song> songList;
    private Toast toastMessage;
    private Song songClicked;
    private LibrarySongsAdapter myAdapter;
    private AccessPlaylist accessPlaylist;
    private RefreshInterface listener;

    public interface RefreshInterface{
        public void refreshPlaylists();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (RefreshInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    context.toString() + " must implement OnArticleSelectedListener");
        }
    }

    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {

        copyDatabaseToDevice();
        accessPlaylist = new AccessPlaylist();
        AccessSong accessSong = new AccessSong();

        songList = accessSong.getSongs();

        View v = inflater.inflate(R.layout.fragment_library, container, false);

        Spinner spinner = (Spinner) v.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.filtering_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

        // Spinner click listener. https://stackoverflow.com/a/40465646
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                String filterSelection = parent.getItemAtPosition(position).toString();

                if( filterSelection.equals("Track Name")) {
                    AccessSong accessSong = new AccessSong();
                    List<Song> newSongList = accessSong.getSongs();
                    myAdapter.updateList(newSongList);
                } else if( filterSelection.equals("Artist")) {
                    List<Playlist> allPlaylists = accessPlaylist.getPlaylists();
                    Playlist thePlaylist = allPlaylists.get(0);
                    AccessSong accessSong = new AccessSong();
                    List<Song> newSongList = accessSong.getSongs();// track name
                    //List<Song> newSongList = accessSong.getPlaylistSongs(thePlaylist.getPlaylistID());
                    myAdapter.updateList(newSongList);
                }

                Toast.makeText(parent.getContext(), "Selected: " + filterSelection, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return v;
    }

    public static LibrarySongsFragment newInstance() {
        LibrarySongsFragment fragment = new LibrarySongsFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecyclerView myRecyclerView;

        RecyclerView.LayoutManager myLinearLayout;

        myRecyclerView = (RecyclerView)getView().findViewById(R.id.library_recycler_view);
        myRecyclerView.setHasFixedSize(true);

        myLinearLayout = new LinearLayoutManager(getActivity());
        myRecyclerView.setLayoutManager(myLinearLayout);

        myAdapter = new LibrarySongsAdapter(songList, this, this);
        myRecyclerView.setAdapter(myAdapter);

        registerForContextMenu(myRecyclerView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu,
                                    View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.library_context_menu, menu);

        String songTitle = songClicked.getSongName();
        menu.setHeaderTitle(songTitle);

        MenuItem menuItem = menu.findItem(R.id.add_to_playlist);
        SubMenu subMenu = menuItem.getSubMenu();

        List<Playlist> allPlaylists = accessPlaylist.getPlaylists();

        int count = 0;
        for(Playlist currentPlaylist: allPlaylists) {
            String playlistTitle = currentPlaylist.getPlaylistName();
            subMenu.add(menu.NONE, menu.NONE, count, playlistTitle);
            count++;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.queue:
                String songTitle = songClicked.getSongName();
                toastMessage = Toast.makeText(getActivity(),
                                         "Add to Queue: " + songTitle,
                                              Toast.LENGTH_SHORT);
                toastMessage.show();
                accessPlaylist.updatePlaylist(0, songClicked.getSongID());

                return true;
            case R.id.add_to_playlist:
                toastMessage = Toast.makeText(getActivity(),
                                         "Add to Playlist",
                                              Toast.LENGTH_SHORT);
                toastMessage.show();
                return true;
            default:
                List<Playlist> allPlaylists = accessPlaylist.getPlaylists();
                Playlist playlistClicked = allPlaylists.get(item.getOrder());
                int playlistID = playlistClicked.getPlaylistID();
                accessPlaylist.updatePlaylist(playlistID, songClicked.getSongID());

                listener.refreshPlaylists();

                String songTItle = songClicked.getSongName();
                String title = playlistClicked.getPlaylistName();


                toastMessage = Toast.makeText(getActivity(),
                                         songTItle + " added to " + title,
                                              Toast.LENGTH_SHORT);
                toastMessage.show();
                return super.onContextItemSelected(item);
        }
    }

    // Passing data from Adapter to Fragment.
    // https://developer.android.com/guide/components/fragments.html#EventCallbacks
    // https://stackoverflow.com/a/52830847
    public void onSongClicked(Song theSong)
    {
        String songTitle = theSong.getSongName();
        String message =
                String.format("Added \"%s\" to the queue", songTitle);
        if(toastMessage != null)
            toastMessage.cancel();

        toastMessage = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
        toastMessage.show();
    }

    public void onSongLongClicked(Song theSong)
    {
        songClicked = theSong;
    }

    private void copyDatabaseToDevice() {
        final String DB_PATH = "db";

        String[] assetNames;
        Context context = getActivity ();
        File dataDirectory = context.getDir(DB_PATH, Context.MODE_PRIVATE);
        AssetManager assetManager = getActivity().getAssets();

        try {

            assetNames = assetManager.list(DB_PATH);
            for (int i = 0; i < assetNames.length; i++) {
                assetNames[i] = DB_PATH + "/" + assetNames[i];
            }

            copyAssetsToDirectory(assetNames, dataDirectory);

            Main.setDBPathName(dataDirectory.toString() + "/" + Main.getDBPathName());

        } catch (final IOException ioe) {
            //Messages.warning(this, "Unable to access application data: " + ioe.getMessage());
        }
    }

    public void copyAssetsToDirectory(String[] assets, File directory) throws IOException {
        AssetManager assetManager = getActivity().getAssets();

        for (String asset : assets) {
            String[] components = asset.split("/");
            String copyPath = directory.toString() + "/" + components[components.length - 1];

            char[] buffer = new char[1024];
            int count;

            File outFile = new File(copyPath);

            if (!outFile.exists()) {
                InputStreamReader in = new InputStreamReader(assetManager.open(asset));
                FileWriter out = new FileWriter(outFile);

                count = in.read(buffer);
                while (count != -1) {
                    out.write(buffer, 0, count);
                    count = in.read(buffer);
                }

                out.close();
                in.close();
            }
        }
    }
}