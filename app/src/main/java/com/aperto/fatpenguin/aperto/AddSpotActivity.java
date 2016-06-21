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
import android.os.Environment;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class AddSpotActivity extends Activity {

    private boolean categoryPressed = false;
    private int categoryIndex;
    private Drawable currentCategory;
    private ColorFilter black = new LightingColorFilter(Color.BLACK, Color.BLACK);
    private static final String SPOT_DATA = "com.aperto.fatpenguin.aperto.spot_data";
    private static final String PHOTO_PRIMARY_KEY = "com.aperto.fatpenguin.aperto.photo_primary_key";
    private static final int REQUEST_TAKE_PHOTO = 1;

    private EditText editTitle;
    private EditText editDescription;
    private ImageView imageView;
    private ImageButton imgButton;
    private Button submitBtn;
    private File photoFile;
    private RatingBar rating;
    private CollapsingToolbarLayout topToolLayout;

    private String[] spotFields;
    private Bitmap photoBitmap;
    private long nextId;

    private Realm realm;
    private RealmConfiguration realmConfig;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addspot);

        Log.e("AddSpotActivity", "Setting up realm");
        realmConfig = new RealmConfiguration
                .Builder(AddSpotActivity.this)
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(realmConfig);

        editTitle = (EditText) findViewById(R.id.title_edit_txt);
        editDescription = (EditText) findViewById(R.id.description_edit_txt);
        rating = (RatingBar) findViewById(R.id.detail_view_rating_bar);
        imageView = (ImageView) findViewById(R.id.add_spot_thumbnail);
        imgButton = (ImageButton) findViewById(R.id.add_image);
        submitBtn = (Button) findViewById(R.id.submit_btn);
        topToolLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_activity);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.inner_category_wheel);
        final TypedArray images = this.getResources().obtainTypedArray(R.array.categories_image_links);

        for (int i = 0; i < images.length(); i++){
            setCategoryOnClickAction(images, linearLayout, i);
        }

        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create Intent to take a picture and return control to the calling application
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    photoFile = null;
                    try {
                        photoFile = createPhotoFile();
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                        startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                    }
                }
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gotValidInput()) {
                    Log.e("AddSpotActivity", "Input good");
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(SPOT_DATA, spotFields);
                    resultIntent.putExtra(PHOTO_PRIMARY_KEY, nextId);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                } else {
                    Log.e("AddSpotActivity", "Input bad");
                    setResult(RESULT_CANCELED);
                    Snackbar snackbar = Snackbar.make(v, "Please complete the form", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });

        }

    private boolean gotValidInput() {
        Log.e("AddSpotActivity", "Checking for valid input");
        if (categoryPressed
                && editTitle != null
                && photoBitmap != null
                && rating.getRating() != 0) {
            spotFields = new String[4];
            spotFields[0] = Integer.toString(categoryIndex);
            spotFields[1] = editTitle.getText().toString();
            spotFields[3] = Float.toString(rating.getRating());
        } else {
            return false;
        }

        if (editDescription.getText() != null) {
            spotFields[2] = editDescription.getText().toString();
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
        Log.e("AddSpotActiivty", "Photo retrieved from camera");
            try {
                final FileInputStream inputStream = new FileInputStream(photoFile);

                final BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 5;
                options.inPreferQualityOverSpeed = true;

                photoBitmap = BitmapFactory.decodeStream(inputStream, null, options);

                // Generate a BitmapDrawable just for the ImageView in this activity.
                BitmapDrawable photoDrawable = new BitmapDrawable(getResources(), photoBitmap);

                topToolLayout.removeView(imgButton);
                imageView.setImageDrawable(photoDrawable);

                Log.e("AddSpotActiivty", "Time to genereate an id for the photo");
                // Generate the primary key for the photo.
                if (realm.where(Photo.class).max("id") == null) {
                    nextId = 0;
                    Log.e("AddSpotActiivty", "First photo to go in realm, id is set to " + nextId);
                } else {
                    Log.e("AddSpotActiivty", "Not first photo to go in realm...");
                    nextId = (long) realm.where(Photo.class).max("id").longValue() + 1;
                    Log.e("AddSpotActiivty", "...id is set to " + nextId);
                }

                Photo photo= new Photo();
                photo.setId(nextId);
                photo.setPhoto(photoBitmap);

                Log.e("AddSpotActivity", "Getting ready to commit photo to realm");
                realm.beginTransaction();
                realm.copyToRealm(photo);
                Log.e("AddSpotActivity", "Photo got id: " + nextId);
                realm.commitTransaction();

            } catch (Exception e) {
                e.printStackTrace();
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

    private File createPhotoFile() throws IOException {
        // Create a collision-resistant file name for the photo file.
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp;

        // Define the directory for the photo file.
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        // Create the photo file.
        return photoFile = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("AddSpotActivity", "Closing realm");
        realm.close();
    }

}
