package com.allpoint.services;

import java.io.Serializable;

public class ResponseCustomerProtfolioDetails implements Serializable {
	ResponseCustomerProtfolioDetails() {

	}

	public String customerPan;
	public String CustomerSavedMoney;

	public String getCustomerPan() {
		return customerPan;
	}

	public void setCustomerPan(String customerPan) {
		this.customerPan = customerPan;
	}

	public String getCustomerSavedMoney() {
		return CustomerSavedMoney;
	}

	public void setCustomerSavedMoney(String customerSavedMoney) {
		CustomerSavedMoney = customerSavedMoney;
	}
}
