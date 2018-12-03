package com.allpoint.activities.tablet;

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

    private ResponseCustomerPortfilio customerPortfilioResp = null;
    private AtmFinderApplication atmfinderappcontext;
    private InternetConnectionManager connectionManager;
    private ProgressDialog dialog;

    private ImageButton transactionButton;
    private TextView transactionButtonText;
    private TextView titletextIs;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;

    ParseXML parseXml;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cardlist);
        connectionManager = InternetConnectionManager.getInstance(this);
        BugSenseHandler.initAndStartSession(this, Constant.BUG_SENSE_API_KEY);
        atmfinderappcontext = (AtmFinderApplication) getApplicationContext();
        parseXml = new ParseXML();

        initGUIElements();
        AfterViews();


    }

    private void initGUIElements() {

        transactionButton = findViewById(R.id.iBtnBottomTransaction);
        transactionButtonText = findViewById(R.id.iTxtBottomTransaction);
        titletextIs = findViewById(R.id.titletext);
    }

    void AfterViews() {

        recyclerView = findViewById(R.id.cardlist);
        // Layout Managers:
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        transactionButton.setImageResource(R.drawable.bottom_history_press);
        transactionButtonText.setTextColor(getResources().getColor(R.color.textColor));

        if (Utils.getLoginStatus()) {

            titletextIs.setText(getResources().getString(R.string.deleteCardMsg));

            // Item Decorator:
            // recyclerView.addItemDecoration(new
            // DividerItemDecoration(getResources().getDrawable(R.drawable.divider)));
            recyclerView.setItemAnimator(new FadeInLeftAnimator());

            callCardPortfilioService();
        } else {
            /*
             * if(dialog!=null){ dialog.dismiss(); }
             */

            showCloseDialogAlert(getResources().getString(R.string.historyLoginMsg));

            titletextIs.setText(getResources().getString(R.string.historyLoginMsg));
            recyclerView.setAdapter(null);
        }
    }

    private void showCloseDialogAlert(String msg) {
        AlertDialog alertDialog = new AlertDialog.Builder(CardListActivity.this).create();
        alertDialog.setMessage(msg);
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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        // super.onWindowFocusChanged(hasFocus);
        if (customerPortfilioResp != null && customerPortfilioResp.getCustomerProtfolio().size() > 0) {
            int contentHeight = recyclerView.getChildAt(0).getHeight();

            // set listview height
            android.view.ViewGroup.LayoutParams lp = recyclerView.getLayoutParams();
            lp.height = contentHeight * customerPortfilioResp.getCustomerProtfolio().size();
            recyclerView.setLayoutParams(lp);
        } else {
            titletextIs.setVisibility(View.GONE);
            android.view.ViewGroup.LayoutParams lp = recyclerView.getLayoutParams();
            lp.height = 0;
            recyclerView.setLayoutParams(lp);
        }
    }

    @Override
    protected void onResume() {

        if (customerPortfilioResp != null) {
            mAdapter = new RecyclerViewAdapter(this, customerPortfilioResp);
            recyclerView.setAdapter(mAdapter);
        }
        super.onResume();
    }

    LoadWebServiceAsync callApi;

    private void callCardPortfilioService() {

        if (!connectionManager.isConnected()) {
            Utils.showDialogAlert(getResources().getString(R.string.en_dialogCannotConnectText), CardListActivity.this);

        } else {
            String value = null;
            dialog = ProgressDialog.show(CardListActivity.this,
                    "Please wait...", "Loading...");
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
                    Utils.getUserName(), atmfinderappcontext.sessionToken);

            // LoadWebServiceAsync callApi = new
            // LoadWebServiceAsync(getApplicationContext(), LoginActivity.this,
            // value, Constant.HOSTNAME_LINK, Constant.FORGET_METHOD_NAME,
            // Constant.FORGET_SOAP_ACTION,Constant.FORGET_NAMESPACE);

            callApi.execute();

            dialog.show();
        }
    }

    public void onIbtnAddCardClicked(View view) {
        Utils.startActivity(CardListActivity.this, com.allpoint.activities.CardCheckActivity.class, false, false, false);
    }

    public void onIbtnCancelCardListClicked(View view) {
        startActivity(new Intent(CardListActivity.this, com.allpoint.activities.phone.MainMenuActivity.class));
        finish();

    }

    @Override
    public void afterOnclick(int position) {
        Intent cardHistoryIntent = new Intent(CardListActivity.this, HistoryActivity.class);
        cardHistoryIntent.putExtra("selected_card", customerPortfilioResp.getCustomerProtfolio().get(position));
        startActivity(cardHistoryIntent);
    }

    @Override
    public void onResult(String result) {

        if (Utils.getLoginStatus()) {

            RespSessionInvalid mRespForInvalidSession = parseXml.parseXMLforSessionInvalid(result);
            // mRespForInvalidSession = parseXMLforSessionInvalid(result);

            // if session is Invalid
            if (mRespForInvalidSession != null && mRespForInvalidSession.getSessionInvalidStatusCode().trim().equals(Constant.SESSION_ERROR_CODE)) {
                if (dialog != null) {
                    dialog.dismiss();
                }

                // show message
                // showSessionInvalid(mRespForInvalidSession.getSessionInvalidStatusMessage());
                showSessionInvalid(getResources().getString(R.string.msg_sessionInvalid));

            } else {

                ResponseCustomerPortfilio mResult = parseXMLCustomerPortfilio(result);

                if (mResult != null && !mResult.getCustomerProtfolio().isEmpty() && mResult.getCustomerPortfilioStatus()) {
                    if (dialog != null) {
                        dialog.dismiss();
                    }

                    mAdapter = new RecyclerViewAdapter(this, mResult);
                    ((RecyclerViewAdapter) mAdapter).setMode(Attributes.Mode.Single);
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
                    Utils.showDialogAlert(getResources().getString(R.string.en_dialogCannotConnectText), CardListActivity.this);

                }
            }
        }

    }

    private ResponseCustomerPortfilio parseXMLCustomerPortfilio(String resp) {

        try {
            // is = getResources().openRawResource(R.raw.resp);
            InputStream is = new ByteArrayInputStream(resp.getBytes());
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
        Utils.showDialogAlert(getResources().getString(R.string.en_dialogCannotConnectText), CardListActivity.this);

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

        AlertDialog alertDialog = new AlertDialog.Builder(CardListActivity.this).create();
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

}
