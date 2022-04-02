package com.example.flightprep;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
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
import java.util.List;
import java.util.Locale;

public class RouteInfoActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {

    public static final String SHARED_PREFS = "newRoute";
    DBHelperMain dbh = new DBHelperMain(this);
    MapView mapView;
    String aircraft = null;
    String from = null;
    String to = null;
    String stop1 = null;
    String stop2 = null;
    String stop3 = null;
    String stop4 = null;
    String stop5 = null;
    String speed = null;
    String time = null;
    String txtFrom = null;
    String txtTo = null;
    String txtStop1 = null;
    String txtStop2 = null;
    String txtStop3 = null;
    String txtStop4 = null;
    String txtStop5 = null;
    String txtSpeed = null;
    String txtAltitude = null;
    String txtDistance = null;
    TextView tvArrTime;
    TextView tvDistance;
    TextView tvArrText;
    TextView tvDisText;
    Button btnFly;
    Button btnSave;
    double latFrom, lngFrom,
            latTo, lngTo,
            latStop1, lngStop1,
            latStop2, lngStop2,
            latStop3, lngStop3,
            latStop4, lngStop4,
            latStop5, lngStop5;
    LatLng latlngFrom1, latlngTo1, latlngStop1_1, latlngStop2_1, latlngStop3_1, latlngStop4_1, latlngStop5_1;
    List<Double> stopArrayList = new ArrayList<>();
    float fin = 0.0f;
    private GoogleMap routeMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_info);

        tvArrTime = findViewById(R.id.tvArrT);
        tvDistance = findViewById(R.id.tvDist);
        tvArrText = findViewById(R.id.tvArrTText);
        tvDisText = findViewById(R.id.tvDistText);

        btnFly = findViewById(R.id.btnFly);
        btnSave = findViewById(R.id.btnSave);
        btnFly.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        mapView = findViewById(R.id.mapView1);

        mapView.getMapAsync((OnMapReadyCallback) this);

        addRouteVariable();

        distanceCalc();
        timeCalc();

        mapView.onCreate(savedInstanceState);
    }

    private void addRouteVariable() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        aircraft = sharedPreferences.getString("idAircraft", "");

        from = sharedPreferences.getString("latlngFrom", "");
        txtFrom = sharedPreferences.getString("txtFrom", "");

        to = sharedPreferences.getString("latlngTo", "");
        txtTo = sharedPreferences.getString("txtTo", "");

        stop1 = sharedPreferences.getString("latlngStop1", null);
        txtStop1 = sharedPreferences.getString("txtStop1", null);

        stop2 = sharedPreferences.getString("latlngStop2", null);
        txtStop2 = sharedPreferences.getString("txtStop2", null);

        stop3 = sharedPreferences.getString("latlngStop3", null);
        txtStop3 = sharedPreferences.getString("txtStop3", null);

        stop4 = sharedPreferences.getString("latlngStop4", null);
        txtStop4 = sharedPreferences.getString("txtStop4", null);

        stop5 = sharedPreferences.getString("latlngStop5", null);
        txtStop5 = sharedPreferences.getString("txtStop5", null);

        txtSpeed = sharedPreferences.getString("txtSpeed", null);

        txtAltitude = sharedPreferences.getString("txtAltitude", null);

        String[] latlngFrom = from.split(",");
        latFrom = Double.parseDouble(latlngFrom[0]);
        lngFrom = Double.parseDouble(latlngFrom[1]);
        latlngFrom1 = new LatLng(latFrom, lngFrom);
        stopArrayList.add(latFrom);
        stopArrayList.add(lngFrom);

        if (stop1 != null) {
            String[] latlngStop1 = stop1.split(",");
            latStop1 = Double.parseDouble(latlngStop1[0]);
            lngStop1 = Double.parseDouble(latlngStop1[1]);
            latlngStop1_1 = new LatLng(latStop1, lngStop1);
            stopArrayList.add(latStop1);
            stopArrayList.add(lngStop1);
        }

        if (stop2 != null) {
            String[] latlngStop2 = stop2.split(",");
            latStop2 = Double.parseDouble(latlngStop2[0]);
            lngStop2 = Double.parseDouble(latlngStop2[1]);
            latlngStop2_1 = new LatLng(latStop2, lngStop2);
            stopArrayList.add(latStop2);
            stopArrayList.add(lngStop2);
        }

        if (stop3 != null) {
            String[] latlngStop3 = stop3.split(",");
            latStop3 = Double.parseDouble(latlngStop3[0]);
            lngStop3 = Double.parseDouble(latlngStop3[1]);
            latlngStop3_1 = new LatLng(latStop3, lngStop3);
            stopArrayList.add(latStop3);
            stopArrayList.add(lngStop3);
        }

        if (stop4 != null) {
            String[] latlngStop4 = stop4.split(",");
            latStop4 = Double.parseDouble(latlngStop4[0]);
            lngStop4 = Double.parseDouble(latlngStop4[1]);
            latlngStop4_1 = new LatLng(latStop4, lngStop4);
            stopArrayList.add(latStop4);
            stopArrayList.add(lngStop4);
        }

        if (stop5 != null) {
            String[] latlngStop5 = stop5.split(",");
            latStop5 = Double.parseDouble(latlngStop5[0]);
            lngStop5 = Double.parseDouble(latlngStop5[1]);
            latlngStop5_1 = new LatLng(latStop5, lngStop5);
            stopArrayList.add(latStop5);
            stopArrayList.add(lngStop5);
        }

        String[] latlngTo = to.split(",");
        latTo = Double.parseDouble(latlngTo[0]);
        lngTo = Double.parseDouble(latlngTo[1]);
        latlngTo1 = new LatLng(latTo, lngTo);
        stopArrayList.add(latTo);
        stopArrayList.add(lngTo);
    }

    private double distanceCalc() {
        for (int i = 0; i < stopArrayList.size(); i += 2) {
            float[] results = new float[1];

            if (i + 3 <= stopArrayList.size()) {
                Location.distanceBetween(stopArrayList.get(i), stopArrayList.get(i + 1), stopArrayList.get(i + 2), stopArrayList.get(i + 3), results);
                float distance = results[0];

                fin = fin + distance;
            }
        }

        String distFin = String.format(Locale.ENGLISH, "%.02f", fin / 1000);

        tvDistance.setText(distFin + " km");

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("txtDistance", distFin);
        editor.apply();

        return fin;
    }

    private void timeCalc() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        double distance = Double.parseDouble(sharedPreferences.getString("txtDistance", null)); //m to km
        double spdKMH = Double.parseDouble(txtSpeed) * 1.85200; //kt to kmph

        double time = distance / spdKMH;
        int hours = (int) time;
        int minutes = (int) (60 * (time - hours));

        String flightTime = hours + "h" + " " + minutes + "m";

        tvArrTime.setText(flightTime);
    }

    private void addFlightMap() {
        TileProvider tileProvider = new UrlTileProvider(512, 512) {
            @Override
            public URL getTileUrl(int x, int y, int zoom) {
                String imageDayPath = String.format("/assets/maps/day/%d/%d/%d.png", zoom, x, y);
                if (!checkTileExists(x, y, zoom)) {
                    return null;
                }

                URL urla = getClass().getResource(imageDayPath);
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

        TileOverlay tileOverlay = routeMap.addTileOverlay(new TileOverlayOptions()
                .tileProvider(tileProvider));

        routeMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                routeMap.setMinZoomPreference(6.5f);
                routeMap.setMaxZoomPreference(11.9f);

                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                builder.include(latlngFrom1);
                if (latlngStop1_1 != null) {
                    builder.include(latlngStop1_1);
                }
                if (latlngStop2_1 != null) {
                    builder.include(latlngStop2_1);
                }
                if (latlngStop3_1 != null) {
                    builder.include(latlngStop3_1);
                }
                if (latlngStop4_1 != null) {
                    builder.include(latlngStop4_1);
                }
                if (latlngStop5_1 != null) {
                    builder.include(latlngStop5_1);
                }
                builder.include(latlngTo1);
                LatLngBounds bounds = builder.build();
                routeMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));

                LatLngBounds slovakiaBounds = new LatLngBounds(
                        new LatLng(47.69, 16.74), // SW bounds
                        new LatLng(49.64, 22.64)  // NE bounds
                );
                routeMap.setLatLngBoundsForCameraTarget(slovakiaBounds);
            }
        });
    }

    private void addMarkers() {
        routeMap.addMarker(new MarkerOptions()
                .position(latlngFrom1)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        routeMap.addMarker(new MarkerOptions()
                .position(latlngTo1)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        if (latlngStop1_1 != null) {
            routeMap.addMarker(new MarkerOptions()
                    .position(latlngStop1_1)
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        }

        if (latlngStop2_1 != null) {
            routeMap.addMarker(new MarkerOptions()
                    .position(latlngStop2_1)
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        }

        if (latlngStop3_1 != null) {
            routeMap.addMarker(new MarkerOptions()
                    .position(latlngStop3_1)
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        }

        if (latlngStop4_1 != null) {
            routeMap.addMarker(new MarkerOptions()
                    .position(latlngStop4_1)
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        }

        if (latlngStop5_1 != null) {
            routeMap.addMarker(new MarkerOptions()
                    .position(latlngStop5_1)
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        }
    }

    private void addPolylines() {
        PolylineOptions routeLine = new PolylineOptions();

        routeLine.add(latlngFrom1);

        if (latlngStop1_1 != null) {
            routeLine.add(latlngStop1_1);
        }

        if (latlngStop2_1 != null) {
            routeLine.add(latlngStop2_1);
        }

        if (latlngStop3_1 != null) {
            routeLine.add(latlngStop3_1);
        }

        if (latlngStop4_1 != null) {
            routeLine.add(latlngStop4_1);
        }

        if (latlngStop5_1 != null) {
            routeLine.add(latlngStop5_1);
        }

        routeLine.add(latlngTo1);

        Polyline polyline = routeMap.addPolyline(routeLine);
        polyline.setZIndex(1000);
        polyline.setGeodesic(true);
        polyline.setColor(Color.BLUE);
    }

    @Override
    public void onClick(View v) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        txtDistance = sharedPreferences.getString("txtDistance", null);

        switch (v.getId()) {
            case R.id.btnFly: {
                Intent intentFly = new Intent(RouteInfoActivity.this, NavigationActivity.class);
                startActivityForResult(intentFly, v.getId());
                dbh.addRoute(new Route(1, from, to, stop1, stop2, stop3, stop4, stop5, time, speed, txtDistance));

                Cursor cursor = dbh.lastRouteID();
                String lastRouteID = cursor.getString(0);

                editor.putString("lastRouteID", lastRouteID);
                editor.apply();
            }
            break;

            case R.id.btnSave: {

                dbh.saveRoute(new SavedRoutes(1, txtFrom, from, txtTo, to, txtStop1, stop1, txtStop2, stop2, txtStop3, stop3, txtStop4, stop4, txtStop5, stop5, txtSpeed, txtAltitude, txtDistance));

                Toast.makeText(RouteInfoActivity.this, "Route saved", Toast.LENGTH_LONG).show();
            }
            break;

            default:
                return;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intentFlightActivity = new Intent(RouteInfoActivity.this, FlightActivity.class);
        setResult(RESULT_CANCELED, intentFlightActivity);
        finish();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        routeMap = googleMap;

        routeMap.setMinZoomPreference(4);
        routeMap.setMaxZoomPreference(11);

        routeMap.setMapType(GoogleMap.MAP_TYPE_NONE);
        routeMap.getUiSettings().setMapToolbarEnabled(false);
        routeMap.getUiSettings().setMyLocationButtonEnabled(true);
        routeMap.getUiSettings().setZoomControlsEnabled(true);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        routeMap.setMyLocationEnabled(true);

        addMarkers();

        addPolylines();

        addFlightMap();
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