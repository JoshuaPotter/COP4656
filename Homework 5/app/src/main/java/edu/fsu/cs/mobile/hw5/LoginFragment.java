package edu.fsu.cs.mobile.hw5;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class LoginFragment extends Fragment {
    public static final String TAG = LoginFragment.class.getCanonicalName();
    private EditText email;
    private EditText password;
    private Button btn;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        email = (EditText) getActivity().findViewById(R.id.editText_email_login);
        password = (EditText) getActivity().findViewById(R.id.editText_password_login);
        btn = (Button) getActivity().findViewById(R.id.button_login);
    }

    public void loginUser(View view) {
        // Set ContentValues
        ContentValues values = getCurrentContentValues();
        Uri newUri = onQuery(values);

        if(newUri != null) {
            email.setError(null);
            password.setError(null);

            // Construct user profile and send to HomeActivity for display
            Intent homeActivity = new Intent(getContext(), HomeActivity.class);

            Bundle bundle = new Bundle();
            // put uri
            bundle.putString("uri", newUri.toString());

            homeActivity.putExtras(bundle);
            startActivity(homeActivity);
        } else {
            email.setError("Invalid login");
            password.setError("Invalid login");
        }
    }

    private ContentValues getCurrentContentValues() {
        ContentValues values = new ContentValues();

        String email_val = email.getText().toString().trim();
        String password_val = password.getText().toString().trim();
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss", Locale.US);
        String current = dateFormat.format(date);

        values.put(UserContract.UserEntry.EMAIL, email_val);
        values.put(UserContract.UserEntry.PASSWORD, password_val);
        values.put(UserContract.UserEntry.LASTLOGIN, current);

        return values;
    }

    public Uri onQuery(ContentValues values) {
        String[] args = null;
        String where = null;
        Uri newUri = null;

        if(values.containsKey(UserContract.UserEntry.EMAIL) && values.containsKey(UserContract.UserEntry.PASSWORD)) {
            String email = values.getAsString(UserContract.UserEntry.EMAIL);
            String password = values.getAsString(UserContract.UserEntry.PASSWORD);
            where = UserContract.UserEntry.EMAIL + " = ? AND " + UserContract.UserEntry.PASSWORD + " = ?";
            args = new String[] {email, password};
        }

        Cursor mCursor = getActivity().getContentResolver().query(UserContract.CONTENT_URI, null, where, args, null);

        if(mCursor != null) {
            if (mCursor.getCount() > 0) {
                mCursor.moveToNext();
                int rowId = mCursor.getInt(mCursor.getColumnIndex(UserContract.UserEntry._ID));

                newUri = ContentUris.withAppendedId(UserContract.CONTENT_URI, rowId);

            }
        }

        return newUri;
    }
}
