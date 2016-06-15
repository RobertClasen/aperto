
package com.aperto.fatpenguin.aperto;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @Author Robert Clasen
 * @Author Marco Illemann
 */
public class DetailActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setSupportActionBar((Toolbar) findViewById(R.id.detail_toolbar));

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        collapsingToolbar.setTitle("Urban training");

        Resources resources = getResources();
        ImageView placePicutre = (ImageView) findViewById(R.id.image);
        placePicutre.setImageDrawable(resources.getDrawable(R.drawable.urban));

        TextView description = (TextView) findViewById(R.id.place_detail);
        description.setText(R.string.detail_desc_short);


        GalleryView galleryView = (GalleryView) findViewById(R.id.gallery_view);
//        galleryView.setAdapter();

        getLayoutInflater().inflate(R.layout.recycler_view, null);
        GalleryView gallery = (GalleryView) getLayoutInflater().inflate(R.layout.recycler_view, null);
    }
}
