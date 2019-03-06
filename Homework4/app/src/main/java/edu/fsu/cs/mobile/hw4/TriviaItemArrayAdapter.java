package edu.fsu.cs.mobile.hw4;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TriviaItemArrayAdapter extends ArrayAdapter<TriviaItem> {
    private ArrayList<TriviaItem> triviaItems;
    private Context myContext;

    public TriviaItemArrayAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        myContext = context;
        triviaItems = new ArrayList<>();
    }

    private static class TriviaItemHolder {
        TextView question;
        TextView category;
        TextView difficulty;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final TriviaItem item = getItem(position);

        // Populate list with each element
        TriviaItemHolder viewHolder;
        if(convertView == null) {
            viewHolder = new TriviaItemHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_trivia_item, parent, false);

            viewHolder.question = convertView.findViewById(R.id.row_textView_question);
            viewHolder.category = convertView.findViewById(R.id.row_textView_category);
            viewHolder.difficulty = convertView.findViewById(R.id.row_textView_difficulty);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (TriviaItemHolder) convertView.getTag();
        }
        item.setDifficulty(item.getDifficulty().substring(0,1).toUpperCase() + item.getDifficulty().substring(1));
        viewHolder.difficulty.setText(item.getDifficulty());
        viewHolder.question.setText(item.getQuestion());
        viewHolder.category.setText(item.getCategory());
        switch(item.getDifficulty()) {
            case "Easy":
                viewHolder.difficulty.setTextColor(Color.parseColor("#0eea87"));
                break;
            case "Medium":
                viewHolder.difficulty.setTextColor(Color.parseColor("#dd8118"));
                break;
            case "Hard":
                viewHolder.difficulty.setTextColor(Color.parseColor("#ea0909"));
                break;
            default:
                break;
        }

        // Add onclick for each item in list
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Send parcelable `item` object to fragment
                Bundle bundle = new Bundle();
                bundle.putParcelable("trivia_item", item);

                // Display TriviaItemFragment
                FragmentManager manager = ((MainActivity) myContext).getSupportFragmentManager();
                FragmentTransaction trans = manager.beginTransaction();

                // Create new fragment and set arguments
                TriviaItemFragment fragment = new TriviaItemFragment();
                fragment.setArguments(bundle);

                // Hide Trivia List fragment and show Trivia Item fragment
                trans.addToBackStack(TriviaListFragment.TAG);
                trans.hide(manager.findFragmentByTag(TriviaListFragment.TAG));
                trans.add(R.id.fragment_container, fragment, TriviaItemFragment.TAG);
                trans.commit();
            }
        });

        return convertView;
    }

    @Override
    public int getCount() {
        return triviaItems.size();
    }

    @Nullable
    @Override
    public TriviaItem getItem(int position) {
        return triviaItems.get(position);
    }

    @Override
    public int getPosition(@Nullable TriviaItem item) {
        for (int i = 0; i < triviaItems.size(); i++) {
            if(TextUtils.equals( item.getQuestion(), triviaItems.get(i).getQuestion() )) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void add(@Nullable TriviaItem item) {
        int idx = getPosition(item);
        if(idx >= 0) {
            triviaItems.set(idx, item);
        } else {
            triviaItems.add(item);
        }
        notifyDataSetChanged();
    }
}
