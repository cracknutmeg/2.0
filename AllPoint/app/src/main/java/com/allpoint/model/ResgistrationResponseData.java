package com.allpoint.model;

public class ResgistrationResponseData {

	boolean status;
	String statusCode;
	String msg;

	public ResgistrationResponseData() {
		// TODO Auto-generated constructor stub
	}

	public ResgistrationResponseData(boolean status, String statusCode,
			String msg) {
		this.status = status;
		this.statusCode = statusCode;
		this.msg = msg;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean getStatus() {
		return status;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public String getMsg() {
		return msg;
	}

	public String getSession() {
		return msg;
	}

}
