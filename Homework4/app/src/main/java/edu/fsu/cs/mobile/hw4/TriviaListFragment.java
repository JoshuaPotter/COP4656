// TriviaListFragment
package edu.fsu.cs.mobile.hw4;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class TriviaListFragment extends Fragment {
    public static final String TAG = TriviaListFragment.class.getCanonicalName();
    private TriviaItemArrayAdapter triviaAdapter;
    private int score;
    private boolean session = false;
    private long sessionStartTime;
    private long sessionEndTime;

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

        newGame();

        list.setAdapter(triviaAdapter);
    }

    public TriviaItemArrayAdapter getTriviaAdapter() {
        return triviaAdapter;
    }

    public void incrementScore() {
        score++;
    }

    public int getScore() {
        return score;
    }

    public long getSessionStartTime() {
        return sessionStartTime;
    }

    public void setSessionStartTime(long startTime) {
        sessionStartTime = startTime;
    }

    public long getSessionEndTime() {
        return sessionEndTime;
    }

    public void setSessionEndTime(long endTime) {
        sessionEndTime = endTime;
    }

    public boolean getSession() {
        return session;
    }

    public void setSession(boolean thisSession) {
        session = thisSession;
    }

    public void newGame() {
        try {
            sessionStartTime = System.currentTimeMillis();
            session = true;
            score = 0;

            // Get preferences for API request
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String amount = sharedPreferences.getString(SettingsFragment.KEY_QUESTIONS_AMOUNT, "5");
            String difficulty = sharedPreferences.getString(SettingsFragment.KEY_QUESTIONS_DIFFICULTY, "any");

            // Create Volley Request
            HttpURLConnection urlConnection = null;
            try {
                String request_url = "https://opentdb.com/api.php?amount=" + amount + "&difficulty=" + difficulty + "/";
                URL url = new URL(request_url);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(urlConnection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                //print result
                System.out.println(response.toString());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }

            // Populate ArrayList of TriviaItem from parser
            // @Param: String response (json)
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
    }
}
