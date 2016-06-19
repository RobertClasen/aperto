
package com.aperto.fatpenguin.aperto;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.ByteArrayInputStream;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        realmConfig = new RealmConfiguration
                .Builder(this)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfig);
        realm = Realm.getDefaultInstance();

        position = getIntent().getDoubleArrayExtra("marker_data");

        RealmResults<Spot> spots = realm.where(Spot.class)
                .equalTo("lt", position[0])
                .equalTo("ln", position[1])
                .findAll();

        spot = spots.get(0);

        setSupportActionBar((Toolbar) findViewById(R.id.detail_toolbar));

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        collapsingToolbar.setTitle(spot.getTitle());

        image = (ImageView) findViewById(R.id.info_window_image);

        ByteArrayInputStream inputStream = new ByteArrayInputStream(spot.getThumbnail());
//        image.setBackground(Drawable.createFromStream(inputStream, "image"));
        image.setImageDrawable(Drawable.createFromStream(inputStream, "image"));

        ratingBar = (RatingBar) findViewById(R.id.detail_view_rating_bar);
        ratingBar.setRating(spot.getRating());

        TextView description = (TextView) findViewById(R.id.place_detail);
        description.setText(spot.getDescription());

        final ImageButton favoriteBtn = (ImageButton) findViewById(R.id.favorite_button);
        favoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm.beginTransaction();
                spot.setFavorite(true);
                realm.commitTransaction();
                favoriteBtn.setBackgroundResource(R.drawable.football);
            }
        });

//        RecyclerView galleryView = (RecyclerView) findViewById(R.id.gallery_view);
//        galleryView.setAdapter(new GalleryAdapter(this));
//
//        galleryView.setLayoutManager(new GridLayoutManager(this, 2));


    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//
//        RealmResults<Spot> spotsToDelete = realm.where(Spot.class)
//                .equalTo("lt", position[0])
//                .equalTo("ln", position[1])
//                .findAll();
//
////        Spot spotToDelete = spotsToDelete.get(0);
//
//        realm.beginTransaction();
//        spot.setRating(ratingBar.getRating());
//        spotsToDelete.deleteAllFromRealm();
//        realm.copyToRealm(spot);
//        realm.commitTransaction();
//    }
}
