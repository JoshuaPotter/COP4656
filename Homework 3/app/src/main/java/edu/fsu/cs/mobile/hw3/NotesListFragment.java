package edu.fsu.cs.mobile.hw3;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import static android.app.Activity.RESULT_CANCELED;

public class NotesListFragment extends Fragment {

    public static final String TAG = NotesListFragment.class.getCanonicalName();

    private static final int NEW_NOTE = 100;
    private static final int EDIT_NOTE = 101;

    private Button addNote;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View view = inflater.inflate(R.layout.fragment_notes_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // setup list view
        ListView listView = view.findViewById(R.id.listView_notes);
        MainActivity.myAdapter = new MyNoteArrayAdapter(getActivity(), R.layout.row_note);

        // dummy data
//        for(int i = 1; i <= 50; i++) {
//            MyNote note = new MyNote("Note #".concat(Integer.toString(i)), "Body of note #".concat(Integer.toString(i)), "Timestamp");
//            MainActivity.MainActivity.myAdapter.add(note);
//        }

        listView.setAdapter(MainActivity.myAdapter);

        // setup button
        addNote = (Button) view.findViewById(R.id.button_add);
        addNote.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent addNote = new Intent(v.getContext(), EditNoteActivity.class);
                addNote.setAction(EditNoteActivity.ACTION_NEW_NOTE);
                startActivityForResult(addNote, NEW_NOTE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // If the user canceled without logging in, we don't have any profile to show.
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(getActivity(), "Cancelled",
                    Toast.LENGTH_SHORT).show();
        }
        else if (requestCode == NEW_NOTE) {
            MyNote thisNote = data.getParcelableExtra("note");
            MainActivity.myAdapter.add(thisNote);
        }
        else if (requestCode == EDIT_NOTE) {
            MyNote thisNote = data.getParcelableExtra("note");
        }
    }
}
