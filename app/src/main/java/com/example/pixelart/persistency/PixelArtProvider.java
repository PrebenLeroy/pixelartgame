package com.example.pixelart.persistency;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import static com.example.pixelart.persistency.PixelArtContract.CONTENT_AUTHORITY;
import static com.example.pixelart.persistency.PixelArtContract.PATH_BOARDS;
import static com.example.pixelart.persistency.PixelArtContract.PixelArtEntry.TABLE_NAME;

/**
 * Created by prebe on 21/01/2018.
 */

public class PixelArtProvider extends ContentProvider {
    private static final String TAG = PixelArtProvider.class.getSimpleName();
    private PixelArtDbHelper databaseHelper;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int BOARDS = 1; //whole table
    private static final int BOARDS_ID = 2;//row by id
    private static final int BOARDS_BOARD_NAME = 3; // rows by boardname

    static{
        //contains all URI patterns
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_BOARDS, BOARDS);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_BOARDS + "/#", BOARDS_ID);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_BOARDS + "/*", BOARDS_BOARD_NAME);
    }

    @Override
    public boolean onCreate() {
        databaseHelper = new PixelArtDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        Cursor cursor;

        switch (uriMatcher.match(uri)) {

            case BOARDS:
                cursor = database.query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;

            case BOARDS_ID:
                selection = PixelArtContract.PixelArtEntry._ID + " = ?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                cursor = database.query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;

            default:
                throw new IllegalArgumentException(TAG + "Unknown URI: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        switch (uriMatcher.match(uri)) {

            case BOARDS:
                return insertRecord(uri, values, TABLE_NAME);
            default:
                throw new IllegalArgumentException(TAG + "Unknown URI: " + uri);
        }
    }

    private Uri insertRecord(Uri uri, ContentValues values, String tableName) {

        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        long rowId = database.insert(tableName, null, values);

        if (rowId == -1) {
            Log.e(TAG, "Insert error for URI " + uri);
            return null;
        } else {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return ContentUris.withAppendedId(uri, rowId);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        switch (uriMatcher.match(uri)) {
            case BOARDS:			// To delete whole table
                return deleteRecord(uri, null, null, TABLE_NAME);
            case BOARDS_ID:		// To delete a row by ID
                selection = PixelArtContract.PixelArtEntry._ID + " = ?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return deleteRecord(uri, selection, selectionArgs, TABLE_NAME);
            case BOARDS_BOARD_NAME:	// To Delete a row by COUNTRY NAME
                selection = PixelArtContract.PixelArtEntry.COLUMN_BOARDNAME + " = ? ";
                selectionArgs = new String[] { String.valueOf(uri.getLastPathSegment()) };
                return deleteRecord(uri, selection, selectionArgs, TABLE_NAME);
            default:
                throw new IllegalArgumentException(TAG + "Unknown URI: " + uri);
        }
    }

    private int deleteRecord(Uri uri, String selection, String[] selectionArgs, String tableName) {

        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        int rowsDeleted = database.delete(tableName, selection, selectionArgs);

        if (rowsDeleted != 0 ) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        switch (uriMatcher.match(uri)) {

            case BOARDS:
                return updateRecord(uri, values, selection, selectionArgs, TABLE_NAME);
            default:
                throw new IllegalArgumentException(TAG + "Unknown URI: " + uri);
        }
    }

    private int updateRecord(Uri uri, ContentValues values, String selection, String[] selectionArgs, String tableName) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        int rowsUpdated = database.update(tableName, values, selection, selectionArgs);

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }
}