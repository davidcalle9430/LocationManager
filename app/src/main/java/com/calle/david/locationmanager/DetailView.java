package com.calle.david.locationmanager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Icon;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.calle.david.locationmanager.data.LocationContract;
import com.calle.david.locationmanager.data.LocationContract.LocationEntry;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

public class DetailView extends AppCompatActivity {

    private ListView listaView;
    private List<String> datos;
    private ArrayAdapter<String> adaptador;

    private String lat;
    private String lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        datos = new ArrayList<>();
        listaView = (ListView) findViewById(R.id.listView);

        ImageView image= (ImageView)findViewById(R.id.imageView);
        image.setImageResource(R.drawable.javeriana);

        Intent i = getIntent();
        String locationId = i.getStringExtra(MainActivity.ID_LOCATION);
        Log.wtf("Id-ubicacion", locationId);
        Cursor information = getContentResolver().query(
                LocationContract.InformationEntry.buildInformationUri(Long.parseLong(locationId)),
                null, null, null, null, null
        );

        String[] queryArgs = { locationId };
        //obtengo los datos principales de la ubicacion
        Cursor baseData = getContentResolver().query( LocationEntry.CONTENT_URI , null , LocationEntry._ID +" = ?", queryArgs  , null);
        while( baseData.moveToNext() ){
            datos.add("   -   NOMBRE:");
            setTitle(baseData.getString(baseData.getColumnIndex(LocationEntry.COLUMN_LOCATION_NAME)));
            datos.add(baseData.getString(baseData.getColumnIndex(LocationEntry.COLUMN_LOCATION_NAME)));
            datos.add("   -   LONGITUD:");
            datos.add( baseData.getString( baseData.getColumnIndex( LocationEntry.COLUMN_COOD_LON) ));
            datos.add("   -   LATITUD:");
            datos.add(baseData.getString( baseData.getColumnIndex( LocationEntry.COLUMN_COORD_LAT ) ));
            lat =  baseData.getString(baseData.getColumnIndex(LocationEntry.COLUMN_COORD_LAT));
            lon =  baseData.getString(baseData.getColumnIndex(LocationEntry.COLUMN_COOD_LON));
            datos.add("   -   DESCRIPCION:");
            datos.add( baseData.getString( baseData.getColumnIndex( LocationEntry.COLUMN_DESCRIPTION ) ));
            datos.add("   -   RADIO DE BUSQUEDA:");
            datos.add(baseData.getString(baseData.getColumnIndex(LocationEntry.COLUMN_RADIO)));

        }

        while (information.moveToNext()) {
            String key = information.getString(information.getColumnIndex(LocationContract.InformationEntry.COLUMN_KEY));

            String value = information.getString(information.getColumnIndex(LocationContract.InformationEntry.COLUMN_VALUE));
            Log.wtf("datos", key + ": " + value);
            datos.add("   -   "+key.toUpperCase()+":");
            datos.add(value);
        }
        adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datos){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                ((TextView)view).setTextColor(Color.WHITE);
                if (position % 2 == 1) {
                    view.setBackgroundColor(Color.parseColor("#7373E4"));
                } else {
                    view.setBackgroundColor(Color.parseColor("#1A1AD3"));

                }

            return view;
        }
        };
        listaView.setAdapter(adaptador);
       // viewInGoogleMaps();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        // map intent

    }

    public void viewInGoogleMaps(View v){
        Uri gmmIntentUri = Uri.parse("google.navigation:q="+lat+"," +lon+"&mode=w");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
}
