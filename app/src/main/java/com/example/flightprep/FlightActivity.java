package com.example.flightprep;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


public class FlightActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String SHARED_PREFS = "newRoute";
    Button btnNxt;
    Button btnSavedRoutes;
    TextView tv_dep, tv_arr, tv_rp1, tv_rp2, tv_rp3, tv_rp4, tv_rp5, tv_chA;
    EditText te_speed, te_altitude;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Flight");
        }


        tv_dep = findViewById(R.id.txtDeparture);
        tv_arr = findViewById(R.id.txtArrival);
        tv_chA = findViewById(R.id.txtChooseAircraft);

        tv_rp1 = findViewById(R.id.txtRoutePoint1);
        tv_rp1.setOnClickListener(this);

        tv_rp2 = findViewById(R.id.txtRoutePoint2);
        tv_rp2.setOnClickListener(this);

        tv_rp3 = findViewById(R.id.txtRoutePoint3);
        tv_rp3.setOnClickListener(this);

        tv_rp4 = findViewById(R.id.txtRoutePoint4);
        tv_rp4.setOnClickListener(this);

        tv_rp5 = findViewById(R.id.txtRoutePoint5);
        tv_rp5.setOnClickListener(this);

        te_speed = findViewById(R.id.etSpeed);
        te_altitude = findViewById(R.id.etAltitude);

        btnNxt = findViewById(R.id.btnNext);
        btnSavedRoutes = findViewById(R.id.btnSavedRoutes);

        tv_chA.setOnClickListener(this);
        tv_dep.setOnClickListener(this);
        tv_arr.setOnClickListener(this);
        btnNxt.setOnClickListener(this);
        btnSavedRoutes.setOnClickListener(this);


        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();
        editor.apply();
    }


    private void addRouteVariable() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        txtSpeed = te_speed.getText().toString();
        txtAltitude = te_altitude.getText().toString();
        editor.putString("txtSpeed", txtSpeed);
        editor.putString("txtAltitude", txtAltitude);

        if (TextUtils.isEmpty(tv_rp1.getText().toString())) {
            editor.putString("latlngStop1", null);
        }

        if (TextUtils.isEmpty(tv_rp2.getText().toString())) {
            editor.putString("latlngStop2", null);
        }

        if (TextUtils.isEmpty(tv_rp3.getText().toString())) {
            editor.putString("latlngStop3", null);
        }

        if (TextUtils.isEmpty(tv_rp4.getText().toString())) {
            editor.putString("latlngStop4", null);
        }

        if (TextUtils.isEmpty(tv_rp5.getText().toString())) {
            editor.putString("latlngStop5", null);
        }

        editor.apply();


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

        if (TextUtils.isEmpty(from) ||
                TextUtils.isEmpty(to) ||
                TextUtils.isEmpty(txtSpeed) ||
                TextUtils.isEmpty(txtAltitude) ||
                TextUtils.isEmpty(aircraft)
        ) {

            Toast.makeText(FlightActivity.this, "Missing data, try again", Toast.LENGTH_LONG).show();
            return;
        } else {


            Intent intentNext = new Intent(FlightActivity.this, RouteInfoActivity.class);
            startActivityForResult(intentNext, R.id.btnNext);

        }
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        PopupMenu popup = new PopupMenu(this, v);
        popup.getMenuInflater().inflate(R.menu.flightpressmenu1, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Airport:
                        Intent intentChooseAirport = new Intent(FlightActivity.this, ListAirportActivity.class);
                        startActivityForResult(intentChooseAirport, v.getId());
                        return true;

                    case R.id.Map:
                        Intent intentChooseOnMap = new Intent(FlightActivity.this, MapsPrepActivity.class);
                        startActivityForResult(intentChooseOnMap, v.getId());
                        return true;

                    case R.id.Delete:
                        switch (v.getId()) {
                            case R.id.txtRoutePoint1: {
                                tv_rp1.setText("");
                                editor.putString("latlngStop1", null);
                            }
                            break;

                            case R.id.txtRoutePoint2: {
                                tv_rp2.setText("");
                                editor.putString("latlngStop2", null);
                            }
                            break;

                            case R.id.txtRoutePoint3: {
                                tv_rp3.setText("");
                                editor.putString("latlngStop3", null);
                            }
                            break;

                            case R.id.txtRoutePoint4: {
                                tv_rp4.setText("");
                                editor.putString("latlngStop4", null);
                            }
                            break;

                            case R.id.txtRoutePoint5: {
                                tv_rp5.setText("");
                                editor.putString("latlngStop5", null);
                            }
                            break;

                        }
                        editor.apply();
                        return true;
                    default:
                        return onMenuItemClick(item);
                }
            }
        });

        switch (v.getId()) {

            case R.id.txtDeparture: {
                Intent intentChooseAirport = new Intent(FlightActivity.this, ListAirportActivity.class);
                startActivityForResult(intentChooseAirport, R.id.txtDeparture);
            }
            break;

            case R.id.txtArrival: {
                Intent intentChooseAirport = new Intent(FlightActivity.this, ListAirportActivity.class);
                startActivityForResult(intentChooseAirport, R.id.txtArrival);
            }
            break;

            case R.id.txtRoutePoint1: {
                popup.show();
            }
            break;

            case R.id.txtRoutePoint2: {
                popup.show();
            }
            break;

            case R.id.txtRoutePoint3: {
                popup.show();
            }
            break;

            case R.id.txtRoutePoint4: {
                popup.show();
            }
            break;

            case R.id.txtRoutePoint5: {
                popup.show();
            }
            break;

            case R.id.txtChooseAircraft: {
                Intent intentChooseAircraft = new Intent(FlightActivity.this, ListAircraftActivity.class);
                startActivityForResult(intentChooseAircraft, R.id.txtChooseAircraft);
            }
            break;

            case R.id.btnNext: {
                addRouteVariable();
            }
            break;

            case R.id.btnSavedRoutes: {
                Intent intentSavedRoutes = new Intent(FlightActivity.this, ListSavedRoutesActivity.class);
                startActivityForResult(intentSavedRoutes, R.id.btnSavedRoutes);
            }
            break;

            default:
                return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (requestCode) {

            case R.id.txtChooseAircraft:
                if (resultCode == RESULT_OK) {

                    String addaircraft = data.getStringExtra("aname");
                    String idaircraft = data.getStringExtra("aid");
                    tv_chA.setText(addaircraft);

                    editor.putString("addAircraft", addaircraft);
                    editor.putString("idAircraft", idaircraft);
                    editor.apply();

                }
                break;


            case R.id.txtDeparture:
                if (resultCode == RESULT_OK) {
                    txtFrom = data.getStringExtra("name");
                    tv_dep.setText(txtFrom);
                    from = data.getStringExtra("routelallng");

                    editor.putString("latlngFrom", from);
                    editor.putString("txtFrom", txtFrom);
                    editor.apply();

                }
                break;


            case R.id.txtArrival:
                if (resultCode == RESULT_OK) {
                    txtTo = data.getStringExtra("name");
                    tv_arr.setText(txtTo);
                    to = data.getStringExtra("routelallng");

                    editor.putString("latlngTo", to);
                    editor.putString("txtTo", txtTo);
                    editor.apply();

                }
                break;

            case R.id.txtRoutePoint1:
                if (resultCode == RESULT_OK) {
                    txtStop1 = data.getStringExtra("name");
                    tv_rp1.setText(txtStop1);
                    stop1 = data.getStringExtra("routelallng");

                    editor.putString("latlngStop1", stop1);
                    editor.putString("txtStop1", txtStop1);
                    editor.apply();

                }
                break;

            case R.id.txtRoutePoint2:
                if (resultCode == RESULT_OK) {
                    txtStop2 = data.getStringExtra("name");
                    tv_rp2.setText(txtStop2);
                    stop2 = data.getStringExtra("routelallng");

                    editor.putString("latlngStop2", stop2);
                    editor.putString("txtStop2", txtStop2);
                    editor.apply();

                }
                break;

            case R.id.txtRoutePoint3:
                if (resultCode == RESULT_OK) {
                    txtStop3 = data.getStringExtra("name");
                    tv_rp3.setText(txtStop3);
                    stop3 = data.getStringExtra("routelallng");

                    editor.putString("latlngStop3", stop3);
                    editor.putString("txtStop3", txtStop3);
                    editor.apply();

                }
                break;

            case R.id.txtRoutePoint4:
                if (resultCode == RESULT_OK) {
                    txtStop4 = data.getStringExtra("name");
                    tv_rp4.setText(txtStop4);
                    stop4 = data.getStringExtra("routelallng");

                    editor.putString("latlngStop4", stop4);
                    editor.putString("txtStop4", txtStop4);
                    editor.apply();

                }
                break;

            case R.id.txtRoutePoint5:
                if (resultCode == RESULT_OK) {
                    txtStop5 = data.getStringExtra("name");
                    tv_rp5.setText(txtStop5);
                    stop5 = data.getStringExtra("routelallng");

                    editor.putString("latlngStop5", stop5);
                    editor.putString("txtStop5", txtStop5);
                    editor.apply();

                }
                break;

            case R.id.btnSavedRoutes:
                if (resultCode == RESULT_OK) {
                    txtFrom = data.getStringExtra("from");
                    txtTo = data.getStringExtra("to");
                    txtStop1 = data.getStringExtra("stop1");
                    txtStop2 = data.getStringExtra("stop2");
                    txtStop3 = data.getStringExtra("stop3");
                    txtStop4 = data.getStringExtra("stop4");
                    txtStop5 = data.getStringExtra("stop5");
                    txtSpeed = data.getStringExtra("speed");
                    txtAltitude = data.getStringExtra("altitude");

                    from = data.getStringExtra("fromgps");
                    to = data.getStringExtra("togps");
                    stop1 = data.getStringExtra("stop1gps");
                    stop2 = data.getStringExtra("stop2gps");
                    stop3 = data.getStringExtra("stop3gps");
                    stop4 = data.getStringExtra("stop4gps");
                    stop5 = data.getStringExtra("stop5gps");


                    editor.putString("txtFrom", txtFrom);
                    editor.putString("txtTo", txtTo);
                    editor.putString("txtStop1", txtStop1);
                    editor.putString("txtStop2", txtStop2);
                    editor.putString("txtStop3", txtStop3);
                    editor.putString("txtStop4", txtStop4);
                    editor.putString("txtStop5", txtStop5);
                    editor.putString("txtSpeed", txtSpeed);
                    editor.putString("txtAltitude", txtAltitude);

                    editor.putString("latlngFrom", from);
                    editor.putString("latlngTo", to);
                    editor.putString("latlngStop1", stop1);
                    editor.putString("latlngStop2", stop2);
                    editor.putString("latlngStop3", stop3);
                    editor.putString("latlngStop4", stop4);
                    editor.putString("latlngStop5", stop5);
                    editor.apply();


                    tv_dep.setText(txtFrom);
                    tv_arr.setText(txtTo);
                    tv_rp1.setText(txtStop1);
                    tv_rp2.setText(txtStop2);
                    tv_rp3.setText(txtStop3);
                    tv_rp4.setText(txtStop4);
                    tv_rp5.setText(txtStop5);
                    te_speed.setText(txtSpeed);
                    te_altitude.setText(txtAltitude);

                }
                break;

            default:
                return;

        }
    }
}