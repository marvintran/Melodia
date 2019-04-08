package comp3350.melodia.presentation;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;


import comp3350.melodia.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState,String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}
