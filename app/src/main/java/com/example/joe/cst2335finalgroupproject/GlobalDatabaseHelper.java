package com.example.joe.cst2335finalgroupproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GlobalDatabaseHelper extends SQLiteOpenHelper {

    //Database and Table Names
    static final String DATABASE_NAME = "GlobalDatabase.db";
    static final String THERMOSTAT_TABLE_NAME = "THERMOSTAT_RULES";
    static final String RULE_ID = "RULE_ID";
    static final String RULE_COL_NAME = "RULES";
    static final String NUTRITION_TABLE_NAME = "NUTRITION_INFO";
    static final String FOOD_ID = "FOOD_ID";
    static final String CALORIES_COL_NAME = "CALORIES";
    static final String CARB_COL_NAME = "CARBOHYDRATE";
    static final String FAT_COL_NAME = "FAT";
    static final String CAR_TABLE_NAME = "GAS_PURCHASES";
    static final String PURCHASE_ID = "PURCHASE_ID";
    static final String VOLUME_COL_NAME = "VOLUME_PURCHASED";
    static final String PRICE_COL_NAME = "PRICE_PER_LITER";
    static final String DISTANCE_COL_NAME = "KILOMETERS_SINCE_LAST";
    static final String ACTIVITY_TABLE_NAME = "ACTIVITY_LOG";
    static final String WORKOUT_ID = "WORKOUT_ID";
    static final String TYPE_COL_NAME = "WORKOUT_TYPE";
    static final String DURATION_COL_NAME = "WORKOUT_DURATION";
    static final String NOTE_COL_NAME = "WORKOUT_NOTES";
    static final String TIME_COL_NAME = "TIMESTAMP";
    //Database Version Number
    private static final int DATABASE_VERSION_NUM = 1;  //WARNING: AVOID ALTERING THIS VALUE UNLESS REQUIRED. THIS WILL CLEAR ALL TABLES FOR ALL ACTIVITIES.


    GlobalDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + THERMOSTAT_TABLE_NAME + " (" + RULE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + RULE_COL_NAME + " TEXT);");
        db.execSQL("CREATE TABLE " + NUTRITION_TABLE_NAME + " (" + FOOD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CALORIES_COL_NAME + " INTEGER, " + CARB_COL_NAME + " INTEGER," + FAT_COL_NAME + " INTEGER);");
        db.execSQL("CREATE TABLE " + CAR_TABLE_NAME + " (" + PURCHASE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + VOLUME_COL_NAME + " INTEGER, " + PRICE_COL_NAME + " INTEGER," + DISTANCE_COL_NAME + " INTEGER);");
        db.execSQL("CREATE TABLE " + ACTIVITY_TABLE_NAME + " (" + WORKOUT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TYPE_COL_NAME + " TEXT, " + DURATION_COL_NAME + " INTEGER," + NOTE_COL_NAME + " TEXT, " + TIME_COL_NAME + "DATETIME DEFAULT CURRENT_TIMESTAMP);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        //Log.i("ChatDatabaseHelper", "Calling onUpgrade. Old Version: " + oldVer + ", New Version: " + newVer);
        db.execSQL("DROP TABLE IF EXISTS " + THERMOSTAT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + NUTRITION_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CAR_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ACTIVITY_TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVer, int newVer) {
        //Log.i("ChatDatabaseHelper", "Calling onDowngrade. Old Version: " + oldVer + ", New Version: " + newVer);
        db.execSQL("DROP TABLE IF EXISTS " + THERMOSTAT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + NUTRITION_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CAR_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ACTIVITY_TABLE_NAME);
        onCreate(db);
    }

}
