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

public class PlaylistSongsRecyclerViewAdapter extends LibraryRecyclerViewAdapter {

    // constructor
    public PlaylistSongsRecyclerViewAdapter(List<Song> songs, OnSongClickedListener listenerClick,
                                            OnSongLongClickedListener listenerLongClick){
        super(songs, listenerClick, listenerLongClick);
    }

    // creates a view row for a playlist_songs_item where views contained in the view row are stored in a SongViewHolder
    // does not give the views in the SongViewHolder any data,
    // the layout is the same as LibraryRecyclerViewAdapter except that it contains a dragHandle for reordering songs
    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a view row as defined by our library_item.xml file
        View songView = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_songs_item, parent, false);
        // create a songViewHolder which contains references to the views for this row
        SongViewHolder songViewHolder = new SongViewHolder(songView);
        return songViewHolder;
    }

}
