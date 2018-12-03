package com.allpoint.services.xmlParser;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;

import com.allpoint.model.Filter;
import com.allpoint.model.PointRecord;
import com.allpoint.model.ResultContainter;
import com.allpoint.model.SearchResult;
import com.allpoint.model.VersionInfo;
import com.allpoint.services.InternetConnectionManager;
import com.google.android.gms.maps.model.LatLng;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


/**
 * TaskManager
 *
 * @author: Vyacheslav.Shmakin
 * @version: 08.01.14
 */
public class TaskManager {

    private static TaskManager taskManager = new TaskManager();

    private Activity mContext;
    private InternetConnectionManager connectionManager;


    private TaskManager() {
        connectionManager = InternetConnectionManager.getInstance(mContext);
    }

    public static TaskManager getInstance(Activity mContext) {
        taskManager.mContext = mContext;
        return taskManager;

    }


    private static final List<AsyncTaskListener> taskListeners = new ArrayList<>();
    private static final Set<String> taskIdList = new HashSet<>();

    private String stoppingTask;
    private boolean fullStop = false;

    public interface AsyncTaskListener {
        void onTaskStarted(final String taskId, final QueryId queryId);

        void onSearchFinished(final String taskId, final ErrorType error, final SearchResult result);

        void onReceiveFilters(final String taskId, final ErrorType error, final List<Filter> result);

        void onReceiveVersionInfo(final String taskId, final ErrorType error, final VersionInfo result);
    }

    public Set<String> getRunningTaskList() {
        return taskIdList;
    }

    public void stopTask(final String taskId) {
        stoppingTask = taskId;
    }

    public void shutdownAllTasks() {
        if (taskIdList.size() != 0) {
            fullStop = true;
        }
    }

    public void addTaskListener(final AsyncTaskListener listener) {
        taskListeners.add(listener);
    }

    public void removeTaskListener(final AsyncTaskListener listener) {
        taskListeners.remove(listener);
    }

    private final SearchResult results = new SearchResult();

    private SearchResult getResults() {
        return results;
    }

    private void setResults(final List<PointRecord> records,
                            final String startAddress, final LatLng startPosition) {
        this.results.setPoints(records);
        this.results.setStartAddress(startAddress);
        this.results.setStartPosition(startPosition);
    }

    private final VersionInfo versionInfo = new VersionInfo();

    private void setVersionInfo(final int versionId,
                                final String googlePlayUrl, final boolean isForceUpdate) {
        this.versionInfo.setVersionId(versionId);
        this.versionInfo.setGooglePlayUrl(googlePlayUrl);
        this.versionInfo.setForceUpdate(isForceUpdate);
    }

    private VersionInfo getVersionInfo() {
        return this.versionInfo;
    }

    private final List<Filter> filters = new ArrayList<Filter>();

    private void setFilters(final List<Filter> filterList) {
        this.filters.clear();
        this.filters.addAll(filterList);
    }

    private List<Filter> getFilters() {
        return this.filters;
    }

    private static tagList selectedTag = tagList.None;

    private static enum tagList {
        None, LocationId, RetailOutlet, AddressLine, CityName, StateCode, PostalCode, CountryCode, Latitude, Longitude, Distance, ImageURL, MapIcon, MobileValue, service, hour, CurrentVersionID, AppUri, ForceUpdate
    }

    private static void setSelectedTag(final String tagValue) {
        for (tagList tag : tagList.values()) {
            if (tagValue.equalsIgnoreCase(tag.toString())) {
                selectedTag = tag;
                return;
            }
        }
        selectedTag = tagList.None;
    }

    private static void setSelectedTag(final tagList tagValue) {
        selectedTag = tagValue;
    }

    public enum QueryId {
        SEARCH_LOCATIONS, GET_FILTERS, GET_VERSION_INFO
    }

    public void searchLocations(final String taskId, final String address,
                                final String locationTypes) {
        QueryId queryId = QueryId.SEARCH_LOCATIONS;
        if (!prepareForExecuteTask(taskId, queryId)) {
            return;
        }

        new taskExecute(taskId, queryId, UrlBuilder.getSearchUrl(address, locationTypes)).execute();
    }

    public void searchLocations(final String taskId, final LatLng position,
                                final String locationTypes) {
        QueryId queryId = QueryId.SEARCH_LOCATIONS;
        if (!prepareForExecuteTask(taskId, queryId)) {
            return;
        }

        new taskExecute(taskId, queryId, UrlBuilder.getSearchUrl(position, locationTypes)).execute();
        //taskExecute(taskId, queryId, UrlBuilder.getSearchUrl(position, locationTypes));
    }

    public void searchFilters(final String taskId) {
        QueryId queryId = QueryId.GET_FILTERS;
        if (!prepareForExecuteTask(taskId, queryId)) {
            return;
        }

        new taskExecute(taskId, queryId, UrlBuilder.getFiltersUrl()).execute();
        //taskExecute(taskId, queryId, UrlBuilder.getFiltersUrl());
    }

    public void getVersionInfo(final String taskId, final String deviceType) {
        QueryId queryId = QueryId.GET_VERSION_INFO;
        if (!prepareForExecuteTask(taskId, queryId)) {
            return;
        }
        new taskExecute(taskId, queryId, UrlBuilder.getVersionInfoUrl(deviceType)).execute();
        //taskExecute(taskId, queryId, UrlBuilder.getVersionInfoUrl(deviceType));
    }

    private boolean prepareForExecuteTask(final String taskId, final QueryId queryId) {
        if (!taskIdList.contains(taskId)) {
            taskIdList.add(taskId);
        } else {
            return false;
        }

        connectionManager = InternetConnectionManager.getInstance(mContext);
        if (!connectionManager.isConnected()) {
            taskFinished(taskId, queryId, ErrorType.CONNECTION_ERROR, null);
            return false;
        }

        for (AsyncTaskListener listener : taskListeners) {
            listener.onTaskStarted(taskId, queryId);
        }
        stopTask(null);
        return true;
    }


    @SuppressLint("StaticFieldLeak")
    public class taskExecute extends AsyncTask<Void, Void, Void> {
        String taskId;
        QueryId queryId;
        String url;

        taskExecute(final String taskId, final QueryId queryId, final String url) {

            this.taskId = taskId;
            this.queryId = queryId;
            this.url = url;
        }

        @Override
        protected Void doInBackground(Void[] objects) {


            try {


                SAXParserFactory spf = SAXParserFactory.newInstance();
                SAXParser sp = spf.newSAXParser();

                DefaultHandler handler;

                switch (queryId) {
                    case SEARCH_LOCATIONS:
                        handler = new SearchHandler(taskId, queryId);
                        sp.parse(url, handler);

                        taskFinished(taskId, queryId, ErrorType.TASK_FINISHED, new ResultContainter(getResults()));
                        break;
                    case GET_FILTERS:
                        handler = new FiltersHandler(taskId, queryId);
                        sp.parse(url, handler);
                        taskFinished(taskId, queryId, ErrorType.TASK_FINISHED, new ResultContainter(getFilters()));
                        break;
                    case GET_VERSION_INFO:
                        handler = new VersionInfoHandler(taskId, queryId);
                        sp.parse(url, handler);
                        taskFinished(taskId, queryId, ErrorType.TASK_FINISHED, new ResultContainter(getVersionInfo()));
                        break;
                }
            } catch (ParserConfigurationException e) {
                //e.printStackTrace();
            } catch (SAXException e) {

                if (!connectionManager.isConnected()) {
                    taskFinished(taskId, queryId, ErrorType.CONNECTION_ERROR, null);
                } else if (!isContainsErrorMessage(e.getMessage())) {
                    taskFinished(taskId, queryId, ErrorType.XML_PARSER_ERROR, null);
                    //e.printStackTrace();
                }
            } catch (IOException e) {
                if (!connectionManager.isConnected()) {
                    taskFinished(taskId, queryId, ErrorType.CONNECTION_ERROR, null);
                } else {
                    taskFinished(taskId, queryId, ErrorType.SERVICE_UNAVAILABLE,
                            null);
                    //e.printStackTrace();
                }
            } catch (NumberFormatException e) {
                if (!connectionManager.isConnected()) {
                    taskFinished(taskId, queryId, ErrorType.CONNECTION_ERROR, null);
                } else {
                    taskFinished(taskId, queryId, ErrorType.XML_PARSER_ERROR, null);
                    //e.printStackTrace();
                }
            }

            return null;
        }
    }


    private void taskFinished(final String taskId, final QueryId queryId, final ErrorType error, final ResultContainter results) {

        mContext.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                ResultContainter resultsTemp = results;

                taskIdList.remove(taskId);
                if (taskIdList.size() == 0) {
                    fullStop = false;
                }

                if (resultsTemp == null) {
                    resultsTemp = new ResultContainter(null, null, null);
                }

                for (AsyncTaskListener listener : taskListeners) {
                    switch (queryId) {
                        case SEARCH_LOCATIONS:
                            listener.onSearchFinished(taskId, error, resultsTemp.getSearchResult());
                            break;
                        case GET_FILTERS:
                            listener.onReceiveFilters(taskId, error, resultsTemp.getFilters());
                            break;
                        case GET_VERSION_INFO:
                            listener.onReceiveVersionInfo(taskId, error, resultsTemp.getVersionInfo());
                            break;
                    }
                }

            }
        });


    }

    private class SearchHandler extends DefaultHandler {
        private final List<PointRecord> recordList = new ArrayList<>();
        private final QueryId queryId;
        private PointRecord tempRecord;
        private LatLng startPosition;
        private String startAddress;
        private final String taskId;
        double latitude;
        double longitude;

        private SearchHandler(final String newTaskId, final QueryId newQueryId) {
            this.queryId = newQueryId;
            this.taskId = newTaskId;
        }

        public void startElement(final String uri, final String localName, final String qName, final Attributes attributes)
                throws SAXException {
            if (qName.equalsIgnoreCase("locations")) {
                if (attributes.getLength() != 0) {
                    double latitude = Double.valueOf(attributes
                            .getValue("StartLatitude"));
                    double longitude = Double.valueOf(attributes
                            .getValue("StartLongitude"));

                    startPosition = new LatLng(latitude, longitude);
                    startAddress = attributes.getValue("StartAddress");
                } else {
                    taskFinished(taskId, queryId, ErrorType.NO_RESULTS_FOUND,
                            null);
                    throw new SAXException(
                            ErrorType.NO_RESULTS_FOUND.toString());
                }
            }

            if (qName.equalsIgnoreCase("attribute")) {
                String key = attributes.getValue(0);

                if (key != null && !key.isEmpty()) {
                    setSelectedTag(key);
                } else {
                    key = attributes.getValue("key");

                    if (key != null && !key.isEmpty()) {
                        setSelectedTag(key);
                    } else {
                        setSelectedTag(tagList.None);
                    }
                }

            } else if (qName.equalsIgnoreCase("service")) {
                setSelectedTag(tagList.service);
            } else if (qName.equalsIgnoreCase("hour")) {
                setSelectedTag(tagList.hour);
            }

            if ((stoppingTask != null && stoppingTask.equalsIgnoreCase(taskId))
                    || fullStop) {
                stopTask(null);
                taskFinished(taskId, queryId, ErrorType.TASK_STOPPED, null);
                throw new SAXException(ErrorType.TASK_STOPPED.toString());
            }
        }

        public void endElement(final String uri, final String localName,
                               final String qName) throws SAXException {
            if (qName.equalsIgnoreCase("location")) {
                tempRecord.setPosition(new LatLng(latitude, longitude));
                recordList.add(tempRecord);
                tempRecord = null;
            } else if (qName.equalsIgnoreCase("locations")) {
                setResults(recordList, startAddress, startPosition);
            }
        }

        public void characters(final char ch[], final int start,
                               final int length) throws SAXException {
            if (tempRecord == null) {
                tempRecord = new PointRecord();
            }

            final String result = new String(ch, start, length);

            switch (selectedTag) {
                case AddressLine:
                    tempRecord.setAddressLine(result);
                    break;
                case CityName:
                    tempRecord.setCity(result);
                    break;
                case CountryCode:
                    tempRecord.setCountry(result);
                    break;
                case Distance:

                    if (!result.isEmpty()) {
                        tempRecord.setDistance(Float.valueOf(result));
                    }
                    break;
                case Latitude:
                    if (!result.isEmpty()) {
                        latitude = (Double.valueOf(result));
                    }
                    break;
                case Longitude:
                    if (!result.isEmpty()) {
                        longitude = (Double.valueOf(result));
                    }
                    break;
                case hour:
                    tempRecord.addHour(result);
                    break;
                case LocationId:
                    if (!result.isEmpty()) {
                        tempRecord.setLocationId(Long.parseLong(result));
                    }
                    break;
                case MobileValue:
                    tempRecord.setMobileValue(result);
                    break;
                case PostalCode:
                    tempRecord.setPostalCode(result);
                    break;
                case RetailOutlet:
                    tempRecord.setName(result);
                    break;
                case service:
                    tempRecord.addService(result);
                    break;
                case StateCode:
                    tempRecord.setState(result);
                    break;
                case ImageURL:
                    tempRecord.setLogoName(result);
                    break;
                case MapIcon:
                    tempRecord.setPinName(result);
                    break;
            }
            setSelectedTag(tagList.None);
        }
    }

    private class FiltersHandler extends DefaultHandler {
        private final List<Filter> filterList = new ArrayList<>();
        private final QueryId queryId;
        private final String taskId;
        private String filterName;
        private String filterValue;

        private FiltersHandler(final String newTaskId, final QueryId newQueryId) {
            this.queryId = newQueryId;
            this.taskId = newTaskId;
        }

        public void startElement(final String uri, final String localName,
                                 final String qName, final Attributes attributes)
                throws SAXException {
            if (qName.equalsIgnoreCase("option")) {
                if (attributes.getLength() != 0) {
                    filterName = attributes.getValue("Name");
                    filterValue = attributes.getValue("Value");
                } else {
                    taskFinished(taskId, queryId, ErrorType.NO_FILTERS_FOUND,
                            null);
                    throw new SAXException(
                            ErrorType.NO_FILTERS_FOUND.toString());
                }
            } else if (qName.equalsIgnoreCase("status")) {
                taskFinished(taskId, queryId, ErrorType.NO_FILTERS_FOUND, null);
                throw new SAXException(ErrorType.NO_FILTERS_FOUND.toString());
            }

            if ((stoppingTask != null && stoppingTask.equalsIgnoreCase(taskId))
                    || fullStop) {
                stopTask(null);
                taskFinished(taskId, queryId, ErrorType.TASK_STOPPED, null);
                throw new SAXException(ErrorType.TASK_STOPPED.toString());
            }
        }

        public void endElement(final String uri, final String localName, final String qName) throws SAXException {

            if (qName.equalsIgnoreCase("option")) {

                filterList.add(new Filter(filterName, filterValue));

            } else if (qName.equalsIgnoreCase("SearchOptions")) {

                setFilters(filterList);

            }
        }
    }

    private class VersionInfoHandler extends DefaultHandler {
        private final QueryId queryId;
        private final String taskId;
        private String googlePlayUrl;
        private int versionId = -1;
        private boolean isForceUpdate = false;

        private VersionInfoHandler(final String newTaskId, final QueryId newQueryId) {
            this.queryId = newQueryId;
            this.taskId = newTaskId;
        }

        public void startElement(final String uri, final String localName, final String qName, final Attributes attributes) throws SAXException {
            if (qName.equalsIgnoreCase("CurrentVersionID")) {
                setSelectedTag(tagList.CurrentVersionID);
            } else if (qName.equalsIgnoreCase("AppUri")) {
                setSelectedTag(tagList.AppUri);
            } else if (qName.equalsIgnoreCase("ForceUpdate")) {
                setSelectedTag(tagList.ForceUpdate);
            }

            if ((stoppingTask != null && stoppingTask.equalsIgnoreCase(taskId))
                    || fullStop) {
                stopTask(null);
                taskFinished(taskId, queryId, ErrorType.TASK_STOPPED, null);
                throw new SAXException(ErrorType.TASK_STOPPED.toString());
            }
        }

        public void endElement(final String uri, final String localName,
                               final String qName) throws SAXException {
            if (qName.equalsIgnoreCase("CurrentAppVersion")) {
                if (versionId == -1 || googlePlayUrl == null) {
                    taskFinished(taskId, queryId, ErrorType.NO_VERSION_INFO_FOUND, null);
                } else {
                    setVersionInfo(versionId, googlePlayUrl, isForceUpdate);
                }
            }
        }

        public void characters(final char ch[], final int start, final int length) throws SAXException {
            switch (selectedTag) {
                case CurrentVersionID:
                    versionId = Integer.parseInt(new String(ch, start, length));
                    break;
                case ForceUpdate:
                    isForceUpdate = Boolean.valueOf(new String(ch, start, length));
                    break;
                case AppUri:
                    googlePlayUrl = new String(ch, start, length);
                    break;
            }
            setSelectedTag(tagList.None);
        }
    }

    private boolean isContainsErrorMessage(final String errorMessage) {
        for (ErrorType error : ErrorType.values()) {
            if (error.toString().equalsIgnoreCase(errorMessage)) {
                return true;
            }
        }
        return false;
    }
}