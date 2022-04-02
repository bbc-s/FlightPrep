package com.example.flightprep;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;
import com.google.android.gms.maps.model.UrlTileProvider;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Locale;

public class MapsPrepActivity extends FragmentActivity implements OnMapReadyCallback/*, TileProvider*/ {

    public static final String SHARED_PREFS = "newRoute";
    String locality;
    LatLng latlngFrom1, latlngTo1, latlngStop1_1, latlngStop2_1, latlngStop3_1, latlngStop4_1, latlngStop5_1;
    double latFrom, lngFrom,
            latTo, lngTo,
            latStop1, lngStop1,
            latStop2, lngStop2,
            latStop3, lngStop3,
            latStop4, lngStop4,
            latStop5, lngStop5;
    String from = null;
    String to = null;
    String stop1 = null;
    String stop2 = null;
    String stop3 = null;
    String stop4 = null;
    String stop5 = null;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapsprep);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        addRouteVariable();

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_NONE);

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
                addMarkers();
                mMap.setMinZoomPreference(6.5f);
                mMap.setMaxZoomPreference(11.9f);
                LatLngBounds slovakiaBounds = new LatLngBounds(
                        new LatLng(47.69, 16.74), // SW bounds
                        new LatLng(49.64, 22.64)  // NE bounds
                );
                mMap.setLatLngBoundsForCameraTarget(slovakiaBounds);
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(slovakiaBounds, 0));
            }
        });

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng point) {
                mMap.addMarker(new MarkerOptions()
                        .position(point)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

                double lat = point.latitude;
                double lng = point.longitude;
                String routelatlng = lat + "," + lng;
                locality = String.format(Locale.ENGLISH, "%.4f", lat) + "," + String.format(Locale.ENGLISH,"%.4f", lng);

                Geocoder geocoder = new Geocoder(MapsPrepActivity.this, Locale.getDefault());
                try {
                    List<Address> listAdress = geocoder.getFromLocation(lat, lng, 1);

                    if (listAdress.size() > 0) {
                        locality = listAdress.get(0).getLocality();

                        if (locality == null) {
                            locality = listAdress.get(0).getSubAdminArea();
                        }


                    }
                } catch (IOException e) {

                }

                Intent intentFlightActivity = new Intent(MapsPrepActivity.this, FlightActivity.class);
                intentFlightActivity.putExtra("name", locality);
                intentFlightActivity.putExtra("routelallng", routelatlng);
                setResult(RESULT_OK, intentFlightActivity);
                finish();

            }
        });
    }

    private void addMarkers() {
        if (latlngFrom1 != null) {
            mMap.addMarker(new MarkerOptions()
                    .position(latlngFrom1)
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        }

        if (latlngTo1 != null) {
            mMap.addMarker(new MarkerOptions()
                    .position(latlngTo1)
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        }

        if (latlngStop1_1 != null) {
            mMap.addMarker(new MarkerOptions()
                    .position(latlngStop1_1)
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        }

        if (latlngStop2_1 != null) {
            mMap.addMarker(new MarkerOptions()
                    .position(latlngStop2_1)
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        }

        if (latlngStop3_1 != null) {
            mMap.addMarker(new MarkerOptions()
                    .position(latlngStop3_1)
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        }

        if (latlngStop4_1 != null) {
            mMap.addMarker(new MarkerOptions()
                    .position(latlngStop4_1)
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        }

        if (latlngStop5_1 != null) {
            mMap.addMarker(new MarkerOptions()
                    .position(latlngStop5_1)
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        }
    }


    private void addRouteVariable() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        from = sharedPreferences.getString("latlngFrom", null);
        to = sharedPreferences.getString("latlngTo", null);
        stop1 = sharedPreferences.getString("latlngStop1", null);
        stop2 = sharedPreferences.getString("latlngStop2", null);
        stop3 = sharedPreferences.getString("latlngStop3", null);
        stop4 = sharedPreferences.getString("latlngStop4", null);
        stop5 = sharedPreferences.getString("latlngStop5", null);


        if (from != null) {
            String[] latlngFrom = from.split(",");
            latFrom = Double.parseDouble(latlngFrom[0]);
            lngFrom = Double.parseDouble(latlngFrom[1]);
            latlngFrom1 = new LatLng(latFrom, lngFrom);
        }


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

        if (to != null) {
            String[] latlngTo = to.split(",");
            latTo = Double.parseDouble(latlngTo[0]);
            lngTo = Double.parseDouble(latlngTo[1]);
            latlngTo1 = new LatLng(latTo, lngTo);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intentFlightActivity = new Intent(MapsPrepActivity.this, FlightActivity.class);
        setResult(RESULT_CANCELED, intentFlightActivity);
        finish();
    }
}