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

import com.calle.david.locationmanager.data.LocationContract;

public class MainActivity extends AppCompatActivity {

    private  LocationAdapter mAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Cursor cursor = getContentResolver().query(LocationContract.LocationEntry.CONTENT_URI, null, null, null, null);
        while(cursor.moveToNext()){
            Log.v("Dato[0]", cursor.getString(0));
            Log.v("Dato[1]", cursor.getString(1));
        }

        //Cursor cursor = getContentResolver().query(LocationContract.LocationEntry.CONTENT_URI, null, null, null, null);
        mAdapter = new LocationAdapter(getApplicationContext(), cursor, 0);
        listView = (ListView) findViewById(R.id.view_activity_main);
        listView.setAdapter(mAdapter);
    }
}
