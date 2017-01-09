package com.example.administrator.icdsalesrep.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.icdsalesrep.R;
import com.example.administrator.icdsalesrep.util.Utility;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;

/**
 * Created by nitin on 23/08/16.
 */
public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        setupDeviceId();

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                Intent i;
                // This method will be executed once the timer is over
                if(Utility.getInstance().isRememberMe())
                    i = new Intent(SplashActivity.this, SignInActivity.class);
                else
                     i = new Intent(SplashActivity.this, RegisterDeviceActivity.class);

                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        }, SPLASH_TIME_OUT);

    }

    public void setupDeviceId() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    AdvertisingIdClient.Info adInfo = AdvertisingIdClient.getAdvertisingIdInfo(SplashActivity.this);
                    Utility.getInstance().saveDeviceId(adInfo.getId());
                    //Toast.makeText(SplashActivity.this,adInfo.getId(),Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
