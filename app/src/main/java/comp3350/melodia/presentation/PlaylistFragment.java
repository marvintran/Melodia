package comp3350.melodia.presentation;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import comp3350.melodia.R;
import comp3350.melodia.logic.AccessPlaylist;
import comp3350.melodia.objects.Playlist;
import comp3350.melodia.objects.Song;

// the screen for viewing all of the playlists
public class PlaylistFragment extends Fragment implements PlaylistAdapter.OnPlaylistClickedListener,
                                                          PlaylistAdapter.OnPlaylistLongClickedListener {

    private List<Playlist> allPlaylists;

    private RecyclerView myRecyclerView;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager myLinearLayout;
    private AccessPlaylist accessPlaylist;

    private Toast toastMessage;

    OnPlaylistClickedListener listener;

    public interface OnPlaylistClickedListener {
        public void onPlaylistClicked(int playlistIndex);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnPlaylistClickedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnArticleSelectedListener");
        }
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

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

        // get playlists from persistence
        accessPlaylist = new AccessPlaylist();
        allPlaylists = accessPlaylist.getPlaylists();

        // obtain a handle to the recyclerView
        myRecyclerView = (RecyclerView)getView().findViewById(R.id.my_recycler_view);
        // make sure the size does not change for improved performance
        myRecyclerView.setHasFixedSize(true);

        // making the RecyclerView look like a ListView
        myLinearLayout = new LinearLayoutManager(getActivity());
        myRecyclerView.setLayoutManager(myLinearLayout);

        // define the adapter that will communicate between the dataset and the RecycleView
        myAdapter = new PlaylistAdapter(allPlaylists, this, this);
        myRecyclerView.setAdapter(myAdapter);
    }

    // https://stackoverflow.com/questions/25251876/onclick-fragments-could-not-find-method
    private View.OnClickListener mButtonClickListener = new View.OnClickListener() {
        // Alert dialog
        // http://android.pcsalt.com/create-alertdialog-with-custom-layout-using-xml-layout/
        public void onClick(View v) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.new_playlist_dialog, null);
        final EditText playlistTitle = alertLayout.findViewById(R.id.playlist_title);

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
            // print out Toast message to give user feedback
            toastMessage = Toast.makeText(getActivity(), "Created Playlist: " + title, Toast.LENGTH_SHORT);
            toastMessage.show();

            // create a new playlist with this playlist title
            ArrayList<Song> emptyList = new ArrayList<Song>();
            Playlist emptyPlaylist = new Playlist(title, 0, 0, emptyList, "");
            accessPlaylist.insertPlaylist(emptyPlaylist);
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
        }
    };


    // passing data from Adapter to Fragment
    // https://developer.android.com/guide/components/fragments.html#EventCallbacks
    // https://stackoverflow.com/a/52830847

    // if we click on a playlist, we should see the songs contained in that playlist
    public void onPlaylistClicked(int playlistIndex) {
        String playlistName = allPlaylists.get(playlistIndex).getPlaylistName();
        toastMessage = Toast.makeText(getActivity(), " Open the songs in playlist \"" + playlistName +"\"", Toast.LENGTH_SHORT);
        toastMessage.show();

        // send the MainActivity the index of the playlist
        // so that we can view the songs in that playlist
        // the MainActivity will switch to a new fragment that will display the list of songs
        listener.onPlaylistClicked(playlistIndex);
    }

    // long clicking a playlist should open a context menu with various options
    public void onPlaylistLongClicked(int playlistIndex) {
        toastMessage = Toast.makeText(getActivity(), "Long Clicked: Open Context Menu", Toast.LENGTH_SHORT);
        toastMessage.show();

        // open context menu,

        // option to play the songs in this playlist (replaces all the songs currently in the queue)
        // option to queue the songs in this playlist to the currently playing queue
        // option to delete this playlist
        // option to rename this playlist

    }
}