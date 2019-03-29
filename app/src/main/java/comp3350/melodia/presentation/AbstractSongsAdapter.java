package comp3350.melodia.presentation;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import comp3350.melodia.objects.Song;
import comp3350.melodia.objects.SongViewHolder;

// inspiration was taken from the following:
// https://developer.android.com/guide/topics/ui/layout/recyclerview
// https://code.tutsplus.com/tutorials/android-from-scratch-understanding-adapters-and-adapter-views--cms-26646
// https://guides.codepath.com/android/using-the-recyclerview
public abstract class AbstractSongsAdapter extends RecyclerView.Adapter<SongViewHolder>{

    protected List<Song> songs;
    protected OnSongClickedListener listenerClick;
    protected OnSongLongClickedListener listenerLongClick;

    // The fragment must implement these interfaces
    // so that the adapter can send data back to the fragment
    public interface OnSongClickedListener {
        public void onSongClicked(Song theSong);
    }
    public interface OnSongLongClickedListener {
        public void onSongLongClicked(Song theSong);
    }

    // constructor
    public AbstractSongsAdapter(List<Song> songs, OnSongClickedListener listenerClick,
                                OnSongLongClickedListener listenerLongClick){
        //this.songs = songs;
        this.songs = new ArrayList(songs);
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

    // we don't implement onCreateViewHolder() in this abstract class,
    // subclasses do because depending on the screen, songs may be displayed differently
    // for example, it may or may not have a drag handle, the .xml layout defines that

    // creates a view row for a library_item where views contained in the view row are stored in a SongViewHolder
    // does not give the views in the SongViewHolder any data
    public abstract SongViewHolder onCreateViewHolder(ViewGroup parent, int position);

    public String getSongTimeString(Song song){
        int hrs = song.getSongTime() / 3600;
        int mins = (song.getSongTime() % 3600) / 60;
        int secs   = song.getSongTime() % 60;

        return String.format("%02d : %02d : %02d ", hrs, mins, secs);
    }

    // populates the views contained in this SongViewHolder
    @Override
    public void onBindViewHolder(final SongViewHolder songViewHolder, final int position) {
        songViewHolder.getSongNameView().setText(songs.get(position).getSongName());
        songViewHolder.getArtistNameView().setText(songs.get(position).getArtistName());
        songViewHolder.getTrackDurationView().setText(getSongTimeString(songs.get(position)));

        // implementing onClick() in RecyclerView https://stackoverflow.com/a/38090900

        // clicking on an item in the list
        songViewHolder.getLinearlayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if a song was clicked in the library, add it to the queue
                // pass the song clicked to the fragment so it can add it to the queue
                Song songClicked = songs.get(position);
                listenerClick.onSongClicked(songClicked);
            }
        });

        // long clicking on an item in the list
        songViewHolder.getLinearlayout().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // if a song was long clicked in the library, open context menu
                // pass the song to the fragment so we can do stuff depending on the menu option selected
                Song songClicked = songs.get(position);
                listenerLongClick.onSongLongClicked(songClicked);

                v.showContextMenu();// opens the context menu
                return true;
            }
        });
    }
}