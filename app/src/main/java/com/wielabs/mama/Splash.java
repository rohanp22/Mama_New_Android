package com.wielabs.mama;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.widget.Toast;

import com.wielabs.mama.Activities.Login;

public class Splash extends Activity {

    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        boolean checkConnection = new ApplicationUtility().checkConnection(this);
        if (checkConnection) {

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    Intent i = new Intent(Splash.this, Login.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                }

            }, SPLASH_TIME_OUT);

        } else {

            Toast.makeText(getApplicationContext(), "Connection not Found... Kindly Check Connection",
                    Toast.LENGTH_LONG).show();

        }

    }
}