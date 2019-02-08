package comp3350.melodia.presentation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import comp3350.melodia.R;
import comp3350.melodia.objects.Song;


// lots of inspiration from the following:
// https://developer.android.com/guide/topics/ui/layout/recyclerview
// https://code.tutsplus.com/tutorials/android-from-scratch-understanding-adapters-and-adapter-views--cms-26646
// https://guides.codepath.com/android/using-the-recyclerview
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.SongViewHolder>{

    ArrayList<Song> songs;

    public static class SongViewHolder extends RecyclerView.ViewHolder {
        TextView songName;
        TextView artistName;
        TextView trackDuration;

        SongViewHolder(View itemView) {
            super(itemView);
            songName = (TextView)itemView.findViewById(R.id.song_name);
            artistName = (TextView)itemView.findViewById(R.id.artist_name);
            trackDuration = (TextView)itemView.findViewById(R.id.track_duration);
        }
    }

    // constructor
    public RecyclerViewAdapter(ArrayList<Song> songs){
        this.songs = songs;
    }

    // RecyclerView.Adapter has three abstract methods that we must override
    // getItemCount(), onCreateViewHolder() and onBindViewHolder()


    // returns the size of the list of songs to display
    @Override
    public int getItemCount() {
        return songs.size();
    }

    // creates an one item in our list (SongViewHolder) which will be displayed in the RecycleView
    // does not give the SongViewHolder any data, just creates an instance of the object
    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item, viewGroup, false);

        // Return a new holder instance
        SongViewHolder songViewHolder = new SongViewHolder(contactView);
        return songViewHolder;
    }

    // gives each SongViewHolder the data that it will display
    @Override
    public void onBindViewHolder(SongViewHolder songViewHolder, int i) {
        songViewHolder.songName.setText( songs.get(i).getSongName() );
        songViewHolder.artistName.setText( songs.get(i).getArtistName() );
        songViewHolder.trackDuration.setText( Integer.toString(songs.get(i).getSongTime()) );
    }
}
