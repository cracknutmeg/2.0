package com.allpoint.services;

public class RespVerifyEmailRequest {

	public boolean VerifyEmailStatus;
	public String VerifyEmailStatusCode;
	public String VerifyEmailStatusMessage;
	public String sessionTokenIs;

	public boolean getVerifyEmailStatus() {
		return VerifyEmailStatus;
	}

	public boolean setVerifyEmailStatus(boolean verifyEmailStatus) {
		return VerifyEmailStatus = verifyEmailStatus;
	}

	public String getVerifyEmailStatusCode() {
		return VerifyEmailStatusCode;
	}

	public void setVerifyEmailStatusCode(String verifyEmailStatusCode) {
		VerifyEmailStatusCode = verifyEmailStatusCode;
	}

	public String getVerifyEmailStatusMessage() {
		return VerifyEmailStatusMessage;
	}

	public void setVerifyEmailStatusMessage(String verifyEmailStatusMessage) {
		VerifyEmailStatusMessage = verifyEmailStatusMessage;
	}

	public String getVerifyEmailSessionToken() {
		return sessionTokenIs;
	}

	public void setVerifyEmailSessionToken(String verifyEmailsessionToken) {
		sessionTokenIs = verifyEmailsessionToken;
	}

}
