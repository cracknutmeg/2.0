package com.allpoint.services;

public class RespLogin {

	public boolean LoginStatus;
	public String LoginStatusCode;
	public String LoginStatusMessage;
	public String sessionTokenIs;

	public boolean getLoginStatus() {
		return LoginStatus;
	}

	public boolean setLoginStatus(boolean loginStatus) {
		return LoginStatus = loginStatus;
	}

	public String getLoginStatusCode() {
		return LoginStatusCode;
	}

	public void setLoginStatusCode(String loginStatusCode) {
		LoginStatusCode = loginStatusCode;
	}

	public String getLoginStatusMessage() {
		return LoginStatusMessage;
	}

	public void setLoginStatusMessage(String loginStatusMessage) {
		LoginStatusMessage = loginStatusMessage;
	}

	public String getLoginSessionToken() {
		return sessionTokenIs;
	}

	public void setLoginSessionToken(String sessionToken) {
		sessionTokenIs = sessionToken;
	}

}
