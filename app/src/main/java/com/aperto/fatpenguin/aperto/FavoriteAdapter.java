package com.aperto.fatpenguin.aperto;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

//test
public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private ArrayList<Spot> spotsData;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public TextView dummy;
        public ViewHolder(View view){
            super(view);
            textView = (TextView) view.findViewById(R.id.favorite_title);
            dummy = (TextView) view.findViewById(R.id.dummy);
            //imageView = (ImageView) view.findViewById(R.id.favorite_image);
        }

    }

    public FavoriteAdapter(ArrayList<Spot> favoriteSpots) {
        spotsData = favoriteSpots;
    }

    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_list_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(spotsData.get(position).getTitle());
        holder.dummy.setText(spotsData.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return spotsData.size();
    }
}
