package com.example.pixelart.persistency;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by prebe on 21/01/2018.
 */

public class PixelArtDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "pixelart.db";
    private static final int DATABASE_VERSION = 1;

    private final String SQL_CREATE_PIXELART_TABLE
            = "CREATE TABLE " + PixelArtContract.PixelArtEntry.TABLE_NAME
            + " (" + PixelArtContract.PixelArtEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PixelArtContract.PixelArtEntry.COLUMN_BOARDNAME + " TEXT NOT NULL, "
            + PixelArtContract.PixelArtEntry.COLUMN_COLORS + " TEXT NOT NULL"
            + ");";

    private static final String SQL_DROP_PIXELART_TABLE =
            "DROP TABLE IF EXISTS " + PixelArtContract.PixelArtEntry.TABLE_NAME;

    public PixelArtDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PIXELART_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Ideally we wouldn't want to delete all of our entries!
        db.execSQL(SQL_DROP_PIXELART_TABLE);
        onCreate(db);	// Call to create a new db with upgraded schema and version
    }
}