package edu.fsu.cs.mobile.hw4;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
