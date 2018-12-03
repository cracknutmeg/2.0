package com.allpoint.services;

public class RespReg {

	public boolean RegStatus;
	public String RegStatusCode;
	public String RegStatusMessage;
	public String RegUserId;
	public String RegFirstName;
	public String RegLastName;
	public String RegEmailId;
	public String RegMobNo;
	public String RegZipCode;
	public String RegTokan;

	public boolean getRegStatus() {
		return RegStatus;
	}

	public boolean setRegStatus(boolean regStatus) {
		return RegStatus = regStatus;
	}

	public String getRegStatusCode() {
		return RegStatusCode;
	}

	public void setRegStatusCode(String regStatusCode) {
		RegStatusCode = regStatusCode;
	}

	public String getRegStatusMessage() {
		return RegStatusMessage;
	}

	public void setRegStatusMessage(String regStatusMessage) {
		RegStatusMessage = regStatusMessage;
	}

	public String getRegUserId() {
		return RegUserId;
	}

	public void setRegUserId(String regUserId) {
		RegUserId = regUserId;
	}

	public String getFirstName() {
		return RegFirstName;
	}

	public void setFirstName(String regFirstName) {
		RegFirstName = regFirstName;
	}

	public String getLastName() {
		return RegLastName;
	}

	public void setLastName(String regLastName) {
		RegLastName = regLastName;
	}

	public String getEmailId() {
		return RegEmailId;
	}

	public void setEmailId(String regEmailId) {
		RegEmailId = regEmailId;
	}

	public String getMobNo() {
		return RegMobNo;
	}

	public void setMobNoId(String regMobNo) {
		RegMobNo = regMobNo;
	}

	public String getZipCode() {
		return RegZipCode;
	}

	public void setZipCode(String regZipCode) {
		RegZipCode = regZipCode;
	}

	public String getTokan() {
		return RegTokan;
	}

	public void setTokan(String regTokan) {
		RegTokan = regTokan;
	}
}
