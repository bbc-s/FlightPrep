package com.example.flightprep;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

public class StatsActivity extends AppCompatActivity {

    DBHelperMain dbh = new DBHelperMain(this);
    TextView txtTime, txtSpeed, txtDistance, txtMin, txtMax, txtAvg, txtTotal;
    TextView minTime, minSpeed, minDistance;
    TextView maxTime, maxSpeed, maxDistance;
    TextView avgTime, avgSpeed, avgDistance;
    TextView totalTime, totalSpeed, totalDistance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Stats");
        }

        loadTxts();
        loadData();

    }

    private void loadTxts() {
        txtMin = findViewById(R.id.txtMin);
        txtMax = findViewById(R.id.txtMax);
        txtAvg = findViewById(R.id.txtAvg);
        txtTotal = findViewById(R.id.txtTotal);

        txtTime = findViewById(R.id.txtTime);
        minTime = findViewById(R.id.minTime);
        maxTime = findViewById(R.id.maxTime);
        avgTime = findViewById(R.id.avgTime);
        totalTime = findViewById(R.id.totalTime);

        txtSpeed = findViewById(R.id.txtSpeed);
        minSpeed = findViewById(R.id.minSpeed);
        maxSpeed = findViewById(R.id.maxSpeed);
        avgSpeed = findViewById(R.id.avgSpeed);
        totalSpeed = findViewById(R.id.totalSpeed);

        txtDistance = findViewById(R.id.txtDistance);
        minDistance = findViewById(R.id.minDistance);
        maxDistance = findViewById(R.id.maxDistance);
        avgDistance = findViewById(R.id.avgDistance);
        totalDistance = findViewById(R.id.totalDistance);

    }

    @SuppressLint("DefaultLocale")
    private void loadData() {
        Cursor cursor = dbh.getStats();
        if (cursor.getString(0) != null && cursor.getString(1) != null && cursor.getString(2) != null && cursor.getString(3) != null) {
            String STRminTime = (String.format("%dh %dm",
                    TimeUnit.SECONDS.toHours(Long.parseLong(cursor.getString(0))),
                    TimeUnit.SECONDS.toMinutes(Long.parseLong(cursor.getString(0))) - TimeUnit.HOURS.toMinutes(TimeUnit.SECONDS.toHours(Long.parseLong(cursor.getString(0))))));
            String STRmaxTime = (String.format("%dh %dm",
                    TimeUnit.SECONDS.toHours(Long.parseLong(cursor.getString(1))),
                    TimeUnit.SECONDS.toMinutes(Long.parseLong(cursor.getString(1))) - TimeUnit.HOURS.toMinutes(TimeUnit.SECONDS.toHours(Long.parseLong(cursor.getString(1))))));
            String STRavgTime = (String.format("%dh %dm",
                    TimeUnit.SECONDS.toHours(Long.parseLong(cursor.getString(2))),
                    TimeUnit.SECONDS.toMinutes(Long.parseLong(cursor.getString(2))) - TimeUnit.HOURS.toMinutes(TimeUnit.SECONDS.toHours(Long.parseLong(cursor.getString(2))))));
            String STRtotalTime = (String.format("%dh %dm",
                    TimeUnit.SECONDS.toHours(Long.parseLong(cursor.getString(3))),
                    TimeUnit.SECONDS.toMinutes(Long.parseLong(cursor.getString(3))) - TimeUnit.HOURS.toMinutes(TimeUnit.SECONDS.toHours(Long.parseLong(cursor.getString(3))))));

            String STRminDistance = String.format("%.02f", Double.parseDouble(cursor.getString(4)) / 1000) + " km";
            String STRmaxDistance = String.format("%.02f", Double.parseDouble(cursor.getString(5)) / 1000) + " km";
            String STRavgDistance = String.format("%.02f", Double.parseDouble(cursor.getString(6)) / 1000) + " km";
            String STRtotalDistance = String.format("%.02f", Double.parseDouble(cursor.getString(7)) / 1000) + " km";

            String STRminSpeed = String.format("%.2f", Double.parseDouble(cursor.getString(8))) + " kt";
            String STRmaxSpeed = String.format("%.2f", Double.parseDouble(cursor.getString(9))) + " kt";
            String STRavgSpeed = String.format("%.2f", Double.parseDouble(cursor.getString(10))) + " kt";


            minTime.setText(STRminTime);
            maxTime.setText(STRmaxTime);
            avgTime.setText(STRavgTime);
            totalTime.setText(STRtotalTime);

            minDistance.setText(STRminDistance);
            maxDistance.setText(STRmaxDistance);
            avgDistance.setText(STRavgDistance);
            totalDistance.setText(STRtotalDistance);

            minSpeed.setText(STRminSpeed);
            maxSpeed.setText(STRmaxSpeed);
            avgSpeed.setText(STRavgSpeed);

        }
    }
}