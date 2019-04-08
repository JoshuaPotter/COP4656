package edu.fsu.cs.mobile.hw4;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

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
        Context context = getContext();
        switch(item.getDifficulty()) {
            case "Easy":
                difficulty.setTextColor(ContextCompat.getColor(context, R.color.colorGreen));
                break;
            case "Medium":
                difficulty.setTextColor(ContextCompat.getColor(context, R.color.colorYellow));
                break;
            case "Hard":
                difficulty.setTextColor(ContextCompat.getColor(context, R.color.colorRed));
                break;
            default:
                break;
        }

        // Consolidate possible answers and shuffle
        ArrayList<String> possibleAnswers = new ArrayList<>();
        for(String answer : item.getIncorrectAnswers()) {
            possibleAnswers.add(answer);
        }
        possibleAnswers.add(item.getCorrectAnswer());
        Collections.shuffle(possibleAnswers);

        // Create buttons for possible answers
        LinearLayout buttonContainer = view.findViewById(R.id.linearLayout_choice);
        for(String answer : possibleAnswers) {
            createBtn(answer, buttonContainer);
        }

        // Output answer
        System.out.println("Answer: " + item.getCorrectAnswer());
    }

    public void createBtn(String text, LinearLayout container) {
        Button btn = new Button(getActivity().getApplicationContext());
        btn.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        btn.setText(text);
        LinearLayout.LayoutParams parameter = (LinearLayout.LayoutParams) btn.getLayoutParams();
        parameter.setMargins(0,0,0,10);
        btn.setLayoutParams(parameter);

        // Set onclick listener
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button btn = (Button) view;
                TriviaListFragment fragment = ((TriviaListFragment) getActivity().getSupportFragmentManager()
                        .findFragmentByTag(TriviaListFragment.TAG));

                if(btn.getText().equals(item.getCorrectAnswer())) {
                    // Selected correct answer, update score
                    btn.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorGreen));
                    btn.setTextColor(Color.WHITE);
                    fragment.incrementScore();
                } else {
                    btn.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRed));
                    btn.setTextColor(Color.WHITE);
                }

                // Disable all btns once selection is made
                LinearLayout buttonContainer = view.getRootView().findViewById(R.id.linearLayout_choice);
                for(int i = 0; i < buttonContainer.getChildCount(); i++) {
                    Button v = (Button) buttonContainer.getChildAt(i);
                    if(v.getText().equals(item.getCorrectAnswer())) {
                        v.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorGreen));
                        v.setTextColor(Color.WHITE);
                    }
                    v.setEnabled(false);
                }

                // Remove item from triviaItems
                TriviaItemArrayAdapter adapter = fragment.getTriviaAdapter();
                adapter.remove(item);
                NotificationHelper.gameSession((MainActivity) getActivity());
            }
        });
        container.addView(btn);
    }
}
