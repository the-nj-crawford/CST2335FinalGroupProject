package com.example.joe.cst2335finalgroupproject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

/**
 * Created by Nathan on 2017-12-06.
 */

public class c_DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private Activity parentActivity;
    private EditText display;
    private Calendar calendar = Calendar.getInstance();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(parentActivity, this, year, month, day);

        // https://stackoverflow.com/questions/4854492/setting-width-to-wrap-content-for-textview-through-code
        // https://stackoverflow.com/questions/3060619/how-to-get-the-visible-size-on-an-activity

        int newHeight = (int) (parentActivity.getWindow().getDecorView().getHeight() * 0.65);
        int newWidth = (int) (parentActivity.getWindow().getDecorView().getWidth() * 0.75);
        float newScaleX = 1.5f;
        float newScaleY = 1.6f;
        float translationFactorY = 0.2857f;

        DatePicker datePicker = datePickerDialog.getDatePicker();
        datePicker.setTranslationY((int) (translationFactorY * newHeight));

        // increase the size of the DatePicker pop up
        datePicker.setMinimumHeight(newHeight);
        datePicker.setMinimumWidth(newWidth);

        // blow up the DatePicker contents so they fill the pop up window
        datePicker.setScaleX(newScaleX);
        datePicker.setScaleY(newScaleY);

        return datePickerDialog;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.parentActivity = activity;
    }

    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        display.setText(c_CarTrackerActivity.DD_MM_YYYY.format(calendar.getTime()));
    }

    public void setDisplay(EditText editText) {
        this.display = editText;
    }
}
