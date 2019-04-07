package comp3350.melodia.presentation;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import comp3350.melodia.R;
import comp3350.melodia.application.Services;
import comp3350.melodia.logic.AccessPlaylist;
import comp3350.melodia.logic.AccessSong;
import comp3350.melodia.objects.Playlist;
import comp3350.melodia.objects.Song;

public class PlaylistFragment
       extends Fragment
       implements PlaylistAdapter.OnPlaylistClickedListener,
                  PlaylistAdapter.OnPlaylistLongClickedListener {

    private List<Playlist> allPlaylists;
    private AccessPlaylist accessPlaylist;
    private Toast toastMessage;
    private OnPlaylistClickedListener listener;
    private Playlist playlistClicked;
    private PlaylistAdapter myAdapter;

    public interface OnPlaylistClickedListener {
        public void onPlaylistClicked(int playlistIndex);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnPlaylistClickedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    context.toString() + " must implement OnArticleSelectedListener");
        }
    }

    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_playlist, container, false);

        FloatingActionButton newPlaylist = v.findViewById(R.id.new_playlist);
        newPlaylist.setOnClickListener(mButtonClickListener);
        return v;
    }

    public static PlaylistFragment newInstance() {
        PlaylistFragment fragment = new PlaylistFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RecyclerView myRecyclerView;

        RecyclerView.LayoutManager myLinearLayout;

        accessPlaylist = new AccessPlaylist(Services.getPlaylistPersistence());
        allPlaylists = accessPlaylist.getPlaylists();

        myRecyclerView = (RecyclerView)getView()
                .findViewById(R.id.playlist_recycler_view);
        myRecyclerView.setHasFixedSize(true);

        myLinearLayout = new LinearLayoutManager(getActivity());
        myRecyclerView.setLayoutManager(myLinearLayout);

        myAdapter = new PlaylistAdapter(allPlaylists, this, this);
        myRecyclerView.setAdapter(myAdapter);

        registerForContextMenu(myRecyclerView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu,
                                    View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.playlist_context_menu, menu);

        String playlistTitle = playlistClicked.getPlaylistName();
        menu.setHeaderTitle(playlistTitle);

        MenuItem menuItem = menu.findItem(R.id.add_tracks_to_playlist);
        SubMenu subMenu = menuItem.getSubMenu();

        int count = 0;
        for(Playlist currentPlaylist: allPlaylists) {
            String currPlaylistTitle = currentPlaylist.getPlaylistName();
            subMenu.add(menu.NONE, menu.NONE, count, currPlaylistTitle);
            count++;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        List<Song> playlistClickedSongs;
        AccessSong accessSong = new AccessSong(Services.getSongPersistence());
        String playlistTitle = playlistClicked.getPlaylistName();
        List<Playlist> allPlaylists = accessPlaylist.getPlaylists();
        int numSongs = playlistClicked.getNumberOfSongs();
        int playlistClickedID = playlistClicked.getPlaylistID();
        String songOrSongs;

        if(numSongs == 1)
            songOrSongs = "song";
        else
            songOrSongs = "songs";

        switch (item.getItemId()) {
            case R.id.queue:
                playlistClickedSongs = accessSong.getPlaylistSongs(playlistClickedID);
                Playlist queuePlaylist = accessPlaylist.getSpecificPlaylist(0);
                for(int i = 0; i< playlistClickedSongs.size(); i++) {
                    accessSong.insertPlaylistSong(0,
                            playlistClickedSongs.get(i).getSongID(),
                            queuePlaylist.getNumberOfSongs());
                }

                toastMessage = Toast.makeText(getActivity(),
                        "Queued " + playlistTitle,
                        Toast.LENGTH_SHORT);
                toastMessage.show();

                return true;
            case R.id.add_tracks_to_playlist:
                if(allPlaylists.size() == 0) {
                    toastMessage = Toast.makeText(getActivity(),
                            "No playlists have been created",
                            Toast.LENGTH_SHORT);
                    toastMessage.show();
                }
                return true;
            case R.id.delete:
                toastMessage = Toast.makeText(getActivity(),
                        "Deleted " + playlistTitle,
                        Toast.LENGTH_SHORT);
                toastMessage.show();

                accessPlaylist.deletePlaylist(playlistClicked.getPlaylistID());
                updatePlaylists();

                return true;
            default:
                Playlist submenuPlaylistClicked = allPlaylists.get(item.getOrder());
                int submenuPlaylistID = submenuPlaylistClicked.getPlaylistID();

                playlistClickedSongs = accessSong.getPlaylistSongs(playlistClickedID);
                for(int i = 0; i< playlistClickedSongs.size(); i++) {
                    accessSong.insertPlaylistSong(submenuPlaylistID,
                            playlistClickedSongs.get(i).getSongID(),
                            playlistClicked.getNumberOfSongs());
                }
                updatePlaylists();

                toastMessage = Toast.makeText(getActivity(),
                        String.format("%d %s added to %s",
                                numSongs, songOrSongs, playlistTitle),
                        Toast.LENGTH_SHORT);
                toastMessage.show();

                return super.onContextItemSelected(item);
        }
    }

    // https://stackoverflow.com/questions/25251876/onclick-fragments-could-not-find-method
    private View.OnClickListener mButtonClickListener = new View.OnClickListener() {
        // Alert dialog
        // http://android.pcsalt.com/create-alertdialog-with-custom-layout-using-xml-layout/
        public void onClick(View v) {
            LayoutInflater inflater = getLayoutInflater();
            View alertLayout = inflater.inflate(
                    R.layout.new_playlist_dialog, null);
            final EditText playlistTitle = alertLayout.findViewById(
                    R.id.playlist_title);

            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
            alert.setTitle("Create new Playlist");
            alert.setView(alertLayout);

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            alert.setPositiveButton("Create", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String title = playlistTitle.getText().toString();
                    accessPlaylist.insertPlaylist(title);
                    updatePlaylists();
                    toastMessage = Toast.makeText(getActivity(),
                                                  "Created playlist "+title,
                                                  Toast.LENGTH_SHORT);
                    toastMessage.show();
                }
            });
            AlertDialog dialog = alert.create();
            dialog.show();
        }
    };


    // Passing data from Adapter to Fragment.
    // https://developer.android.com/guide/components/fragments.html#EventCallbacks
    // https://stackoverflow.com/a/52830847
    public void onPlaylistClicked(int playlistIndex) {
        listener.onPlaylistClicked(playlistIndex);
    }

    public void onPlaylistLongClicked(int playlistIndex) {
        playlistClicked = allPlaylists.get(playlistIndex);
    }

    public void updatePlaylists()
    {
        allPlaylists = accessPlaylist.getPlaylists();
        myAdapter.updateItems(allPlaylists);
    }
}