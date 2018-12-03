package com.allpoint.services;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * InternetConnectionManager
 *
 * @author: Vyacheslav.Shmakin
 * @version: 08.01.14
 */
public class InternetConnectionManager {

    private static InternetConnectionManager im = new InternetConnectionManager();
    private Context mContext;


    private InternetConnectionManager() {

    }

    public static InternetConnectionManager getInstance(Context mContext) {

        im.mContext = mContext;
        return im;
    }

    public boolean isConnected() {

        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

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
