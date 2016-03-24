package com.calle.david.locationmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import com.calle.david.locationmanager.data.LocationContract.LocationEntry;

import com.calle.david.locationmanager.data.LocationContract;
import com.calle.david.locationmanager.services.GpsTrackerService;

public class SearchActivity extends AppCompatActivity {

    public static final float RANGE = (float)0.00005 ;// five meters
    GpsTrackerService gps;
    GPSChange receiver;
    LocationAdapter mAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        listView = (ListView) findViewById( R.id.view_activity_search );
    }

    @Override
    protected  void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        GpsTrackerService.mContext = this;
        IntentFilter filter = new IntentFilter( GpsTrackerService.GPS_LOCATION_CHANGE_FILTER );
        receiver = new GPSChange( );
        registerReceiver( receiver , filter);
        startService(new Intent(getApplicationContext(), GpsTrackerService.class));
        getCurrentLocation();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
    }

    public void getCurrentLocation(){
        sendBroadcast(new Intent (GpsTrackerService.GPS_GET_LOCATION_FILTER ));
    }

    public void searchNearLocations( float lat , float lon ) {
        float latitudLeft = lat - RANGE;
        float latitudRight = lat + RANGE;
        float longitudeUp = lon + RANGE;
        float longitudeDown = lon - RANGE;
        String where = LocationEntry.COLUMN_COOD_LON + "+"+ LocationEntry.COLUMN_RADIO + ">" +longitudeDown + " AND " +
                LocationEntry.COLUMN_COOD_LON + "-"+LocationEntry.COLUMN_RADIO + "<" +longitudeUp + " AND " +
                LocationEntry.COLUMN_COORD_LAT + "+"+ LocationEntry.COLUMN_RADIO + ">" +latitudLeft + " AND " +
                LocationEntry.COLUMN_COORD_LAT + "-"+ LocationEntry.COLUMN_RADIO + "<" +latitudRight;
        Cursor cursor = getContentResolver().query( LocationContract.LocationEntry.CONTENT_URI, null, where ,null, null );
        mAdapter = new LocationAdapter( getApplicationContext(), cursor , 0 );
        listView.setAdapter( mAdapter );
    }

    class GPSChange extends BroadcastReceiver {
        @Override
        public void onReceive( Context context, Intent intent ) {
            String action = intent.getAction();
            float latitude = Float.parseFloat( intent.getStringExtra( "Lat" ) );
            float longitude = Float.parseFloat( intent.getStringExtra( "Lon" ) );
            searchNearLocations( latitude, longitude );
        }
    }
}