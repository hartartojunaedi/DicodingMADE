package com.example.mysubmitdicoding2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mysubmitdicoding2.db.FavoriteHelper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private final List<Bitmap> mWidgetItems = new ArrayList<>();
    private final Context mContext;
    private ListMovieAdapter movieadapter;
    private FavoriteHelper favoriteHelper;
    private ArrayList<Movie> movies = new ArrayList<>();


    public StackRemoteViewsFactory(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onCreate() {

    }


    @Override
    public void onDataSetChanged() {

        favoriteHelper = FavoriteHelper.getInstance(mContext);
        favoriteHelper.open();
        Cursor cursor =favoriteHelper.queryAll("tipe='movie'");
        int i=cursor.getCount();
        Log.i("Cek Error",i+"");
        movies.clear();
        if (cursor.moveToFirst())
        {
            do {

                Movie movie = new Movie();
                movie.setName(cursor.getString(1));
                movie.setDescription(cursor.getString(2));
                movie.setPhoto(cursor.getString(3));
                movies.add(movie);
                //  Log.i(cursor.getString(2),"nama film:");
                String url_image = "https://image.tmdb.org/t/p/w185" + movie.getPhoto();
                Bitmap bmp= null;
                try {
                    bmp = Glide.with(mContext).asBitmap().load(url_image).submit().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mWidgetItems.add(bmp);
                //mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.evil));
            } while(cursor.moveToNext());
        }
        favoriteHelper.close();
//       mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.arrow));
//       mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.avenger));
//       mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.beijing));
//       mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.arrow));mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.dark));
//       mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.evil));
//       mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.arrow));

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mWidgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

        rv.setImageViewBitmap(R.id.wfavitem, mWidgetItems.get(position));
        Bundle extras = new Bundle();
        extras.putInt(FavoriteWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        rv.setOnClickFillInIntent(R.id.wfavitem, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
