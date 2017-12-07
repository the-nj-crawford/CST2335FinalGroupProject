package com.example.joe.cst2335finalgroupproject;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class c_CarTrackerActivity extends Activity {

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

    public static final DateFormat DD_MM_YYYY = new SimpleDateFormat("dd/MM/yyyy");

    public static final int ADD_DETAILS_REQUEST = 1;
    public static final int EDIT_DETAILS_REQUEST = 2;

    private ArrayList<c_FuelDetails> cFuelDetailsList;
    private ListView lvPurchaseHistory;
    private Button btnAddPurchase;
    private Button btnViewFuelStats;
    private FuelDetailsAdapter adapter;

    //private c_CarDatabaseHelper carDbHelper;
    private m_GlobalDatabaseHelper carDbHelper;
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_activity_car);

        cFuelDetailsList = new ArrayList<c_FuelDetails>();
        lvPurchaseHistory = findViewById(R.id.lvPurchaseHistory);
        adapter = new FuelDetailsAdapter(this);
        lvPurchaseHistory.setAdapter(adapter);

        //carDbHelper = new c_CarDatabaseHelper(this);
        //db = carDbHelper.getWritableDatabase();

        carDbHelper = new m_GlobalDatabaseHelper(this);
        db = carDbHelper.getWritableDatabase();

        testFillDB();
        fillFuelDetailsList();

        btnAddPurchase = findViewById(R.id.btnAddPurchase);
        btnAddPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(c_CarTrackerActivity.this,
                        c_AddFuelDetailsActivity.class);
                startActivity(intent);
            }
        });

        btnViewFuelStats = findViewById(R.id.btnViewFuelStats);
        btnViewFuelStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO - add stats activity
            }
        });

        lvPurchaseHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                c_FuelDetails details = cFuelDetailsList.get(i);
                Bundle extras = new Bundle();
                extras.putDouble("price", details.getPrice());
                extras.putDouble("litres", details.getLitres());
                extras.putDouble("kilometers", details.getKilometers());
                extras.putLong("date", details.getDate().getTime());

                Intent intent = new Intent(c_CarTrackerActivity.this, c_EditFuelDetailsActivity.class);
                intent.putExtra("fuelDetails", extras);
                startActivityForResult(intent, EDIT_DETAILS_REQUEST);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        db.close();
        carDbHelper.close();
    }

    private void testFillDB() {

        Date currentDate = Calendar.getInstance().getTime();

        c_FuelDetails[] testData = new c_FuelDetails[]{
                new c_FuelDetails(1.11, 1, 10, currentDate),
                new c_FuelDetails(2.22, 2, 20, currentDate),
                new c_FuelDetails(3.33, 3, 30, currentDate)
        };

        for (int i = 0, n = testData.length; i < n; i++) {
            ContentValues values = new ContentValues();
            values.put("price", testData[i].getPrice());
            values.put("litres", testData[i].getLitres());
            values.put("kilometers", testData[i].getKilometers());
            values.put("date", testData[i].getDate().getTime());
            db.insert(m_GlobalDatabaseHelper.FUEL_DETAILS_TABLE, null, values);
        }

        adapter.notifyDataSetChanged();
    }

    private void fillFuelDetailsList() {
        cursor = db.rawQuery(m_GlobalDatabaseHelper.SELECT_ALL_SQL, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            double price = cursor.getDouble(cursor.getColumnIndex(m_GlobalDatabaseHelper.KEY_PRICE));
            double litres = cursor.getDouble(cursor.getColumnIndex(m_GlobalDatabaseHelper.KEY_LITRES));
            double kilometers = cursor.getDouble(cursor.getColumnIndex(m_GlobalDatabaseHelper.KEY_KILOMETERS));
            long longDateRepresentation = cursor.getLong(cursor.getColumnIndex(m_GlobalDatabaseHelper.KEY_DATE));
            Date date = new Date(longDateRepresentation);

            c_FuelDetails details = new c_FuelDetails(price, litres, kilometers, date);
            cFuelDetailsList.add(details);

            cursor.moveToNext();
        }
    }

    private class FuelDetailsAdapter extends ArrayAdapter<c_FuelDetails> {

        public FuelDetailsAdapter(Context context) {
            super(context, 0);
        }

        @Override
        public int getCount() {
            return cFuelDetailsList.size();
        }

        @Override
        public c_FuelDetails getItem(int position) {
            return cFuelDetailsList.get(position);
        }

        @Override
        public long getItemId(int postion) {
            if (cursor == null)
                throw new NullPointerException("ERROR: cursor is null");

            cursor = db.rawQuery(m_GlobalDatabaseHelper.SELECT_ALL_SQL, null);
            cursor.moveToPosition(postion);
            return cursor.getLong(cursor.getColumnIndex(m_GlobalDatabaseHelper.KEY_ID));
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = c_CarTrackerActivity.this.getLayoutInflater();
            View view = inflater.inflate(R.layout.c_fuel_details_summary, null);

            c_FuelDetails details = getItem(position);

            TextView tvPrice = view.findViewById(R.id.tvPrice);
            tvPrice.setText(String.valueOf(details.getPrice()));

            TextView tvLitres = view.findViewById(R.id.tvLitres);
            tvLitres.setText(String.valueOf(details.getPrice()));

            TextView tvKilometers = view.findViewById(R.id.tvKilometers);
            tvKilometers.setText(String.valueOf(details.getPrice()));

            TextView tvDate = view.findViewById(R.id.tvDate);
            tvDate.setText(DD_MM_YYYY.format(details.getDate()));

            return view;
        }
    }
}