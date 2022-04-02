package com.example.flightprep;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;
import com.google.android.gms.maps.model.UrlTileProvider;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private final ArrayList<LatLng> locations = new ArrayList<>();
    MapView mapView;
    String routeID = null;
    DBHelperMain dbh = new DBHelperMain(this);
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Map");
        }

        mapView = findViewById(R.id.mapView2);
        mapView.getMapAsync(this);
        mapView.onCreate(savedInstanceState);

        Intent intentShowMyRoute = getIntent();
        routeID = intentShowMyRoute.getStringExtra("routeID");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intentFlightActivity = new Intent(MapActivity.this, MainActivity.class);
        setResult(RESULT_CANCELED, intentFlightActivity);
        finish();
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMinZoomPreference(4);
        mMap.setMaxZoomPreference(11);

        mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
        mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);

        addFlightMap();

        if (routeID != null) {
            addMyRoutePolylines();
        }
    }

    private void addFlightMap() {
        TileProvider tileProvider = new UrlTileProvider(512, 512) {
            @Override
            public URL getTileUrl(int x, int y, int zoom) {
                String imagePath = String.format("/assets/maps/day/%d/%d/%d.png", zoom, x, y);
                if (!checkTileExists(x, y, zoom)) {
                    return null;
                }

                URL urla = getClass().getResource(imagePath);
                try {
                    String image = String.valueOf(urla.toURI());
                    return new URL(image);
                } catch (URISyntaxException | MalformedURLException e) {
                    e.printStackTrace();
                    throw new AssertionError(e);
                }
            }

            private boolean checkTileExists(int x, int y, int zoom) {
                int minZoom = 5;
                int maxZoom = 11;

                return (zoom >= minZoom && zoom <= maxZoom);
            }
        };

        TileOverlay tileOverlay = mMap.addTileOverlay(new TileOverlayOptions()
                .tileProvider(tileProvider));

        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                mMap.setMinZoomPreference(6.5f);
                mMap.setMaxZoomPreference(11.9f);
                LatLngBounds slovakiaBounds = new LatLngBounds(
                        new LatLng(47.69, 16.74), // SW bounds
                        new LatLng(49.64, 22.64)  // NE bounds
                );

                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(slovakiaBounds, 0));
                mMap.setLatLngBoundsForCameraTarget(slovakiaBounds);

            }
        });
    }


    private void addMyRoutePolylines() {
        if (routeID == null) {
            return;
        } else {


            Cursor c = dbh.getMyRoutesLocationsCursor(Long.parseLong(routeID));
            while (c.moveToNext()) {
                String latlngPointsString = c.getString(c.getColumnIndex(Database.MyRoutesLocations.LOCATION));
                String[] latlngsplit = latlngPointsString.split(",");
                double lat = Double.parseDouble(latlngsplit[0]);
                double lng = Double.parseDouble(latlngsplit[1]);
                LatLng myRoute = new LatLng(lat, lng);
                locations.add(myRoute);
            }

            Polyline myRouteLine = mMap.addPolyline(new PolylineOptions().color(Color.BLUE).addAll(locations));
            myRouteLine.setGeodesic(true);
            myRouteLine.setZIndex(1100);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

}