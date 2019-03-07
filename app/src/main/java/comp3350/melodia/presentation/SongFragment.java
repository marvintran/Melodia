package comp3350.melodia.presentation;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
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
                        playSong();
                    }
                    System.out.println("Update text");
                    updateText();

                }
            });
            buttonPrev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(currSong-1 >= 0){
                        currSong--;
                        playSong();
                    }
                    updateText();

                }
            });
            buttonPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        if(player.isPlaying() && player.getCurrentPosition() > 1){
                            player.pause();
                        }
                        else if(player.getCurrentPosition() > 1){
                            player.start();
                        }
                        else{
                            playSong();
                        }

                    }catch (Exception e){
                        System.out.println("Error: " + e);
                    }
                    updateText();
                }
            });
        } catch(Exception e){
            System.out.println("Error:" + e);
        }
    }


    private  void playSong(){
        try{
            //Temp song from
            //https://www.bensound.com/royalty-free-music/track/all-that-chill-hop

            //Uses database to get URI
            AssetFileDescriptor afd = getContext().getAssets().openFd(
                    songList.get(currSong).getSongData().getPath());

            //Replace once database implemented
            //AssetFileDescriptor afd = getContext().getAssets().openFd(
            //        "bensound_allthat.mp3");

            if(player.isPlaying()){
                player.stop();
            }
            player.reset();
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(),
                    afd.getLength());
            player.prepare();
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer player) {
                    player.start();
                }
            });
            updateText();

        }catch (Exception e){
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

        TextView t4 = getActivity().findViewById(R.id.buttonPlay);

        if(player.isPlaying()){
            t4.setText("Pause");
        }
        else{
            t4.setText("Play");
        }


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
