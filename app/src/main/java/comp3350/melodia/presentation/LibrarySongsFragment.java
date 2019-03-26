package comp3350.melodia.presentation;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {

        copyDatabaseToDevice();

        AccessSong accessSong = new AccessSong();

        songList = accessSong.getSongs();

        View v = inflater.inflate(R.layout.fragment_library, container, false);

        Spinner spinner = (Spinner) v.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.filtering_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

        // Spinner click listener https://stackoverflow.com/a/40465646
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                // On selecting a spinner item
                String filterSelection = parent.getItemAtPosition(position).toString();

                if( filterSelection.equals("Track name"))
                {
                    AccessSong accessSong = new AccessSong();
                    List<Song> newSongList = accessSong.getSongs();
                    myAdapter.updateList(newSongList);
                }
                else if( filterSelection.equals("Artist"))
                {
                    // currently just replaces the list with the songs in the first playlsit
                    AccessPlaylist accessPlaylist = new AccessPlaylist();
                    List<Playlist> allPlaylists = accessPlaylist.getPlaylists();
                    Playlist thePlaylist = allPlaylists.get(0);
                    List<Song> newSongList = thePlaylist.getSongs();
                    myAdapter.updateList(newSongList);
                }
                // Showing selected spinner item
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

        myRecyclerView = (RecyclerView)getView().findViewById(R.id.my_recycler_view);
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

        AccessPlaylist accessPlaylist = new AccessPlaylist();
        List<Playlist> allPlaylists = accessPlaylist.getPlaylists();

        int count = 0;
        for(Playlist currentPlaylist: allPlaylists) {
            String playlistTitle = currentPlaylist.getPlaylistName();
            String titleNoSpaces = playlistTitle.replaceAll(" ", "_");
            subMenu.add(menu.NONE, count, menu.NONE, playlistTitle);
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
                // Todo: Add the song that was long clicked to the queue.

                return true;
            case R.id.add_to_playlist:
                toastMessage = Toast.makeText(getActivity(),
                                         "Add to Playlist",
                                              Toast.LENGTH_SHORT);
                toastMessage.show();
                return true;
            default:
                AccessPlaylist accessPlaylist = new AccessPlaylist();
                List<Playlist> allPlaylists = accessPlaylist.getPlaylists();

                Playlist playlistClicked = allPlaylists.get(item.getItemId());
                List<Song> playlistSongs = playlistClicked.getSongs();

                playlistSongs.add(songClicked);
                playlistClicked.setSongs(playlistSongs);

                String songTItle = songClicked.getSongName();
                String title = playlistClicked.getPlaylistName();
                accessPlaylist.updatePlaylist(playlistClicked);

                toastMessage = Toast.makeText(getActivity(),
                                         songTItle + " added to " + title,
                                              Toast.LENGTH_SHORT);
                toastMessage.show();
                return super.onContextItemSelected(item);
        }
    }

    // passing data from Adapter to Fragment
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

        // Todo: Add this song to the current queue and play it.

    }

    public void onSongLongClicked(Song theSong)
    {
        songClicked = theSong;

        toastMessage = Toast.makeText(getActivity(),
                                 "Long Clicked: Open Context Menu",
                                      Toast.LENGTH_SHORT);
        toastMessage.show();
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