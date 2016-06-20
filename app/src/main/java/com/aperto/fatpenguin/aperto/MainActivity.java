
package com.aperto.fatpenguin.aperto;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements
        OnMapReadyCallback,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.InfoWindowAdapter,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private DrawerLayout drawerLayout;
    private GoogleMap map;
    private GoogleApiClient googleApiClient;
    private double latitude;
    private double longitude;
    private Fragment selectorFragment;
    private boolean selectorIsVisible;
    private static final String MARKER_DATA = "marker_data";
    private CameraPosition startPosition;

    private static final int REQUEST_NEW_SPOT = 0;

    private Realm realm;
    private RealmConfiguration realmConfig;

    //test
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            selectorFragment = new CategorySelectorFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectorFragment).commit();
            selectorIsVisible = true;
        }

        // Adding Toolbar to Main screen
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.aperto_fat_a_lys_rest);

        // Create Navigation drawer and inflate layout
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        // Adding menu icon to Toolbar
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Set behavior of Navigation drawer
        assert navigationView != null; //TODO: Is this one needed??
        navigationView.setNavigationItemSelectedListener(
            new NavigationView.OnNavigationItemSelectedListener() {
                // This method will trigger on item Click of navigation menu
                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem) {

                    int menuId = menuItem.getItemId();

                    // TODO: handle navigation
                    // Closing drawer on item click
                    switch (menuItem.getItemId()) {
                        case R.id.drawer_favorite:
                            Intent intent = new Intent(MainActivity.this, FavoriteActivity.class);
                            MainActivity.this.startActivity(intent);
                            return true;
                        case R.id.drawer_settings:
                            return true;
                    }
                    drawerLayout.closeDrawers();
                    return false;
                }
            }
        );



        // Set behavior of Floating Action Button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addSpotIntent = new Intent(MainActivity.this, AddSpotActivity.class);
                startActivityForResult(addSpotIntent, REQUEST_NEW_SPOT);
            }
        });

        // Set behavior of the test fab
//        FloatingActionButton testFab = (FloatingActionButton) findViewById(R.id.test_fab);
//        testFab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Delete all spots
//                realm.executeTransaction(new Realm.Transaction() {
//                    @Override
//                    public void execute(Realm realm) {
//                        realm.delete(Spot.class);
//                    }
//                });
//            }
//        });

        // Set behavior of the test_fab_query
//        FloatingActionButton testFabQuery = (FloatingActionButton) findViewById(R.id.test_fab_query);
//        testFabQuery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                placeMarkers(1);
//            }
//        });


        // Get a reference to the MapFragment from resources.
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Create an instance of GoogleAPIClient.
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        realmConfig = new RealmConfiguration
                .Builder(MainActivity.this)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfig);
        realm = Realm.getDefaultInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the app bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle app bar item clicks here. The app bar will automatically handle clicks on
        // the Home/Up button, so long as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.filter) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if (selectorIsVisible) {
                ft.hide(selectorFragment);
                ft.commit();
                selectorIsVisible = false;
            } else {
                ft.show(selectorFragment);
                ft.commit();
                selectorIsVisible = true;
            }

        } else if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        map.setPadding(0, 110, 0, 0);
        map.getUiSettings().setCompassEnabled(false);

        // Setting an info window adapter allows us to change both the contents and look of the
        // info window.
        map.setInfoWindowAdapter(new CustomInfoWindowAdapter(this));
        map.setOnInfoWindowClickListener(this);
        map.setOnMarkerClickListener(this);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(marker.getPosition().latitude+0.004, marker.getPosition().longitude))
                .zoom(15.0f)
                .tilt(30)
                .build();

        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        marker.showInfoWindow();

        return true;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        double[] position = new double[] {marker.getPosition().latitude,
                marker.getPosition().longitude};

        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(MARKER_DATA, position);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        map.setMyLocationEnabled(true);

        if (checkPermission("android.permission.ACCESS_FINE_LOCATION", 1, 0)
                == PackageManager.PERMISSION_GRANTED) {
            Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (lastLocation != null) {
                latitude = lastLocation.getLatitude();
                longitude = lastLocation.getLongitude();
            }
        }

        LatLng currentLocation = new LatLng(latitude, longitude);

        if (startPosition == null) {
            startPosition = new CameraPosition.Builder()
                    .target(currentLocation)
                    .zoom(15.0f)
                    .tilt(30)
                    .build();

            map.animateCamera(CameraUpdateFactory.newCameraPosition(startPosition));
        }
    }

    @Override
    public void onConnectionSuspended(int i) { }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) { }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Spot spot = new Spot();

        if (requestCode == REQUEST_NEW_SPOT && resultCode == RESULT_OK) {
            String[] spotData = data.getStringArrayExtra("spot_data");
            byte[] thumbnail = data.getByteArrayExtra("spot_thumbnail");

            spot.setCategory(Integer.valueOf(spotData[0]));
            spot.setTitle(spotData[1]);
            spot.setDescription(spotData[2]);
            spot.setRating(Float.valueOf(spotData[3]));
            spot.setLatitude(latitude);
            spot.setLongitude(longitude);
            spot.setThumbnail(thumbnail);

            realm.beginTransaction();
            final Spot managedSpot = realm.copyToRealm(spot);
            realm.commitTransaction();
        }

        int categoryId = getResources().obtainTypedArray(R.array.categories_markers)
                .getResourceId(spot.getCategory(), -1);


        map.addMarker(new MarkerOptions()
                .position(new LatLng(spot.getLatitude(), spot.getLongitude()))
                .icon(BitmapDescriptorFactory.fromResource(categoryId)));

    }

    public void placeMarkers(boolean[] chosenCategories) {

        map.clear();

        for (int i = 0; i < chosenCategories.length; i++) {
            if (chosenCategories[i]) {
                RealmResults<Spot> spots = realm.where(Spot.class)
                        .equalTo("category", i).findAll();
                for (Spot s : spots) {
                    int categoryId = getResources().obtainTypedArray(R.array.categories_markers)
                            .getResourceId(i, -1);

                    map.addMarker(new MarkerOptions()
                            .position(new LatLng(s.getLatitude(), s.getLongitude()))
                            .icon(BitmapDescriptorFactory.fromResource(categoryId)));
                }
            }
        }
    }

}
