package com.example.consumerapp;


import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMovie extends Fragment implements LoadNotesCallback {

    private ListMovieAdapter movieadapter;
    private ProgressBar progressBar;
    private ArrayList<Movie> movies = new ArrayList<>();
    private RecyclerView rvView;

    public FavoriteMovie() {
        // Required empty public constructor
    }

    private Observer<ArrayList<Movie>> getMovie = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> movies) {
            if (movies != null) {
                movieadapter.setData(movies);
            }

            showLoading(false);
        }


    };
    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        loadFavoriteMovie();
    }

    private void loadFavoriteMovie() {


        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        DataObserver myObserver = new DataObserver(handler, getActivity());
        getActivity().getContentResolver().registerContentObserver(DatabaseContract.FavoriteColumns.CONTENT_URI, true, myObserver);

        new LoadNoteAsync(getActivity(), this).execute();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       //return inflater.inflate(R.layout.fragment_favorite_movie, container, false);
        movieadapter = new ListMovieAdapter();
        View view = inflater.inflate(R.layout.fragment_favorite_movie, container, false);
        rvView = view.findViewById(R.id.rv_movie);
        rvView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvView.setAdapter(movieadapter);
        progressBar = view.findViewById(R.id.pbar);

        movieadapter.setOnClickCallback(new ListMovieAdapter.OnClickCallback() {
            @Override
            public void onClick(Movie movie) {
                Intent detailIntent = new Intent(getActivity(), Detail.class);
                detailIntent.putExtra(Detail.EXTRA_TIPE,"movie");
                detailIntent.putExtra(Detail.EXTRA_FILM, movie);
                startActivity(detailIntent);
            }
        });

        loadFavoriteMovie();

        return view;
    }

    @Override
    public void preExecute() {
        showLoading(true);
    }

    @Override
    public void postExecute(ArrayList<Movie> notes) {
        movies.clear();
        movies.addAll(notes);
        //Toast.makeText(getActivity(), movies.size() + "", Toast.LENGTH_SHORT).show();
        movieadapter.setItemArrayList(movies);
        showLoading(false);

        if (movies.size() == 0) {
            Toast.makeText(getActivity(), getResources().getString(R.string.no_data), Toast.LENGTH_SHORT).show();
        }
    }

    public static class DataObserver extends ContentObserver {
        final Context context;
        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new LoadNoteAsync(context, (LoadNotesCallback) context).execute();
        }
    }

    private static class LoadNoteAsync extends AsyncTask<Void, Void, ArrayList<Movie>> {

        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadNotesCallback> weakCallback;

        private LoadNoteAsync(Context context, LoadNotesCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<Movie> doInBackground(Void... voids) {
            Context context = weakContext.get();
            Log.d("KEY","Out");
            Cursor dataCursor = context.getContentResolver().query(DatabaseContract.FavoriteColumns.CONTENT_URI, null, null, null, "asc");
            Log.d("KEY",dataCursor.toString());
            //Toast.makeText(context, dataCursor.getCount() + "", Toast.LENGTH_SHORT).show();
            return MappingHelper.mapCursorToArrayList(dataCursor);
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> notes) {
            super.onPostExecute(notes);
            weakCallback.get().postExecute(notes);
        }
    }

}

interface LoadNotesCallback {
    void preExecute();
    void postExecute(ArrayList<Movie> notes);
}