package com.aperto.fatpenguin.aperto;

import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;


/**
 * Created by rasmusliebst on 14/06/16.
 */


public class AddSpotActivity extends Activity {

    private boolean categoryPressed = false;
    private Drawable currentCategory;
    ColorFilter black = new LightingColorFilter(Color.BLACK, Color.BLACK);


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addspot);


        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.inner_category_wheel);
        final TypedArray images = this.getResources().obtainTypedArray(R.array.categories_image_links);

        for (int i = 0; i < images.length(); i++){
            int imgID = images.getResourceId(i, -1);
            final ImageView img = new ImageView(this);
            img.setImageResource(imgID);
            img.setColorFilter(black);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (categoryPressed){
                        currentCategory.setColorFilter(black);
                    }
                    currentCategory = img.getDrawable();
                    int hej = 0;
                    for (int i = 0; i< images.length(); i++){
                        if (currentCategory == images.getDrawable(i)){
                            hej = i;
                        }
                    }
                    int c = v.getResources().obtainTypedArray(R.array.categories_colors).getColor(hej, -1);
                    currentCategory.setColorFilter(c, PorterDuff.Mode.SRC_ATOP);
                    categoryPressed = true;
                }
            });

            linearLayout.addView(img);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

    }
}
