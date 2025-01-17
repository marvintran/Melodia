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
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import comp3350.melodia.R;
import comp3350.melodia.application.Services;
import comp3350.melodia.logic.AccessPlaylist;
import comp3350.melodia.logic.AccessSong;
import comp3350.melodia.objects.Playlist;
import comp3350.melodia.objects.Song;

public class QueueFragment
       extends Fragment
       implements QueueAdapter.OnSongClickedListener,
                  QueueAdapter.OnSongLongClickedListener,
                  QueueAdapter.OnStartDragListener,
                  View.OnCreateContextMenuListener {

    private List<Song> songList;
    private Toast toastMessage;
    private ItemTouchHelper touchHelper;
    private Song songClicked;
    private int positionSongClicked;
    private AccessPlaylist accessPlaylist;
    private AccessSong accessSong;
    private QueueAdapter myAdapter;
    private RefreshInterface listener;
    private onPlayerButtonClickedListener playerButtonListener;
    private PlaySongAtPosition playSongAtPositionListener;

    public interface onPlayerButtonClickedListener {
        public void onShowPlayer();
    }

    public interface RefreshInterface{
        public void refreshPlaylists();
    }

    public interface PlaySongAtPosition{
        public void playSongAtPosition(int positionSongClicked);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            playerButtonListener = (onPlayerButtonClickedListener) context;
            listener = (RefreshInterface) context;
            playSongAtPositionListener = (PlaySongAtPosition) context;
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

        // The current playing queue has playlistID = 0.
        accessSong = new AccessSong(Services.getSongPersistence());
        songList = accessSong.getPlaylistSongs(0);

        View v = inflater.inflate(R.layout.queue_fragment, container, false);

        Button showPlayer = v.findViewById(R.id.player);
        showPlayer.setOnClickListener(mButtonClickListener);

        Button shuffle = v.findViewById(R.id.shuffle);
        shuffle.setOnClickListener(shuffleButtonListener);

        return v;
    }

    public static QueueFragment newInstance() {
        QueueFragment fragment = new QueueFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RecyclerView myRecyclerView;
        RecyclerView.LayoutManager myLinearLayout;

        myRecyclerView = (RecyclerView)getView().findViewById(R.id.queue_recycler_view);
        myRecyclerView.setHasFixedSize(true);

        myLinearLayout = new LinearLayoutManager(getActivity());
        myRecyclerView.setLayoutManager(myLinearLayout);

        myAdapter = new QueueAdapter(songList, this, this, this);
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
        inflater.inflate(R.menu.queue_context_menu, menu);

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
        switch (item.getItemId()) {
            case R.id.add_to_playlist:
                if(allPlaylists.size() == 0) {
                    toastMessage = Toast.makeText(getActivity(),
                            "No playlists have been created",
                            Toast.LENGTH_SHORT);
                    toastMessage.show();
                }
                return true;
            case R.id.remove_from_queue:
                accessSong.deletePlaylistSong(0, positionSongClicked);
                updateSongList();
                toastMessage = Toast.makeText(getActivity(),
                                              "Removed " + songTitle,
                                              Toast.LENGTH_SHORT);
                toastMessage.show();
                return true;
            default:
                Playlist playlistClicked = allPlaylists.get(item.getOrder());
                int playlistID = playlistClicked.getPlaylistID();
                int playlistNumSongs = playlistClicked.getNumberOfSongs();
                accessSong.insertPlaylistSong(playlistID, songClicked.getSongID(), playlistNumSongs);

                listener.refreshPlaylists();

                String playlistTitle = playlistClicked.getPlaylistName();
                toastMessage = Toast.makeText(getActivity(),
                        String.format("%s added to %s", songTitle, playlistTitle),
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
        String message = "Playing " + songTitle;

        toastMessage = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
        toastMessage.show();

        playSongAtPositionListener.playSongAtPosition(position);
    }

    public void onSongLongClicked(Song theSong, int position) {
        songClicked = theSong;
        positionSongClicked = position;
    }

    @Override
    public void onStartDrag(SongViewHolder viewHolder) {
        touchHelper.startDrag(viewHolder);
    }

    private View.OnClickListener mButtonClickListener = new View.OnClickListener() {
        public void onClick(View v) {

            playerButtonListener.onShowPlayer();
        }
    };

    private View.OnClickListener shuffleButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
            accessSong.shuffleQueue();
            updateSongList();
            toastMessage = Toast.makeText(getActivity(),
                    "Shuffled Songs",
                    Toast.LENGTH_SHORT);
            toastMessage.show();
        }
    };

    public void updateSongList() {
        songList = accessSong.getPlaylistSongs(0);
        myAdapter.updateSongList(songList);
    }
}