package com.example.joe.cst2335finalgroupproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class c_EditFuelDetailsActivity extends Activity {

    private EditText etEditPrice;
    private EditText etEditLitres;
    private EditText etEditKilometers;
    private EditText etEditDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_activity_edit_fuel_details);

        etEditPrice = findViewById(R.id.etEditPrice);
        etEditLitres = findViewById(R.id.etEditLitres);
        etEditKilometers = findViewById(R.id.etEditKilometers);
        etEditDate = findViewById(R.id.etEditDate);
        etEditDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c_DatePickerFragment datePicker = new c_DatePickerFragment();
                datePicker.setDisplay(etEditDate);
                datePicker.show(getFragmentManager(), "Date Picker");
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            Bundle fuelDetails = extras.getBundle("fuelDetails");
            double price = fuelDetails.getDouble("price");
            double litres = fuelDetails.getDouble("litres");
            double kilometers = fuelDetails.getDouble("kilometers");
            long longDate = fuelDetails.getLong("date");

            // move cursor to end of the EditText
            // references:
            // https://stackoverflow.com/questions/6217378/place-cursor-at-the-end-of-text-in-edittext
            etEditPrice.setText(String.valueOf(price));
            etEditPrice.setSelection(etEditPrice.getText().length());

            etEditLitres.setText(String.valueOf(litres));
            etEditLitres.setSelection(etEditLitres.getText().length());

            etEditKilometers.setText(String.valueOf(kilometers));
            etEditKilometers.setSelection(etEditKilometers.getText().length());

            etEditDate.setText(c_CarTrackerActivity.DD_MM_YYYY.format(longDate));
        }
    }
}
