package comp3350.melodia.presentation;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import comp3350.melodia.R;
import comp3350.melodia.objects.Song;
import comp3350.melodia.objects.SongViewHolder;

public class PlaylistSongsAdapter extends AbstractSongsAdapter
                                  implements SwipeAndDragHelper.ItemDraggingListener{

    private OnStartDragListener theFragmentListening;

    public interface OnStartDragListener {
        void onStartDrag(SongViewHolder viewHolder);
    }

    public PlaylistSongsAdapter(List<Song> songs, OnSongClickedListener listenerClick,
                                OnSongLongClickedListener listenerLongClick,
                                OnStartDragListener dragStartListener){
        super(songs, listenerClick, listenerLongClick);
        this.theFragmentListening = dragStartListener;
    }

    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        // create a view row as defined by our library_item.xml file
        View songView = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_songs_item, parent, false);
        // create a songViewHolder which contains references to the views for this row
        SongViewHolder songViewHolder = new SongViewHolder(songView);
        return songViewHolder;
    }

    // override the onBindViewHolder from AbstractSongsAdapter to include
    // the dragHandle setOnTouchListener()
    public void onBindViewHolder(final SongViewHolder songViewHolder, final int position) {
        songViewHolder.getSongNameView().setText(songs.get(position).getSongName());
        songViewHolder.getArtistNameView().setText(songs.get(position).getArtist().getArtistName());
        songViewHolder.getTrackDurationView().setText(songs.get(position).getSongTimeString());

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

    // implementing ItemTouchHelperAdapter
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
