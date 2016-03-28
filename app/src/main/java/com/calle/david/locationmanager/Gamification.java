package com.calle.david.locationmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.calle.david.locationmanager.services.GpsTrackerService;

public class Gamification extends AppCompatActivity  implements SensorEventListener {

    private final static Integer NO_CHALLENGE = 0;
    private final static Integer CHALLENGE_1 = 1;
    private final static Integer CHALLENGE_2 = 2;
    private final static Integer CHALLENGE_1_SECONDS = 30;
    private final static Integer CHALLENGE_2_SECONDS = 20;

    private final static float DARK = (float) 10.0;
    private final static float BRIGHT = (float) 300.0;


    private final static int COMPLETED = 1;
    private final static int NOT_COMPLETED = 0;

    private final static String STORED_CHALLENGE_1_STATE = "reto1";
    private final static String STORED_CHALLENGE_2_STATE = "reto2";
    private final static String SHARED_REF_NAME = "miguardado";

    private double currentTime = 0.0;
    private ProgressBar progressBar;
    private SensorManager sensorManager;
    private Sensor iluminationSensor;

    private long timeLastCalled;

    private Integer currentChallenge = NO_CHALLENGE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags( WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON );
        setContentView(R.layout.activity_gamification);
        GpsTrackerService.mContext = this;
        progressBar = ( ProgressBar ) findViewById(R.id.challenge_progress_bar);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        iluminationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        timeLastCalled =  SystemClock.elapsedRealtime();
        checkStorage();
    }

    private void checkStorage() {
        SharedPreferences prefs = getSharedPreferences( SHARED_REF_NAME , MODE_PRIVATE );
        Integer challenge1State = prefs.getInt( STORED_CHALLENGE_1_STATE, -1 );
        if ( challenge1State != -1 ) {
            updateProgressBar();
        }else{
            SharedPreferences.Editor editor = getSharedPreferences( SHARED_REF_NAME , MODE_PRIVATE ).edit();
            editor.putInt( STORED_CHALLENGE_1_STATE , NOT_COMPLETED );
            editor.putInt( STORED_CHALLENGE_2_STATE , NOT_COMPLETED );
            editor.commit();
        }
    }


    public void updateProgressBar(){
        SharedPreferences prefs = getSharedPreferences( SHARED_REF_NAME , MODE_PRIVATE );
        Integer challenge1State = prefs.getInt( STORED_CHALLENGE_1_STATE, NOT_COMPLETED );
        Integer challenge2State = prefs.getInt( STORED_CHALLENGE_2_STATE, NOT_COMPLETED );
        int progress = 0;
        if( challenge1State == COMPLETED ){ progress +=50; }
        if( challenge2State == COMPLETED ){ progress +=50; }
        progressBar.setProgress( progress );
    }
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, iluminationSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float light = event.values[0];
        checkChallenge(light);
    }

    public void checkCompletion(){
        if( currentChallenge == CHALLENGE_1 && currentTime > CHALLENGE_1_SECONDS ){ // logra reto 1
            SharedPreferences.Editor editor = getSharedPreferences( SHARED_REF_NAME , MODE_PRIVATE ).edit();
            editor.putInt(STORED_CHALLENGE_1_STATE, COMPLETED);
            editor.commit();
            updateProgressBar();
            // mostrar notifiacion
            new AlertDialog.Builder( this )
                    .setTitle("Adivinanza")
                    .setMessage("Lograste el reto 1. Tu tiempo fue: " + currentTime + "s")
                    .setCancelable( false )
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Whatever...
                        }
                    }).create().show();
            Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 500 milliseconds
            v.vibrate(500);


        }else if( currentChallenge == CHALLENGE_2 && currentTime > CHALLENGE_2_SECONDS ){ //logra reto 2
            SharedPreferences.Editor editor = getSharedPreferences( SHARED_REF_NAME , MODE_PRIVATE ).edit();
            editor.putInt( STORED_CHALLENGE_2_STATE , COMPLETED );
            editor.commit();
            updateProgressBar();

            //mostrat nofiicacion
            new AlertDialog.Builder( this )
                    .setTitle("Adivinanza")
                    .setMessage("Lograste el reto 2. Felicitaciones! Tu tiempo fue: " + currentTime + "s")
                    .setCancelable(false)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).create().show();
            Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 500 milliseconds
            v.vibrate(500);
        }
    }


    public void updateTime(){
        currentTime += (float) ( SystemClock.elapsedRealtime() - timeLastCalled ) / 1000;
        timeLastCalled = SystemClock.elapsedRealtime();
    }
    public void checkChallenge( float light ){
        updateTime();
        final int nextChallenge = getCurrentChallenge( light ); // obtengo reto dado por la adivinanza
        if( currentChallenge - nextChallenge != 0 ){
            checkCompletion();
            currentChallenge = nextChallenge;
            currentTime = 0.0;
        }
    }

    public int getCurrentChallenge( float light ){
        if( light < DARK ){
            return CHALLENGE_1;
        }else if( light > BRIGHT ){
            return CHALLENGE_2;
        }
        return NO_CHALLENGE;
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
