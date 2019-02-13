package edu.fsu.cs.mobile.hw3;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static android.app.Activity.RESULT_CANCELED;
import static edu.fsu.cs.mobile.hw3.NotesListFragment.EDIT_NOTE;

public class ViewNoteFragment extends Fragment {
    public static final String TAG = NotesListFragment.class.getCanonicalName();

    private String title_val;
    private String note_val;
    private String timestamp_val;
    private TextView title;
    private TextView note;
    private TextView timestamp;
    private Button editNote;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        title_val = getArguments().getString("title");
        timestamp_val = getArguments().getString("timestamp");
        note_val = getArguments().getString("note");

        View view = inflater.inflate(R.layout.fragment_view_note, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        title = (TextView) view.findViewById(R.id.textView_title);
        note = (TextView) view.findViewById(R.id.textView_note);
        timestamp = (TextView) view.findViewById(R.id.textView_timestamp);

        editNote = (Button) view.findViewById(R.id.button_edit);
        editNote.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent editNote = new Intent(v.getContext(), EditNoteActivity.class);
               editNote.putExtra("note", new MyNote(title_val, note_val, timestamp_val));
               editNote.setAction(EditNoteActivity.ACTION_EDIT_NOTE);
               startActivityForResult(editNote, EDIT_NOTE);
           }
        });

        title.setText(title_val);
        timestamp.setText(timestamp_val);
        note.setText(note_val);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // If the user canceled without logging in, we don't have any profile to show.
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(getActivity(), "Cancelled",
                    Toast.LENGTH_SHORT).show();
        }
        else if (requestCode == EDIT_NOTE) {
            MyNote old_note = data.getParcelableExtra("old_note");
            MyNote new_note = data.getParcelableExtra("note");

            // compare and update
//            MainActivity.myAdapter.add(thisNote);
            int notePos = MainActivity.myAdapter.getPosition(old_note);

            // update values on ViewNoteFragment
            title_val = new_note.getTitle();
            note_val = new_note.getNote();
            timestamp_val = new_note.getTime();

            // update value in array adapter
            MainActivity.myAdapter.getItem(notePos).setTitle(title_val);
            MainActivity.myAdapter.getItem(notePos).setNote(note_val);
            MainActivity.myAdapter.getItem(notePos).setTime(timestamp_val);
            MainActivity.myAdapter.notifyDataSetChanged();

            title.setText(title_val);
            note.setText(note_val);
            timestamp.setText(timestamp_val);
        }
    }
}
