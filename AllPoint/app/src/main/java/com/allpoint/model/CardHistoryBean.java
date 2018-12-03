package com.allpoint.model;

import java.io.Serializable;

public class CardHistoryBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String mTransactionId;
	private String mDate;
	private String mAmount;
	private String mAddress;
	private String mCity;
	private String mState;
	private String mZip;
	private String mLatitude;
	private String mLongitude;

	// public CardHistoryBean(String mTransactionType, String mDate,
	// String mAmount, String mAddress, String mDetailAddress,String mLatitude,
	// String mLongitude) {
	// this.mTransactionId = mTransactionType;
	// this.mDate = mDate;
	// this.mAmount = mAmount;
	// this.mAddress = mAddress;
	// this.mDetailAddress = mDetailAddress;
	// this.mLatitude = mLatitude;
	// this.mLongitude = mLongitude;
	// }

	public String getTransactionType() {
		return mTransactionId;
	}

	public void setTransactionType(String mTransactionID) {
		this.mTransactionId = mTransactionID;
	}

	public String getDate() {
		return mDate;
	}

	public void setDate(String mDate) {
		this.mDate = mDate;
	}

	public String getAmount() {
		return mAmount;
	}

	public void setAmount(String mAmount) {
		this.mAmount = mAmount;
	}

	public String getAddress() {
		return mAddress;
	}

	public void setAddress(String mAddress) {
		this.mAddress = mAddress;
	}

	public String getCity() {
		return mCity;
	}

	public void setCity(String mCity) {
		this.mCity = mCity;
	}

	public String getState() {
		return mState;
	}

	public void setState(String mState) {
		this.mState = mState;
	}

	public String getZip() {
		return mZip;
	}

	public void setZip(String mZip) {
		this.mZip = mZip;
	}

	public String getLatitude() {
		return mLatitude;
	}

	public void setLatitude(String mLatitude) {
		this.mLatitude = mLatitude;
	}

	public String getLongitude() {
		return mLongitude;
	}

	public void setLongitude(String mLongitude) {
		this.mLongitude = mLongitude;
	}

}
