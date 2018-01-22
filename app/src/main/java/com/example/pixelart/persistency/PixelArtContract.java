package com.example.pixelart.persistency;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by prebe on 21/01/2018.
 */

public final class PixelArtContract {

    //EERST schema aanmaken die database gaat declareren
    //added voor contentprovider
    public static final String CONTENT_AUTHORITY = "com.example.provider.pixelart";

    //added voor contentprovider
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    //added voor contentprovider
    // Path to get CLIENT APP to our table
    public static final String PATH_BOARDS = "boards";

    public static final class PixelArtEntry implements BaseColumns {

        //added voor contentprovider
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_BOARDS);

        // Table Name
        public static final String TABLE_NAME = "boards";

        // Columns
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_BOARDNAME = "boardname";
        public static final String COLUMN_COLORS = "colors";
    }
}
