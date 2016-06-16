package com.aperto.fatpenguin.aperto;

import android.graphics.Bitmap;
import android.location.Location;

import io.realm.RealmObject;

public class Spot extends RealmObject {
    private String title;
    private String description;
    private int category;
    private float rating;
//    private Bitmap photo;
    private double lt;
    private double ln;

    public Spot(MainActivity mainActivity, String title, String description, int category,
                float rating) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.rating = rating;

        Location currentLocation = mainActivity.getCurrentLocation();

        this.lt = currentLocation.getLatitude();
        this.ln = currentLocation.getLongitude();
    }

    public Spot() {

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

    public double getLongitude() {
        return ln;
    }

//    public Bitmap getPhoto() {
//        return photo;
//    }
//
//    public void setPhoto(Bitmap photo) {
//        this.photo = photo;
//    }
}
