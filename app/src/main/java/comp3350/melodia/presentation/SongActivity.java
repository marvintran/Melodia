package comp3350.melodia.presentation;

import android.app.Activity;
import android.os.Bundle;

import comp3350.melodia.R;

public class SongActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
