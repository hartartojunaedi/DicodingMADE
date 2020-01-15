package com.example.consumerapp;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String AUTHORITY = "com.example.mysubmitdicoding2";
    private static final String SCHEME = "content";

    public static String TABLE_NAME = "FavoriteDB";
    static final class FavoriteColumns implements BaseColumns {
        static String TITLE = "title";
        static String DESCRIPTION = "description";
        static String FOTO = "foto";
        static String TIPE = "tipe";
        static String DATE = "date";

        // untuk membuat URI content://com.dicoding.picodiploma.mynotesapp/note
        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }
}
