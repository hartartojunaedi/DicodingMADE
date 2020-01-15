package com.example.mysubmitdicoding2;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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

import com.example.mysubmitdicoding2.db.FavoriteHelper;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TVShowFavorite extends Fragment {

    private ListMovieAdapter movieadapter;
    private FavoriteHelper favoriteHelper;
    private ProgressBar progressBar;
    private ArrayList<Movie> movies = new ArrayList<>();
    private RecyclerView rvView;

    public TVShowFavorite() {
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
        showLoading(true);

        favoriteHelper = FavoriteHelper.getInstance(getContext());
        favoriteHelper.open();

        Cursor cursor =favoriteHelper.queryAll("tipe='TV'");
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


            } while(cursor.moveToNext());
        }
        //  faveHelper.close();
        movieadapter.setItemArrayList(movies);
       // favoriteHelper.close();

        //showRecyclerView();

        showLoading(false);

        if (movies.size() == 0) {
            Toast.makeText(getActivity(), getResources().getString(R.string.no_data), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_tvshow_favorite, container, false);
        movieadapter = new ListMovieAdapter();
        View view = inflater.inflate(R.layout.fragment_tvshow_favorite, container, false);
        rvView = view.findViewById(R.id.rv_movie);
        rvView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvView.setAdapter(movieadapter);
        progressBar = view.findViewById(R.id.pbar);

        movieadapter.setOnClickCallback(new ListMovieAdapter.OnClickCallback() {
            @Override
            public void onClick(Movie movie) {
                Intent detailIntent = new Intent(getActivity(), Detail.class);
                detailIntent.putExtra(Detail.EXTRA_TIPE,"TV");
                detailIntent.putExtra(Detail.EXTRA_FILM, movie);
                startActivity(detailIntent);
            }
        });

        loadFavoriteMovie();

        return view;
    }

}
