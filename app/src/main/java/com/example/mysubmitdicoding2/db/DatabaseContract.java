package com.example.mysubmitdicoding2.db;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String AUTHORITY = "com.example.mysubmitdicoding2";
    private static final String SCHEME = "content";

    public static String TABLE_NAME = "FavoriteDB";
    public static final class FavoriteColumns implements BaseColumns {
        public static String TITLE = "title";
        public static String DESCRIPTION = "description";
        public static String FOTO = "foto";
        public static String TIPE = "tipe";
        public static String DATE = "date";

        // untuk membuat URI content://com.dicoding.picodiploma.mynotesapp/note
        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }
}
