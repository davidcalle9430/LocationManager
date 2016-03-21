package com.calle.david.locationmanager;
import com.calle.david.locationmanager.data.LocationContract.InformationEntry;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.location.LocationProvider;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import com.calle.david.locationmanager.data.LocationContract.LocationEntry;

import com.calle.david.locationmanager.data.LocationContract;

public class MainActivity extends AppCompatActivity {

    public final static String ID_LOCATION = "id_location";

    private  LocationAdapter mAdapter;
    private ListView listView;

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
        val.put(LocationContract.LocationEntry.COLUMN_LOCATION_NAME, name);
        val.put(LocationContract.LocationEntry.COLUMN_DESCRIPTION, description);
        val.put(LocationContract.LocationEntry.COLUMN_COORD_LAT, lat);
        val.put(LocationContract.LocationEntry.COLUMN_COOD_LON, lon);
        val.put(LocationContract.LocationEntry.COLUMN_RADIO, radio);
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
        contentValues.put( InformationEntry.COLUMN_KEY, key );
        contentValues.put( InformationEntry.COLUMN_VALUE, value );
        contentValues.put( InformationEntry.COLUMN_LOC_KEY, locationId );
        return contentValues;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Cursor cursor = getContentResolver().query(LocationEntry.CONTENT_URI, null, null, null, null);
        mAdapter = new LocationAdapter( getApplicationContext(), cursor, 0 );
        listView = (ListView) findViewById( R.id.view_activity_main );
        listView.setAdapter(mAdapter);

    }
}
/*
ContentValues ingenieria = buildLocation("Tunel","Todos comen ahi, es deamisado caro, DEMASIADO",
                1.1, 2.2, 5.0);

        getContentResolver().insert( LocationEntry.CONTENT_URI , ingenieria );
        ContentValues contentValues = buildInformation("Ruta", "Siempre esta lleno", 1);
        ContentValues contentValues2 = buildInformation("Camino Alterno", "NO salir al almuerzo", 1);
        ContentValues contentValues3 = buildInformation("Camino Alterno", "jamas en la vida", 1);

        getContentResolver().insert( InformationEntry.CONTENT_URI  , contentValues);
        getContentResolver().insert(InformationEntry.CONTENT_URI, contentValues2);
        getContentResolver().insert(InformationEntry.CONTENT_URI, contentValues3);
 */



