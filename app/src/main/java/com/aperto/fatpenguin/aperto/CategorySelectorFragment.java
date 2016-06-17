package com.aperto.fatpenguin.aperto;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class CategorySelectorFragment extends Fragment {

    ColorFilter black;
    private boolean categoryPressed = false;
//    private Drawable currentCategory;
    private int currentCategory;
    private MainActivity mainActivity;

    public CategorySelectorFragment() {
//        this.mainActivity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.category_selector_fragment, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        black = new LightingColorFilter(Color.BLACK, Color.BLACK);

        LinearLayout linearLayout = (LinearLayout) getView().findViewById(R.id.inner_category_wheel);
        final TypedArray images = this.getResources().obtainTypedArray(R.array.categories_filters);

        for (int i = 0; i < images.length(); i++) {
            setCategoryOnClickAction(images, linearLayout, i);
        }

    }

    public void setCategoryOnClickAction(TypedArray images, LinearLayout ll, int index) {
        final int imgID = images.getResourceId(index, -1);
        final ImageButton imgBtn = (ImageButton) getActivity().getLayoutInflater().inflate(R.layout.my_image_button,ll,false);
        imgBtn.setImageResource(imgID);
        imgBtn.setColorFilter(black);
        final int j = index;
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgBtn.getColorFilter().equals(black)) {
                    int c = v.getResources().obtainTypedArray(R.array.categories_colors).getColor(j, -1);
                    imgBtn.setColorFilter(c, PorterDuff.Mode.SRC_ATOP);
                } else {
                    imgBtn.setColorFilter(black);
                }

                ((MainActivity)getActivity()).placeMarkers(j);

            }
        });
        ll.addView(imgBtn);

//    public void setCategoryOnClickAction(TypedArray images, LinearLayout ll, int index) {
//        final int imgID = images.getResourceId(index, -1);
//        final ImageView img = new ImageView(getContext());
//        img.setImageResource(imgID);
//        img.setColorFilter(black);
//        final int j = index;
//        img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (categoryPressed){
//                    currentCategory.setColorFilter(black);
//                }
//                currentCategory = img.getDrawable();
//
//                int c = v.getResources().obtainTypedArray(R.array.categories_colors).getColor(j, -1);
//                currentCategory.setColorFilter(c, PorterDuff.Mode.SRC_ATOP);
//                categoryPressed = true;
//
//                ((MainActivity)getActivity()).placeMarkers(j);
//
//            }
//        });
//        ll.addView(img);

    }



}
