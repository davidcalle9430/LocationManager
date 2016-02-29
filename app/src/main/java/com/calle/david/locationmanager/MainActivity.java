package com.calle.david.locationmanager;

import android.content.ContentValues;
import android.database.Cursor;
import android.location.LocationProvider;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.calle.david.locationmanager.data.LocationContract;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Cursor cursor = getContentResolver().query(LocationContract.LocationEntry.CONTENT_URI, null, null, null, null);
        while(cursor.moveToNext()){
            Log.v("Dato[0]", cursor.getString(0));
            Log.v("Dato[1]", cursor.getString(1));
        }
        //Log.wtf( "INSETADO" , uri.toString());
    }
}
