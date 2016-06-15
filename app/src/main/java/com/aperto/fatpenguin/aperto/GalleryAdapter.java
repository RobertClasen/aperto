package com.aperto.fatpenguin.aperto;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryViewHolder> {

    // Set numbers of Tiles in RecyclerView.
    private static final int LENGTH = 4;
    Resources resources;

    public GalleryAdapter(Context context) {
        resources = context.getResources();
    }

    @Override
    public GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GalleryViewHolder(LayoutInflater.from(parent.getContext()), parent);
    }

    @Override
    public void onBindViewHolder(GalleryViewHolder holder, int position) {
        holder.picture.setImageDrawable(resources.getDrawable(R.drawable.urban));
    }

    @Override
    public int getItemCount() {
        return LENGTH;
    }
}
