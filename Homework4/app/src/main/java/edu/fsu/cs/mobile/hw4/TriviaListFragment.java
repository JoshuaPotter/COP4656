// TriviaListFragment
package edu.fsu.cs.mobile.hw4;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONException;

import java.util.ArrayList;

public class TriviaListFragment extends Fragment {
    public static final String TAG = TriviaListFragment.class.getCanonicalName();
    private TriviaItemArrayAdapter triviaAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trivia_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView list = view.findViewById(R.id.listView_trivia);
        triviaAdapter = new TriviaItemArrayAdapter(getActivity(), R.layout.row_trivia_item);

        try {
            // Populate ArrayList of TriviaItem from parser
            // @Param: String response
            ArrayList<TriviaItem> parsedItems = OpentdbParser.parseTriviaItems(OpentdbParser.SAMPLE_ITEMS);

            // Add TriviaItems to adapter
            for(TriviaItem item : parsedItems) {
                triviaAdapter.add(item);
            }

            // Show notification for new questions
            NotificationHelper.fetchedQuestions((MainActivity) getActivity());

            // Start ongoing notification to track game session
            NotificationHelper.gameSession((MainActivity) getActivity());

        } catch (JSONException event) {
            System.out.println("/------------------------ Error: API parser failed. --------------------------/");
        }

        list.setAdapter(triviaAdapter);
    }

    public TriviaItemArrayAdapter getTriviaAdapter() {
        return triviaAdapter;
    }

}
