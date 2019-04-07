package comp3350.melodia.presentation;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import comp3350.melodia.application.Services;
import comp3350.melodia.logic.AccessSong;

// RecyclerView reordering items.
// https://medium.com/@ipaulpro/drag-and-swipe-with-recyclerview-b9456d2b1aaf
// https://medium.com/@ipaulpro/drag-and-swipe-with-recyclerview-6a6f0c422efd
// https://therubberduckdev.wordpress.com/2017/10/24/android-recyclerview-drag-and-drop-and-swipe-to-dismiss/
public class SwipeAndDragHelper extends ItemTouchHelper.Callback {

    private final ItemDraggingListener theAdapterListening;
    private int fromPosition;
    private int toPosition;

    public interface ItemDraggingListener {
        boolean onItemMove(int fromPosition, int toPosition);
        void onItemDismiss(int position);
        int currPlaylistID();
    }

    public SwipeAndDragHelper(ItemDraggingListener adapterListeningForDragging) {
        this.theAdapterListening = adapterListeningForDragging;
    }

    // This disables holding anywhere on a song to reorder the song.
    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView,
                                RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        fromPosition = viewHolder.getAdapterPosition();
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView,
                          RecyclerView.ViewHolder viewHolder,
                          RecyclerView.ViewHolder target) {
        theAdapterListening.onItemMove(viewHolder.getAdapterPosition(),
                                       target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        theAdapterListening.onItemDismiss(viewHolder.getAdapterPosition());
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        toPosition = viewHolder.getAdapterPosition();

        AccessSong accessSong = new AccessSong(Services.getSongPersistence());
        if(fromPosition != toPosition) {
            accessSong.updateOrder(theAdapterListening.currPlaylistID(), fromPosition, toPosition);
        }
    }
}