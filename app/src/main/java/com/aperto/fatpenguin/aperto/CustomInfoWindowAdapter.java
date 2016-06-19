package com.aperto.fatpenguin.aperto;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

//    private final View window;
    private final View contents;
//    private RelativeLayout root;
    private TextView title;
    private RatingBar rating;
    private RealmConfiguration realmConfig;
    private Realm realm;

    public CustomInfoWindowAdapter(Context context) {
        Resources resources = context.getResources();
        LayoutInflater lf = LayoutInflater.from(context);
//        window = lf.inflate(R.layout.info_window_view,null);
        contents = lf.inflate(R.layout.info_contents_view,null);
//        root = (RelativeLayout) contents.findViewById(R.id.info_window_root);
        title = (TextView) contents.findViewById(R.id.info_window_title);
        rating = (RatingBar) contents.findViewById(R.id.info_window_rating_bar);

        realmConfig = new RealmConfiguration
                .Builder(context)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfig);
        realm = Realm.getDefaultInstance();
    }


    /**
     * Provides a custom info window for a marker.
     * If this method returns a view, it is used for the entire info window.
     * If this method returns null , the default info window frame will be used,
     * with contents provided by getInfoContents(Marker).
     */
    @Override
    public View getInfoWindow(Marker marker) {
       return null;
    }


    /**
     * Provides custom contents for the default info window frame of a marker.
     * This method is only called if getInfoWindow(Marker) first returns null.
     * If this method returns a view, it will be placed inside the default info window frame.
     */
    @Override
    public View getInfoContents(Marker marker) {
        LatLng position = marker.getPosition();
        RealmResults<Spot> spots = realm.where(Spot.class)
                .equalTo("lt", position.latitude)
                .equalTo("ln", position.longitude)
                .findAll();

        Spot spot = spots.get(0);

        title.setText(spot.getTitle());
        rating.setRating(spot.getRating());


        return contents;

    }
}
