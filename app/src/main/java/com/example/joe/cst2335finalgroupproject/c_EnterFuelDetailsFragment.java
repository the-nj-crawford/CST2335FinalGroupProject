package com.example.joe.cst2335finalgroupproject;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by Nathan on 2017-12-09.
 */

public class c_EnterFuelDetailsFragment extends Fragment {

    private Activity callingActivity;
    Bundle fuelDetails;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){

        View view = inflater.inflate(R.layout.c_enter_fuel_details_fragment, container, false);

        final TextView enterFuelDetailsTitle = view.findViewById(R.id.enterFuelDetailsTitle);
        final EditText etPrice = view.findViewById(R.id.etPrice);
        final EditText etLitres = view.findViewById(R.id.etLitres);
        final EditText etKilometers = view.findViewById(R.id.etKilometers);
        final EditText etDate = view.findViewById(R.id.etDate);
        final Button btnEnterDetails = view.findViewById(R.id.btnEnterDetails);

        Bundle fragmentDetails = getArguments();

        if (fragmentDetails != null){
            String title = fragmentDetails.getString("fragmentTitle");
            String btnText = fragmentDetails.getString("btnText");

            enterFuelDetailsTitle.setText(title);
            btnEnterDetails.setText(btnText);

            fuelDetails = fragmentDetails.getBundle("fuelDetails");

            // There will be no fuel details if this fragment is used for adding a new set of
            //   fuel details
            if (fuelDetails != null){
                double price = fuelDetails.getDouble("price");
                double litres = fuelDetails.getDouble("litres");
                double kilometers = fuelDetails.getDouble("kilometers");
                long longDate = fuelDetails.getLong("date");

                etPrice.setText(String.valueOf(price));
                etLitres.setText(String.valueOf(litres));
                etKilometers.setText(String.valueOf(kilometers));
                etDate.setText(String.valueOf(c_CarTrackerActivity.DD_MM_YYYY.format(longDate)));
            }
        }

        btnEnterDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO handle blank data entered
                try {
                    // get values in EditText fields
                    double price = Double.parseDouble(etPrice.getText().toString());
                    double litres = Double.parseDouble(etLitres.getText().toString());
                    double kilometers = Double.parseDouble(etKilometers.getText().toString());
                    String stringDate = etDate.getText().toString();
                    Date date =  c_CarTrackerActivity.DD_MM_YYYY.parse(stringDate);

                    // update the fuel details bundle
                    fuelDetails.putDouble("price", price);
                    fuelDetails.putDouble("litres", litres);
                    fuelDetails.putDouble("kilometers", kilometers);
                    fuelDetails.putLong("date", date.getTime());

                    if (fuelDetails != null){
                        switch(callingActivity.getLocalClassName()){
                            case "c_EditFuelDetailsActivity":
                                ((c_EditFuelDetailsActivity)callingActivity).updateFuelDetail(fuelDetails);
                                break;
                            case "c_AddFuelDetailsActivity":
                                break;
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c_DatePickerFragment datePicker = new c_DatePickerFragment();
                datePicker.setDisplay(etDate);
                datePicker.show(getFragmentManager(), "Date Picker");
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        this.callingActivity = activity;
    }
}
