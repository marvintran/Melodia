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
import comp3350.melodia.application.Services;
import comp3350.melodia.logic.AccessPlaylist;
import comp3350.melodia.logic.AccessSong;
import comp3350.melodia.objects.Playlist;
import comp3350.melodia.objects.Song;

public class PlaylistSongsFragment
       extends Fragment
       implements PlaylistSongsAdapter.OnSongClickedListener,
                  PlaylistSongsAdapter.OnSongLongClickedListener,
                  PlaylistSongsAdapter.OnStartDragListener,
                  PlaylistSongsAdapter.GetPlaylistIDListener,
                  View.OnCreateContextMenuListener {

    private List<Song> playlistSongs;
    private Playlist thePlaylist;
    private Toast toastMessage;
    private ItemTouchHelper touchHelper;
    private Song songClicked;
    private int positionSongClicked;
    private AccessPlaylist accessPlaylist;
    private AccessSong accessSong;
    private PlaylistSongsAdapter myAdapter;

    private RefreshInterface listener;
    private ShowPlayer playerListener;
    private PlaySongsFromPlaylist playSongsFromPlaylistListener;

    public interface RefreshInterface{
        public void refreshPlaylists();
    }

    public interface ShowPlayer {
        public void onShowPlayer();
    }

    public interface PlaySongsFromPlaylist{
        public void playSongsFromPlaylist(int positionSongClicked);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (RefreshInterface) context;
            playerListener = (ShowPlayer) context;
            playSongsFromPlaylistListener = (PlaySongsFromPlaylist) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    context.toString() + " must implement OnArticleSelectedListener");
        }
    }

    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {

        accessPlaylist = new AccessPlaylist(Services.getPlaylistPersistence());

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
        RecyclerView.LayoutManager myLinearLayout;

        myRecyclerView = (RecyclerView)getView()
                .findViewById(R.id.playlist_songs_recycler_view);
        myRecyclerView.setHasFixedSize(true);

        myLinearLayout = new LinearLayoutManager(getActivity());
        myRecyclerView.setLayoutManager(myLinearLayout);

        accessSong = new AccessSong(Services.getSongPersistence());
        playlistSongs = accessSong.getPlaylistSongs(thePlaylist.getPlaylistID());

        myAdapter = new PlaylistSongsAdapter(
                playlistSongs, this, this, this, this);
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
        inflater.inflate(R.menu.playlist_songs_context_menu, menu);

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
            subMenu.add(menu.NONE, menu.NONE, count, playlistTitle);
            count++;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        String songTitle = songClicked.getSongName();
        List<Playlist> allPlaylists = accessPlaylist.getPlaylists();
        int playlistNumSongs;
        switch (item.getItemId()) {
            case R.id.play:
                int currPlaylistID = thePlaylist.getPlaylistID();
                List<Song> currPlaylistSongs = accessSong.getPlaylistSongs(currPlaylistID);
                List<Song> queueSongs = accessSong.getPlaylistSongs(0);
                accessSong.replaceQueueWithPlaylist(currPlaylistSongs, queueSongs);

                playerListener.onShowPlayer();

                return true;
            case R.id.queue:
                toastMessage = Toast.makeText(getActivity(),
                                              "Queued " + songTitle,
                                              Toast.LENGTH_SHORT);
                toastMessage.show();
                Playlist queuePlaylist = accessPlaylist.getSpecificPlaylist(0);
                playlistNumSongs = queuePlaylist.getNumberOfSongs();
                accessSong.insertPlaylistSong(0,
                                                  songClicked.getSongID(),
                                                  playlistNumSongs);

                return true;
            case R.id.add_to_playlist:
                if(allPlaylists.size() == 0) {
                    toastMessage = Toast.makeText(getActivity(),
                            "No playlists have been created",
                            Toast.LENGTH_SHORT);
                    toastMessage.show();
                }
                return true;
            case R.id.removed_from_playlist:
                accessSong.deletePlaylistSong(thePlaylist.getPlaylistID(),
                                                  positionSongClicked);
                updateSongList();
                listener.refreshPlaylists();
                toastMessage = Toast.makeText(getActivity(),
                                              "Removed " + songTitle,
                                              Toast.LENGTH_SHORT);
                toastMessage.show();
                return true;
            default:
                Playlist playlistClicked = allPlaylists.get(item.getOrder());
                int playlistID = playlistClicked.getPlaylistID();
                playlistNumSongs = playlistClicked.getNumberOfSongs();
                accessSong.insertPlaylistSong(playlistID,
                                                  songClicked.getSongID(),
                                                  playlistNumSongs);

                listener.refreshPlaylists();

                String playlistName = playlistClicked.getPlaylistName();

                toastMessage = Toast.makeText(getActivity(),
                                              songTitle + " added to " + playlistName,
                                              Toast.LENGTH_SHORT);
                toastMessage.show();
                return super.onContextItemSelected(item);
        }
    }

    // Passing data from Adapter to Fragment.
    // https://developer.android.com/guide/components/fragments.html#EventCallbacks
    // https://stackoverflow.com/a/52830847
    public void onSongClicked(Song theSong, int position) {
        positionSongClicked = position;
        String songTitle = theSong.getSongName();
        String playlistName = thePlaylist.getPlaylistName();
        String message =
                String.format("Playing playlist \"%s\" at song \"%s\"",
                        playlistName, songTitle);

        int currPlaylistID = thePlaylist.getPlaylistID();
        List<Song> currPlaylistSongs = accessSong.getPlaylistSongs(currPlaylistID);
        List<Song> queueSongs = accessSong.getPlaylistSongs(0);
        accessSong.replaceQueueWithPlaylist(currPlaylistSongs, queueSongs);

        playerListener.onShowPlayer();
        playSongsFromPlaylistListener.playSongsFromPlaylist(position);

        toastMessage = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
        toastMessage.show();
    }

    public void onSongLongClicked(Song theSong, int position) {
        songClicked = theSong;
        positionSongClicked = position;
        toastMessage = Toast.makeText(getActivity(),
                "Long Clicked: Open Context Menu",
                Toast.LENGTH_SHORT);
        toastMessage.show();
    }

    @Override
    public void onStartDrag(SongViewHolder viewHolder) {
        touchHelper.startDrag(viewHolder);
    }

    public void updateSongList() {
        playlistSongs = accessSong.getPlaylistSongs(thePlaylist.getPlaylistID());
        myAdapter.updateSongList(playlistSongs);
    }

    public int getPlaylistSelectedID() {
        return thePlaylist.getPlaylistID();
    }
}