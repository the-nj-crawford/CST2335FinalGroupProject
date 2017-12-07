package com.example.joe.cst2335finalgroupproject;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Milestone 1 (Must be demonstrated December 7th):
 * X 3.	Each Activity must have a ListView to present items. Selecting an item from the ListView must show detailed information about the item selected.
 * X 7.	Each activity must have at least 1 button
 * X 8.	Each activity must have at least 1 edit text with appropriate text input method.
 *
 * Milestone 2 (Must be demonstrated December 14th):
 * O 2.	Each Activity must use a fragment in its graphical interface.
 * X 5.	Each activity must use an AsyncTask in the code. This can be to open a Database, retrieve data from a server, save data, or any other reasonable circumstance.
 * X 6.	Each activity must have at least 1 progress bar
 * O 9.	Each activity must have at least 1 Toast, Snackbar, and custom dialog notification.
 *
 * Milestone 3 (Must be demonstrated December 21st):
 * ~ 1.  The software must have 1 different activity written by each person in your group. The activity must be accessible by selecting a graphical icon from a Toolbar.
 * ~ 4.  The items listed in the ListView must be stored by the application so that appear the next time the application is launched. The user must be able to add and delete items, which would then also be stored.
 * ~ 10.	A help menu item that displays a dialog with the author’s name, Activity version number, and instructions for how to use the interface.
 * O 11.	There must be at least 1 other language supported by your Activity. If you are not bilingual, then you must support both British and American English (words like colour, color, neighbour, neighbor, etc).
 * If you know a language other than English, then you can support that language in your application and don’t need to support American English.
 *
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

public class t_ThermostatProgramActivity extends Activity {

    //TODO: 1.3/1.8/2.9 - Create window for rule popup - This will be a very similar popup dialog for add and "details" (ie tapping a row).  Window needs "Save as New Rule" button.  This is my custom dialog.
    //TODO: 2.5 - Write ASync for Database 1) reads and 2) writes
    //TODO: 2.9 - Add a Toast somewhere.  On ASync postexecute?
    //TODO: 2.9 - Add a Snackbar somewhere.
    //TODO: 3.10 - Help menu (snackbar? custom dialog?) shows author ane, activity version number?, and instructions
    //TODO: 3.11 - Add Translation

    //local ArrayList to handle the rules_list being read from the database and displayed on screen
    final ArrayList<String> rules_list = new ArrayList<>();

    //database references
    m_GlobalDatabaseHelper tdh;
    SQLiteDatabase db;
    Cursor c;

    //create handles to asyncTask objects (database interactions)
    asyncRead aRead;
    asyncWrite aWrite;

    //create handles to the objects on the screen
    ListView lv;
    Button addButton;
    Button editButton;
    ProgressBar pb;

    ViewHolder vh = new ViewHolder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t_activity_program_thermostat);

        lv = findViewById(R.id.rulesView);

        tdh = new m_GlobalDatabaseHelper(this);
        db = tdh.getWritableDatabase();

        aRead = new asyncRead();
        aWrite = new asyncWrite();

        pb = findViewById(R.id.progressBar);
        addButton = findViewById(R.id.addButton);
        editButton = findViewById(R.id.editButton);

        aRead.execute();

        final RuleAdapter rulesAdapter = new RuleAdapter(this);
        lv.setAdapter(rulesAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //this is going to be called by the window created by clicking the "Add" button
                //set up a window with a day picker, a time picker, an edit text
                //remember to reset text to .setText("");
                String newRuleText = "test #" + String.valueOf(rules_list.size() + 1);
                rules_list.add(newRuleText);
                ContentValues newRule = new ContentValues();
                newRule.put(m_GlobalDatabaseHelper.RULE_COL_NAME, newRuleText);
                db.insert(m_GlobalDatabaseHelper.THERMOSTAT_TABLE_NAME, null, newRule);
                Log.i("Adding Dummy Rule", newRule.toString());
                //lv.notify();
                rulesAdapter.notifyDataSetChanged();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleCheckboxesVisible();
                Log.i("checkboxes", "toggling");
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("Rule Row Clicked", rules_list.get(position));
                Intent detailsIntent = new Intent(t_ThermostatProgramActivity.this, t_DetailView.class);

                detailsIntent.putExtra("rule", rules_list.get(position));

                startActivity(detailsIntent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
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

    static class ViewHolder {
        TextView ruleView;
        CheckBox selected;
    }

    //https://stackoverflow.com/questions/20945528/android-hide-and-show-checkboxes-in-custome-listview-on-button-click
    class RuleAdapter extends ArrayAdapter<String> {

        RuleAdapter(Context ctx) {
            super(ctx, 0);
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

            layout = inflater.inflate(R.layout.t_rule_row, null);

            vh.ruleView = layout.findViewById(R.id.ruleTextView);
            vh.ruleView.setText(getItem(position));

            vh.selected = layout.findViewById(R.id.ruleCheckbox);

            layout.setTag(vh);

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

            c = db.query(false, m_GlobalDatabaseHelper.THERMOSTAT_TABLE_NAME, new String[]{m_GlobalDatabaseHelper.RULE_COL_NAME}, null, null, null, null, null, null);
            c.moveToFirst();
            while (!c.isAfterLast()) {
                String rule_text = c.getString(c.getColumnIndex(m_GlobalDatabaseHelper.RULE_COL_NAME));
                //Log.i("db contained", rule);
                rules_list.add(rule_text);
                c.moveToNext();
            }
            c.close();

            for (int i = 0; i < 100; i++) {
                publishProgress(i);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return "Database Read Complete.";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            pb.setProgress(values[0]);
        }
    }
}