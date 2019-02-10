package comp3350.melodia.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import comp3350.melodia.R;
import comp3350.melodia.logic.AccessSong;
import comp3350.melodia.objects.Song;

public class SongActivity extends Activity {

    private List<Song> songList;
    private int currSong;
    private AccessSong accessSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        accessSong = new AccessSong();

        try{
            songList = accessSong.getSongs();
            currSong = 0;

            Button buttonPlay = (Button)findViewById(R.id.buttonPlay);
            buttonPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView t1 = findViewById(R.id.textSong);
                    t1.setText(songList.get(currSong).getSongName());

                    TextView t2 = findViewById(R.id.textArtist);
                    t2.setText(songList.get(currSong).getArtist().getArtistName());

                    TextView t3 = findViewById(R.id.textSongTime);

                    t3.setText(songList.get(currSong).getSongTimeString());
                }
            });
        } catch(Exception e){
            System.out.println("Error:" + e);
        }

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
