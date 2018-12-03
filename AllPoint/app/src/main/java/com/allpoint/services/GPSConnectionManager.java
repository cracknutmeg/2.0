package com.allpoint.services;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * InternetConnectionManager
 *
 * @author: Vyacheslav.Shmakin
 * @version: 08.01.14
 */
public class GPSConnectionManager {
    ConnectivityManager connectivityManager;

    public boolean isConnected() {
        if (connectivityManager == null) {
            return false;
        }

        NetworkInfo[] allNetworks = connectivityManager.getAllNetworkInfo();
        for (NetworkInfo networkInfo : allNetworks) {
            if (networkInfo.isConnected()) {
                return true;
            }
        }
        return false;
    }
}
