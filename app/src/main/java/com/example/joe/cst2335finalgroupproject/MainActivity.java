package com.example.joe.cst2335finalgroupproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //TODO: Add methods to add icons to toolbar, and tie each icon to launch the same activities below.

        //TODO: Do we want to have these buttons on the screen as well as the expandable toolbar?

        Button launchActivityTracker = (Button) findViewById(R.id.activityTrackerButton);
        launchActivityTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivityTrackerActivity.class);
                startActivityForResult(intent, 10);
            }
        });

        Button launchNutritionTracker = (Button) findViewById(R.id.nutritionTrackerButton);
        launchNutritionTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NutritionTrackerActivity.class);
                startActivityForResult(intent, 10);
            }
        });

        Button launchThermostatProgramActivity = (Button) findViewById(R.id.thermostatProgramButton);
        launchThermostatProgramActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ThermostatProgramActivity.class);
                startActivityForResult(intent, 10);
            }
        });

        Button launchCarTrackerActivity = (Button) findViewById(R.id.carTrackerButton);
        launchCarTrackerActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CarTrackerActivity.class);
                startActivityForResult(intent, 10);
            }
        });
    }


}