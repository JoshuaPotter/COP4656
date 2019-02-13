package edu.fsu.cs.mobile.hw3;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static MyNoteArrayAdapter myAdapter;
    public static final String TAG = "MainActivity";
    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = getSupportFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();
        NotesListFragment fragment = new NotesListFragment();
        trans.add(R.id.fragment_container, fragment, NotesListFragment.TAG);
        trans.commit();
    }

    @Override
    public void onBackPressed() {
        if (manager.getBackStackEntryCount() > 0) {
            Toast.makeText(this, "Pop Back Stack",
                    Toast.LENGTH_SHORT).show();
            manager.popBackStack();
//            FragmentTransaction trans = manager.beginTransaction();
//            trans.remove(manager.findFragmentByTag(ViewNoteFragment.TAG));
//            trans.attach(manager.findFragmentByTag(NotesListFragment.TAG));
//            trans.commit();
        } else {
            super.onBackPressed();
        }
    }
}
