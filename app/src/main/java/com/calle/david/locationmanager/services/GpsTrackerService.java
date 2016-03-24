package com.calle.david.locationmanager.services;
import android.app.AlertDialog;
import android.app.IntentService;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Context;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class GpsTrackerService extends Service implements LocationListener{
    //Contexto de la aplicacion, quitar que es publica
    public static Context mContext;

    // bandera para estado del GPS (habilitado)
    boolean isGPSEnabled = false;

    // bandera para red
    boolean isNetworkEnabled = false;

    // bandera para estado del GPS (obtener lugar)
    boolean canGetLocation = false;

    Location location; // localizacion
    double latitude; // latitud
    double longitude; // longitud

    // Distancia minima en metros para actualizacion
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 metros

    // Tiempo minimo de actualizacion por tiempo
    private static final long MIN_TIME_BW_UPDATES = 1000 * 5; // 5 segundos

    // Manager de Localizacion
    protected LocationManager locationManager;

    public final static String GPS_LOCATION_CHANGE_FILTER= "com.calle.david.locationmanager.services.gpscambio";
    public final static String GPS_GET_LOCATION_FILTER= "com.calle.david.locationmanager.services.darcambio";


    //Constructor
    public GpsTrackerService(Context context) {
        this.mContext = context; //Obtener el contexto
        getLocation();
    }
    public GpsTrackerService(){

    }
    // al parecer el onCreate nunca se llama
    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter filter = new IntentFilter(GpsTrackerService.GPS_GET_LOCATION_FILTER);
        registerReceiver(new GPSChange(), filter );
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        IntentFilter filter = new IntentFilter(GpsTrackerService.GPS_GET_LOCATION_FILTER);
        registerReceiver(new GPSChange(), filter);
        getLocation();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public Location getLocation(){
        try {
            //Se obtiene localización
            locationManager = (LocationManager) mContext
                    .getSystemService(LOCATION_SERVICE);

            // se obtiene el estado del GPS
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // obteniendo estado de la red
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (isGPSEnabled || isNetworkEnabled) {
                // no hay posibilidad de localizacion
                this.canGetLocation = true;
                // Obtener localización de la red
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                // si se inicia el GOS obtener longitud y latitud
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            Log.wtf("PEPE", "Error fatal");
            e.printStackTrace();
        }

        return location;
    }

    /**
     * Detener el GPS
     * Esta funcion detiene el uso de GPS por la aplicacion
     * */
    public void stopUsingGPS(){
        if(locationManager != null){
            locationManager.removeUpdates(GpsTrackerService.this);
        }
    }

    /**
     * Obtener latitud
     * */
    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }

        // return latitude
        return latitude;
    }

    /**
     * Obtener longitud
     * */
    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }
        return longitude;
    }

    /**
     * verificar si esta disponible el GPS o la red
     * */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    /**
     Funcion que muestra la pantalla de configuración
     * */
    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        // Titulo del alertDialog
        alertDialog.setTitle("Configuracion GPS");

        // Mensaje del alertDialog
        alertDialog.setMessage("Debe ha bilitar el GPS, desea ir al menu de configuracion?");

        // Evento del boton Settings
        alertDialog.setPositiveButton("Configuracion", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        // evento presionar cancelar
        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // mostrar alertDialog
        alertDialog.show();
    }

    @Override
    public void onLocationChanged(Location location) {
        updateLocation();
    }

    public void updateLocation(){
        getLocation();
        Intent intent = new Intent(GPS_LOCATION_CHANGE_FILTER);
        intent.putExtra( "Lat" , latitude + "" );
        intent.putExtra( "Lon" , longitude + "" );
        mContext.sendBroadcast(intent);
    }
    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }



    class GPSChange extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent){
            Log.wtf("senial", "Recibo la signal");
            updateLocation();
        }
    }
}