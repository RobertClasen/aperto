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

public class FavoriteActivity extends AppCompatActivity{
    private RecyclerView recyclerView;
    private FavoriteAdapter adapter;
    List<Spot> favoriteSpots = new ArrayList<>();
    private static final String MARKER_DATA = "marker_data";

    private Realm realm;
    private RealmConfiguration realmConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        recyclerView = (RecyclerView) findViewById(R.id.favorite_recyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        //recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        adapter = new FavoriteAdapter(favoriteSpots, this);


        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        // specify an adapter

        realmConfig = new RealmConfiguration
                .Builder(FavoriteActivity.this)
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(realmConfig);
        RealmResults<Spot> spots = realm.where(Spot.class).equalTo("favorite", true).findAll();
        for (Spot s : spots){
            favoriteSpots.add(s);
            Log.v("another favorite", s.getTitle());
        }
        adapter.notifyDataSetChanged();
    }
}
