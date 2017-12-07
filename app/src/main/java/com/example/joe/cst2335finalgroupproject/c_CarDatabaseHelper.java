package com.example.joe.cst2335finalgroupproject;

/**
 * Nathan, as discussed, I've moved all of your database components to the app-wide database
 * m_GlobalDatabaseHelper so that we only have one.
 */


//
//public class c_CarDatabaseHelper extends SQLiteOpenHelper{
//
//    public static final String DATABASE_NAME = "FUEL_DATABASE.db";
//    public static final int VERSION_NUMBER = 1;
//    public static final String FUEL_DETAILS_TABLE = "FUEL_DETAILS_TABLE";
//
//    public static final String KEY_ID = "_id";;
//    public static final String KEY_PRICE = "price";
//    public static final String KEY_LITRES = "litres";
//    public static final String KEY_KILOMETERS = "kilometers";
//
//    public static final String KEY_DATE = "date";
//
//
//    public static final String CREATE_TABLE_SQL
//        = "CREATE TABLE " + FUEL_DETAILS_TABLE + " ( "
//            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
//            + KEY_PRICE + " REAL, "
//            + KEY_LITRES + " REAL, "
//            + KEY_KILOMETERS + " REAL, "
//            + KEY_DATE + " INTEGER"
//            + " );";  // can convert to timestamp later
//
//    public static final String DROP_TABLE_SQL = "DROP TABLE IF EXISTS " + FUEL_DETAILS_TABLE;
//
//    public static final String SELECT_ALL_SQL
//        = String.format("SELECT %s, %s, %s, %s, %s FROM %s",
//            KEY_ID, KEY_PRICE, KEY_LITRES, KEY_KILOMETERS, KEY_DATE, FUEL_DETAILS_TABLE);
//
//
//    public c_CarDatabaseHelper(Context context){
//        super(context, DATABASE_NAME, null, VERSION_NUMBER);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db){
//        db.execSQL(CREATE_TABLE_SQL);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
//        db.execSQL(DROP_TABLE_SQL);
//        onCreate(db);
//    }
//
//    @Override
//    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
//        db.execSQL(DROP_TABLE_SQL);
//        onCreate(db);
//    }
//}