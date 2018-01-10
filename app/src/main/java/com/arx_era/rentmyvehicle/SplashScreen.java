package com.arx_era.rentmyvehicle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

/**
 * Created by Tronix99 on 08-01-2018.
 */

public class SplashScreen extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 1000;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {


                // Check for existing Google Sign In account, if the user is already signed in
                // the GoogleSignInAccount will be non-null.
                GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(SplashScreen.this);
                // This method will be executed once the timer is over
                // Start your app main activity
                if(account != null){
                        i = new Intent(SplashScreen.this, MainActivity.class);
                } else  if (account == null){
                        i = new Intent(SplashScreen.this, LoginActivity.class);
                }
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
