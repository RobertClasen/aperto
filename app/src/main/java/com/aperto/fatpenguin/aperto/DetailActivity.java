
package com.aperto.fatpenguin.aperto;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * @Author Robert Clasen
 * @Author Marco Illemann
 */

public class DetailActivity extends AppCompatActivity {
    private ImageView image;
    private TextView title;
    private RatingBar ratingBar;
    private RealmConfiguration realmConfig;
    private Realm realm;
    private Spot spot;
    double[] position;
    private ColorFilter redColorFilter;
    private ColorFilter blackColorFilter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        redColorFilter = new LightingColorFilter(Color.WHITE, getResources().getColor(R.color.favoriteColor));
        blackColorFilter = new LightingColorFilter(Color.WHITE, Color.BLACK);

        realmConfig = new RealmConfiguration
                .Builder(this)
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(realmConfig);

        position = getIntent().getDoubleArrayExtra("marker_data");

        RealmResults<Spot> spots = realm.where(Spot.class)
                .equalTo("latitude", position[0])
                .equalTo("longitude", position[1])
                .findAll();

        spot = spots.get(0);

        setSupportActionBar((Toolbar) findViewById(R.id.detail_toolbar));

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        collapsingToolbar.setTitle(spot.getTitle());

        image = (ImageView) findViewById(R.id.info_window_image);

        RealmResults<Photo> photos = realm.where(Photo.class)
                .equalTo("id", spot.getPhotoId()).findAll();

        Photo photo = photos.get(0);

        image.setImageDrawable(photo.getPhoto());

        ratingBar = (RatingBar) findViewById(R.id.detail_view_rating_bar);
        ratingBar.setRating(spot.getRating());

        TextView description = (TextView) findViewById(R.id.place_detail);
        description.setText(spot.getDescription());

        final ImageButton favoriteBtn = (ImageButton) findViewById(R.id.favorite_button);
        if (spot.getFavorite()){
            favoriteBtn.setImageResource(R.drawable.ic_favorite_black_24dp);
            favoriteBtn.setColorFilter(redColorFilter);
        }else{
            favoriteBtn.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            favoriteBtn.setColorFilter(blackColorFilter);

        }
        favoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean isFavorite = spot.getFavorite();
                if (isFavorite){
                    favoriteBtn.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    favoriteBtn.setColorFilter(blackColorFilter);
                }else{
                    favoriteBtn.setImageResource(R.drawable.ic_favorite_black_24dp);
                    favoriteBtn.setColorFilter(redColorFilter);
                }
                realm.beginTransaction();
                spot.setFavorite(!isFavorite);
                realm.commitTransaction();
            }
        });

        final ImageButton directionsBtn = (ImageButton) findViewById(R.id.directions_button);
        directionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("DetailActivity", "directionsBtn clicked");
                String latitude = Double.toString(spot.getLatitude());
                String longitude = Double.toString(spot.getLongitude());

                Uri geoUri = Uri.parse("google.navigation:q=" + latitude + "," + longitude + "&mode=w");

                Intent directionsIntent = new Intent(Intent.ACTION_VIEW, geoUri);
                startActivity(directionsIntent);
            }
        });

    }
}
