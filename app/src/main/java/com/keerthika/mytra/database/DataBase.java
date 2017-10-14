package com.keerthika.mytra.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Keerthika Manchala O 7 on 26-09-2017.
 */

public class DataBase extends SQLiteOpenHelper {

    // Table Name
    public static final String TABLE_NAME = "mytra";

    // Table columns
    public static final String _ID = "_id";
    public static final String NAME = "name";
    public static final String PHONENUMBER = "phonenumber";
    public static final String EMAIL = "email";
    public static final String COMMENTS = "comments";
    public static final String RATING = "rating";
    public static final String HAIRDRESSER = "hairdresser";
    public static final String DATE_TIME = "date";
    public static final String TIME_VALUE = "time";
    // Database Information
    static final String DB_NAME = "MYTRASALLON.db";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "("
            + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAME + " TEXT NOT NULL, "
            + PHONENUMBER + " TEXT NOT NULL, "
            + EMAIL + " TEXT, "
            + COMMENTS + " TEXT,"
            + RATING + " TEXT,"
            + HAIRDRESSER +" TEXT, "
            + DATE_TIME +" TEXT,"
            + TIME_VALUE + " TEXT );";

    public DataBase(Context context) {
        super(context, DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
