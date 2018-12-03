package com.allpoint.activities.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.allpoint.R;
import com.allpoint.model.PointRecord;
import com.allpoint.util.Constant;
import com.allpoint.util.Localization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * SettingsFragment
 *
 * @version: 08.01.14
 */
@SuppressLint("ValidFragment")
public class HoursFragment extends DialogFragment {

    private PointRecord record;
    private onHoursChangeStateListener listener;
    private static HoursFragment instance;

    public HoursFragment(final PointRecord record) {
        this.record = record;
        instance = this;
    }

    public interface onHoursChangeStateListener {
        void onHoursShow();

        void onHoursDismiss();
    }

    /**
     * @return Returns HoursFragment instance if it does exists
     */
    public static HoursFragment getInstance() {
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
        final View hoursLayout = inflater.inflate(R.layout.hours_fragment,
                container, false);
        getDialog().getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        ((TextView) hoursLayout.findViewById(R.id.tvHoursTitle))
                .setText(Localization.getDetailViewHoursTitle());

        ListView hoursList = (ListView) hoursLayout
                .findViewById(R.id.lvHoursValue);

        // Each row in the list stores country name, currency and flag
        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

        for (String hour : record.getHours()) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put(Constant.POINT_HOUR_VALUE, hour);
            aList.add(hm);
        }

        // Keys used in Hashmap
        String[] from = {Constant.POINT_HOUR_VALUE};

        // Ids of views in listview_layout
        int[] to = {R.id.tvHoursItem};

        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
        SimpleAdapter adapter = new SimpleAdapter(this.getActivity(), aList,
                R.layout.hour_item, from, to);
        // Setting the adapter to the listView
        hoursList.setAdapter(adapter);

        return hoursLayout;
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
    public void onAttach(final Activity activity) {
        super.onAttach(activity);

        final Fragment frg = getTargetFragment();
        if (frg != null && frg instanceof onHoursChangeStateListener) {
            listener = (onHoursChangeStateListener) frg;
        } else {
            if (activity != null
                    && activity instanceof onHoursChangeStateListener) {
                listener = (onHoursChangeStateListener) activity;
            }
        }

        if (listener != null) {
            listener.onHoursShow();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener.onHoursDismiss();
        listener = null;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        instance = null;
    }
}
