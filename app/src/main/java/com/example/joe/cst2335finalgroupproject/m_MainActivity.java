package com.example.joe.cst2335finalgroupproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class m_MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m_activity_main);


        //TODO: Add methods to add icons to toolbar, and tie each icon to launch the same activities below.

        //TODO: Do we want to have these buttons on the screen as well as the expandable toolbar?

        Button launchActivityTracker = findViewById(R.id.activityTrackerButton);
        launchActivityTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(m_MainActivity.this, a_ActivityTrackerActivity.class);
                startActivityForResult(intent, 10);
            }
        });

        Button launchNutritionTracker = findViewById(R.id.nutritionTrackerButton);
        launchNutritionTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(m_MainActivity.this, n_NutritionTrackerActivity.class);
                startActivityForResult(intent, 10);
            }
        });

        Button launchThermostatProgramActivity = findViewById(R.id.thermostatProgramButton);
        launchThermostatProgramActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(m_MainActivity.this, t_ThermostatProgramActivity.class);
                startActivityForResult(intent, 10);
            }
        });

        Button launchCarTrackerActivity = findViewById(R.id.carTrackerButton);
        launchCarTrackerActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(m_MainActivity.this, c_CarTrackerActivity.class);
                startActivityForResult(intent, 10);
            }
        });
    }


}