package com.calle.david.locationmanager.data;

import android.content.ContentValues;
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

        final double m_degree = 111320;
        //TODO: Inserts de las ubicaciones, el id esta dado por el orden, el primero tiene id 1 y asi sucesivamente
        // String name, String description, Double lat, Double lon, Double radio
        sqLiteDatabase.insert( LocationEntry.TABLE_NAME , null , buildLocation("Edificio Gabriel Giraldo", "Edificio de la facultad de derecho",4.6269336, -74.06496,(double) 20 / m_degree ) );
        sqLiteDatabase.insert( LocationEntry.TABLE_NAME , null , buildLocation("Edificio Fernando Baron", "Edificio para clases de diversas facultades, tiene cafeteria", 4.6266522, -74.06407 ,  (double)  16 / m_degree) );
        sqLiteDatabase.insert( LocationEntry.TABLE_NAME , null , buildLocation("Edificio Rafael Arboleda", "Edificio que queda cerca de parqueaderos", 4.628762 , -74.062827 ,  (double) 25 / m_degree ) );
        sqLiteDatabase.insert( LocationEntry.TABLE_NAME , null , buildLocation("Edificio Gabriel Maldonado", "Edificio de la facultad de ingenieria industrial, civil, eletrectronica y de sistemas", 4.626978 ,-74.064081 , (double) 27 / m_degree ) );
        sqLiteDatabase.insert( LocationEntry.TABLE_NAME , null , buildLocation("Biblioteca Alfonso Borrero", "Abierta 24 horas para las personas de la comunidad Javeriana ", 4.6289625 , -74.064896 , (double) 30 / m_degree  ));
        sqLiteDatabase.insert( LocationEntry.TABLE_NAME , null , buildLocation("Edificio Jesus Ramirez", "Otro edificio de la universidad", 4.630642 , -74.064, (double) 15 / m_degree ));
        sqLiteDatabase.insert( LocationEntry.TABLE_NAME , null , buildLocation("Centro Atico", "El proyecto atico, es uno de tecnologias de informacion de la universidad", 4.626924 , -74.065147 ,(double) 20 / m_degree ));
        sqLiteDatabase.insert( LocationEntry.TABLE_NAME , null , buildLocation("Biblioteca de Filosofia", "Biblioteca alterna de la universidad ", 4.626216 , -74.063296 , (double) 15 / m_degree ));
        sqLiteDatabase.insert( LocationEntry.TABLE_NAME , null , buildLocation("Restaurante el Mirador", "Restaurante de alto costo de la universidad ", 4.626445 , -74.064767 , (double) 15 / m_degree ));
        sqLiteDatabase.insert( LocationEntry.TABLE_NAME , null , buildLocation("Cafe el Alcaparro", "Venta de ricos cafes ", 4.626526 , -74.062886 ,(double) 10 / m_degree ));
        sqLiteDatabase.insert( LocationEntry.TABLE_NAME , null , buildLocation("Cafeteria Central", "Cafeteria de almuerzos caseros en la universidad ",4.6280947 , -74.064766 , (double) 20 / m_degree ));
        sqLiteDatabase.insert( LocationEntry.TABLE_NAME , null , buildLocation("La creperia", "Ricos crepes como alternativa de comida ", 4.627354 , -74.062659 , (double) 20 / m_degree ));
        sqLiteDatabase.insert( LocationEntry.TABLE_NAME , null , buildLocation("Tepanyaki", "Restaurante y cateria frente al edificio baron ",4.626713 , -74.06409 ,(double) 8 / m_degree ));
        sqLiteDatabase.insert( LocationEntry.TABLE_NAME , null , buildLocation("Cafeteria HUSI", "Descansa y tomate algo aca ", 4.6281724 , -74.064443 , (double) 15 / m_degree ));
        sqLiteDatabase.insert( LocationEntry.TABLE_NAME , null , buildLocation("Artes", "Facultad de artes ", 4.6267 , -74.06413 , (double) 35 / m_degree  ));
        //TODO: Inserts de las Situaciones
        sqLiteDatabase.insert( LocationContract.SituationEntry.TABLE_NAME , null , buildSituation("Agua") );
        sqLiteDatabase.insert( LocationContract.SituationEntry.TABLE_NAME , null , buildSituation( "Transporte" ) );
        sqLiteDatabase.insert( LocationContract.SituationEntry.TABLE_NAME , null , buildSituation( "Comida" ) );
        sqLiteDatabase.insert( LocationContract.SituationEntry.TABLE_NAME , null , buildSituation( "Estudiando" ) );

        //TODO: Inserts de las Situaciones X ubicacion
        //String situationName , Long locationId
            //Edificio Gabriel Giraldo, 1
        sqLiteDatabase.insert( LocationContract.SituationXLocationEntry.TABLE_NAME , null , buildSituationXLocation( "Comida" , (long) 1 ) );
        sqLiteDatabase.insert( LocationContract.SituationXLocationEntry.TABLE_NAME , null , buildSituationXLocation( "Estudiando" , (long) 1 ) );
            //Edificio Fernando Baron, 2
        sqLiteDatabase.insert( LocationContract.SituationXLocationEntry.TABLE_NAME , null , buildSituationXLocation( "Comida" , (long) 2 ) );
        sqLiteDatabase.insert( LocationContract.SituationXLocationEntry.TABLE_NAME , null , buildSituationXLocation( "Agua" , (long) 2 ) );
        sqLiteDatabase.insert( LocationContract.SituationXLocationEntry.TABLE_NAME , null , buildSituationXLocation( "Estudiando" , (long) 2 ) );
            //Edificio Rafael Arboleda, 3
        sqLiteDatabase.insert( LocationContract.SituationXLocationEntry.TABLE_NAME , null , buildSituationXLocation( "Estudiando" , (long) 3 ) );
        sqLiteDatabase.insert( LocationContract.SituationXLocationEntry.TABLE_NAME , null , buildSituationXLocation( "Transporte" , (long) 3 ) );
            //Edificio Gabriel Maldonado, 4
        sqLiteDatabase.insert( LocationContract.SituationXLocationEntry.TABLE_NAME , null , buildSituationXLocation( "Estudiando" , (long) 4 ) );
            //Biblioteca Alfonso Borrero, 5
        sqLiteDatabase.insert( LocationContract.SituationXLocationEntry.TABLE_NAME , null , buildSituationXLocation( "Comida" , (long) 5 ) );
        sqLiteDatabase.insert( LocationContract.SituationXLocationEntry.TABLE_NAME , null , buildSituationXLocation( "Agua" , (long) 5 ) );
        sqLiteDatabase.insert( LocationContract.SituationXLocationEntry.TABLE_NAME , null , buildSituationXLocation( "Estudiando" , (long) 5 ) );
           //Edificio Jesus Ramirez, 6
        sqLiteDatabase.insert( LocationContract.SituationXLocationEntry.TABLE_NAME , null , buildSituationXLocation( "Estudiando" , (long) 6 ) );
            // Centro Atico, 7
        sqLiteDatabase.insert( LocationContract.SituationXLocationEntry.TABLE_NAME , null , buildSituationXLocation( "Comida" , (long) 7 ) );
        sqLiteDatabase.insert( LocationContract.SituationXLocationEntry.TABLE_NAME , null , buildSituationXLocation( "Transporte" , (long) 7 ) );
        sqLiteDatabase.insert( LocationContract.SituationXLocationEntry.TABLE_NAME , null , buildSituationXLocation( "Estudiando" , (long) 7 ) );
        sqLiteDatabase.insert( LocationContract.SituationXLocationEntry.TABLE_NAME , null , buildSituationXLocation( "Agua" , (long) 7 ) );
            // Biblioteca de filosofia, 8
        sqLiteDatabase.insert( LocationContract.SituationXLocationEntry.TABLE_NAME , null , buildSituationXLocation( "Estudiando" , (long) 8 ) );
        // Restaurante el Mirador, 9
        sqLiteDatabase.insert( LocationContract.SituationXLocationEntry.TABLE_NAME , null , buildSituationXLocation( "Comida" , (long) 9 ) );
        // Cafe el Alcaparro, 10
        sqLiteDatabase.insert( LocationContract.SituationXLocationEntry.TABLE_NAME , null , buildSituationXLocation( "Comida" , (long) 10  ) );
        sqLiteDatabase.insert( LocationContract.SituationXLocationEntry.TABLE_NAME , null , buildSituationXLocation( "Agua" , (long) 10 ) );
        // Cafeteria Central, 11
        sqLiteDatabase.insert( LocationContract.SituationXLocationEntry.TABLE_NAME , null , buildSituationXLocation( "Comida" , (long) 11  ) );
        // La creperia, 12
        sqLiteDatabase.insert( LocationContract.SituationXLocationEntry.TABLE_NAME , null , buildSituationXLocation( "Comida" , (long) 12  ) );
        // Tepanyaki, 13
        sqLiteDatabase.insert( LocationContract.SituationXLocationEntry.TABLE_NAME , null , buildSituationXLocation( "Comida" , (long) 13  ) );
        // Cafeteria HUSI, 14
        sqLiteDatabase.insert( LocationContract.SituationXLocationEntry.TABLE_NAME , null , buildSituationXLocation( "Comida" , (long) 14  ) );
        // La conjera, 15
        sqLiteDatabase.insert( LocationContract.SituationXLocationEntry.TABLE_NAME , null , buildSituationXLocation( "Comida" , (long) 15  ) );


        //TODo:  inserts de la infromacion
        //key, value, location id
        sqLiteDatabase.insert( InformationEntry.TABLE_NAME , null , buildInformation( "Menu" , "Batidos de fruta" , 1 ) );
        sqLiteDatabase.insert( InformationEntry.TABLE_NAME , null , buildInformation( "Menu" , "Ensalada de fruta" , 1 ) );
    }


    /**
     * Por ahora lo que va a hacer este metodo es que cuando
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




    /**
     * este metodo solo retorna los content values que se le pasan al content provider para el insert de la tabla ubicacion
     * @param name
     * @param description
     * @param lat
     * @param lon
     * @param radio
     * @return
     */
    public static ContentValues buildLocation(String name, String description, Double lat, Double lon, Double radio){
        ContentValues val = new ContentValues();
        val.put( LocationContract.LocationEntry.COLUMN_LOCATION_NAME , name );
        val.put( LocationContract.LocationEntry.COLUMN_DESCRIPTION , description );
        val.put( LocationContract.LocationEntry.COLUMN_COORD_LAT , lat );
        val.put( LocationContract.LocationEntry.COLUMN_COOD_LON , lon );
        val.put( LocationContract.LocationEntry.COLUMN_RADIO , radio );
        return val;
    }

    /**
     * este metodo se encarga de retornar los content values que se le pasan al provider para el insert de la tabla information
     * @param key
     * @param value
     * @param locationId
     * @return
     */
    public static ContentValues buildInformation(String key, String value, Integer locationId){
        ContentValues contentValues = new ContentValues();
        contentValues.put( InformationEntry.COLUMN_KEY , key );
        contentValues.put( InformationEntry.COLUMN_VALUE , value );
        contentValues.put( InformationEntry.COLUMN_LOC_KEY , locationId);
        return contentValues;
    }

    public static ContentValues buildSituation(String name){
        ContentValues contentValues = new ContentValues();
        contentValues.put( LocationContract.SituationEntry.COLUMN_SITUATION_NAME, name );
        return contentValues;
    }

    public static ContentValues buildSituationXLocation(String situationName , Long locationId){
        ContentValues contentValues = new ContentValues();
        contentValues.put( LocationContract.SituationXLocationEntry.COLUMN_LOCATION_KEY, locationId );
        contentValues.put( LocationContract.SituationXLocationEntry.COLUMN_SITUATION_NAME, situationName );
        return contentValues;
    }
}
