package com.example.mysubmitdicoding2.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class FavoriteDB implements Parcelable {
    private int id;
    private String title,description,photo,tipe;
    private String date;

    protected FavoriteDB(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        photo = in.readString();
        tipe = in.readString();
        date = in.readString();
    }

    public static final Creator<FavoriteDB> CREATOR = new Creator<FavoriteDB>() {
        @Override
        public FavoriteDB createFromParcel(Parcel in) {
            return new FavoriteDB(in);
        }

        @Override
        public FavoriteDB[] newArray(int size) {
            return new FavoriteDB[size];
        }
    };

    public FavoriteDB() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public FavoriteDB(int id, String title, String description, String photo, String tipe, String date) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.photo = photo;
        this.tipe = tipe;
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(photo);
        parcel.writeString(tipe);
        parcel.writeString(date);
    }
}
