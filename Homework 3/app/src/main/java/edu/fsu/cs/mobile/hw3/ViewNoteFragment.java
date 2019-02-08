package edu.fsu.cs.mobile.hw3;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ViewNoteFragment extends Fragment {
    private String title;
    private String timestamp;
    private String note;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        title = getArguments().getString("title");
        timestamp = getArguments().getString("timestamp");
        note = getArguments().getString("note");

        View view = inflater.inflate(R.layout.fragment_view_note, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((TextView) view.findViewById(R.id.textView_title)).setText(title);
        ((TextView) view.findViewById(R.id.textView_timestamp)).setText(timestamp);
        ((TextView) view.findViewById(R.id.textView_note)).setText(note);
    }
}
