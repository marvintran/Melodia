package comp3350.melodia.presentation;

import android.content.Context;
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
import comp3350.melodia.logic.AccessSong;
import comp3350.melodia.objects.Playlist;
import comp3350.melodia.objects.Song;

// the screen for viewing songs in a playlist
public class PlaylistSongsFragment extends Fragment
                                   implements PlaylistSongsAdapter.OnSongClickedListener,
                                              PlaylistSongsAdapter.OnSongLongClickedListener,
                                              PlaylistSongsAdapter.OnStartDragListener,
                                              View.OnCreateContextMenuListener {

    private List<Song> playlistSongs;
    private Playlist thePlaylist;
    private Toast toastMessage;
    private ItemTouchHelper touchHelper;
    private Song songClicked;
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

        accessPlaylist = new AccessPlaylist();

        Bundle b = getArguments();
        int playlistIndex = b.getInt("exampleInt");

        List<Playlist> allPlaylists = accessPlaylist.getPlaylists();
        thePlaylist = allPlaylists.get(playlistIndex);

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

        myRecyclerView = (RecyclerView)getView().findViewById(R.id.playlist_songs_recycler_view);
        myRecyclerView.setHasFixedSize(true);

        myLinearLayout = new LinearLayoutManager(getActivity());
        myRecyclerView.setLayoutManager(myLinearLayout);

        AccessSong accessSong = new AccessSong();
        playlistSongs = accessSong.getPlaylistSongs(thePlaylist.getPlaylistID());

        myAdapter = new PlaylistSongsAdapter(playlistSongs, this, this, this);
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

        List<Playlist> allPlaylists = accessPlaylist.getPlaylists();

        int count = 0;
        for(Playlist currentPlaylist: allPlaylists) {

            // For every playlist, make a submenu item.
            String playlistTitle = currentPlaylist.getPlaylistName();
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
                Playlist playlistClicked = allPlaylists.get(item.getItemId());
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

    // passing data from Adapter to Fragment
    // https://developer.android.com/guide/components/fragments.html#EventCallbacks
    // https://stackoverflow.com/a/52830847
    public void onSongClicked(Song theSong)
    {
        String songTitle = theSong.getSongName();
        String playlistName = thePlaylist.getPlaylistName();
        String message =
                String.format("Playing playlist \"%s\" at song \"%s\"",
                        playlistName, songTitle);
        toastMessage = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
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