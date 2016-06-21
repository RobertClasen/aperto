package com.aperto.fatpenguin.aperto;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

/**
 * @Author Robert Clasen & Marco Illemann
 */
public class CategorySelectorFragment extends Fragment {
    private ColorFilter white;
    private TypedArray categoryIcons;
    private boolean[] filter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        white = new LightingColorFilter(Color.WHITE, Color.WHITE);
        categoryIcons = getResources().obtainTypedArray(R.array.categories_filters);
        filter = new boolean[categoryIcons.length()];
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.category_selector_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
            LinearLayout linearLayout = (LinearLayout) getView().findViewById(R.id.inner_category_wheel);
            for (int i = 0; i < categoryIcons.length(); i++) {
                setCategoryOnClickAction(categoryIcons, linearLayout, i);
            }
        }
    }

    public void setCategoryOnClickAction(TypedArray images, LinearLayout ll, int index) {
        final int imgID = images.getResourceId(index, -1);
        final ImageButton imgBtn = (ImageButton) getActivity().getLayoutInflater().inflate(R.layout.my_image_button,ll,false);
        imgBtn.setImageResource(imgID);
        imgBtn.setId(index);
        imgBtn.setColorFilter(white);
        final int j = index;

        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgBtn.getColorFilter().equals(white)) {
                    int c = v.getResources().obtainTypedArray(R.array.categories_colors).getColor(j, -1);
                    imgBtn.setColorFilter(c, PorterDuff.Mode.SRC_ATOP);
                    filter[v.getId()] = true;
                } else {
                    imgBtn.setColorFilter(white);
                    filter[v.getId()] = false;
                }

                ((MainActivity)getActivity()).placeMarkers(filter);

            }
        });

        ll.addView(imgBtn);
    }

}
