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
        for(int i = 1; i <= 50; i++) {
            MyNote note = new MyNote("Note #".concat(Integer.toString(i)), "Body of note #".concat(Integer.toString(i)), "Timestamp");
            myAdapter.add(note);
        }

        listView.setAdapter(myAdapter);
    }
}
