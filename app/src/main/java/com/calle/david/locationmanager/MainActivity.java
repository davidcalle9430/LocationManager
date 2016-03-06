package com.calle.david.locationmanager;

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

    private  LocationAdapter mAdapter;
    private ListView listView;

    public static ContentValues buildLocation(String name, String description, Double lat, Double lon, Double radio){
        ContentValues val = new ContentValues();
        val.put(LocationContract.LocationEntry.COLUMN_LOCATION_NAME, name);
        val.put(LocationContract.LocationEntry.COLUMN_DESCRIPTION, description);
        val.put(LocationContract.LocationEntry.COLUMN_COORD_LAT, lat);
        val.put(LocationContract.LocationEntry.COLUMN_COOD_LON, lon);
        val.put(LocationContract.LocationEntry.COLUMN_RADIO, radio);
        return val;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Cursor cursor = getContentResolver().query( LocationEntry.CONTENT_URI, null, null, null, null );
        mAdapter = new LocationAdapter(getApplicationContext(), cursor, 0);
        listView = (ListView) findViewById(R.id.view_activity_main);
        listView.setAdapter(mAdapter);
    }
}
