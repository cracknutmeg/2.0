package com.allpoint.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.allpoint.AtmFinderApplication;
import com.allpoint.R;
import com.allpoint.services.InternetConnectionManager;
import com.allpoint.services.LoadWebServiceAsync;
import com.allpoint.services.RespSessionInvalid;
import com.allpoint.services.ResponseHandlerForTransactions;
import com.allpoint.services.ResponseVarifyPan;
import com.allpoint.services.WebServiceListner;
import com.allpoint.services.parse.ParseXML;
import com.allpoint.util.Constant;
import com.allpoint.util.Utils;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;

public class ScanCardActivity extends Activity implements WebServiceListner {

    InputStream is;
    private ProgressDialog dialog;
    AtmFinderApplication appContext;
    String hashCardNumber;
    InternetConnectionManager connectionManager;

    RespSessionInvalid mRespForInvalidSession;
    ParseXML parseXML;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        connectionManager = InternetConnectionManager.getInstance(this);

        onScanPress();
        appContext = (AtmFinderApplication) getApplicationContext();

        parseXML = new ParseXML();
    }

    private void onScanPress() {

        Intent scanIntent = new Intent(this, CardIOActivity.class);

        // customize these values to suit your needs.
        // scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true); //
        // default: true
        scanIntent.putExtra(CardIOActivity.EXTRA_USE_CARDIO_LOGO, true);
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false); // default:
        // false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false); // default:
        // false

        // hides the manual entry button
        // if set, developers should provide their own manual entry mechanism in
        // the app
        scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, false); // default:
        // false

        // MY_SCAN_REQUEST_CODE is arbitrary and is only used within this
        // activity.
        startActivityForResult(scanIntent, Constant.MY_SCAN_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
            CreditCard scanResult = data
                    .getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);

            String cardNumber = (scanResult.getFormattedCardNumber()).replace(
                    " ", "");
            callCardWebservice(cardNumber);

            hashCardNumber = "XXXX XXXX XXXX " + cardNumber.substring(cardNumber.length() - 4, cardNumber.length());

        } else {
            finish();

        }
    }

    LoadWebServiceAsync callApi;

    private void callCardWebservice(String cardNoIs) {

        if (!connectionManager.isConnected()) {
            Utils.showDialogAlert(
                    getResources().getString(
                            R.string.en_dialogCannotConnectText),
                    ScanCardActivity.this);
        } else {
            String value = null;

            value = "<PanCheckRequest>" + "<PANData>" + "<EmailId>"
                    + Utils.getUserName().trim() + "</EmailId>" + "<PANNumber>"
                    + cardNoIs + "</PANNumber>"
                    //+ "<ApplicationId>"	+ Constant.ALLPOINT_SERVER_APP_ID + "</ApplicationId>"
                    + "</PANData>" + "</PanCheckRequest>";

            callApi = new LoadWebServiceAsync(getApplicationContext(),
                    ScanCardActivity.this, value,
                    Constant.CUSTOMER_MANAGEMENT_TRANS_URL,
                    Constant.VARIFY_PAN_METHOD_NAME,
                    Constant.VARIFY_PAN_SOAP_ACTION,
                    Constant.CUSTOMER_MANAGEMENT_TRANS_NAMESPACE,
                    Utils.getUserName().trim(), appContext.sessionToken);

            // LoadWebServiceAsync callApi = new
            // LoadWebServiceAsync(getApplicationContext(), LoginActivity.this,
            // value, Constant.HOSTNAME_LINK, Constant.FORGET_METHOD_NAME,
            // Constant.FORGET_SOAP_ACTION,Constant.FORGET_NAMESPACE);

            callApi.execute();
            dialog = ProgressDialog.show(ScanCardActivity.this,
                    "Please wait...", "Loading...");
            dialog.show();
        }
    }

    private ResponseVarifyPan parseXMLForVarifyPan(String resp) {
        ResponseVarifyPan varifyPandetails = null;
        try {

            //resp = "<BinCheckServiceResponse><Status>False</Status><StatusCode>101</StatusCode><StatusMessage>PAN does not exist.</StatusMessage><IsActive>False</IsActive></BinCheckServiceResponse>";

            // is = getResources().openRawResource(R.raw.resp);
            is = new ByteArrayInputStream(resp.getBytes());
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();

            ResponseHandlerForTransactions resp_Handler = new ResponseHandlerForTransactions();
            xr.setContentHandler(resp_Handler);
            InputSource inStream = new InputSource(is);
            xr.parse(inStream);

            varifyPandetails = resp_Handler.getVarifyPanResp();

            is.close();

        } catch (Exception e) {
            //e.printStackTrace();
        }

        return varifyPandetails;
    }

    public void showDialogAlert(String message) {

        AlertDialog alertDialog = new AlertDialog.Builder(ScanCardActivity.this)
                .create();
        alertDialog.setMessage(message);

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        finish();

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

    /****************************** Handling Server Side Response ***********************************/
    @Override
    public void onResult(String result) {

        mRespForInvalidSession = parseXML.parseXMLforSessionInvalid(result);
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

            ResponseVarifyPan mResult = parseXMLForVarifyPan(result);

            if (mResult != null && mResult.getVarifyPanStatus()) {

                if (dialog != null) {
                    dialog.dismiss();
                }

                // check active & inActive flag
                if (mResult.getVarifyActivtInActiveCode()) {

                    showCardScreenSuccess();


                } else {

                    //Utils.showDialogAlert(mResult.getVarifyPanStatusMessage(),
                    //		ScanCardActivity.this);
                    showCardFailScreen();
                }

            } else if (mResult != null
                    && Integer.parseInt(mResult.getVarifyPanStatusCode()) == 101) {
                if (dialog != null) {
                    dialog.dismiss();
                }

                showCardFailScreen();


            } else if (mResult != null
                    && Integer.parseInt(mResult.getVarifyPanStatusCode()) == 131) {
                if (dialog != null) {
                    dialog.dismiss();
                }

                showCardScreenSuccess();

            } else {
                if (dialog != null) {
                    dialog.dismiss();
                }

                String msg = "";

                if (mResult != null) {
                    //msg = mResult.getVarifyPanStatusMessage();
                    showCardFailScreen();
                    //Utils.showDialogAlert(mResult.getVarifyPanStatusMessage(),
                    //		ScanCardActivity.this);
                } else {
                    Utils.showDialogAlert(
                            getResources().getString(
                                    R.string.err_server_Connection),
                            ScanCardActivity.this);
                }

            }
        }
    }

    private void showCardScreenSuccess() {
        // TODO Auto-generated method stub
        appContext.isCardSuccess = true;

        Intent intent = new Intent(ScanCardActivity.this,
                com.allpoint.activities.CardSuccessActivity.class);
        intent.putExtra("card_number", hashCardNumber);

        startActivity(intent);
        finish();
        // Utils.startActivity(ScanCardActivity.this,
        // com.allpoint.activities.CardSuccessActivity.class,
        // false, false, true);
    }

    private void showCardFailScreen() {
        // TODO Auto-generated method stub
        appContext.isCardSuccess = false;
        Intent intent = new Intent(ScanCardActivity.this,
                com.allpoint.activities.CardSuccessActivity.class);
        intent.putExtra("card_number", hashCardNumber);

        startActivity(intent);

        finish();
    }

    public void showSessionInvalid(String message) {

        AlertDialog alertDialog = new AlertDialog.Builder(ScanCardActivity.this)
                .create();
        alertDialog.setMessage(message);
        alertDialog.setCancelable(true);
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Utils.setLoginStatus(false);
                        Utils.setUsername("");
                        Utils.startActivity(ScanCardActivity.this,
                                com.allpoint.activities.LoginActivity.class,
                                false, false, true);

                    }
                });
        alertDialog.show();
    }
}
