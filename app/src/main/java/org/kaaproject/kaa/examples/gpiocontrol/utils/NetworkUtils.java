package org.kaaproject.kaa.examples.gpiocontrol.utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public final class NetworkUtils {

    private NetworkUtils() { }

    public static boolean isOn(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnected());
    }
}
