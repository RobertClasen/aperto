package com.aperto.fatpenguin.aperto;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

//test
public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {
    private List<Spot> spotsData;
    public static class FavoriteViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public FavoriteViewHolder(View view){
            super(view);
            textView = (TextView) view.findViewById(R.id.favorite_title);
        }
    }

    public FavoriteAdapter(List<Spot> favoriteSpots) {spotsData = favoriteSpots;
    }

    @Override
    public FavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_list_row, parent, false);
        //ViewHolder viewHolder = new ViewHolder(v);
        return new FavoriteViewHolder(v);
    }


    @Override
    public void onBindViewHolder(FavoriteViewHolder holder, int position) {
        holder.textView.setText(spotsData.get(position).getTitle());
    }

    @Override
    public int getItemCount() {return spotsData.size();}
}
