package com.aperto.fatpenguin.aperto;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.zip.Inflater;

/**
 * @Author Robert Clasen
 * @Author Marco Illemann
 */
public class GalleryView extends RecyclerView {
    public GalleryView(Context context) {
        super(context);

        TileContentFragment.ContentAdapter adapter =
                new TileContentFragment.ContentAdapter(getContext());
        setAdapter(adapter);
        setHasFixedSize(true);
        // Set padding for Tiles
        int tilePadding = getResources().getDimensionPixelSize(R.dimen.tile_padding);
        setPadding(tilePadding, tilePadding, tilePadding, tilePadding);
        setLayoutManager(new GridLayoutManager(context, 2));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_tile, parent, false));
            picture = (ImageView) itemView.findViewById(R.id.tile_picture);
        }
    }

}
