package comp3350.melodia.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void buttonSongOnClick(View v){
        Intent songIntent = new Intent(HomeActivity.this, SongActivity.this);
        HomeActivity.this.startActivity(songIntent);
    }
}
