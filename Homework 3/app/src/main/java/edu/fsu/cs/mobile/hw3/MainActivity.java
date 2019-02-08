package edu.fsu.cs.mobile.hw3;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager manager = getFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();
        NotesListFragment fragment = new NotesListFragment();
        trans.add(R.id.fragment_container, fragment, "list_notes");
        trans.commit();
    }
}
