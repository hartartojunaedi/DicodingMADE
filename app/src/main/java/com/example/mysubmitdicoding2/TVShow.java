package com.example.mysubmitdicoding2;


import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TVShow extends Fragment {
    private ListMovieAdapter tvshowadapter;
    private ArrayList<Movie> tvshows= new ArrayList<Movie>();
    private RecyclerView rvmovie;
    private TVViewModel tvViewModel;
    public static final String EXTRA_QUERY = "Swat";
    private ProgressBar pb;
    public TVShow() {
        Bundle mBundle = new Bundle();
        mBundle.putString(this.EXTRA_QUERY, "");
        this.setArguments(mBundle);
    }
    public void updateList(String query) {
        Bundle mBundle = new Bundle();
        mBundle.putString(this.EXTRA_QUERY, query);
        this.setArguments(mBundle);
        //Toast.makeText(getContext(),query,Toast.LENGTH_SHORT).show();
        refresh();
    }
    void refresh(){
        tvViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(TVViewModel.class);


        tvViewModel.getMovies().observe(this,getMovie);
        showLoading(true);
        String query = getArguments().getString(EXTRA_QUERY);
        //String query="";
        if (query==""){
            tvViewModel.setFilm(1,query);
        }
        else {
            tvViewModel.setFilm(2,query);
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_tvshow, container, false);
        tvshowadapter = new ListMovieAdapter(tvshows);
        View view = inflater.inflate(R.layout.fragment_movie_show, container, false);
        rvmovie = view.findViewById(R.id.rv_movie);
        rvmovie.setLayoutManager(new LinearLayoutManager(this.getContext()));
        tvshowadapter.setOnClickCallback(new ListMovieAdapter.OnClickCallback() {
            @Override
            public void onClick(Movie movie) {
                Intent detailIntent = new Intent(getActivity(), Detail.class);
                detailIntent.putExtra(Detail.EXTRA_TIPE, "TV");
                detailIntent.putExtra(Detail.EXTRA_FILM, movie);
                startActivity(detailIntent);
            }
        });
        rvmovie.setAdapter(tvshowadapter);
        pb = view.findViewById(R.id.pb);
        //  mainViewModel= new ViewModelProviders.of(this).get(MainViewModel.class);

        tvViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(TVViewModel.class);


        tvViewModel.getMovies().observe(this,getMovie);
        showLoading(true);
        String query = getArguments().getString(EXTRA_QUERY);
        //String query="";
        if (query==""){
            tvViewModel.setFilm(1,query);
        }
        else {
            tvViewModel.setFilm(2,query);
        }

        return view;
    }
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        tvshowadapter = new ListMovieAdapter(tvshows);
//
//        rvmovie = view.findViewById(R.id.rv_movie);
//        rvmovie.setHasFixedSize(true);
//
//        tvshows.clear();
//        tvshows.addAll(getListTVShow());
//        showRecyclerList();
//
//    }
//    public ArrayList<Movie> getListTVShow() {
//        String[] dataMovie = getResources().getStringArray(R.array.data_tv);
//        String[] dataDescription = getResources().getStringArray(R.array.data_tv_description);
//        TypedArray dataPhoto = getResources().obtainTypedArray(R.array.data_Cover_tv);
//
//        ArrayList<Movie> listMovie = new ArrayList<>();
//        for (int i = 0; i < dataMovie.length; i++) {
//            Movie movie = new Movie();
//            movie.setName(dataMovie[i]);
//            movie.setDescription(dataDescription[i]);
//            movie.setPhoto(dataPhoto.getResourceId(i, -1));
//            listMovie.add(movie);
//        }
//        return listMovie;
//    }
//    private void showRecyclerList() {
//        rvmovie.setLayoutManager(new LinearLayoutManager(getContext()));
//        ListMovieAdapter listMovieAdapter = new ListMovieAdapter(tvshows);
//        rvmovie.setAdapter(listMovieAdapter);
//        listMovieAdapter.setOnClickCallback(new ListMovieAdapter.OnClickCallback() {
//            @Override
//            public void onClick(Movie movie) {
//                Intent detailIntent = new Intent(getActivity(), Detail.class);
//                detailIntent.putExtra(Detail.EXTRA_FILM, movie);
//                startActivity(detailIntent);
//            }
//        });
//    }
private Observer<ArrayList<Movie>> getMovie = new Observer<ArrayList<Movie>>() {
    @Override
    public void onChanged(ArrayList<Movie> movies) {
        if (movies != null) {
            tvshowadapter.setData(movies);
        }

        showLoading(false);
    }


};
    private void showLoading(Boolean state) {
        if (state) {
            pb.setVisibility(View.VISIBLE);
        } else {
            pb.setVisibility(View.GONE);
        }
    }
}
