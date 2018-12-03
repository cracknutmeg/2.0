package com.allpoint.services;

public class RespChangePass {

	public boolean ChangeStatus;
	public String ChangeStatusCode;
	public String ChangeStatusMessage;

	public String ChangeStatusTokenInvalidCode;

	public boolean getChangePassStatus() {
		return ChangeStatus;
	}

	public boolean setChangePassStatus(boolean changeStatus) {
		return ChangeStatus = changeStatus;
	}

	public String getChangePassStatusCode() {
		return ChangeStatusCode;
	}

	public void setChangePassStatusCode(String changeStatusCode) {
		ChangeStatusCode = changeStatusCode;
	}

	public String getChangePassStatusMessage() {
		return ChangeStatusMessage;
	}

	public void setChangePassStatusMessage(String changeStatusMessage) {
		ChangeStatusMessage = changeStatusMessage;
	}

	public String getChangePassStatusTokenInvalidCode() {
		return ChangeStatusTokenInvalidCode;
	}

	public void setChangePassStatusTokenInvalidCode(
			String changeStatusTokenInvalidCode) {
		ChangeStatusTokenInvalidCode = changeStatusTokenInvalidCode;
	}
}
