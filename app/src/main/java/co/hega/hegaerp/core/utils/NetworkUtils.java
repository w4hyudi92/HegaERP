package co.hega.hegaerp.core.utils;

import android.content.Context;
import android.net.ConnectivityManager;

public class NetworkUtils {
    public static boolean isConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService("connectivity");
        return manager.getActiveNetworkInfo() != null && manager.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
