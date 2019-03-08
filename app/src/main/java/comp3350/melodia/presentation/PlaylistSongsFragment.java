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
    private Toast toastMessage;
    private ItemTouchHelper touchHelper;
    private Song songClicked;

    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {

        Bundle b = getArguments();
        int playlistIndex = b.getInt("exampleInt");

        AccessPlaylist accessPlaylist = new AccessPlaylist();
        List<Playlist> allPlaylists = accessPlaylist.getPlaylists();
        thePlaylist = allPlaylists.get(playlistIndex);

        songList = thePlaylist.getSongs();

        return inflater.inflate(
                R.layout.fragment_playlist_songs, container, false);
    }

    public static PlaylistSongsFragment newInstance() {
        PlaylistSongsFragment fragment = new PlaylistSongsFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RecyclerView myRecyclerView;
        PlaylistSongsAdapter myAdapter;
        RecyclerView.LayoutManager myLinearLayout;

        myRecyclerView = (RecyclerView)getView().findViewById(R.id.my_recycler_view);
        myRecyclerView.setHasFixedSize(true);

        myLinearLayout = new LinearLayoutManager(getActivity());
        myRecyclerView.setLayoutManager(myLinearLayout);

        myAdapter = new PlaylistSongsAdapter(songList, this, this, this);
        myRecyclerView.setAdapter(myAdapter);

        SwipeAndDragHelper swipeAndDragHelper = new SwipeAndDragHelper(myAdapter);
        touchHelper = new ItemTouchHelper(swipeAndDragHelper);
        touchHelper.attachToRecyclerView(myRecyclerView);

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

        // We will dynamically create submenu items for adding to playlists.
        MenuItem menuItem = menu.findItem(R.id.add_to_playlist);
        SubMenu subMenu = menuItem.getSubMenu();

        AccessPlaylist accessPlaylist = new AccessPlaylist();
        List<Playlist> allPlaylists = accessPlaylist.getPlaylists();

        int count = 0;
        for(Playlist currentPlaylist: allPlaylists) {

            // For every playlist, make a submenu item.
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
                AccessPlaylist accessPlaylist2 = new AccessPlaylist();
                List<Playlist> allPlaylists2 = accessPlaylist2.getPlaylists();

                Playlist playlistClicked2 = allPlaylists2.get(item.getItemId());
                List<Song> playlistSongs2 = playlistClicked2.getSongs();

                playlistSongs2.add(songClicked);
                playlistClicked2.setSongs(playlistSongs2);

                String songTitle2 = songClicked.getSongName();
                String title2 = playlistClicked2.getPlaylistName();
                accessPlaylist2.updatePlaylist(playlistClicked2);

                toastMessage = Toast.makeText(getActivity(),
                                         songTitle2 + " added to " + title2,
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
        String playlistName = thePlaylist.getPlaylistName();

        toastMessage = Toast.makeText(getActivity(),
                                 "Playing playlist \"" + playlistName +
                                      "\" at song \"" + songTitle + "\"",
                                      Toast.LENGTH_SHORT);
        toastMessage.show();
    }

    public void onSongLongClicked(Song theSong)
    {
        songClicked = theSong;

        toastMessage = Toast.makeText(getActivity(),
                                 "Long Clicked: Open Context Menu",
                                      Toast.LENGTH_SHORT);
        toastMessage.show();
    }

    @Override
    public void onStartDrag(SongViewHolder viewHolder) {
        touchHelper.startDrag(viewHolder);
    }
}