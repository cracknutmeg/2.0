/**
 * @ com.allpoint.util.Settings
 */
package com.allpoint.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.allpoint.AtmFinderApplication;

/**
 * Settings
 *
 * @author: Vyacheslav.Shmakin
 * @version: 08.01.14
 */
public class Settings {

    private static Settings settings = new Settings();

    private Settings() {
    }

    public static Settings getInstance() {
        return settings;
    }

    private static DistanceUnits distanceUnits = DistanceUnits.Miles;
    private static LanguageList language = LanguageList.English;
    private static CurrentActivity currentActivity = CurrentActivity.NotSet;
    private static SharedPreferences sharedPreferences;

    private static boolean launchWithNearMe = true;

    private static boolean launchSetGeofence = true;

    public enum CurrentActivity {
        NotSet, AboutActivity, MainActivity, MessageActivity, PointDetailsActivity, MainMenuActivity, HistoryActivity
    }

    public static void setCurrentActivity(final CurrentActivity activityName) {
        Settings.currentActivity = activityName;
    }

    public static CurrentActivity getCurrentActivity() {
        return currentActivity;
    }

    public enum DistanceUnits {
        Miles, Kilometers
    }

    public enum LanguageList {
        English, Spanish
    }

    public static void setDistanceUnits(final DistanceUnits newDistanceUnits) {
        Settings.distanceUnits = newDistanceUnits;
    }

    public static void setDistanceUnits(final String newDistanceUnits) {
        for (DistanceUnits distanceUnit : DistanceUnits.values()) {
            if (newDistanceUnits.equals(distanceUnit.toString())) {
                Settings.distanceUnits = distanceUnit;
                break;
            }
        }
    }

    public static void setLanguage(final LanguageList newLanguage) {
        Settings.language = newLanguage;
    }

    public static void setLanguage(final String newLanguage) {
        for (LanguageList lang : LanguageList.values()) {
            if (newLanguage.equals(lang.toString())) {
                Settings.language = lang;
                break;
            }
        }
    }

    public static void setLaunchWithNearMe(final boolean launchWithNearMe) {
        Settings.launchWithNearMe = launchWithNearMe;
    }

    public static int getItemCode(final LanguageList lang) {
        for (int i = 0; i < LanguageList.values().length; i++) {
            if (LanguageList.values()[i].equals(lang)) {
                return i;
            }
        }
        return -1;
    }

    public static int getItemCode(final DistanceUnits distUnits) {
        for (int i = 0; i < DistanceUnits.values().length; i++) {
            if (DistanceUnits.values()[i].equals(distUnits)) {
                return i;
            }
        }
        return -1;
    }

    public static boolean isLaunchWithNearMe() {
        return launchWithNearMe;
    }

    public static boolean isSetGeofence() {
        return launchSetGeofence;
    }

    public static void setGeofence(final boolean launchSetGeofence) {
        Settings.launchSetGeofence = launchSetGeofence;
    }

    public static DistanceUnits getDistanceUnits() {
        return distanceUnits;
    }

    public static LanguageList getLanguage() {
        return language;
    }

    public static void SaveSettings() {
        sharedPreferences = AtmFinderApplication.getContext()
                .getSharedPreferences(Constant.SETTINGS_FILENAME,
                        Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Constant.SETTINGS_LAUNCH_NEAR_WITH_ME,
                isLaunchWithNearMe());
        editor.putString(Constant.SETTINGS_DISTANCE_UNIT, getDistanceUnits()
                .toString());
        editor.putString(Constant.SETTINGS_LANGUAGE, getLanguage().toString());
        editor.putBoolean(Constant.SETTINGS_SET_GEOFENCE, isSetGeofence());

        editor.commit();
    }

    public static void LoadSettings() {

        sharedPreferences = AtmFinderApplication.getContext()
                .getSharedPreferences(Constant.SETTINGS_FILENAME,
                        Context.MODE_PRIVATE);

        setLaunchWithNearMe(sharedPreferences.getBoolean(
                Constant.SETTINGS_LAUNCH_NEAR_WITH_ME, false));

        setDistanceUnits(sharedPreferences
                .getString(Constant.SETTINGS_DISTANCE_UNIT,
                        DistanceUnits.Miles.toString()));

        String language = sharedPreferences.getString(
                Constant.SETTINGS_LANGUAGE, LanguageList.English.toString());

        setGeofence(sharedPreferences.getBoolean(
                Constant.SETTINGS_SET_GEOFENCE, true));

        setLanguage(language);

        if (language.equals(LanguageList.English.toString())) {
            Localization.setEnglish();
        } else {
            Localization.setSpanish();
        }
    }
}
