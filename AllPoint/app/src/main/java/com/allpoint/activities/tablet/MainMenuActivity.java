package com.allpoint.activities.tablet;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allpoint.AtmFinderApplication;
import com.allpoint.R;
import com.allpoint.activities.phone.MainActivity;
import com.allpoint.activities.tablet.fragments.SettingsFragment;
import com.allpoint.services.InternetConnectionManager;
import com.allpoint.services.LoadWebServiceAsync;
import com.allpoint.services.RespSessionInvalid;
import com.allpoint.services.RespSignOut;
import com.allpoint.services.WebServiceListner;
import com.allpoint.services.parse.ParseXML;
import com.allpoint.util.Constant;
import com.allpoint.util.Localization;
import com.allpoint.util.Settings;
import com.allpoint.util.Utils;
import com.bugsense.trace.BugSenseHandler;
import com.gimbal.android.PlaceManager;
import com.urbanairship.UAirship;
import com.urbanairship.richpush.RichPushInbox;

/**
 * MainMenuActivity
 *
 * @version: 08.01.14
 */
public class MainMenuActivity extends FragmentActivity implements View.OnClickListener,
        SettingsFragment.SettingsChangeListener, RichPushInbox.Listener, WebServiceListner {
    ParseXML parseXML;
    private ProgressDialog dialog;
    RespSessionInvalid mRespForInvalidSession;
    ParseXML parseXml;
    InternetConnectionManager connectionManager;
    private SettingsFragment settingsFragment;

    AtmFinderApplication atmfinderappcontext;
    TextView mainMenuSearch;
    TextView mainMenuScan;
    TextView mainMenuMessages;
    TextView numberOfMessagesText;
    TextView mainMenuTrans;
    protected ImageButton btnHome;
    protected TextView txtHome;
    ImageButton searchBtn;
    protected Button btnLogin;
    RelativeLayout messageCountLayout;

    public static Context mContext;

    //For History Remove

    protected ImageButton imgBtnTrasHistory;
    private ImageButton btnMenuCardScan;
    private TextView txtSearch;
    private ImageButton messagesBtn;
    private TextView txtMessage;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.main_menu);
        UAirship.shared().getInbox().addListener(this);
        connectionManager = InternetConnectionManager.getInstance(this);
        atmfinderappcontext = (AtmFinderApplication) getApplicationContext();
        parseXml = new ParseXML();
        initGUIElement();

        AfterViews();

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

        if (!Utils.isSettingsLoaded()) {
            Settings.LoadSettings();
            Utils.setSettingsLoaded(true);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        parseXML = new ParseXML();
        Utils.startFlurry(this, Constant.MAIN_MENU_ACTIVITY_EVENT);

        Settings.setCurrentActivity(Settings.CurrentActivity.MainMenuActivity);
        if (settingsFragment == null) {
            settingsFragment = new SettingsFragment();
            settingsFragment.addSettingsListener(this);
        }

        if (Utils.isFromSplash()) {
            Utils.setFromSplash(false);

            if (Settings.isLaunchWithNearMe()) {
                searchBtn.performClick();
            }
        }
    }

    @Override
    public void onStop() {
        settingsFragment.removeSettingsListener(this);
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        btnHome.setImageResource(R.drawable.bottom_home_press);
        txtHome.setTextColor(getResources().getColor(R.color.textColor));
        btnHome.setEnabled(false);
        mContext = MainMenuActivity.this;

        Utils.showMessageCounter(messageCountLayout, numberOfMessagesText);

        if (Utils.getLoginStatus()) {
            btnLogin.setText(getResources().getString(R.string.app_logout));
            PlaceManager.getInstance().startMonitoring();
        } else {
            btnLogin.setText(getResources().getString(R.string.app_login));
        }

        mainMenuScan.setText(Localization.getMainMenuScanTitle());
        mainMenuMessages.setText(Localization.getMainMenuMessagesTitle());
        mainMenuSearch.setText(Localization.getMainMenuSearchTitle());
        //mainMenuTrans.setText(Localization.getMainMenuTransTitle());


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


    public void onIbtnLoginClicked(View view) {

        if (btnLogin.getText().toString()
                .equals(getResources().getString(R.string.app_login))) {

            Utils.startActivity(this,
                    com.allpoint.activities.LoginActivity.class, true, false,
                    false);
        } else {
            // on logout session set null
			/*atmfinderappcontext.sessionToken = "";
			Utils.setLoginStatus(false);
			Utils.startActivity(this,
					com.allpoint.activities.LoginActivity.class, true, false,
					false);*/

            callLogout();
        }
    }

    void onIbtnMenuSearchClicked() {


        Utils.startActivity(this, MainActivity.class, true, false, false);
    }

    void onIbtnMenuMessagesClicked() {
        Utils.startActivity(this, MessageActivity.class, true, false, false);
    }

    void onIbtnMenuScanCardClicked() {

        Utils.startActivity(MainMenuActivity.this,
                com.allpoint.activities.CardCheckActivity.class, false, false,
                false);

    }

    void onIbtnMenuAboutClicked() {

//		Utils.startActivity(MainMenuActivity.this,
//				com.allpoint.activities.tablet.CardListActivity.class, false,
//				false, false);

        //History Remove Change
        if (Constant.HISTORY_BUTTON_DISABLE) {
            Utils.startActivity(MainMenuActivity.this, AboutActivity.class,
                    false, false, false);
        } else {

            startActivity(new Intent(MainMenuActivity.this, CardListActivity.class));

        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        //TODO  uncomment the if condition after remove the all erroe from MainActivity here..
		/*if (MainActivity.instance != null) {
			MainActivity.instance.finish();
		}*/
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        UAirship.shared().getInbox().removeListener(this);
    }

    @Override
    public void onSettingsChanged() {
        mainMenuScan.setText(Localization.getMainMenuScanTitle());
        mainMenuMessages.setText(Localization.getMainMenuMessagesTitle());
        mainMenuSearch.setText(Localization.getMainMenuSearchTitle());
        mainMenuTrans.setText(Localization.getMainMenuTransTitle());

    }

    @Override
    public void onSettingsShowed() {
    }

    @Override
    public void onSettingsDismissed() {
        Settings.SaveSettings();
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

            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }

            // show message
            showSessionInvalid(getResources().getString(R.string.msg_sessionInvalid));

        } else {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
            RespSignOut mResult = parseXML.parseXMLSignOutRequest(result);

            if (mResult != null && mResult.getSignOutStatus()) {


                Utils.setLoginStatus(false);
                Utils.setUsername("");
                atmfinderappcontext.sessionToken = mResult.getSignOutToken();
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

    @Override
    public void onInboxUpdated() {

        Utils.showMessageCounter(messageCountLayout, numberOfMessagesText);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iBtnMenuSearch:


                onIbtnMenuSearchClicked();
                break;
            case R.id.iBtnMenuTrans:
                onIbtnMenuAboutClicked();
                break;
            case R.id.iBtnMenuScan:
                onIbtnMenuScanCardClicked();
                break;
            case R.id.iBtnMenuMessages:
                onIbtnMenuMessagesClicked();
                break;
            case R.id.tvLogin:
                onIbtnLoginClicked(v);
                break;

        }
    }
}
