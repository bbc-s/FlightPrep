package com.example.flightprep;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ListAircraftActivity extends AppCompatActivity {

    ListView lv;
    DBHelperMain dbh = new DBHelperMain(this);
    SimpleCursorAdapter cursorAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aircraft);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Aircraft list");
        }

        lv = findViewById(R.id.listAircrafts);

        addCursorAdapter();
    }

    private void addCursorAdapter() {
        cursorAdapter = new SimpleCursorAdapter(this,
                R.layout.myaricraftlayout,
                dbh.getAircraftCursor(),
                new String[]{Database.Aircrafts.AIRCRAFT_ID,
                        Database.Aircrafts.MANUFACTURER,
                        Database.Aircrafts.REG,
                        Database.Aircrafts.TYPE,
                        Database.Aircrafts.MAX_CRUISE_SPEED,
                        Database.Aircrafts.MAX_ALTITUDE,
                        Database.Aircrafts.MAX_RANGE},
                new int[]{R.id.idListAircraft,
                        R.id.manufacturerListAircraft,
                        R.id.regListAircraft,
                        R.id.typeListAircraft,
                        R.id.maxcruisespeedListAircraft,
                        R.id.maxaltitudeListAircraft,
                        R.id.maxrangeListAircraft,}, 0);

        lv.setAdapter(cursorAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor c = ((SimpleCursorAdapter) lv.getAdapter()).getCursor();
                c.moveToPosition(position);
                long ID = c.getLong(c.getColumnIndex(Database.Aircrafts.AIRCRAFT_ID));

                Intent intentSendAircraft = new Intent(ListAircraftActivity.this, FlightActivity.class);
                String aname = c.getString(1);
                String aid = c.getString(0);
                intentSendAircraft.putExtra("aname", aname);
                intentSendAircraft.putExtra("aid", aid);

                setResult(RESULT_OK, intentSendAircraft);
                finish();
            }
        });
    }
}