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

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    //tutorial for NavigationBar found at https://code.tutsplus.com/tutorials/how-to-code-a-bottom-navigation-bar-for-an-android-app--cms-30305
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.homeNav:
                    Fragment homeFragment = HomeFragment.newInstance();
                    openFragment(homeFragment);mTextMessage.setText(R.string.home_button);
                    return true;
                case R.id.playlistNav:
                    Fragment playlistFragment = PlaylistFragment.newInstance();
                    openFragment(playlistFragment);
                   // mTextMessage.setText(R.string.playlist_button);
                    return true;
                case R.id.shareNav:
                    Fragment shareFragment = ShareFragment.newInstance();
                    openFragment(shareFragment);
                   // mTextMessage.setText(R.string.share_button);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

}
