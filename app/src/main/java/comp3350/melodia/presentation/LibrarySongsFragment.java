package comp3350.melodia.presentation;

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
import android.widget.Toast;

import java.util.List;

import comp3350.melodia.R;
import comp3350.melodia.logic.AccessPlaylist;
import comp3350.melodia.logic.AccessSong;
import comp3350.melodia.objects.Playlist;
import comp3350.melodia.objects.Song;

public class LibrarySongsFragment
        extends Fragment
        implements LibrarySongsAdapter.OnSongClickedListener,
                   LibrarySongsAdapter.OnSongLongClickedListener,
                   View.OnCreateContextMenuListener{

    private List<Song> songList;
    private Toast toastMessage;
    private Song songClicked;

    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {

        AccessSong accessSong = new AccessSong();
        songList = accessSong.getSongs();
        return inflater.inflate(R.layout.fragment_library, container, false);
    }

    public static LibrarySongsFragment newInstance() {
        LibrarySongsFragment fragment = new LibrarySongsFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecyclerView myRecyclerView;
        RecyclerView.Adapter myAdapter;
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

        if(toastMessage != null)
            toastMessage.cancel();

        toastMessage = Toast.makeText(getActivity(),
                                 "Added \"" + songTitle + "\" to the queue",
                                      Toast.LENGTH_SHORT);
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
}