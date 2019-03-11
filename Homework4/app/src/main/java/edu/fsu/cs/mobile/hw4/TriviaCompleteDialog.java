package edu.fsu.cs.mobile.hw4;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class TriviaCompleteDialog extends DialogFragment {
    public static final String TAG = TriviaCompleteDialog.class.getCanonicalName();
    private TriviaListFragment fragment;
    private AppCompatActivity activity;

    public static TriviaCompleteDialog newInstance(int score, long time) {
        TriviaCompleteDialog fragment = new TriviaCompleteDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("score", score);
        bundle.putLong("time", time);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int score = getArguments().getInt("score");
        long time = getArguments().getLong("time") / 1000; // Milliseconds to seconds
        long hours = time / 3600;
        long minutes = (time % 3600) / 60;
        long seconds = time % 60;

        fragment = ((TriviaListFragment) getActivity().getSupportFragmentManager()
                .findFragmentByTag(TriviaListFragment.TAG));

        return new AlertDialog.Builder(getActivity())
                .setIcon(R.drawable.ic_launcher_foreground)
                .setTitle("Game Completed!")
                .setMessage("Score: " + score + "\nElapsed Time: " + String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds))
                .setPositiveButton("Play Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        fragment.newGame();
                        getActivity().onBackPressed();
                    }
                })
                .setNegativeButton("Finish Game", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((MainActivity) getContext()).exitGame();
                    }
                })
                .create();
    }
}
