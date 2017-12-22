package com.example.joe.cst2335finalgroupproject;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class n_NutritionTrackerActivity extends AppCompatActivity {

    protected final static String ACTIVITY_NAME = "NutritionTracker";
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
        nutritionListView = findViewById(R.id.nutrition_listview);
        globalDatabaseHelper = new m_GlobalDatabaseHelper(this);
        db = globalDatabaseHelper.getWritableDatabase();
        listViewAdapter = new FoodAdapter(this);
        nutritionListView.setAdapter(listViewAdapter);

        AddItemQuery populateList = new AddItemQuery();
        populateList.execute();

        Toolbar myToolbar = findViewById(R.id.appToolbar);
        setSupportActionBar(myToolbar);


        // FRAGMENT PREAMBLE -----------------------------------------------------------------------

        boolean frameLayoutExists = false;

        if (findViewById(R.id.wideScreenFrameLayout) != null) {
            frameLayoutExists = true;
            Log.i(ACTIVITY_NAME, "Tablet - FrameLayout is visible.");
        } else {
            Log.i(ACTIVITY_NAME, "Under 600dp - FrameLayout is NOT visible.");
        }

        final boolean frameLayoutExistsFinal = frameLayoutExists;


        // LISTVIEW ONCLICK ------------------------------------------------------------------------

        nutritionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(ACTIVITY_NAME, "ListView was clicked");

                final int foodPosition = position + 1;

                Cursor cursor = db.query(m_GlobalDatabaseHelper.NUTRITION_TABLE_NAME, new String[]{m_GlobalDatabaseHelper.FOOD_ID,
                                m_GlobalDatabaseHelper.FOOD_ITEM_COL_NAME, m_GlobalDatabaseHelper.CALORIES_COL_NAME, m_GlobalDatabaseHelper.CARB_COL_NAME, m_GlobalDatabaseHelper.FAT_COL_NAME}, m_GlobalDatabaseHelper.FOOD_ID + "=?",
                        new String[]{String.valueOf(foodPosition)}, null, null, null, null);

                if (cursor != null)
                    cursor.moveToFirst();

                Bundle bundle = new Bundle();

                bundle.putString("id", cursor.getString(0));

                bundle.putString("item", cursor.getString(1));

                if (!cursor.getString(2).isEmpty()) {
                    bundle.putString("calories", cursor.getString(2));
                } else {
                    bundle.putString("calories", "");
                }
                if (!cursor.getString(3).isEmpty()) {
                    bundle.putString("carbs", cursor.getString(3));
                } else {
                    bundle.putString("carbs", "");
                }
                if (!cursor.getString(4).isEmpty()) {
                    bundle.putString("fat", cursor.getString(4));
                } else {
                    bundle.putString("fat", "");
                }

                bundle.putString("comment", ""); // DELETE ME - SEE BELOW
                /*
                if (!cursor.getString(5).isEmpty()) {
                    bundle.putString("comment", cursor.getString(5));
                } else {
                    bundle.putString("comment", cursor.getString(5));
                }*/

                if (!frameLayoutExistsFinal) {
                    n_NutritionFragment fragment = new n_NutritionFragment();
                    fragment.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.myRelativeLayout, fragment).commit();
                } else {
                    n_NutritionFragment nutritionFragment = new n_NutritionFragment();
                    nutritionFragment.setArguments(bundle);
                    getFragmentManager().beginTransaction()
                            .replace(R.id.wideScreenFrameLayout, nutritionFragment).commit();
                }
            }
        });
    }


    // TOOLBAR -------------------------------------------------------------------------------------

    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.n_nutrition_toolbar, m);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi) {
        switch (mi.getItemId()) {

            case R.id.n_action_return:
                Toast.makeText(this, "Version 1.0 by Neil Gagne", Toast.LENGTH_LONG).show();
                break;

            case R.id.n_action_rotate:
                Toast.makeText(this, "Version 1.0 by Neil Gagne", Toast.LENGTH_LONG).show();

                break;

            case R.id.n_action_add:
                addItem();
                break;

            case R.id.n_action_about:
                final AlertDialog aboutDialog = new AlertDialog.Builder(n_NutritionTrackerActivity.this).create();
                aboutDialog.setTitle(R.string.n_AboutTitle);
                aboutDialog.setMessage(getResources().getString(R.string.n_AboutMessage));
                aboutDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getResources().getString(R.string.n_OK),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                aboutDialog.dismiss();
                            }
                        });
                aboutDialog.show();
        }
        return true;
    }


    // ADD ITEM TO LISTVIEW ------------------------------------------------------------------------

    public void addItem() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(n_NutritionTrackerActivity.this);
        final View logView = getLayoutInflater().inflate(R.layout.n_activity_nutrition_dialog, null);

        builder.setView(logView);
        final AlertDialog dialog = builder.create();
        final EditText nameField = logView.findViewById(R.id.nutrition_name_field);

        Button submitNewActivity = logView.findViewById(R.id.nutrition_log_new_button);
        Button cancelNewActivity = logView.findViewById(R.id.nutrition_cancel);
        final ProgressBar myProgressBar = logView.findViewById(R.id.progressBar);

        submitNewActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameField.getText().toString().length() < 1) {
                    showValidationDialog();
                } else {
                    EditText namefield = logView.findViewById(R.id.nutrition_name_field);
                    EditText caloriesfield = logView.findViewById(R.id.nutrition_calories_field);
                    EditText carbsfield = logView.findViewById(R.id.nutrition_carbs_field);
                    EditText fatfield = logView.findViewById(R.id.nutrition_fat_field);
                    //EditText commentfield = logView.findViewById(R.id.nutrition_comment_field);

                    String itemName = namefield.getText().toString();
                    String itemCalories = caloriesfield.getText().toString();
                    String itemCarbs = carbsfield.getText().toString();
                    String itemFat = fatfield.getText().toString();
                    //String itemComment = commentfield.getText().toString();

                    ContentValues values = new ContentValues();
                    values.put(m_GlobalDatabaseHelper.FOOD_ITEM_COL_NAME, itemName);
                    values.put(m_GlobalDatabaseHelper.CALORIES_COL_NAME, itemCalories);
                    values.put(m_GlobalDatabaseHelper.CARB_COL_NAME, itemCarbs);
                    values.put(m_GlobalDatabaseHelper.FAT_COL_NAME, itemFat);
                    //values.put(m_GlobalDatabaseHelper.COMMENTS_COL_NAME, itemComment);

                    db.insert(m_GlobalDatabaseHelper.NUTRITION_TABLE_NAME, null, values);

                    updateNutrition();

                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), R.string.n_FoodAddedToast,
                            Toast.LENGTH_LONG).show();
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


    // UPDATE LISTVIEW -----------------------------------------------------------------------------

    public void updateNutrition() {
        Log.i(ACTIVITY_NAME, "In updateNutrition");
        Cursor cursor = db.query(m_GlobalDatabaseHelper.NUTRITION_TABLE_NAME, new String[]{m_GlobalDatabaseHelper.FOOD_ITEM_COL_NAME}, null, null, null, null, null);
        int colIndex = cursor.getColumnIndex(m_GlobalDatabaseHelper.FOOD_ITEM_COL_NAME);

        Cursor c = db.rawQuery("SELECT  * FROM " + m_GlobalDatabaseHelper.NUTRITION_TABLE_NAME + ";", null);
        int cnt = c.getCount();
        if (cnt > 0) {
            for (cursor.moveToLast(); !cursor.isAfterLast(); cursor.moveToNext()) {
                String value = cursor.getString(colIndex);
                foodArrayList.add(value);
                listViewAdapter.notifyDataSetChanged();
            }
            c.close();
        }
    }

    public void onDestroy() {
        db.close();
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }


    // EDIT LISTVIEW -------------------------------------------------------------------------------

    public void editItem(int id) {
        final int itemID = id;
        final AlertDialog.Builder builder = new AlertDialog.Builder(n_NutritionTrackerActivity.this);
        final View logView = getLayoutInflater().inflate(R.layout.n_activity_nutrition_dialog, null);

        builder.setView(logView);
        final AlertDialog dialog = builder.create();
        final EditText nameField = logView.findViewById(R.id.nutrition_name_field);

        Button submitNewActivity = logView.findViewById(R.id.nutrition_log_new_button);
        Button cancelNewActivity = logView.findViewById(R.id.nutrition_cancel);

        submitNewActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameField.getText().toString().length() < 1) {
                    showValidationDialog();
                } else {

                    // VALID ENTRY. ADD TO DB

                    EditText namefield = logView.findViewById(R.id.nutrition_name_field);
                    EditText caloriesfield = logView.findViewById(R.id.nutrition_calories_field);
                    EditText carbsfield = logView.findViewById(R.id.nutrition_carbs_field);
                    EditText fatfield = logView.findViewById(R.id.nutrition_fat_field);
                    //EditText commentfield = logView.findViewById(R.id.nutrition_comment_field);

                    String itemName = namefield.getText().toString();
                    String itemCalories = caloriesfield.getText().toString();
                    String itemCarbs = carbsfield.getText().toString();
                    String itemFat = fatfield.getText().toString();
                    //String itemComment = commentfield.getText().toString();

                    ContentValues values = new ContentValues();
                    values.put(m_GlobalDatabaseHelper.FOOD_ITEM_COL_NAME, itemName);
                    values.put(m_GlobalDatabaseHelper.CALORIES_COL_NAME, itemCalories);
                    values.put(m_GlobalDatabaseHelper.CARB_COL_NAME, itemCarbs);
                    values.put(m_GlobalDatabaseHelper.FAT_COL_NAME, itemFat);
                    //values.put(m_GlobalDatabaseHelper.COMMENTS_COL_NAME, itemComment);

                    String strFilter = "FOOD_ID=" + itemID;

                    db.update(m_GlobalDatabaseHelper.NUTRITION_TABLE_NAME, values, strFilter, null);

                    // UPDATE THE LISTVIEW BY REFRESHING ACTIVITY

                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);

                    // CLOSE DIALOG AFTER UPDATING DB AND LISTVIEW

                    Toast.makeText(getApplicationContext(), R.string.n_EditToastMessage,
                            Toast.LENGTH_LONG).show();
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

    public void deleteItem(int id) {
        final int itemID = id;
        db.execSQL("DELETE FROM " + m_GlobalDatabaseHelper.NUTRITION_TABLE_NAME + " WHERE " + m_GlobalDatabaseHelper.FOOD_ID + " = " + id + ";");
        updateNutrition();
    }


    // DELETE ITEM ---------------------------------------------------------------------------------

    public void showValidationDialog() {
        final AlertDialog validateDialog = new AlertDialog.Builder(n_NutritionTrackerActivity.this).create();
        validateDialog.setTitle(R.string.n_ValidateDialogTitle);
        validateDialog.setMessage(getResources().getString(R.string.n_ValidateDialogMessage));
        validateDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getResources().getString(R.string.n_OK),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        validateDialog.dismiss();
                    }
                });
        validateDialog.show();
    }


    // ASYNC TASK ----------------------------------------------------------------------------------

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


    // ADD ITEM VALIDATION -------------------------------------------------------------------------

    protected class AddItemQuery extends AsyncTask<String, Integer, String> {
        String recent = "";
        ProgressBar pBar = findViewById(R.id.progressBar);

        @Override
        protected String doInBackground(String... strings) {
            Log.i(ACTIVITY_NAME, "In AddItemQuery");
            pBar.setProgress(25);
            Cursor cursor = db.query(m_GlobalDatabaseHelper.NUTRITION_TABLE_NAME, new String[]{m_GlobalDatabaseHelper.FOOD_ITEM_COL_NAME}, null, null, null, null, null);
            int colIndex = cursor.getColumnIndex(m_GlobalDatabaseHelper.FOOD_ITEM_COL_NAME);

            Cursor c = db.rawQuery("SELECT  * FROM " + m_GlobalDatabaseHelper.NUTRITION_TABLE_NAME + ";", null);
            int cnt = c.getCount();
            pBar.setProgress(50);
            if (cnt > 0) {
                for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                    String value = cursor.getString(colIndex);
                    foodArrayList.add(value);
                    listViewAdapter.notifyDataSetChanged();
                    if (cursor.isLast()) {
                        pBar.setProgress(75);
                        recent = cursor.getString(colIndex);
                    }
                }
                c.close();
                pBar.setProgress(100);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... value) {
            pBar.setVisibility(View.VISIBLE);
            pBar.setProgress(0);
        }

        @Override
        protected void onPostExecute(String result) {
            Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content), "Most recently logged food: " + recent, Snackbar.LENGTH_LONG);

            snackbar.show();
        }
    }
}
