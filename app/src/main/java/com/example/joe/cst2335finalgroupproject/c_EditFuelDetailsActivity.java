package com.example.joe.cst2335finalgroupproject;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

public class c_EditFuelDetailsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_activity_edit_fuel_details);

        Bundle fragmentDetails = getIntent().getExtras();
        if (fragmentDetails != null){

            // add title of the fragment and button text
            fragmentDetails.putString("btnText", getResources().getString(R.string.c_BtnSaveDetails));
            fragmentDetails.putString("fragmentTitle", getResources().getString(R.string.c_EditDetailsTitle));

            c_EnterFuelDetailsFragment loadedFragment = new c_EnterFuelDetailsFragment();
            loadedFragment.setArguments(fragmentDetails);

            getFragmentManager().beginTransaction().
                    add(R.id.flEditDetails, loadedFragment).commit();
        }
    }

    public void updateFuelDetail(Bundle fuelDetails){
        Intent resultIntent = new Intent();
        resultIntent.putExtra("fuelDetails", fuelDetails);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}
