
package com.aperto.fatpenguin.aperto;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
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
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

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

import java.security.Permissions;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity implements
        OnMapReadyCallback,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.InfoWindowAdapter,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private DrawerLayout drawerLayout;
    private GoogleMap map;
    private GoogleApiClient googleApiClient;
    private double lt;
    private double ln;
    private final Fragment selectorFragment = new CategorySelectorFragment();


    private Realm realm;
    private RealmConfiguration realmConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Set behavior of Navigation drawer
        navigationView.setNavigationItemSelectedListener(
            new NavigationView.OnNavigationItemSelectedListener() {
                // This method will trigger on item Click of navigation menu
                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem) {
                    // Set item in checked state
                    menuItem.setChecked(true);
                    // TODO: handle navigation
                    // Closing drawer on item click
                    drawerLayout.closeDrawers();
                    return true;
                }
            }
        );

        // Set colors of icons in drawer.
        navigationView.getMenu()
                .findItem(R.id.skateboard)
                .getIcon()
                .setColorFilter(Color.parseColor("#C2948A"), PorterDuff.Mode.SRC_ATOP);
        navigationView.getMenu()
                .findItem(R.id.urban_training)
                .getIcon()
                .setColorFilter(Color.parseColor("#E56399"), PorterDuff.Mode.SRC_ATOP);
        navigationView.getMenu()
                .findItem(R.id.basketball)
                .getIcon()
                .setColorFilter(Color.parseColor("#3DCCC7"), PorterDuff.Mode.SRC_ATOP);
        navigationView.getMenu()
                .findItem(R.id.football)
                .getIcon()
                .setColorFilter(Color.parseColor("#4F7CAC"), PorterDuff.Mode.SRC_ATOP);
        navigationView.getMenu()
                .findItem(R.id.running)
                .getIcon()
                .setColorFilter(Color.parseColor("#AA4465"), PorterDuff.Mode.SRC_ATOP);
        navigationView.getMenu()
                .findItem(R.id.swimming)
                .getIcon()
                .setColorFilter(Color.parseColor("#B118C8"), PorterDuff.Mode.SRC_ATOP);
        navigationView.getMenu()
                .findItem(R.id.windsurfing)
                .getIcon()
                .setColorFilter(Color.parseColor("#ED6A5A"), PorterDuff.Mode.SRC_ATOP);



        // Set behavior of Floating Action Button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addSpotIntent = new Intent(MainActivity.this, AddSpotActivity.class);
                startActivity(addSpotIntent);
            }
        });


        /*
        // Set behavior of the test fab
<<<<<<< Updated upstream
        // Set behavior of the test_fab
=======>>>>>>> Stashed changes
        FloatingActionButton testFab = (FloatingActionButton) findViewById(R.id.test_fab);
        testFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spot testSpot1 = new Spot(MainActivity.this, "Matematiktorvet", "foo bar", "basket", 3);
//                Spot testSpot2 = new Spot("Græsplæne", "urban", 55.784820f, 12.519832f);
//                Spot testSpot3 = new Spot("Trappen", "skating", 55.785331f, 12.518995f);
//                Spot testSpot4 = new Spot("Fodboldbane", "football", 55.790746f, 12.521697f);

                // Delete all spots
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.delete(Spot.class);
                    }
                });

                realm.beginTransaction();
                final Spot managedSpot1 = realm.copyToRealm(testSpot1);
//                final Spot managedSpot2 = realm.copyToRealm(testSpot2);
//                final Spot managedSpot3 = realm.copyToRealm(testSpot3);
//                final Spot managedSpot4 = realm.copyToRealm(testSpot4);
                realm.commitTransaction();
            }
        });
<<<<<<< Updated upstream

//        // Set behavior of the test_fab_query
        FloatingActionButton testFabQuery = (FloatingActionButton) findViewById(R.id.test_fab_query);
        testFabQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Spot spot = realm.where(Spot.class).findFirst();
                showStatus(spot.getTitle() + ", " + spot.getDescription() + ", " + spot.getType() +
                ", location: " + spot.getLatitude() + "," + spot.getLongitude());

            }
        });

=======>>>>>>> Stashed changes
        */

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
            if (getSupportFragmentManager().findFragmentById(selectorFragment.getId()) != null) {
                getSupportFragmentManager().beginTransaction().remove(selectorFragment).commit();
            } else {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectorFragment).commit();
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
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent intent = new Intent(this, DetailActivity.class);
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
                lt = lastLocation.getLatitude();
                ln = lastLocation.getLongitude();
            }
        }

        LatLng currentLocation = new LatLng(lt, ln);

        map.addMarker(new MarkerOptions()
                .position(new LatLng(55.7849209, 12.5190433))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_lightblue_logo_small)));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(currentLocation)
                .zoom(15.0f)
                .tilt(30)
                .build();

        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void showStatus(String txt) {
        TextView tv = new TextView(this);
        tv.setText(txt);
        drawerLayout.addView(tv);
    }

    public Location getCurrentLocation() {
        Location currentLocation = null;
        if (checkPermission("android.permission.ACCESS_FINE_LOCATION", 1, 0)
                == PackageManager.PERMISSION_GRANTED) {
            currentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        }

        return currentLocation;
    }
}

