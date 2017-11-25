package com.example.joe.cst2335finalgroupproject;

import android.app.Activity;
import android.os.Bundle;

public class ThermostatProgramActivity extends Activity {

    /**
     * Priorities for each Activity (suggested complete in this order in case Prof
     * pulls us for review with no warning.)
     *
     * Milestone 1 (Must be demonstrated December 7th):
     *          3.	Each Activity must have a ListView to present items. Selecting an item from the ListView must show detailed information about the item selected.
     *          7.	Each activity must have at least 1 button
     *          8.	Each activity must have at least 1 edit text with appropriate text input method.
     * Milestone 2 (Must be demonstrated December 14th):
     *          2.	Each Activity must use a fragment in its graphical interface.
     *          5.	Each activity must use an AsyncTask in the code. This can be to open a Database, retrieve data from a server, save data, or any other reasonable circumstance.
     *          6.	Each activity must have at least 1 progress bar
     *          9.	Each activity must have at least 1 Toast, Snackbar, and custom dialog notification.
     *
     * Milestone 3 (Must be demonstrated December 21st):
     *          1.  The software must have 1 different activity written by each person in your group. The activity must be accessible by selecting a graphical icon from a Toolbar.
     *          4.  The items listed in the ListView must be stored by the application so that appear the next time the application is launched. The user must be able to add and delete items, which would then also be stored.
     *          10.	A help menu item that displays a dialog with the author’s name, Activity version number, and instructions for how to use the interface.
     *          11.	There must be at least 1 other language supported by your Activity. If you are not bilingual, then you must support both British and American English (words like colour, color, neighbour, neighbor, etc).
     *          If you know a language other than English, then you can support that language in your application and don’t need to support American English.
     *
     * Activity Specific Requirements:
     •	Include an instruction window that the user can access from a menu on the navigation bar.
     •	Users should be able to set a schedule for a thermostat. The user should be able to set rules for the temperature based on which day and time to set the temperature.  For example:
     o	Monday 6:00 Temp -> 20
     o	Monday 9:00 Temp -> 16
     o	Wednesday 16:00 Temp -> 20
     o	Wednesday 22:00 Temp -> 18
     •	This should be displayed in a ListView. Clicking on an item from the list should show the details of the temperature rule, and let the user edit the rule and save it.
     The user should also be allowed to edit the rule, and “save as new rule” instead of changing the selected rule. For example, clicking on “Monday 6:00 Temp -> 20” will show the day, time, and temperature.
     If the user changes the day to Tuesday and clicks “save”, the rule will now apply to Tuesday instead of Monday. If the user changes the day to Tuesday and clicks “Save as new rule”,
     a new rule “Tuesday 6:00 Temp -> 20” will be added, and the original “Monday 6:00 Temp -> 20” will still be in the list.
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thermostat);
    }
}