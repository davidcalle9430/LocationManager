package com.calle.david.locationmanager.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.calle.david.locationmanager.MainActivity;
import com.calle.david.locationmanager.R;

import java.util.Calendar;

public class SituationService extends Service {
    public final static String[] STATES = { "Estudiando" , "Comida" , "Transporte" };
    public final static String STOP_SITUTATION_SERVICE = "com.movil.sagrado.corazon.parar.situacion";

    private StopServiceReceiver receiver;
    private TimeChangeReceiver timeReceiver;
    private String currentState;

    public String getState(){
        return currentState;
    }

    public void setState(String state){
        currentState = state;
    }

    public SituationService() {}

    public void showNotification( String state ){

        Intent targetIntent = new Intent( this, MainActivity.class );
        targetIntent.putExtra( MainActivity.SITUATION_EXTRA , currentState );
        Intent stopService = new Intent( STOP_SITUTATION_SERVICE );
        PendingIntent stop = PendingIntent.getBroadcast(this, 0, stopService, PendingIntent.FLAG_UPDATE_CURRENT );
        PendingIntent contentIntent = PendingIntent.getActivity( this, 0, targetIntent, PendingIntent.FLAG_UPDATE_CURRENT );

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon( R.drawable.common_full_open_on_phone )
                        .setContentTitle("Buscando: " + state)
                        .setContentText( "El estado es " + state )
                        .addAction( R.drawable.common_ic_googleplayservices  , "Parar", stop  );
        int NOTIFICATION_ID = 12345;

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        builder.setStyle( inboxStyle );
        inboxStyle.setBigContentTitle("Buscando: " + state);


        builder.setContentIntent(contentIntent);
        NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.notify( NOTIFICATION_ID, builder.build() );
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        receiver = new StopServiceReceiver( this );
        timeReceiver = new TimeChangeReceiver( this );

        IntentFilter filter = new IntentFilter( STOP_SITUTATION_SERVICE );
        registerReceiver( receiver , filter );
        currentState = "Agua";
        showNotification( currentState );

        IntentFilter timeFilter = new IntentFilter( Intent.ACTION_TIME_TICK );
        registerReceiver( timeReceiver, timeFilter );

        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver( receiver );
        unregisterReceiver( timeReceiver );
    }
}
class StopServiceReceiver extends BroadcastReceiver {
    private Service service;

    public StopServiceReceiver(Service service){
        this.service = service;
    }
    @Override
    public void onReceive(Context context, Intent intent){
        service.stopSelf();
    }
}

class TimeChangeReceiver extends BroadcastReceiver {
    private SituationService service;

    public TimeChangeReceiver(SituationService service){
        this.service = service;
    }
    @Override
    public void onReceive(Context context, Intent intent){
        int hour = Calendar.getInstance().get( Calendar.HOUR_OF_DAY );
        Log.wtf( "Tiempo", "Hubo un cambio en el tiempo " + hour );
        if( hour > 0 && hour < 11 ){
            service.setState( "Estudiando" );
        }else if( hour >= 11 && hour < 12 ){
            service.setState( "Comida" );
        }else{
            service.setState( "Transporte" );
        }
        service.showNotification( service.getState() );
    }
}
