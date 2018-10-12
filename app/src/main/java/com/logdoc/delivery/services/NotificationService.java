package com.logdoc.delivery.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;

import com.logdoc.delivery.R;
import com.logdoc.delivery.activities.MainActivity;

import java.util.ArrayList;

public class NotificationService extends Service {

    public static final String ACTION_ON_BOOT_START_FOREGROUND = "ACTION_ON_BOOT_START_FOREGROUND";
    private final String NOTIFICATION_CHANNEL_ID = "LOGDOC_CHANNEL_ID";
    private final String NOTIFICATION_CHANNEL_NAME = "Уведомления о новых заказах";
    private final String WAKELOCK_TAG = "NOTIFICATION_SERVICE_WAKELOCK";
    private final int NOTIFICATION_ID = 123452345;

    private long TEN_MINUTE_MILLIS = 10 * 60 * 1000L;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);


        return START_NOT_STICKY;
    }


    public void startForeground(String username) {
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        PowerManager.WakeLock wakeLock;
        if (powerManager != null) {
            wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, WAKELOCK_TAG);
            wakeLock.acquire(TEN_MINUTE_MILLIS);
        }

        startForeground(NOTIFICATION_ID, getNotification(MainActivity.class,
                getString(R.string.app_name),
                getString(R.string.text_notification_description) + " " + username, false));
    }

    public void start(String username) {
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        PowerManager.WakeLock wakeLock;
        if (powerManager != null) {
            wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, WAKELOCK_TAG);
            wakeLock.acquire(TEN_MINUTE_MILLIS);
        }

        getNotification(MainActivity.class,
                getString(R.string.app_name),
                getString(R.string.text_notification_description) + " " + username, true);
    }

    public Notification getNotification(final Class classForIntent, final String contentTitle, final String contentText, boolean notify) {

        PendingIntent pendingIntent = PendingIntent.getActivity(NotificationService.this, 1, new Intent(NotificationService.this, classForIntent), 0);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(NotificationService.this, NOTIFICATION_CHANNEL_ID)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        R.mipmap.ic_launcher))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{300, 300})
                .setSound(Uri.parse("android.resource://"
                        + getPackageName() + "/"
                        + R.raw.notification_sound));


        if (notify) {

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            if (notificationManager != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                            NOTIFICATION_CHANNEL_NAME,
                            NotificationManager.IMPORTANCE_DEFAULT);
                    notificationManager.createNotificationChannel(channel);
                }
                notificationManager.notify(0, notificationBuilder.build());
            }
        }

        return notificationBuilder.build();
    }

}

