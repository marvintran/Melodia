package comp3350.melodia.presentation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import comp3350.melodia.R;
import comp3350.melodia.objects.Playlist;


// inspiration was taken from the following:
// https://developer.android.com/guide/topics/ui/layout/recyclerview
// https://code.tutsplus.com/tutorials/android-from-scratch-understanding-adapters-and-adapter-views--cms-26646
// https://guides.codepath.com/android/using-the-recyclerview
public class PlaylistRecyclerViewAdapter extends RecyclerView.Adapter<PlaylistRecyclerViewAdapter.PlaylistViewHolder>{

    ArrayList<Playlist> playlists;

    // creates a PlaylistViewHolder which contains references to all the views in this view row
    public static class PlaylistViewHolder extends RecyclerView.ViewHolder {
        TextView playlistTitle;
        TextView numberOfSongs;
        TextView playlistDuration;

        PlaylistViewHolder(View itemView) {
            super(itemView);
            playlistTitle = (TextView)itemView.findViewById(R.id.playlist_title);
            numberOfSongs = (TextView)itemView.findViewById(R.id.total_songs);
            playlistDuration = (TextView)itemView.findViewById(R.id.playlist_duration);
        }
    }

    // constructor
    public PlaylistRecyclerViewAdapter(ArrayList<Playlist> playlists){
        this.playlists = playlists;
    }

    // RecyclerView.Adapter has three abstract methods that we must override
    // getItemCount(), onCreateViewHolder() and onBindViewHolder()


    // returns the size of the list of songs to display
    @Override
    public int getItemCount() {
        return playlists.size();
    }

    // creates a view row for an library_item where views contained in the view row are stored in a PlaylistViewHolder
    // does not give the views in the PlaylistViewHolder any data,
    @Override
    public PlaylistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a view row as defined by our library_item.xml file
        View songView = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_item, parent, false);
        // create a songViewHolder which contains references to the views for this row
        PlaylistViewHolder playlistViewHolder = new PlaylistViewHolder(songView);
        return playlistViewHolder;
    }

    // populates the views contained in this PlaylistViewHolder
    @Override
    public void onBindViewHolder(PlaylistViewHolder playlistViewHolder, int viewType) {
        playlistViewHolder.playlistTitle.setText(playlists.get(viewType).getPlaylistName());
        playlistViewHolder.numberOfSongs.setText(Integer.toString(playlists.get(viewType).getNumberOfSongs()));
        playlistViewHolder.playlistDuration.setText(Integer.toString(playlists.get(viewType).getPlaylistTime()));
    }
}
