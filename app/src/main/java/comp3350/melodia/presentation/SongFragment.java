package comp3350.melodia.presentation;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import comp3350.melodia.R;
import comp3350.melodia.application.Services;
import comp3350.melodia.logic.AccessSong;
import comp3350.melodia.objects.Song;

public class SongFragment extends Fragment {

    private MediaPlayer player;
    private List<Song> songList;
    private int currSong;
    private Handler seekbarHandler;
    private Runnable seekbarUpdater;

    private onQueueButtonClickedListener queueButtonListener;

    public interface onQueueButtonClickedListener {
        public void onShowQueue();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            queueButtonListener = (onQueueButtonClickedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    context.toString() + " must implement OnArticleSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState){
        AccessSong accessSong = new AccessSong(Services.getSongPersistence());
        songList = accessSong.getSongsSortedTrackName();
        currSong = 0;
        player = new MediaPlayer();
        seekbarHandler = new Handler();
        this.setRetainInstance(true);

        View v = inflater.inflate(R.layout.fragment_song, container, false);

        Button newPlaylist = v.findViewById(R.id.queue_button);
        newPlaylist.setOnClickListener(mButtonClickListener);

        return v;
    }

    @Override
    public void onHiddenChanged(boolean hidden){
        super.onHiddenChanged(hidden);
        AccessSong accessSong = new AccessSong(Services.getSongPersistence());
        songList = accessSong.getPlaylistSongs(0);
        updateText();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(savedInstanceState != null) {

        }
        try{
            updateText();
            Button buttonNext = getActivity().findViewById(R.id.buttonNext);
            Button buttonPrev = getActivity().findViewById(R.id.buttonPrev);
            Button buttonPlay = getActivity().findViewById(R.id.buttonPlay);
            final SeekBar seekBar = getActivity().findViewById(R.id.seekBar);

            buttonNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                if(currSong+1 < songList.size()){
                    currSong++;
                    playSong();
                    seekBar.setMax(player.getDuration());
                    updateText();
                }
                else{
                    currSong = 0;
                    playSong();
                    seekBar.setMax(player.getDuration());
                    updateText();
                }
                }
            });
            buttonPrev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                if(currSong-1 >= 0){
                    currSong--;
                    playSong();
                    seekBar.setMax(player.getDuration());
                    updateText();
                }
                else{
                    currSong = songList.size()-1;
                    playSong();
                    seekBar.setMax(player.getDuration());
                    updateText();
                }
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
                        seekBar.setMax(player.getDuration());
                    }


                }catch (Exception e){
                    System.out.println("Error: " + e);
                }
                    updateText();
                }
            });
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if(currSong+1 < songList.size()){
                        currSong++;
                        playSong();
                        seekBar.setMax(player.getDuration());
                        updateText();
                    }
                    else{
                        currSong = 0;
                        playSong();
                        seekBar.setMax(player.getDuration());
                        updateText();
                    }

                }
            });

            createSeekbar(seekBar);

        } catch(Exception e) {
            System.out.println("Error:" + e);
        }
    }
    
    private void createSeekbar(final SeekBar seekbar){
        seekbarUpdater = new Runnable() {
            @Override
            public void run() {
                seekbar.setProgress(player.getCurrentPosition());
                seekbarHandler.postDelayed(this,50);
                updateTime();
            }
        };
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar,
                                          int progress,
                                          boolean fromUser) {
                if(fromUser){
                    player.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private  void playSong(){
        try{
            // Temp song from:
            // https://www.bensound.com/royalty-free-music/track/all-that-chill-hop
            Switch shuffleSwitch = getActivity().findViewById(R.id.switchShuffle);
            if(shuffleSwitch.isChecked()){
                Random rand = new Random();
                int randomSong = rand.nextInt(((songList.size()-1) - 0) +1);
                currSong = randomSong;
            }
            AssetFileDescriptor afd = getContext().getAssets().openFd(
                    songList.get(currSong).getSongData().getPath());

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
                    updateText();
                    seekbarHandler.postDelayed(seekbarUpdater,0);
                }
            });


        }catch (Exception e){
            System.out.println("Error:" + e);
        }
    }
    private void updateText(){
        try{
            TextView t1 = getActivity().findViewById(R.id.textSongName);
            t1.setText(songList.get(currSong).getSongName());


            TextView t2 = getActivity().findViewById(R.id.textArtist);
            t2.setText(songList.get(currSong).getArtistName());

            TextView t3 = getActivity().findViewById(R.id.textSongTime);
            String songTimeText = String.format("%02d : %02d : %02d ",
                    songList.get(currSong).getHours(),
                    songList.get(currSong).getMinutes(),
                    songList.get(currSong).getSeconds());
            t3.setText(songTimeText);
        }catch (Exception e){
            System.out.println("Error " + e);
        }


        Button play_pause = getActivity().findViewById(R.id.buttonPlay);

        if(player.isPlaying()){
             play_pause.setBackgroundResource(R.drawable.pause);

        }
        else{
             play_pause.setBackgroundResource(R.drawable.play);
        }
    }

    private void updateTime(){
        int currDuration = player.getCurrentPosition();
        int hrs = (currDuration/1000) / 3600;
        int mins = ((currDuration/1000) % 3600) / 60;
        int secs   = (currDuration/1000) % 60;

        TextView timePlayed = getActivity().findViewById(R.id.textCurrSongTime);
        timePlayed.setText(String.format("%02d : %02d : %02d ", hrs, mins, secs));
    }

    public static SongFragment newInstance() {
        SongFragment fragment = new SongFragment();
        return fragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private View.OnClickListener mButtonClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            queueButtonListener.onShowQueue();
        }
    };

    public void playSongAtPosition(int positionSongClicked) {
        currSong = positionSongClicked;
        playSong();
        updateText();
        updateTime();
    }
}
