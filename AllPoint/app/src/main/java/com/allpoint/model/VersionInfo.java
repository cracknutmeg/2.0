/**
 *@ com.allpoint.model.VersionInfo
 */
package com.allpoint.model;

/**
 * VersionInfo
 * 
 * @author: Vyacheslav.Shmakin
 * @version: 08.01.14
 */
public class VersionInfo {
	private int versionId = -1;
	private boolean isForceUpdate = false;

	private String googlePlayUrl;

	public VersionInfo setVersionId(final int versionId) {
		this.versionId = versionId;
		return this;
	}

	public VersionInfo setForceUpdate(final boolean forceUpdate) {
		isForceUpdate = forceUpdate;
		return this;
	}

	public VersionInfo setGooglePlayUrl(final String googlePlayUrl) {
		this.googlePlayUrl = googlePlayUrl;
		return this;
	}

	public String getGooglePlayUrl() {
		return googlePlayUrl;
	}

	public int getVersionId() {
		return versionId;
	}

	public boolean isForceUpdate() {
		return isForceUpdate;
	}
}
