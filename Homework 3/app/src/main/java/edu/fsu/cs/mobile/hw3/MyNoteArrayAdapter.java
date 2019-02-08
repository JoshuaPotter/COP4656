package edu.fsu.cs.mobile.hw3;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyNoteArrayAdapter extends ArrayAdapter<MyNote> {
    private ArrayList<MyNote> myNotes;
    private Context myContext;

    public MyNoteArrayAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        myContext = context;
        myNotes = new ArrayList<>();
    }

    private static class MyNoteHolder {
        TextView title;
        TextView time;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final MyNote item = getItem(position);
        MyNoteHolder viewHolder;
        if(convertView == null) {
            viewHolder = new MyNoteHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_note, parent, false);
            viewHolder.title = convertView.findViewById(R.id.textView_title);
            viewHolder.time = convertView.findViewById(R.id.textView_time);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MyNoteHolder) convertView.getTag();
        }

        viewHolder.title.setText(item.getTitle());
        viewHolder.time.setText(item.getTime());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // display ViewNoteFragment

//                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    @Override
    public int getCount() {
        return myNotes.size();
    }

    @Nullable
    @Override
    public MyNote getItem(int position) {
        return myNotes.get(position);
    }

    @Override
    public int getPosition(@Nullable MyNote item) {
        for (int i = 0; i < myNotes.size(); i++) {
            if(TextUtils.equals( item.getTitle(), myNotes.get(i).getTitle() )) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void add(@Nullable MyNote note) {
        int idx = getPosition(note);
        if(idx >= 0) {
            myNotes.set(idx, note);
        } else {
            myNotes.add(note);
        }

        notifyDataSetChanged();
    }
}
