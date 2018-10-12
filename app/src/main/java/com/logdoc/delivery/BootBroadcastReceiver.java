package com.logdoc.delivery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.logdoc.delivery.services.NotificationService;

public class BootBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent serviceIntent = new Intent(context, NotificationService.class);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            context.startService(serviceIntent);
        } else {
            serviceIntent.setAction(NotificationService.ACTION_ON_BOOT_START_FOREGROUND);
            context.startForegroundService(serviceIntent);
        }

    }
}