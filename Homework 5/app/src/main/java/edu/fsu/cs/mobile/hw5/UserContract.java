package edu.fsu.cs.mobile.hw5;

import android.content.ContentResolver;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.Locale;

public class UserContract {
    private static final String TAG = UserContract.class.getCanonicalName();
    public final static String DBNAME = "hw5.db";
    public final static Integer DBVERSION = 1;
    public static final String AUTHORITY = "edu.fsu.cs.mobile.hw5.provider";

    public static final int URI_USERS = 1;
    public static final int URI_USER_ID = 2;

    public class UserEntry implements BaseColumns {
        public static final String TABLE = "users";

        static final String EMAIL = "email";
        static final String PASSWORD = "password";
        static final String NAME = "name";
        static final String ROLE = "role";
        static final String CLASSNAME = "class";
        static final String LASTLOGIN = "lastlogin";
    }

    public static final Uri CONTENT_URI = Uri.parse(
            "content://" + AUTHORITY + "/" + UserEntry.TABLE);
    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/edu.fsu.cs.mobile.hw5/" + UserEntry.TABLE;
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/edu.fsu.cs.mobile.hw5/" + UserEntry.TABLE;

    public static final String SQL_CREATE_TRANSACTION_TABLE =
            String.format(Locale.US, "CREATE TABLE %s (" // UserEntry.TABLE
                            + "%s INTEGER PRIMARY KEY AUTOINCREMENT," // COLUMN _ID
                            + " %s TEXT NOT NULL," // COLUMN_EMAIL
                            + " %s TEXT NOT NULL," // COLUMN_PASSWORD
                            + " %s TEXT," // COLUMN_NAME
                            + " %s TEXT," // COLUMN_ROLE
                            + " %s TEXT," // COLUMN_CLASS
                            + " %s TEXT);", // COLUMN_LASTLOGIN
                    UserEntry.TABLE, UserEntry._ID, UserEntry.EMAIL,
                    UserEntry.PASSWORD, UserEntry.NAME, UserEntry.ROLE, UserEntry.CLASSNAME, UserEntry.LASTLOGIN);

    public static final class MainDatabaseHelper extends SQLiteOpenHelper {
        MainDatabaseHelper(Context context) {
            super(context, DBNAME, null, DBVERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i(TAG, "Creating: " + SQL_CREATE_TRANSACTION_TABLE);
            db.execSQL(SQL_CREATE_TRANSACTION_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(String.format(Locale.US,
                    "DROP TABLE IF EXISTS %s", UserEntry.TABLE));
            onCreate(db);
        }
    }
}
