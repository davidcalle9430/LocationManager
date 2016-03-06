package com.calle.david.locationmanager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.calle.david.locationmanager.data.LocationContract;

public class DetailView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        String locationId = i.getStringExtra( MainActivity.ID_LOCATION );
        Log.wtf("Id-ubicacion", locationId);
        Cursor information = getContentResolver().query(
                LocationContract.InformationEntry.buildInformationUri(Long.parseLong(locationId)),
                null, null, null, null, null
        );
        while( information.moveToNext() ){
            String key = information.getString( information.getColumnIndex( LocationContract.InformationEntry.COLUMN_KEY ) );

            String value = information.getString( information.getColumnIndex( LocationContract.InformationEntry.COLUMN_VALUE )  );
            Log.wtf( "datos" , key + ": " + value );
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
