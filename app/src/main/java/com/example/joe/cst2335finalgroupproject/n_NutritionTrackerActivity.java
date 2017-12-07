package com.example.joe.cst2335finalgroupproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.joe.cst2335finalgroupproject.m_GlobalDatabaseHelper.NUTRITION_TABLE_NAME;

public class n_NutritionTrackerActivity extends Activity {

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
     •	Allow the user to enter nutritional information about food that they have eaten. It should include normal items that are found on a nutrition label: Calories, Total Fat, Total Carbohydrate. When the user enters the information, the database should also store the time and day that the user entered the information.
     •	You should use fragments to show a ListView displays the items eaten, either ordered by time or as a daily summary of total calories, fat and carbohydrates. Users should be able to delete or edit previous entries, like changing the number of calories or carbohydrates.
     •	The application should calculate the average calories eaten per day, and show how many calories were eaten in the last day that information was entered.
     *
     */

    protected final static String ACTIVITY_NAME = "NutritionTracker";
    Button newFoodButton;
    ListView nutritionListView;

    m_GlobalDatabaseHelper globalDatabaseHelper;
    SQLiteDatabase db;
    FoodAdapter listViewAdapter;

    ArrayList<String> foodArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(ACTIVITY_NAME, "In onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n_activity_nutrition);
        newFoodButton = findViewById(R.id.nutrition_add_new_food);
        nutritionListView = findViewById(R.id.nutrition_listview);

        // READ IN DB

        globalDatabaseHelper = new m_GlobalDatabaseHelper(this);
        db = globalDatabaseHelper.getWritableDatabase();
        listViewAdapter = new FoodAdapter(this);
        nutritionListView.setAdapter(listViewAdapter);

        Cursor cursor = db.query(NUTRITION_TABLE_NAME, new String[]{m_GlobalDatabaseHelper.FOOD_ITEM_COL_NAME}, null, null, null, null, null);
        int colIndex = cursor.getColumnIndex(m_GlobalDatabaseHelper.FOOD_ITEM_COL_NAME);

        Cursor c = db.rawQuery("SELECT  * FROM " + NUTRITION_TABLE_NAME + ";", null);
        int cnt = c.getCount();
        if (cnt > 0) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                String value = cursor.getString(colIndex);
                foodArrayList.add(value);
                listViewAdapter.notifyDataSetChanged();
            }
            c.close();
        }

        // ADD NEW FOOD ITEM

        newFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(n_NutritionTrackerActivity.this);
                final View logView = getLayoutInflater().inflate(R.layout.n_activity_nutrition_dialog, null);

                builder.setView(logView);
                final AlertDialog dialog = builder.create();
                final EditText nameField = logView.findViewById(R.id.nutrition_name_field);

                Button submitNewActivity = logView.findViewById(R.id.nutrition_log_new_button);
                Button cancelNewActivity = logView.findViewById(R.id.nutrition_cancel);

                // VALIDATE ENTRY

                submitNewActivity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (nameField.getText().toString().length() < 1) {

                            // INVALID ENTRY. MUST ADD TITLE


                            final AlertDialog validateDialog = new AlertDialog.Builder(n_NutritionTrackerActivity.this).create();
                            validateDialog.setTitle("Please try again.");
                            validateDialog.setMessage("You must enter a food name/description.");
                            validateDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            validateDialog.dismiss();
                                        }
                                    });
                            validateDialog.show();

                        } else {

                            // VALID ENTRY. ADD TO DB

                            EditText namefield = logView.findViewById(R.id.nutrition_name_field);
                            EditText caloriesfield = logView.findViewById(R.id.nutrition_calories_field);
                            EditText carbsfield = logView.findViewById(R.id.nutrition_carbs_field);
                            EditText fatfield = logView.findViewById(R.id.nutrition_fat_field);

                            String itemName = namefield.getText().toString();
                            String itemCalories = caloriesfield.getText().toString();
                            String itemCarbs = carbsfield.getText().toString();
                            String itemFat = fatfield.getText().toString();

                            ContentValues values = new ContentValues();
                            values.put(m_GlobalDatabaseHelper.FOOD_ITEM_COL_NAME, itemName);
                            values.put(m_GlobalDatabaseHelper.CALORIES_COL_NAME, itemCalories);
                            values.put(m_GlobalDatabaseHelper.CARB_COL_NAME, itemCarbs);
                            values.put(m_GlobalDatabaseHelper.FAT_COL_NAME, itemFat);

                            db.insert(NUTRITION_TABLE_NAME, null, values);

                            // UPDATE THE LISTVIEW

                            Cursor cursor = db.query(NUTRITION_TABLE_NAME, new String[]{m_GlobalDatabaseHelper.FOOD_ITEM_COL_NAME}, null, null, null, null, null);
                            int colIndex = cursor.getColumnIndex(m_GlobalDatabaseHelper.FOOD_ITEM_COL_NAME);

                            Cursor c = db.rawQuery("SELECT  * FROM " + NUTRITION_TABLE_NAME + ";", null);
                            int cnt = c.getCount();
                            if (cnt > 0) {
                                for (cursor.moveToLast(); !cursor.isAfterLast(); cursor.moveToNext()) {
                                    String value = cursor.getString(colIndex);
                                    foodArrayList.add(value);
                                }
                                c.close();
                            }

                            // CLOSE DIALOG AFTER UPDATING DB AND LISTVIEW

                            dialog.dismiss();
                        }
                    }
                });

                cancelNewActivity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        // TODO: ONCLICKLISTENER ON LISTVIEW ITEMS OPENING n_activity_nutrition_details DIALOG WITH READ INFO
        //I copied the "new" dialog from above.  It can be retooled to read the item that was clicked and load it's properites into this window
        nutritionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(n_NutritionTrackerActivity.this);
                final View logView = getLayoutInflater().inflate(R.layout.n_activity_nutrition_dialog, null);

                builder.setView(logView);
                final AlertDialog dialog = builder.create();
                final EditText nameField = logView.findViewById(R.id.nutrition_name_field);

                Button submitNewActivity = logView.findViewById(R.id.nutrition_log_new_button);
                Button cancelNewActivity = logView.findViewById(R.id.nutrition_cancel);

                // VALIDATE ENTRY

                submitNewActivity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (nameField.getText().toString().length() < 1) {

                            // INVALID ENTRY. MUST ADD TITLE


                            final AlertDialog validateDialog = new AlertDialog.Builder(n_NutritionTrackerActivity.this).create();
                            validateDialog.setTitle("Please try again.");
                            validateDialog.setMessage("You must enter a food name/description.");
                            validateDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            validateDialog.dismiss();
                                        }
                                    });
                            validateDialog.show();

                        } else {

                            // VALID ENTRY. ADD TO DB

                            EditText namefield = logView.findViewById(R.id.nutrition_name_field);
                            EditText caloriesfield = logView.findViewById(R.id.nutrition_calories_field);
                            EditText carbsfield = logView.findViewById(R.id.nutrition_carbs_field);
                            EditText fatfield = logView.findViewById(R.id.nutrition_fat_field);

                            String itemName = namefield.getText().toString();
                            String itemCalories = caloriesfield.getText().toString();
                            String itemCarbs = carbsfield.getText().toString();
                            String itemFat = fatfield.getText().toString();

                            ContentValues values = new ContentValues();
                            values.put(m_GlobalDatabaseHelper.FOOD_ITEM_COL_NAME, itemName);
                            values.put(m_GlobalDatabaseHelper.CALORIES_COL_NAME, itemCalories);
                            values.put(m_GlobalDatabaseHelper.CARB_COL_NAME, itemCarbs);
                            values.put(m_GlobalDatabaseHelper.FAT_COL_NAME, itemFat);

                            db.insert(NUTRITION_TABLE_NAME, null, values);

                            // UPDATE THE LISTVIEW

                            Cursor cursor = db.query(NUTRITION_TABLE_NAME, new String[]{m_GlobalDatabaseHelper.FOOD_ITEM_COL_NAME}, null, null, null, null, null);
                            int colIndex = cursor.getColumnIndex(m_GlobalDatabaseHelper.FOOD_ITEM_COL_NAME);

                            Cursor c = db.rawQuery("SELECT  * FROM " + NUTRITION_TABLE_NAME + ";", null);
                            int cnt = c.getCount();
                            if (cnt > 0) {
                                for (cursor.moveToLast(); !cursor.isAfterLast(); cursor.moveToNext()) {
                                    String value = cursor.getString(colIndex);
                                    foodArrayList.add(value);
                                }
                                c.close();
                            }

                            // CLOSE DIALOG AFTER UPDATING DB AND LISTVIEW

                            dialog.dismiss();
                        }
                    }
                });

                cancelNewActivity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }


        });
    }

    public void onDestroy() {
        db.close();
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }

    protected class FoodAdapter extends ArrayAdapter<String> {
        public FoodAdapter(Context c) {
            super(c, 0);
        }

        public int getCount() {
            return foodArrayList.size();
        }

        public String getItem(int position) {
            return foodArrayList.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = n_NutritionTrackerActivity.this.getLayoutInflater();
            View result = inflater.inflate(R.layout.n_activity_nutrition_list, null);
            TextView message = result.findViewById(R.id.message_text);
            message.setText(getItem(position));
            return result;
        }
    }
}
