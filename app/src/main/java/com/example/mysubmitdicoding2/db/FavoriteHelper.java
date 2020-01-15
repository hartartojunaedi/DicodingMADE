package com.example.mysubmitdicoding2.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mysubmitdicoding2.Movie;

import static com.example.mysubmitdicoding2.db.DatabaseContract.TABLE_NAME;

public class FavoriteHelper {
    private static final String DATABASE_TABLE = TABLE_NAME;
    private static DatabaseHelper dataBaseHelper;
    private static FavoriteHelper INSTANCE;

    public static FavoriteHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FavoriteHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }
    public void close() {
        dataBaseHelper.close();
        if (database.isOpen())
            database.close();
    }

    public FavoriteHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }
    public Cursor queryAll(String t) {
        return database.query(
                DATABASE_TABLE,
                null,
                t,
                null,
                null,
                null,
                null);
    }

    public Cursor queryById(String id) {
        return database.query(
                DATABASE_TABLE,
                null,
                "TITLE" + " = ?",
                new String[]{id},
                null,
                null,
                null,
                null);
    }
    private static SQLiteDatabase database;

    public long insert(Movie movie, String tipe, String date) {
        ContentValues args = new ContentValues();
        args.put(DatabaseContract.FavoriteColumns.TITLE,movie.getName());
        args.put(DatabaseContract.FavoriteColumns.DESCRIPTION,movie.getDescription());
        args.put(DatabaseContract.FavoriteColumns.FOTO,movie.getPhoto());
        args.put(DatabaseContract.FavoriteColumns.TIPE,tipe);
        args.put(DatabaseContract.FavoriteColumns.DATE,date);
        return database.insert(DATABASE_TABLE, null, args);
    }

    public int update(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, "TITLE  = ?", new String[]{id});
    }

    public int deleteById(String id) {
        return database.delete(DATABASE_TABLE, "TITLE  = ?", new String[]{id});
    }

}
