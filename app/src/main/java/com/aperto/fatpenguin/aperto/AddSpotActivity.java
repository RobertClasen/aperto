package com.aperto.fatpenguin.aperto;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.graphics.Bitmap;


/**
 * Created by rasmusliebst, kristianwolthers and pellerubin on 14/06/16. its a me mario
 */


public class AddSpotActivity extends Activity {

    private boolean categoryPressed = false;
    private int categoryIndex;
    private Drawable currentCategory;
    ColorFilter black = new LightingColorFilter(Color.BLACK, Color.BLACK);
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int TAKE_PICTURE = 1;
    private static final String TAG = "addSpot";
    private static final String SPOT_RESULT_CODE = "spot_data";
    private static final String SPOT_THUMBNAIL_CODE = "spot_thumbnail";
    private static Spot spot;

    private EditText editTitle;
    private EditText editDescription;
    private ImageButton imgButton;
    private Uri imageFileUri;
    private RatingBar rating;
    private CollapsingToolbarLayout topToolLayout;

    private String[] spotFields;
    private byte[] thumbnail;



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addspot);

        editTitle = (EditText) findViewById(R.id.title_edit_txt);
        editDescription = (EditText) findViewById(R.id.description_edit_txt);
        rating = (RatingBar) findViewById(R.id.detail_view_rating_bar);
        imgButton = (ImageButton) findViewById(R.id.add_image);
        topToolLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_activity);


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

        Button submitBtn = (Button) findViewById(R.id.submit_btn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gotValidInput()) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(SPOT_RESULT_CODE, spotFields);
                    resultIntent.putExtra(SPOT_THUMBNAIL_CODE, thumbnail);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                } else {
                    setResult(RESULT_CANCELED);
                    Snackbar snackbar = Snackbar.make(v, "not able to submit", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });

    }

    private boolean gotValidInput() {
        //TODO - add picture check as well
        if (categoryPressed && editTitle != null) {
            spotFields = new String[4];
            spotFields[0] = Integer.toString(categoryIndex);
            spotFields[1] = editTitle.getText().toString();
        } else {
            return false;
        }

        if (editDescription.getText() != null) {
            spotFields[2] = editDescription.getText().toString();
        }
        if (rating.getRating() != 0) {
            spotFields[3] = Float.toString(rating.getRating());
        }

        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {super.onStop();}


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE){
            switch (resultCode){
                case RESULT_OK:
                    try{
                        Bundle extras = data.getExtras();
                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), imageBitmap);
                        imgButton.setVisibility(View.GONE);
                        topToolLayout.setBackground(bitmapDrawable);
                    }catch (Exception e){
                        System.out.print("something went wrooong");
                    }

                    break;
                case RESULT_CANCELED:
                    Log.i(TAG, "Image camera cancelled");
                    break;
                default:
                    Log.i(TAG, "Image camera failed!");
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
                categoryIndex = j;
                int c = v.getResources().obtainTypedArray(R.array.categories_colors).getColor(categoryIndex, -1);
                currentCategory.setColorFilter(c, PorterDuff.Mode.SRC_ATOP);
                categoryPressed = true;

            }
        });
        ll.addView(img);
    }
}
