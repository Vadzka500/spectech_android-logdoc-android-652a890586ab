package com.logdoc.delivery;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Dmitry Ushkevich on 28.03.18.
 */
public abstract class Internet {
    public static boolean hasConnection(final Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo;
        if (cm != null) {
            wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            if (wifiInfo != null && wifiInfo.isConnected())
                return true;

            wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (wifiInfo != null && wifiInfo.isConnected())
                return true;

            wifiInfo = cm.getActiveNetworkInfo();

            return wifiInfo != null && wifiInfo.isConnected();
        } else return false;
    }
}