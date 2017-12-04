package com.example.joe.cst2335finalgroupproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Priorities for each Activity (suggested complete in this order in case Prof
 * pulls us for review with no warning.)
 * <p>
 * Milestone 1 (Must be demonstrated December 7th):
 * X 3.	Each Activity must have a ListView to present items. Selecting an item from the ListView must show detailed information about the item selected.
 * X 7.	Each activity must have at least 1 button
 * O 8.	Each activity must have at least 1 edit text with appropriate text input method.
 * Milestone 2 (Must be demonstrated December 14th):
 * O 2.	Each Activity must use a fragment in its graphical interface.
 * X 5.	Each activity must use an AsyncTask in the code. This can be to open a Database, retrieve data from a server, save data, or any other reasonable circumstance.
 * X 6.	Each activity must have at least 1 progress bar
 * ~ 9.	Each activity must have at least 1 Toast, Snackbar, and custom dialog notification.
 * <p>
 * Milestone 3 (Must be demonstrated December 21st):
 * ~ 1.  The software must have 1 different activity written by each person in your group. The activity must be accessible by selecting a graphical icon from a Toolbar.
 * ~ 4.  The items listed in the ListView must be stored by the application so that appear the next time the application is launched. The user must be able to add and delete items, which would then also be stored.
 * ~ 10.	A help menu item that displays a dialog with the author’s name, Activity version number, and instructions for how to use the interface.
 * O 11.	There must be at least 1 other language supported by your Activity. If you are not bilingual, then you must support both British and American English (words like colour, color, neighbour, neighbor, etc).
 * If you know a language other than English, then you can support that language in your application and don’t need to support American English.
 * <p>
 * Activity Specific Requirements:
 * •	Include an instruction window that the user can access from a menu on the navigation bar.
 * •	Users should be able to set a schedule for a thermostat. The user should be able to set rules for the temperature based on which day and time to set the temperature.  For example:
 * o	Monday 6:00 Temp -> 20
 * o	Monday 9:00 Temp -> 16
 * o	Wednesday 16:00 Temp -> 20
 * o	Wednesday 22:00 Temp -> 18
 * •	This should be displayed in a ListView. Clicking on an item from the list should show the details of the temperature rule, and let the user edit the rule and save it.
 * The user should also be allowed to edit the rule, and “save as new rule” instead of changing the selected rule. For example, clicking on “Monday 6:00 Temp -> 20” will show the day, time, and temperature.
 * If the user changes the day to Tuesday and clicks “save”, the rule will now apply to Tuesday instead of Monday. If the user changes the day to Tuesday and clicks “Save as new rule”,
 * a new rule “Tuesday 6:00 Temp -> 20” will be added, and the original “Monday 6:00 Temp -> 20” will still be in the list.
 */

public class ThermostatProgramActivity extends Activity {

    //TODO: 1.3/1.8/2.9 - Create window for rule popup - This will be a very similar popup dialog for add and "details" (ie tapping a row).  Window needs "Save as New Rule" button.  This is my custom dialog.
    //TODO: 2.5 - Write ASync for Database 1) reads and 2) writes
    //TODO: 2.9 - Add a Toast somewhere.  On ASync postexecute?
    //TODO: 2.9 - Add a Snackbar somewhere.
    //TODO: 3.10 - Help menu (snackbar? custom dialog?) shows author ane, activity version number?, and instructions
    //TODO: 3.11 - Add Translation

    //local ArrayList to handle the rules_list being read from the database and displayed on screen
    final ArrayList<String> rules_list = new ArrayList<>();
    //database references
    GlobalDatabaseHelper tdh;
    SQLiteDatabase db;
    //create handles to asyncTask objects (database interactions)
    asyncRead aRead;
    asyncWrite aWrite;
    //create handles to the objects on the screen
    ListView lv;
    Button addButton;
    Button editButton;

    ViewHolder vh = new ViewHolder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thermostat);

        lv = findViewById(R.id.rulesView);

        tdh = new GlobalDatabaseHelper(this);
        db = tdh.getWritableDatabase();

        aRead = new asyncRead();
        aWrite = new asyncWrite();

        addButton = findViewById(R.id.addButton);
        editButton = findViewById(R.id.editButton);

        aRead.execute();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //this is going to be called by the window created by clicking the "Add" button
                //set up a window with a day picker, a time picker, an edit text
                //remember to reset text to .setText("");
                String newRuleText = "test #" + String.valueOf(rules_list.size() + 1);
                rules_list.add(newRuleText);
                ContentValues newRule = new ContentValues();
                newRule.put(GlobalDatabaseHelper.RULE_COL_NAME, newRuleText);
                db.insert(GlobalDatabaseHelper.THERMOSTAT_TABLE_NAME, null, newRule);
                Log.i("Adding Dummy Rule", newRule.toString());
                //lv.notify();
            }
        });


        final RuleAdapter rulesAdapter = new RuleAdapter(this);
        lv.setAdapter(rulesAdapter);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rulesAdapter.toggleCheckboxesVisible();
                Log.i("checkboxes", "toggling");
            }
        });
    }

    //https://bhavyanshu.me/tutorials/create-custom-alert-dialog-in-android/08/20/2015
    public void showRuleEditDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.thermostat_edit_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText manualRuleEntryField = (EditText) dialogView.findViewById(R.id.ruleTextView);
        //manualRuleEntryField.setText(" ");


        final Spinner daySpinner = (Spinner) dialogView.findViewById(R.id.day_spinner);
        List<String> days = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.days_array)));
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, days);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(dayAdapter);
        daySpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //manualRuleEntryField.setText("");
                Toast.makeText(adapterView.getContext(), "OnItemSelectedListener : " + adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final Spinner hourSpinner = (Spinner) dialogView.findViewById(R.id.hour_spinner);
        Integer[] hours_array = new Integer[24];
        for (int i = 0; i < hours_array.length; i++) {
            hours_array[i] = i;
        }
        List<Integer> hours = new ArrayList<Integer>(Arrays.asList(hours_array));
        ArrayAdapter<Integer> hourAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, hours);
        hourAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hourSpinner.setAdapter(hourAdapter);
        hourSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //manualRuleEntryField.setText("");
                Toast.makeText(adapterView.getContext(), "OnItemSelectedListener : " + adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final Spinner minuteSpinner = (Spinner) dialogView.findViewById(R.id.minute_spinner);
        Integer[] minute_array = new Integer[60];
        for (int i = 0; i < minute_array.length; i++) {
            minute_array[i] = i;
        }
        List<Integer> minutes = new ArrayList<Integer>(Arrays.asList(minute_array));
        ArrayAdapter<Integer> minuteAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, minutes);
        minuteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        minuteSpinner.setAdapter(minuteAdapter);
        minuteSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //manualRuleEntryField.setText("");
                Toast.makeText(adapterView.getContext(), "OnItemSelectedListener : " + adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final Spinner tempSpinner = (Spinner) dialogView.findViewById(R.id.temp_spinner);
        Integer[] temp_array = new Integer[26];
        for (int i = 0; i < temp_array.length; i++) {
            temp_array[i] = i + 10;
        }
        List<Integer> temps = new ArrayList<Integer>(Arrays.asList(temp_array));
        ArrayAdapter<Integer> tempAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, temps);
        tempAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tempSpinner.setAdapter(tempAdapter);
        tempSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //manualRuleEntryField.setText("");
                Toast.makeText(adapterView.getContext(), "OnItemSelectedListener : " + adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //set on click or on focus to reset all spinners


        dialogBuilder.setTitle("Edit Rule");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
                Log.i("day spinner", String.valueOf(daySpinner.getSelectedItem()));
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    //https://stackoverflow.com/questions/20945528/android-hide-and-show-checkboxes-in-custome-listview-on-button-click
    class RuleAdapter extends ArrayAdapter<String> {

        RuleAdapter(Context ctx) {
            super(ctx, 0);
        }

        void setCheckboxesVisible() {
            vh.selected.setVisibility(View.VISIBLE);
        }

        void setCheckboxesInvisible() {
            vh.selected.setVisibility(View.INVISIBLE);
        }

        void toggleCheckboxesVisible() {
            if (vh.selected.getVisibility() == View.VISIBLE) {
                setCheckboxesInvisible();
            } else {
                setCheckboxesVisible();
            }
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public int getCount() {
            return rules_list.size();
        }

        public String getItem(int position) {
            return rules_list.get(position);
        }

        //TODO: make sure this is recycling correctly - Rule data is wrong on click and check
        @Override
        public View getView(int position, View recycled, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            View layout;

            layout = inflater.inflate(R.layout.rule_row, null);

            vh.ruleView = layout.findViewById(R.id.ruleTextView);
            vh.ruleView.setText(getItem(position));
            vh.ruleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("Rule Row Clicked", vh.ruleView.getText().toString());
                    showRuleEditDialog();
                }
            });
            vh.selected = layout.findViewById(R.id.ruleCheckbox);
            vh.selected.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (vh.selected.isChecked()) {
                        Log.i("Rule Checked", vh.ruleView.getText().toString());
                    } else {
                        Log.i("Rule Unchecked", vh.ruleView.getText().toString());
                    }
                }
            });

            return layout;
        }
    }

    private class asyncWrite extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... args) {

            return "Database Write Complete.";
        }
    }

    private class asyncRead extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... args) {

            Cursor c = db.query(false, GlobalDatabaseHelper.THERMOSTAT_TABLE_NAME, new String[]{GlobalDatabaseHelper.RULE_COL_NAME}, null, null, null, null, null, null);
            c.moveToFirst();
            while (!c.isAfterLast()) {
                String rule_text = c.getString(c.getColumnIndex(GlobalDatabaseHelper.RULE_COL_NAME));
                //Log.i("db contained", rule);
                rules_list.add(rule_text);
                c.moveToNext();
            }
            c.close();

            return "Database Read Complete.";
        }
    }

    class ViewHolder {
        TextView ruleView;
        CheckBox selected;
    }


}