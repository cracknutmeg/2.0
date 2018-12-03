package com.allpoint.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.allpoint.R;
import com.allpoint.activities.fragments.AlertDialogFragment;
import com.allpoint.model.Filter;
import com.allpoint.model.SearchResult;
import com.allpoint.model.VersionInfo;
import com.allpoint.services.GeofenceService;
import com.allpoint.services.xmlParser.ErrorType;
import com.allpoint.services.xmlParser.TaskManager;
import com.allpoint.util.Constant;
import com.allpoint.util.Localization;
import com.allpoint.util.Settings;
import com.allpoint.util.Utils;
import com.bugsense.trace.BugSenseHandler;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;

/**
 * SplashActivity *
 *
 * @author: Vyacheslav.Shmakin
 * @version: 08.01.14
 */

public class SplashActivity extends Activity implements TaskManager.AsyncTaskListener {
    TaskManager taskManager;
    private AlertDialogFragment alertDialog = null;

    private void openUrlIntent(final String url, final boolean isFinishCurrent) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);

        if (isFinishCurrent) {
            finish();
        }
    }

    @Override
    public void onCreate(final Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.splash);

        //Added for Firebase Push messaging insteadof  GCM
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);

        startService(new Intent(this, GeofenceService.class));


        Utils.startFlurry(this, Constant.SPLASH_ACTIVITY_EVENT);
        Settings.LoadSettings();
        Utils.setSettingsLoaded(true);
        BugSenseHandler.initAndStartSession(this, Constant.BUG_SENSE_API_KEY);

        taskManager = TaskManager.getInstance(SplashActivity.this);
        taskManager.addTaskListener(this);
        taskManager.getVersionInfo(Constant.VERSION_INFO_TASK_ID, Constant.DEVICE_TYPE);

        alertDialog = null;



    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    @Override
    protected void onDestroy() {
        taskManager.removeTaskListener(this);
        super.onDestroy();
    }

    @Override
    public void onTaskStarted(final String taskId, final TaskManager.QueryId queryId) {
    }

    @Override
    public void onReceiveVersionInfo(final String taskId, final ErrorType error, final VersionInfo result) {

        switch (error) {
            case TASK_FINISHED:
                if (Utils.isPlayServicesAvailable(this)) {

                    final Class<?> activityClass;

                    if (Utils.isTablet()) {
                        activityClass = com.allpoint.activities.LoginActivity.class;
                    } else {
                        activityClass = com.allpoint.activities.LoginActivity.class;
                    }

                    final String url = result.getGooglePlayUrl();

                    if (null != url && !url.isEmpty()) {
                        Utils.setAppUrl(url);
                    }

                    if (result.getVersionId() > Constant.APP_VERSION_ID) {
                        if (result.isForceUpdate()) {
                            alertDialog = new AlertDialogFragment(
                                    Localization.getDialogForceUpdate(),
                                    Localization.getDialogOk(),
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            openUrlIntent(result.getGooglePlayUrl(), true);

                                        }
                                    });
                        } else {
                            alertDialog = new AlertDialogFragment(
                                    Localization.getDialogUpdate(),
                                    Localization.getDialogOk(),
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which) {
                                            openUrlIntent(result.getGooglePlayUrl(), true);
                                        }
                                    }, Localization.getDialogCancel(),
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            Utils.setFromSplash(true);
                                            Utils.startActivity(SplashActivity.this, activityClass, false, false, true);
                                        }
                                    });
                        }
                    } else {
                        Utils.setFromSplash(true);
                        Utils.startActivity(SplashActivity.this, activityClass,
                                false, false, true);
                    }
                } else {
                    alertDialog = new AlertDialogFragment(
                            Localization.getDialogPlayServicesUnavailable(),
                            Localization.getDialogOk());
                }
                break;
            case CONNECTION_ERROR:
                alertDialog = new AlertDialogFragment(
                        Localization.getDialogCannotConnectTitle(),
                        Localization.getDialogCannotConnectText(),
                        Localization.getDialogOk());
                break;
            case SERVICE_UNAVAILABLE:
                alertDialog = new AlertDialogFragment(
                        Localization.getDialogServiceUnavailable(),
                        Localization.getDialogOk());
                break;
            case NO_VERSION_INFO_FOUND:
                alertDialog = new AlertDialogFragment(
                        Localization.getDialogParsingError(),
                        Localization.getDialogOk());
                break;
            case XML_PARSER_ERROR:
                alertDialog = new AlertDialogFragment(
                        Localization.getDialogParsingError(),
                        Localization.getDialogOk());
                break;
            case TASK_STOPPED:
                break;
            case NO_FILTERS_FOUND:
                break;
            case NO_RESULTS_FOUND:
                break;
            default:
                break;
        }

        if (alertDialog != null) {
            if (error == ErrorType.CONNECTION_ERROR) {
                showDialogAlert(Localization.getDialogCannotConnectText());
            } else if (error == ErrorType.SERVICE_UNAVAILABLE) {
                showDialogAlert(Localization.getDialogServiceUnavailable());
            } else {
                alertDialog.show(getFragmentManager(),
                        Constant.ERROR_DIALOG_TAG);
            }

        }

    }

    @Override
    public void onReceiveFilters(final String taskId, final ErrorType error,
                                 final List<Filter> result) {
    }

    @Override
    public void onSearchFinished(final String taskId, final ErrorType error,
                                 final SearchResult result) {
    }

    public void showDialogAlert(String message) {

        AlertDialog alertDialog = new AlertDialog.Builder(SplashActivity.this)
                .create();
        alertDialog.setMessage(message);

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Utils.startActivity(SplashActivity.this,
                                com.allpoint.activities.LoginActivity.class,
                                false, false, true);
                    }
                });
        alertDialog.show();
    }
}
