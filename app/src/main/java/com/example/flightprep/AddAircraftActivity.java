package com.example.flightprep;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class AddAircraftActivity extends AppCompatActivity {

    Button addbtn;
    EditText manutxt;
    EditText regtxt;
    EditText typetxt;
    EditText mcstxt;
    EditText matxt;
    EditText mrtxt;
    Long aircraftID;
    DBHelperMain dbh = new DBHelperMain(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_aircraft);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Aircraft");
        }

        addbtn = findViewById(R.id.btnSaveAircraft);
        manutxt = findViewById(R.id.addManufacturer);
        regtxt = findViewById(R.id.addReg);
        typetxt = findViewById(R.id.addType);
        mcstxt = findViewById(R.id.addMaxCruiseSpeed);
        matxt = findViewById(R.id.addMaxAltitude);
        mrtxt = findViewById(R.id.addMaxRange);

        Intent intentShowMyRoute = getIntent();
        aircraftID = intentShowMyRoute.getLongExtra("aID", 0);

        if (aircraftID != 0) {
            editAircraft();
        }

        addNewAircraft();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intentAircraftActivity = new Intent(AddAircraftActivity.this, AircraftActivity.class);
        setResult(RESULT_CANCELED, intentAircraftActivity);
        finish();
    }

    private void editAircraft() {
        Cursor c = dbh.getSingleAircraftCursor(aircraftID);
        String aircraft_manu = c.getString(c.getColumnIndex(Database.Aircrafts.MANUFACTURER));
        String aircraft_reg = c.getString(c.getColumnIndex(Database.Aircrafts.REG));
        String aircraft_type = c.getString(c.getColumnIndex(Database.Aircrafts.TYPE));
        String aircraft_mcs = c.getString(c.getColumnIndex(Database.Aircrafts.MAX_CRUISE_SPEED));
        String aircraft_ma = c.getString(c.getColumnIndex(Database.Aircrafts.MAX_ALTITUDE));
        String aircraft_mr = c.getString(c.getColumnIndex(Database.Aircrafts.MAX_RANGE));

        manutxt.setText(aircraft_manu);
        regtxt.setText(aircraft_reg);
        typetxt.setText(aircraft_type);
        mcstxt.setText(aircraft_mcs);
        matxt.setText(aircraft_ma);
        mrtxt.setText(aircraft_mr);

    }

    public void addNewAircraft() {
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(manutxt.getText().toString()) ||
                        TextUtils.isEmpty(regtxt.getText().toString()) ||
                        TextUtils.isEmpty(typetxt.getText().toString()) ||
                        TextUtils.isEmpty(mcstxt.getText().toString()) ||
                        TextUtils.isEmpty(matxt.getText().toString()) ||
                        TextUtils.isEmpty(mrtxt.getText().toString())) {

                    Toast.makeText(AddAircraftActivity.this, "Missing data, try again", Toast.LENGTH_LONG).show();
                    return;
                }

                String manu = manutxt.getText().toString();
                String reg = regtxt.getText().toString();
                String type = typetxt.getText().toString();
                int mc = Integer.parseInt(mcstxt.getText().toString());
                int ma = Integer.parseInt(matxt.getText().toString());
                int mr = Integer.parseInt(mrtxt.getText().toString());

                if (aircraftID == 0) {
                    dbh.addAircraft(new Aircraft(1, manu, reg, type, mc, ma, mr));
                } else {
                    dbh.editAircraft(new Aircraft(aircraftID, manu, reg, type, mc, ma, mr));

                }

                Intent aircraftActivity = new Intent(AddAircraftActivity.this, AircraftActivity.class);
                setResult(RESULT_OK, aircraftActivity);
                finish();
            }
        });
    }
}