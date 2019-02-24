package comp3350.melodia.presentation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.List;

import comp3350.melodia.R;
import comp3350.melodia.logic.AccessSong;
import comp3350.melodia.objects.Song;

// this is the song library screen
public class HomeFragment extends Fragment implements LibraryRecyclerViewAdapter.OnSongClickedListener,
                                                      LibraryRecyclerViewAdapter.OnSongLongClickedListener,
                                                      View.OnCreateContextMenuListener{

    private List<Song> songList;
    private AccessSong accessSong;

    private RecyclerView myRecyclerView;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager myLinearLayout;

    private Toast toastMessage;

    private Song songClicked;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        accessSong = new AccessSong();
        songList = accessSong.getSongs();
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
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
        myAdapter = new LibraryRecyclerViewAdapter(songList, this, this);
        myRecyclerView.setAdapter(myAdapter);

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
                return super.onContextItemSelected(item);
        }
    }

    // passing data from Adapter to Fragment
    // https://developer.android.com/guide/components/fragments.html#EventCallbacks
    // https://stackoverflow.com/a/52830847

    // when we click on a song in the library, it queues the song and starts playing it
    public void onSongClicked(Song theSong)
    {
        // tell user the song clicked was added to the queue
        String songTitle = theSong.getSongName();

        // cancel the current toast message if there is one currently displaying
        // this way we don't have to wait for the previous message to fully complete it's animation
        if(toastMessage != null)
            toastMessage.cancel();

        toastMessage = Toast.makeText(getActivity(), "Added \"" + songTitle + "\" to the queue", Toast.LENGTH_SHORT);;
        toastMessage.show();

        // add this song to the current queue and play it

    }

    // long clicking a song in the library should open a context menu with various options
    public void onSongLongClicked(Song theSong)
    {
        // save the song clicked in this class so the context menu can do stuff with it later
        songClicked = theSong;

        toastMessage = Toast.makeText(getActivity(), "Long Clicked: Open Context Menu", Toast.LENGTH_SHORT);
        toastMessage.show();

        // open context menu,

        // option to add this song to the currently playing queue
        // option to add this song to a playlist
    }
}