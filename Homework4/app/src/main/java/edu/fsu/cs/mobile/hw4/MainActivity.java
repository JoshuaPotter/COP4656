package edu.fsu.cs.mobile.hw4;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();

        // Instantiate TriviaListFragment and add to FragmentManager
        TriviaListFragment fragment = new TriviaListFragment();
        trans.add(R.id.fragment_container, fragment, TriviaListFragment.TAG);
        trans.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate Menu on Activity
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_item_settings:
                // Set preferences
//                Bundle bundle = new Bundle();
//                bundle.putParcelable("high_score", score);

                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction trans = manager.beginTransaction();

                // Create new fragment and set arguments
                SettingsFragment fragment = new SettingsFragment();

                // Hide Trivia List fragment and show Trivia Item fragment
                trans.addToBackStack(SettingsFragment.TAG);
                trans.hide(manager.findFragmentById(R.id.fragment_container));
                trans.add(R.id.fragment_container, fragment, TriviaItemFragment.TAG);
                trans.commit();
                return true;
            case R.id.menu_item_exit:
                // Exit Activity
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        // Override back button to traverse through FragmentManager until no fragments in stack
        //    otherwise restore native functionality
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
