package edu.fsu.cs.mobile.hw5;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiate LoginFragment
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
         LoginFragment fragment = new LoginFragment();
         transaction.add(R.id.frameLayout_main, fragment, LoginFragment.TAG);
         transaction.commit();

    }

    @Override
    public void onBackPressed() {
        // Override back button to traverse through FragmentManager until no fragments in stack
        //    otherwise restore native functionality
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
