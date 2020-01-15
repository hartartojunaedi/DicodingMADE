package com.example.consumerapp;

import android.database.Cursor;

import java.util.ArrayList;

public class MappingHelper {
    public static ArrayList<Movie> mapCursorToArrayList(Cursor notesCursor) {
        ArrayList<Movie> notesList = new ArrayList<>();

        while (notesCursor.moveToNext()) {
            //int id = notesCursor.getInt(notesCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns._ID));
            String title = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.TITLE));
            String description = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.DESCRIPTION));
            String photo = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.FOTO));
            //String type = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.TIPE));
            //String date = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.DATE));
            notesList.add(new Movie(title, description, photo));
        }

        return notesList;
    }
}
