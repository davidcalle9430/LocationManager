package com.calle.david.locationmanager.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.calle.david.locationmanager.data.LocationContract.InformationEntry;

import com.calle.david.locationmanager.data.LocationContract.LocationEntry;
/**
 * Created by david on 2/28/2016.
 */
public class LocationDBHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 7;

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
                + LocationEntry.COLUMN_COORD_LAT + " REAL NOT NULL, "
                + LocationEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, "
                + LocationEntry.COLUMN_RADIO + " REAL NOT NULL "
                + ");";
        final String SQL_CREATE_INFORMATION_TABLE = "CREATE TABLE " + InformationEntry.TABLE_NAME + " ("
                + InformationEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + InformationEntry.COLUMN_LOC_KEY + " INTEGER NOT NULL, "
                + InformationEntry.COLUMN_KEY + " TEXT NOT NULL, "
                + InformationEntry.COLUMN_VALUE + " TEXT NOT NULL, "
                + "FOREIGN KEY ( " + InformationEntry.COLUMN_LOC_KEY + " ) REFERENCES " + LocationEntry.TABLE_NAME + "( "+ LocationEntry._ID + " )"
                + ");";

        final String SQL_CREATE_SITUATION_TABLE = "CREATE TABLE " + LocationContract.SituationEntry.TABLE_NAME + "( " +
                 LocationContract.SituationEntry.COLUMN_SITUATION_NAME  +" TEXT PRIMARY KEY NOT NULL );";

        final String SQL_CREATE_SITUATION_X_LOCATION_TABLE = "CREATE TABLE " + LocationContract.SituationXLocationEntry.TABLE_NAME + " ( " +
                LocationContract.SituationXLocationEntry._ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                LocationContract.SituationXLocationEntry.COLUMN_LOCATION_KEY + " INTEGER NOT NULL, " +
                LocationContract.SituationXLocationEntry.COLUMN_SITUATION_NAME + " TEXT NOT NULL, " +
                "FOREIGN KEY ( " + LocationContract.SituationXLocationEntry.COLUMN_LOCATION_KEY + ") REFERENCES " + LocationEntry.TABLE_NAME +"(" + ( LocationEntry._ID ) + ") , " +
                "FOREIGN KEY ( " + LocationContract.SituationXLocationEntry.COLUMN_SITUATION_NAME + ") REFERENCES " + LocationContract.SituationEntry.TABLE_NAME +"(" + (LocationContract.SituationEntry.COLUMN_SITUATION_NAME ) + ") ); ";

        sqLiteDatabase.execSQL( SQL_CREATE_LOCATION_TABLE );
        sqLiteDatabase.execSQL( SQL_CREATE_INFORMATION_TABLE );
        sqLiteDatabase.execSQL( SQL_CREATE_SITUATION_TABLE );
        sqLiteDatabase.execSQL( SQL_CREATE_SITUATION_X_LOCATION_TABLE );
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
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + InformationEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + LocationContract.SituationEntry.TABLE_NAME );
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + LocationContract.SituationXLocationEntry.TABLE_NAME );
        onCreate(sqLiteDatabase);
    }
}
