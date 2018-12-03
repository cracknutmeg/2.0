package com.allpoint.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.allpoint.AtmFinderApplication;
import com.allpoint.R;
import com.allpoint.model.ResgistrationResponseData;
import com.allpoint.services.InternetConnectionManager;
import com.allpoint.services.LoadWebServiceAsync;
import com.allpoint.services.RespSessionInvalid;
import com.allpoint.services.ResponseGetUserDetails;
import com.allpoint.services.ResponseHandler;
import com.allpoint.services.ResponseUpdateUser;
import com.allpoint.services.WebServiceListner;
import com.allpoint.services.parse.ParseXML;
import com.allpoint.util.Constant;
import com.allpoint.util.Settings;
import com.allpoint.util.Utils;
import com.allpoint.util.Validation;
import com.bugsense.trace.BugSenseHandler;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class RegistrationActivity extends Activity implements WebServiceListner, View.OnClickListener {

    String AlertMessage;
    String respCardDetails;
    InputStream is;
    private boolean isUpdateUser;

	/*@ViewById(R.id.first_name_edit_text)
	protected EditText firstName;*/

    protected TextView regTitle;

	/*@ViewById(R.id.last_name_edit_text)
	protected EditText lastName;*/

    protected EditText email;

    protected TextView email_Msg_Is;

	/*@ViewById(R.id.confirm_email_edit_text)
	protected EditText confirm_email;*/

	/*@ViewById(R.id.mobile_number_edit_text)
	protected EditText mobNo;*/

	/*@ViewById(R.id.postal_edit_text)
	protected EditText zipCode;*/

    protected EditText password;

    protected EditText confirmPswd;

    protected RelativeLayout laySub;

    protected LinearLayout layPass;

    protected TextView passMsg;

    protected Switch launchSettingGeofence;

    protected Switch subscribeToggleButton;

    protected TextView contBtn;

    protected TextView registrationTitle;

    protected TextView cancel;

    protected TextView termsAndCond;

    private ProgressDialog dialog;

    ResgistrationResponseData respData = null;

    InternetConnectionManager connectionManager;

    AtmFinderApplication atmfinderappcontext;

    ParseXML parseXML;
    RespSessionInvalid mRespForInvalidSession;

    LoadWebServiceAsync callApi;

    @Override
    public void onCreate(final Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_registration);

        connectionManager = InternetConnectionManager.getInstance(this);

        initGUIElements();

        AfterViews();

        BugSenseHandler.initAndStartSession(this, Constant.BUG_SENSE_API_KEY);

        // hide keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        atmfinderappcontext = (AtmFinderApplication) getApplicationContext();

        // dialog = ProgressDialog.show(RegistrationActivity.this,
        // "Please wait...", "Loading..");

        parseXML = new ParseXML();


        cancel.setOnClickListener(this);
    }

    private void initGUIElements() {


        regTitle = findViewById(R.id.registrationTitle);
        email = findViewById(R.id.email_edit_text);
        email_Msg_Is = findViewById(R.id.email_msg_is);
        password = findViewById(R.id.password_edit_text);
        confirmPswd = findViewById(R.id.confirm_edit_text);
        laySub = findViewById(R.id.subscribe_layout);
        layPass = findViewById(R.id.password_layout);
        passMsg = findViewById(R.id.password_msg_textview);
        launchSettingGeofence = findViewById(R.id.tBtnSetGeofence);
        subscribeToggleButton = findViewById(R.id.subscribe_toggle_button);
        contBtn = findViewById(R.id.continue_button);
        registrationTitle = findViewById(R.id.registrationTitle);
        cancel = findViewById(R.id.cancel);
        termsAndCond = findViewById(R.id.tc);

    }

    @Override
    protected void onStart() {

//		email_msg_is,confirm_email
//		@ViewById(R.id.email_msg_is)
//		protected TextView email_Msg_Is;
//
//		@ViewById(R.id.confirm_email_edit_text)
//		protected EditText confirm_email;

        //Make Edit Text Non Editable and set mail id

        email.setEnabled(false);
        //confirm_email.setEnabled(false);
        email.setText(Utils.getUserName().trim());
        //confirm_email.setText(Utils.getUserName());

        if (atmfinderappcontext.isEditProfile) {

            regTitle.setText(getResources().getString(
                    R.string.en_settingsEditProfile));

            email.setKeyListener(null);
            email.setEnabled(false);

            //confirm_email.setVisibility(View.GONE);
            password.setVisibility(View.GONE);
            confirmPswd.setVisibility(View.GONE);
            passMsg.setVisibility(View.GONE);

            laySub.setVisibility(View.GONE);
            layPass.setVisibility(View.GONE);
            passMsg.setVisibility(View.GONE);
            if (!connectionManager.isConnected()) {

                Utils.showDialogAlert(
                        getResources().getString(
                                R.string.en_dialogCannotConnectText),
                        RegistrationActivity.this);
            } else {
                getProfileInformation();
            }
        } else {

            regTitle.setText(getResources().getString(R.string.signup_title));

            email.setClickable(true);
            //confirm_email.setVisibility(View.VISIBLE);
            password.setVisibility(View.VISIBLE);
            confirmPswd.setVisibility(View.VISIBLE);
            passMsg.setVisibility(View.VISIBLE);
            layPass.setVisibility(View.VISIBLE);
            laySub.setVisibility(View.VISIBLE);
            passMsg.setVisibility(View.VISIBLE);
        }

        super.onStart();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        // firstName.setHint(Localization.getSignUpFN());
        // lastName.setHint(Localization.getsignUpLN());
        // mobNo.setHint(Localization.getsignUpMN());
        // email.setHint(Localization.getsignUpEI());
        // confirm_email.setHint(Localization.getsignUpCEI());
        // email_Msg_Is.setText(Localization.getsignUpEMSG());
        // zipCode.setHint(Localization.getsignUpPZ());
        // password.setHint(Localization.getsignUpPW());
        // confirmPswd.setHint(Localization.getsignUpCPW());
        // passMsg.setText(Localization.getsignUpMsg());
        // termsAndCond.setHint(Localization.getsignUpMsg2());
        // contBtn.setText(Localization.getsignUpCont());

        // registrationTitle.setText(Localization.getsignup());
        // cancel.setText(Localization.getcancel());

    }

    @Override
    public void onBackPressed() {

        Utils.hideKeyboard(RegistrationActivity.this);
        super.onBackPressed();
    }


    void onIbtnCancelClicked() {
        Utils.hideKeyboard(RegistrationActivity.this);
        finish();

    }

    void onIbtnContinueClicked() {
        atmfinderappcontext.setEvent = "2";
        // Utils.startActivity(RegistrationActivity.this, OTPActivity.class,
        // false, false, true);
        checkValidation();
    }

    private void checkValidation() {
        Utils.hideKeyboard(RegistrationActivity.this);
        if (atmfinderappcontext.isEditProfile) {

            //setProfileInformation();

        } else {

            String alertMessage = "";
			/*if (firstName.getText().toString().trim().length() == 0) {
				alertMessage = getResources().getString(
						R.string.Err_Msg_FIrstName);
				Utils.showDialogAlert(alertMessage, RegistrationActivity.this);
			} else if (!Validation.isValid(firstName.getText().toString()
					.trim())) {
				firstName.requestFocus();
				alertMessage = getResources().getString(
						R.string.Err_Msg_ValidFIrstName);
				Utils.showDialogAlert(alertMessage, RegistrationActivity.this);
			} else if (lastName.getText().toString().trim().length() == 0) {
				alertMessage = getResources().getString(
						R.string.Err_Msg_LsatName);
				Utils.showDialogAlert(alertMessage, RegistrationActivity.this);
			} else if (!Validation
					.isValid(lastName.getText().toString().trim())) {
				lastName.requestFocus();
				alertMessage = getResources().getString(
						R.string.Err_Msg_ValidlastName);
				Utils.showDialogAlert(alertMessage, RegistrationActivity.this);
			} else if (mobNo.getText().toString().trim().length() == 0) {
				mobNo.requestFocus();
				alertMessage = getResources().getString(
						R.string.Err_Msg_mobileNumber);
				Utils.showDialogAlert(alertMessage, RegistrationActivity.this);
			} else if (mobNo.length() > 0
					&& !Validation.validatePhoneNumber(mobNo.getText()
							.toString().trim())) {
				mobNo.requestFocus();
				alertMessage = getResources().getString(
						R.string.Err_Msg_MobileValidtaion);
				Utils.showDialogAlert(alertMessage, RegistrationActivity.this);
			} else*/
            if (email.getText().toString().trim().length() == 0) {
                email.requestFocus();
                alertMessage = getResources()
                        .getString(R.string.Err_MSG_EMaild);
                Utils.showDialogAlert(alertMessage, RegistrationActivity.this);
            } else if (!Validation.isValidEmail(email.getText().toString()
                    .trim())) {
                email.requestFocus();
                alertMessage = getResources().getString(
                        R.string.Err_Msg_EmailValid);
                Utils.showDialogAlert(alertMessage, RegistrationActivity.this);
            } /*else if (confirm_email.getText().toString().trim().length() == 0) {
				confirm_email.requestFocus();
				alertMessage = getResources().getString(
						R.string.Err_MSG_ConfirmEMaild);
				Utils.showDialogAlert(alertMessage, RegistrationActivity.this);
			} else if (!Validation.isValidEmail(confirm_email.getText()
					.toString().trim())) {
				confirm_email.requestFocus();
				alertMessage = getResources().getString(
						R.string.Err_Msg_ConfirmEmailValid);
				Utils.showDialogAlert(alertMessage, RegistrationActivity.this);
			} else if (!email.getText().toString().trim()
					.equals(confirm_email.getText().toString().trim())) {
				email.requestFocus();
				alertMessage = getResources().getString(
						R.string.Err_Msg_EmailMatch);
				Utils.showDialogAlert(alertMessage, RegistrationActivity.this);

			} else if (zipCode.getText().toString().trim().length() == 0) {
				zipCode.requestFocus();
				alertMessage = getResources().getString(
						R.string.Err_Msg_Zipcode);
				Utils.showDialogAlert(alertMessage, RegistrationActivity.this);
			} else if (!Validation.validateZipCode(zipCode.getText().toString()
					.trim())) {
				zipCode.requestFocus();
				alertMessage = getResources().getString(
						R.string.Err_Msg_ZipValidtaion);
				Utils.showDialogAlert(alertMessage, RegistrationActivity.this);
			}*/ else if (password.getText().toString().trim().length() == 0) {
                password.requestFocus();
                alertMessage = getResources().getString(
                        R.string.Err_msg_Password);
                Utils.showDialogAlert(alertMessage, RegistrationActivity.this);
            } else if (!Validation.passwordvalidation(password.getText()
                    .toString().trim())) {
                password.requestFocus();
                alertMessage = getResources().getString(
                        R.string.Err_Msg_PasswordCharactors);
                Utils.showDialogAlert(alertMessage, RegistrationActivity.this);
            } else if (confirmPswd.getText().toString().trim().length() == 0) {
                confirmPswd.requestFocus();
                alertMessage = getResources().getString(
                        R.string.Err_msg_ConfirmPassword);
                Utils.showDialogAlert(alertMessage, RegistrationActivity.this);
            } else if (!Validation.passwordvalidation(confirmPswd.getText()
                    .toString().trim())) {
                confirmPswd.requestFocus();
                alertMessage = getResources().getString(
                        R.string.Err_Msg_PasswordConfirmCharactors);
                Utils.showDialogAlert(alertMessage, RegistrationActivity.this);
            } else if (!password.getText().toString().trim()
                    .equals(confirmPswd.getText().toString().trim())) {
                password.requestFocus();
                alertMessage = getResources().getString(
                        R.string.Err_Msg_PwdMatch);
                Utils.showDialogAlert(alertMessage, RegistrationActivity.this);

            } else if (password != null
                    && confirmPswd != null
                    && (password.getText().toString()).equals(confirmPswd
                    .getText().toString())) {

                callRegWebService();
            }
        }
    }

    private void getProfileInformation() {
        String value = "<GetUserRequest><UserData><EmailId>"
                + Utils.getUserName().trim() + "</EmailId>"
                //+ "<ApplicationId>"	+ Constant.ALLPOINT_SERVER_APP_ID + "</ApplicationId>"
                + "</UserData></GetUserRequest>";
        // callApi(value,
        // Constant.CUSTOMER_MANAGEMENT_URL, Constant.EDIT_METHOD_NAME,
        // Constant.EDIT_SOAP_ACTION,
        // Constant.CUSTOMER_MANAGEMENT_NAMESPACE);

        callApi = new LoadWebServiceAsync(getApplicationContext(),
                RegistrationActivity.this, value,
                Constant.CUSTOMER_MANAGEMENT_URL, Constant.EDIT_METHOD_NAME,
                Constant.EDIT_SOAP_ACTION,
                Constant.CUSTOMER_MANAGEMENT_NAMESPACE, Utils.getUserName().trim(),
                atmfinderappcontext.sessionToken);

        dialog = ProgressDialog.show(RegistrationActivity.this,
                "Please wait...Get", "Loading...");
        dialog.show();
        callApi.execute();

    }

    private void setProfileInformation() {
        email.setKeyListener(null);
        email.setEnabled(false);

        password.setClickable(false);
        password.setVisibility(View.GONE);
        confirmPswd.setClickable(false);
        confirmPswd.setVisibility(View.GONE);

        //updateProfileValidation();
    }

	/*private void updateProfileValidation() {
		String alertMessage = "";
		if (firstName.getText().toString().trim().length() == 0) {
			alertMessage = getResources().getString(R.string.Err_Msg_FIrstName);
			Utils.showDialogAlert(alertMessage, RegistrationActivity.this);

		} else if (!Validation.isValid(firstName.getText().toString().trim())) {
			firstName.requestFocus();
			alertMessage = getResources().getString(
					R.string.Err_Msg_ValidFIrstName);
			Utils.showDialogAlert(alertMessage, RegistrationActivity.this);
		} else if (lastName.getText().toString().trim().length() == 0) {
			alertMessage = getResources().getString(R.string.Err_Msg_LsatName);
			Utils.showDialogAlert(alertMessage, RegistrationActivity.this);
		} else if (!Validation.isValid(lastName.getText().toString().trim())) {
			lastName.requestFocus();
			alertMessage = getResources().getString(
					R.string.Err_Msg_ValidlastName);
			Utils.showDialogAlert(alertMessage, RegistrationActivity.this);
		} else if (mobNo.getText().toString().trim().length() == 0) {
			mobNo.requestFocus();
			alertMessage = getResources().getString(
					R.string.Err_Msg_mobileNumber);
			Utils.showDialogAlert(alertMessage, RegistrationActivity.this);
		} else if (mobNo.length() > 0
				&& !Validation.validatePhoneNumber(mobNo.getText().toString()
						.trim())) {
			mobNo.requestFocus();
			alertMessage = getResources().getString(
					R.string.Err_Msg_MobileValidtaion);
			Utils.showDialogAlert(alertMessage, RegistrationActivity.this);
		} else if (zipCode.getText().toString().trim().length() == 0) {
			zipCode.requestFocus();
			alertMessage = getResources().getString(R.string.Err_Msg_Zipcode);
			Utils.showDialogAlert(alertMessage, RegistrationActivity.this);
		} else if (!Validation.validateZipCode(zipCode.getText().toString()
				.trim())) {
			zipCode.requestFocus();
			alertMessage = getResources().getString(
					R.string.Err_Msg_ZipValidtaion);
			Utils.showDialogAlert(alertMessage, RegistrationActivity.this);
		} else {
			String value = "<UpdateUserRequest><CustomerData><FirstName>"
					+ firstName.getText() + "</FirstName><LastName>"
					+ lastName.getText() + "</LastName><EmailId>"
					+ Utils.getUserName() + "</EmailId><MobileNumber>"
					+ mobNo.getText() + "</MobileNumber>" + "<ZipCode>"
					+ zipCode.getText() + "</ZipCode>" 
					//+ "<ApplicationId>"	+ Constant.ALLPOINT_SERVER_APP_ID + "</ApplicationId>"
					+ "</CustomerData></UpdateUserRequest>";

			isUpdateUser = true;
			// callApi = callApi(value,
			// Constant.CUSTOMER_MANAGEMENT_URL, Constant.UPDATE_METHOD_NAME,
			// Constant.UPDATE_SOAP_ACTION,
			// Constant.CUSTOMER_MANAGEMENT_NAMESPACE);

			callApi = new LoadWebServiceAsync(getApplicationContext(),
					RegistrationActivity.this, value,
					Constant.CUSTOMER_MANAGEMENT_URL,
					Constant.UPDATE_METHOD_NAME, Constant.UPDATE_SOAP_ACTION,
					Constant.CUSTOMER_MANAGEMENT_NAMESPACE,
					Utils.getUserName(), atmfinderappcontext.sessionToken);

			dialog = ProgressDialog.show(RegistrationActivity.this,
					"Please wait...", "Loading...");
			dialog.show();
			callApi.execute();
		}
	}*/

    void AfterViews() {

        BugSenseHandler.initAndStartSession(this, Constant.BUG_SENSE_API_KEY);
        if (atmfinderappcontext.isEditProfile) {
            contBtn.setText(getResources().getString(
                    R.string.en_updateProfileSave));
        } else {
            contBtn.setText(getResources().getString(R.string.continue_button));
        }


    }

    @Override
    public void onResult(String result) {


        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog.cancel();
        }

        if (!connectionManager.isConnected()) {

            Utils.showDialogAlert(
                    getResources().getString(
                            R.string.en_dialogCannotConnectText),
                    RegistrationActivity.this);
        } else {

            if (atmfinderappcontext.isEditProfile) {

                mRespForInvalidSession = parseXML.parseXMLforSessionInvalid(result);
                // mRespForInvalidSession = parseXMLforSessionInvalid(result);

                // if session is Invalid
                if (mRespForInvalidSession != null
                        && mRespForInvalidSession
                        .getSessionInvalidStatusCode().equals(
                                Constant.SESSION_ERROR_CODE)) {
                    if (dialog != null) {
                        dialog.dismiss();
                    }

                    // show message
                    // showSessionInvalid(mRespForInvalidSession.getSessionInvalidStatusMessage());
                    showSessionInvalid(getResources().getString(
                            R.string.msg_sessionInvalid));

                } else {

                    if (isUpdateUser) {

                        // check session

                        isUpdateUser = false;

                        ResponseUpdateUser mResult = parseXML
                                .parseXMLForUpdateUser(result);
                        // ResponseUpdateUser mResult =
                        // parseXMLForUpdateUser(result);

                        if (mResult != null) {

                            Utils.showDialogAlert(mResult.getMsg(), RegistrationActivity.this);
                        }

                    } else {
                        ResponseGetUserDetails mResult = parseXML
                                .parseXMLForGetUser(result);
                        // ResponseGetUserDetails mResult =
                        // parseXMLForGetUser(result);

                        if (mResult != null) {

//							firstName.setText(mResult.getFirstName());
//							firstName.setSelection(firstName.length());
//							lastName.setText(mResult.getLastName());
//							lastName.setSelection(lastName.length());
//							mobNo.setText(mResult.getMobileNumber());
//							mobNo.setSelection(mobNo.length());
                            email.setText(mResult.getUserName());
                            email.setSelection(email.length());
//							zipCode.setText(mResult.getZipCode());
//							zipCode.setSelection(zipCode.length());
                        } else {
                            showDialogAlertForUpdateUser(getResources()
                                    .getString(R.string.err_server_Connection));
                        }

                    }
                }

            } else {

                // ResgistrationResponseData mResult =
                // parseXML.parseXMLforRegisterUser(result);
                ResgistrationResponseData mResult = parseXML(result);

                if (mResult != null && mResult.getStatus()) {

                    // Set User name
                    Utils.setUsername(email.getText().toString().trim());
                    showSessionInvalid(mResult.getMsg().toString().trim());

                    if (subscribeToggleButton.isChecked()) {
                        atmfinderappcontext.isGeofenceOn = true;
                        Settings.setGeofence(true);
                    } else {
                        atmfinderappcontext.isGeofenceOn = false;
                        Settings.setGeofence(false);
                    }

                    Settings.SaveSettings();

                } else if (mResult != null && mResult.getStatusCode().equalsIgnoreCase(Constant.REG_MOB_ERROR_CODE)
                        && mResult.getMsg().equalsIgnoreCase("Success")) { // handled for same mobile number case

                    Utils.showDialogAlert(
                            getResources().getString(
                                    R.string.reg_mob_no_msg),
                            RegistrationActivity.this);

                } else {
                    getWindow().setSoftInputMode(
                            WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

                    if (mResult != null) {

                        if (mResult.getMsg() == null) {

                            Utils.showDialogAlert(
                                    getResources().getString(
                                            R.string.err_server_Connection),
                                    RegistrationActivity.this);

                        } else {

                            Utils.showDialogAlert(mResult.getMsg(),
                                    RegistrationActivity.this);

                        }
                    } else {
                        Utils.showDialogAlert(
                                getResources().getString(
                                        R.string.err_server_Connection),
                                RegistrationActivity.this);
                    }
                }
            }
        }
    }

    private void callRegWebService() {

        if (!connectionManager.isConnected()) {

            Utils.showDialogAlert(
                    getResources().getString(
                            R.string.en_dialogCannotConnectText),
                    RegistrationActivity.this);
        } else {
            String value = "<RegistrationRequest>" + "<CustomerData>"
                    + "<FirstName>"
                    + ""
                    + "</FirstName>"
                    + "<LastName>"
                    + ""
                    + "</LastName>"
                    + "<EmailId>"
                    + email.getText().toString().trim()
                    + "</EmailId>"
                    + "<Password>"
                    + password.getText().toString().trim()
                    + "</Password>"
                    + "<MobileNumber>"
                    + ""
                    + "</MobileNumber>"
                    + "<ZipCode>"
                    + ""
                    + "</ZipCode>"
                    + "<token>"
                    + atmfinderappcontext.tempTokenForOTP
                    + "</token>"
                    //+ "<ApplicationId>"
                    //+ Constant.ALLPOINT_SERVER_APP_ID
                    //+ "</ApplicationId>"

                    + "</CustomerData>" + "</RegistrationRequest>";

            callApi = new LoadWebServiceAsync(getApplicationContext(),
                    RegistrationActivity.this, value,
                    Constant.CUSTOMER_MANAGEMENT_URL, Constant.REG_METHOD_NAME,
                    Constant.REG_SOAP_ACTION,
                    Constant.CUSTOMER_MANAGEMENT_NAMESPACE, "", "");

            dialog = ProgressDialog.show(RegistrationActivity.this,
                    "Please wait...", "Loading...");
            dialog.show();
            callApi.execute();
        }
    }


    public void showDialogAlertForUpdateUser(String message) {

        AlertDialog alertDialog = new AlertDialog.Builder(
                RegistrationActivity.this).create();
        alertDialog.setMessage(message);

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();

                    }
                });
        alertDialog.show();
    }

    void onTermsAndConditionClicked() {

        if (!connectionManager.isConnected()) {
            Utils.showDialogAlert(
                    getResources().getString(
                            R.string.en_dialogCannotConnectText),
                    RegistrationActivity.this);
        } else {
            Utils.startActivity(RegistrationActivity.this,
                    com.allpoint.activities.TermsAndCondition.class, false,
                    false, false);
        }
    }

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

    public void showSessionInvalid(String message) {

        AlertDialog alertDialog = new AlertDialog.Builder(
                RegistrationActivity.this).create();
        alertDialog.setMessage(message);
        alertDialog.setCancelable(true);
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Utils.setUsername("");
                        Utils.setLoginStatus(false);
                        Utils.startActivity(RegistrationActivity.this,
                                com.allpoint.activities.LoginActivity.class,
                                false, false, true);
                    }
                });
        alertDialog.show();
    }


    private ResgistrationResponseData parseXML(String resp) {

        ResgistrationResponseData udetails = new ResgistrationResponseData();
        try {

            // remove static
            // is = new ByteArrayInputStream(data.getBytes());

            is = new ByteArrayInputStream(resp.getBytes());
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();

            ResponseHandler resp_Handler = new ResponseHandler();
            xr.setContentHandler(resp_Handler);
            InputSource inStream = new InputSource(is);
            xr.parse(inStream);

            udetails = resp_Handler.getRegResp();

            is.close();

        } catch (Exception e) {
            //e.printStackTrace();
        }

        return udetails;

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tc:

                onTermsAndConditionClicked();

                break;

            case R.id.cancel:

                onIbtnCancelClicked();

                break;

            case R.id.continue_button:

                onIbtnContinueClicked();
                break;
        }
    }


    /*
     * private ResponseGetUserDetails parseXMLForGetUser(String resp) {
     *
     *
     *
     * ResponseGetUserDetails udetails = null; try {
     *
     * is = new ByteArrayInputStream(resp.getBytes()); SAXParserFactory spf =
     * SAXParserFactory.newInstance(); SAXParser sp = spf.newSAXParser();
     * XMLReader xr = sp.getXMLReader();
     *
     * ResponseHandler resp_Handler = new ResponseHandler();
     * xr.setContentHandler(resp_Handler); InputSource inStream = new
     * InputSource(is); xr.parse(inStream);
     *
     * udetails = resp_Handler.getUserDetailsResp();
     *
     * is.close();
     *
     * } catch (Exception e) { e.printStackTrace(); }
     *
     * return udetails;
     *
     * }
     */

    /*
     * private ResponseUpdateUser parseXMLForUpdateUser(String resp) {
     * ResponseUpdateUser udetails = null;
     *
     *
     *
     * try { is = new ByteArrayInputStream(resp.getBytes()); SAXParserFactory
     * spf = SAXParserFactory.newInstance(); SAXParser sp = spf.newSAXParser();
     * XMLReader xr = sp.getXMLReader();
     *
     * ResponseHandler resp_Handler = new ResponseHandler();
     * xr.setContentHandler(resp_Handler); InputSource inStream = new
     * InputSource(is); xr.parse(inStream); udetails =
     * resp_Handler.getUpdateUserDetailsResp();
     *
     * is.close();
     *
     * } catch (Exception e) { e.printStackTrace(); }
     *
     * return udetails;
     *
     * }
     */

    /*
     * LoadWebServiceAsync callApi; void callApi(String value, String URL,
     * String methodName, String soapAction, String nameSpace) {
     *
     * //new token & uname String uname = ""; //token = "";
     *
     * callApi = new LoadWebServiceAsync(getApplicationContext(),
     * RegistrationActivity.this, value, URL, methodName, soapAction,
     * nameSpace,uname,atmfinderappcontext.sessionToken+"1"); callApi.execute();
     *
     * dialog.show(); }
     */

	/*public void showDialogAlert(String message) {

		AlertDialog alertDialog = new AlertDialog.Builder(
				RegistrationActivity.this).create();
		alertDialog.setMessage(message);

		alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						finish();

						Utils.setLoginStatus(true);
						Utils.setFirstLogin(false);
						Utils.setActivityStatus(true);
						Utils.setUsername(email.getText().toString());
						//Utils.setPassword(password.getText().toString());
						Utils.startActivity(RegistrationActivity.this,
								CardCheckActivity.class, false, false, true);

					}

				});
		alertDialog.show();
	}*/

    /*
     * if (atmfinderappcontext.isEditProfile) {
     *
     * regTitle.setText(getResources().getString(R.string.en_settingsEditProfile
     * ));
     *
     * email.setKeyListener(null); email.setEnabled(false);
     *
     * password.setVisibility(View.GONE);
     * confirmPswd.setVisibility(View.GONE);
     * passMsg.setVisibility(View.GONE);
     *
     * laySub.setVisibility(View.GONE); layPass.setVisibility(View.GONE);
     * passMsg.setVisibility(View.GONE);
     *
     * if (!connectionManager.isConnected()) {
     *
     * Utils.showDialogAlert(getResources().getString(R.string.
     * en_dialogCannotConnectText), RegistrationActivity.this); } else {
     * getProfileInformation(); }
     *
     * } else {
     *
     * if (dialog != null && dialog.isShowing()) { dialog.dismiss();
     * dialog.cancel(); }
     *
     * regTitle.setText(getResources().getString(R.string.signup_title));
     *
     * email.setClickable(true); password.setVisibility(View.VISIBLE);
     * confirmPswd.setVisibility(View.VISIBLE);
     * passMsg.setVisibility(View.VISIBLE);
     * layPass.setVisibility(View.VISIBLE);
     * laySub.setVisibility(View.VISIBLE);
     * passMsg.setVisibility(View.VISIBLE); }
     */

}
