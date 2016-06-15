package com.aperto.fatpenguin.aperto;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

public class GalleryViewHolder extends RecyclerView.ViewHolder {
    public ImageView picture;

    public GalleryViewHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.item_tile, parent, false));
        picture = (ImageView) itemView.findViewById(R.id.tile_picture);
    }
}