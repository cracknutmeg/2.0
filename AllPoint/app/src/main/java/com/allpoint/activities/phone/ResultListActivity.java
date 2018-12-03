package com.allpoint.activities.phone;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.allpoint.R;
import com.allpoint.activities.fragments.AlertDialogFragment;
import com.allpoint.model.Filter;
import com.allpoint.model.PointRecord;
import com.allpoint.model.SearchResult;
import com.allpoint.model.VersionInfo;
import com.allpoint.services.FilterManager;
import com.allpoint.services.xmlParser.ErrorType;
import com.allpoint.services.xmlParser.TaskManager;
import com.allpoint.util.Constant;
import com.allpoint.util.Localization;
import com.allpoint.util.Utils;
import com.allpoint.util.adapters.ResultListAdapter;
import com.bugsense.trace.BugSenseHandler;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * ResultListActivity
 *
 * @author: Mikhail.Shalagin & Vyacheslav.Shmakin
 * @version: 23.09.13
 */
public class ResultListActivity extends FragmentActivity implements
        AdapterView.OnItemClickListener, SearchView.OnQueryTextListener,
        TaskManager.AsyncTaskListener {

    TaskManager taskManager;
    FilterManager filterManager;
    ListView listView;
    private ResultListAdapter listAdapter;
    ImageButton bottomSearchButton;
    SearchView searchView;
    TextView tvSearch;
    // SearchResult
    TextView searchResultTitle;
    private AlertDialogFragment alertDialog;
    private ProgressDialog dialog;

    private List<PointRecord> tempResults;
    private LatLng currentPosition;
    private boolean isQuerySearch = false;
    private StringBuilder tempQueryText = new StringBuilder();
    private StringBuilder queryText = new StringBuilder();
    private ImageButton iBtnResultsBack;
    private ImageButton iBtnOpenMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results_list);
        taskManager = TaskManager.getInstance(this);
        filterManager = FilterManager.getInstance();

        initGUIElments();
        afterViews();
    }

    private void initGUIElments() {

        listView = findViewById(R.id.resultListView);
        bottomSearchButton = findViewById(R.id.iBtnBottomSearch);
        searchView = findViewById(R.id.searchViewResults);
        tvSearch = findViewById(R.id.iTxtBottomSearch);
        searchResultTitle = findViewById(R.id.tvResultsTitle);
        iBtnResultsBack = findViewById(R.id.iBtnResultsBack);
        iBtnOpenMap = findViewById(R.id.iBtnOpenMap);

        iBtnResultsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onIbtnResultsBackClicked();
            }
        });

        iBtnOpenMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onIbtnOpenMapClicked();
            }
        });
    }

    void afterViews() {


        BugSenseHandler.initAndStartSession(this, Constant.BUG_SENSE_API_KEY);

        bottomSearchButton.setImageResource(R.drawable.bottom_search_press);
        tvSearch.setTextColor(getResources().getColor(R.color.textColor));
        // bottomSearchButton.setBackgroundResource(R.drawable.bottom_press_button_bg);
        bottomSearchButton.setEnabled(false);

        listAdapter = new ResultListAdapter(this);

        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(this);

        taskManager.addTaskListener(this);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Utils.startFlurry(this, Constant.RESULTS_ACTIVITY_EVENT);
        if (!isQuerySearch) {
            taskManager.searchLocations(
                    Constant.RESULT_LIST_ACTIVITY_POSITION_SEARCH,
                    MainActivity.getCurrentPosition(),
                    filterManager.getFiltersString(null));
            currentPosition = MainActivity.getCurrentPosition();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // SearchResult
        searchResultTitle.setText(Localization.getSearchResultLayoutTitle());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Utils.setRecord(tempResults.get(position));
        Utils.startActivity(this, PointDetailsActivity.class, true, false,
                false);
    }

    protected void onIbtnResultsBackClicked() {
        onBackPressed();
    }

    // @Click(R.id.iBtnOpenMap)
    // protected void onIbtnShowCurrentClicked() {
    // taskManager.searchLocations(Constant.RESULT_LIST_ACTIVITY_POSITION_SEARCH,
    // Utils.getMyLocation(), filterManager.getFiltersString(null));
    // currentPosition = MainActivity.getCurrentPosition();
    //
    // }

    protected void onIbtnOpenMapClicked() {
        Utils.setActionEnabled(true);
        Utils.setActionCode(Constant.OPEN_MAP_VALUE);
        if (tempQueryText.length() != 0) {
            MainActivity.setQueryText(tempQueryText.toString());
        }
        finish();
    }

    @Override
    public void onBackPressed() {

        MainActivity.setCurrentPosition(currentPosition);
        Utils.setActionEnabled(true);
        Utils.setActionCode(Constant.RETURN_ON_MAP_VALUE);

        super.onBackPressed();
    }

   /* @Click(R.id.tvResultsTitle)
    public void onTvResultsTitleClick() {
        onBackPressed();
    }*/

    @Override
    public boolean onQueryTextSubmit(String query) {
        taskManager.searchLocations(Constant.RESULT_LIST_ACTIVITY_QUERY_SEARCH,
                query, filterManager.getFiltersString(null));
        isQuerySearch = true;
        queryText.delete(0, queryText.length()).append(query);

        searchView.onActionViewCollapsed();

        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        taskManager.removeTaskListener(this);
    }

    @Override
    public void onTaskStarted(String taskId, TaskManager.QueryId queryId) {
        if (taskId
                .equalsIgnoreCase(Constant.RESULT_LIST_ACTIVITY_POSITION_SEARCH)
                || taskId
                .equalsIgnoreCase(Constant.RESULT_LIST_ACTIVITY_QUERY_SEARCH)) {
            dialog = ProgressDialog.show(this,
                    Localization.getDialogLoadingTitle(),
                    Localization.getDialogPleaseWait(), true, false);
        }
    }

    @Override
    public void onSearchFinished(String taskId, ErrorType error,
                                 SearchResult result) {
        if (taskId
                .equalsIgnoreCase(Constant.RESULT_LIST_ACTIVITY_POSITION_SEARCH)
                || taskId
                .equalsIgnoreCase(Constant.RESULT_LIST_ACTIVITY_QUERY_SEARCH)) {
            switch (error) {
                case NO_RESULTS_FOUND:
                    dialog.dismiss();
                    alertDialog = new AlertDialogFragment(
                            Localization.getDialogNoResults(),
                            Localization.getDialogOk());
                    break;
                case TASK_FINISHED:
                    listAdapter.clear();
                    tempResults = result.getPoints();
                    listAdapter.addAll(tempResults);

                    if (taskId.equalsIgnoreCase(Constant.RESULT_LIST_ACTIVITY_QUERY_SEARCH)) {


                        MainActivity.setCurrentPosition(result.getStartPosition());
                        isQuerySearch = false;

                        tempQueryText.delete(0, tempQueryText.length());
                        String startAddress = result.getStartAddress();

                        if (startAddress != null) {
                            tempQueryText.append(startAddress);
                        } else {
                            tempQueryText.append(queryText.toString());
                        }
                    }
                    dialog.dismiss();
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
                if (alertDialog != null) {
                    alertDialog.show(getFragmentManager(),
                            Constant.ERROR_DIALOG_TAG);
                }
            }
        }
    }

    @Override
    public void onReceiveFilters(String taskId, ErrorType error,
                                 List<Filter> result) {
    }

    @Override
    public void onReceiveVersionInfo(String taskId, ErrorType error,
                                     VersionInfo result) {
    }
}
