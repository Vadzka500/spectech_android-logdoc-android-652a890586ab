package com.logdoc.delivery.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.logdoc.delivery.authorization.AuthActivity;

//import com.crashlytics.android.Crashlytics;
//
//import io.fabric.sdk.android.Fabric;

public class SplashActivity extends AppCompatActivity {

    public static final int START_MAIN_ACTIVITY_DELAY_MILLIS = 300;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Fabric.with(this, new Crashlytics());

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                final Intent intent = new Intent(SplashActivity.this, AuthActivity.class);
                startActivity(intent);
                finish();
            }
        }, START_MAIN_ACTIVITY_DELAY_MILLIS);
    }
}