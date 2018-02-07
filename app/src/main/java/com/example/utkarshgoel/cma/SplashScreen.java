package com.example.utkarshgoel.cma;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_SCREEN_TIME_OUT = 3000;

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spash_screen);

        if(isNetworkAvailable())
            Toast.makeText(this,"Host Reachable",Toast.LENGTH_LONG).show();
        else {
            Toast.makeText(this, "Host Unreachable", Toast.LENGTH_LONG).show();
            //System.exit(1);
        }

        new Handler().postDelayed(new Runnable() {

            /*  Showing splash screen with a timer.  */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Starts your app main activity
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_SCREEN_TIME_OUT);
    }

}
