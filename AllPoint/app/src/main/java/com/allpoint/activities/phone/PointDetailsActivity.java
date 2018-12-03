/**
 * @ PointDetailsActivity
 */
package com.allpoint.activities.phone;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.allpoint.R;
import com.allpoint.activities.fragments.HoursFragment;
import com.allpoint.activities.fragments.ShareFragment;
import com.allpoint.model.PointRecord;
import com.allpoint.util.Constant;
import com.allpoint.util.IconManager;
import com.allpoint.util.Localization;
import com.allpoint.util.Utils;
import com.bugsense.trace.BugSenseHandler;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

/**
 * PointDetailsActivity
 *
 * @author: Mikhail.Shalagin & Vyacheslav.Shmakin
 * @version: 23.09.13
 */
public class PointDetailsActivity extends FragmentActivity implements GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener,
        ShareFragment.onShareChangeStateListener, OnMapReadyCallback, HoursFragment.onHoursChangeStateListener {

    protected ViewGroup detailsWindow;
    protected ListView lvServices;
    protected TextView tvPointName;
    protected TextView tvPointAddress;
    protected TextView tvPointDistance;
    private ImageButton btnGetHours;
    private ImageButton btnCall;
    private ImageButton btnShare;

    // DetailView
    protected TextView detailViewTitle;
    protected TextView detailViewServicesTitle;
    protected ImageView logoView;

    protected View shareInflater;

    protected View hoursInflater;

    protected IconManager iconManager;
    private PointRecord record;
    private ImageButton iBtnDetailsBack;
    private TextView tvDetailViewTitle;

    private boolean isContainsNumeric(String str) {
        for (int i = 0; i < 10; i++) {
            if (str.contains(String.valueOf(i))) {
                return true;
            }
        }
        return false;
    }

    private ShareFragment shareFragment;
    private HoursFragment hoursFragment;
    private String phoneNumber;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.point_details);

        record = Utils.getRecord();
        Utils.setPointPosition(record.getPosition());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapDetails);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        initGUIElements();

        afterViews();

        hoursInflater = LayoutInflater.from(getApplicationContext()).inflate(R.layout.details_action_bar_fragment, null);
        shareInflater = LayoutInflater.from(getApplicationContext()).inflate(R.layout.share_fragment, null);


        iBtnDetailsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onIbtnDetailsBackClicked();
            }
        });


        tvDetailViewTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onTvDetailViewTitleClick();
            }
        });

    }

    private void initGUIElements() {


        tvDetailViewTitle = findViewById(R.id.tvDetailViewTitle);
        iBtnDetailsBack = findViewById(R.id.iBtnDetailsBack);
        detailsWindow = findViewById(R.id.details);
        lvServices = findViewById(R.id.listViewServices);
        tvPointName = findViewById(R.id.tvDetailsPointName);
        tvPointAddress = findViewById(R.id.tvDetailsPointAddress);
        tvPointDistance = findViewById(R.id.tvDetailsPointDistance);
        btnGetHours = findViewById(R.id.hoursButton);
        btnCall = findViewById(R.id.callButton);
        //TODo check this
        btnShare = findViewById(R.id.shareButton);
        detailViewTitle = findViewById(R.id.tvDetailViewTitle);
        detailViewServicesTitle = findViewById(R.id.tvServicesTitle);
        logoView = findViewById(R.id.iViewRetailerLogo);

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onShareButtonClicked();
            }
        });
    }

    public void afterViews() {

        iconManager = IconManager.getInstance(this);

        BugSenseHandler.initAndStartSession(this, Constant.BUG_SENSE_API_KEY);


        Integer resourceId;
        try {
            resourceId = iconManager.getIconByName(record.getLogoName());
        } catch (NullPointerException npe) {
            resourceId = null;
            //npe.printStackTrace();
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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.service_item, R.id.tvService, record.getServices());
        lvServices.setAdapter(adapter);

        if (record.getHours() != null && record.getHours().size() == 0) {
            btnGetHours.setEnabled(false);
            btnGetHours.setImageResource(R.drawable.details_hours_disable);
        }

        if (phoneNumber.length() == 0) {
            btnCall.setEnabled(false);
            btnCall.setImageResource(R.drawable.details_call_disable);
        }
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
    }

    @Override
    public void onResume() {
        super.onResume();
        detailViewTitle.setText(Localization.getDetailViewLayoutTitle());
        detailViewServicesTitle.setText(Localization
                .getDetailViewServicesTitle());
    }

    protected void onShareButtonClicked() {
        shareFragment.show(getFragmentManager(), Constant.ERROR_DIALOG_TAG);
    }

    //TODO check this
    //@Click(R.id.hoursButton)
    protected void onHoursButtonClicked() {
        hoursFragment.show(getFragmentManager(), Constant.ERROR_DIALOG_TAG);
    }

    //TODO check this
    // @Click(R.id.callButton)
    protected void onCallButtonClicked() {
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


    protected void onIbtnDetailsBackClicked() {
        onBackPressed();
    }


    public void onTvDetailViewTitleClick() {
        onBackPressed();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Utils.startActivity(this, com.allpoint.activities.PinMapActivity.class, true, false, false);
        return true;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        Utils.startActivity(this, com.allpoint.activities.PinMapActivity.class, true, false,
                false);
    }

    //TODO change icon to details_share_press
    @Override
    public void onShareShow() {
        btnShare.setImageResource(R.drawable.details_share);
    }

    @Override
    public void onShareDismiss() {
        btnShare.setImageResource(R.drawable.details_share);
    }


    //TODO change icon to details_hours_press
    @Override
    public void onHoursShow() {
        btnGetHours.setImageResource(R.drawable.details_hours);
    }

    @Override
    public void onHoursDismiss() {
        btnGetHours.setImageResource(R.drawable.details_hours);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMap.addMarker(record.toMarkerOptions());
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(record.getPosition(), Constant.DEFAULT_MAP_ZOOM_VALUE, 0, 0)));
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        googleMap.getUiSettings().setCompassEnabled(false);
        googleMap.getUiSettings().setAllGesturesEnabled(false);
        googleMap.getUiSettings().setZoomControlsEnabled(false);
        googleMap.setMyLocationEnabled(false);
        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnMapClickListener(this);

    }
}
