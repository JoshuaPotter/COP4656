package edu.fsu.cs.mobile.hw4;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import java.util.HashSet;
import java.util.Set;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    public static final String TAG = TriviaItemFragment.class.getCanonicalName();
    public static final String KEY_QUESTIONS_AMOUNT = "questions_amount";
    public static final String KEY_QUESTIONS_DIFFICULTY = "questions_difficulty";
    public static final String KEY_HIGH_SCORE = "high_score";

    public SettingsFragment() {

    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        // Load the Preferences from the XML file
        addPreferencesFromResource(R.xml.app_preferences);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        findPreference(KEY_QUESTIONS_AMOUNT).setSummary(sharedPreferences.getString(KEY_QUESTIONS_AMOUNT, "5"));
        findPreference(KEY_HIGH_SCORE).setSummary(sharedPreferences.getString(KEY_HIGH_SCORE, "0"));

        String difficulties = sharedPreferences.getStringSet(KEY_QUESTIONS_DIFFICULTY, null).toString();
        difficulties = difficulties.substring(1, difficulties.length()-1);
        findPreference(KEY_QUESTIONS_DIFFICULTY).setSummary(difficulties);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        switch(key) {
            case KEY_QUESTIONS_AMOUNT:
                preference.setSummary(sharedPreferences.getString(key, "5"));
                break;
            case KEY_QUESTIONS_DIFFICULTY:
                String difficulties = sharedPreferences.getStringSet(key, null).toString();
                difficulties = difficulties.substring(1, difficulties.length()-1);
                preference.setSummary(difficulties);
                break;
            case KEY_HIGH_SCORE:
                preference.setSummary(sharedPreferences.getString(key, "0"));
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
