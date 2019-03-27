package comp3350.melodia.presentation;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import comp3350.melodia.R;
import comp3350.melodia.objects.Song;

public class PlaylistSongsAdapter
        extends AbstractSongsAdapter
        implements SwipeAndDragHelper.ItemDraggingListener {

    private OnStartDragListener theFragmentListening;

    public interface OnStartDragListener {
        void onStartDrag(SongViewHolder viewHolder);
    }

    public PlaylistSongsAdapter(List<Song> songs,
                                OnSongClickedListener listenerClick,
                                OnSongLongClickedListener listenerLongClick,
                                OnStartDragListener dragStartListener) {
        super(songs, listenerClick, listenerLongClick);
        this.theFragmentListening = dragStartListener;
    }

    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View songView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.playlist_songs_item, parent, false);
        SongViewHolder songViewHolder = new SongViewHolder(songView);
        return songViewHolder;
    }

    // We override the onBindViewHolder from AbstractSongsAdapter.
    // We want to include the dragHandle setOnTouchListener().
    public void onBindViewHolder(final SongViewHolder songViewHolder,
                                 final int position) {
        songViewHolder.getSongNameView().setText(
                songs.get(position).getSongName());
        songViewHolder.getArtistNameView().setText(
                songs.get(position).getArtist().getArtistName());
        songViewHolder.getTrackDurationView().setText(
                getSongTimeString(songs.get(position)));

        // implementing onClick() in RecyclerView https://stackoverflow.com/a/38090900
        songViewHolder.getLinearlayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Song songClicked = songs.get(position);
                listenerClick.onSongClicked(songClicked);
            }
        });

        songViewHolder.getLinearlayout().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Song songClicked = songs.get(position);
                listenerLongClick.onSongLongClicked(songClicked);

                v.showContextMenu();
                return true;
            }
        });

        songViewHolder.getDragHandleView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    theFragmentListening.onStartDrag(songViewHolder);
                }
                return false;
            }
        });
    }

    // These are the methods for implementing SwipeAndDragHelper.ItemDraggingListener
    @Override
    public void onItemDismiss(int position) {
        songs.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(songs, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(songs, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }
}
