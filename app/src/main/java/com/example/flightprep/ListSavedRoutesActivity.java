package com.example.flightprep;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ListSavedRoutesActivity extends AppCompatActivity {

    ListView lv;
    DBHelperMain dbh = new DBHelperMain(this);
    SimpleCursorAdapter cursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_saved_routes);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Saved Routes");
        }

        lv = findViewById(R.id.listSavedRoutes);

        addCursorAdapter();

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.longpressmenu1, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Delete:
                Cursor c = ((SimpleCursorAdapter) lv.getAdapter()).getCursor();
                long ID = c.getLong(c.getColumnIndex(Database.SavedRoutes.SAVEDROUTE_ID));
                dbh.deleteSavedRoute(ID);
                addCursorAdapter();
                break;

        }
        return super.onContextItemSelected(item);
    }



 /*   private void loadData() {
        dbh.getSavedRoutesAsList();
    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intentFlightActivity = new Intent(ListSavedRoutesActivity.this, FlightActivity.class);
        setResult(RESULT_CANCELED, intentFlightActivity);
        finish();
    }

    private void addCursorAdapter() {
        cursorAdapter = new SimpleCursorAdapter(this,
                R.layout.mysavedrouteslayout,
                dbh.getSavedRoutesNameCursor(),
                new String[]{Database.SavedRoutes.SAVEDROUTE_ID,
                        Database.SavedRoutes.FROM,
                        Database.SavedRoutes.TO,
                        Database.SavedRoutes.STOP1,
                        Database.SavedRoutes.STOP2,
                        Database.SavedRoutes.STOP3,
                        Database.SavedRoutes.STOP4,
                        Database.SavedRoutes.STOP5,
                        Database.SavedRoutes.SPEED,
                        Database.SavedRoutes.ALTITUDE,
                        Database.SavedRoutes.DISTANCE,
                        Database.SavedRoutes.FROMGPS,
                        Database.SavedRoutes.TOGPS,
                        Database.SavedRoutes.STOP1GPS,
                        Database.SavedRoutes.STOP2GPS,
                        Database.SavedRoutes.STOP3GPS,
                        Database.SavedRoutes.STOP4GPS,
                        Database.SavedRoutes.STOP5GPS},
                new int[]{R.id.idSavedRoute,
                        R.id.fromSavedRoute,
                        R.id.toSavedRoute,
                        R.id.stop1SavedRoute,
                        R.id.stop2SavedRoute,
                        R.id.stop3SavedRoute,
                        R.id.stop4SavedRoute,
                        R.id.stop5SavedRoute,
                        R.id.speedSavedRoute,
                        R.id.altitudeSavedRoute,
                        R.id.distanceSavedRoute,
                        1,
                        2,
                        3,
                        4,
                        5,
                        6,
                        7,}, 0);

        lv.setAdapter(cursorAdapter);
        registerForContextMenu(lv);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor c = ((SimpleCursorAdapter) lv.getAdapter()).getCursor();
                c.moveToPosition(position);
                long ID = c.getLong(c.getColumnIndex(Database.SavedRoutes.SAVEDROUTE_ID));

                Intent intentFlightActivity = new Intent(ListSavedRoutesActivity.this, FlightActivity.class);


                String idr = (c.getString(0));
                String from = (c.getString(1));
                String to = (c.getString(2));
                String stop1 = (c.getString(3));
                String stop2 = (c.getString(4));
                String stop3 = (c.getString(5));
                String stop4 = (c.getString(6));
                String stop5 = (c.getString(7));
                String speed = (c.getString(8));
                String altitude = (c.getString(9));
                String distance = (c.getString(10));

                String fromgps = (c.getString(11));
                String togps = (c.getString(12));
                String stop1gps = (c.getString(13));
                String stop2gps = (c.getString(14));
                String stop3gps = (c.getString(15));
                String stop4gps = (c.getString(16));
                String stop5gps = (c.getString(17));


                intentFlightActivity.putExtra("idr", idr);
                intentFlightActivity.putExtra("from", from);
                intentFlightActivity.putExtra("to", to);
                intentFlightActivity.putExtra("stop1", stop1);
                intentFlightActivity.putExtra("stop2", stop2);
                intentFlightActivity.putExtra("stop3", stop3);
                intentFlightActivity.putExtra("stop4", stop4);
                intentFlightActivity.putExtra("stop5", stop5);
                intentFlightActivity.putExtra("speed", speed);
                intentFlightActivity.putExtra("altitude", altitude);


                intentFlightActivity.putExtra("fromgps", fromgps);
                intentFlightActivity.putExtra("togps", togps);
                intentFlightActivity.putExtra("stop1gps", stop1gps);
                intentFlightActivity.putExtra("stop2gps", stop2gps);
                intentFlightActivity.putExtra("stop3gps", stop3gps);
                intentFlightActivity.putExtra("stop4gps", stop4gps);
                intentFlightActivity.putExtra("stop5gps", stop5gps);

                setResult(RESULT_OK, intentFlightActivity);
                finish();
            }
        });
    }
}