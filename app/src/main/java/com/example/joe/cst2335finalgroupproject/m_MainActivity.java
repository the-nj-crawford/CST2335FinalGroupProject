package com.example.joe.cst2335finalgroupproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class m_MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m_activity_main);

        final Toolbar m_toolbar = findViewById(R.id.m_toolbar);
        setSupportActionBar(m_toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.m_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        switch(menuItem.getItemId()){

            case R.id.menu_exercise:
                startActivity(new Intent(m_MainActivity.this, a_ActivityTrackerActivity.class));
                break;

            case R.id.menu_food:
                startActivity(new Intent(m_MainActivity.this, n_NutritionTrackerActivity.class));
                break;

            case R.id.menu_thermostat:
                startActivity(new Intent(m_MainActivity.this, t_ThermostatProgramActivity.class));
                break;

            case R.id.menu_automobile:
                startActivity(new Intent(m_MainActivity.this, c_CarTrackerActivity.class));
                break;
        }
        return true;
    }
}