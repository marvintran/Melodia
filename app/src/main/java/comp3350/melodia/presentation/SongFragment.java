package comp3350.melodia.presentation;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import comp3350.melodia.R;
import comp3350.melodia.logic.AccessSong;
import comp3350.melodia.objects.Song;

public class SongFragment extends Fragment {

    private MediaPlayer player;
    private List<Song> songList;
    private int currSong;
    private AccessSong accessSong;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        accessSong = new AccessSong();
        songList = accessSong.getSongs();
        currSong = 0;
        player = new MediaPlayer();
        return inflater.inflate(R.layout.fragment_song, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try{
            updateText();
            Button buttonNext = getActivity().findViewById(R.id.buttonNext);
            Button buttonPrev = getActivity().findViewById(R.id.buttonPrev);
            Button buttonPlay = getActivity().findViewById(R.id.buttonPlay);

            buttonNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(currSong+1 < songList.size()){
                        currSong++;
                        updateText();
                    }

                }
            });
            buttonPrev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(currSong-1 >= 0){
                        currSong--;
                        updateText();
                    }

                }
            });
            buttonPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        //Uses the database to get the song path
                        //player.setDataSource(songList.get(currSong).getSongData().getPath());

                        //TODO: change to line shown above when database is ready
                        //Temp song from
                        //https://www.bensound.com/royalty-free-music/track/all-that-chill-hop
                        player.setDataSource();
                        player.start();
                    }catch (Exception e){
                        System.out.println("Error: " + e);
                    }

                }
            });

        } catch(Exception e){
            System.out.println("Error:" + e);
        }
    }

    private void updateText(){
        TextView t1 = getActivity().findViewById(R.id.textSongName);
        t1.setText(songList.get(currSong).getSongName());


        TextView t2 = getActivity().findViewById(R.id.textArtist);
        t2.setText(songList.get(currSong).getArtist().getArtistName());

        TextView t3 = getActivity().findViewById(R.id.textSongTime);

        t3.setText(getSongTimeString(songList.get(currSong)));
    }

    public String getSongTimeString(Song song){
        int hrs = song.getSongTime() / 3600;
        int mins = (song.getSongTime() % 3600) / 60;
        int secs   = song.getSongTime() % 60;

        return String.format("%02d : %02d : %02d ", hrs, mins, secs);
    }


    public static SongFragment newInstance() {
        SongFragment fragment = new SongFragment();
        return fragment;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
