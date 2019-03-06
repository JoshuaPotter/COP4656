package edu.fsu.cs.mobile.hw4;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;

class NotificationHelper {
    // Display number of fetched questions in a notification
    static void fetchedQuestions(AppCompatActivity activity) {
        final int NOTIFICATION_ID = 0;
        final String CHANNEL_ID = "new_questions";
        final String CHANNEL_NAME = "New Questions";
        final Context context = activity.getApplicationContext();

        // Get TriviaItemArrayAdapter from TriviaListFragment
        TriviaItemArrayAdapter adapter = ((TriviaListFragment) activity.getSupportFragmentManager()
                .findFragmentByTag(TriviaListFragment.TAG)).getTriviaAdapter();
        int size = adapter.getCount();

        // Create notification and assign to channel
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(size + " new questions")
                .setContentText("Fetched " + size + " new questions, time to get your trivia on.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setWhen(System.currentTimeMillis())
                .setShowWhen(true)
                .setAutoCancel(true);

        // Create channel and push notification
        createNotificationChannel(CHANNEL_ID, CHANNEL_NAME, context);
        NotificationManagerCompat nm = NotificationManagerCompat.from(context);
        nm.notify(NOTIFICATION_ID, builder.build());
    }

    // Display number of remaining questions, score, and elapsed time
    static void trackGame(AppCompatActivity activity) {
        final int NOTIFICATION_ID = 1;
        final String CHANNEL_ID = "game_tracker";
        final String CHANNEL_NAME = "Game Tracker";
        final Context context = activity.getApplicationContext();

        // Get TriviaItemArrayAdapter from TriviaListFragment
        TriviaItemArrayAdapter adapter = ((TriviaListFragment) activity.getSupportFragmentManager()
                .findFragmentByTag(TriviaListFragment.TAG)).getTriviaAdapter();
        int size = adapter.getCount();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("Current Game (Score: )")
                .setContentText("Questions: " + size)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setWhen(System.currentTimeMillis())
                .setUsesChronometer(true)
                .setShowWhen(true)
                .setOngoing(true)
                .setOnlyAlertOnce(true);

        // Create channel and push notification
        createNotificationChannel(CHANNEL_ID, CHANNEL_NAME, context);
        NotificationManagerCompat nm = NotificationManagerCompat.from(context);
        nm.notify(NOTIFICATION_ID, builder.build());
    }

    static void updateGame(AppCompatActivity activity) {
        final int NOTIFICATION_ID = 1;
        final String CHANNEL_ID = "game_tracker";
        final Context context = activity.getApplicationContext();

        // Get TriviaItemArrayAdapter from TriviaListFragment
        TriviaItemArrayAdapter adapter = ((TriviaListFragment) activity.getSupportFragmentManager()
                .findFragmentByTag(TriviaListFragment.TAG)).getTriviaAdapter();
        int size = adapter.getCount();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("Current Game (Score: )")
                .setContentText("Questions: " + size);

        // Create channel and push notification
        NotificationManagerCompat nm = NotificationManagerCompat.from(context);
        nm.notify(NOTIFICATION_ID, builder.build());
    }

    static void createNotificationChannel(String CHANNEL_ID, String CHANNEL_NAME, Context context) {
        // Create the channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
