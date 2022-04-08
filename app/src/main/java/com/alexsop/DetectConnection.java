package com.alexsop;

import android.content.Context;
import android.net.ConnectivityManager;

public class DetectConnection {
    public static boolean hasInternetConnection(Context context)
    {
        ConnectivityManager conManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return (conManager.getActiveNetworkInfo() != null && conManager.getActiveNetworkInfo().isAvailable() && conManager.getActiveNetworkInfo().isConnected());
    }
}
