package comp3350.melodia.presentation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import comp3350.melodia.R;
import comp3350.melodia.objects.Song;

public class PlaylistSongsRecyclerViewAdapter extends AbstractSongAdapter {

    public PlaylistSongsRecyclerViewAdapter(List<Song> songs, OnSongClickedListener listenerClick,
                                            OnSongLongClickedListener listenerLongClick){
        super(songs, listenerClick, listenerLongClick);
    }

    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a view row as defined by our library_item.xml file
        View songView = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_songs_item, parent, false);
        // create a songViewHolder which contains references to the views for this row
        SongViewHolder songViewHolder = new SongViewHolder(songView);
        return songViewHolder;
    }

}
