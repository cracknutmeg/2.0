/**
 * @ DetailsActionBarFragment
 */
package com.allpoint.activities.phone.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.allpoint.R;
import com.allpoint.util.Constant;
import com.allpoint.util.Localization;
import com.allpoint.util.Utils;
import com.google.android.gms.maps.model.LatLng;

/**
 * DetailsActionBarFragment
 *
 * @author: Mikhail.Shalagin & Vyacheslav.Shmakin
 * @version: 23.08.13
 */
public class DetailsActionBarFragment extends Fragment {

    public DetailsActionBarFragment getInstance() {

        return new DetailsActionBarFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.details_action_bar_fragment, container, false);
        ImageButton directionButton = view.findViewById(R.id.directionButton);


        directionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onDirectionButtonClicked();
            }
        });

        return view;

    }

    public void onDirectionButtonClicked() {

        LatLng currentPosition = Utils.getMyLocation();

        if (currentPosition == null || Utils.getPointPosition() == null) {

            AlertDialog.Builder builder = new AlertDialog.Builder(
                    this.getActivity());
            builder.setMessage(Localization.getDialogCannotGetPosition())
                    .setPositiveButton(Localization.getDialogOk(), null)
                    .create().show();
            return;
        }

        String url = "http://maps.google.com/maps?saddr="
                + String.valueOf(currentPosition.latitude) + ","
                + String.valueOf(currentPosition.longitude) + "&daddr="
                + String.valueOf(Utils.getPointPosition().latitude) + ","
                + String.valueOf(Utils.getPointPosition().longitude);

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

        Utils.startFlurry(this.getActivity(), Constant.DIRECTIONS_ACTIVITY_EVENT);

        startActivity(intent);

    }
}
