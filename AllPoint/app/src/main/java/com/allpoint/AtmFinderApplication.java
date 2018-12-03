package com.allpoint;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;

import com.allpoint.activities.LoginActivity;
import com.allpoint.services.MemorizingTrustManager;
import com.allpoint.util.Constant;
import com.urbanairship.UAirship;

import java.io.InputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Timer;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

/**
 * AtmFinderApplication Service-class for working with Urban Airship API
 *
 * @author: Vyacheslav.Shmakin
 * @version: 08.01.14
 */
public class AtmFinderApplication extends Application {

    public static boolean isGeofenceOn = true;

    public static String uaChannelID = "";

    MemorizingTrustManager mtm;

    /**
     * It is app context.
     */

    public boolean setRegFlag = false;

    private static Context context;

    private Timer updateMessagesTimer;

    public boolean isEditProfile = false;

    public boolean isFirstTimeReg = false;

    public boolean isCardSuccess = false;

    public String sessionToken = "";

    //For First time user Register time use temp Token
    public String tempTokenForOTP = "";

    public String setEvent = "";


    public boolean setChangePassFromSettings = false;

    X509Certificate checkCertFull;

    /****************************** OnCreate ***********************************/

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();

        ///UAirship.shared().getPushManager().setUserNotificationsEnabled(true);

       /* AirshipConfigOptions airshipConfigOptions = new AirshipConfigOptions();
        airshipConfigOptions.gcmSender = Constant.URBAN_AIRSHIP_GCM_SENDER;

        airshipConfigOptions.developmentAppKey = Constant.URBAN_AIRSHIP_DEVELOPMENT_APP_KEY;
        airshipConfigOptions.developmentAppSecret = Constant.URBAN_AIRSHIP_DEVELOPMENT_APP_SECRET;
        airshipConfigOptions.productionAppKey = Constant.URBAN_AIRSHIP_PRODUCTION_APP_KEY;
        airshipConfigOptions.productionAppSecret = Constant.URBAN_AIRSHIP_PRODUCTION_APP_SECRET;
        airshipConfigOptions.inProduction = Constant.URBAN_AIRSHIP_IN_PRODUCTION;*/


        //startService(new Intent(this, GeofenceService.class));
        // TODO   Not allowed to start service Intent { cmp=com.allpoint/.services.GeofenceService }: app is in background uid UidRecord{8531375 u0a90 SVC  idle change:idle|uncached procs:1 seq(0,0,0)}

       /* UAirship.takeOff(this, new UAirship.OnReadyCallback() {
            @Override
            public void onAirshipReady(UAirship airship) {

                // Create a customized default notification factory
                DefaultNotificationFactory defaultNotificationFactory = new DefaultNotificationFactory(
                        getApplicationContext());

                // set icon to urban airship notification
                defaultNotificationFactory.setSmallIconId(R.drawable.app_icon);
                defaultNotificationFactory
                        .setColor(NotificationCompat.PRIORITY_MAX);
                // Set it
                airship.getPushManager().setNotificationFactory(
                        defaultNotificationFactory);
                // ua upgrade 6.0
                airship.getPushManager().setUserNotificationsEnabled(true);

            }
        });*/


        //TODO Need to handle
        /*if (Utils.isApplicationCreated()) {
            UAirship.shared().getRichPushManager().addListener(this);
            Utils.setApplicationCreated();

            updateMessagesTimer = new Timer();
            updateMessagesTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    refreshRichPushInbox();
                }
            }, 0, Constant.URBAN_AIRSHIP_UPDATE_TIMER);
        }*/


        boolean qc_build = LoginActivity.prod_build;
        System.out.println("ATM finder qc build = " + qc_build);
        if (qc_build == false) {
            activiateCertificate();

            System.out.println("Certificate activated in ATM finder");
        }


    }

    /****************************** Urban Airship ***********************************/

    public static int getUnreadCounter() {
        // replace ua
        return UAirship.shared().getInbox().getUnreadCount();
    }


    @Override
    public final void onTerminate() {
        //UAirship.shared().getInbox().removeListener(this);

        updateMessagesTimer.purge();
        updateMessagesTimer.cancel();
        super.onTerminate();
    }

    /**
     * @return Returns app's context
     */
    public static Context getContext() {
        return AtmFinderApplication.context;
    }

    private String lastMessageId = "";

    /*void refreshRichPushInbox() {

        if (!UAirship.shared().getRichPushManager().isRefreshingMessages()) {

            UAirship.shared().getRichPushManager().refreshMessages();
            UAirship.shared().getRichPushManager().updateUser();
        }
    }*/

    /*
    @Override
    public void onUpdateMessages(boolean isSuccess) {
        if (UAirship.shared().getRichPushManager().getRichPushInbox()
                .getMessages().size() != 0) {
            RichPushMessage lastMsg = UAirship.shared().getRichPushManager()
                    .getRichPushInbox().getMessages().get(0);

            // If last message is not same too current message
            // Than we have received a new message. Then generate notification
            // message
            if (!lastMessageId.equals(lastMsg.getMessageId())) {
                lastMessageId = lastMsg.getMessageId();
                if (Utils.getLastUnreadCount() != UAirship.shared()
                        .getRichPushManager().getRichPushInbox()
                        .getUnreadCount()) {
                    if (Utils.getLastUnreadCount() < UAirship.shared()
                            .getRichPushManager().getRichPushInbox()
                            .getUnreadCount()) {
                        if (Utils.isPlayServicesAvailable(this)) {
                            Notify();
                        }
                    }
                }
            }
        }
    }*/

    /*void Notify() {
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
        contentView.setTextViewText(
                R.id.tvNotificationsCount,
                String.valueOf(UAirship.shared().getRichPushManager()
                        .getRichPushInbox().getUnreadCount()
                        - Utils.getLastUnreadCount()));
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

        notificationManager.notify(Constant.NOTIFICATION_ID, notification);
    }*/

    /*@Override
    public void onUpdateUser(boolean b) {
    }*/

    /****************************** Certificate Pinning ***********************************/

    private void activiateCertificate() {


        // Here, the MemorizingTrustManager is activated for HTTPS
        try {
            MemorizingTrustManager.setKeyStoreFile("private", Constant.CERTIFICATE_PATH);

            SSLContext sc = SSLContext.getInstance("TLS");
            mtm = new MemorizingTrustManager(this, getServerCertificate());
            sc.init(null, new X509TrustManager[]{mtm},
                    new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(
                    mtm.wrapHostnameVerifier(HttpsURLConnection.getDefaultHostnameVerifier()));

            // disable redirects to reduce possible confusion
            HttpsURLConnection.setFollowRedirects(false);

        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    String getServerCertificate() {
        InputStream keyStoreInputStream = null;
        X509Certificate certificateToReturn = null;

        try {
            AssetManager assetManager = this.getAssets();
            keyStoreInputStream = assetManager.open(Constant.CERTIFICATE_PATH_DEV_56);

            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            Certificate ca;

            ca = cf.generateCertificate(keyStoreInputStream);
            certificateToReturn = ((X509Certificate) ca);

            keyStoreInputStream.close();

        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            //keyStoreInputStream.close();
        }

        // check Name
        return certificateToReturn.getIssuerX500Principal().getName();
        //return certificateToReturn;
    }
    //For Production
	/*X509Certificate getServerCertificate() 
	{
		InputStream keyStoreInputStream=null;
		X509Certificate certificateToReturn = null;

		try {
			AssetManager assetManager = this.getAssets();
			keyStoreInputStream = assetManager.open(Constant.CERTIFICATE_PATH_PEN_56);
			
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			Certificate ca;

			ca = cf.generateCertificate(keyStoreInputStream);
			certificateToReturn=((X509Certificate) ca);

			keyStoreInputStream.close();

		} catch (Exception e) {
			//e.printStackTrace();
		} 
		finally {
			//keyStoreInputStream.close();
		}

		// check Name
		//return certificateToReturn.getIssuerX500Principal().getName(); 
		return certificateToReturn;
	}*/

}
