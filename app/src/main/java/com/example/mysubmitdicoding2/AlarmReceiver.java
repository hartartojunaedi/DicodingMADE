package com.example.mysubmitdicoding2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import androidx.lifecycle.MutableLiveData;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.*;

public class AlarmReceiver extends BroadcastReceiver {
    public static final String TYPE_DAILYREMINDER = "Daily Reminder";
    public static final String TYPE_RELEASEREMINDER = "RELEASE Reminder";
    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_TYPE = "type";
    String DATE_FORMAT_MOVIE = "yyyy-MM-dd";
    //private ArrayList<Movie> listMovie = new ArrayList<Movie>();
    private TodayReleaseViewModel todayReleaseViewModel=new TodayReleaseViewModel();
    private final int ID_RELEASEREMINDER = 300;
    private final int ID_DAILYREMINDER   = 301;

    private ArrayList<NotificationItem> data= new ArrayList<NotificationItem>();
    private Context context;
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //throw new UnsupportedOperationException("Not yet implemented");
        NotificationBuilder notificationBuilder=new NotificationBuilder();
        this.context = context;
        String type = intent.getStringExtra(EXTRA_TYPE);
        String message = intent.getStringExtra(EXTRA_MESSAGE);
        String title = type.equalsIgnoreCase(TYPE_DAILYREMINDER) ? TYPE_DAILYREMINDER : TYPE_RELEASEREMINDER;
        int notifId = type.equalsIgnoreCase(TYPE_DAILYREMINDER) ? ID_DAILYREMINDER : ID_RELEASEREMINDER;
        if (type.equalsIgnoreCase(TYPE_DAILYREMINDER)){
           notificationBuilder.displaynotif(context,title, message, notifId);
        }
        else
        {
            getTodayRelease();
            //untuk release
        }
    }

    private void getTodayRelease() {
        System.out.println("release today");
        final String API_KEY = BuildConfig.myDBAPIKey;
        String tgltoday = getCurrentTime(DATE_FORMAT_MOVIE);
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
                        movie.setName(w.getString("title"));

                        movie.setDescription(w.getString("overview"));
                        movie.setPhoto(w.getString("poster_path"));
                        listitemMovie.add(movie);
                    }
                    NotificationBuilder notificationBuilder=new NotificationBuilder();
                    int notifId=0;
                    data.clear();
                    int size = listitemMovie.size();
                    Log.i("Cek Error",size+"");
                    for (int i = 0; i < size; i++) {
                        Log.i("data film", listitemMovie.get(i).getName());
                        data.add(new NotificationItem(notifId,listitemMovie.get(i).getName(),listitemMovie.get(i).getName(),listitemMovie.get(i).getName()));
                        notificationBuilder.displaystacknotif(context,notifId,data);
                        notifId++;
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
    }


    public void cancelAlarm(Context context, String type) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        int requestCode = type.equalsIgnoreCase(TYPE_RELEASEREMINDER) ? ID_RELEASEREMINDER : ID_DAILYREMINDER;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        pendingIntent.cancel();
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
        Toast.makeText(context, "Repeating alarm dibatalkan", Toast.LENGTH_SHORT).show();
    }

    public void setRepeatingAlarm(Context context, String type, String time, String message) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_TYPE, type);
        String[] timeArray = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_DAILYREMINDER, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
        Toast.makeText(context, "Repeating alarm set up", Toast.LENGTH_SHORT).show();
    }
     String getCurrentTime(String format){
        return new SimpleDateFormat(format,Locale.getDefault()).format(new Date());
    }
}
