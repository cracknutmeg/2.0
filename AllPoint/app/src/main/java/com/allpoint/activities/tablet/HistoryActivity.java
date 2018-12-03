package com.allpoint.activities.tablet;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.allpoint.AtmFinderApplication;
import com.allpoint.R;
import com.allpoint.services.InternetConnectionManager;
import com.allpoint.services.LoadWebServiceAsync;
import com.allpoint.services.RespSessionInvalid;
import com.allpoint.services.ResponseCustomerProtfolioDetails;
import com.allpoint.services.ResponseTransaction;
import com.allpoint.services.WebServiceListner;
import com.allpoint.services.parse.ParseXML;
import com.allpoint.util.Constant;
import com.allpoint.util.Utils;
import com.allpoint.util.adapters.ExpandableListAdapter;
import com.bugsense.trace.BugSenseHandler;

import java.io.InputStream;

public class HistoryActivity extends FragmentActivity implements OnClickListener, WebServiceListner {

    ResponseTransaction respTransIs = null;

    InternetConnectionManager connectionManager;

    InputStream is;
    private ProgressDialog dialog;
    // Declare variable
    private static int prev = -1;

    protected ExpandableListView displayList;
    ImageButton transactionButton;
    TextView transactionButtonText;
    protected TextView cardHistoryTitle;
    protected TextView curSavingIs;

    AtmFinderApplication atmfinderappcontext;

    private ExpandableListAdapter displayListAdapter;
    // private ArrayList<ResponseTransactionDetails> mCardHistoryList;

    RespSessionInvalid mRespForInvalidSession;
    ParseXML parseXml;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_history);
        connectionManager = InternetConnectionManager.getInstance(this);

        BugSenseHandler.initAndStartSession(this, Constant.BUG_SENSE_API_KEY);

        atmfinderappcontext = (AtmFinderApplication) getApplicationContext();
        parseXml = new ParseXML();

        initGUIElements();
        AfterViews();
    }

    private void initGUIElements() {


        displayList = findViewById(R.id.displayList);
        transactionButton = findViewById(R.id.iBtnBottomTransaction);
        transactionButtonText = findViewById(R.id.iTxtBottomTransaction);
        cardHistoryTitle = findViewById(R.id.tvcardhistoryTitle);
        curSavingIs = findViewById(R.id.curSaving);

    }

    void AfterViews() {
        transactionButton.setImageResource(R.drawable.bottom_history_press);
        transactionButtonText.setTextColor(getResources().getColor(
                R.color.textColor));
        transactionButton.setEnabled(false);

        if (Utils.getLoginStatus()) {

            if (getIntent().getSerializableExtra("selected_card") != null) {
                ResponseCustomerProtfolioDetails cardBean = (ResponseCustomerProtfolioDetails) getIntent()
                        .getSerializableExtra("selected_card");
                // cardHistoryTitle.setText(cardBean.getCustomerPan());
                cardHistoryTitle.setText(getResources().getString(
                        R.string.en_titleTransaction));
                curSavingIs.setText(getResources().getString(
                        R.string.curSavings)
                        + " " + "$" + cardBean.getCustomerSavedMoney());

                callHistoryService(cardBean.getCustomerPan());
            }
        } else {
            curSavingIs.setText(getResources().getString(
                    R.string.historyLoginMsg));
        }
    }

    LoadWebServiceAsync callApi;

    private void callHistoryService(String CardNo) {

        if (!connectionManager.isConnected()) {
            Utils.showDialogAlert(
                    getResources().getString(
                            R.string.en_dialogCannotConnectText),
                    HistoryActivity.this);
            if (dialog != null) {
                dialog.dismiss();
            }
        } else {
            String value = null;

            value = "<TransactionDetailRequest>" + "<PANTranscationData>"
                    + "<EmailId>" + Utils.getUserName().trim() + "</EmailId>"
                    + "<PANNumber>" + CardNo + "</PANNumber>" + "<Count>"
                    + Constant.COUNT_TRANSACTION + "</Count>"
                    //+ "<ApplicationId>" + Constant.ALLPOINT_SERVER_APP_ID	+ "</ApplicationId>"
                    + "</PANTranscationData>"
                    + "</TransactionDetailRequest>";

            callApi = new LoadWebServiceAsync(getApplicationContext(),
                    HistoryActivity.this, value,
                    Constant.CUSTOMER_MANAGEMENT_URL,
                    Constant.TRANS_METHOD_NAME, Constant.TRANS_SOAP_ACTION,
                    Constant.CUSTOMER_MANAGEMENT_TRANS_NAMESPACE,
                    Utils.getUserName().trim(), atmfinderappcontext.sessionToken);

            // LoadWebServiceAsync callApi = new
            // LoadWebServiceAsync(getApplicationContext(), LoginActivity.this,
            // value, Constant.HOSTNAME_LINK, Constant.FORGET_METHOD_NAME,
            // Constant.FORGET_SOAP_ACTION,Constant.FORGET_NAMESPACE);

            callApi.execute();
            dialog = ProgressDialog.show(HistoryActivity.this,
                    "Please wait...", "Loading...");
            dialog.show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {

    }

    public void onIbtnCancelClicked(View view) {
        HistoryActivity.this.finish();
    }

    @Override
    public void onResult(String result) {

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
            ResponseTransaction mResult = parseXml.parseXMLhistoryResp(result);
            // ResponseTransaction mResult = parseXMLhistoryResp(result);

            if (dialog != null) {
                dialog.dismiss();
            }

            if (mResult != null && !mResult.getRspTrans().isEmpty()) {

                displayExpandableList(mResult);

            } else if (mResult != null && mResult.getRspTrans().size() == 0) {

                Utils.showDialogAlert(
                        getResources().getString(R.string.no_trans_found_msg),
                        HistoryActivity.this);

            } else {

                Utils.showDialogAlert(
                        mResult.getResponseTransactionStatusMessage(),
                        HistoryActivity.this);
            }
        }
    }

    private void displayExpandableList(ResponseTransaction mResult) {

        displayListAdapter = new ExpandableListAdapter(this, mResult);
        displayList.setAdapter(displayListAdapter);

        displayList.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                return false;
            }
        });

        displayList.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != prev)
                    displayList.collapseGroup(prev);
                prev = groupPosition;

            }
        });

    }

    /*
     * private ResponseTransaction parseXMLhistoryResp(String resp) {
     *
     * try { // is = getResources().openRawResource(R.raw.resp); is = new
     * ByteArrayInputStream(resp.getBytes()); SAXParserFactory spf =
     * SAXParserFactory.newInstance(); SAXParser sp = spf.newSAXParser();
     * XMLReader xr = sp.getXMLReader();
     *
     * ResponseHandlerForTransactions resp_Handler = new
     * ResponseHandlerForTransactions(); xr.setContentHandler(resp_Handler);
     * InputSource inStream = new InputSource(is); xr.parse(inStream);
     *
     * respTransIs = resp_Handler.getTransactions();
     *
     * is.close();
     *
     * } catch (Exception e) { e.printStackTrace(); }
     *
     * return respTransIs; }
     */

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

        AlertDialog alertDialog = new AlertDialog.Builder(HistoryActivity.this)
                .create();
        alertDialog.setMessage(message);

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Utils.setLoginStatus(false);
                        Utils.startActivity(HistoryActivity.this,
                                com.allpoint.activities.LoginActivity.class,
                                false, false, true);

                    }
                });
        alertDialog.show();
    }

}
