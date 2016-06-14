package com.aperto.fatpenguin.aperto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    // These a both viewgroups containing an ImageView with id "badge" and two TextViews with id
    // "title" and "snippet".
//    private final View window;
    private final View contents;

    public CustomInfoWindowAdapter(Context context) {
        LayoutInflater lf = LayoutInflater.from(context);
//        window = lf.inflate(R.layout.info_window_view,null);
        contents = lf.inflate(R.layout.info_contents_view,null);

    }

    @Override
    public View getInfoWindow(Marker marker) {
       return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return contents;
    }
}
