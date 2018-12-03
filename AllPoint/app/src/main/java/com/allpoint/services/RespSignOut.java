package com.allpoint.services;

public class RespSignOut {

	public boolean SignOutStatus;
	public String SignOutStatusCode;
	public String SignOutStatusMessage;
	public String SignOutToken;
	
	public boolean getSignOutStatus() {
		return SignOutStatus;
	}

	public boolean setSignOutStatus(boolean signoutStatus) {
		return SignOutStatus = signoutStatus;
	}

	public String getSignOutStatusCode() {
		return SignOutStatusCode;
	}

	public void setSignOutStatusCode(String signoutStatusCode) {
		SignOutStatusCode = signoutStatusCode;
	}

	public String getSignOutStatusMessage() {
		return SignOutStatusMessage;
	}

	public void setSignOutStatusMessage(String signoutStatusMessage) {
		SignOutStatusMessage = signoutStatusMessage;
	}
	
	
	public String getSignOutToken() {
		return SignOutToken;
	}

	public void setSignOutToken(String signoutToken) {
		SignOutToken = signoutToken;
	}
}
