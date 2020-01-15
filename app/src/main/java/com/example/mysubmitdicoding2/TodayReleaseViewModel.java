package com.example.mysubmitdicoding2;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TodayReleaseViewModel {
    private static final String API_KEY = BuildConfig.myDBAPIKey;
    private MutableLiveData<ArrayList<Movie>> listMovie = new MutableLiveData<>();
   // private ArrayList<Movie> listMovie = new ArrayList<Movie>();

    public LiveData<ArrayList<Movie>> getMovies() {
        return listMovie;
    }

    public ArrayList<Movie> setFilm(String tgltoday) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> listitemMovie = new ArrayList<>();
        String url;
        url="https://api.themoviedb.org/3/discover/movie?api_key="+API_KEY+"&primary_release_date.gte="+tgltoday+"&primary_release_date.lte="+tgltoday;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    System.out.println(result);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject w = list.getJSONObject(i);
                        Movie movie = new Movie(w);
                        movie.setName(w.getString("name"));

                        movie.setDescription(w.getString("overview"));
                        movie.setPhoto(w.getString("poster_path"));
                        listitemMovie.add(movie);
                    }
                   // listMovie.postValue(listitemMovie);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }


        });

        return listitemMovie;
    }

}
