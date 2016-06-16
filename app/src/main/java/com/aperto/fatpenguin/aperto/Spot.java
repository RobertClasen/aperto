package com.aperto.fatpenguin.aperto;

import android.location.Location;

import io.realm.RealmObject;

public class Spot extends RealmObject {
    private String title;
    private String type;
    private float lt;
    private float ln;

    public Spot(String title, String type, float lt, float ln) {
        this.title = title;
        this.type = type;
        this.lt = lt;
        this.ln = ln;
    }

    public Spot() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
