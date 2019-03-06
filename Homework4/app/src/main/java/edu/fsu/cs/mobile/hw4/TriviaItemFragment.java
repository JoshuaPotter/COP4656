package edu.fsu.cs.mobile.hw4;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TriviaItemFragment extends Fragment {
    public static final String TAG = TriviaItemFragment.class.getCanonicalName();
    private TriviaItem item;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if(getArguments() != null) {
            item = bundle.getParcelable("trivia_item");
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trivia_item, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // populate fields with TriviaItem object
        TextView question = view.findViewById(R.id.textView_question);
        TextView category = view.findViewById(R.id.textView_category);
        TextView difficulty = view.findViewById(R.id.textView_difficulty);
        question.setText(item.getQuestion());
        category.setText(item.getCategory());
        difficulty.setText(item.getDifficulty());
        switch(item.getDifficulty()) {
            case "Easy":
                difficulty.setTextColor(Color.parseColor("#0eea87"));
                break;
            case "Medium":
                difficulty.setTextColor(Color.parseColor("#dd8118"));
                break;
            case "Hard":
                difficulty.setTextColor(Color.parseColor("#ea0909"));
                break;
            default:
                break;
        }

    }
}
