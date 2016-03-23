package com.calle.david.locationmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import com.calle.david.locationmanager.data.LocationContract.LocationEntry;

import com.calle.david.locationmanager.data.LocationContract;
import com.calle.david.locationmanager.services.GpsTrackerService;

public class GpsTracker extends AppCompatActivity {

    public static final float RANGE = 0.00005 // five meters
    GpsTrackerService gps;
    GPSChange receiver;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        listView = findViewById( R.id.view_search_main );
    }

    @Override
    protected  void onStart()
    {
        super.onStart();

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setContentView( R.layout.activity_gps_tracker );
        GpsTrackerService.mContext = this;
        IntentFilter filter = new IntentFilter( GpsTrackerService.GPS_LOCATION_CHANGE_FILTER );
        receiver = new GPSChange( );
        gps = new Intent( getApplicationContext(), GpsTrackerService.class );
        registerReceiver(receiver, filter);
        bindService( gps );
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        unbindService(gps);
        unregisterReceiver(receiver);
    }

    public void getCurrentLocation(View v){
        sendBroadcast(new Intent(GpsTrackerService.GPS_GET_LOCATION_FILTER));
    }

    public void searchNearLocations( float lat , float lon )
    {
        float latitudLeft = lat - RANGE;
        float latitudRight = lat + RANGE;
        float longitudeUp = lon + RANGE;
        float longitudeDown = lon - RANGE;

        Cursor cursor = getContentResolver().query( LocationContract.LocationEntry.CONTENT_URI, null,
                LocationEntry.COLUMN_COOD_LON + LocationEntry.COLUMN_RADIO + ">" +longitudeDown + " AND " +
                LocationEntry.COLUMN_COOD_LON - LocationEntry.COLUMN_RADIO + "<" +longitudeUp + " AND " +
                LocationEntry.COLUMN_COOD_LAT + LocationEntry.COLUMN_RADIO + ">" +latitudLeft + " AND " +
                LocationEntry.COLUMN_COOD_LAT - LocationEntry.COLUMN_RADIO + "<" +latitudRight, null, null );
        mAdapter = new LocationAdapter( getApplicationContext(), cursor, 0 );
        listView = ( ListView ) findViewById( R.id.view_activity_main );
        listView.setAdapter( mAdapter );
    }

    class GPSChange extends BroadcastReceiver
    {
        @Override
        public void onReceive( Context context, Intent intent )
        {
            String action = intent.getAction();
            float latitude = Float.parseFloat( intent.getStringExtra( "Lat" ) );
            float longitude = Float.parseFloat( intent.getStringExtra( "Lon" ) );
            searchNearLocations( latitude, longitude );
        }
    }
}