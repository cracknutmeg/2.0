package com.allpoint.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.allpoint.AtmFinderApplication;
import com.allpoint.R;
import com.allpoint.model.ResgistrationResponseData;
import com.allpoint.services.InternetConnectionManager;
import com.allpoint.services.LoadWebServiceAsync;
import com.allpoint.services.RespChangePass;
import com.allpoint.services.RespResetPassword;
import com.allpoint.services.RespSessionInvalid;
import com.allpoint.services.ResponseHandler;
import com.allpoint.services.WebServiceListner;
import com.allpoint.services.parse.ParseXML;
import com.allpoint.util.Constant;
import com.allpoint.util.Utils;
import com.allpoint.util.Validation;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class ChangePasswordActivity extends Activity implements
        WebServiceListner {

    TextView txtChangePass;
    EditText passOld;
    EditText passNew;
    EditText passConf;
    InternetConnectionManager connectionManager;
    private ProgressDialog dialog;

    String AlertMessage;
    String respCardDetails;
    InputStream is;
    ResgistrationResponseData respData = null;
    ProgressDialog progressDialog = null;

    AtmFinderApplication atmfinderappcontext;

    RespSessionInvalid mRespForInvalidSession;
    ParseXML parseXml;
    private Button iBtnSend;
    private TextView cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password_activity);
        connectionManager = InternetConnectionManager.getInstance(this);
        initGUIElements();

        iBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onIbtnSignClicked();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onIbtnBackClicked();
            }
        });

        atmfinderappcontext = (AtmFinderApplication) getApplicationContext();

        parseXml = new ParseXML();
    }

    private void initGUIElements() {

        txtChangePass = findViewById(R.id.change_pass_text);
        passOld = findViewById(R.id.iEdTxtOldPassword);
        passNew = findViewById(R.id.iEdTxtNewPassword);
        passConf = findViewById(R.id.iEdTxtConfirmPassword);
        iBtnSend = findViewById(R.id.iBtnSend);
        cancel = findViewById(R.id.cancel);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        if (!atmfinderappcontext.setChangePassFromSettings) {

            // txtChangePass.setText(getResources().getString(R.string.changePassTextforForget));
            passOld.setVisibility(View.GONE);

        } else {

            // txtChangePass.setText(getResources().getString(R.string.forgetPassText));
            passOld.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

        if (!atmfinderappcontext.setChangePassFromSettings) {
            txtChangePass.setText(getResources().getString(
                    R.string.changePassTextforForget));
        } else {
            txtChangePass.setText(getResources().getString(
                    R.string.forgetPassText));
        }
    }

    void onIbtnSignClicked() {

        // Utils.startActivity(ChangePasswordActivity.this,
        // com.allpoint.activities.LoginActivity.class, false, false, true);
        checkValidation(atmfinderappcontext.setChangePassFromSettings);

    }

    void onIbtnBackClicked() {
        finish();
    }

    private void checkValidation(Boolean fromSetting) {

        if (fromSetting) {

            // Validation is Remainig
            if (passOld.getText().length() == 0) {
                passOld.requestFocus();
                AlertMessage = getResources().getString(
                        R.string.Err_msg_EnterOldPassword);
                Utils.showDialogAlert(AlertMessage, ChangePasswordActivity.this);
            } else if (!Validation.passwordvalidation(passOld.getText()
                    .toString().trim())) {
                passOld.requestFocus();
                AlertMessage = getResources().getString(
                        R.string.Err_Msg_ChangeOldPassword);
                Utils.showDialogAlert(AlertMessage, ChangePasswordActivity.this);
            } else if (passOld.getText().length() == 0) {
                passNew.requestFocus();
                AlertMessage = getResources().getString(
                        R.string.Err_msg_EnterNewPassword);
                Utils.showDialogAlert(AlertMessage, ChangePasswordActivity.this);
            } else if (!Validation.passwordvalidation(passNew.getText()
                    .toString().trim())) {
                passNew.requestFocus();
                AlertMessage = getResources().getString(
                        R.string.Err_Msg_ChangeNewPassword);
                Utils.showDialogAlert(AlertMessage, ChangePasswordActivity.this);
            } else if (passConf.getText().length() == 0) {
                passConf.requestFocus();
                AlertMessage = getResources().getString(
                        R.string.Err_msg_EnterConfirmPassword);
                Utils.showDialogAlert(AlertMessage, ChangePasswordActivity.this);
            } else if (!Validation.passwordvalidation(passConf.getText()
                    .toString().trim())) {
                passConf.requestFocus();
                AlertMessage = getResources().getString(
                        R.string.Err_Msg_ChangeConfirmPassword);
                Utils.showDialogAlert(AlertMessage, ChangePasswordActivity.this);
            } else if (!passNew.getText().toString().trim()
                    .equals(passConf.getText().toString().trim())) {
                passNew.requestFocus();
                AlertMessage = getResources().getString(
                        R.string.Err_Msg_PwdMatch);
                Utils.showDialogAlert(AlertMessage, ChangePasswordActivity.this);
            } else if (passNew != null
                    && passConf != null
                    && (passNew.getText().toString()).equals(passConf.getText()
                    .toString())) { // check that passwords match or not

                Utils.hideKeyboard(ChangePasswordActivity.this);

                callChangePasswordWebApi(fromSetting);
            }
        } else {

            if (!Validation.passwordvalidation(passNew.getText().toString()
                    .trim())) {
                passNew.requestFocus();
                AlertMessage = getResources().getString(
                        R.string.Err_Msg_ChangeNewPassword);
                Utils.showDialogAlert(AlertMessage, ChangePasswordActivity.this);
            } else if (passConf.getText().length() == 0) {
                passConf.requestFocus();
                AlertMessage = getResources().getString(
                        R.string.Err_msg_EnterConfirmPassword);
                Utils.showDialogAlert(AlertMessage, ChangePasswordActivity.this);
            } else if (!Validation.passwordvalidation(passConf.getText()
                    .toString().trim())) {
                passConf.requestFocus();
                AlertMessage = getResources().getString(
                        R.string.Err_Msg_ChangeConfirmPassword);
                Utils.showDialogAlert(AlertMessage, ChangePasswordActivity.this);
            } else if (!passNew.getText().toString().trim()
                    .equals(passConf.getText().toString().trim())) {
                passNew.requestFocus();
                AlertMessage = getResources().getString(
                        R.string.Err_Msg_PwdMatch);
                Utils.showDialogAlert(AlertMessage, ChangePasswordActivity.this);
            } else if (passNew != null
                    && passConf != null
                    && (passNew.getText().toString()).equals(passConf.getText()
                    .toString())) { // check that passwords match or not

                Utils.hideKeyboard(ChangePasswordActivity.this);

                callChangePasswordWebApi(fromSetting);
            }
        }

    }

    LoadWebServiceAsync callApi;

    private void callChangePasswordWebApi(boolean fromSettingAPI) {

        if (!connectionManager.isConnected()) {
            Utils.showDialogAlert(
                    getResources().getString(
                            R.string.en_dialogCannotConnectText),
                    ChangePasswordActivity.this);

        } else {

            if (fromSettingAPI) {

                // From Setting Change Password
                String value = "<ChangePasswordRequest>" + "<PasswordData>"
                        + "<UserName>" + Utils.getUserName().trim() + "</UserName>"
                        + "<OldPassword>" + passOld.getText().toString() + "</OldPassword>"
                        + "<NewPassword>" + passNew.getText().toString() + "</NewPassword>"
                        //+ "<ApplicationId>" + Constant.ALLPOINT_SERVER_APP_ID + "</ApplicationId>"
                        + "</PasswordData></ChangePasswordRequest>";

                callApi = new LoadWebServiceAsync(getApplicationContext(),
                        ChangePasswordActivity.this, value,
                        Constant.CUSTOMER_MANAGEMENT_URL,
                        Constant.CHANGE_METHOD_NAME,
                        Constant.CHANGE_SOAP_ACTION,
                        Constant.CUSTOMER_MANAGEMENT_NAMESPACE,
                        Utils.getUserName().trim(), atmfinderappcontext.sessionToken);

            } else {

                // From forget Pass to change password
                String value = "<ChangePasswordRequest>" + "<PasswordData>"
                        + "<UserName>" + Utils.getUserName().trim() + "</UserName>"
                        + "<NewPassword>" + passNew.getText().toString() + "</NewPassword>"
                        + "<Toekn>" + atmfinderappcontext.tempTokenForOTP + "</Toekn>"
                        //+ "<ApplicationId>"	+ Constant.ALLPOINT_SERVER_APP_ID + "</ApplicationId>"
                        + "</PasswordData></ChangePasswordRequest>";

                callApi = new LoadWebServiceAsync(getApplicationContext(),
                        ChangePasswordActivity.this, value,
                        Constant.CUSTOMER_MANAGEMENT_URL,
                        Constant.CHANGE_RESET_METHOD_NAME,
                        Constant.CHANGE_RESET_SOAP_ACTION,
                        Constant.CUSTOMER_MANAGEMENT_NAMESPACE,
                        Utils.getUserName().trim(), atmfinderappcontext.sessionToken);

            }

            callApi.execute();
            dialog = ProgressDialog.show(ChangePasswordActivity.this,
                    "Please wait....", "Loading..");
            dialog.show();
        }
    }

    @Override
    public void onResult(String result) {

        if (atmfinderappcontext.setChangePassFromSettings) {

            mRespForInvalidSession = parseXml.parseXMLforSessionInvalid(result);
            // mRespForInvalidSession = parseXMLforSessionInvalid(result);

            // if session is Invalid
            if (mRespForInvalidSession != null
                    && mRespForInvalidSession.getSessionInvalidStatusCode()
                    .equals(Constant.SESSION_ERROR_CODE)) {
                if (dialog != null) {
                    dialog.dismiss();
                }

                // show message
                // showSessionInvalid(mRespForInvalidSession.getSessionInvalidStatusMessage());
                showSessionInvalid(getResources().getString(
                        R.string.msg_sessionInvalid));

            } else {

                RespChangePass mResult = parseXMLForLgoinUser(result);

                if (mResult != null && mResult.getChangePassStatus()) {
                    if (dialog != null) {
                        dialog.dismiss();
                    }

                    showDialogCloseAlert(mResult.getChangePassStatusMessage());
                } else if (mResult != null
                        && mResult.getChangePassStatusCode().equals(
                        Constant.SESSION_ERROR_CODE)) { // check error
                    // code and
                    // session

                    // go to Login if session invalid
                    Utils.startActivity(ChangePasswordActivity.this,
                            com.allpoint.activities.LoginActivity.class,
                            false, false, true);

                } else {
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    if (mResult != null) {

                        if (mResult.getChangePassStatusMessage() != null) {
                            Utils.showDialogAlert(
                                    mResult.getChangePassStatusMessage(),
                                    ChangePasswordActivity.this);
                        } else {
                            Utils.showDialogAlert(
                                    getResources().getString(
                                            R.string.err_server_Connection),
                                    ChangePasswordActivity.this);
                        }
                    } else {
                        Utils.showDialogAlert(
                                getResources().getString(
                                        R.string.err_server_Connection),
                                ChangePasswordActivity.this);
                    }
                }
            }

        } else {

            // from forgot reset password
            RespResetPassword mResult = parseXMLForReset(result);

            if (mResult != null && mResult.getResetPassStatus()) {
                if (dialog != null) {
                    dialog.dismiss();
                }

                showDialogCloseAlert(mResult.getResetPassStatusMessage());
            } else {
                if (dialog != null) {
                    dialog.dismiss();
                }
                if (mResult != null) {

                    if (mResult.getResetPassStatusMessage() != null) {
                        Utils.showDialogAlert(
                                mResult.getResetPassStatusMessage(),
                                ChangePasswordActivity.this);
                    } else {
                        Utils.showDialogAlert(
                                getResources().getString(
                                        R.string.err_server_Connection),
                                ChangePasswordActivity.this);
                    }
                } else {
                    Utils.showDialogAlert(
                            getResources().getString(
                                    R.string.err_server_Connection),
                            ChangePasswordActivity.this);
                }
            }
        }
    }

    private RespChangePass parseXMLForLgoinUser(String resp) {
        RespChangePass uforgetPassDetails = null;
        try {
            // is = getResources().openRawResource(R.raw.resp);
            is = new ByteArrayInputStream(resp.getBytes());
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();

            ResponseHandler resp_Handler = new ResponseHandler();
            xr.setContentHandler(resp_Handler);
            InputSource inStream = new InputSource(is);
            xr.parse(inStream);

            uforgetPassDetails = resp_Handler.getChangePassResp();

            is.close();

        } catch (Exception e) {
            //e.printStackTrace();
        }

        return uforgetPassDetails;
    }

    private RespResetPassword parseXMLForReset(String resp) {
        RespResetPassword resetDetails = null;
        try {
            // is = getResources().openRawResource(R.raw.resp);
            is = new ByteArrayInputStream(resp.getBytes());
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();

            ResponseHandler resp_Handler = new ResponseHandler();
            xr.setContentHandler(resp_Handler);
            InputSource inStream = new InputSource(is);
            xr.parse(inStream);

            resetDetails = resp_Handler.getResetPassResp();

            is.close();

        } catch (Exception e) {
            //e.printStackTrace();
        }

        return resetDetails;
    }

    private void showDialogCloseAlert(String msg) {
        AlertDialog alertDialog = new AlertDialog.Builder(
                ChangePasswordActivity.this).create();
        alertDialog.setMessage(msg);

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        if (atmfinderappcontext.setChangePassFromSettings) {

                            //
                            finish();

                        } else {

                            final Class<?> activityClass;
                            if (Utils.isTablet()) {
                                activityClass = com.allpoint.activities.LoginActivity.class;
                            } else {
                                activityClass = com.allpoint.activities.LoginActivity.class;
                            }
                            Utils.startActivity(ChangePasswordActivity.this,
                                    activityClass, false, false, true);

                        }

                    }
                });
        alertDialog.show();

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

        AlertDialog alertDialog = new AlertDialog.Builder(ChangePasswordActivity.this)
                .create();
        alertDialog.setMessage(message);
        alertDialog.setCancelable(true);
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Utils.setLoginStatus(false);
                        Utils.setUsername("");
                        Utils.startActivity(ChangePasswordActivity.this,
                                com.allpoint.activities.LoginActivity.class,
                                false, false, true);

                    }
                });
        alertDialog.show();
    }
}
