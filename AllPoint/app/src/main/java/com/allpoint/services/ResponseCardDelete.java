package com.allpoint.services;

public class ResponseCardDelete {

	public boolean CardDeleteStatus;
	public String CardDeleteStatusCode;
	public String CardDeleteStatusMessage;

	public boolean getCardDeleteStatus() {
		return CardDeleteStatus;
	}

	public boolean setCardDeleteStatus(boolean loginStatus) {
		return CardDeleteStatus = loginStatus;
	}

	public String getCardDeleteStatusCode() {
		return CardDeleteStatusCode;
	}

	public void setCardDeleteStatusCode(String loginStatusCode) {
		CardDeleteStatusCode = loginStatusCode;
	}

	public String getCardDeleteStatusMessage() {
		return CardDeleteStatusMessage;
	}

	public void setCardDeleteStatusMessage(String loginStatusMessage) {
		CardDeleteStatusMessage = loginStatusMessage;
	}

}
