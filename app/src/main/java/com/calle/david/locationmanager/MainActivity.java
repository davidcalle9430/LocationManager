package com.calle.david.locationmanager;
import com.calle.david.locationmanager.data.LocationContract.InformationEntry;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.LocationProvider;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.calle.david.locationmanager.data.LocationContract.LocationEntry;

import com.calle.david.locationmanager.data.LocationContract;
import com.calle.david.locationmanager.services.SituationService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public final static String ID_LOCATION = "id_location";
    public final static String SITUATION_EXTRA = "situation_extra";

    private LocationAdapter mAdapter;
    private ListView listView;
    private List<String> situationList = new ArrayList<>();
    private Spinner spinner;

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
        contentValues.put( InformationEntry.COLUMN_KEY, key );
        contentValues.put( InformationEntry.COLUMN_VALUE, value );
        contentValues.put( InformationEntry.COLUMN_LOC_KEY, locationId );
        return contentValues;
    }

    public static ContentValues buildSituation(String name){
        ContentValues contentValues = new ContentValues();
        contentValues.put( LocationContract.SituationEntry.COLUMN_SITUATION_NAME , name );
        return contentValues;
    }

    public static ContentValues buildSituationXLocation(String situationName , Long locationId){
        ContentValues contentValues = new ContentValues();
        contentValues.put( LocationContract.SituationXLocationEntry.COLUMN_LOCATION_KEY , locationId );
        contentValues.put(LocationContract.SituationXLocationEntry.COLUMN_SITUATION_NAME, situationName);
        return contentValues;
    }
    public void getSituationItems( String situation ){
        String[] queryArgs = { situation };
        Cursor situationWithLocation = getContentResolver().query( LocationContract.SituationXLocationEntry.CONTENT_URI , null , LocationContract.SituationXLocationEntry.COLUMN_SITUATION_NAME + " = ?", queryArgs , null );
        mAdapter.swapCursor(situationWithLocation);
    }

    //TODO: llenarlo con los datos reales
    public void fillSpinner(){
        spinner = (Spinner) findViewById( R.id.situation_spinner );
        Cursor cursor = getContentResolver().query( LocationContract.SituationEntry.CONTENT_URI , null, null, null, null );
        while( cursor.moveToNext() ){ situationList.add( cursor.getString( 0 ) ) ;}
        ArrayAdapter< String > spinnerAdapter = new ArrayAdapter( this  , android.R.layout.simple_spinner_dropdown_item  , situationList );
        spinner.setAdapter(spinnerAdapter);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getSituationItems( situationList.get( position ) );
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fillLocationList();
        fillSpinner();
        startService(new Intent( getApplicationContext() , SituationService.class ) );
        if( getIntent() != null && getIntent().getExtras() != null){
            String currentState = getIntent().getExtras().getString( SITUATION_EXTRA );
            if( currentState != null ){
                int selected = situationList.indexOf( currentState );
                spinner.setSelection( selected );
            }
        }
    }

    public void fillLocationList(){
        Cursor cursor = getContentResolver().query( LocationEntry.CONTENT_URI, null, null, null, null );
        mAdapter = new LocationAdapter( getApplicationContext(), cursor, 0 );
        listView = (ListView) findViewById( R.id.view_activity_main );
        listView.setAdapter( mAdapter );
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

        ContentValues situacion = buildSituation("Estudiando");
        getContentResolver().insert(LocationContract.SituationEntry.CONTENT_URI, situacion);
        ContentValues situacion1 = buildSituation("Comida");
        getContentResolver().insert( LocationContract.SituationEntry.CONTENT_URI, situacion1 );
        ContentValues situacion2 = buildSituation("Transporte");
        getContentResolver().insert( LocationContract.SituationEntry.CONTENT_URI, situacion2 );


         ContentValues situation1 = buildSituationXLocation("Transporte", (long) 2);
        ContentValues situation2 = buildSituationXLocation("Transporte", (long) 3);
        ContentValues situation3 = buildSituationXLocation("Estudiando", (long) 2);
        getContentResolver().insert( LocationContract.SituationXLocationEntry.CONTENT_URI  , situation );
        getContentResolver().insert( LocationContract.SituationXLocationEntry.CONTENT_URI  , situation1 );
        getContentResolver().insert( LocationContract.SituationXLocationEntry.CONTENT_URI  , situation2 );
        getContentResolver().insert( LocationContract.SituationXLocationEntry.CONTENT_URI  , situation3 );

*/

