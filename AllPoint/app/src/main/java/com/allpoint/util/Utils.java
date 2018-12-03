/**
 * @ com.atm1.util.Utils
 */
package com.allpoint.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allpoint.AtmFinderApplication;
import com.allpoint.R;
import com.allpoint.model.PointRecord;
import com.flurry.android.FlurryAgent;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.List;

/**
 * Utils
 *
 * @author: Vyacheslav.Shmakin
 * @version: 08.01.14
 */
public class Utils {

    private static Utils utils = new Utils();

    private Utils() {

    }

    public static Utils getInstance() {
        return utils;
    }

    public static boolean isAscendingSort = false;

    private static List<PointRecord> locationList = null;

    private static Marker infoWindowMarker;
    private static PointRecord record;
    private static PointRecord infoWindowRecord;

    private static LatLng currentPosition;
    private static LatLng myLocation;
    private static LatLng pointPosition;

    private static String appUrl;
    private static String queryText;

    private static int lastUnreadCount = 0;
    private static int checkedMessagesCount = 0;


    private static boolean isApplicationCreated = false;
    private static boolean onMainActivity = false;
    private static boolean settingsLoaded = false;
    private static boolean fromSplash = false;
    private static boolean actionEnabled = false;

    public static boolean isFirstLogin() {
        sharedpreferences = AtmFinderApplication.getContext()
                .getSharedPreferences(Constant.SETTINGS_FILENAME,
                        Context.MODE_PRIVATE);
        return sharedpreferences.getBoolean(Constant.FIRST_LOGIN, true);

        // return firstLogin;
    }

    public static void setFirstLogin(boolean firstLogin) {
        sharedpreferences = AtmFinderApplication.getContext()
                .getSharedPreferences(Constant.SETTINGS_FILENAME,
                        Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(Constant.FIRST_LOGIN, firstLogin);
        editor.apply();

    }

    private static SharedPreferences sharedpreferences;

    public static boolean getLoginStatus() {
        sharedpreferences = AtmFinderApplication.getContext()
                .getSharedPreferences(Constant.SETTINGS_FILENAME,
                        Context.MODE_PRIVATE);
        return sharedpreferences.getBoolean(Constant.IS_LOGIN, false);
    }

    public static void setLoginStatus(boolean loginStatus) {
        sharedpreferences = AtmFinderApplication.getContext()
                .getSharedPreferences(Constant.SETTINGS_FILENAME,
                        Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(Constant.IS_LOGIN, loginStatus);

        editor.apply();

    }

    public static String getUserName() {
        sharedpreferences = AtmFinderApplication.getContext()
                .getSharedPreferences(Constant.SETTINGS_FILENAME,
                        Context.MODE_PRIVATE);
        return sharedpreferences.getString(Constant.USER_NAME, "");
    }

    public static void setUsername(String userName) {
        sharedpreferences = AtmFinderApplication.getContext()
                .getSharedPreferences(Constant.SETTINGS_FILENAME,
                        Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(Constant.USER_NAME, userName);

        editor.apply();

    }

	/*public static String getpassword() {
		sharedpreferences = AtmFinderApplication.getContext()
				.getSharedPreferences(Constant.SETTINGS_FILENAME,
						Context.MODE_PRIVATE);
		return sharedpreferences.getString(Constant.PASSWORD, "");
	}

	public static void setPassword(String loginStatus) {
		sharedpreferences = AtmFinderApplication.getContext()
				.getSharedPreferences(Constant.SETTINGS_FILENAME,
						Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedpreferences.edit();
		editor.putString(Constant.PASSWORD, loginStatus);

		editor.commit();

	}*/

    public static boolean getActivityStatus() {
        sharedpreferences = AtmFinderApplication.getContext()
                .getSharedPreferences(Constant.SETTINGS_FILENAME,
                        Context.MODE_PRIVATE);
        return sharedpreferences.getBoolean(Constant.ACTIVITY_STATUS, false);
    }

    public static void setActivityStatus(boolean status) {
        sharedpreferences = AtmFinderApplication.getContext()
                .getSharedPreferences(Constant.SETTINGS_FILENAME,
                        Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(Constant.ACTIVITY_STATUS, status);

        editor.commit();

    }

    private static byte actionCode = Constant.DEFAULT_EXTRA_VALUE;

    public static boolean isTablet() {
        return AtmFinderApplication.getContext().getResources()
                .getBoolean(R.bool.isTablet);
    }

    public static boolean isPlayServicesAvailable(Context context) {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        return status == ConnectionResult.SUCCESS;
    }

    public static void startFlurry(final Context context, final String eventName) {

        new FlurryAgent.Builder()
                .withLogEnabled(false)
                .build(context, Constant.FLURRY_API_KEY);

        FlurryAgent.onStartSession(context, Constant.FLURRY_API_KEY);
        FlurryAgent.logEvent(eventName);
        FlurryAgent.onEndSession(context);
    }

    public static void startActivity(final Context context, final Class<?> cls,
                                     final boolean isProgressDialogEnabled,
                                     final boolean isProgressDialogCancelable,
                                     final boolean finishCurrent) {
        ProgressDialog dialog = null;

        if (isProgressDialogEnabled) {
            dialog = ProgressDialog.show(context,
                    Localization.getDialogLoadingTitle(),
                    Localization.getDialogPleaseWait(), true,
                    isProgressDialogCancelable);
        }

        Intent intent = new Intent(AtmFinderApplication.getContext(), cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);

        if (isProgressDialogEnabled) {
            dialog.dismiss();
        }

        if (finishCurrent) {
            ((Activity) context).finish();
        }
    }

    public static void showDialogAlert(String message, Context mContext) {

        AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setMessage(message);

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        alertDialog.show();
    }

    public static void hideKeyboard(Activity mActivity) {
        // Check if no view has focus:
        View view = mActivity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) mActivity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void showMessageCounter(final RelativeLayout layout,
                                          final TextView textView) {
        if (AtmFinderApplication.getUnreadCounter() != 0) {
            layout.setVisibility(View.VISIBLE);
            textView.setText(String.valueOf(AtmFinderApplication
                    .getUnreadCounter()));
        } else {
            layout.setVisibility(View.GONE);
        }
    }

    public static String getDistanceString(final float distance) {
        if (Settings.getDistanceUnits() == Settings.DistanceUnits.Kilometers) {
            return (String.format("%.2f", distance * 1.609344) + " " + Constant.KILOMETERS_SHORT_NAME)
                    .replace(",", ".");
        } else {
            if (Settings.getLanguage() == Settings.LanguageList.English) {
                return (String.format("%.2f", distance) + " " + Constant.MILES_SHORT_NAME_EN)
                        .replace(",", ".");
            } else {
                return (String.format("%.2f", distance) + " " + Constant.MILES_SHORT_NAME_ES)
                        .replace(",", ".");
            }
        }
    }

    public static void setRecord(final PointRecord newRecord) {
        record = newRecord;
    }

    public static void setPointPosition(final LatLng newPointPosition) {
        pointPosition = newPointPosition;
    }

    public static void setCurrentPosition(final LatLng location) {
        currentPosition = location;
    }

    public static void setMyLocation(final LatLng newLocation) {
        myLocation = newLocation;
    }

    public static void setAppUrl(final String newAppUrl) {
        appUrl = newAppUrl;
    }

    public static void setQueryText(final String newQueryText) {
        queryText = newQueryText;
    }

    public static void setLastUnreadCount(final int unread) {
        lastUnreadCount = unread;
    }

    public static void setSettingsLoaded(final boolean loadState) {
        settingsLoaded = loadState;
    }

    public static void setApplicationCreated() {
        isApplicationCreated = true;
    }

    public static void setOnMainActivity(final boolean onMainActivity) {
        Utils.onMainActivity = onMainActivity;
    }

    public static void setFromSplash(final boolean fromSplash) {
        Utils.fromSplash = fromSplash;
    }

    public static PointRecord getRecord() {
        return record;
    }

    public static LatLng getPointPosition() {
        return pointPosition;
    }

    public static LatLng getCurrentPosition() {
        return currentPosition;
    }

    public static LatLng getMyLocation() {
        return myLocation;
    }

    public static String getAppUrl() {
        return appUrl;
    }

    public static String getQueryText() {
        return queryText;
    }

    public static int getLastUnreadCount() {
        return lastUnreadCount;
    }

    public static boolean isSettingsLoaded() {
        return settingsLoaded;
    }

    public static boolean isApplicationCreated() {
        return !isApplicationCreated;
    }

    public static boolean isOnMainActivity() {
        return onMainActivity;
    }

    public static boolean isFromSplash() {
        return fromSplash;
    }

    public static Marker getInfoWindowMarker() {
        return infoWindowMarker;
    }

    public static void setInfoWindowMarker(final Marker infoWindowMarker) {
        Utils.infoWindowMarker = infoWindowMarker;
    }

    public static PointRecord getInfoWindowRecord() {
        return infoWindowRecord;
    }

    public static void setInfoWindowRecord(final PointRecord infoWindowRecord) {
        Utils.infoWindowRecord = infoWindowRecord;
    }

    public static List<PointRecord> getLocationList() {
        return locationList;
    }

    public static void setLocationList(final List<PointRecord> locationList) {
        Utils.locationList = locationList;
    }

    public static int getCheckedMessagesCount() {
        return checkedMessagesCount;
    }

    public static void setCheckedMessagesCount(int checkedMessagesCount) {
        Utils.checkedMessagesCount = checkedMessagesCount;
    }

    public static boolean isActionEnabled() {
        return actionEnabled;
    }

    public static void setActionEnabled(boolean actionEnabled) {
        Utils.actionEnabled = actionEnabled;
    }

    public static byte getActionCode() {
        return actionCode;
    }

    public static void setActionCode(byte actionCode) {
        Utils.actionCode = actionCode;
    }

}
