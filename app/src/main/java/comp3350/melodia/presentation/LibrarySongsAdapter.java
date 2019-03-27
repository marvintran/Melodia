package comp3350.melodia.presentation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import comp3350.melodia.R;
import comp3350.melodia.objects.Song;

public class LibrarySongsAdapter extends AbstractSongsAdapter {

    public LibrarySongsAdapter(List<Song> songs,
                               OnSongClickedListener listenerClick,
                               OnSongLongClickedListener listenerLongClick){
        super(songs, listenerClick, listenerLongClick);
    }

    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent,int position) {
        View songView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.library_item, parent, false);
        SongViewHolder songViewHolder = new SongViewHolder(songView);
        return songViewHolder;
    }

    public void updateList(List<Song> songs) {
        this.songs = songs;
        notifyDataSetChanged();
    }
}