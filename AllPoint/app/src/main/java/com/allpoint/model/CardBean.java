package com.allpoint.model;

import java.io.Serializable;

public class CardBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String mCardNumber;
	private String mCardName;

	public CardBean(String name, String cardnumber) {
		mCardName = name;
		mCardNumber = cardnumber;
	}

	public String getCardNumber() {
		return mCardNumber;
	}

	public void setCardNumber(String mCardNumber) {
		this.mCardNumber = mCardNumber;
	}

	public String getCardName() {
		return mCardName;
	}

	public void setCardName(String mCardName) {
		this.mCardName = mCardName;
	}

}
