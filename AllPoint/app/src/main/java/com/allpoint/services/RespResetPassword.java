package com.allpoint.services;

public class RespResetPassword {

	public boolean ResetPassStatus;
	public String ResetPassStatusCode;
	public String ResetPassStatusMessage;
	public String ResetPassTokenIs;

	public boolean getResetPassStatus() {
		return ResetPassStatus;
	}

	public boolean setResetPassStatus(boolean resetPassStatus) {
		return ResetPassStatus = resetPassStatus;
	}

	public String getResetPassStatusCode() {
		return ResetPassStatusCode;
	}

	public void setResetPassStatusCode(String resetPassStatusCode) {
		ResetPassStatusCode = resetPassStatusCode;
	}

	public String getResetPassStatusMessage() {
		return ResetPassStatusMessage;
	}

	public void setResetPassStatusMessage(String resetPassStatusMessage) {
		ResetPassStatusMessage = resetPassStatusMessage;
	}

}
