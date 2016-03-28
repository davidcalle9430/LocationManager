package com.calle.david.locationmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void toSituationActivity(View view){
        Intent intent = new Intent( this , MainActivity.class );
        startActivity(intent);
    }

    public void toGPSLocationActivity(View view){
        Intent intent = new Intent( this , SearchActivity.class );
        startActivity( intent );
    }
    public void toGamificationActivity(View view){
        Intent intent = new Intent( this , Gamification.class );
        startActivity( intent );
    }
}
