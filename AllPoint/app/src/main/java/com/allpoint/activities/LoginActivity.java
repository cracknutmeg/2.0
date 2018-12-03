package com.allpoint.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.allpoint.AtmFinderApplication;
import com.allpoint.R;
import com.allpoint.model.ResgistrationResponseData;
import com.allpoint.services.InternetConnectionManager;
import com.allpoint.services.LoadWebServiceAsync;
import com.allpoint.services.RespLogin;
import com.allpoint.services.RespOTPreg;
import com.allpoint.services.WebServiceListner;
import com.allpoint.services.parse.ParseXML;
import com.allpoint.util.Constant;
import com.allpoint.util.Localization;
import com.allpoint.util.Settings;
import com.allpoint.util.Utils;
import com.allpoint.util.Validation;
import com.bugsense.trace.BugSenseHandler;

import java.io.InputStream;

public class LoginActivity extends Activity implements WebServiceListner {

    public static String uaChannelID = "";

    public static boolean prod_build;
    public static String cert = null;
    public static String hostname = null;
    public static String username = null;
    public static String password = null;

    InputStream is;

    private ProgressDialog dialog;


    private EditText email;
    private EditText paswd;
    private Button login_button;
    private TextView loginMsgIs;
    private Button signup_button;
    private Button forget_pass_button;
    private Button skip_button;

    // ProgressDialog progressDialog;

    String respCardDetails;

    InternetConnectionManager connectionManager;
    ResgistrationResponseData respData = null;

    AtmFinderApplication atmfinderappcontext;

    ParseXML parseXML;

    /****************************** OnCreate ***********************************/
    @Override
    public void onCreate(final Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_login);

        initGUIElements();
        connectionManager = InternetConnectionManager.getInstance(this);

        BugSenseHandler.initAndStartSession(this, Constant.BUG_SENSE_API_KEY);

        // hide keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        atmfinderappcontext = (AtmFinderApplication) AtmFinderApplication.getContext();

        parseXML = new ParseXML();

        ContextWrapper activity = null;
        ApplicationInfo ai = null;
        try {
            ai = getPackageManager().getApplicationInfo(this.getPackageName(), PackageManager.GET_META_DATA);
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Bundle bundle = ai.metaData;

        hostname = bundle.getString("hostname");
        //System.out.println("#hostname "+hostname);

        prod_build = bundle.getBoolean("PRODUCTION_BUILD");
        //System.out.println("#Prod build "+prod_build);

        cert = bundle.getString("SERVER_CERIFICATE_NAME");
        //System.out.println("#cert "+cert);

        username = bundle.getString("LOGIN_USER_NAME");
        // System.out.println("#username "+username);

        password = bundle.getString("LOGIN_USER_PASSWORD");
        // System.out.println("#password "+password);

    }

    private void initGUIElements() {

        email = findViewById(R.id.uname_edit_text);
        paswd = findViewById(R.id.upass_edit_text);
        login_button = findViewById(R.id.login_button);
        loginMsgIs = findViewById(R.id.loginMsgIs);
        signup_button = findViewById(R.id.signup_button);
        forget_pass_button = findViewById(R.id.forget_pass_button);
        skip_button = findViewById(R.id.skip_button);
    }


    /****************************** OnResume ***********************************/
    @Override
    protected void onResume() {

        email.setHint(Localization.getloginEdtUN());
        paswd.setHint(Localization.getloginEdtPW());
        login_button.setText(Localization.getloginBtnSignIn());
        loginMsgIs.setText(Localization.getloginTxtMsg());
        signup_button.setText(Localization.getloginBtnSignUp());
        forget_pass_button.setText(Localization.getloginBtnFgtPw());
        skip_button.setText(Localization.getloginBtnSkip());

        if (Utils.getLoginStatus()) {

            //callLoginWebService(true);

        }


        super.onResume();
    }

    /****************************** Handling Click Events ***********************************/

    public void onIbtnLoginClicked(View view) {

        checkValidation();
		
		/*if(LoginActivity.uaChannelID == null || LoginActivity.uaChannelID.equals("")) {
			showLoginChannelID("You need to re-install application, to get Urban Airship Channel ID!");
		} else {
			showLoginChannelID(LoginActivity.uaChannelID);	
		}*/


    }

    public void onIbtnSignClicked(View view) {

        atmfinderappcontext.isEditProfile = false;

        atmfinderappcontext.isFirstTimeReg = true;

        atmfinderappcontext.setEvent = "2";
        Utils.startActivity(LoginActivity.this,
                com.allpoint.activities.ForgetPasswordActivity.class,
                false, false, false);
    }

    public void onIbtnForgetPasswordClicked(View view) {
        atmfinderappcontext.isFirstTimeReg = false;
        if (!connectionManager.isConnected()) {
            Utils.showDialogAlert(
                    getResources().getString(
                            R.string.en_dialogCannotConnectText),
                    LoginActivity.this);
        } else {

            atmfinderappcontext.setEvent = "1";
            Utils.startActivity(LoginActivity.this,
                    com.allpoint.activities.ForgetPasswordActivity.class,
                    false, false, false);
        }
    }

    public void onIbtnSkipClicked(View view) {

        email.setText("");
        paswd.setText("");
        callMainMenuScreen(false);

        Utils.setUsername("");

        Settings.setGeofence(false);

    }

    /****************************** Validation ***********************************/

    private void checkValidation() {
        String alertMessage = "";
        if (email.getText().length() == 0 || paswd.getText().length() == 0) {
            if (email.getText().length() > 0 && paswd.getText().length() == 0) {

                alertMessage = getResources().getString(
                        R.string.alertMsg_EnterPass);
                Utils.showDialogAlert(alertMessage, LoginActivity.this);

            } else if ((email.getText().length() == 0 && paswd.getText()
                    .length() > 0)) {

                alertMessage = getResources().getString(
                        R.string.alertMsg_EnterUserName);
                Utils.showDialogAlert(alertMessage, LoginActivity.this);
            } else {
                alertMessage = getResources().getString(
                        R.string.alertMsg_EnterUNandPW);

                Utils.showDialogAlert(alertMessage, LoginActivity.this);
            }
        } else {
            // On successful login
            // make uncomment after getting proper response
            if (!Validation.isValidEmail(email.getText().toString().trim())) {
                alertMessage = getResources().getString(
                        R.string.alertMsg_EntervalidUN);
                Utils.showDialogAlert(alertMessage, LoginActivity.this);
            } /*
             * else if
             * (!Validation.passwordvalidation(paswd.getText().toString()
             * .trim())){ paswd.requestFocus(); paswd.setText(""); alertMessage
             * = getResources().getString(R.string.alertMsg_IncorrectPassword);
             * Utils.showDialogAlert(alertMessage,LoginActivity.this); }
             */ else {
                Utils.hideKeyboard(LoginActivity.this);


//				alertMessage = getResources().getString(
//						R.string.en_dialogCannotConnectText);
//				Utils.showDialogAlert(alertMessage, LoginActivity.this);

                //removed flow after login if user is not active
                callLoginWebService(false);
            }
        }
    }


    // Main Menu Screen Call
    private void callMainMenuScreen(boolean setLoginStaus) {

        final Class<?> activityClass;
        if (Utils.isTablet()) {
            activityClass = com.allpoint.activities.tablet.MainMenuActivity.class;
        } else {
            activityClass = com.allpoint.activities.phone.MainMenuActivity.class;
        }
        Utils.setLoginStatus(setLoginStaus);
        Utils.startActivity(LoginActivity.this, activityClass, false, false,
                true);

    }

    /****************************** Server Call ***********************************/
    // Login WebApi Call

    LoadWebServiceAsync callApi;

    private void callLoginWebService(boolean isAfterLogin) {

        if (!connectionManager.isConnected()) {
            Utils.showDialogAlert(
                    getResources().getString(
                            R.string.en_dialogCannotConnectText),
                    LoginActivity.this);

        } else {
            String value = null;
            if (!isAfterLogin) {
                value = "<LoginRequest>" + "<LoginData>" + "<EmailId>"
                        + email.getText().toString().trim() + "</EmailId>"
                        + "<Password>" + paswd.getText().toString()
                        + "</Password>"
                        //+ "<ApplicationId>"	+ Constant.ALLPOINT_SERVER_APP_ID + "</ApplicationId>"
                        + "</LoginData></LoginRequest>";
            } else {
                value = "<LoginRequest>" + "<LoginData>" + "<EmailId>"
                        + Utils.getUserName().trim() + "</EmailId>" + "<Password>"
                        + paswd.getText().toString() + "</Password>"
                        //+ "<ApplicationId>" + Constant.ALLPOINT_SERVER_APP_ID + "</ApplicationId>"
                        + "</LoginData></LoginRequest>";

            }

            callApi = new LoadWebServiceAsync(LoginActivity.this,
                    LoginActivity.this, value,
                    Constant.CUSTOMER_MANAGEMENT_URL,
                    Constant.LOGIN_METHOD_NAME, Constant.LOGIN_SOAP_ACTION,
                    Constant.CUSTOMER_MANAGEMENT_NAMESPACE,
                    Utils.getUserName().trim(), "");

            dialog = ProgressDialog.show(LoginActivity.this, "Please wait...",
                    "Loading...");
            dialog.show();
            callApi.execute();
        }
    }


    @Override
    public void onResult(String result) {


        RespOTPreg mResultForOTP = null;

        RespLogin mResult = parseXML.parseXMLForLgoinUser(result);
        // RespLogin mResult = parseXMLForLgoinUser(result);

        if (atmfinderappcontext.setRegFlag) {
            mResultForOTP = parseXML.parseXMLforReg(result);
        }

        if (mResult != null && mResult.getLoginStatus()) {

            // get Application session Token From Server
            atmfinderappcontext.sessionToken = mResult.getLoginSessionToken();

            // Save username and password for remember me option
            if (!Utils.getLoginStatus()) {
                Utils.setUsername(email.getText().toString());
                //Utils.setPassword(paswd.getText().toString());
            }
            // Onsucessful login every time
            Utils.setLoginStatus(true);

            // on successful login and app is first time installed.
            // changed flows as per sim request on 04/10/2016
//			if (Utils.isFirstLogin()) {
//				Utils.setFirstLogin(false);
//				if (dialog != null && dialog.isShowing()) {
//					dialog.dismiss();
//					dialog = null;
//				}
//				Utils.setActivityStatus(true);
//				Utils.startActivity(LoginActivity.this,
//						com.allpoint.activities.CardCheckActivity.class,
//						false, false, true);
//
//			} else { 
            // On successful login except 1st installation

            if (atmfinderappcontext.isGeofenceOn) {
                Settings.setGeofence(true);
            } else {
                Settings.setGeofence(false);
            }

            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                dialog = null;
            }

            final Class<?> activityClass;
            if (Utils.isTablet()) {
                activityClass = com.allpoint.activities.tablet.MainMenuActivity.class;
            } else {
                activityClass = com.allpoint.activities.phone.MainMenuActivity.class;
            }
            Utils.startActivity(LoginActivity.this, activityClass, false,
                    false, true);
        }
		/*} else if (mResult != null
				&& mResult.getLoginStatusCode().endsWith("109")) {

			// if user is not active then call this method

			if (dialog != null && dialog.isShowing()) {
				dialog.dismiss();
				dialog = null;
			}

			Utils.setLoginStatus(false);
			showLoginOTPmsg(mResult.getLoginStatusMessage());

		} else if (mResultForOTP != null && mResultForOTP.getOTPRegStatus()) {
			if (dialog != null && dialog.isShowing()) {
				dialog.dismiss();
				dialog = null;
			}
			// call when user register but not varify otp
			atmfinderappcontext.setEvent = "2";
			Utils.startActivity(LoginActivity.this,
					com.allpoint.activities.OTPActivity.class, false, false,
					true);

		} */
        else {

            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                dialog = null;
            }

            if (mResult != null) {
                Utils.showDialogAlert(mResult.getLoginStatusMessage()
                        .toString().trim(), LoginActivity.this);
                Utils.setLoginStatus(false);
            } else {
                Utils.showDialogAlert(
                        getResources()
                                .getString(R.string.err_server_Connection),
                        LoginActivity.this);
                Utils.setLoginStatus(false);
            }

        }
        paswd.setText("");
    }

    /****************************** Handling Server Side Response ***********************************/

    @Override
    public void onRunning() {
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


    /****************************** Handling Server Side Response ***********************************/

    public void showLoginOTPmsg(String message) {

        AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this)
                .create();
        alertDialog.setMessage(message + " "
                + getResources().getString(R.string.user_not_active));

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        atmfinderappcontext.setRegFlag = true;

                        Utils.setLoginStatus(false);
                        atmfinderappcontext.setEvent = "2";
                        callRegResetWebService();

                    }
                });
        alertDialog.show();
    }

    /************ UA Channel ID Copy *************/
    public void showLoginChannelID(String message) {

        AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this)
                .create();
        alertDialog.setMessage(message);

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Copy",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        setClipboard(getApplicationContext(), LoginActivity.uaChannelID);
                    }
                });
        alertDialog.show();
    }

    // copy text to clipboard
    private void setClipboard(Context context, String text) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clip);
        }
    }

    /************ UA Channel ID Copy *************/

    private void callRegResetWebService() {

        if (!connectionManager.isConnected()) {

            Utils.showDialogAlert(
                    getResources().getString(
                            R.string.en_dialogCannotConnectText),
                    LoginActivity.this);
        } else {

            // After Forgot Password

            String value = "<USEROTT>" + "<USERNAME>" + Utils.getUserName().trim()
                    + "</USERNAME>"
                    //+ "<ApplicationId>"	+ Constant.ALLPOINT_SERVER_APP_ID + "</ApplicationId>"
                    + "<EVENT>" + atmfinderappcontext.setEvent + "</EVENT>"
                    + "</USEROTT>";

            callApi = new LoadWebServiceAsync(getApplicationContext(),
                    LoginActivity.this, value,
                    Constant.CUSTOMER_MANAGEMENT_URL,
                    Constant.OTT_RESEND_REG_METHOD_NAME,
                    Constant.OTT_RESEND_REG_SOAP_ACTION,
                    Constant.CUSTOMER_MANAGEMENT_NAMESPACE,
                    Utils.getUserName().trim(), atmfinderappcontext.sessionToken);
            callApi.execute();
            dialog = ProgressDialog.show(LoginActivity.this, "Please wait....",
                    "Loading..");
            dialog.show();

        }
    }
}
