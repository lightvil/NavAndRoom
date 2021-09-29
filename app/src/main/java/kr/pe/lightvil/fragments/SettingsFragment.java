package kr.pe.lightvil.fragments;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import kr.pe.lightvil.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}