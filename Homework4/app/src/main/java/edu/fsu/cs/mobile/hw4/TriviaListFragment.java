// TriviaListFragment
package edu.fsu.cs.mobile.hw4;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
            notificationFetchedQuestions(parsedItems.size());

            // Add TriviaItems to adapter
            for(TriviaItem item : parsedItems) {
                triviaAdapter.add(item);
            }
        } catch (JSONException event) {
            System.out.println("/------------------------ Error: API parser failed. --------------------------/");
        }

        list.setAdapter(triviaAdapter);
    }

    public TriviaItemArrayAdapter getTriviaAdapter() {
        return triviaAdapter;
    }

    // Display number of fetched questions in a notification
    public void notificationFetchedQuestions(Integer i) {
        final String CHANNEL_ID = "new_questions";
        final String CHANNEL_NAME = "New Questions";

        // Create notification and assign to channel
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), CHANNEL_ID)
                .setContentTitle(i + " new questions")
                .setContentText("Fetched " + i + " new questions.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSmallIcon(android.R.color.transparent)
                .setWhen(System.currentTimeMillis())
                .setShowWhen(true)
                .setAutoCancel(true);

        // Create the channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        // Push the notification
        NotificationManagerCompat nm = NotificationManagerCompat.from(getActivity());
        nm.notify(0, builder.build());
    }
}
