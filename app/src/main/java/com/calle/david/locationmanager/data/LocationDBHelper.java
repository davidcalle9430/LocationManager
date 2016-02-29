package com.calle.david.locationmanager.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.calle.david.locationmanager.data.LocationContract.LocationEntry;
/**
 * Created by david on 2/28/2016.
 */
public class LocationDBHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 2;

    static final String DATABASE_NAME = "location.db";


    public LocationDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Metodo que se encarga de la creacion de la base de datos
     * basado en el contrato definido en LocationContract
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_LOCATION_TABLE = "CREATE TABLE " + LocationEntry.TABLE_NAME + " ("
                + LocationEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + LocationEntry.COLUMN_LOCATION_NAME + " TEXT NOT NULL, "
                + LocationEntry.COLUMN_COOD_LON + " REAL NOT NULL, "
                + LocationEntry.COLUMN_COORD_LAT + " REAL NOT NULL"
                + ");";
        Log.v("Base de datos", SQL_CREATE_LOCATION_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_LOCATION_TABLE);
    }

    /**
     * Por ahora lo que va a hacer este método es que cuando
     * haya una nueva version de la base de datos la borra y la vuelve a crear
     * @param sqLiteDatabase
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + LocationEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
