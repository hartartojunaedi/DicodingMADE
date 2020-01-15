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

public class MainViewModel extends ViewModel {
 //   private static final String API_KEY = "ff3a5fa3aed8a9e1ed4f4b08b0f522c7";
    private static final String API_KEY = BuildConfig.myDBAPIKey;
    private MutableLiveData<ArrayList<Movie>> listMovie = new MutableLiveData<>();

    void setFilm(final int type,final String query) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> listitemMovie = new ArrayList<>();
        String url;
        if (type==1)
            url= "https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY+"&language=en-US";
        else
            //url = "https://api.themoviedb.org/3/discover/tv?api_key=" + API_KEY+"&language=en-US";
            url="https://api.themoviedb.org/3/search/movie?api_key="+API_KEY+"&language=en-US&query="+query;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject w = list.getJSONObject(i);
                        Movie movie = new Movie(w);
                        //if (type==1)
                            movie.setName(w.getString("title"));
                        //else
                        //    movie.setName(w.getString("name"));

                        movie.setDescription(w.getString("overview"));
                        movie.setPhoto(w.getString("poster_path"));
                        listitemMovie.add(movie);
                    }
                    listMovie.postValue(listitemMovie);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }


        });
    }
    public LiveData<ArrayList<Movie>> getMovies() {
        return listMovie;
    }
}
