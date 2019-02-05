package comp3350.melodia.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import comp3350.melodia.R;

public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void buttonSongOnClick(View v){
        Intent songIntent = new Intent( HomeActivity.this, SongActivity.class);
        HomeActivity.this.startActivity(songIntent);
    }
}
