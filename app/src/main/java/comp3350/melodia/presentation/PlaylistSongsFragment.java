package comp3350.melodia.presentation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import comp3350.melodia.R;
import comp3350.melodia.logic.AccessPlaylist;
import comp3350.melodia.objects.Playlist;
import comp3350.melodia.objects.Song;
import comp3350.melodia.objects.SongViewHolder;

// the screen for viewing songs in a playlist
public class PlaylistSongsFragment extends Fragment
                                   implements PlaylistSongsAdapter.OnSongClickedListener,
                                              PlaylistSongsAdapter.OnSongLongClickedListener,
                                              PlaylistSongsAdapter.OnStartDragListener,
                                              View.OnCreateContextMenuListener {

    private List<Song> songList;
    private Playlist thePlaylist;

    private RecyclerView myRecyclerView;
    private PlaylistSongsAdapter myAdapter;
    private RecyclerView.LayoutManager myLinearLayout;

    private Toast toastMessage;
    private ItemTouchHelper touchHelper;

    private Song songClicked;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // we receive the integer that the MainActivity passed to this fragment
        // this integer is the index of the playlist in our List<Playlist>
        // that we want to see the songs of
        Bundle b = getArguments();
        int playlistIndex = b.getInt("exampleInt");

        // get the list of playlists, then get the playlist at indexPlaylist
        AccessPlaylist accessPlaylist = new AccessPlaylist();
        List<Playlist> allPlaylists = accessPlaylist.getPlaylists();
        thePlaylist = allPlaylists.get(playlistIndex);

        // get the List<Song> contained in the playlist of indexPlaylist
        // this list of songs is the one we will display
        songList = thePlaylist.getSongs();

        return inflater.inflate(R.layout.fragment_playlist_songs, container, false);
    }

    public static PlaylistSongsFragment newInstance() {
        PlaylistSongsFragment fragment = new PlaylistSongsFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // obtain a handle to the recyclerView
        myRecyclerView = (RecyclerView)getView().findViewById(R.id.my_recycler_view);
        // make sure the size does not change for improved performance
        myRecyclerView.setHasFixedSize(true);

        // making the RecyclerView look like a ListView
        myLinearLayout = new LinearLayoutManager(getActivity());
        myRecyclerView.setLayoutManager(myLinearLayout);

        // define the adapter that will communicate between the dataset and the RecycleView
        myAdapter = new PlaylistSongsAdapter(songList, this, this, this);
        myRecyclerView.setAdapter(myAdapter);

        SwipeAndDragHelper swipeAndDragHelper = new SwipeAndDragHelper(myAdapter);
        touchHelper = new ItemTouchHelper(swipeAndDragHelper);
        touchHelper.attachToRecyclerView(myRecyclerView);

        registerForContextMenu(myRecyclerView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.library_context_menu, menu);

        String songTitle = songClicked.getSongName();
        menu.setHeaderTitle(songTitle);

        // dynamically create submenu items for adding to playlists
        MenuItem menuItem = menu.findItem(R.id.add_to_playlist);
        SubMenu subMenu = menuItem.getSubMenu();

        // get all playlists
        AccessPlaylist accessPlaylist = new AccessPlaylist();
        List<Playlist> allPlaylists = accessPlaylist.getPlaylists();

        int count = 0;
        for(Playlist currentPlaylist: allPlaylists) {

            // for every playlislts, make a submenu item
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
                toastMessage = Toast.makeText(getActivity(), "Add to Queue: " + songTitle, Toast.LENGTH_SHORT);;
                toastMessage.show();

                // add the song that was long clicked to the queue



                return true;
            case R.id.add_to_playlist:
                toastMessage = Toast.makeText(getActivity(), "Add to Playlist", Toast.LENGTH_SHORT);;
                toastMessage.show();

                // open another context menu where menu options are the titles of our playlists

                return true;
            default:
                AccessPlaylist accessPlaylist2 = new AccessPlaylist();
                List<Playlist> allPlaylists2 = accessPlaylist2.getPlaylists();

                Playlist playlistClicked2 = allPlaylists2.get(item.getItemId());
                List<Song> playlistSongs2 = playlistClicked2.getSongs();

                playlistSongs2.add(songClicked);
                playlistClicked2.setSongs(playlistSongs2);

                String songTitle2 = songClicked.getSongName();
                String title2 = playlistClicked2.getPlaylistName();
                accessPlaylist2.updatePlaylist(playlistClicked2);

                toastMessage = Toast.makeText(getActivity(), songTitle2 + " added to "+title2, Toast.LENGTH_SHORT);;
                toastMessage.show();
                return super.onContextItemSelected(item);
        }
    }

    // passing data from Adapter to Fragment
    // https://developer.android.com/guide/components/fragments.html#EventCallbacks
    // https://stackoverflow.com/a/52830847

    // clicking on a song in a playlist should:
    // clear the queue and add the songs in this playlist to the queue
    // then start playing this playlist at the song the user clicked
    public void onSongClicked(Song theSong)
    {
        String songTitle = theSong.getSongName();
        String playlistName = thePlaylist.getPlaylistName();

        toastMessage = Toast.makeText(getActivity(), "Playing playlist \"" + playlistName + "\" at song \"" + songTitle + "\"", Toast.LENGTH_SHORT);;
        toastMessage.show();

        // clear current queue


        // add the songList to the currently playing queue


        // start playing songs in the currently playing queue at the song the user clicked (Song theSong)


    }

    // long clicking a song in the playlist should open a context menu with various options
    public void onSongLongClicked(Song theSong)
    {
        // save the song clicked in this class so the context menu can do stuff with it later
        songClicked = theSong;

        toastMessage = Toast.makeText(getActivity(), "Long Clicked: Open Context Menu", Toast.LENGTH_SHORT);
        toastMessage.show();

        // open context menu,

        // option to add this song to the currently playing queue
        // option to add this song to a playlist
        // option to delete this song from the playlist

    }

    @Override
    public void onStartDrag(SongViewHolder viewHolder) {
        touchHelper.startDrag(viewHolder);
    }
}