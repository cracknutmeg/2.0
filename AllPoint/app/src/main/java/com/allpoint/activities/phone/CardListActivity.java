package com.allpoint.activities.phone;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.allpoint.AtmFinderApplication;
import com.allpoint.R;
import com.allpoint.services.InternetConnectionManager;
import com.allpoint.services.LoadWebServiceAsync;
import com.allpoint.services.RespSessionInvalid;
import com.allpoint.services.ResponseCustomerPortfilio;
import com.allpoint.services.ResponseHandlerForTransactions;
import com.allpoint.services.WebServiceListner;
import com.allpoint.services.parse.ParseXML;
import com.allpoint.util.Constant;
import com.allpoint.util.Utils;
import com.allpoint.util.adapters.RecyclerViewAdapter;
import com.bugsense.trace.BugSenseHandler;
import com.daimajia.swipe.util.Attributes;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import jp.wasabeef.recyclerview.animators.FadeInLeftAnimator;

public class CardListActivity extends FragmentActivity implements RecyclerViewAdapter.OnItemClickListner, WebServiceListner {

    ResponseCustomerPortfilio customerPortfilioResp = null;
    ResponseCustomerPortfilio mResult;

    RespSessionInvalid mRespForInvalidSession;

    InternetConnectionManager connectionManager;

    InputStream is;
    private ProgressDialog dialog;
    private ImageButton transactionButton;
    private TextView transactionButtonText;
    private TextView titletextIs;
    private AtmFinderApplication atmfinderappcontext;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;

    ParseXML parseXml;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cardlist);
        atmfinderappcontext = (AtmFinderApplication) getApplicationContext();
        parseXml = new ParseXML();

        transactionButton = findViewById(R.id.iBtnBottomTransaction);
        transactionButtonText = findViewById(R.id.iTxtBottomTransaction);
        titletextIs = findViewById(R.id.titletext);
        BugSenseHandler.initAndStartSession(this, Constant.BUG_SENSE_API_KEY);
        AfterViews();


    }

    void AfterViews() {

        recyclerView = (RecyclerView) findViewById(R.id.cardlist);
        // Layout Managers:
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        transactionButton.setImageResource(R.drawable.bottom_history_press);
        transactionButtonText.setTextColor(getResources().getColor(
                R.color.textColor));

        if (Utils.getLoginStatus()) {

            titletextIs.setText(getResources()
                    .getString(R.string.deleteCardMsg));

            // Item Decorator:
            // recyclerView.addItemDecoration(new
            // DividerItemDecoration(getResources().getDrawable(R.drawable.divider)));
            recyclerView.setItemAnimator(new FadeInLeftAnimator());

            callCardPortfilioService();
        } else {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }

            showCloseDialogAlert(getResources().getString(
                    R.string.historyLoginMsg));

            titletextIs.setText(getResources().getString(
                    R.string.historyLoginMsg));
            recyclerView.setAdapter(null);
        }
    }

    private void showCloseDialogAlert(String msg) {
        AlertDialog alertDialog = new AlertDialog.Builder(CardListActivity.this)
                .create();
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(false);
        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // do whatever you want the back key to do
            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        finish();
                        Utils.startActivity(CardListActivity.this,
                                com.allpoint.activities.LoginActivity.class,
                                false, false, true);
                    }
                });
        alertDialog.show();

    }

    private void showCloseDialogAlertDisplay(String msg) {
        AlertDialog alertDialog = new AlertDialog.Builder(CardListActivity.this)
                .create();
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(false);
        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // do whatever you want the back key to do
            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        // finish();
                        // Utils.startActivity(CardListActivity.this,
                        // com.allpoint.activities.LoginActivity.class, false,
                        // false, true);
                    }
                });
        alertDialog.show();

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        super.onWindowFocusChanged(hasFocus);

        if (customerPortfilioResp != null
                && customerPortfilioResp.getCustomerProtfolio().size() > 0) {
            // int contentHeight = recyclerView.getChildAt(0).getHeight();
            int contentHeight = 180;
            // set listview height
            android.view.ViewGroup.LayoutParams lp = recyclerView
                    .getLayoutParams();
            lp.height = contentHeight
                    * customerPortfilioResp.getCustomerProtfolio().size();
            recyclerView.setLayoutParams(lp);
        } else {
            titletextIs.setVisibility(View.GONE);
            android.view.ViewGroup.LayoutParams lp = recyclerView
                    .getLayoutParams();
            lp.height = 0;
            recyclerView.setLayoutParams(lp);
        }
    }

    @Override
    protected void onResume() {

        if (Utils.getLoginStatus()) {

            if (customerPortfilioResp != null) {
                mAdapter = new RecyclerViewAdapter(CardListActivity.this, customerPortfilioResp);
                mAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(mAdapter);
            }

        } else {

            showCloseDialogAlert(getResources().getString(
                    R.string.historyLoginMsg));

            titletextIs.setText(getResources().getString(
                    R.string.historyLoginMsg));
            recyclerView.setAdapter(null);
        }
        super.onResume();
    }

    @Override
    protected void onPause() {

        super.onPause();
    }

    @Override
    protected void onStop() {

        super.onPause();
    }

    LoadWebServiceAsync callApi;

    private void callCardPortfilioService() {

        connectionManager = InternetConnectionManager.getInstance(CardListActivity.this);
        if (!connectionManager.isConnected()) {
            Utils.showDialogAlert(
                    getResources().getString(
                            R.string.en_dialogCannotConnectText),
                    CardListActivity.this);

        } else {
            String value;

            value = "<CustomerProtfolioRequest>"
                    + "<CustomerProtfolio>"
                    + "<EmailId>"
                    + Utils.getUserName().trim()
                    + "</EmailId>"
                    + "<StartDate> "
                    + new SimpleDateFormat("yyyy").format(new Date()).concat(
                    "-01-01") + "</StartDate>" + "<EndDate> "
                    + new SimpleDateFormat("yyyy-MM-dd").format(new Date())
                    + "</EndDate>"
                    //+ "<ApplicationId>"	+ Constant.ALLPOINT_SERVER_APP_ID + "</ApplicationId>"
                    + "</CustomerProtfolio>" + "</CustomerProtfolioRequest>";

            callApi = new LoadWebServiceAsync(getApplicationContext(),
                    CardListActivity.this, value,
                    Constant.CUSTOMER_MANAGEMENT_TRANS_URL,
                    Constant.CUSTOMER_PORTFILIO_METHOD_NAME,
                    Constant.CUSTOMER_PORTFILIO_SOAP_ACTION,
                    Constant.CUSTOMER_MANAGEMENT_TRANS_NAMESPACE,
                    Utils.getUserName().trim(), atmfinderappcontext.sessionToken);

            // LoadWebServiceAsync callApi = new
            // LoadWebServiceAsync(getApplicationContext(), LoginActivity.this,
            // value, Constant.HOSTNAME_LINK, Constant.FORGET_METHOD_NAME,
            // Constant.FORGET_SOAP_ACTION,Constant.FORGET_NAMESPACE);
            dialog = ProgressDialog.show(CardListActivity.this,
                    "Please wait...", "Loading...");
            dialog.show();
            callApi.execute();

        }
    }

   /* @Click(R.id.addcard)
    void onIbtnAddCardClicked() {
        Utils.startActivity(CardListActivity.this,
                com.allpoint.activities.CardCheckActivity.class, false, false,
                true);
    }

    @Click(R.id.cancelcardList)
    void onIbtnCancelCardListClicked() {
        startActivity(new Intent(CardListActivity.this,
                com.allpoint.activities.phone.MainMenuActivity.class));
        finish();

    }*/

    @Override
    public void afterOnclick(int position) {

        if (customerPortfilioResp.getCustomerPortfilioCardHistoryEnabled()) {
            Intent cardHistoryIntent = new Intent(CardListActivity.this,
                    HistoryActivity.class);
            cardHistoryIntent.putExtra("selected_card", customerPortfilioResp
                    .getCustomerProtfolio().get(position));
            startActivity(cardHistoryIntent);
        } else {

            showCloseDialogAlertDisplay(getResources().getString(
                    R.string.en_card_history_disable));
            // Utils.showDialogAlert(getResources().getString(R.string.en_card_history_disable),
            // CardListActivity.this);
        }

    }

    @Override
    public void onResult(String result) {

        if (Utils.getLoginStatus()) {

            mRespForInvalidSession = parseXml.parseXMLforSessionInvalid(result);

            // if session is Invalid
            if (mRespForInvalidSession != null
                    && mRespForInvalidSession.getSessionInvalidStatusCode().trim()
                    .equals(Constant.SESSION_ERROR_CODE)) {
                if (dialog != null) {
                    dialog.dismiss();
                }

                // show message
                // showSessionInvalid(mRespForInvalidSession.getSessionInvalidStatusMessage());
                showSessionInvalid(getResources().getString(
                        R.string.msg_sessionInvalid));

            } else {

                // if session is valid

                ResponseCustomerPortfilio mResult = parseXMLCustomerPortfilio(result);
                // mResult = parseXml.parseXMLCustomerPortfilio(result);

                if (mResult != null
                        && !mResult.getCustomerProtfolio().isEmpty()
                        && mResult.getCustomerPortfilioStatus()) {

                    if (dialog != null) {
                        dialog.dismiss();
                    }

                    mAdapter = new RecyclerViewAdapter(this, mResult);
                    ((RecyclerViewAdapter) mAdapter)
                            .setMode(Attributes.Mode.Single);
                    mAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(mAdapter);

                } else if (mResult != null && mResult.getCustomerProtfolio().size() == 0) {

                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    Utils.showDialogAlert(
                            getResources().getString(
                                    R.string.msg_NoCard),
                            CardListActivity.this);
                } else {

                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    Utils.showDialogAlert(
                            getResources().getString(
                                    R.string.en_dialogCannotConnectText),
                            CardListActivity.this);

                }
            }
        }
    }

    /*
     * private RespSessionInvalid parseXMLforSessionInvalid(String resp) {
     *
     * try { // is = getResources().openRawResource(R.raw.resp); is = new
     * ByteArrayInputStream(resp.getBytes()); SAXParserFactory spf =
     * SAXParserFactory.newInstance(); SAXParser sp = spf.newSAXParser();
     * XMLReader xr = sp.getXMLReader();
     *
     * ResponseHandler resp_Handler = new ResponseHandler();
     * xr.setContentHandler(resp_Handler); InputSource inStream = new
     * InputSource(is); xr.parse(inStream);
     *
     * mRespForInvalidSession = resp_Handler.getSessionInvalidresp();
     *
     * is.close();
     *
     * } catch (Exception e) { e.printStackTrace(); }
     *
     * return mRespForInvalidSession; }
     */

    private ResponseCustomerPortfilio parseXMLCustomerPortfilio(String resp) {

        try {
            // is = getResources().openRawResource(R.raw.resp);
            is = new ByteArrayInputStream(resp.getBytes());
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();

            ResponseHandlerForTransactions resp_Handler = new ResponseHandlerForTransactions();
            xr.setContentHandler(resp_Handler);
            InputSource inStream = new InputSource(is);
            xr.parse(inStream);

            customerPortfilioResp = resp_Handler.getCustomerPortfilio();

            is.close();

        } catch (Exception e) {
            //e.printStackTrace();
        }

        return customerPortfilioResp;
    }

    @Override
    public void showDialog(String msg) {

    }

    @Override
    public void updateList(ResponseCustomerPortfilio mDataset) {
        customerPortfilioResp = mDataset;

    }

    @Override
    public void showDialogForNetwork() {
        Utils.showDialogAlert(
                getResources().getString(R.string.en_dialogCannotConnectText),
                CardListActivity.this);

    }

    public void showSessionInvalid(String message) {

        AlertDialog alertDialog = new AlertDialog.Builder(CardListActivity.this)
                .create();
        alertDialog.setMessage(message);
        alertDialog.setCancelable(true);
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Utils.setLoginStatus(false);
                        Utils.setUsername("");
                        Utils.startActivity(CardListActivity.this,
                                com.allpoint.activities.LoginActivity.class,
                                false, false, true);

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

}
