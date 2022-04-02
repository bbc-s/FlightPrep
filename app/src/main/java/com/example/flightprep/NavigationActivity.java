package com.example.flightprep;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
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
import com.google.android.gms.tasks.OnSuccessListener;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class NavigationActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {

    public static final String SHARED_PREFS = "newRoute";
    private static final int PERMISSION_FINE_LOCATION = 99;
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
    String LastRouteID = null;
    TextView tvFlightTime;
    TextView tvFlightTimeText;
    TextView tvDistToFin;
    TextView tvDistToFinText;
    TextView tvSpeed;
    TextView tvSpdText;
    TextView tvAltitude;
    TextView tvAltText;
    Button btnEnd;
    double latFrom, lngFrom,
            latTo, lngTo,
            latStop1, lngStop1,
            latStop2, lngStop2,
            latStop3, lngStop3,
            latStop4, lngStop4,
            latStop5, lngStop5;
    LatLng latlngFrom1, latlngTo1, latlngStop1_1, latlngStop2_1, latlngStop3_1, latlngStop4_1, latlngStop5_1;
    List<Double> stopArrayList = new ArrayList<>();
    List<LatLng> polylineArrayList = new ArrayList<>();
    float fin = 0.0f;
    LatLng myLocation;
    Double myLatH = 0.0;
    Double myLngH = 0.0;
    long startTime;
    boolean doubleBackToExitPressedOnce = false;
    PolylineOptions realline;
    List<String> myLine = new ArrayList<String>();
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest = new LocationRequest();
    LocationCallback locationCallBack;
    private GoogleMap navigationMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        mapView = findViewById(R.id.mapView);
        tvFlightTime = findViewById(R.id.tvFlightTime);
        tvDistToFin = findViewById(R.id.tvFlightDistance);
        tvSpeed = findViewById(R.id.tvSpeed);
        tvAltitude = findViewById(R.id.tvAltitute);
        tvFlightTimeText = findViewById(R.id.tvFlightTimeText);
        tvDistToFinText = findViewById(R.id.tvFlightDistanceText);
        tvSpdText = findViewById(R.id.tvSpeedText);
        tvAltText = findViewById(R.id.tvAltitudeText);
        btnEnd = findViewById(R.id.btnEnd);
        btnEnd.setOnClickListener(this);

        mapView.getMapAsync(this);
        mapView.onCreate(savedInstanceState);

        startTime = System.currentTimeMillis();

        addRouteVariable();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                locationRequest = LocationRequest.create()
                        .setInterval(10000)
                        .setFastestInterval(5000)
                        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                        .setMaxWaitTime(100);

                locationCallBack = new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        updateValues(locationResult.getLastLocation());
                    }
                };

                startLocationUpdates();
                updateGPS();
            }
        }, 3000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.btnEnd): {
                endNavigation();
            }
            break;

            default:
                return;
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            String rawFlightTimeCalc = sharedPreferences.getString(String.format(Locale.ENGLISH, "rawFlightTimeCalc"), null);
            String rawFinDistance = sharedPreferences.getString(String.format(Locale.ENGLISH, "rawFinDistance"), null);
            Double rawAvgSpeed = Double.parseDouble(rawFinDistance) / Double.parseDouble(rawFlightTimeCalc);

            if (rawFinDistance == null || rawFlightTimeCalc == null) {
                rawAvgSpeed = null;
            }

            long ID = Long.parseLong(LastRouteID);
            dbh.deleteMyRoutesLocations(ID);
            dbh.updateRoute(new Route(Long.parseLong(LastRouteID), from, to, stop1, stop2, stop3, stop4, stop5, rawFlightTimeCalc, String.format(Locale.ENGLISH, String.valueOf(rawAvgSpeed)), rawFinDistance));

            Intent intentFlightActivity = new Intent(NavigationActivity.this, RouteInfoActivity.class);
            setResult(RESULT_CANCELED, intentFlightActivity);
            stopLocationUpdates();
            finish();
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Double click BACK to exit - your route will not be saved!", Toast.LENGTH_LONG).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSION_FINE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    updateGPS();
                } else {
                    Toast.makeText(this, "This app requires permission to be granted in order to work properly", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }

    private void updateGPS() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    updateValues(location);
                }
            });
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_FINE_LOCATION);
            }
        }
    }

    private void startLocationUpdates() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallBack, null);
            updateGPS();
        }
    }

    private void updateValues(Location location) {
        Double myLat = location.getLatitude();
        Double myLng = location.getLongitude();
        myLocation = new LatLng(myLat, myLng);

        if (myLatH != myLat && myLngH != myLng) {
            stopArrayList.add(myLat);
            stopArrayList.add(myLng);
            polylineArrayList.add(myLocation);
            myLine.add(myLat + "," + myLng);
            myLatH = myLat;
            myLngH = myLng;
        }

        updateSpeed(location);
        updateAltitude(location);
        distanceCalc();
        timeCalc();
        saveRouteData();
        addPolylinesReal();

        navigationMap.animateCamera(CameraUpdateFactory.newLatLng(myLocation));
    }

    private void updateSpeed(Location location) {
        if (location.hasSpeed()) {
            String mySpeed = String.valueOf(location.getSpeed());
            Double mySpeedKt = Double.parseDouble(mySpeed) * 1.94384449;
            if (mySpeedKt >= 2.0) {
                String userSpeed = String.format(Locale.ENGLISH, "%.2f", mySpeedKt) + " kt";
                tvSpeed.setText(userSpeed);

                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("mySpeed", mySpeed);
                editor.putString("userSpeed", userSpeed);
                editor.apply();
            } else {
                tvSpeed.setText("0.00" + " kt");
            }

        } else {
            String mySpeed = "No available";
            tvSpeed.setText(mySpeed);
        }
    }

    private void updateAltitude(Location location) {
        if (location.hasAltitude()) {
            String myAltitude = String.valueOf(location.getAltitude());
            Double myAltitudeFt = Double.parseDouble(myAltitude) * 3.28084;
            tvAltitude.setText(String.format(Locale.ENGLISH, "%.2f", myAltitudeFt) + " ft");
        } else {
            String myAltitude = "No available";
            tvAltitude.setText(myAltitude);
        }
    }

    private void saveRouteData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String LastRouteID = sharedPreferences.getString("lastRouteID", null);

        dbh.addMyrouteLocations(new MyRoutesLocation(1, LastRouteID, String.valueOf(myLine.get(0))));
        myLine.remove(0);
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

        txtDistance = sharedPreferences.getString("txtDistance", null);

        LastRouteID = sharedPreferences.getString("lastRouteID", null);

        String[] latlngFrom = from.split(",");
        latFrom = Double.parseDouble(latlngFrom[0]);
        lngFrom = Double.parseDouble(latlngFrom[1]);
        latlngFrom1 = new LatLng(latFrom, lngFrom);

        if (stop1 != null) {
            String[] latlngStop1 = stop1.split(",");
            latStop1 = Double.parseDouble(latlngStop1[0]);
            lngStop1 = Double.parseDouble(latlngStop1[1]);
            latlngStop1_1 = new LatLng(latStop1, lngStop1);
        }

        if (stop2 != null) {
            String[] latlngStop2 = stop2.split(",");
            latStop2 = Double.parseDouble(latlngStop2[0]);
            lngStop2 = Double.parseDouble(latlngStop2[1]);
            latlngStop2_1 = new LatLng(latStop2, lngStop2);
        }

        if (stop3 != null) {
            String[] latlngStop3 = stop3.split(",");
            latStop3 = Double.parseDouble(latlngStop3[0]);
            lngStop3 = Double.parseDouble(latlngStop3[1]);
            latlngStop3_1 = new LatLng(latStop3, lngStop3);
        }

        if (stop4 != null) {
            String[] latlngStop4 = stop4.split(",");
            latStop4 = Double.parseDouble(latlngStop4[0]);
            lngStop4 = Double.parseDouble(latlngStop4[1]);
            latlngStop4_1 = new LatLng(latStop4, lngStop4);
        }

        if (stop5 != null) {
            String[] latlngStop5 = stop5.split(",");
            latStop5 = Double.parseDouble(latlngStop5[0]);
            lngStop5 = Double.parseDouble(latlngStop5[1]);
            latlngStop5_1 = new LatLng(latStop5, lngStop5);
        }

        String[] latlngTo = to.split(",");
        latTo = Double.parseDouble(latlngTo[0]);
        lngTo = Double.parseDouble(latlngTo[1]);
        latlngTo1 = new LatLng(latTo, lngTo);
    }

    private double distanceCalc() {
        float[] results = new float[1];

        if (stopArrayList.size() != 4) {
            tvDistToFin.setText("No movement");
        } else {
            Location.distanceBetween(stopArrayList.get(0), stopArrayList.get(1), stopArrayList.get(2), stopArrayList.get(3), results);
            float distance = results[0];
            stopArrayList.remove(1);
            stopArrayList.remove(0);

            if (Float.compare(distance, 10.0f) <= 0) {
                distance = 0.0f;
                fin = fin + distance;
            } else {
                fin = fin + distance;
            }
            String distFin = String.format(Locale.ENGLISH, "%.02f", fin / 1000); //m to km
            String userDistance = distFin + " km";

            tvDistToFin.setText(userDistance);

            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("rawFinDistance", String.valueOf(fin));
            editor.putString("userDistance", userDistance);
            editor.apply();
        }

        return fin;
    }

    private void timeCalc() {

        long actualTime = System.currentTimeMillis();
        long flightTime = (actualTime - startTime);

        String flightTimeCalc = (String.format(Locale.ENGLISH, "%dh %dm",
                TimeUnit.MILLISECONDS.toHours(flightTime),
                TimeUnit.MILLISECONDS.toMinutes(flightTime) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(flightTime))
        ));

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String rawFlightTimeCalc = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(flightTime));
        editor.putString("rawFlightTimeCalc", rawFlightTimeCalc);
        editor.putString("flightTimeCalc", flightTimeCalc);
        editor.apply();

        tvFlightTime.setText(flightTimeCalc);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        navigationMap = googleMap;

        navigationMap.setMinZoomPreference(4);
        navigationMap.setMaxZoomPreference(11);

        navigationMap.setMapType(GoogleMap.MAP_TYPE_NONE);

        navigationMap.getUiSettings().setMapToolbarEnabled(false);
        navigationMap.getUiSettings().setZoomControlsEnabled(true);
        navigationMap.getUiSettings().setMyLocationButtonEnabled(false);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        navigationMap.setMyLocationEnabled(true);

        addMarkers();

        addPolylinesPlan();

        addFlightMap();
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

        TileOverlay tileOverlay = navigationMap.addTileOverlay(new TileOverlayOptions()
                .tileProvider(tileProvider));

        navigationMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                navigationMap.setMinZoomPreference(6.5f);
                navigationMap.setMaxZoomPreference(11.9f);
                LatLngBounds slovakiaBounds = new LatLngBounds(
                        new LatLng(47.69, 16.74),
                        new LatLng(49.64, 22.64)
                );

                navigationMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlngFrom1, 11.9f));
                navigationMap.setLatLngBoundsForCameraTarget(slovakiaBounds);
            }
        });
    }

    private void addMarkers() {
        navigationMap.addMarker(new MarkerOptions()
                .position(latlngFrom1)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        navigationMap.addMarker(new MarkerOptions()
                .position(latlngTo1)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        if (latlngStop1_1 != null) {
            navigationMap.addMarker(new MarkerOptions()
                    .position(latlngStop1_1)
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        }

        if (latlngStop2_1 != null) {
            navigationMap.addMarker(new MarkerOptions()
                    .position(latlngStop2_1)
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        }

        if (latlngStop3_1 != null) {
            navigationMap.addMarker(new MarkerOptions()
                    .position(latlngStop3_1)
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        }

        if (latlngStop4_1 != null) {
            navigationMap.addMarker(new MarkerOptions()
                    .position(latlngStop4_1)
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        }

        if (latlngStop5_1 != null) {
            navigationMap.addMarker(new MarkerOptions()
                    .position(latlngStop5_1)
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        }
    }

    private void addPolylinesPlan() {
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

        Polyline polyline = navigationMap.addPolyline(routeLine);
        polyline.setGeodesic(true);
        polyline.setZIndex(1000);
        polyline.setColor(Color.BLUE);
    }

    private void addPolylinesReal() {
        realline = new PolylineOptions();

        if (polylineArrayList.size() != 2) {
            return;
        } else {
            realline.add(polylineArrayList.get(0));
            realline.add(polylineArrayList.get(1));

            Polyline polyline = navigationMap.addPolyline(realline);
            polyline.setZIndex(1000);
            int my_route = ContextCompat.getColor(this, R.color.my_route);
            polyline.setColor(my_route);
            polyline.setGeodesic(true);

            polylineArrayList.remove(0);
        }
    }

    private void endNavigation() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        String rawFlightTimeCalc = sharedPreferences.getString(String.format(Locale.ENGLISH, "rawFlightTimeCalc"), null);
        String rawFinDistance = sharedPreferences.getString(String.format(Locale.ENGLISH, "rawFinDistance"), null);
        Double rawAvgSpeed = Double.parseDouble(rawFinDistance) / Double.parseDouble(rawFlightTimeCalc);

        if (rawFinDistance == null || rawFlightTimeCalc == null) {
            rawAvgSpeed = null;
        }

        String flightTimeCalc = sharedPreferences.getString("flightTimeCalc", null);
        String userDistance = sharedPreferences.getString("userDistance", null);
        Double doubleAvgSpeed = rawAvgSpeed * 1.94384449;
        String userAvgSpeed = String.format(Locale.ENGLISH, "%.2f", doubleAvgSpeed) + " kt";

        dbh.addMyRouteData(new MyRoutesData(1, LastRouteID, txtFrom, txtTo, txtStop1, txtStop2, txtStop3, txtStop4, txtStop5, rawFlightTimeCalc, rawFinDistance, String.valueOf(rawAvgSpeed), flightTimeCalc, userDistance, userAvgSpeed));
        dbh.updateRoute(new Route(Long.parseLong(LastRouteID), from, to, stop1, stop2, stop3, stop4, stop5, rawFlightTimeCalc, String.valueOf(rawAvgSpeed), rawFinDistance));

        exitNavigation();
    }

    private void exitNavigation() {
        stopLocationUpdates();

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Intent intentToMainMenu = new Intent(NavigationActivity.this, MainActivity.class);
        startActivity(intentToMainMenu);
        finish();
        return;
    }

    private void stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallBack);
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