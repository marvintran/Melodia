package comp3350.melodia.presentation;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import comp3350.melodia.objects.Song;

// Inspiration was taken from the following.
// https://developer.android.com/guide/topics/ui/layout/recyclerview
// https://code.tutsplus.com/tutorials/android-from-scratch-understanding-adapters-and-adapter-views--cms-26646
// https://guides.codepath.com/android/using-the-recyclerview
public abstract class AbstractSongsAdapter extends RecyclerView.Adapter<SongViewHolder>{

    protected List<Song> songs;
    protected OnSongClickedListener listenerClick;
    protected OnSongLongClickedListener listenerLongClick;

    public interface OnSongClickedListener {
        public void onSongClicked(Song theSong, int position);
    }
    public interface OnSongLongClickedListener {
        public void onSongLongClicked(Song theSong, int position);
    }

    public AbstractSongsAdapter(List<Song> songs, OnSongClickedListener listenerClick,
                                OnSongLongClickedListener listenerLongClick){
        this.songs = new ArrayList(songs);
        this.listenerClick = listenerClick;
        this.listenerLongClick = listenerLongClick;
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public abstract SongViewHolder onCreateViewHolder(ViewGroup parent, int position);

    @Override
    public void onBindViewHolder(final SongViewHolder songViewHolder, final int position) {
        songViewHolder.getSongNameView().setText(songs.get(position).getSongName());
        songViewHolder.getArtistNameView().setText(songs.get(position).getArtistName());
        String songTimeText = String.format("%02d : %02d : %02d ",
                songs.get(position).getHours(),
                songs.get(position).getMinutes(),
                songs.get(position).getSeconds());
        songViewHolder.getTrackDurationView().setText(songTimeText);

        // Implementing onClick() in RecyclerView https://stackoverflow.com/a/38090900
        songViewHolder.getLinearlayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Song songClicked = songs.get(position);
                listenerClick.onSongClicked(songClicked, position);
            }
        });

        songViewHolder.getLinearlayout().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Song songClicked = songs.get(position);
                listenerLongClick.onSongLongClicked(songClicked, position);

                v.showContextMenu();
                return true;
            }
        });
    }
}