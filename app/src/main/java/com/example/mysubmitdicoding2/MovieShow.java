package com.example.mysubmitdicoding2;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_SHORT;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieShow extends Fragment {
    private ListMovieAdapter movieadapter;
    private ArrayList<Movie> movies= new ArrayList<Movie>();
    private RecyclerView rvmovie;
    private MainViewModel mainViewModel;
    private ProgressBar pb;
    public static final String EXTRA_QUERY = "Frozen";
    public MovieShow() {
        // Required empty public constructor
        Bundle mBundle = new Bundle();
        mBundle.putString(this.EXTRA_QUERY, "");
        this.setArguments(mBundle);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_movie_show, container, false);
        movieadapter = new ListMovieAdapter(movies);
        View view = inflater.inflate(R.layout.fragment_movie_show, container, false);
        rvmovie = view.findViewById(R.id.rv_movie);
        rvmovie.setLayoutManager(new LinearLayoutManager(this.getContext()));
        movieadapter.setOnClickCallback(new ListMovieAdapter.OnClickCallback() {
            @Override
            public void onClick(Movie movie) {
                Intent detailIntent = new Intent(getActivity(), Detail.class);
                detailIntent.putExtra(Detail.EXTRA_FILM, movie);
                detailIntent.putExtra(Detail.EXTRA_TIPE,"movie");
                startActivity(detailIntent);
            }
        });
        rvmovie.setAdapter(movieadapter);
        pb = view.findViewById(R.id.pb);
        //  mainViewModel= new ViewModelProviders.of(this).get(MainViewModel.class);

        mainViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);


        mainViewModel.getMovies().observe(this,getMovie);
        showLoading(true);
        String query = getArguments().getString(EXTRA_QUERY);
        //String query="";
        if (query==""){
            mainViewModel.setFilm(1,query);
        }
        else {
            mainViewModel.setFilm(2,query);
        }

        return view;
    }

    void refresh(){
        mainViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);


        mainViewModel.getMovies().observe(this,getMovie);
        showLoading(true);
        String query = getArguments().getString(EXTRA_QUERY);
        //String query="";
        if (query==""){
            mainViewModel.setFilm(1,query);
        }
        else {
            mainViewModel.setFilm(2,query);
        }
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
            pb.setVisibility(View.VISIBLE);
        } else {
            pb.setVisibility(View.GONE);
        }
    }

    public void updateList(String query) {
        Bundle mBundle = new Bundle();
        mBundle.putString(this.EXTRA_QUERY, query);
        this.setArguments(mBundle);
        Toast.makeText(getContext(),query,Toast.LENGTH_SHORT).show();
        refresh();
    }
}
