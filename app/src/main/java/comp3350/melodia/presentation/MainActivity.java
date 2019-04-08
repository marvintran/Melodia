package comp3350.melodia.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import comp3350.melodia.R;

public class MainActivity extends AppCompatActivity
                          implements PlaylistFragment.OnPlaylistClickedListener,
                                     SongFragment.onQueueButtonClickedListener,
                                     QueueFragment.onPlayerButtonClickedListener,
                                     LibrarySongsFragment.RefreshInterface,
                                     QueueFragment.RefreshInterface,
                                     PlaylistSongsFragment.RefreshInterface,
                                     PlaylistSongsFragment.ShowPlayer{


    final Fragment homeNav = LibrarySongsFragment.newInstance();
    final PlaylistFragment playlistNav = PlaylistFragment.newInstance();
    final Fragment songNav = SongFragment.newInstance();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = homeNav;

    private TextView mTextMessage;
    // Tutorial for NavigationBar found at:
    // https://code.tutsplus.com/tutorials/how-to-code-a-bottom-navigation-bar-for-an-android-app--cms-30305
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.homeNav:
                    fm.beginTransaction().hide(active).show(homeNav).commit();
                    active = homeNav;
                    return true;
                case R.id.playlistNav:
                    fm.beginTransaction().hide(active).show(playlistNav).commit();
                    active = playlistNav;
                    return true;
                case R.id.songNav:
                    fm.beginTransaction().hide(active).show(songNav).commit();
                    active = songNav;
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fm.beginTransaction().add(R.id.container, homeNav, "homeNav").commit();
        fm.beginTransaction().add(R.id.container, playlistNav, "playlistNav").hide(
                playlistNav).commit();
        fm.beginTransaction().add(R.id.container, songNav, "songNav").hide(songNav).commit();


        mTextMessage = findViewById(R.id.message);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.homeNav);
    }

    public void onPlaylistClicked(int playlistIndex) {

        // Passing data (integer) from activity to fragment using Bundles.
        // https://stackoverflow.com/a/17063584
        Bundle args = new Bundle();
        args.putInt("exampleInt", playlistIndex);
        Fragment playlistSongs = PlaylistSongsFragment.newInstance();
        playlistSongs.setArguments(args);

        fm.beginTransaction().hide(active).commit();
        fm.beginTransaction().add(R.id.container, playlistSongs, "playlistSongs").commit();
        active = playlistSongs;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.settings) {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onShowQueue(){
        Fragment currentQueue = QueueFragment.newInstance();
        fm.beginTransaction().hide(active).commit();
        fm.beginTransaction().add(R.id.container, currentQueue).commit();
        active = currentQueue;
    }

    @Override
    public void onShowPlayer() {
        fm.beginTransaction().hide(active).show(songNav).commit();
        active = songNav;
    }

    public void refreshPlaylists() {
        playlistNav.updatePlaylists();
    }
}