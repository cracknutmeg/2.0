package com.allpoint.FCM;

import android.app.Notification;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

import com.allpoint.R;
import com.urbanairship.push.PushMessage;
import com.urbanairship.push.notifications.ActionsNotificationExtender;
import com.urbanairship.push.notifications.NotificationFactory;
import com.urbanairship.util.NotificationIdGenerator;
import com.urbanairship.util.UAStringUtil;

public class CustomNotificationFactory extends NotificationFactory {

    public CustomNotificationFactory(Context context) {
        super(context);
    }

    @Override
    public Notification createNotification(@NonNull PushMessage message, int notificationId) {
        // do not display a notification if there is not an alert
        if (UAStringUtil.isEmpty(message.getAlert())) {
            return null;
        }

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext())
                .setContentTitle("Notification title")
                .setContentText(message.getAlert())
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.app_icon);

        // Notification action buttons
        builder.extend(new ActionsNotificationExtender(getContext(), message, notificationId));

        return builder.build();
    }

    @Override
    public int getNextId(@NonNull PushMessage pushMessage) {
        return NotificationIdGenerator.nextID();
    }

    /**
     * Checks if the push message requires a long running task. If {@code true}, the push message
     * will be scheduled to process at a later time when the app has more background time. If {@code false},
     * the app has approximately 10 seconds total for {@link #createNotification(PushMessage, int)}
     * and {@link #getNextId(PushMessage)}.
     * <p>
     * Apps that return {@code false} are highly encouraged to add {@code RECEIVE_BOOT_COMPLETED} so
     * the push message will persist between device reboots.
     *
     * @param message The push message.
     * @return {@code true} to require long running task, otherwise {@code false}.
     */
    @Override
    public boolean requiresLongRunningTask(PushMessage message) {
        return false;
    }

}
