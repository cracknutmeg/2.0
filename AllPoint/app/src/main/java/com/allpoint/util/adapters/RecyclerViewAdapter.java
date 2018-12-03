package com.allpoint.util.adapters;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allpoint.AtmFinderApplication;
import com.allpoint.R;
import com.allpoint.services.InternetConnectionManager;
import com.allpoint.services.LoadWebServiceAsync;
import com.allpoint.services.RespSessionInvalid;
import com.allpoint.services.ResponseCardDelete;
import com.allpoint.services.ResponseCustomerPortfilio;
import com.allpoint.services.ResponseCustomerProtfolioDetails;
import com.allpoint.services.ResponseHandler;
import com.allpoint.services.ResponseHandlerForTransactions;
import com.allpoint.services.WebServiceListner;
import com.allpoint.util.Constant;
import com.allpoint.util.Utils;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import static com.urbanairship.UAirship.getApplicationContext;

public class RecyclerViewAdapter extends
        RecyclerSwipeAdapter<RecyclerViewAdapter.SimpleViewHolder> implements
        WebServiceListner {

    DecimalFormat dec = new DecimalFormat("0.00");
    InternetConnectionManager connectionManager;

    private int positionOfItem;

    InputStream is;
    private ProgressDialog dialog;

    AtmFinderApplication atmfinderappcontext;

    RespSessionInvalid mRespForInvalidSession;

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {

        SwipeLayout swipeLayout;
        TextView textViewPos;
        TextView textViewData;
        Button buttonDelete;
        RelativeLayout mLinearRow;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            swipeLayout = itemView.findViewById(R.id.swipe);
            textViewPos = itemView.findViewById(R.id.position);
            textViewData = itemView.findViewById(R.id.text_data);
            buttonDelete = itemView.findViewById(R.id.delete);
            mLinearRow = itemView.findViewById(R.id.row_list);

        }
    }

    private Context mContext;
    private ResponseCustomerPortfilio mDataset;

    // protected SwipeItemRecyclerMangerImpl mItemManger = new
    // SwipeItemRecyclerMangerImpl(this);

    public RecyclerViewAdapter(Context context,
                               ResponseCustomerPortfilio objects) {
        this.mContext = context;
        this.mDataset = objects;

        atmfinderappcontext = (AtmFinderApplication) context
                .getApplicationContext();
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.cardrow_item, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder,
                                 final int position) {
        ResponseCustomerProtfolioDetails item = mDataset.getCustomerProtfolio()
                .get(position);
        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);

        viewHolder.mLinearRow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                OnItemClickListner mClick = null;
                if (Utils.isTablet()) {
                    if (mContext instanceof com.allpoint.activities.tablet.CardListActivity) {
                        mClick = (OnItemClickListner) mContext;
                        mClick.afterOnclick(position);

                    }
                } else {
                    if (mContext instanceof com.allpoint.activities.phone.CardListActivity) {
                        mClick = (OnItemClickListner) mContext;
                        mClick.afterOnclick(position);

                    }
                }

            }
        });

        viewHolder.buttonDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                OnItemClickListner mDialog = null;
                /*connectionManager = InternetConnectionManager_
                        .getInstance_(mContext);*/

                //ToDO need re write the code here

                InternetConnectionManager connectionManager = InternetConnectionManager.getInstance(getApplicationContext());

                //connectionManager = new InternetConnectionManager();

                if (!connectionManager.isConnected()) {

                    if (mContext instanceof com.allpoint.activities.tablet.CardListActivity) {
                        mDialog = (OnItemClickListner) mContext;
                        mDialog.showDialogForNetwork();

                    } else {
                        if (mContext instanceof com.allpoint.activities.phone.CardListActivity) {
                            mDialog = (OnItemClickListner) mContext;
                            mDialog.showDialogForNetwork();
                        }
                    }

                } else {
                    mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                    positionOfItem = position;
                    callDeleteCardWebservice(mDataset.getCustomerProtfolio()
                            .get(position).getCustomerPan());
                }
            }
        });

        viewHolder.textViewPos.setText(item.getCustomerPan());

        Double in = Double.parseDouble(item.getCustomerSavedMoney());
        viewHolder.textViewData.setText("Savings: " + "$" + dec.format(in));
        //Todo need to check
        //mItemManger.bind(viewHolder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return mDataset.getCustomerProtfolio().size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    public interface OnItemClickListner {
        void afterOnclick(int position);

        void showDialog(String msg);

        void showDialogForNetwork();

        void updateList(ResponseCustomerPortfilio mDataset);
    }

    LoadWebServiceAsync callApi;

    // Delete Card URL
    private void callDeleteCardWebservice(String panNum) {

        // if (!connectionManager.isConnected()) {
        // //Utils.showDialogAlert(getResources().getString(R.string.en_dialogCannotConnectText),this);
        //
        // }
        // else {
        String value = null;

        value = "<DeleteCardDetails>" + "<DeleteCardDetail>" + "<EmailId>"
                + Utils.getUserName().trim() + "</EmailId>" + "<PANNumber>" + panNum
                + "</PANNumber>"
                //+ "<ApplicationId>"	+ Constant.ALLPOINT_SERVER_APP_ID + "</ApplicationId>"
                + "</DeleteCardDetail>" + "</DeleteCardDetails>";

        callApi = new LoadWebServiceAsync(mContext.getApplicationContext(),
                this, value, Constant.CUSTOMER_MANAGEMENT_TRANS_URL,
                Constant.CARD_DELETE_METHOD_NAME,
                Constant.CARD_DELETE_SOAP_ACTION,
                Constant.CUSTOMER_MANAGEMENT_TRANS_NAMESPACE,
                Utils.getUserName().trim(), atmfinderappcontext.sessionToken);

        // LoadWebServiceAsync callApi = new
        // LoadWebServiceAsync(getApplicationContext(), LoginActivity.this,
        // value, Constant.HOSTNAME_LINK, Constant.FORGET_METHOD_NAME,
        // Constant.FORGET_SOAP_ACTION,Constant.FORGET_NAMESPACE);

        callApi.execute();
        dialog = ProgressDialog.show(mContext, "Please wait...", "Loading...");
        dialog.show();
    }

    // }

    private ResponseCardDelete parseXMLForDeleteCard(String resp) {
        ResponseCardDelete cardDeleteResp = null;
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

            cardDeleteResp = resp_Handler.getCardDeleteResp();

            is.close();

        } catch (Exception e) {
            //e.printStackTrace();
        }

        return cardDeleteResp;
    }

    @Override
    public void onResult(String result) {

        // mRespForInvalidSession = parseXml.parseXMLforSessionInvalid(result);
        mRespForInvalidSession = parseXMLforSessionInvalid(result);

        // if session is Invalid
        if (mRespForInvalidSession != null
                && mRespForInvalidSession.getSessionInvalidStatusCode()
                .equals(Constant.SESSION_ERROR_CODE)) {
            if (dialog != null) {
                dialog.dismiss();
            }

            // show message
            // showSessionInvalid(mRespForInvalidSession.getSessionInvalidStatusMessage());
            showSessionInvalid(mContext.getResources().getString(
                    R.string.msg_sessionInvalid));

        } else {

            ResponseCardDelete mResult = parseXMLForDeleteCard(result);
            OnItemClickListner mDialog = null;
            if (mResult != null && mResult.getCardDeleteStatus()) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                // TODO After True response

                mDataset.getCustomerProtfolio().remove(positionOfItem);
                notifyItemRemoved(positionOfItem);
                notifyItemRangeChanged(positionOfItem, mDataset
                        .getCustomerProtfolio().size());
                mItemManger.closeAllItems();
                if (mContext instanceof com.allpoint.activities.tablet.CardListActivity) {
                    mDialog = (OnItemClickListner) mContext;
                    mDialog.updateList(mDataset);

                }
                // Toast.makeText(view.getContext(), "Deleted " +
                // viewHolder.textViewData.getText().toString() + "!",
                // Toast.LENGTH_SHORT).show();

            } else if (mResult != null
                    && mResult.getCardDeleteStatusCode().equals(
                    Constant.SESSION_ERROR_CODE)) { // check error code
                // and session

                // go to Login if session invalid
                Utils.startActivity(mContext,
                        com.allpoint.activities.LoginActivity.class, false,
                        false, true);

            } else {
                if (dialog != null) {
                    dialog.dismiss();
                }
                // TODO After False response

                if (Utils.isTablet()) {

                    if (mResult.getCardDeleteStatusMessage() != null) {

                        if (mContext instanceof com.allpoint.activities.tablet.CardListActivity) {
                            mDialog = (OnItemClickListner) mContext;
                            mDialog.showDialog(mResult
                                    .getCardDeleteStatusMessage());

                        } else {
                            if (mContext instanceof com.allpoint.activities.phone.CardListActivity) {
                                mDialog = (OnItemClickListner) mContext;
                                mDialog.showDialog(mResult
                                        .getCardDeleteStatusMessage());
                            }
                        }
                    } else {
                        mDialog = (OnItemClickListner) mContext;
                        mDialog.showDialog("Server Error");
                    }
                }

                // Toast.makeText(view.getContext(), "Deleted " +
                // viewHolder.textViewData.getText().toString() + "!",
                // Toast.LENGTH_SHORT).show();

            }

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

    public RespSessionInvalid parseXMLforSessionInvalid(String resp) {

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

            mRespForInvalidSession = resp_Handler.getSessionInvalidresp();

            is.close();

        } catch (Exception e) {
            //e.printStackTrace();
        }

        return mRespForInvalidSession;
    }

    public void showSessionInvalid(String message) {

        AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setMessage(message);
        alertDialog.setCancelable(true);
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Utils.setLoginStatus(false);
                        Utils.setUsername("");
                        Utils.startActivity(mContext,
                                com.allpoint.activities.LoginActivity.class,
                                false, false, true);

                    }
                });
        alertDialog.show();
    }
}
