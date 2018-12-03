package com.allpoint.services;

public class RespForgetPass {

	public boolean ForgetStatus;
	public String ForgetStatusCode;
	public String ForgetStatusMessage;

	public boolean getForgetStatus() {
		return ForgetStatus;
	}

	public boolean setForgeStatus(boolean loginStatus) {
		return ForgetStatus = loginStatus;
	}

	public String getForgetStatusCode() {
		return ForgetStatusCode;
	}

	public void setForgetStatusCode(String loginStatusCode) {
		ForgetStatusCode = loginStatusCode;
	}

	public String getForgetStatusMessage() {
		return ForgetStatusMessage;
	}

	public void setForgetStatusMessage(String loginStatusMessage) {
		ForgetStatusMessage = loginStatusMessage;
	}
}
