package edu.fsu.cs.mobile.hw5;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class UserCursorAdapter extends SimpleCursorAdapter {
    public UserCursorAdapter(Context context, int layout, Cursor cursor, String[] from, int[] to) {
        super(context, layout, cursor, from, to);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.listview_users_row, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView user_id = (TextView) view.findViewById(R.id.user_id);
        TextView user_email = (TextView) view.findViewById(R.id.user_email);
        TextView user_lastlogin = (TextView) view.findViewById(R.id.user_lastlogin);

        int id = cursor.getInt(cursor.getColumnIndex(UserContract.UserEntry._ID));
        String email = cursor.getString(cursor.getColumnIndex(UserContract.UserEntry.EMAIL));
        String lastLogin = cursor.getString(cursor.getColumnIndex(UserContract.UserEntry.LASTLOGIN));

        user_id.setText(Integer.toString(id));
        user_email.setText(email);
        user_lastlogin.setText(lastLogin);
    }
}
