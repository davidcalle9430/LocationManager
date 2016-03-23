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


        //TODO: Inserts de las ubicaciones, el id esta dado por el orden, el primero tiene id 1 y asi sucesivamente
        // String name, String description, Double lat, Double lon, Double radio
        sqLiteDatabase.insert( LocationEntry.TABLE_NAME , null , buildLocation("Fruteria", "En este punto de servicio ubicado en la Plazoleta de Arquitectura, usted podrá disfrutar de las frutas en todas sus presentaciones, desde opciones ligeras como jugos, fruta entera y porcionada, postres a base de fruta, deliciosas malteadas y smothies, hasta opciones de almuerzo ligero como nuestra completa ensalada de frutas.", 1.0, 1.0, 1.0) );
        sqLiteDatabase.insert( LocationEntry.TABLE_NAME , null , buildLocation("Cafeteria Central", "En estos puntos de auto-servicio usted podra disfrutar principalmente de opciones caseras de desayunos, almuerzos y carnes a la plancha; nuestro menu del dia es cuidadosamente  diseñado por una profesional de Nutrición quien se asegura que sea nutricionalmente balanceado y a la vez delicioso. En este espacio tambien puede darse el permiso de disfrutar de una amplia variedad de comidas rapidas como pizzas, hamburguesas, pastas y pinchos, entre otros, preparados con materias primas de muy alta calidad. Para prestarle un servicio completo en las cafeterías hemos integrado módulos de café donde al igual que en los cafes y kioscos distribuidos por el Campus usted disfrutara de nuestra pastelería, bebidas caliente, bebidas frías, sandwiches, frutas y ensaladas. Actualmente usted cuenta con las siguientes cafeterias:", 1.0, 1.0, 1.0) );
        sqLiteDatabase.insert( LocationEntry.TABLE_NAME , null , buildLocation("Tepanyaki", "En el Teppanyaki  puedes disfrutar de un delicioso arroz japones con la posibilidad de elegir  los ingredientes que más te gustan. Verduras (apio, cebolla cabezona, pimenton rojo, zuchini amarillo y zuchini verde) y en carnes (calamar, pollo, cerdo, filete de pescado). Para vegetarianos existen las alternativas de champiñon y orellanas, todos preparados a la plancha.", 1.0, 1.0, 1.0) );
        sqLiteDatabase.insert( LocationEntry.TABLE_NAME , null , buildLocation("Edificio Giraldo", "Edificio universitario academico , tambien hay comida", 1.0 , 1.0 ,  1.0 ) );
        sqLiteDatabase.insert( LocationEntry.TABLE_NAME , null , buildLocation("Edificio Atico", "Este proyecto denominado “ATICO” integrara recursos de la Universidad Javeriana en torno a la aplicacion de tecnologias de informacion y comunicacion ", 1.0 , 1.0 , 1.0));
        sqLiteDatabase.insert( LocationEntry.TABLE_NAME , null , buildLocation("Edificio Parqueadero", "El servicio de parqueaderos es una unidad que busca la excelencia en la prestacion del servicio de estacionamento y seguridad de los automotores, con el fin de contribuir a la comodidad y satisfaccion de la comunidad javeriana", 1.0 , 1.0 , 1.0));
        sqLiteDatabase.insert( LocationEntry.TABLE_NAME , null , buildLocation("Biblioteca Central", "El excelente servicio de un equipo profesional te espera en la Biblioteca para orientar las necesidades de informacion y documentacion que requieres en tus labores academicas e investigativas. Aprovecha el acceso a mas de 300.000 volumenes de documentos impresos y digitales en todas las areas del saber y en diferentes formatos que te llevaran por el universo del conocimiento. Nuestra Biblioteca \"Conocimiento y Servicio para la Excelencia Integral\". - See more at: http://www.javerianacali.edu.co/biblioteca", 1.0 , 1.0 , 1.0));

        //TODO: Inserts de las Situaciones
        sqLiteDatabase.insert( LocationContract.SituationEntry.TABLE_NAME , null , buildSituation("Agua") );
        sqLiteDatabase.insert( LocationContract.SituationEntry.TABLE_NAME , null , buildSituation( "Transporte" ) );
        sqLiteDatabase.insert( LocationContract.SituationEntry.TABLE_NAME , null , buildSituation( "Comida" ) );
        sqLiteDatabase.insert( LocationContract.SituationEntry.TABLE_NAME , null , buildSituation( "Estudiando" ) );

        //TODO: Inserts de las Situaciones X ubicacion
        //String situationName , Long locationId
            //fruteria
        sqLiteDatabase.insert( LocationContract.SituationXLocationEntry.TABLE_NAME , null , buildSituationXLocation( "Comida" , (long) 1 ) );
        sqLiteDatabase.insert( LocationContract.SituationXLocationEntry.TABLE_NAME , null , buildSituationXLocation( "Agua" , (long) 1 ) );
            //Central
        sqLiteDatabase.insert( LocationContract.SituationXLocationEntry.TABLE_NAME , null , buildSituationXLocation( "Comida" , (long) 2 ) );
        sqLiteDatabase.insert( LocationContract.SituationXLocationEntry.TABLE_NAME , null , buildSituationXLocation( "Agua" , (long) 2 ) );
            //Tepanyaki
        sqLiteDatabase.insert( LocationContract.SituationXLocationEntry.TABLE_NAME , null , buildSituationXLocation( "Comida" , (long) 3 ) );
            //Giraldo
        sqLiteDatabase.insert( LocationContract.SituationXLocationEntry.TABLE_NAME , null , buildSituationXLocation( "Estudiando" , (long) 4 ) );
        sqLiteDatabase.insert( LocationContract.SituationXLocationEntry.TABLE_NAME , null , buildSituationXLocation( "Agua" , (long) 4 ) );
            //Atico
        sqLiteDatabase.insert( LocationContract.SituationXLocationEntry.TABLE_NAME , null , buildSituationXLocation( "Comida" , (long) 5 ) );
        sqLiteDatabase.insert( LocationContract.SituationXLocationEntry.TABLE_NAME , null , buildSituationXLocation( "Agua" , (long) 5 ) );
        sqLiteDatabase.insert( LocationContract.SituationXLocationEntry.TABLE_NAME , null , buildSituationXLocation( "Estudiando" , (long) 5 ) );
           //Parqueadero
        sqLiteDatabase.insert( LocationContract.SituationXLocationEntry.TABLE_NAME , null , buildSituationXLocation( "Agua" , (long) 6 ) );
        sqLiteDatabase.insert( LocationContract.SituationXLocationEntry.TABLE_NAME , null , buildSituationXLocation( "Comida" , (long) 6 ) );
        sqLiteDatabase.insert( LocationContract.SituationXLocationEntry.TABLE_NAME , null , buildSituationXLocation( "Transporte" , (long) 6 ) );
            // Biblioteca
        sqLiteDatabase.insert( LocationContract.SituationXLocationEntry.TABLE_NAME , null , buildSituationXLocation( "Comida" , (long) 7 ) );
        sqLiteDatabase.insert( LocationContract.SituationXLocationEntry.TABLE_NAME , null , buildSituationXLocation( "Estudio" , (long) 7 ) );
        sqLiteDatabase.insert( LocationContract.SituationXLocationEntry.TABLE_NAME , null , buildSituationXLocation( "Estudiando" , (long) 7 ) );

        //TODo:  inserts de la infromación
        //key, value, location id
        sqLiteDatabase.insert( InformationEntry.TABLE_NAME , null , buildInformation( "Menu" , "Batidos de fruta" , 1 ) );
        sqLiteDatabase.insert( InformationEntry.TABLE_NAME , null , buildInformation( "Menu" , "Ensalada de fruta" , 1 ) );
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
