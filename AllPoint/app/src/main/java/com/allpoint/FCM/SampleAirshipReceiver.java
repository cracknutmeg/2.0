package com.allpoint.FCM;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.allpoint.activities.LoginActivity;
import com.allpoint.activities.phone.MessageActivity;
import com.allpoint.util.Constant;
import com.allpoint.util.Utils;
import com.urbanairship.AirshipReceiver;
import com.urbanairship.push.PushMessage;

public class SampleAirshipReceiver extends AirshipReceiver {

    private static final String TAG = "SampleAirshipReceiver";

    @Override
    protected void onChannelCreated(@NonNull Context context, @NonNull String channelId) {
        Log.i(TAG, "Channel created. Channel Id:" + channelId + ".");

        LoginActivity.uaChannelID = channelId;
    }

    @Override
    protected void onChannelUpdated(@NonNull Context context, @NonNull String channelId) {
        Log.i(TAG, "Channel updated. Channel Id:" + channelId + ".");
    }

    @Override
    protected void onChannelRegistrationFailed(Context context) {
        Log.i(TAG, "Channel registration failed.");
    }

    @Override
    protected void onPushReceived(@NonNull Context context, @NonNull PushMessage message, boolean notificationPosted) {
        Log.i(TAG, "Received push message. Alert: " + message.getAlert() + ". posted notification: " + notificationPosted);

    }

    @Override
    protected void onNotificationPosted(@NonNull Context context, @NonNull NotificationInfo notificationInfo) {
        Log.i(TAG, "Notification posted. Alert: " + notificationInfo.getMessage().getAlert() + ". NotificationId: " + notificationInfo.getNotificationId());
    }

    @Override
    protected boolean onNotificationOpened(@NonNull Context context, @NonNull NotificationInfo notificationInfo) {
        Log.i(TAG, "Notification opened. Alert: " + notificationInfo.getMessage().getAlert() + ". NotificationId: " + notificationInfo.getNotificationId());

        Intent messageIntent;
        boolean prod_build=LoginActivity.prod_build;
        if(prod_build==false)
        {
            messageIntent = new Intent(context, MessageActivity.class);
        }


        if (Utils.isTablet()) {
            messageIntent = new Intent(context, com.allpoint.activities.tablet.MessageActivity.class);
        } else {
            messageIntent = new Intent(context, com.allpoint.activities.phone.MessageActivity.class);
        }

		/*Intent messageIntent;
		if (Utils.isTablet()) {
			messageIntent = new Intent(context, com.allpoint.activities.tablet.MainMenuActivity.class);

		} else {
			messageIntent = new Intent(context, com.allpoint.activities.phone.MainMenuActivity.class);
		}*/

        String messageId = notificationInfo.getMessage().getRichPushMessageId();
        if (messageId != null && !messageId.isEmpty()) {
            // Logger.debug("Channel Notified of a notification opened with ID "
            // + messageId);

            // Launch the main activity to the message in the inbox
            messageIntent.putExtra(Constant.EXTRA_MESSAGE_ID, messageId);
            messageIntent.putExtra(Constant.EXTRA_NAVIGATE_ITEM,
                    Constant.INBOX_ITEM);
        }

        messageIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(messageIntent);


        // Return false here to allow Urban Airship to auto launch the launcher activity
        return false;
    }

    @Override
    protected boolean onNotificationOpened(@NonNull Context context, @NonNull NotificationInfo notificationInfo, @NonNull ActionButtonInfo actionButtonInfo) {
        Log.i(TAG, "Notification action button opened. Button ID: " + actionButtonInfo.getButtonId() + ". NotificationId: " + notificationInfo.getNotificationId());

        // Return false here to allow Urban Airship to auto launch the launcher
        // activity for foreground notification action buttons
        return false;
    }

    @Override
    protected void onNotificationDismissed(@NonNull Context context, @NonNull NotificationInfo notificationInfo) {
        Log.i(TAG, "Notification dismissed. Alert: " + notificationInfo.getMessage().getAlert() + ". Notification ID: " + notificationInfo.getNotificationId());
    }




}