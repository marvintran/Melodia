package comp3350.melodia.presentation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import comp3350.melodia.R;
import comp3350.melodia.objects.Song;


// THIS IS THE ADAPTER FOR HomeFragment

// inspiration was taken from the following:
// https://developer.android.com/guide/topics/ui/layout/recyclerview
// https://code.tutsplus.com/tutorials/android-from-scratch-understanding-adapters-and-adapter-views--cms-26646
// https://guides.codepath.com/android/using-the-recyclerview
public class LibraryRecyclerViewAdapter extends RecyclerView.Adapter<LibraryRecyclerViewAdapter.SongViewHolder>{

    private List<Song> songs;
    private OnSongClickedListener listenerClick;
    private OnSongLongClickedListener listenerLongClick;

    // HomeFragment must implement these interfaces
    // so that the adapter can send data back to the fragment
    public interface OnSongClickedListener {
        public void onSongClicked(Song theSong);
    }
    public interface OnSongLongClickedListener {
        public void onSongLongClicked(Song theSong);
    }

    // creates a SongViewHolder which contains references to all the views in this view row
    public static class SongViewHolder extends RecyclerView.ViewHolder {
        TextView songName;
        TextView artistName;
        TextView trackDuration;
        LinearLayout linearlayout;

        SongViewHolder(View itemView) {
            super(itemView);
            songName = (TextView)itemView.findViewById(R.id.song_name);
            artistName = (TextView)itemView.findViewById(R.id.artist_name);
            trackDuration = (TextView)itemView.findViewById(R.id.track_duration);
            this.linearlayout = (LinearLayout) itemView.findViewById(R.id.linearlayout);
        }
    }

    // constructor
    public LibraryRecyclerViewAdapter(List<Song> songs, OnSongClickedListener listenerClick,
                                                        OnSongLongClickedListener listenerLongClick){
        this.songs = songs;
        this.listenerClick = listenerClick;
        this.listenerLongClick = listenerLongClick;
    }

    // RecyclerView.Adapter has three abstract methods that we must override
    // getItemCount(), onCreateViewHolder() and onBindViewHolder()


    // returns the size of the list of songs to display
    @Override
    public int getItemCount() {
        return songs.size();
    }

    // creates a view row for a library_item where views contained in the view row are stored in a SongViewHolder
    // does not give the views in the SongViewHolder any data,
    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a view row as defined by our library_item.xml file
        View songView = LayoutInflater.from(parent.getContext()).inflate(R.layout.library_item, parent, false);
        // create a songViewHolder which contains references to the views for this row
        SongViewHolder songViewHolder = new SongViewHolder(songView);
        return songViewHolder;
    }

    // populates the views contained in this SongViewHolder
    @Override
    public void onBindViewHolder(final SongViewHolder songViewHolder, final int viewType) {
        songViewHolder.songName.setText(songs.get(viewType).getSongName());
        songViewHolder.artistName.setText(songs.get(viewType).getArtist().getArtistName());
        songViewHolder.trackDuration.setText(songs.get(viewType).getSongTimeString());

        // implementing onClick() in RecyclerView https://stackoverflow.com/a/38090900

        // clicking on an item in the list
        songViewHolder.linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if a song was clicked in the library, add it to the queue
                // pass the song clicked to the fragment so it can add it to the queue
                Song songClicked = songs.get(viewType);
                listenerClick.onSongClicked(songClicked);
            }
        });

        // long clicking on an item in the list
        songViewHolder.linearlayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // if a song was long clicked in the library, open context menu
                // pass the song to the fragment so we can do stuff depending on the menu option selected
                Song songClicked = songs.get(viewType);
                listenerLongClick.onSongLongClicked(songClicked);

                v.showContextMenu();// opens the context menu
                return true;
            }
        });
    }
}