package comp3350.melodia.presentation;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

// RecyclerView reordering items.
// https://medium.com/@ipaulpro/drag-and-swipe-with-recyclerview-b9456d2b1aaf
// https://medium.com/@ipaulpro/drag-and-swipe-with-recyclerview-6a6f0c422efd
// https://therubberduckdev.wordpress.com/2017/10/24/android-recyclerview-drag-and-drop-and-swipe-to-dismiss/
public class SwipeAndDragHelper extends ItemTouchHelper.Callback {

    private final ItemDraggingListener theAdapterListening;

    public interface ItemDraggingListener {
        boolean onItemMove(int fromPosition, int toPosition);
        void onItemDismiss(int position);
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

}