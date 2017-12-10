package com.example.joe.cst2335finalgroupproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.Calendar;

// https://android--examples.blogspot.ca/2015/05/how-to-use-datepickerdialog-in-android.html
public class c_AddFuelDetailsActivity extends Activity {

    private EditText etAddPrice;
    private EditText etAddLitres;
    private EditText etAddKilometers;
    private EditText etAddDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_activity_add_fuel_details);

        etAddPrice = findViewById(R.id.etAddPrice);
        etAddLitres = findViewById(R.id.etAddLitres);
        etAddKilometers = findViewById(R.id.etAddKilometers);

        etAddDate = findViewById(R.id.etAddDate);
        etAddDate.setText(c_CarTrackerActivity.DD_MM_YYYY.format(Calendar.getInstance().getTime()));
        etAddDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c_DatePickerFragment datePicker = new c_DatePickerFragment();
                datePicker.setDisplay(etAddDate);
                datePicker.show(getFragmentManager(), "Date Picker");
            }
        });
    }
}
