package edu.fsu.cs.mobile.hw4;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
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
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        builder.setContentTitle("Current Score: " + fragment.getScore() + "")
                .setContentText("Questions Remaining: " + size)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setShowWhen(true)
                .setOnlyAlertOnce(true)
                .setOngoing(true);

        // Start timer on game start
        //    If there are no questions left, stop timer
        if(adapter.getCount() == 0) {
            fragment.setSessionEndTime(System.currentTimeMillis());
            builder.setUsesChronometer(false)
                    .setWhen(fragment.getSessionEndTime());

            // Set High Score Preference
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
            if(fragment.getScore() > Integer.parseInt(sharedPreferences.getString(SettingsFragment.KEY_HIGH_SCORE, "0"))) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(SettingsFragment.KEY_HIGH_SCORE, Integer.toString(fragment.getScore()));
                editor.commit();
            }

            // Show TriviaCompleteDialog
            DialogFragment dialog = TriviaCompleteDialog.newInstance(fragment.getScore(), fragment.getSessionEndTime() - fragment.getSessionStartTime());
            dialog.show(activity.getSupportFragmentManager(), TriviaCompleteDialog.TAG);
        } else {
            builder.setUsesChronometer(true)
                    .setWhen(fragment.getSessionStartTime());
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
