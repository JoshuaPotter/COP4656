package edu.fsu.cs.mobile.hw4;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class TriviaItemFragment extends Fragment {
    public static final String TAG = TriviaItemFragment.class.getCanonicalName();
    private TriviaItem item;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if(bundle != null) {
            item = bundle.getParcelable("item");
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trivia_item, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // populate fields with TriviaItem item
    }
}
