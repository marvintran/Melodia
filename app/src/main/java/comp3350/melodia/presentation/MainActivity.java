package comp3350.melodia.presentation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import comp3350.melodia.R;

public class MainActivity extends AppCompatActivity implements PlaylistFragment.OnPlaylistClickedListener{

    final Fragment homeNav = LibrarySongsFragment.newInstance();
    final Fragment playlistNav = PlaylistFragment.newInstance();
    final Fragment shareNav = ShareFragment.newInstance();
    final Fragment songNav = SongFragment.newInstance();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = homeNav;

    private TextView mTextMessage;
    //tutorial for NavigationBar found at https://code.tutsplus.com/tutorials/how-to-code-a-bottom-navigation-bar-for-an-android-app--cms-30305
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.homeNav:
                    fm.beginTransaction().hide(active).show(homeNav).commit();
                    active = homeNav;
                    mTextMessage.setText(R.string.home_button);
                    return true;
                case R.id.playlistNav:
                    fm.beginTransaction().hide(active).show(playlistNav).commit();
                    active = playlistNav;
                   // mTextMessage.setText(R.string.playlist_button);
                    return true;
                case R.id.shareNav:
                    fm.beginTransaction().hide(active).show(shareNav).commit();
                    active = shareNav;
                   // mTextMessage.setText(R.string.share_button);
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


        fm.beginTransaction().add(R.id.container, homeNav, "songNav").commit();
        fm.beginTransaction().add(R.id.container, playlistNav, "songNav").hide(
                playlistNav).commit();
        fm.beginTransaction().add(R.id.container, shareNav, "songNav").hide(shareNav).commit();
        fm.beginTransaction().add(R.id.container, songNav, "songNav").hide(songNav).commit();


        mTextMessage = findViewById(R.id.message);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.homeNav);
    }

    private void openFragment (Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    // creates a new fragment to display the songs in that playlist
    // we pass the index of the playlist that we want to display songs from
    // the fragment will index into the List<Playlist> to get the playlist we want
    public void onPlaylistClicked(int playlistIndex) {

        // passing data (integer) from activity to fragment using Bundles
        // https://stackoverflow.com/a/17063584
        Bundle args = new Bundle();
        args.putInt("exampleInt", playlistIndex);
        Fragment playlistSongs = PlaylistSongsFragment.newInstance();
        playlistSongs.setArguments(args);

        openFragment(playlistSongs);
    }
}
