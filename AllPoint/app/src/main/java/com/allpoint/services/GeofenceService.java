package com.allpoint.services;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

import com.allpoint.AtmFinderApplication;
import com.allpoint.util.Constant;
import com.allpoint.util.GPSTracker;
import com.allpoint.util.Settings;
import com.allpoint.util.Utils;
import com.gimbal.android.Gimbal;
import com.gimbal.android.PlaceEventListener;
import com.gimbal.android.PlaceManager;
import com.gimbal.android.Visit;
import com.urbanairship.UAirship;
import com.urbanairship.push.PushManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;

public class GeofenceService extends Service implements WebServiceListner {
    private PlaceEventListener placeEventListener;
    private PlaceManager placeManager;
    AtmFinderApplication appContext;

    @Override
    public void onCreate() {

        super.onCreate();

        Gimbal.setApiKey(this.getApplication(), Constant.GIMBALE_API_KEY);

        placeEventListener = new PlaceEventListener() {

            final PushManager urbanPushMgr = UAirship.shared().getPushManager();

            @Override
            public void onVisitStart(Visit visit) {
                if (Utils.getLoginStatus()) {
                    if (Settings.isSetGeofence()) {
                        HashSet<String> tags = new HashSet<>(// Get the
                                // current
                                // set of
                                // tags
                                urbanPushMgr.getTags());

                        tags.add(visit.getPlace().getName());// Add the
                        // geofence-nearby-<NAME>
                        // tag
                        urbanPushMgr.setTags(tags);// Set the tags

                    }
                    pushToEDW();
                }
            }

            @Override
            public void onVisitEnd(Visit visit) {
                if (Utils.getLoginStatus()) {
                    if (Settings.isSetGeofence()) { // Get the current set of
                        // tags
                        HashSet<String> tags = new HashSet<String>(
                                urbanPushMgr.getTags());

                        tags.remove(visit.getPlace().getName());// Remove the
                        // geofence-nearby-<NAME>
                        // tag
                        urbanPushMgr.setTags(tags);
                    }
                    pushToEDW();
                }
            }
        };
        placeManager = PlaceManager.getInstance();
        placeManager.addListener(placeEventListener);
        placeManager.startMonitoring();
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    String createGeorequest() {
        GPSTracker gps = new GPSTracker(getApplicationContext());
        TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        String uuid = tManager.getDeviceId();

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        Date date = new Date();
        String value = "";

//		System.out.println("#geo : " + gps.getLatitude());
//		System.out.println("#geo : " + gps.getLongitude());

        if (gps.getLatitude() != 0.0 && gps.getLongitude() != 0.0) {
            value = "<GeofenceRequest ver=\"build:" + Build.VERSION.RELEASE
                    + "\" platform=\"android\" DeviceId=\"" + uuid + "\">"
                    + "<Geofence>" + "<Email>" + Utils.getUserName().trim()
                    + "</Email>" + "<Date>" + dateFormat.format(date)
                    + "</Date>" + "<Latitude>" + gps.getLatitude()
                    + "</Latitude>" + "<Longitude>" + gps.getLongitude()
                    + "</Longitude>" + "</Geofence>" + "</GeofenceRequest>";
        }
        return value;

    }

    @Override
    public void onResult(String result) {

    }

    private void pushToEDW() {

//		System.out.println("#geo request : " + createGeorequest());
//		System.out.println("#geo username : " + Utils.getUserName());

        appContext = (AtmFinderApplication) getApplicationContext();
        LoadWebServiceAsync callApi = new LoadWebServiceAsync(
                getApplicationContext(), GeofenceService.this,
                createGeorequest(), Constant.EDW_LINK,
                Constant.EDW_METHOD_NAME, Constant.EDW_SOAP_ACTION,
                Constant.EDW_NAMESPACE, Utils.getUserName().trim(), appContext.sessionToken);
        callApi.execute();
    }

    @Override
    public void onRunning() {
        // TODO Auto-generated method stub

    }
}


//package com.allpoint.services;
//
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Locale;
//
//import android.app.Service;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Build;
//import android.os.IBinder;
//import android.telephony.TelephonyManager;
//
//import com.allpoint.util.Constant;
//import com.allpoint.util.GPSTracker;
//import com.allpoint.util.Settings;
//import com.allpoint.util.Utils;
//import com.gimbal.android.Gimbal;
//import com.gimbal.android.PlaceEventListener;
//import com.gimbal.android.PlaceManager;
//import com.gimbal.android.Visit;
//import com.urbanairship.UAirship;
//import com.urbanairship.location.RegionEvent;
//import com.urbanairship.push.PushManager;
//import com.urbanairship.richpush.RichPushManager;
//
//public class GeofenceService extends Service implements WebServiceListner {
//	private PlaceEventListener placeEventListener;
//	private PlaceManager placeManager;
//	
//	
//	private static final String SOURCE = "Gimbal";
//	private final List<Listener> listeners = new ArrayList<>();
//	    
//	@Override
//	public void onCreate() {
//
//		
//		System.out.println("#UAG : " + "Gimbal Service started");
//		
//		super.onCreate();
//		
//		Gimbal.setApiKey(this.getApplication(), Constant.GIMBALE_API_KEY);
//
//		placeEventListener = new PlaceEventListener() {
//			
//			
//
//			final PushManager urbanPushMgr = UAirship.shared().getPushManager();
//			
//			@Override
//			public void onVisitStart(final Visit visit) {
//				if (Utils.getLoginStatus()) {
//					if (Settings.isSetGeofence()) {
//						HashSet<String> tags = new HashSet<String>(// Get the current set of tags
//								
//						urbanPushMgr.getTags());
//												
//						System.out.println("#UAG : " + tags);
//
//						tags.add(visit.getPlace().getName());// Add the
//																// geofence-nearby-<NAME>
//																// tag
//						urbanPushMgr.setTags(tags);// Set the tags
//						
//						/* Updated Code For Location Trigger - 2017 April 07
//						*/
//						UAirship.shared(new UAirship.OnReadyCallback() {
//			                @Override
//			                public void onAirshipReady(UAirship airship) {
//			                	
//			                	System.out.println("#onVisitStart - onAirshipReady");
//			                	
//			                    RegionEvent enter = new RegionEvent(visit.getPlace().getIdentifier(), SOURCE, RegionEvent.BOUNDARY_EVENT_ENTER);
//			                    System.out.println("#onVisitStart - onAirshipReady - place : " + visit.getPlace().getIdentifier());
//			                    airship.getAnalytics().addEvent(enter);
//
//			                    synchronized (listeners) {
//			                        for (Listener listener : new ArrayList<>(listeners)) {
//			                            listener.onRegionEntered(enter, visit);
//			                        }
//			                    }
//			                }
//			            });
//					}
//					pushToEDW();
//				}
//			}
//
//			@Override
//			public void onVisitEnd(final Visit visit) {
//				//for testng commented
//				if (Utils.getLoginStatus()) {
//					if (Settings.isSetGeofence()) { // Get the current set of
//													// tags
//						HashSet<String> tags = new HashSet<String>(
//								urbanPushMgr.getTags());
//
//						tags.remove(visit.getPlace().getName());// Remove the
//																// geofence-nearby-<NAME>
//																// tag
//						urbanPushMgr.setTags(tags);
//						
//						/* Updated Code For Location Trigger - 2017 April 07
//						*/
//						UAirship.shared(new UAirship.OnReadyCallback() {
//							
//			                @Override
//			                public void onAirshipReady(UAirship airship) {
//			                	
//			                	System.out.println("#onVisitEnd - onAirshipReady");
//			                	
//			                    RegionEvent exit = new RegionEvent(visit.getPlace().getIdentifier(), SOURCE, RegionEvent.BOUNDARY_EVENT_EXIT);
//			                    
//			                    System.out.println("#onVisitEnd - onAirshipReady - place : " + visit.getPlace().getIdentifier());
//			                    
//			                    airship.getAnalytics().addEvent(exit);
//
//			                    synchronized (listeners) {
//			                        for (Listener listener : new ArrayList<>(listeners)) {
//			                            listener.onRegionExited(exit, visit);
//			                            System.out.println("#listeners");
//			                        }
//			                    }
//			                }
//			            });
//						
//					}
//					pushToEDW();
//				}
//			}
//		};
//		placeManager = PlaceManager.getInstance();
//		placeManager.addListener(placeEventListener);
//		placeManager.startMonitoring();
//	}
//
//	@Override
//	public IBinder onBind(Intent intent) {
//
//		return null;
//	}
//
//	String createGeorequest() {
//		GPSTracker gps = new GPSTracker(getApplicationContext());
//		TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//		String uuid = tManager.getDeviceId();
//		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss",
//				Locale.US);
//		Date date = new Date();
//		String value = "";
//		if (gps.getLatitude() != 0.0 && gps.getLongitude() != 0.0) {
//			value = "<GeofenceRequest ver=\"build:" + Build.VERSION.RELEASE
//					+ "\" platform=\"android\" DeviceId=\"" + uuid + "\">"
//					+ "<Geofence>" + "<Email>" + Utils.getUserName()
//					+ "</Email>" + "<Date>" + dateFormat.format(date)
//					+ "</Date>" + "<Latitude>" + gps.getLatitude()
//					+ "</Latitude>" + "<Longitude>" + gps.getLongitude()
//					+ "</Longitude>" + "</Geofence>" + "</GeofenceRequest>";
//		}
//		return value;
//
//	}
//
//	@Override
//	public void onResult(String result) {
//
//	}
//
//	private void pushToEDW() {
//
//		String uName = "";
//		String token = "";
//
//		LoadWebServiceAsync callApi = new LoadWebServiceAsync(
//				getApplicationContext(), GeofenceService.this,
//				createGeorequest(), Constant.EDW_LINK,
//				Constant.EDW_METHOD_NAME, Constant.EDW_SOAP_ACTION,
//				Constant.EDW_NAMESPACE, uName, token);
//		callApi.execute();
//	}
//
//	@Override
//	public void onRunning() {
//		// TODO Auto-generated method stub
//
//	}
//	
//	
//	/**
//     * Adapter listener.
//     */
//    public interface Listener {
//
//        /**
//         * Called when a Urban Airship Region enter event is created from a Gimbal Visit.
//         *
//         * @param event The Urban Airship event.
//         * @param visit The Gimbal visit.
//         */
//        void onRegionEntered(RegionEvent event, Visit visit);
//
//        /**
//         * Called when a Urban Airship Region exit event is created from a Gimbal Visit.
//         *
//         * @param event The Urban Airship event.
//         * @param visit The Gimbal visit.
//         */
//        void onRegionExited(RegionEvent event, Visit visit);
//    }
//}
