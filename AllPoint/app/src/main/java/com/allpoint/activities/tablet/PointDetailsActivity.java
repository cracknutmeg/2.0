/**
 * @ PointDetailsActivity
 */
package com.allpoint.activities.tablet;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.allpoint.R;
import com.allpoint.activities.fragments.HoursFragment;
import com.allpoint.activities.fragments.ShareFragment;
import com.allpoint.activities.tablet.fragments.SettingsFragment;
import com.allpoint.model.PointRecord;
import com.allpoint.util.Constant;
import com.allpoint.util.IconManager;
import com.allpoint.util.Localization;
import com.allpoint.util.Settings;
import com.allpoint.util.Utils;
import com.allpoint.util.adapters.ServiceListAdapter;
import com.bugsense.trace.BugSenseHandler;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import androidx.annotation.Nullable;

/**
 * PointDetailsActivity
 *
 * @author: Vyacheslav.Shmakin
 * @version: 08.01.14
 */
public class PointDetailsActivity extends FragmentActivity implements
        GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener,
        ShareFragment.onShareChangeStateListener,
        HoursFragment.onHoursChangeStateListener,
        SettingsFragment.SettingsChangeListener {

    protected LayoutInflater shareInflater;
    protected LayoutInflater hoursInflater;
    protected IconManager iconManager;
    protected ViewGroup map;
    protected ListView lvServices;
    protected TextView tvPointName;
    protected TextView tvPointAddress;
    protected TextView tvPointDistance;
    protected TextView detailViewTitle;
    protected TextView detailViewServicesTitle;
    protected ImageView logoView;
    protected ImageButton bottomSearchButton;
    TextView tvSearch;
    ImageButton btnGetHours;
    ImageButton btnCall;
    ImageButton btnShare;
    protected SupportMapFragment mapFragment;

    private ShareFragment shareFragment;
    private HoursFragment hoursFragment;
    private String phoneNumber;

    private boolean isContainsNumeric(String str) {
        for (int i = 0; i < 10; i++) {
            if (str.contains(String.valueOf(i))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onCreate(@Nullable @android.support.annotation.Nullable Bundle savedInstanceState, @Nullable @android.support.annotation.Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.point_details);
        initGUIElements();

        afterViews();
    }

    private void initGUIElements() {

        tvPointName = findViewById(R.id.tvDetailsPointName);
        tvPointAddress = findViewById(R.id.tvDetailsPointAddress);
        tvPointDistance = findViewById(R.id.tvDetailsPointDistance);
        detailViewTitle = findViewById(R.id.tvDetailViewTitle);
        detailViewServicesTitle = findViewById(R.id.tvServicesTitle);
        logoView = findViewById(R.id.iViewRetailerLogo);
        bottomSearchButton = findViewById(R.id.iBtnBottomSearch);
        tvSearch = findViewById(R.id.iTxtBottomSearch);
        btnGetHours = findViewById(R.id.hoursButton);
        btnCall = findViewById(R.id.callButton);
        btnShare = findViewById(R.id.shareButton);
    }


    void afterViews() {
        BugSenseHandler.initAndStartSession(this, Constant.BUG_SENSE_API_KEY);

        bottomSearchButton.setImageResource(R.drawable.bottom_search_press);
        tvSearch.setTextColor(getResources().getColor(R.color.textColor));
        bottomSearchButton.setEnabled(false);

        PointRecord record = Utils.getRecord();

        Integer resourceId;
        try {
            resourceId = getResources().getIdentifier(record.getLogoName(), "drawable", getPackageName());

            // resourceId = iconManager.getIconByName(record.getLogoName());
        } catch (NullPointerException npe) {
            resourceId = null;
            npe.printStackTrace();
        }

        if (resourceId == null) {
            resourceId = R.drawable.retailer_allpoint;
        }
        logoView.setImageResource(resourceId);
        phoneNumber = record.getMobileValue();

        if (phoneNumber == null) {
            phoneNumber = "";
        } else if (!isContainsNumeric(phoneNumber)) {
            phoneNumber = "";
        }

        tvPointName.setText(record.getName());
        tvPointAddress.setText(record.getAddressLine() + " " + record.getCity()
                + ", " + record.getState() + " " + record.getPostalCode());
        tvPointDistance.setText(Utils.getDistanceString(record.getDistance()));

        ServiceListAdapter serviceAdapter = new ServiceListAdapter(this);
        serviceAdapter.addAll(record.getServices());
        lvServices.setAdapter(serviceAdapter);

        if (record.getHours() != null && record.getHours().size() == 0) {
            btnGetHours.setEnabled(false);
            btnGetHours.setImageResource(R.drawable.details_hours_disable);
        }

        if (phoneNumber.length() == 0) {
            btnCall.setEnabled(false);
            btnCall.setImageResource(R.drawable.details_call_disable);
        }
        // TODO need to give Map
        /*
        if (mapFragment.getMap() == null) {
            return;
        }
        mapFragment
                .getMap()
                .moveCamera(
                        CameraUpdateFactory
                                .newCameraPosition(new CameraPosition(record
                                        .getPosition(),
                                        Constant.DEFAULT_MAP_ZOOM_VALUE, 0, 0)));

        Utils.setPointPosition(record.getPosition());
        mapFragment.getMap().addMarker(record.toMarkerOptions());
        mapFragment.getMap().getUiSettings().setMyLocationButtonEnabled(false);
        mapFragment.getMap().getUiSettings().setCompassEnabled(false);
        mapFragment.getMap().getUiSettings().setAllGesturesEnabled(false);
        mapFragment.getMap().getUiSettings().setZoomControlsEnabled(false);
        mapFragment.getMap().setMyLocationEnabled(false);
        map.setEnabled(false);
        map.setActivated(false);
        map.setSelected(false);
        map.setPressed(false);
        map.setFocusable(false);
        map.setClickable(false);
        mapFragment.getMap().setOnMarkerClickListener(this);
        mapFragment.getMap().setOnMapClickListener(this);*/

        shareFragment = ShareFragment.getInstance();
        hoursFragment = HoursFragment.getInstance();

        if (shareFragment == null) {
            shareFragment = new ShareFragment(record);
        }

        if (hoursFragment == null) {
            hoursFragment = new HoursFragment(record);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        Utils.startFlurry(this, Constant.POINT_DETAILS_ACTIVITY_EVENT);
        Settings.setCurrentActivity(Settings.CurrentActivity.PointDetailsActivity);
    }

    @Override
    public void onResume() {
        super.onResume();
        detailViewTitle.setText(Localization.getDetailViewLayoutTitle());
        detailViewServicesTitle.setText(Localization
                .getDetailViewServicesTitle());
    }

    public void onDirectionButtonClicked(View view) {

        LatLng currentPosition = Utils.getMyLocation();

        if (currentPosition == null || Utils.getPointPosition() == null) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
        Utils.startFlurry(this, Constant.DIRECTIONS_ACTIVITY_EVENT);

        startActivity(intent);
    }

    public void onShareButtonClicked(View view) {
        shareFragment.show(getFragmentManager(), Constant.ERROR_DIALOG_TAG);

    }

    public void onHoursButtonClicked(View view) {
        hoursFragment.show(getFragmentManager(), Constant.ERROR_DIALOG_TAG);
    }

    public void onCallButtonClicked(View view) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(callIntent);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Utils.startActivity(this,
                com.allpoint.activities.PinMapActivity.class, true, false,
                false);
        return true;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        Utils.startActivity(this,
                com.allpoint.activities.PinMapActivity.class, true, false,
                false);
    }

    public void onIbtnDetailsBackClicked(View view) {
        onBackPressed();
    }


    void onTvDetailViewTitleClick() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        finish();
    }


    //Todo   it should be this =details_share_press

    @Override
    public void onShareShow() {
        btnShare.setImageResource(R.drawable.details_share);
    }

    @Override
    public void onShareDismiss() {
        btnShare.setImageResource(R.drawable.details_share);
    }

    //Todo   it should be this =details_share_press
    @Override
    public void onHoursShow() {
        btnGetHours.setImageResource(R.drawable.details_hours);
    }

    @Override
    public void onHoursDismiss() {
        btnGetHours.setImageResource(R.drawable.details_hours);
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
}
