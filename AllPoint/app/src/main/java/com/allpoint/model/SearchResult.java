package com.allpoint.model;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.model.LatLng;

/**
 * SearchResult
 * 
 * @author: Vyacheslav.Shmakin
 * @version: 08.01.14
 */
public class SearchResult {

	private final List<PointRecord> recordList = new ArrayList<PointRecord>();
	private LatLng startPosition;
	private String startAddress;

	public SearchResult() {
	}

	public SearchResult(final SearchResult result) {
		recordList.clear();
		recordList.addAll(result.getPoints());
		this.startAddress = result.getStartAddress();
		this.startPosition = result.getStartPosition();
	}

	public List<PointRecord> getPoints() {
		return recordList;
	}

	public SearchResult setPoints(final List<PointRecord> newRecordList) {
		recordList.clear();
		recordList.addAll(newRecordList);
		return this;
	}

	public LatLng getStartPosition() {
		return startPosition;
	}

	public SearchResult setStartPosition(final LatLng newStartPosition) {
		startPosition = newStartPosition;
		return this;
	}

	public String getStartAddress() {
		return startAddress;
	}

	public SearchResult setStartAddress(final String address) {
		startAddress = address;
		return this;
	}
}
