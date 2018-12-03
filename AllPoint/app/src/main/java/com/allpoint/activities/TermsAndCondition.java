/**
 * @ AboutActivity
 */
package com.allpoint.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
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
public class TermsAndCondition extends FragmentActivity {
    ConnectivityManager connectivityManager;

    WebView webView;

    TextView aboutTitle;

    ImageButton backButton;
    private ProgressBar progressBarWeb;

    void afterViews() {
        BugSenseHandler.initAndStartSession(this, Constant.BUG_SENSE_API_KEY);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_terms_condition);

        webView = findViewById(R.id.terms_condition_web_view);
        aboutTitle = findViewById(R.id.tvAboutTitle);
        backButton = findViewById(R.id.iBtnTermsBack);
        progressBarWeb = findViewById(R.id.progressBarWeb);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onIbtnAboutBackClicked();

            }
        });

        afterViews();
    }

    @Override
    protected void onStart() {
        super.onStart();

        Utils.startFlurry(this, Constant.ABOUT_ACTIVITY_EVENT);

        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (!(connectivityManager.getActiveNetworkInfo() != null && connectivityManager
                .getActiveNetworkInfo().isConnected())) {
            AlertDialogFragment alertDialog = new AlertDialogFragment(
                    Localization.getDialogCannotConnectTitle(),
                    Localization.getDialogCannotConnectText());
            alertDialog.show(getFragmentManager(), Constant.ERROR_DIALOG_TAG);
        } else {
            webView.setWebViewClient(new MyWebViewClient());
            openURL();
        }

    }

    private void openURL() {

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);

        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setSupportZoom(true);
        // webView.getSettings().setDomStorageEnabled(true);
        // webView.getSettings().setLoadWithOverviewMode(true);
        // webView.getSettings().setUseWideViewPort(true);
        //webView.setInitialScale(100);

        webView.loadUrl(Constant.ALLPOINT_TERMS_CONDTION_URL);
        webView.requestFocus();
    }

    void onIbtnAboutBackClicked() {
        onBackPressed();
    }

    private class MyWebViewClient extends WebViewClient {
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
    }

}
