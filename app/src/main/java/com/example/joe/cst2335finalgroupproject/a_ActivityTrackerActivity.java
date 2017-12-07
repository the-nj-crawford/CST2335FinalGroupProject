package com.example.joe.cst2335finalgroupproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class a_ActivityTrackerActivity extends Activity {

    /**
     * Milestone 1 (Must be demonstrated December 7th):
     * 3.	Each Activity must have a ListView to present items. Selecting an item from the ListView must show detailed information about the item selected.
     * 7.	Each activity must have at least 1 button
     * 8.	Each activity must have at least 1 edit text with appropriate text input method.
     * Milestone 2 (Must be demonstrated December 14th):
     * 2.	Each Activity must use a fragment in its graphical interface.
     * 5.	Each activity must use an AsyncTask in the code. This can be to open a Database, retrieve data from a server, save data, or any other reasonable circumstance.
     * 6.	Each activity must have at least 1 progress bar
     * 9.	Each activity must have at least 1 Toast, Snackbar, and custom dialog notification.
     *
     * Milestone 3 (Must be demonstrated December 21st):
     * 1.  The software must have 1 different activity written by each person in your group. The activity must be accessible by selecting a graphical icon from a Toolbar.
     * 4.  The items listed in the ListView must be stored by the application so that appear the next time the application is launched. The user must be able to add and delete items, which would then also be stored.
     * 10.	A help menu item that displays a dialog with the author’s name, Activity version number, and instructions for how to use the interface.
     * 11.	There must be at least 1 other language supported by your Activity. If you are not bilingual, then you must support both British and American English (words like colour, color, neighbour, neighbor, etc).
     * If you know a language other than English, then you can support that language in your application and don’t need to support American English.
     *
     * Activity Specific Requirements:
     * The user should be able to enter physical activities they did.
     * •	The user should be able to choose from 5 different activities: Running, Walking, Biking, Swimming or Skating.
     * •	The user should be able to enter the amount of time in minutes they did the activity.
     * •	The user should also be able to add comment, like “This felt easy”, or “My knee hurts after 10 minutes”. The database should track what time the activity was entered.
     * •	You should use fragments to show a ListView displays the activities, either ordered by time or as a daily summary of activities and time exercising. Users should be able to delete or edit previous entries, like changing the activity type or time spent, or change comments.
     * •	The application should calculate how many minutes of activity the user does per month, and also show how much activity the user did last month.
     */

    final ArrayList<a_TrackedActivity> activityList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_tracker_activity);
        Button addButton = findViewById(R.id.a_addButton);
        ListView activityListView = findViewById(R.id.a_listView);
        final ActivityAdapter aa = new ActivityAdapter(this);
        activityListView.setAdapter(aa);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a_TrackedActivity newAct = new a_TrackedActivity(1, "Running", 5, "easy", new Date());
                activityList.add(newAct);
                aa.notifyDataSetChanged();
            }
        });

        activityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(a_ActivityTrackerActivity.this, "This was activity number: " + position, Toast.LENGTH_SHORT).show();
                Intent detailsIntent = new Intent(a_ActivityTrackerActivity.this, a_DetailView.class);
                startActivity(detailsIntent);
            }
        });
    }

    private class ActivityAdapter extends ArrayAdapter<a_TrackedActivity> {
        public ActivityAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount() {
            return activityList.size();
        }

        public a_TrackedActivity getItem(int pos) {
            return activityList.get(pos);
        }

        public View getView(int pos, View convertView, ViewGroup parent) {
            LayoutInflater inflater = a_ActivityTrackerActivity.this.getLayoutInflater();
            View result = null;
            result = inflater.inflate(R.layout.a_activity_row, null);

            TextView activityTypeText = result.findViewById(R.id.a_TypeText);
            TextView durationText = result.findViewById(R.id.a_DurationText);
            TextView dateText = result.findViewById(R.id.a_DateText);

            a_TrackedActivity activityToDisplay = getItem(pos);
            activityTypeText.setText(activityToDisplay.getType());
            durationText.setText(Integer.toString(activityToDisplay.getDuration()));
            dateText.setText(activityToDisplay.getTimeStamp().toString());


            return result;
        }
    }
}