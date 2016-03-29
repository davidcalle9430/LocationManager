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
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

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
            datos.add(baseData.getString( baseData.getColumnIndex( LocationEntry.COLUMN_LOCATION_NAME ) ));
            datos.add("   -   LONGITUD:");
            datos.add( baseData.getString( baseData.getColumnIndex( LocationEntry.COLUMN_COOD_LON) ));
            datos.add("   -   LATITUD:");
            datos.add(baseData.getString( baseData.getColumnIndex( LocationEntry.COLUMN_COORD_LAT ) ));
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

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "DetailView Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.calle.david.locationmanager/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "DetailView Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.calle.david.locationmanager/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
