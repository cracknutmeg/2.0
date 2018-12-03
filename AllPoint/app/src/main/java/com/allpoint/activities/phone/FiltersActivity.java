/**
 * @ FiltersActivity
 */
package com.allpoint.activities.phone;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.allpoint.R;
import com.allpoint.activities.fragments.AlertDialogFragment;
import com.allpoint.model.Filter;
import com.allpoint.model.SearchResult;
import com.allpoint.model.VersionInfo;
import com.allpoint.services.FilterManager;
import com.allpoint.services.xmlParser.ErrorType;
import com.allpoint.services.xmlParser.TaskManager;
import com.allpoint.util.Constant;
import com.allpoint.util.Localization;
import com.allpoint.util.Utils;
import com.allpoint.util.adapters.FilterListAdapter;
import com.bugsense.trace.BugSenseHandler;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * FiltersActivity
 *
 * @author: Mikhail.Shalagin & Vyacheslav.Shmakin
 * @version: 23.09.13
 */
public class FiltersActivity extends Activity implements TaskManager.AsyncTaskListener {

    protected TaskManager taskManager;
    protected FilterManager filterManager;
    protected ListView listView;
    // Filters
    protected TextView filtersTitle;
    protected TextView filtersSelectBy;
    protected Button filtersDoneButton;
    private AlertDialogFragment alertDialog;
    protected LayoutInflater inflater;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable @android.support.annotation.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filters);
        initGUIElements();
        afterViews();
    }

    private void initGUIElements() {

        taskManager = TaskManager.getInstance(this);
        filterManager = FilterManager.getInstance();
        listView = findViewById(R.id.listView);
        filtersTitle = findViewById(R.id.tvFiltersTitle);
        filtersSelectBy = findViewById(R.id.tvSelectFilterBy);
        filtersDoneButton = findViewById(R.id.btnFiltersDone);

        filtersDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBtnFiltersDoneClick();
            }
        });

    }

    protected void afterViews() {
        BugSenseHandler.initAndStartSession(this, Constant.BUG_SENSE_API_KEY);

        dialog = ProgressDialog.show(this,
                Localization.getDialogLoadingTitle(),
                Localization.getDialogPleaseWait(), true);

        taskManager.addTaskListener(this);
        taskManager.searchFilters(Constant.FILTERS_TASK_ID);
    }

    void onBtnFiltersDoneClick() {
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        Utils.startFlurry(this, Constant.FILTERS_ACTIVITY_EVENT);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Filters
        filtersTitle.setText(Localization.getFiltersLayoutTitle());
        filtersSelectBy.setText(Localization.getFiltersSelectByTitle());
        filtersDoneButton.setText(Localization.getFiltersBtnDone());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        taskManager.removeTaskListener(this);
    }

    protected void onIbtnFiltersBackClicked(View view) {
        onBackPressed();
    }

    public void onTvFiltersTitleClick(View view) {
        onBackPressed();
    }

    @Override
    public void onTaskStarted(String taskId, TaskManager.QueryId queryId) {
    }

    @Override
    public void onSearchFinished(String taskId, ErrorType error,
                                 SearchResult result) {
    }

    @Override
    public void onReceiveFilters(String taskId, final ErrorType error,
                                 List<Filter> result) {
        if (taskId.equals(Constant.FILTERS_TASK_ID)) {
            switch (error) {
                case NO_FILTERS_FOUND:
                    break;
                case TASK_FINISHED:
                    FilterListAdapter filterListAdapter = new FilterListAdapter(
                            FiltersActivity.this);

                    final List<Filter> temp = filterManager
                            .getUpdatedFilterList(result);
                    for (Filter filter : temp) {
                        filterListAdapter.add(filter);
                    }
                    listView.setAdapter(filterListAdapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            CheckBox checkBox = (CheckBox) view
                                    .findViewById(R.id.chBoxFilterName);
                            if (checkBox != null) {
                                checkBox.setChecked(!checkBox.isChecked());
                                filterManager.setActivated(position,
                                        !temp.get(position).isActivated());
                            }
                        }
                    });
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
                case NO_RESULTS_FOUND:
                    break;
                case NO_VERSION_INFO_FOUND:
                    break;
                case TASK_STOPPED:
                    break;
                default:
                    break;
            }
            if (AlertDialogFragment.getInstance() != null) {
                if (alertDialog != null) {
                    alertDialog.show(getFragmentManager(),
                            Constant.ERROR_DIALOG_TAG);
                }
            }
            dialog.dismiss();
        }
    }

    @Override
    public void onReceiveVersionInfo(String taskId, ErrorType error, VersionInfo result) {
    }
}
