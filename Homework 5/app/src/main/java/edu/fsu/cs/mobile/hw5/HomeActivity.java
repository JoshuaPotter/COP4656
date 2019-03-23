package edu.fsu.cs.mobile.hw5;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    private TextView email;
    private TextView password;
    private TextView name;
    private TextView classes;
    private TextView role;
    private TextView lastlogin;
    private ListView users;
    private Button passwordButton;
    private String passwordVal;
    private boolean showPassword;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        email = (TextView) findViewById(R.id.textView_email);
        password = (TextView) findViewById(R.id.textView_password);
        name = (TextView) findViewById(R.id.textView_name);
        classes = (TextView) findViewById(R.id.textView_class);
        role = (TextView) findViewById(R.id.textView_role);
        lastlogin = (TextView) findViewById(R.id.textView_lastlogin);
        users = (ListView) findViewById(R.id.listView_users);
        passwordButton = (Button) findViewById(R.id.button_showPassword);
        showPassword = false;

        Intent intent = getIntent();
        bundle = intent.getExtras();

        if(bundle != null) {
            Uri userUri = Uri.parse(bundle.getString("uri"));
            if(userUri == null) {
                throw new IllegalArgumentException("Null URI in Home Activity");
            }
            setupView(userUri);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        ContentValues values = getCurrentContentValues();
        switch (item.getItemId()) {
            case R.id.option_delete:
//                onDelete(values);
                return true;
            case R.id.option_logout:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setupView(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if(cursor == null) {
            return; // nothing to show
        }

        if(cursor.getCount() > 0) {
            cursor.moveToNext();

            String sEmail = cursor.getString(cursor.getColumnIndex(
                    UserContract.UserEntry.EMAIL
            ));
            String sPassword = cursor.getString(cursor.getColumnIndex(
                    UserContract.UserEntry.PASSWORD
            ));
            String sName = cursor.getString(cursor.getColumnIndex(
                    UserContract.UserEntry.NAME
            ));
            String sRole = cursor.getString(cursor.getColumnIndex(
                    UserContract.UserEntry.ROLE
            ));
            String sClassname = cursor.getString(cursor.getColumnIndex(
                    UserContract.UserEntry.CLASSNAME
            ));
            String sLastlogin = cursor.getString(cursor.getColumnIndex(
                    UserContract.UserEntry.LASTLOGIN
            ));

            email.setText(sEmail);
            passwordVal = sPassword;
            password.setText("********");
            name.setText(sName);
            role.setText(sRole);
            classes.setText(sClassname);
            lastlogin.setText(sLastlogin);

            // get all users
            Cursor listCursor = getContentResolver().query(UserContract.CONTENT_URI, null, null, null, null);

            if(cursor == null) {
                return;
            }

            String[] columns = new String[] {
                    UserContract.UserEntry._ID,
                    UserContract.UserEntry.EMAIL,
                    UserContract.UserEntry.LASTLOGIN
            };

            int[] textViewIds = new int[] {
                    R.id.user_id,
                    R.id.user_email,
                    R.id.user_lastlogin
            };

            UserCursorAdapter adapter = new UserCursorAdapter(
                    HomeActivity.this,
                    R.layout.listview_users_row,
                    listCursor,
                    columns, textViewIds);

            users.setAdapter(adapter);
        }
    }

    public void showPassword(View view) {
        if(showPassword) {
            password.setText("********");
            passwordButton.setText("Show Password");
            showPassword = false;
        } else {
            password.setText(passwordVal);
            passwordButton.setText("Hide Password");
            showPassword = true;
        }
    }

}
