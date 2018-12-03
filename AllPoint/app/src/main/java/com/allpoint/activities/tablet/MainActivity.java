package com.allpoint.activities.tablet;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.allpoint.R;
import com.allpoint.activities.fragments.AlertDialogFragment;
import com.allpoint.activities.tablet.fragments.SettingsFragment;
import com.allpoint.model.Filter;
import com.allpoint.model.PointRecord;
import com.allpoint.model.SearchResult;
import com.allpoint.model.VersionInfo;
import com.allpoint.services.FilterManager;
import com.allpoint.services.xmlParser.ErrorType;
import com.allpoint.services.xmlParser.TaskManager;
import com.allpoint.util.Compute;
import com.allpoint.util.Constant;
import com.allpoint.util.Localization;
import com.allpoint.util.PermissionUtils;
import com.allpoint.util.Settings;
import com.allpoint.util.Utils;
import com.allpoint.util.adapters.FilterListAdapter;
import com.allpoint.util.adapters.ResultListAdapter;
import com.bugsense.trace.BugSenseHandler;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;
import com.urbanairship.PendingResult;
import com.urbanairship.ResultCallback;
import com.urbanairship.UAirship;
import com.urbanairship.location.LocationRequestOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.allpoint.AtmFinderApplication.getContext;


public class MainActivity extends FragmentActivity implements
        SearchView.OnQueryTextListener, GoogleMap.OnInfoWindowClickListener,
        OnMapReadyCallback, GoogleMap.OnMyLocationClickListener,
        GoogleMap.OnMarkerClickListener, GoogleMap.OnCameraMoveListener,
        AdapterView.OnItemClickListener, TaskManager.AsyncTaskListener,
        SettingsFragment.SettingsChangeListener, View.OnClickListener {
    private FilterManager filterManager;
    private TaskManager taskManager;
    private ImageButton bottomSearchButton;
    private ImageButton listsButton;
    private SearchView searchView;
    private TextView tvSearch;
    private ListView resultsListView;
    private ProgressBar loadingProgressBar;
    private GoogleMap mMap;
    public static Activity instance;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private ImageButton iBtnFilters;
    private PendingResult<Location> pendingResult;
    private String TAG = MainActivity.class.getSimpleName();

    private List<PointRecord> setSelectedItem(final List<PointRecord> records,
                                              final int position) {
        for (int i = 0; i < records.size(); i++) {
            if (i == position) {
                records.get(i).setSelected(true);
            } else {
                records.get(i).setSelected(false);
            }
        }
        return records;
    }

    private List<PointRecord> clearSelection(final List<PointRecord> records) {
        for (PointRecord record : records) {
            record.setSelected(false);
        }
        return records;
    }

    private ResultListAdapter resultListAdapter;

    private final Map<Long, Marker> markers = new ConcurrentHashMap<>();

    private AlertDialogFragment alertDialog;
    private SearchResult tempResult;
    // replace ua locaiton
    // private LocationClient locationClient;
    private ProgressDialog dialog;
    private Marker querySearchMarker;

    private boolean isQuerySearch = false;
    private boolean isSearchMyLocation = false;
    private boolean mPermissionDenied = false;
    private int positionInList = 0;
    private static final int DEFAULT_ZOOM = 15;
    private final LatLng mDefaultLocation = new LatLng(-33.8523341, 151.2106085);

    private final GoogleMap.CancelableCallback cancelableCallback = new GoogleMap.CancelableCallback() {
        @Override
        public void onFinish() {
            if (dialog != null) {
                dialog.dismiss();
            }
        }

        @Override
        public void onCancel() {
        }
    };

    private void openDetailsActivity(final List<PointRecord> records, final Marker marker) {

        for (PointRecord record : records) {

            if (Compute.isEqualsLatLng(record.getPosition(), marker.getPosition(), Constant.ROUND_VALUE)) {

                Utils.setRecord(record);
                Utils.startActivity(this, PointDetailsActivity.class, true, false, false);
                break;
            }
        }
    }

    private int getMarkerPositionFromList(final List<PointRecord> records,
                                          final Marker marker) {
        for (int i = 0; i < records.size(); i++) {

            if (Compute.isEqualsLatLng(records.get(i).getPosition(),
                    marker.getPosition(), Constant.ROUND_VALUE)) {
                if (records.get(i).getName().equals(marker.getTitle())) {
                    marker.setSnippet(Utils.getDistanceString(records.get(i)
                            .getDistance()));
                    Utils.setInfoWindowRecord(records.get(i));
                    return i;
                }
            }
        }
        return -1;
    }

    private void showFiltersPopup(final List<Filter> result) {
        // Sending message to Flurry
        Utils.startFlurry(this, Constant.SHOW_FILTERS_EVENT);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.filters_popup, null, false);
        final PopupWindow filtersWindow = new PopupWindow(layout, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);

        // Filters popup window data

        TextView filtersTitle = layout.findViewById(R.id.tvSelectFilterBy);
        filtersTitle.setText(Localization.getFiltersSelectByTitle());

        filtersWindow.setOutsideTouchable(true);
        filtersWindow.setBackgroundDrawable(new BitmapDrawable(null, (Bitmap) null));

        filtersWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);

        FilterListAdapter filterListAdapter = new FilterListAdapter(layout.getContext());

        ListView filtersListView = layout.findViewById(R.id.filtersListView);
        final List<Filter> temp = filterManager.getUpdatedFilterList(result);
        for (Filter filter : temp) {

            filterListAdapter.add(filter);
        }
        filtersListView.setAdapter(filterListAdapter);

        filtersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                CheckBox checkBox = view.findViewById(R.id.chBoxFilterName);
                if (checkBox != null) {
                    checkBox.setChecked(!checkBox.isChecked());
                    filterManager.setActivated(position, !temp.get(position).isActivated());
                }
            }
        });

        filtersWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                iBtnFilters.setImageResource(R.drawable.main_filter);
            }
        });
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        taskManager = TaskManager.getInstance(this);
        filterManager = FilterManager.getInstance();
        dialog = new ProgressDialog(MainActivity.this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapMain);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        initGUIElement();
        AfterViews();


        // Enable location updates
        UAirship.shared().getLocationManager().setLocationUpdatesEnabled(true);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            requestLocation();
        }


        new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.i("Location", "New user location " + location);

                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                LatLng latLng = new LatLng(latitude, longitude);
                Utils.setMyLocation(latLng);
            }
        };


    }

    private void initGUIElement() {
        ImageButton myPositionButton = findViewById(R.id.iBtnShowMyPosition);
        bottomSearchButton = findViewById(R.id.iBtnBottomSearch);
        tvSearch = findViewById(R.id.iTxtBottomSearch);
        listsButton = findViewById(R.id.iBtnShowResultList);
        searchView = findViewById(R.id.searchView);
        Button zoomInButton = findViewById(R.id.zoomInButton);
        Button zoomOutButton = findViewById(R.id.zoomOutButton);
        loadingProgressBar = findViewById(R.id.progressBarLoading);
        ImageButton iBtnShowResultList = findViewById(R.id.iBtnShowResultList);
        iBtnFilters = findViewById(R.id.iBtnFilters);
        iBtnShowResultList = findViewById(R.id.iBtnShowResultList);
        resultsListView = findViewById(R.id.resultListView);


        iBtnShowResultList.setOnClickListener(this);
        myPositionButton.setOnClickListener(this);
        zoomOutButton.setOnClickListener(this);
        zoomInButton.setOnClickListener(this);
    }

    public void AfterViews() {
        BugSenseHandler.initAndStartSession(this, Constant.BUG_SENSE_API_KEY);

        bottomSearchButton.setImageResource(R.drawable.bottom_search_press);
        tvSearch.setTextColor(getResources().getColor(R.color.textColor));
        bottomSearchButton.setEnabled(false);

        iBtnFilters.setEnabled(true);
        listsButton.setEnabled(true);
        boolean firstStart = true;
        instance = this;

        resultListAdapter = new ResultListAdapter(this);
        resultsListView.setAdapter(resultListAdapter);

        searchView.setOnQueryTextListener(this);
        searchView.setBackground(getResources().getDrawable(R.color.apGreen));
        resultsListView.setOnItemClickListener(this);
        taskManager.addTaskListener(this);
    }

    void onIbtnFiltersClick() {
        iBtnFilters.setImageResource(R.drawable.main_filter_press);
        taskManager.searchFilters(Constant.FILTERS_TASK_ID);
    }

    void onIbtnShowResultListClick() {
        if (resultsListView.getVisibility() == View.VISIBLE) {
            resultsListView.setVisibility(View.GONE);
            listsButton.setImageResource(R.drawable.main_list);
        } else {
            resultsListView.setVisibility(View.VISIBLE);
            listsButton.setImageResource(R.drawable.main_list_press);
        }
    }

    void onZoomInButtonClick() {
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
    }

    void onZoomOutButtonClick() {
        mMap.animateCamera(CameraUpdateFactory.zoomOut());
    }

    void onIbtnShowMyPositionClicked() {
        if (mMap != null) {
            requestLocation();
        } else {
            // If it is impossible to receive own position, then shown alert
            // message
            alertDialog = new AlertDialogFragment(Localization.getDialogCannotGetPosition(), Localization.getDialogOk());
            alertDialog.show(getFragmentManager(), Constant.ERROR_DIALOG_TAG);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Utils.startFlurry(this, Constant.MAP_ACTIVITY_EVENT);
        Settings.setCurrentActivity(Settings.CurrentActivity.MainActivity);

        // replace ua locaiton
        // Connect the client.
        // locationClient.connect();

        Utils.setOnMainActivity(true);

        if (Utils.getInfoWindowMarker() != null) {
            onMarkerClick(Utils.getInfoWindowMarker());
        }
    }

    @Override
    public void onTaskStarted(final String taskId,
                              final TaskManager.QueryId queryId) {
        if (taskId.equalsIgnoreCase(Constant.MAIN_ACTIVITY_QUERY_SEARCH)
                || taskId.equalsIgnoreCase(Constant.FILTERS_TASK_ID)) {
            dialog = ProgressDialog.show(this,
                    Localization.getDialogLoadingTitle(),
                    Localization.getDialogPleaseWait(), true, false);
        }
    }

    @Override
    public void onSearchFinished(final String taskId, final ErrorType error,
                                 final SearchResult result) {
        loadingProgressBar.setVisibility(View.GONE);
        switch (error) {
            case NO_RESULTS_FOUND:
                if (taskId.equals(Constant.MAIN_ACTIVITY_QUERY_SEARCH)) {

                    // Simply moving to selected location
                    if (isSearchMyLocation) {
                        isQuerySearch = true;

                        if (mMap != null) {
                            mMap
                                    .animateCamera(
                                            CameraUpdateFactory
                                                    .newCameraPosition(new CameraPosition(
                                                            Utils.getMyLocation(),
                                                            Constant.DEFAULT_MAP_ZOOM_VALUE,
                                                            0, 0)),
                                            cancelableCallback);
                        }
                    } else {
                        dialog.dismiss();
                        alertDialog = new AlertDialogFragment(
                                Localization.getDialogNoResults(),
                                Localization.getDialogOk());
                    }
                }

                break;
            case TASK_FINISHED:

                if (mMap != null) {


                    List<PointRecord> locationList = result.getPoints();
                    markers.clear();

                    int locationsCount = locationList.size();

                    for (int i = 0; i < locationsCount; i++) {
                        Marker marker = mMap.addMarker(locationList.get(i).toMarkerOptions());
                        markers.put(locationList.get(i).getLocationId(), marker);

                        if (i == 0) {
                            Utils.setInfoWindowMarker(marker);
                            Utils.setInfoWindowRecord(locationList.get(i));
                            marker.showInfoWindow();
                        }
                    }

                    loadingProgressBar.setVisibility(View.GONE);
                    if (taskId.equals(Constant.MAIN_ACTIVITY_QUERY_SEARCH)) {
                        isQuerySearch = true;

                        if (mMap != null) {
                            mMap
                                    .animateCamera(
                                            CameraUpdateFactory
                                                    .newCameraPosition(new CameraPosition(
                                                            result.getStartPosition(),
                                                            Compute.zoomLevel(
                                                                    locationList
                                                                            .get(locationList
                                                                                    .size() - 1)
                                                                            .getDistance(),
                                                                    true), 0, 0)),
                                            cancelableCallback);
                        }
                    }
                    Utils.setLocationList(setSelectedItem(result.getPoints(), 0));

                    resultListAdapter.clear();
                    resultListAdapter.addAll(Utils.getLocationList());
                }
                tempResult = new SearchResult(result);
                break;
            case CONNECTION_ERROR:
                dialog.dismiss();
                alertDialog = new AlertDialogFragment(
                        Localization.getDialogCannotConnectTitle(),
                        Localization.getDialogCannotConnectText(),
                        Localization.getDialogOk());
                break;
            case SERVICE_UNAVAILABLE:
                dialog.dismiss();
                alertDialog = new AlertDialogFragment(
                        Localization.getDialogServiceUnavailable(),
                        Localization.getDialogOk());
                break;
            case XML_PARSER_ERROR:
                dialog.dismiss();
                alertDialog = new AlertDialogFragment(
                        Localization.getDialogParsingError(),
                        Localization.getDialogOk());
                break;
        }

        if (AlertDialogFragment.getInstance() != null) {
            alertDialog.show(getFragmentManager(), Constant.ERROR_DIALOG_TAG);
        }
    }

    @Override
    public void onReceiveFilters(final String taskId, final ErrorType error,
                                 final List<Filter> result) {
        switch (error) {
            case NO_FILTERS_FOUND:
                break;
            case TASK_FINISHED:
                showFiltersPopup(result);
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
            case XML_PARSER_ERROR:
                alertDialog = new AlertDialogFragment(
                        Localization.getDialogParsingError(),
                        Localization.getDialogOk());
                break;
        }
        if (AlertDialogFragment.getInstance() != null) {
            alertDialog.show(getFragmentManager(), Constant.ERROR_DIALOG_TAG);
        }
        dialog.dismiss();
    }

    @Override
    public void onReceiveVersionInfo(final String taskId,
                                     final ErrorType error, final VersionInfo result) {
    }

   /* @Override
    public void onConnected(final Bundle bundle) {

        // replace ua locaiton
		 *if (locationClient == null) {
            return;
        }
		 *
		 *if (locationClient.getLastLocation() != null) {
		 *Utils.setMyLocation(new
                    * LatLng(locationClient.getLastLocation().getLatitude(),
                    * locationClient.getLastLocation().getLongitude()));
		 *
		 *if (firstStart) {
                myPositionButton.performClick();
                firstStart =
		 *false;
            } locationClient.requestLocationUpdates(new
                    * LocationRequest().setInterval(Constant.LOCATION_REQUEST_INTERVAL),
                    * this);
        } else if (firstStart) {
            alertDialog = new
                    * AlertDialogFragment(Localization.getDialogCannotGetPosition(),
                    * Localization.getDialogOk());
            alertDialog.show(getFragmentManager(),
                    * Constant.ERROR_DIALOG_TAG);
            firstStart = false;
        }


    }

    @Override
    public void onDisconnected() {
    }*/

  /*  @Override
    public void onLocationChanged(final Location location) {
        Utils.setMyLocation(new LatLng(location.getLatitude(), location
                .getLongitude()));
    }
*/
   /* @Override
    public void onCameraChange(final CameraPosition cameraPosition) {
        // If we have no Current Position value and it is not Query Search
        if (Utils.getCurrentPosition() != null && !isQuerySearch) {

            // If now is running QUERY SEARCHING
            if (!taskManager.getRunningTaskList().contains(
                    Constant.MAIN_ACTIVITY_QUERY_SEARCH)) {
                // If previous position (CURRENT POSITION value) does not equals
                // current position (from camera)
                if (!Utils.getCurrentPosition().equals(cameraPosition.target)) {

                    // Counting delta - distance between current position and
                    // previous position
                    float delta = Compute.distanceBetweenPoints(
                            Utils.getCurrentPosition(), cameraPosition.target);

                    // This is protection from accidental movings over device
                    // display
                    VisibleRegion vr = mapFragment.getMap().getProjection()
                            .getVisibleRegion();
                    float deltaByDisplay = Compute.distanceBetweenPoints(
                            vr.latLngBounds.northeast,
                            vr.latLngBounds.southwest)
                            / Constant.STEP_CONSTANT;

                    // If distance more than minimum value or value from
                    // accidental movings,
                    // then starts POSITION SEARCH
                    if (delta > Constant.MINIMUM_DISTANCE
                            || delta > deltaByDisplay) {

                        // If map has Query search marker, then marker removing
                        // from map
                        if (querySearchMarker != null) {
                            querySearchMarker.remove();
                        }

                        taskManager.searchLocations(
                                Constant.MAIN_ACTIVITY_POSITION_SEARCH,
                                cameraPosition.target,
                                filterManager.getFiltersString(null));
                        loadingProgressBar.setVisibility(View.VISIBLE);

                        // Memorize position where we have started SEARCHING
                        Utils.setCurrentPosition(cameraPosition.target);
                    }
                }
            }
        } else {
            // It is first launch or QUERY SEARCH
            // Memorize position where we have started SEARCHING
            Utils.setCurrentPosition(cameraPosition.target);

            // If is QUERY SEARCH
            if (isQuerySearch) {
                // If map has Query search marker, then marker removing from map
                if (querySearchMarker != null) {
                    querySearchMarker.remove();
                }

                // If it is not searching my location
                if (!isSearchMyLocation) {
                    String searchPointTitle = tempResult.getStartAddress();

                    if (searchPointTitle != null) {
                        if (searchPointTitle.length() == 0) {
                            searchPointTitle = Utils.getQueryText();
                        }
                    }
                    // Creating Query search marker on searching location
                    querySearchMarker = this.mapFragment
                            .getMap()
                            .addMarker(
                                    new MarkerOptions()
                                            .icon(BitmapDescriptorFactory
                                                    .fromResource(R.drawable.location_marker))
                                            .position(
                                                    tempResult
                                                            .getStartPosition())
                                            .title(searchPointTitle));
                } else {
                    // If it is searching my location then flag setting off
                    isSearchMyLocation = false;
                }
                // QUERY SEARCH is ended
                isQuerySearch = false;
            }
        }
    }

    @Override
    public void onConnectionFailed(final ConnectionResult connectionResult) {
    }*/

    @Override
    public void onBackPressed() {
        Utils.setCurrentPosition(null);
        finish();
        Utils.startActivity(MainActivity.this, com.allpoint.activities.tablet.MainMenuActivity.class, false, false, !Utils.isOnMainActivity());
    }

    @Override
    protected void onPause() {
        if (Utils.getInfoWindowMarker() != null) {
            if (!Utils.getInfoWindowMarker().isInfoWindowShown()) {
                Utils.setInfoWindowMarker(null);
            }
        }
        // Cancel the request
        if (pendingResult != null) {
            pendingResult.cancel();
        }

        super.onPause();
    }

    @Override
    public void onStop() {
        // replace ua locaiton
        // Disconnecting the client invalidates it.
        // locationClient.disconnect();
        Utils.setOnMainActivity(false);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        taskManager.removeTaskListener(this);
    }

    @Override
    public void onInfoWindowClick(final Marker marker) {
        if (querySearchMarker != null) {
            if (!marker.equals(querySearchMarker)) {
                openDetailsActivity(Utils.getLocationList(), marker);
            }
        } else {
            openDetailsActivity(Utils.getLocationList(), marker);
        }
    }

    @Override
    public void onItemClick(final AdapterView<?> parent, final View view,
                            final int position, final long id) {
        List<Marker> markerList = new ArrayList<>();
        markerList.addAll(markers.values());

        PointRecord record = Utils.getLocationList().get(position);
        Utils.setRecord(record);

        for (Marker marker : markerList) {
            if (Compute.isEqualsLatLng(record.getPosition(),
                    marker.getPosition(), Constant.ROUND_VALUE)) {
                if (record.getName().equals(marker.getTitle())) {
                    marker.setSnippet(Utils.getDistanceString(record
                            .getDistance()));
                    Utils.setInfoWindowRecord(record);

                    onMarkerClick(marker);
                    break;
                }
            }
        }
        Utils.startActivity(this, PointDetailsActivity.class, true, false,
                false);
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        if (querySearchMarker != null) {
            if (marker.equals(querySearchMarker)) {
                Utils.setInfoWindowMarker(null);
                Utils.setLocationList(clearSelection(Utils.getLocationList()));
            } else {
                positionInList = getMarkerPositionFromList(
                        Utils.getLocationList(), marker);
                Utils.setInfoWindowMarker(marker);
                Utils.setLocationList(setSelectedItem(Utils.getLocationList(),
                        positionInList));
            }
        } else {
            positionInList = getMarkerPositionFromList(Utils.getLocationList(),
                    marker);
            Utils.setInfoWindowMarker(marker);
            Utils.setLocationList(setSelectedItem(Utils.getLocationList(),
                    positionInList));
        }
        resultListAdapter.clear();
        resultListAdapter.addAll(Utils.getLocationList());
        resultsListView.setSelectionFromTop(positionInList, 0);

        marker.showInfoWindow();
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(final String query) {
        taskManager.searchLocations(Constant.MAIN_ACTIVITY_QUERY_SEARCH, query,
                filterManager.getFiltersString(null));

        searchView.onActionViewCollapsed();

        Utils.setQueryText(query);
        searchView.setIconified(false);
        searchView.clearFocus();
        return false;
    }

    @Override
    public boolean onQueryTextChange(final String newText) {
        return false;
    }

    @Override
    public void onSettingsChanged() {

    }

    @Override
    public void onSettingsShowed() {

    }

    @Override
    public void onSettingsDismissed() {
        Settings.SaveSettings();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        enableMyLocation();

        //TODO check map setting
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.getUiSettings().setCompassEnabled(false);
        mMap.getUiSettings().setRotateGesturesEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setTiltGesturesEnabled(false);
        mMap.setOnInfoWindowClickListener(this);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnCameraMoveListener(this);
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE, Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }


    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog.newInstance(true).show(getSupportFragmentManager(), "dialog");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iBtnShowResultList:

                onIbtnShowResultListClick();
                break;
            case R.id.zoomInButton:
                onZoomInButtonClick();
                break;
            case R.id.zoomOutButton:
                onZoomOutButtonClick();
                break;
            case R.id.iBtnShowMyPosition:

                onIbtnShowMyPositionClicked();
                break;

            case R.id.iBtnFilters:

                onIbtnFiltersClick();
                break;


        }
    }

   /* @Override
    public void onLocationChanged(Location location) {
        //TextView locationTv = (TextView) findViewById(R.id.latlongLocation);
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
      *//*  mMap.addMarker(new MarkerOptions().position(latLng));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        //locationTv.setText("Latitude:" + latitude + ", Longitude:" + longitude);*//*
        Utils.setMyLocation(latLng);


    }*/


    //--------------------------------------------
    // Location with Urbanairship start here

    private void requestLocation() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE, Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (!isGPSEnabled()) {

            showGPSSettingDialog();

        } else {


            if (pendingResult != null) {
                pendingResult.cancel();
            }
            LocationRequestOptions options = new LocationRequestOptions.Builder().setPriority(getPriority()).create();


            pendingResult = UAirship.shared()
                    .getLocationManager()
                    .requestSingleLocation(options)
                    .addResultCallback(Looper.getMainLooper(), new ResultCallback<Location>() {
                        @Override
                        public void onResult(@Nullable Location location) {

                            //progress.setVisibility(View.INVISIBLE);

                            if (location != null) {

                                Utils.setMyLocation(new LatLng(location.getLatitude(), location.getLongitude()));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), DEFAULT_ZOOM));
                                //Toast.makeText(getContext(), formatLocation(location), Toast.LENGTH_SHORT).show();
                            } else {

                                Log.d(TAG, "Current location is null. Using defaults.");
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                                mMap.getUiSettings().setMyLocationButtonEnabled(false);

                                Toast.makeText(getContext(), "Failed to get location", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {

        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults, Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
            requestLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            Toast.makeText(getContext(), "Enable location permissions and try again.", Toast.LENGTH_SHORT).show();
            mPermissionDenied = true;
        }

    }

    private String formatLocation(Location location) {
        return String.format("provider: %s lat: %s, lon: %s, accuracy: %s",
                location.getProvider(),
                location.getLatitude(),
                location.getLongitude(),
                location.getAccuracy());
    }

    /**
     * Gets the LocationRequestOptions priority from the radio group.
     *
     * @return The location request options priority.
     */
    @LocationRequestOptions.Priority
    private int getPriority() {
       /* switch (priorityGroup.getCheckedRadioButtonId()) {
            case R.id.priority_high_accuracy:
                return LocationRequestOptions.PRIORITY_HIGH_ACCURACY;
            case R.id.priority_balanced:
                return LocationRequestOptions.PRIORITY_BALANCED_POWER_ACCURACY;
            case R.id.priority_low_power:
                return LocationRequestOptions.PRIORITY_LOW_POWER;
            case R.id.priority_no_power:
                return LocationRequestOptions.PRIORITY_NO_POWER;
        }*/

        return LocationRequestOptions.PRIORITY_BALANCED_POWER_ACCURACY;
    }

    @Override
    public void onCameraMove() {


        // If we have no Current Position value and it is not Query Search
        if (Utils.getCurrentPosition() != null && !isQuerySearch) {

            // If now is running QUERY SEARCHING
            if (!taskManager.getRunningTaskList().contains(Constant.MAIN_ACTIVITY_QUERY_SEARCH)) {
                // If previous position (CURRENT POSITION value) does not equals
                // current position (from camera)
                if (!Utils.getCurrentPosition().equals(mMap.getCameraPosition().target)) {

                    // Counting delta - distance between current position and
                    // previous position
                    float delta = Compute.distanceBetweenPoints(Utils.getCurrentPosition(), mMap.getCameraPosition().target);

                    // This is protection from accidental movings over device
                    // display
                    VisibleRegion vr = mMap.getProjection().getVisibleRegion();
                    float deltaByDisplay = Compute.distanceBetweenPoints(vr.latLngBounds.northeast, vr.latLngBounds.southwest)
                            / Constant.STEP_CONSTANT;

                    // If distance more than minimum value or value from
                    // accidental movings,
                    // then starts POSITION SEARCH
                    if (delta > Constant.MINIMUM_DISTANCE
                            || delta > deltaByDisplay) {

                        // If map has Query search marker, then marker removing
                        // from map
                        if (querySearchMarker != null) {
                            querySearchMarker.remove();
                        }

                        taskManager.searchLocations(Constant.MAIN_ACTIVITY_POSITION_SEARCH, mMap.getCameraPosition().target, filterManager.getFiltersString(null));
                        loadingProgressBar.setVisibility(View.VISIBLE);
                    }
                }
                // Memorize position where we have started SEARCHING
                Utils.setCurrentPosition(mMap.getCameraPosition().target);
            }
        } else {
            // It is first launch or QUERY SEARCH
            // Memorize position where we have started SEARCHING
            Utils.setCurrentPosition(mMap.getCameraPosition().target);

            // If is QUERY SEARCH
            if (isQuerySearch) {
                // If map has Query search marker, then marker removing from map
                if (querySearchMarker != null) {
                    querySearchMarker.remove();
                }

                // If it is not searching my location
                if (!isSearchMyLocation) {
                    String searchPointTitle = tempResult.getStartAddress();

                    if (searchPointTitle != null) {
                        if (searchPointTitle.length() == 0) {
                            searchPointTitle = Utils.getQueryText();
                        }
                    }
                    // Creating Query search marker on searching location
                    querySearchMarker = mMap
                            .addMarker(new MarkerOptions()
                                    .icon(BitmapDescriptorFactory
                                            .fromResource(R.drawable.location_marker))
                                    .position(
                                            tempResult
                                                    .getStartPosition())
                                    .title(searchPointTitle));
                } else {
                    // If it is searching my location then flag setting off
                    isSearchMyLocation = false;
                }
                // QUERY SEARCH is ended
                isQuerySearch = false;
            }
        }


    }

// urban Airship end here
//--------------------------------------------

    public boolean isGPSEnabled() {
        boolean hasGPSEnabled = false;

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            if (lm != null) {
                gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            }
        } catch (Exception ignored) {
        }

        try {
            if (lm != null) {
                network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            }
        } catch (Exception ignored) {
        }

        if (gps_enabled && network_enabled) {
            hasGPSEnabled = true;
        }

        return hasGPSEnabled;
    }

    public void showGPSSettingDialog() {
        // notify user
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setMessage(getResources().getString(R.string.gps_network_not_enabled));
        dialog.setPositiveButton(getResources().getString(R.string.open_location_settings), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                // TODO Auto-generated method stub
                Intent myIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(myIntent);
                //get gps
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                // TODO Auto-generated method stub

            }
        });
        dialog.show();
    }
}
