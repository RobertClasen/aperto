package com.aperto.fatpenguin.aperto;

import io.realm.RealmObject;

/**
 * @Author Robert Clasen & Marco Illemann
 */
public class Spot extends RealmObject {
    private String title;
    private String description;
    private int category;
    private float rating;
    private long photoId;
    private double latitude;
    private double longitude;
    private boolean favorite;

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
        return latitude;
    }

    public void setLatitude(double lt) {
        this.latitude = lt;
    }

    public void setLongitude(double ln) {
        this.longitude = ln;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setFavorite(boolean b){this.favorite = b;}

    public boolean getFavorite(){return favorite;}

    public void setPhotoId(long photoId) {
        this.photoId = photoId;
    }

    public long getPhotoId() {
        return photoId;
    }
}
