package com.allpoint.services;

import java.io.Serializable;
import java.util.ArrayList;

public class ResponseTransaction implements Serializable {

	ResponseTransaction() {

	}

	public boolean ResponseTransactionStatus;
	public String ResponseTransactionStatusCode;
	public String ResponseTransactionStatusMessage;

	public ArrayList<ResponseTransactionDetails> RespTrans = new ArrayList<ResponseTransactionDetails>();

	public ArrayList<ResponseTransactionDetails> getRspTrans() {
		return RespTrans;
	}

	public void setRspTrans(ArrayList<ResponseTransactionDetails> respTrans) {
		RespTrans = respTrans;
	}

	public boolean getResponseTransactionStatus() {
		return ResponseTransactionStatus;
	}

	public boolean setResponseTransactionStatus(
			boolean responseTransactionStatus) {
		return ResponseTransactionStatus = responseTransactionStatus;
	}

	public String getResponseTransactionStatusCode() {
		return ResponseTransactionStatusCode;
	}

	public void setResponseTransactionStatusCode(
			String responseTransactionStatusCode) {
		ResponseTransactionStatusCode = responseTransactionStatusCode;
	}

	public String getResponseTransactionStatusMessage() {
		return ResponseTransactionStatusMessage;
	}

	public void setResponseTransactionStatusMessage(
			String responseTransactionStatusMessage) {
		ResponseTransactionStatusMessage = responseTransactionStatusMessage;
	}
}
