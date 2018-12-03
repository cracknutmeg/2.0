package com.allpoint.services;

public class ResponseVarifyPan {

	public boolean VarifyPanStatus;
	public boolean VarifyActivtInActive;
	public String VarifyPanStatusCode;
	public String VarifyPanStatusMessage;

	public boolean getVarifyPanStatus() {
		return VarifyPanStatus;
	}

	public boolean setVarifyPanStatus(boolean loginStatus) {
		return VarifyPanStatus = loginStatus;
	}

	public String getVarifyPanStatusCode() {
		return VarifyPanStatusCode;
	}

	// check active & inactive status
	public boolean setVarifyActivtInActiveStatus(boolean activeInactiveStatus) {
		return VarifyActivtInActive = activeInactiveStatus;
	}

	public boolean getVarifyActivtInActiveCode() {
		return VarifyActivtInActive;
	}

	public void setVarifyPanStatusCode(String loginStatusCode) {
		VarifyPanStatusCode = loginStatusCode;
	}

	public String getVarifyPanStatusMessage() {
		return VarifyPanStatusMessage;
	}

	public void setVarifyPanStatusMessage(String loginStatusMessage) {
		VarifyPanStatusMessage = loginStatusMessage;
	}

}
