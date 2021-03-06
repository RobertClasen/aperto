package com.aperto.fatpenguin.aperto;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * @Author Rasmus Liebst, Pelle Rubin & Kristian Wolthers
 */
public class FavoriteActivity extends AppCompatActivity{
    List<Spot> favoriteSpots = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.favorite_recyclerView);

        // Use a linear layout manager
        FavoriteAdapter adapter = new FavoriteAdapter(favoriteSpots, this);

        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        RealmConfiguration realmConfig = new RealmConfiguration
                .Builder(FavoriteActivity.this)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm realm = Realm.getInstance(realmConfig);

        RealmResults<Spot> spots = realm.where(Spot.class).equalTo("favorite", true).findAll();
        for (Spot s : spots){
            favoriteSpots.add(s);
            Log.v("another favorite", s.getTitle());
        }
        adapter.notifyDataSetChanged();
    }
}
