package com.allpoint.activities.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.allpoint.R;
import com.allpoint.model.PointRecord;
import com.allpoint.util.Constant;
import com.allpoint.util.Localization;
import com.allpoint.util.ShareApp;
import com.allpoint.util.Utils;

/**
 * SettingsFragment
 *
 * @version: 08.01.14
 */
@SuppressLint("ValidFragment")
public class ShareFragment extends DialogFragment implements
        View.OnClickListener {

    private String messageToShare;
    private onShareChangeStateListener listener;

    private static ShareFragment instance;

    public interface onShareChangeStateListener {
        void onShareShow();

        void onShareDismiss();
    }

    public ShareFragment(final PointRecord record) {
        messageToShare = record.getName() + "\r\n" + record.getAddressLine()
                + " " + record.getCity() + ", " + record.getState() + " "
                + record.getPostalCode();
        instance = this;
    }

    /**
     * @return Returns ShareFragment instance if it does exists
     */
    public static ShareFragment getInstance() {
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             final ViewGroup container, final Bundle savedInstanceState) {
        final View shareLayout = inflater.inflate(R.layout.share_fragment,
                container, false);

        getDialog().getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        ((TextView) shareLayout.findViewById(R.id.tvShareVia))
                .setText(Localization.getShareViaTitle());
        ((TextView) shareLayout.findViewById(R.id.tvShareEmail))
                .setText(Localization.getShareEmailTitle());
        ((TextView) shareLayout.findViewById(R.id.tvShareSms))
                .setText(Localization.getShareMessagingTitle());

        shareLayout.findViewById(R.id.shareEmailImageButton)
                .setOnClickListener(this);
        shareLayout.findViewById(R.id.sharebySmsImageButton)
                .setOnClickListener(this);

        Utils.startFlurry(this.getActivity(), Constant.SHARE_ACTIVITY_EVENT);

        return shareLayout;
    }

    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);
        final Fragment frg = getTargetFragment();
        if (frg != null && frg instanceof onShareChangeStateListener) {
            listener = (onShareChangeStateListener) frg;
        } else {
            if (activity != null
                    && activity instanceof onShareChangeStateListener) {
                listener = (onShareChangeStateListener) activity;
            }
        }
        if (listener != null) {
            listener.onShareShow();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener.onShareDismiss();
        listener = null;
    }

    @Override
    public void onDestroyView() {
        // removes the dismiss message to avoid shown dialogs being dismissed.
        if (getDialog() != null && getRetainInstance()) {
            getDialog().setDismissMessage(null);
        }
        super.onDestroyView();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        instance = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shareEmailImageButton:
                ShareApp.byEmail(v.getContext(), Constant.SHARE_MAIL_TO,
                        Constant.SHARE_SUBJECT, messageToShare);
                Utils.startFlurry(v.getContext(), Constant.SHARE_EMAIL_EVENT);
                break;
            case R.id.sharebySmsImageButton:
                try {
                    ShareApp.bySMS(v.getContext(), messageToShare);
                    Utils.startFlurry(v.getContext(), Constant.SHARE_SMS_EVENT);
                } catch (ActivityNotFoundException e) {
                    AlertDialogFragment alertDialog = new AlertDialogFragment(
                            Localization.getDialogSmsNotSupported(),
                            Localization.getDialogOk());
                    alertDialog.show(getFragmentManager(),
                            Constant.ERROR_DIALOG_TAG);
                    //e.printStackTrace();
                }
                break;
        }
        dismiss();
    }
}
