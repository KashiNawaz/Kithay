package com.kithay;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Noman Sial on 6/11/2018.
 */

public class DBhelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "kithay.db";

    // Contacts table name
    public static final String TABLE_CONTACTS = "contacts";
    public static final String TABLE_HISTORY = "history";
    public static final String TABLE_USER = "user";


    // Contacts Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_PH_NO = "phone_number";
    public static final String KEY_LANG = "langitude";
    public static final String KEY_LAT = "latitude";
    public static final String KEY_TIME = "time";
    public static final String KEY_DATE = "date";
    public static final String KEY_BATTERY = "batteryLevel";
    public static final String KEY_ADDRESS = "address";

    static final String[] Contacts_Columns = new String[]{DBhelper.KEY_ID, DBhelper.KEY_NAME, DBhelper.KEY_PH_NO};
    static final String[] History_columns = new String[]{DBhelper.KEY_ID, DBhelper.KEY_NAME, DBhelper.KEY_PH_NO, DBhelper.KEY_LANG, DBhelper.KEY_LAT, DBhelper.KEY_DATE, DBhelper.KEY_TIME,DBhelper.KEY_BATTERY,DBhelper.KEY_ADDRESS};
    static final String[] User_columns = new String[]{DBhelper.KEY_ID, DBhelper.KEY_NAME,DBhelper.KEY_EMAIL,DBhelper.KEY_PASSWORD, DBhelper.KEY_PH_NO};


    public DBhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        System.out.println("DB Created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_CONTACTS = "CREATE TABLE " + TABLE_CONTACTS +
                "( " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + KEY_NAME + " TEXT NOT NULL , " +
                KEY_PH_NO + " TEXT NOT NULL " + " )";
        db.execSQL(CREATE_TABLE_CONTACTS);
        System.out.println("Contacts Table Created");

        String CREATE_TABLE_HISTORY = "CREATE TABLE " + TABLE_HISTORY +
                "( " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + KEY_NAME + " TEXT NOT NULL , " +
                KEY_PH_NO + " TEXT NOT NULL , " + KEY_LANG + " TEXT NOT NULL , " + KEY_LAT + " TEXT NOT NULL , " +
                KEY_DATE + " TEXT NOT NULL, " + KEY_TIME + " TEXT NOT NULL, " + KEY_BATTERY + " TEXT NOT NULL, " + KEY_ADDRESS + " TEXT NOT NULL " + " )";
        db.execSQL(CREATE_TABLE_HISTORY);
        System.out.println("History Table Created");

        String CREATE_TABLE_USER="CREATE TABLE " + TABLE_USER +
                "( " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + KEY_NAME + " TEXT NOT NULL , " +
                KEY_PH_NO + " TEXT NOT NULL ," + KEY_EMAIL + " TEXT NOT NULL ," + KEY_PASSWORD + " TEXT NOT NULL " + " )";
        db.execSQL(CREATE_TABLE_USER);
        System.out.println("User Table Created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);

        System.out.println("DB Updated");
    }
}
