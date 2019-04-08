package edu.fsu.cs.mobile.hw4;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

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

        // Show existing saved preferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        findPreference(KEY_QUESTIONS_DIFFICULTY).setSummary(sharedPreferences.getString(KEY_QUESTIONS_DIFFICULTY, "any"));
        findPreference(KEY_QUESTIONS_AMOUNT).setSummary(sharedPreferences.getString(KEY_QUESTIONS_AMOUNT, "5"));
        findPreference(KEY_HIGH_SCORE).setSummary(sharedPreferences.getString(KEY_HIGH_SCORE, "0"));
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        // Update preference view when sharedPreferences is updated
        Preference preference = findPreference(key);
        switch(key) {
            case KEY_QUESTIONS_AMOUNT:
                preference.setSummary(sharedPreferences.getString(key, "5"));
                break;
            case KEY_QUESTIONS_DIFFICULTY:
                preference.setSummary(sharedPreferences.getString(key, "any"));
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
