package com.aperto.fatpenguin.aperto;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @Author Robert Clasen & Marco Illemann
 */
public class Photo extends RealmObject {
    @PrimaryKey
    private long id;
    private byte[] photo;

    public Drawable getPhoto() {
        Log.e("Photo", "Trying to retrieve photo drawable");
        ByteArrayInputStream inputStream = new ByteArrayInputStream(photo);
        return Drawable.createFromStream(inputStream, "image");
    }

    public void setPhoto(Bitmap photo) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        this.photo = stream.toByteArray();
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

}
