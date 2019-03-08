package comp3350.melodia.presentation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
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
                                              PlaylistSongsAdapter.OnStartDragListener {

    private List<Song> songList;
    private Playlist thePlaylist;

    private RecyclerView myRecyclerView;
    private PlaylistSongsAdapter myAdapter;
    private RecyclerView.LayoutManager myLinearLayout;

    private Toast toastMessage;
    private ItemTouchHelper touchHelper;

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