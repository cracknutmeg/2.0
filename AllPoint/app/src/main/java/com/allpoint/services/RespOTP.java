package com.allpoint.services;

public class RespOTP {

	public boolean OTPStatus;
	public String OTPStatusCode;
	public String OTPStatusMessage;
	public String OTPToken;
	
	public boolean getOTPStatus() {
		return OTPStatus;
	}

	public boolean setOTPStatus(boolean otpStatus) {
		return OTPStatus = otpStatus;
	}

	public String getOTPStatusCode() {
		return OTPStatusCode;
	}

	public void setOTPStatusCode(String otpStatusCode) {
		OTPStatusCode = otpStatusCode;
	}

	public String getOTPStatusMessage() {
		return OTPStatusMessage;
	}

	public void setOTPStatusMessage(String otpStatusMessage) {
		OTPStatusMessage = otpStatusMessage;
	}
	
	
	public String getOTPToken() {
		return OTPToken;
	}

	public void setOTPToken(String otpToken) {
		OTPToken = otpToken;
	}
}
