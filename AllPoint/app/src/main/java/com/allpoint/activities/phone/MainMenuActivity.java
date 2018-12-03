/**
 * @ SplashActivity
 */
package com.allpoint.activities.phone;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allpoint.AtmFinderApplication;
import com.allpoint.R;
import com.allpoint.services.GeofenceService;
import com.allpoint.services.InternetConnectionManager;
import com.allpoint.services.LoadWebServiceAsync;
import com.allpoint.services.RespSessionInvalid;
import com.allpoint.services.RespSignOut;
import com.allpoint.services.WebServiceListner;
import com.allpoint.services.parse.ParseXML;
import com.allpoint.util.Constant;
import com.allpoint.util.Localization;
import com.allpoint.util.PermissionUtils;
import com.allpoint.util.Settings;
import com.allpoint.util.Utils;
import com.bugsense.trace.BugSenseHandler;
import com.gimbal.android.PlaceManager;
import com.urbanairship.UAirship;
import com.urbanairship.richpush.RichPushInbox;

/**
 * MainMenuActivity
 *
 * @author: Vyacheslav.Shmakin
 * @version: 23.08.13
 */

public class MainMenuActivity extends FragmentActivity implements
        RichPushInbox.Listener, WebServiceListner, View.OnClickListener {
    private static final int PHONE_STATE_REQUEST_CODE = 120;
    AtmFinderApplication atmfinderappcontext;
    ParseXML parseXML;
    private ProgressDialog dialog;
    InternetConnectionManager connectionManager;

    RespSessionInvalid mRespForInvalidSession;
    protected Settings settings;

    ParseXML parseXml;

    // MainMenu

    protected TextView mainMenuSearch, mainMenuScan, mainMenuMessages, mainMenuTrans;
    protected TextView txtSearch, numberOfMessagesText, txtMessage, txtHome;
    protected RelativeLayout messageCountLayout;
    protected ImageButton btnHome, searchBtn, messagesBtn;
    protected Button btnLogin;
    //For History Remove

    protected ImageButton imgBtnTrasHistory;
    private ImageButton btnMenuCardScan;
    private boolean mPermissionDenied;


    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        settings = Settings.getInstance();

        setContentView(R.layout.main_menu);

        connectionManager = InternetConnectionManager.getInstance(MainMenuActivity.this);
        initGUIElement();

        AfterViews();

        //------------------------------------------------------------------------------------------
        // To request for runtime permissions here
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, PHONE_STATE_REQUEST_CODE);

            //PermissionUtils.requestPermission(this, PHONE_STATE_REQUEST_CODE, Manifest.permission.READ_PHONE_STATE, true);
        }
        //------------------------------------------------------------------------------------------

        UAirship.shared().getInbox().addListener(this);
        atmfinderappcontext = (AtmFinderApplication) getApplicationContext();
        parseXml = new ParseXML();
    }

    private void initGUIElement() {

        mainMenuSearch = findViewById(R.id.tvMenuSearch);
        mainMenuScan = findViewById(R.id.tvMenuScan);
        mainMenuTrans = findViewById(R.id.tvMenuTrans);
        mainMenuMessages = findViewById(R.id.tvMenuMessages);
        messageCountLayout = findViewById(R.id.layoutMenuMessageCount);
        numberOfMessagesText = findViewById(R.id.tvMenuNumberOfMessages);
        btnHome = findViewById(R.id.iBtnBottomHome);
        btnMenuCardScan = findViewById(R.id.iBtnMenuScan);
        txtHome = findViewById(R.id.iTxtBottomHome);
        searchBtn = findViewById(R.id.iBtnMenuSearch);
        txtSearch = findViewById(R.id.tvMenuSearch);
        messagesBtn = findViewById(R.id.iBtnMenuMessages);
        txtMessage = findViewById(R.id.tvMenuMessages);
        btnLogin = findViewById(R.id.tvLogin);
        imgBtnTrasHistory = findViewById(R.id.iBtnMenuTrans);

        searchBtn.setOnClickListener(this);
        mainMenuScan.setOnClickListener(this);
        mainMenuMessages.setOnClickListener(this);
        mainMenuMessages.setOnClickListener(this);
        messagesBtn.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        imgBtnTrasHistory.setOnClickListener(this);
        btnMenuCardScan.setOnClickListener(this);

    }


    void AfterViews() {
        BugSenseHandler.initAndStartSession(this, Constant.BUG_SENSE_API_KEY);
        btnHome.setImageResource(R.drawable.bottom_home_press);
        txtHome.setTextColor(getResources().getColor(R.color.textColor));
        // btnHome.setBackgroundResource(R.drawable.bottom_press_button_bg);
        btnHome.setEnabled(true);
        if (!Utils.isSettingsLoaded()) {
            Settings.LoadSettings();
            Utils.setSettingsLoaded(true);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Utils.startFlurry(this, Constant.MAIN_MENU_ACTIVITY_EVENT);

        parseXML = new ParseXML();
        if (Utils.isFromSplash()) {
            Utils.setFromSplash(false);

            if (Settings.isLaunchWithNearMe()) {
                searchBtn.performClick();
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Utils.showMessageCounter(messageCountLayout, numberOfMessagesText);

        if (Utils.getLoginStatus()) {
            // btnLogin.setText(getResources().getString(R.string.app_logout));
            btnLogin.setText(Localization.getlogout());

            PlaceManager.getInstance().startMonitoring();
        } else {
            // btnLogin.setText(getResources().getString(R.string.app_login));

            btnLogin.setText(Localization.getlogin());
        }

        // mainMenuAbout.setText(Localization.getMainMenuAboutTitle());

        mainMenuMessages.setText(Localization.getMainMenuMessagesTitle());
        mainMenuSearch.setText(Localization.getMainMenuSearchTitle());
        mainMenuScan.setText(Localization.getMainMenuScanTitle());


        if (Constant.HISTORY_BUTTON_DISABLE) {
            //set About
            mainMenuTrans.setText(Localization.getMainMenuAboutTitle());
            imgBtnTrasHistory.setBackgroundResource(R.drawable.select_main_about);

            //Set Setting Button

            //

        } else {
            //set History
            mainMenuTrans.setText(Localization.getMainMenuTransTitle());
            imgBtnTrasHistory.setBackgroundResource(R.drawable.select_main_trans);

            //Set More Button


        }


    }


    void onIbtnLoginClicked() {

        if (btnLogin.getText().toString()
                .equals(getResources().getString(R.string.app_login))) {

            Utils.startActivity(this,
                    com.allpoint.activities.LoginActivity.class, true, false,
                    true);
        } else {


            //Call Logout Functionality
            callLogout();
        }
    }


    void onIbtnMenuSearchClicked() {
        Utils.startActivity(this, MainActivity.class, true, false, false);
    }

    void onIbtnMenuMessagesClicked() {
        Utils.startActivity(this, MessageActivity.class, true, false, false);
    }

    void onIbtnMenuCardScanClicked() {
        Utils.startActivity(MainMenuActivity.this,
                com.allpoint.activities.CardCheckActivity.class, false, false,
                false);
    }

    void onIbtnMenuTransClicked() {
        // Utils.startActivity(MainMenuActivity.this, CardListActivity.class,
        // false, false, false);

        //History Remove Change
        if (Constant.HISTORY_BUTTON_DISABLE) {
            Utils.startActivity(MainMenuActivity.this, AboutActivity.class,
                    false, false, false);
        } else {

            startActivity(new Intent(MainMenuActivity.this, CardListActivity.class));

        }


    }

    // @Override
    // public void onBackPressed() {
    // moveTaskToBack(true);
    //
    //
    // if (MainActivity.instance != null) {
    // MainActivity.instance.finish();
    // }
    // finish();

    // Intent i = new Intent(getApplicationContext(), MainMenuActivity.class);
    // // set the new task and clear flags
    // i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
    // Intent.FLAG_ACTIVITY_CLEAR_TASK);
    // startActivity(i);
    // finish();
    // super.onBackPressed();
    // }

    // @Override
    // public boolean onKeyDown(int keyCode, KeyEvent event) {
    // if (keyCode == KeyEvent.KEYCODE_BACK) {
    // if (MainActivity.instance != null) {
    // MainActivity.instance.finish();
    // }
    // finish();
    // System.exit(0);
    // return true;
    // }
    // return super.onKeyDown(keyCode, event);
    // }

    @Override
    public void onBackPressed() {
        Utils.setCurrentPosition(null);
        moveTaskToBack(true);
        if (MainActivity.instance != null) {
            MainActivity.instance.finish();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        UAirship.shared().getInbox().removeListener(this);
    }


    /****************************** Server Call ***********************************/
    // Login WebApi Call

    LoadWebServiceAsync callApi;

    private void callLogout() {

        if (!connectionManager.isConnected()) {
            Utils.showDialogAlert(
                    getResources().getString(
                            R.string.en_dialogCannotConnectText),
                    MainMenuActivity.this);

        } else {

            //Lock Main Menu
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

            String value = null;

            value = "<SignOutUser>" + "<Token>"
                    + atmfinderappcontext.sessionToken + "</Token>"
                    //+ "<ApplicationId>" + Constant.ALLPOINT_SERVER_APP_ID + "</ApplicationId>"
                    + "</SignOutUser>";


            callApi = new LoadWebServiceAsync(getApplicationContext(),
                    MainMenuActivity.this, value,
                    Constant.CUSTOMER_MANAGEMENT_URL,
                    Constant.LOGOUT_METHOD_NAME, Constant.LOGOUT_SOAP_ACTION,
                    Constant.CUSTOMER_MANAGEMENT_NAMESPACE,
                    Utils.getUserName().trim(), "");

            // LoadWebServiceAsync callApi = new
            // LoadWebServiceAsync(getApplicationContext(), LoginActivity.this,
            // value, Constant.HOSTNAME_LINK, Constant.FORGET_METHOD_NAME,
            // Constant.FORGET_SOAP_ACTION,Constant.FORGET_NAMESPACE);
            dialog = ProgressDialog.show(MainMenuActivity.this, "Please wait...",
                    "Loading...");
            dialog.show();
            callApi.execute();
        }
    }

    @Override
    public void onResult(String result) {

        // session Token Checking
        mRespForInvalidSession = parseXml.parseXMLforSessionInvalid(result);
        // mRespForInvalidSession = parseXMLforSessionInvalid(result);

        // if session is Invalid
        if (mRespForInvalidSession != null
                && !mRespForInvalidSession.getSessionInvalidStatusCode().toString().trim()
                .equals(Constant.SESSION_ERROR_CODE)) {

            if (dialog != null) {
                dialog.dismiss();
            }

            // show message
            showSessionInvalid(getResources().getString(R.string.msg_sessionInvalid));

        } else {
            if (dialog != null) {

                dialog.dismiss();
            }
            RespSignOut mResult = parseXML.parseXMLSignOutRequest(result);

            if (mResult != null && mResult.getSignOutStatus()) {


                Utils.setLoginStatus(false);
                Utils.setUsername("");
                atmfinderappcontext.sessionToken = mResult.getSignOutToken();

                if (atmfinderappcontext.sessionToken == null) {
                    atmfinderappcontext.sessionToken = "";
                }

                Utils.startActivity(this,
                        com.allpoint.activities.LoginActivity.class, true, false,
                        true);
            } else {

                Utils.showDialogAlert(
                        getResources().getString(
                                R.string.err_server_Connection),
                        MainMenuActivity.this);

            }
        }
    }


    @Override
    public void onRunning() {
        // TODO Auto-generated method stub
        if (dialog != null) {
            dialog.setCancelable(true);
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface dialog1) {
                    callApi.cancel(true);
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    // finish();
                }
            });
        }
    }

    public void showSessionInvalid(String message) {

        AlertDialog alertDialog = new AlertDialog.Builder(MainMenuActivity.this)
                .create();
        alertDialog.setMessage(message);
        alertDialog.setCancelable(true);
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Utils.setLoginStatus(false);
                        Utils.setUsername("");
                        Utils.startActivity(MainMenuActivity.this,
                                com.allpoint.activities.LoginActivity.class,
                                false, false, true);

                    }
                });
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iBtnMenuSearch:


                onIbtnMenuSearchClicked();
                break;
            case R.id.iBtnMenuTrans:
                onIbtnMenuTransClicked();
                break;
            case R.id.iBtnMenuScan:
                onIbtnMenuCardScanClicked();
                break;
            case R.id.iBtnMenuMessages:
                onIbtnMenuMessagesClicked();
                break;
            case R.id.tvLogin:
                onIbtnLoginClicked();
                break;

        }
    }

    @Override
    public void onInboxUpdated() {

        Utils.showMessageCounter(messageCountLayout, numberOfMessagesText);
    }

    // ----------------------------
    // Start for runtime permissions

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (PermissionUtils.isPermissionGranted(permissions, grantResults, Manifest.permission.READ_PHONE_STATE)) {
            // Enable the my location layer if the permission has been granted.

            startService(new Intent(this, GeofenceService.class));

        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

}
