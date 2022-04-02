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

public class MyRoutesActivity extends AppCompatActivity {

    DBHelperMain dbh = new DBHelperMain(this);
    SimpleCursorAdapter cursorAdapter;
    ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_routes);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("My Routes");
        }

        lv = findViewById(R.id.listMyRoutes);

        addCursorAdapter();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intentFlightActivity = new Intent(MyRoutesActivity.this, FlightActivity.class);
        setResult(RESULT_CANCELED, intentFlightActivity);
        finish();
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
                long ID = c.getLong(c.getColumnIndex(Database.MyRoutesData.ROUTEID));
                dbh.deleteMyRoutesData(ID);
                dbh.deleteMyRoutesLocations(ID);
                addCursorAdapter();
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void addCursorAdapter() {
        cursorAdapter = new SimpleCursorAdapter(this,
                R.layout.mymyrouteslayout,
                dbh.getMyRoutesDataCursor(),
                new String[]{Database.MyRoutesData.MYROUTESDATA_ID,
                        Database.MyRoutesData.ROUTEID,
                        Database.MyRoutesData.FROM,
                        Database.MyRoutesData.TO,
                        Database.MyRoutesData.STOP1,
                        Database.MyRoutesData.STOP2,
                        Database.MyRoutesData.STOP3,
                        Database.MyRoutesData.STOP4,
                        Database.MyRoutesData.STOP5,
                        Database.MyRoutesData.TIME,
                        Database.MyRoutesData.DISTANCE,
                        Database.MyRoutesData.SPEED},
                new int[]{R.id.idMyRoute,
                        1,
                        R.id.fromMyRoute,
                        R.id.toMyRoute,
                        R.id.stop1MyRoute,
                        R.id.stop2MyRoute,
                        R.id.stop3MyRoute,
                        R.id.stop4MyRoute,
                        R.id.stop5MyRoute,
                        R.id.timeMyRoute,
                        R.id.distanceMyRoute,
                        R.id.avgspeedMyRoute,}, 0);

        lv.setAdapter(cursorAdapter);
        registerForContextMenu(lv);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor c = ((SimpleCursorAdapter) lv.getAdapter()).getCursor();
                c.moveToPosition(position);
                long ID = c.getLong(c.getColumnIndex(Database.MyRoutesData.MYROUTESDATA_ID));

                String routeID = (c.getString(1));

                Intent intentShowMyRoute = new Intent(MyRoutesActivity.this, MapActivity.class);
                intentShowMyRoute.putExtra("routeID", routeID);
                startActivity(intentShowMyRoute);
            }
        });
    }
}

