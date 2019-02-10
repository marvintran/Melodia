package comp3350.melodia.presentation;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import comp3350.melodia.R;
import comp3350.melodia.logic.AccessSong;
import comp3350.melodia.objects.Song;

public class SongActivity extends Activity {

    List<Song> Songs;
    Song currSong;
    AccessSong accessSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
    }

    protected void buttonPlayOnClick(){
        TextView t1 = (TextView)findViewById(R.id.textSong);
        t1.setText(currSong.getSongName());

        TextView t2 = (TextView)findViewById(R.id.textSongTime);
        t2.setText(currSong.getSongTime());

        TextView t3 = (TextView)findViewById(R.id.textArtist);
        t3.setText(currSong.getArtist().getArtistName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
