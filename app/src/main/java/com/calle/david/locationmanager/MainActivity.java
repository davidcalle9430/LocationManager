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
        Cursor cursor = getContentResolver().query( LocationEntry.CONTENT_URI , null, null, null, null );
        mAdapter = new LocationAdapter( getApplicationContext(), cursor, 0 );
        listView = (ListView) findViewById( R.id.view_activity_main );
        listView.setAdapter( mAdapter );
    }
}