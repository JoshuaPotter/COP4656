package edu.fsu.cs.mobile.hw3;

import android.support.annotation.Nullable;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class NotesListFragment extends Fragment {
    private MyNoteArrayAdapter myAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View view = inflater.inflate(R.layout.fragment_notes_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView listView = view.findViewById(R.id.listView_notes);
        myAdapter = new MyNoteArrayAdapter(getActivity(), R.layout.row_note);

        // dummy data
        MyNote note = new MyNote("My First Note", "Body of my first note", "Timestamp");
        myAdapter.add(note);
        note = new MyNote("My Second Note", "Body of my second note", "Timestamp");
        myAdapter.add(note);

        listView.setAdapter(myAdapter);
    }
}
