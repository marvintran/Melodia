package comp3350.melodia.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import comp3350.melodia.R;
import comp3350.melodia.logic.AccessSong;
import comp3350.melodia.objects.Song;

public class SongActivity extends Activity {

    List<Song> SongList;
    Song currSong;
    AccessSong accessSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        accessSong = new AccessSong();
        SongList = accessSong.getSongs();
    }

    protected void buttonPlayOnClick(){
        TextView t1 = findViewById(R.id.textSong);
        t1.setText(currSong.getSongName());

        TextView t2 = findViewById(R.id.textSongTime);
        t2.setText(currSong.getSongTime());

        TextView t3 = findViewById(R.id.textArtist);
        t3.setText(currSong.getArtist().getArtistName());
    }

    public void buttonMenuOnClick(View v){
        Intent songIntent = new Intent( SongActivity.this, HomeActivity.class);
        SongActivity.this.startActivity(songIntent);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
