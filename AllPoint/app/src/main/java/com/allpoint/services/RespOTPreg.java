package com.allpoint.services;

public class RespOTPreg {

	public boolean OTPregStatus;
	public String OTPregStatusCode;
	public String OTPregStatusMessage;

	public boolean getOTPRegStatus() {
		return OTPregStatus;
	}

	public boolean setOTPregStatus(boolean otpregStatus) {
		return OTPregStatus = otpregStatus;
	}

	public String getOTPregStatusCode() {
		return OTPregStatusCode;
	}

	public void setOTPregStatusCode(String otpregStatusCode) {
		OTPregStatusCode = otpregStatusCode;
	}

	public String getOTPregStatusMessage() {
		return OTPregStatusMessage;
	}

	public void setOTPregStatusMessage(String otpregStatusMessage) {
		OTPregStatusMessage = otpregStatusMessage;
	}

}
