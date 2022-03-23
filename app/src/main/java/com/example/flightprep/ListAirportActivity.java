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

public class ListAirportActivity extends AppCompatActivity {

    ListView lv;
    DBHelperMain dbh = new DBHelperMain(this);
    SimpleCursorAdapter cursorAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_airport);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Airport");
        }

        lv = findViewById(R.id.listAirports);

        addCursorAdapter();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intentFlightActivity = new Intent(ListAirportActivity.this, FlightActivity.class);
        setResult(RESULT_CANCELED, intentFlightActivity);
        finish();
    }

    private void addCursorAdapter() {
        cursorAdapter = new SimpleCursorAdapter(this,
                R.layout.myairportslayout,
                dbh.getAirportCursor(),
                new String[]{Database.Airports.AIRPORT_ID,
                        Database.Airports.CITY,
                        Database.Airports.ICAO,
                        Database.Airports.IATA,
                        Database.Airports.NAME,
                        Database.Airports.LATITUDE,
                        Database.Airports.LONGITUDE},
                new int[]{R.id.idListAirport,
                        R.id.cityListAirport,
                        R.id.icaoListAirport,
                        R.id.iataListAirport,
                        R.id.nameListAirport,
                        R.id.latListAirport,
                        R.id.lngListAirport,}, 0);

        lv.setAdapter(cursorAdapter);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor c = ((SimpleCursorAdapter) lv.getAdapter()).getCursor();
                c.moveToPosition(position);
                long ID = c.getLong(c.getColumnIndex(Database.Airports.AIRPORT_ID));

                Intent intentFlightActivity = new Intent(ListAirportActivity.this, FlightActivity.class);

                String routelallng = (c.getString(5)) + "," + c.getString(6);

                intentFlightActivity.putExtra("name", c.getString(1));
                intentFlightActivity.putExtra("routelallng", routelallng);

                setResult(RESULT_OK, intentFlightActivity);
                finish();
            }
        });
    }
}

