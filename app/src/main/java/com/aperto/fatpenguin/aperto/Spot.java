package com.aperto.fatpenguin.aperto;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import io.realm.RealmObject;

public class Spot extends RealmObject {
    private String title;
    private String description;
    private int category;
    private float rating;
    private byte[] thumbnail;
    private double lt;
    private double ln;
    private boolean favorite;

    //test
    //test
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

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public double getLatitude() {
        return lt;
    }

    public void setLatitude(double lt) {
        this.lt = lt;
    }

    public void setLongitude(double ln) {
        this.ln = ln;
    }

    public double getLongitude() {
        return ln;
    }

    public byte[] getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setFavorite(boolean b) {
        this.favorite = b;
    }

    public boolean getFavorite() {
        return favorite;
    }
}