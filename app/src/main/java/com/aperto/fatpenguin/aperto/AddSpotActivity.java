package com.aperto.fatpenguin.aperto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;


/**
 * Created by rasmusliebst, kristianwolthers and pellerubin on 14/06/16. its a me mario
 */


public class AddSpotActivity extends Activity {

    private boolean categoryPressed = false;
    private Drawable currentCategory;
    ColorFilter black = new LightingColorFilter(Color.BLACK, Color.BLACK);
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private Uri fileURI;
    private static Spot spot;
    private static final String TAG = "addSpot";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addspot);


        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.inner_category_wheel);
        final TypedArray images = this.getResources().obtainTypedArray(R.array.categories_image_links);

        for (int i = 0; i < images.length(); i++){
            setCategoryOnClickAction(images, linearLayout, i);
        }

        ImageButton imageButton = (ImageButton) findViewById(R.id.add_image);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 // create Intent to take a picture and return control to the calling application
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                 // start the image capture Intent
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {super.onStop();}


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE){
            switch (resultCode){
                case RESULT_OK:
                    fileURI = data.getData();
                    break;
                case RESULT_CANCELED:
                    Log.i(TAG, "image camera canceled");
                    break;
                default:
                    Log.i(TAG, "image camera failed!");
            }
        }
    }

    public void setCategoryOnClickAction(TypedArray images, LinearLayout ll, int index){
        final int imgID = images.getResourceId(index, -1);
        final ImageView img = new ImageView(this);
        img.setImageResource(imgID);
        img.setColorFilter(black);
        final int j = index;
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (categoryPressed){
                    currentCategory.setColorFilter(black);
                }
                currentCategory = img.getDrawable();

                int c = v.getResources().obtainTypedArray(R.array.categories_colors).getColor(j, -1);
                currentCategory.setColorFilter(c, PorterDuff.Mode.SRC_ATOP);
                categoryPressed = true;

            }
        });
        ll.addView(img);
    }
}
