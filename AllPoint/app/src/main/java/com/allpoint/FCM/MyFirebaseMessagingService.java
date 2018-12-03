package com.allpoint.FCM;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.RemoteViews;

import com.allpoint.R;
import com.allpoint.activities.LoginActivity;
import com.allpoint.util.Constant;
import com.allpoint.util.Utils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.urbanairship.push.fcm.AirshipFirebaseInstanceIdService;
import com.urbanairship.push.fcm.AirshipFirebaseMessagingService;

import static com.allpoint.AtmFinderApplication.getContext;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages
        // are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data
        // messages are the type
        // traditionally used with GCM. Notification messages are only received here in
        // onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated
        // notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages
        // containing both notification
        // and data payloads are treated as notification messages. The Firebase console always
        // sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

       /* // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

           *//* if (*//**//* Check if data needs to be processed by long running job *//**//* true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                scheduleJob();
            } else {
                // Handle message within 10 seconds
                handleNow();
            }*//*

        }*/

        AirshipFirebaseMessagingService.processMessageSync(getContext(), remoteMessage);

        /*// Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

            sendNotification(remoteMessage.getNotification().getBody());

        }*/
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]


    // [START on_new_token]

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        //sendRegistrationToServer(token);

        AirshipFirebaseInstanceIdService.processTokenRefresh(getContext());


    }
    // [END on_new_token]

    /**
     * Schedule a job using FirebaseJobDispatcher.
     */


    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }

    /**
     * Persist token to third-party servers.
     * <p>
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     */
    /*private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 *//* Request code *//*, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_stat_ic_notification)
                        .setContentTitle(getString(R.string.fcm_message))
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 *//* ID of notification *//*, notificationBuilder.build());
    }*/


    void sendNotification(String messageBody) {
        // Generate notification
        Notification notification = new Notification();
        notification.icon = R.drawable.ic_stat_notify_message;
        notification.tickerText = Constant.NOTIFICATIONS_TEXT;
        notification.when = System.currentTimeMillis();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        RemoteViews contentView = new RemoteViews(getPackageName(),
                R.layout.notification);
        contentView.setImageViewResource(R.id.iViewNotificationIcon,
                R.drawable.app_icon);
        contentView.setTextViewText(R.id.tvNotificationSubject,
                Constant.NOTIFICATIONS_NAME);
        contentView.setTextViewText(R.id.tvNotificationMessage,
                Constant.NOTIFICATIONS_TEXT);
        contentView.setTextViewText(
                R.id.tvNotificationTime,
                DateFormat.format(Constant.NOTIFICATIONS_DATE_TIME_FORMAT,
                        System.currentTimeMillis()).toString());

        contentView.setTextViewText(R.id.tvNotificationsCount,String.valueOf(Utils.getLastUnreadCount()));

        notification.contentView = contentView;

        Class<?> activityClass;

        boolean p_build = LoginActivity.prod_build;

        if (p_build == false) {
            if (Utils.isTablet()) {
                activityClass = com.allpoint.activities.tablet.MessageActivity.class;
            } else {
                activityClass = com.allpoint.activities.phone.MessageActivity.class;
            }
        }

        if (Utils.isTablet()) {
            activityClass = com.allpoint.activities.tablet.MessageActivity.class;
        } else {
            activityClass = com.allpoint.activities.phone.MessageActivity.class;
        }

        //After Sim Request on 2017/06/14
        if (Utils.isTablet()) {
            activityClass = com.allpoint.activities.tablet.MainMenuActivity.class;

        } else {
            activityClass = com.allpoint.activities.phone.MainMenuActivity.class;
        }

        Intent notificationIntent = new Intent(this, activityClass);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        notification.contentIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        notification.flags |= Notification.FLAG_NO_CLEAR; // Do not clear the
        // notification
        notification.defaults |= Notification.DEFAULT_LIGHTS; // LED
        notification.defaults |= Notification.DEFAULT_VIBRATE; // Vibration
        notification.defaults |= Notification.DEFAULT_SOUND; // Sound

        if (notificationManager != null) {
            notificationManager.notify(Constant.NOTIFICATION_ID, notification);
        }
    }
}
