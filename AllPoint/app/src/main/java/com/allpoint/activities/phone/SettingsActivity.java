/**
 * @ SettingsActivity
 */
package com.allpoint.activities.phone;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.allpoint.AtmFinderApplication;
import com.allpoint.R;
import com.allpoint.activities.phone.fragments.TabBarFragment;
import com.allpoint.services.InternetConnectionManager;
import com.allpoint.util.Constant;
import com.allpoint.util.Localization;
import com.allpoint.util.Settings;
import com.allpoint.util.ShareApp;
import com.allpoint.util.Utils;
import com.bugsense.trace.BugSenseHandler;
import com.flurry.android.FlurryAgent;

/**
 * @author: Mikhail.Shalagin & Vyacheslav.Shmakin
 * @version: 23.09.13
 */
public class SettingsActivity extends FragmentActivity implements
        AdapterView.OnItemSelectedListener {

    TabBarFragment frag;
    InternetConnectionManager connectionManager;
    protected Settings settings;
    protected Spinner unitsSpinner;
    protected Spinner languageSpinner;
    protected Switch launchWithNearMeButton;
    protected Switch launchSettingGeofence;
    // Settings
    protected TextView settingsTitle;
    protected TextView settingsSearchPreference;
    protected TextView settingsVisitGooglePlayButton;
    protected TextView settingsLaunchNearWithMe;
    protected TextView settingsDistanceUnits;
    protected TextView settingsLanguage;
    protected TextView settingsVersion;
    protected TextView settingsFeedbackButton;

    protected TextView tvSetGeofence;

    protected LinearLayout layGeoFence;


    protected TextView settingsbtnChangePasswordButton;

    protected TextView settingsbtnChangePasswordButtonLine;

    protected ImageButton settingsButton;

    protected TextView tvSetting;

    protected TextView btnEditprofileIs;

    protected TextView btnTermsAndCondition;

    AtmFinderApplication atmfinderappcontext;

    private static String AppUrl;

    public static String getAppUrl() {
        return AppUrl;
    }

    public static void setAppUrl(String newAppUrl) {
        AppUrl = newAppUrl;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        connectionManager = InternetConnectionManager.getInstance(this);
        settings = Settings.getInstance();
        initGUIElements();
        afterViews();

        launchWithNearMeButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (isChecked) {
                    Settings.setLaunchWithNearMe(true);
                } else {
                    Settings.setLaunchWithNearMe(false);
                }
            }
        });


        launchSettingGeofence.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    atmfinderappcontext.isGeofenceOn = true;
                    Settings.setGeofence(true);
                } else {
                    atmfinderappcontext.isGeofenceOn = false;
                    Settings.setGeofence(false);
                }
            }
        });

    }

    private void initGUIElements() {

        unitsSpinner = findViewById(R.id.distance_spinner);
        languageSpinner = findViewById(R.id.language_spinner);
        launchWithNearMeButton = findViewById(R.id.tBtnLaunchNear);
        launchSettingGeofence = findViewById(R.id.tBtnSetGeofence);
        settingsTitle = findViewById(R.id.tvSettingsTitle);
        settingsSearchPreference = findViewById(R.id.tvSearchPreference);
        settingsVisitGooglePlayButton = findViewById(R.id.btnVisitPlay);
        settingsLaunchNearWithMe = findViewById(R.id.tvLaunchNear);
        settingsDistanceUnits = findViewById(R.id.tvDistanceUnits);
        settingsLanguage = findViewById(R.id.tvLanguage);
        settingsVersion = findViewById(R.id.tvAppVersion);
        settingsFeedbackButton = findViewById(R.id.btnFeedback);
        tvSetGeofence = findViewById(R.id.tvSetGeofence);
        layGeoFence = findViewById(R.id.layGeoFence);
        settingsbtnChangePasswordButton = findViewById(R.id.btnChangePassword);
        settingsbtnChangePasswordButtonLine = findViewById(R.id.btnChangePasswordLine);
        settingsButton = findViewById(R.id.iBtnBottomMore);
        tvSetting = findViewById(R.id.iTxtBottomMore);
        btnEditprofileIs = findViewById(R.id.btnEditProfile);
        btnTermsAndCondition = findViewById(R.id.btnTermsAndCond);


        btnTermsAndCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBtnTermsAndCondClicked();
            }
        });

        settingsbtnChangePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBtnChangePswdClick();
            }
        });

        settingsFeedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBtnFeedbackClick();
            }
        });


    }

    protected void afterViews() {

        frag = (TabBarFragment) getSupportFragmentManager().findFragmentById(
                R.id.bottom_bar);

        BugSenseHandler.initAndStartSession(this, Constant.BUG_SENSE_API_KEY);

        if (Constant.HISTORY_BUTTON_DISABLE) {
            settingsButton.setImageResource(R.drawable.bottom_settings_press);
            tvSetting.setTextColor(getResources().getColor(R.color.textColor));

            // settingsButton.setBackgroundResource(R.drawable.bottom_press_button_bg);
            settingsButton.setEnabled(true);

        } else {
            settingsButton.setImageResource(R.drawable.bottom_more_press);
            tvSetting.setTextColor(getResources().getColor(R.color.textColor));
            settingsButton.setEnabled(true);

        }

    }

    @Override
    public void onStart() {
        super.onStart();

        atmfinderappcontext = (AtmFinderApplication) getApplicationContext();

        //Hide old Password Field on Change Password
        atmfinderappcontext.setChangePassFromSettings = true;

        FlurryAgent.onStartSession(this, Constant.FLURRY_API_KEY);
        FlurryAgent.logEvent(Constant.SETTINGS_ACTIVITY_EVENT);
        FlurryAgent.onEndSession(this);

        String[] languages = new String[]{Constant.SETTINGS_LANGUAGE_ENGLISH,
                Constant.SETTINGS_LANGUAGE_SPANISH};
        ArrayAdapter<String> languageAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, languages);
        languageSpinner.setAdapter(languageAdapter);

        languageSpinner.setOnItemSelectedListener(this);

        languageSpinner.setSelection(Settings.getItemCode(Settings
                .getLanguage()));
        launchWithNearMeButton.setChecked(Settings.isLaunchWithNearMe());

        launchSettingGeofence.setChecked(Settings.isSetGeofence());

		/*if(atmfinderappcontext.isGeofenceOn && Settings.isSetGeofence()){
			launchSettingGeofence.setChecked(true);
		} else {
			launchSettingGeofence.setChecked(Settings.isSetGeofence());
		}*/

    }

    @Override
    public void onResume() {
        super.onResume();
        LoadLocalizedSettings();
        if (!Utils.getLoginStatus()) {
            settingsbtnChangePasswordButton.setVisibility(View.GONE);
            btnEditprofileIs.setVisibility(View.GONE);
            settingsbtnChangePasswordButtonLine.setVisibility(View.GONE);

            //disable Geo-fence if user not login
            layGeoFence.setVisibility(View.GONE);

        } else {
            settingsbtnChangePasswordButton.setVisibility(View.VISIBLE);
            btnEditprofileIs.setVisibility(View.GONE);
            settingsbtnChangePasswordButtonLine.setVisibility(View.VISIBLE);

            layGeoFence.setVisibility(View.VISIBLE);
        }
    }

    protected void onBtnFeedbackClick() {
        ShareApp.byEmail(this, Constant.ALLPOINT_FEEDBACK_MAIL,
                Constant.SHARE_SUBJECT, Constant.SHARE_MESSAGE);
        FlurryAgent.onStartSession(this, Constant.FLURRY_API_KEY);
        FlurryAgent.logEvent(Constant.SEND_FEEDBACK_EVENT);
        FlurryAgent.onEndSession(this);
    }

    protected void onBtnChangePswdClick() {

        if (!connectionManager.isConnected()) {

            Utils.showDialogAlert(
                    getResources().getString(
                            R.string.en_dialogCannotConnectText),
                    SettingsActivity.this);
        } else {

            Utils.startActivity(SettingsActivity.this,
                    com.allpoint.activities.ChangePasswordActivity.class,
                    false, false, false);
        }
    }

    protected void onBtnEditProfileClicked(View view) {

        atmfinderappcontext.isEditProfile = true;

        if (!connectionManager.isConnected()) {

            Utils.showDialogAlert(
                    getResources().getString(
                            R.string.en_dialogCannotConnectText),
                    SettingsActivity.this);
        } else {
            Utils.startActivity(SettingsActivity.this,
                    com.allpoint.activities.RegistrationActivity.class, false,
                    false, false);
        }
    }

    // Terms And Condtions
    protected void onBtnTermsAndCondClicked() {

        if (!connectionManager.isConnected()) {
            Utils.showDialogAlert(getResources().getString(
                    R.string.en_dialogCannotConnectText), SettingsActivity.this);
        } else {
            Utils.startActivity(SettingsActivity.this, com.allpoint.activities.TermsAndCondition.class, false,
                    false, false);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        switch (parent.getId()) {
            case R.id.distance_spinner: {
                if (parent.getItemIdAtPosition(position) == 0) {
                    Settings.setDistanceUnits(Settings.DistanceUnits.Miles);
                } else {
                    Settings.setDistanceUnits(Settings.DistanceUnits.Kilometers);
                }

                break;
            }
            case R.id.language_spinner: {
                if (parent.getItemIdAtPosition(position) == 0) {
                    Settings.setLanguage(Settings.LanguageList.English);
                    Localization.setEnglish();
                    // frag.onResume();
                } else {
                    Settings.setLanguage(Settings.LanguageList.Spanish);
                    Localization.setSpanish();
                    // frag.onResume();
                }

                LoadLocalizedSettings();

                break;
            }
        }
    }

    private void LoadLocalizedSettings() {
        // Settings
        settingsTitle.setText(Localization.getSettingsLayoutTitle());
        settingsSearchPreference.setText(Localization
                .getSettingsSearchPreferenceTitle());
        settingsVisitGooglePlayButton.setText(Localization
                .getSettingsVisitGooglePlay());
        settingsLaunchNearWithMe.setText(Localization
                .getSettingsLaunchWithNearMeTitle());
        settingsLanguage.setText(Localization.getSettingsLanguageTitle());
        settingsDistanceUnits.setText(Localization
                .getSettingsDistanceUnitsTitle());
        settingsVersion.setText(Localization.getSettingsVersion() + " "
                + Constant.PHONE_APP_VERSION);

        settingsFeedbackButton.setText(Localization.getSettingsFeedback());
        tvSetGeofence.setText(Localization.getSettingsbtnFeedback());
        btnTermsAndCondition.setText(Localization.getSettingsbtnTermsAndCond());

        String[] units = new String[]{
                Localization.getSettingsDistanceUnitsMiles(),
                Localization.getSettingsDistanceUnitsKm()};
        ArrayAdapter<String> unitsAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, units);
        unitsSpinner.setOnItemSelectedListener(this);
        unitsSpinner.setAdapter(unitsAdapter);

        unitsSpinner.setSelection(Settings.getItemCode(Settings
                .getDistanceUnits()));

        // frag.changeText();

        // tTxtBottomHome.setText(Localization.getMainMenuTabHome());
        // itxtBottomSearch.setText(Localization.getMainMenuSearchTitle());
        // itxtBottomTransaction.setText(Localization.getMainMenuTransTitle());
        // itxtBottomMessages.setText(Localization.getMainMenuMessagesTitle());
        // itxtBottomMore.setText(Localization.getMainMenuMessagesTitle());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }


    /*void checkedChangedOnTbtnLaunchNear(CompoundButton compoundButton,
                                        boolean isChecked) {
        if (isChecked) {
            Settings.setLaunchWithNearMe(true);
        } else {
            Settings.setLaunchWithNearMe(false);
        }
    }

    void checkedChangedOnSetGeofence(CompoundButton compoundButton,
                                     boolean isChecked) {
        if (isChecked) {
            atmfinderappcontext.isGeofenceOn = true;
            Settings.setGeofence(true);
        } else {
            atmfinderappcontext.isGeofenceOn = false;
            Settings.setGeofence(false);
        }
    }*/

    @Override
    protected void onPause() {
        Settings.SaveSettings();
        super.onPause();
    }

    public void onBtnVisitPlayClicked(View view) {
        final String appURL = Utils.getAppUrl();

        if (appURL != null && !appURL.isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(Utils.getAppUrl()));
            startActivity(intent);
        }

    }
}
