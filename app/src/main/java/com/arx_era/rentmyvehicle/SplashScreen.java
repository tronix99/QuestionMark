package com.arx_era.rentmyvehicle;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Tronix99 on 08-01-2018.
 */

public class SplashScreen extends Activity {

    FirebaseAuth mAuth;
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        TextView tvTitle = (TextView)findViewById(R.id.rmv);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/arkhip.ttf");
        tvTitle.setTypeface(tf);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {

                //initializing firebase auth object
                mAuth = FirebaseAuth.getInstance();

                if(mAuth.getCurrentUser() != null){
                    //that means user is already logged in
                    //so close this activity
                    finish();

                    //and open profile activity
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }else if (mAuth.getCurrentUser() == null){
                    //that means user is already logged in
                    //so close this activity
                    finish();

                    //and open profile activity
                    startActivity(new Intent(getApplicationContext(), LoginRegisterActivity.class));
                }
            }
        }, SPLASH_TIME_OUT);
    }
}
