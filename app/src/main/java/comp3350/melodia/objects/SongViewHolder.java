package comp3350.melodia.objects;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import comp3350.melodia.R;

// creates a SongViewHolder which contains references to all the views in this view row
public class SongViewHolder extends RecyclerView.ViewHolder {
    private TextView songNameView;
    private TextView artistNameView;
    private TextView trackDurationView;
    private LinearLayout linearlayout;
    private ImageView dragHandleView;

    public SongViewHolder(View itemView) {
        super(itemView);
        songNameView = (TextView)itemView.findViewById(R.id.song_name);
        artistNameView = (TextView)itemView.findViewById(R.id.artist_name);
        trackDurationView = (TextView)itemView.findViewById(R.id.track_duration);
        this.linearlayout = (LinearLayout) itemView.findViewById(R.id.linearlayout);
        dragHandleView = (ImageView) itemView.findViewById(R.id.drag_handle);
    }

    public TextView getSongNameView() {
        return songNameView;
    }
    public TextView getArtistNameView() {
        return artistNameView;
    }
    public TextView getTrackDurationView() {
        return trackDurationView;
    }
    public LinearLayout getLinearlayout() {
        return linearlayout;
    }
    public ImageView getDragHandleView() {
        return dragHandleView;
    }
}
