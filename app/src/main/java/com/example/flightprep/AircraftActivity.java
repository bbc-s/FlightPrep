package com.example.flightprep;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class AircraftActivity extends AppCompatActivity {

    ListView lv;
    DBHelperMain dbh = new DBHelperMain(this);
    SimpleCursorAdapter cursorAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aircraft);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Aircrafts");
        }

        lv = findViewById(R.id.listAircrafts);


        addCursorAdapter();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actiontoolbar, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.longpressmenu1, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuAdd) {
        switch (menuAdd.getItemId()) {
            case R.id.menuAdd:
                Intent intentAddAircraft = new Intent(AircraftActivity.this, AddAircraftActivity.class);
                startActivityForResult(intentAddAircraft, R.id.menuAdd);
                return true;
            default:
                return super.onOptionsItemSelected(menuAdd);
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Delete:
                Cursor c = ((SimpleCursorAdapter) lv.getAdapter()).getCursor();
                long ID = c.getLong(c.getColumnIndex(Database.Aircrafts.AIRCRAFT_ID));
                dbh.deleteAircraft(ID);
                addCursorAdapter();
                break;

        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case R.id.menuAdd:
                if (resultCode == RESULT_OK) {
                    addCursorAdapter();
                }
                break;
        }
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
        registerForContextMenu(lv);

    }

}