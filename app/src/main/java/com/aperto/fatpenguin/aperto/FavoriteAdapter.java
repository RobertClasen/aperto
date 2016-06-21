package com.aperto.fatpenguin.aperto;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

//test
public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {
    private List<Spot> spotsData;
    private ColorFilter redColorFilter;


    public static class FavoriteViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public ImageButton imgButton;

        public FavoriteViewHolder(View view){
            super(view);
            textView = (TextView) view.findViewById(R.id.favorite_title);
            imgButton = (ImageButton) view.findViewById(R.id.favorite_button_recycle);
        }

    }

    public FavoriteAdapter(List<Spot> favoriteSpots) {spotsData = favoriteSpots;}

    @Override
    public FavoriteAdapter.FavoriteViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_list_row, parent, false);
        FavoriteViewHolder favViewHolder = new FavoriteViewHolder(v);
        return favViewHolder;
    }


    @Override
    public void onBindViewHolder(FavoriteViewHolder holder, int position) {
        final Spot s = spotsData.get(position);
        holder.textView.setText(s.getTitle());
        redColorFilter = new LightingColorFilter(Color.WHITE, Color.RED);
        holder.imgButton.setColorFilter(redColorFilter);
    }

    @Override
    public int getItemCount() {return spotsData.size();}

}
