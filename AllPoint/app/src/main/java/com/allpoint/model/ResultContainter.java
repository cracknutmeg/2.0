package com.allpoint.model;

import java.util.ArrayList;
import java.util.List;

/**
 * ResultContainer
 * 
 * @author: Vyacheslav.Shmakin
 * @version: 08.01.14
 */
public class ResultContainter {
	private final List<Filter> filters = new ArrayList<Filter>();
	private SearchResult searchResult;
	private VersionInfo versionInfo;

	public ResultContainter(final SearchResult result,
			final List<Filter> filterList, final VersionInfo info) {
		this.searchResult = result;
		this.versionInfo = info;

		if (filterList != null) {
			this.filters.addAll(filterList);
		}
	}

	public ResultContainter(final SearchResult result) {
		this.searchResult = result;
	}

	public ResultContainter(final List<Filter> filterList) {
		this.filters.addAll(filterList);
	}

	public ResultContainter(final VersionInfo info) {
		this.versionInfo = info;
	}

	public List<Filter> getFilters() {
		return this.filters;
	}

	public SearchResult getSearchResult() {
		return this.searchResult;
	}

	public VersionInfo getVersionInfo() {
		return this.versionInfo;
	}
}
