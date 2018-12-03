package com.allpoint.services;

import java.io.Serializable;
import java.util.ArrayList;

public class ResponseCustomerPortfilio implements Serializable {

	public boolean CustomerPortfilioStatus;
	public String CustomerPortfilioStatusCode;
	public String CustomerPortfilioStatusMessage;
	public boolean CustomerPortfilioCardHistoryEnabled;

	public ArrayList<ResponseCustomerProtfolioDetails> CustomerProtfolio = new ArrayList<ResponseCustomerProtfolioDetails>();

	public ArrayList<ResponseCustomerProtfolioDetails> getCustomerProtfolio() {
		return CustomerProtfolio;
	}

	public void setCustomerProtfolio(
			ArrayList<ResponseCustomerProtfolioDetails> customerProtfolio) {
		CustomerProtfolio = customerProtfolio;
	}

	public boolean getCustomerPortfilioStatus() {
		return CustomerPortfilioStatus;
	}

	public boolean setCustomerPortfilioStatus(boolean customerPortfilioStatus) {
		return CustomerPortfilioStatus = customerPortfilioStatus;
	}

	public String getCustomerPortfilioStatusCode() {
		return CustomerPortfilioStatusCode;
	}

	public void setCustomerPortfilioStatusCode(
			String customerPortfilioStatusCode) {
		CustomerPortfilioStatusCode = customerPortfilioStatusCode;
	}

	public String getCustomerPortfilioStatusMessage() {
		return CustomerPortfilioStatusMessage;
	}

	public void setCustomerPortfilioStatusMessage(
			String customerPortfilioStatusMessage) {
		CustomerPortfilioStatusMessage = customerPortfilioStatusMessage;
	}

	public boolean setCustomerPortfilioCardHistoryEnabled(
			boolean customerPortfilioCardHistoryEnabled) {
		return CustomerPortfilioCardHistoryEnabled = customerPortfilioCardHistoryEnabled;
	}

	public boolean getCustomerPortfilioCardHistoryEnabled() {
		return CustomerPortfilioCardHistoryEnabled;
	}

}
