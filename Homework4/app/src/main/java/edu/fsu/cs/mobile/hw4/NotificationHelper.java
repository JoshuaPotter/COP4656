package edu.fsu.cs.mobile.hw4;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;

class NotificationHelper {
    private static boolean session = false;
    private static long sessionStartTime;
    private static long sessionEndTime;

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

        // Setup notification content
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("New Questions")
                .setContentText("Fetched " + size + " new questions.")
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
    static void gameSession(AppCompatActivity activity) {
        final int NOTIFICATION_ID = 1;
        final String CHANNEL_ID = "game_tracker";
        final String CHANNEL_NAME = "Game Tracker";
        final Context context = activity.getApplicationContext();

        // Get TriviaItemArrayAdapter from TriviaListFragment
        TriviaListFragment fragment = ((TriviaListFragment) activity.getSupportFragmentManager()
                .findFragmentByTag(TriviaListFragment.TAG));
        TriviaItemArrayAdapter adapter = fragment.getTriviaAdapter();
        int size = adapter.getCount();

        // Setup notification content
        if(!session) {
            sessionStartTime = System.currentTimeMillis();
            session = true;
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        builder.setContentTitle("Current Score: " + fragment.getScore() + "")
                .setContentText("Questions Remaining: " + size)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setShowWhen(true)
                .setOnlyAlertOnce(true)
                .setOngoing(true);

        // Set chronometer if game is still going, otherwise, disable
        if(adapter.getCount() == 0) {
            sessionEndTime = System.currentTimeMillis();
            builder.setUsesChronometer(false)
                    .setWhen(sessionEndTime);
        } else {
            builder.setUsesChronometer(true)
                    .setWhen(sessionStartTime);
        }

        // Create channel and push notification
        createNotificationChannel(CHANNEL_ID, CHANNEL_NAME, context);
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
