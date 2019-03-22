package edu.fsu.cs.mobile.hw5;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;

public class UserContentProvider extends ContentProvider {
    private UserContract.MainDatabaseHelper mOpenHelper;

    private static final UriMatcher sUriMatcher;

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(UserContract.AUTHORITY,
                UserContract.UserEntry.TABLE,
                UserContract.URI_USERS);
        sUriMatcher.addURI(UserContract.AUTHORITY,
                UserContract.UserEntry.TABLE + "/#",
                UserContract.URI_USER_ID);
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new UserContract.MainDatabaseHelper(getContext());

        return (mOpenHelper.getWritableDatabase() != null);
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        if(sUriMatcher.match(uri) != UserContract.URI_USERS) {
            throw new IllegalArgumentException("Invalid insert URI: " + uri);
        }

        long rowId = db.insert(UserContract.UserEntry.TABLE, "", values);

        if(rowId <= 0) {
            throw new SQLException("Failed to insert into " + uri);
        }

        Uri insertedUri = ContentUris.withAppendedId(UserContract.CONTENT_URI, rowId);
        getContext().getContentResolver().notifyChange(insertedUri, null);

        return insertedUri;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {

        SQLiteDatabase db = mOpenHelper.getReadableDatabase();

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(UserContract.UserEntry.TABLE);
        switch (sUriMatcher.match(uri)) {
            case UserContract.URI_USERS:
                // Querying many transactions
                break;
            case UserContract.URI_USER_ID:
                // Querying specific id
                builder.appendWhere(UserContract.UserEntry._ID + " = " +
                        uri.getLastPathSegment());
                break;
        }

        return builder.query(db, projection, selection, selectionArgs,
                null, null, sortOrder);
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int numUpdated;
        switch (sUriMatcher.match(uri)) {
            case UserContract.URI_USERS:
                numUpdated = db.update(UserContract.UserEntry.TABLE, values,
                        selection, selectionArgs);
                break;
            case UserContract.URI_USER_ID:
                String where = UserContract.UserEntry._ID + " = " +
                        uri.getLastPathSegment();
                if (!selection.isEmpty()) {
                    where += " AND " + selection;
                }
                numUpdated = db.update(UserContract.UserEntry.TABLE, values, where,
                        selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Invalid update URI: " + uri);
        }

        return numUpdated;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int numDeleted;
        switch (sUriMatcher.match(uri)) {
            case UserContract.URI_USERS:
                numDeleted = db.delete(UserContract.UserEntry.TABLE, selection,
                        selectionArgs);
                break;
            case UserContract.URI_USER_ID:
                String where = UserContract.UserEntry._ID + " = " +
                        uri.getLastPathSegment();
                if (!selection.isEmpty()) {
                    where += " AND " + selection;
                }
                numDeleted = db.delete(UserContract.UserEntry.TABLE, where,
                        selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Invalid delete URI: " + uri);
        }

        return numDeleted;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case UserContract.URI_USERS:
                return UserContract.CONTENT_TYPE;
            case UserContract.URI_USER_ID:
                return UserContract.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }
}
