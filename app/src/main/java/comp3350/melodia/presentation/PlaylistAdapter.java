package comp3350.melodia.presentation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import comp3350.melodia.R;
import comp3350.melodia.objects.Playlist;


// inspiration was taken from the following:
// https://developer.android.com/guide/topics/ui/layout/recyclerview
// https://code.tutsplus.com/tutorials/android-from-scratch-understanding-adapters-and-adapter-views--cms-26646
// https://guides.codepath.com/android/using-the-recyclerview
public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder>{

    private List<Playlist> playlists;
    private OnPlaylistClickedListener listenerClick;
    private OnPlaylistLongClickedListener listenerLongClick;

    // PlaylistFragment must implement these interfaces
    // so that the adapter can send data back to the fragment
    public interface OnPlaylistClickedListener {
        public void onPlaylistClicked(int playlistIndex);
    }
    public interface OnPlaylistLongClickedListener {
        public void onPlaylistLongClicked(int playlistIndex);
    }

    // creates a PlaylistViewHolder which contains references to all the views in this view row
    public static class PlaylistViewHolder extends RecyclerView.ViewHolder {
        TextView playlistTitle;
        TextView numberOfSongs;
        TextView playlistDuration;
        LinearLayout linearlayout;

        PlaylistViewHolder(View itemView) {
            super(itemView);
            playlistTitle = (TextView)itemView.findViewById(R.id.playlist_title);
            numberOfSongs = (TextView)itemView.findViewById(R.id.total_songs);
            playlistDuration = (TextView)itemView.findViewById(R.id.playlist_duration);
            this.linearlayout = (LinearLayout) itemView.findViewById(R.id.linearlayout);
        }
    }

    // constructor
    public PlaylistAdapter(List<Playlist> playlists, OnPlaylistClickedListener listenerClick,
                           OnPlaylistLongClickedListener listenerLongClick) {
        this.playlists = playlists;
        this.listenerClick = listenerClick;
        this.listenerLongClick = listenerLongClick;
    }

    // RecyclerView.Adapter has three abstract methods that we must override
    // getItemCount(), onCreateViewHolder() and onBindViewHolder()


    // returns the size of the list of songs to display
    @Override
    public int getItemCount() {
        return playlists.size();
    }

    // creates a view row for a library_item where views contained in the view row are stored in a PlaylistViewHolder
    // does not give the views in the PlaylistViewHolder any data,
    @Override
    public PlaylistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a view row as defined by our playlist_item.xml file
        View playlistView = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_item, parent, false);
        // create a songViewHolder which contains references to the views for this row
        PlaylistViewHolder playlistViewHolder = new PlaylistViewHolder(playlistView);
        return playlistViewHolder;
    }

    // populates the views contained in this PlaylistViewHolder
    @Override
    public void onBindViewHolder(PlaylistViewHolder playlistViewHolder, final int viewType) {

        String numberOfSongs = Integer.toString(playlists.get(viewType).getNumberOfSongs());
        if(numberOfSongs.equals("1") )
            playlistViewHolder.numberOfSongs.setText(numberOfSongs + " Song");
        else
            playlistViewHolder.numberOfSongs.setText(numberOfSongs + " Songs");

        playlistViewHolder.playlistTitle.setText(playlists.get(viewType).getPlaylistName());
        playlistViewHolder.playlistDuration.setText(Integer.toString(playlists.get(viewType).getPlaylistTime()));

        // implementing onClick() in RecyclerView https://stackoverflow.com/a/38090900

        // clicking on an item in the list
        playlistViewHolder.linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if a playlist was clicked, view the songs inside the playlist
                // pass the playlist clicked to the fragment so it knows which songs to show
                //Playlist playlistClicked = playlists.get(viewType);
                listenerClick.onPlaylistClicked(viewType);
            }
        });

        // long clicking on an item in the list
        playlistViewHolder.linearlayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // if a playlist was long clicked in the library, open context menu
                // pass the playlist to the fragment so we can do stuff depending on the menu option selected
                // Playlist playlistClicked = playlists.get(viewType);
                listenerLongClick.onPlaylistLongClicked(viewType);
                return true;
            }
        });
    }
}
