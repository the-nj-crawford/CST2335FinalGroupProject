package com.example.joe.cst2335finalgroupproject;

import android.app.Activity;
import android.os.Bundle;

public class CarTrackerActivity extends Activity {

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
     •	Create an application that records buying gasoline for a car. The user can select the number of litres, price, and kilometers of the gasoline they purchased. Display the entries in a ListView. The database should store the time that the information was recorded.
     •	The user should be able to select items in the list view and edit them, or click a button to add a new purchase to the list.
     •	The application should provide information including the average price of gas for the last month, how much gasoline the user purchased last month, and how much gasoline the user purchases per month.
     *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);
    }
}