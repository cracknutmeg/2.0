/**
 * @ AboutActivity
 */
package com.allpoint.activities.phone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.allpoint.R;
import com.allpoint.activities.fragments.AlertDialogFragment;
import com.allpoint.util.Constant;
import com.allpoint.util.Localization;
import com.allpoint.util.Utils;
import com.bugsense.trace.BugSenseHandler;

/**
 * AboutActivity
 *
 * @author: Vyacheslav.Shmakin
 * @version: 23.09.13
 */
public class AboutActivity extends FragmentActivity {

    ConnectivityManager connectivityManager;
    WebView webView;
    ImageButton moreButton;
    TextView moreButtonText;
    TextView aboutTitle;
    TextView itxtBottomTransaction;
    ImageButton btnTransaction;
    private ProgressBar progressBarWeb;


    /*
     * @ViewById(R.id.iBtnAboutBack) ImageButton backButton;
     */

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        Utils.startFlurry(this, Constant.ABOUT_ACTIVITY_EVENT);

        initGUIElements();
        afterViews();

        if (!(connectivityManager.getActiveNetworkInfo() != null && connectivityManager

                .getActiveNetworkInfo().isConnected())) {
            AlertDialogFragment alertDialog = new AlertDialogFragment(
                    Localization.getDialogCannotConnectTitle(),
                    Localization.getDialogCannotConnectText());
            alertDialog.show(getFragmentManager(), Constant.ERROR_DIALOG_TAG);
        }

        webView.getSettings().setJavaScriptEnabled(true);

        WebViewClientImpl webViewClient = new WebViewClientImpl();
        webView.setWebViewClient(webViewClient);
        progressBarWeb.setVisibility(View.VISIBLE);
        webView.loadUrl(Constant.ALLPOINT_ABOUT_URL);
        //webView.loadUrl("file:///android_asset/allpoint_about_us.html");

    }

    private void initGUIElements() {

        webView = findViewById(R.id.about_web_view);
        progressBarWeb = findViewById(R.id.progressBarWeb);
        moreButton = findViewById(R.id.iBtnBottomMore);
        moreButtonText = findViewById(R.id.iTxtBottomMore);
        aboutTitle = findViewById(R.id.tvAboutTitle);
        itxtBottomTransaction = findViewById(R.id.iTxtBottomTransaction);
        btnTransaction = findViewById(R.id.iBtnBottomTransaction);


    }


    void afterViews() {

        BugSenseHandler.initAndStartSession(this, Constant.BUG_SENSE_API_KEY);


        if (Constant.HISTORY_BUTTON_DISABLE) {
            btnTransaction.setImageResource(R.drawable.bottom_about_press);
            itxtBottomTransaction.setTextColor(getResources().getColor(R.color.textColor));
            btnTransaction.setEnabled(true);

        } else {
            moreButton.setImageResource(R.drawable.bottom_more_press);
            moreButtonText.setTextColor(getResources().getColor(R.color.textColor));
            // aboutButton.setBackgroundResource(R.drawable.bottom_press_button_bg);
            moreButton.setEnabled(true);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        aboutTitle.setText(Localization.getAboutLayoutTitle());
    }

    /*
     * @Click(R.id.iBtnAboutBack) void onIbtnAboutBackClicked() {
     * onBackPressed(); }
     *
     * @Click(R.id.tvAboutTitle) void onTvAboutTitleClick() { onBackPressed(); }
     */


    public class WebViewClientImpl extends WebViewClient {


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBarWeb.setVisibility(View.GONE);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {

            if (error.getErrorCode() == -1) {
                progressBarWeb.setVisibility(View.VISIBLE);
                view.loadUrl("file:///android_asset/allpoint_about_us.html");
            }

            super.onReceivedError(view, request, error);
            //Log.e("WebErrorCode", String.valueOf(error.getErrorCode()));

        }
    }
}
