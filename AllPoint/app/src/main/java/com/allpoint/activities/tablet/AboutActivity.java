/**
 * @ AboutActivity
 */
package com.allpoint.activities.tablet;

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
import com.allpoint.activities.tablet.fragments.SettingsFragment;
import com.allpoint.services.InternetConnectionManager;
import com.allpoint.util.Constant;
import com.allpoint.util.Localization;
import com.allpoint.util.Settings;
import com.allpoint.util.Utils;
import com.bugsense.trace.BugSenseHandler;

/**
 * AboutActivity
 *
 * @author: Vyacheslav.Shmakin
 * @version: 08.01.14
 */
public class AboutActivity extends FragmentActivity implements SettingsFragment.SettingsChangeListener {

    InternetConnectionManager connectionManager;
    WebView webView;
    ImageButton moreButton;
    TextView aboutButtonText;
    TextView aboutTitle;

    TextView itxtBottomTransaction;
    ImageButton btnTransaction;
    private ProgressBar progressBarWeb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        connectionManager = InternetConnectionManager.getInstance(this);

        initGUIElements();
        afterViews();
    }

    private void initGUIElements() {

        webView = findViewById(R.id.about_web_view);
        moreButton = findViewById(R.id.iBtnBottomMore);
        aboutButtonText = findViewById(R.id.iTxtBottomMore);
        aboutTitle = findViewById(R.id.tvAboutTitle);
        itxtBottomTransaction = findViewById(R.id.iTxtBottomTransaction);
        btnTransaction = findViewById(R.id.iBtnBottomTransaction);

        progressBarWeb = findViewById(R.id.progressBarWeb);
    }

    void afterViews() {
        BugSenseHandler.initAndStartSession(this, Constant.BUG_SENSE_API_KEY);


        if (Constant.HISTORY_BUTTON_DISABLE) {
            btnTransaction.setImageResource(R.drawable.bottom_about_press);
            itxtBottomTransaction.setTextColor(getResources().getColor(R.color.textColor));
            btnTransaction.setEnabled(true);

        } else {
            moreButton.setImageResource(R.drawable.bottom_more_press);
            moreButton.setEnabled(true);
            aboutButtonText
                    .setTextColor(getResources().getColor(R.color.textColor));
        }


       /* progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(Localization.getDialogLoadingTitle());
        progressDialog.setMessage(Localization.getDialogPleaseWait());
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);*/
        WebViewClientImpl webViewClient = new WebViewClientImpl();
        webView.setWebViewClient(webViewClient);
        progressBarWeb.setVisibility(View.VISIBLE);

    }

    @Override
    protected void onStart() {
        super.onStart();

        Utils.startFlurry(this, Constant.ABOUT_ACTIVITY_EVENT);
        Settings.setCurrentActivity(Settings.CurrentActivity.AboutActivity);

        if (!connectionManager.isConnected()) {
            AlertDialogFragment alertDialog = new AlertDialogFragment(
                    Localization.getDialogCannotConnectTitle(),
                    Localization.getDialogCannotConnectText(),
                    Localization.getDialogOk());
            alertDialog.show(getFragmentManager(), Constant.ERROR_DIALOG_TAG);
            return;
        }
        webView.loadUrl(Constant.ALLPOINT_ABOUT_URL);
    }

    @Override
    protected void onResume() {
        super.onResume();
        aboutTitle.setText(Localization.getAboutLayoutTitle());
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onSettingsChanged() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSettingsShowed() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSettingsDismissed() {
        Settings.SaveSettings();

    }


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
