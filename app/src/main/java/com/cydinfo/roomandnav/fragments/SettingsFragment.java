package com.cydinfo.roomandnav.fragments;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.cydinfo.roomandnav.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}