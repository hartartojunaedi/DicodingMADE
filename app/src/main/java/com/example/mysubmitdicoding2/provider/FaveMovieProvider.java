package com.example.mysubmitdicoding2.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.example.mysubmitdicoding2.db.FavoriteHelper;

import static com.example.mysubmitdicoding2.db.DatabaseContract.AUTHORITY;
import static com.example.mysubmitdicoding2.db.DatabaseContract.TABLE_NAME;

public class FaveMovieProvider extends ContentProvider {
    private static final int NOTE = 1;
    private static final int NOTE_ID = 2;
    private FavoriteHelper favoriteHelper;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        // content://com.dicoding.picodiploma.mynotesapp/note
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME, NOTE);
        // content://com.dicoding.picodiploma.mynotesapp/note/id
        sUriMatcher.addURI(AUTHORITY,
                TABLE_NAME + "/#",
                NOTE_ID);
    }

    public FaveMovieProvider() {
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        favoriteHelper = FavoriteHelper.getInstance(getContext());
        favoriteHelper.open();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
       // throw new UnsupportedOperationException("Not yet implemented");
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case NOTE:
                if (sortOrder.equals("asc")){
                cursor = favoriteHelper.queryAll("tipe='movie'");}
                else
                { cursor = favoriteHelper.queryAll("tipe='TV'");}
                //System.out.println("jum:" + cursor.getCount());
                break;
            case NOTE_ID:
                //cursor = favoriteHelper.queryById(uri.getLastPathSegment());
                cursor = favoriteHelper.queryAll("tipe='tipe='Movie''");
                System.out.println("jum:" + cursor.getCount());
                break;
            default:
               // cursor = null;
                cursor = favoriteHelper.queryAll("tipe='movie'");
                System.out.println("jum:" + cursor.getCount());
                break;
        }

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
