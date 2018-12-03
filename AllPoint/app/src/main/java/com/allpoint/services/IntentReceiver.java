package com.allpoint.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.allpoint.AtmFinderApplication;
import com.allpoint.util.Utils;
import com.urbanairship.UAirship;
import com.urbanairship.push.PushManager;

/**
 * IntentReceiver
 *
 * @author: Vyacheslav.Shmakin
 * @version: 08.01.14
 */
public class IntentReceiver extends BroadcastReceiver {

    @Override
    public final void onReceive(final Context aContext, final Intent intent) {

        String action = intent.getAction();

        if (action.equals(PushManager.ACTION_NOTIFICATION_OPENED)) {
            Intent lLaunch;
            if (Utils.isTablet()) {
                lLaunch = new Intent(AtmFinderApplication.getContext(),
                        com.allpoint.activities.tablet.MessageActivity.class);
            } else {
                lLaunch = new Intent(AtmFinderApplication.getContext(),
                        com.allpoint.activities.phone.MessageActivity.class);
            }

            lLaunch.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);

            UAirship.shared().getApplicationContext().startActivity(lLaunch);

        }
    }
}
