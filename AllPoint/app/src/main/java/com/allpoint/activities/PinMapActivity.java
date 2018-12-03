/**
 * @ PointDetailsActivity
 */
package com.allpoint.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.allpoint.AtmFinderApplication;
import com.allpoint.R;
import com.allpoint.model.PointRecord;
import com.allpoint.util.Constant;
import com.allpoint.util.Localization;
import com.allpoint.util.Utils;
import com.bugsense.trace.BugSenseHandler;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Marker;

/**
 * PinMapActivity
 *
 * @author: Vyacheslav.Shmakin
 * @version: 08.01.14
 */
public class PinMapActivity extends FragmentActivity implements GoogleMap.OnMarkerClickListener, OnMapReadyCallback {

    SupportMapFragment mapFragment;
    TextView pinViewTitle;
    private ImageButton iBtnPinBack;
    private PointRecord record;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lonely_pin_map);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapPin);
        pinViewTitle = findViewById(R.id.tvPinViewTitle);
        iBtnPinBack = findViewById(R.id.iBtnPinBack);

        iBtnPinBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onIbtnPinBackClicked();
            }
        });
        pinViewTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onTvPinViewTitleClick();
            }
        });

        record = Utils.getRecord();

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapPin);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        afterViews();
    }

    void afterViews() {
        BugSenseHandler.initAndStartSession(AtmFinderApplication.getContext(), Constant.BUG_SENSE_API_KEY);


    }

    @Override
    public void onStart() {
        super.onStart();
        Utils.startFlurry(this, Constant.PIN_VIEW_ACTIVITY_EVENT);
    }

    @Override
    public void onResume() {
        super.onResume();
        pinViewTitle.setText(Localization.getPinViewLayoutTitle());
    }

    void onIbtnPinBackClicked() {
        onBackPressed();
    }


    void onTvPinViewTitleClick() {
        onBackPressed();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(record.getPosition(), Constant.DEFAULT_MAP_ZOOM_VALUE, 0, 0)));

        googleMap.addMarker(record.toMarkerOptions().snippet(null)
                .title(record.getAddressLine() + " "
                        + record.getCity() + ", "
                        + record.getState() + " "
                        + record.getPostalCode())).showInfoWindow();
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        googleMap.getUiSettings().setCompassEnabled(false);
        googleMap.getUiSettings().setRotateGesturesEnabled(false);
        googleMap.getUiSettings().setZoomControlsEnabled(false);
        googleMap.getUiSettings().setTiltGesturesEnabled(false);
        googleMap.setOnMarkerClickListener(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);
    }
}
