package com.aperto.fatpenguin.aperto;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.List;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * @Author Pelle Rubin & Kristian Wolthers
 */
public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {
    private List<Spot> spotsData;
    private Realm realm;

    public static class FavoriteViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public ImageButton imgButton;
        public FavoriteViewHolder(View view){
            super(view);
            textView = (TextView) view.findViewById(R.id.favorite_title);
            imgButton = (ImageButton) view.findViewById(R.id.favorite_button_recycle);
        }
    }

    public FavoriteAdapter(List<Spot> favoriteSpots, Context context) {
        spotsData = favoriteSpots;
        RealmConfiguration realmConfig = new RealmConfiguration
                .Builder(context)
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(realmConfig);
    }

    @Override
    public FavoriteAdapter.FavoriteViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_list_row, parent, false);
        return new FavoriteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FavoriteViewHolder holder, int position) {
        final Spot s = spotsData.get(position);
        final ImageButton imgBut = holder.imgButton;
        holder.textView.setText(s.getTitle());

        imgBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean b = s.getFavorite();
                if (b) {
                    imgBut.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                } else {
                    imgBut.setImageResource(R.drawable.ic_favorite_filled_red_24dp);
                }
                realm.beginTransaction();
                s.setFavorite(!b);
                realm.commitTransaction();
            }
        });
    }

    @Override
    public int getItemCount() {
        return spotsData.size();
    }

}
