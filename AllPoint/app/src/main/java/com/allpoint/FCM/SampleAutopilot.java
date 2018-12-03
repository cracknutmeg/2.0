package com.allpoint.FCM;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

import com.allpoint.R;
import com.urbanairship.AirshipConfigOptions;
import com.urbanairship.Autopilot;
import com.urbanairship.UAirship;
import com.urbanairship.push.notifications.DefaultNotificationFactory;

import static com.urbanairship.UAirship.getApplicationContext;

public class SampleAutopilot extends Autopilot {

    @Override
    public void onAirshipReady(@NonNull UAirship airship) {

        airship.getPushManager().setUserNotificationsEnabled(true);

        // Android O
        if (Build.VERSION.SDK_INT >= 26) {
            Context context = getApplicationContext();
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            NotificationChannel channel = new NotificationChannel("customChannel",
                    context.getString(R.string.custom_channel_name),
                    NotificationManager.IMPORTANCE_DEFAULT);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        // Create a customized default notification factory
        DefaultNotificationFactory defaultNotificationFactory = new DefaultNotificationFactory(getApplicationContext());
        // set icon to urban airship notification
        defaultNotificationFactory.setSmallIconId(android.R.drawable.ic_lock_idle_alarm);
        defaultNotificationFactory.setColor(NotificationCompat.PRIORITY_MAX);
        defaultNotificationFactory.setLargeIcon(R.mipmap.ic_launcher);

        // Set it
        airship.getPushManager().setNotificationFactory(defaultNotificationFactory);
        // ua upgrade 6.0
        airship.getPushManager().setUserNotificationsEnabled(true);

    }

    @Override
    public AirshipConfigOptions createAirshipConfigOptions(@NonNull Context context) {

       /* AirshipConfigOptions options = new AirshipConfigOptions.Builder()
                .setDevelopmentAppKey(Constant.URBAN_AIRSHIP_DEVELOPMENT_APP_KEY)//"Your Development App Key"
                .setDevelopmentAppSecret(Constant.URBAN_AIRSHIP_DEVELOPMENT_APP_SECRET)//"Your Development App Secret"
                .setProductionAppKey(Constant.URBAN_AIRSHIP_PRODUCTION_APP_KEY)//"Your Production App Key"
                .setProductionAppSecret(Constant.URBAN_AIRSHIP_PRODUCTION_APP_SECRET)//"Your Production App Secret"
                .setInProduction(!BuildConfig.DEBUG)
                .setFcmSenderId(Constant.FCM_SENDER_ID) // FCM/GCM sender ID "Your Google API Project Number"
                .setNotificationIcon(R.mipmap.ic_launcher)
                .setNotificationAccentColor(context.getResources().getColor(R.color.color_accent))
                .setNotificationChannel("customChannel")
                .build();

        return options;*/

        return super.createAirshipConfigOptions(context);
    }
}
