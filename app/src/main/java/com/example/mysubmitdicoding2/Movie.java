package com.example.mysubmitdicoding2;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class Movie implements Parcelable {
    private String photo;
    private String namafilm;
    private String description;
    public Movie() {
        try {




        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Movie(String namafilm, String description,String photo) {
        this.photo = photo;
        this.namafilm = namafilm;
        this.description = description;
    }

    public Movie(JSONObject object){
        try {
            String title = object.getString("title");
            // String original_language = object.getString("original_language");
            String overview = object.getString("overview");
            String poster_path = object.getString("poster_path");

            this.namafilm= title;
            //this.original_language = original_language;
            this.description = overview;
            this.photo=poster_path;



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        protected Movie(Parcel in) {
        this.photo = in.readString();
        this.namafilm = in.readString();
        this.description = in.readString();
    }



    public String getName() {
        return namafilm;
    }

    public void setName(String name) {
        this.namafilm = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString((this.photo));
        parcel.writeString(this.namafilm);
        parcel.writeString(this.description);
    }
}
