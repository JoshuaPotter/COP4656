package edu.fsu.cs.mobile.hw4;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GetQuestions extends AsyncTask<String, Integer, String> {
    private FragmentActivity activity;

    public GetQuestions(FragmentActivity activity) {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... params) {
        String response = null;
        StringBuffer sb = new StringBuffer();

        try {
            URL url = new URL(params[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String inputLine = "";
                while ((inputLine = br.readLine()) != null) {
                    sb.append(inputLine);
                }
                response = sb.toString();
            } finally {
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//        System.out.println(response);

        return response;
    }

    @Override
    protected void onPostExecute(String result) {
        // Populate ArrayList of TriviaItem from parser
        // @Param: String response (json)
        try {
            ArrayList<TriviaItem> parsedItems = OpentdbParser.parseTriviaItems(result);

            // Add TriviaItems to adapter
            for(TriviaItem item : parsedItems) {
                TriviaListFragment fragment = ((TriviaListFragment) activity.getSupportFragmentManager()
                        .findFragmentByTag(TriviaListFragment.TAG));
                TriviaItemArrayAdapter adapter = fragment.getTriviaAdapter();
                adapter.add(item);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        // Show notification for new questions
        NotificationHelper.fetchedQuestions((MainActivity) activity);

        // Start ongoing notification to track game session
        NotificationHelper.gameSession((MainActivity) activity);
    }
}
