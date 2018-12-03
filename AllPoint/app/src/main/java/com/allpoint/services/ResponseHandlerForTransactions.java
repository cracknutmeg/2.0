package com.allpoint.services;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ResponseHandlerForTransactions extends DefaultHandler {

	boolean currentElement = false;

	String currentValue = "";

	ResponseVarifyPan getVarifyPan;
	ResponseCardDelete getCardDelete;

	ResponseCustomerPortfilio getCustomerPortfilio;
	ResponseCustomerProtfolioDetails mCard;

	ResponseTransaction getRespTrans;
	ResponseTransactionDetails transDetails;

	boolean isVarifyPan = false;
	boolean isCardDelete = false;
	boolean isCustomerPortfilio = false;
	boolean isTrans = false;

	// Varify Pan
	public ResponseVarifyPan getVarifyPanResp() {
		return getVarifyPan;
	}

	// Card Delete
	public ResponseCardDelete getCardDeleteResp() {
		return getCardDelete;
	}

	public ResponseCustomerPortfilio getCustomerPortfilio() {
		return getCustomerPortfilio;
	}

	public ResponseTransaction getTransactions() {
		return getRespTrans;
	}

	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		currentElement = true;

		if (qName.equals("BinCheckServiceResponse")) {

			getVarifyPan = new ResponseVarifyPan();
			isCardDelete = false;
			isVarifyPan = true;
			isCustomerPortfilio = false;
			isTrans = false;
		} else if (qName.equals("DeletePANDetailsResponseResponse")) {

			getCardDelete = new ResponseCardDelete();
			isCardDelete = true;
			isVarifyPan = false;
			isCustomerPortfilio = false;
			isTrans = false;
		} else if (qName.equals("CustomerProtfolioServiceResponse")) {

			getCustomerPortfilio = new ResponseCustomerPortfilio();
			getCustomerPortfilio
					.setCustomerProtfolio(new ArrayList<ResponseCustomerProtfolioDetails>());
			isVarifyPan = false;
			isCustomerPortfilio = true;
			isTrans = false;
			isCardDelete = false;
		} else if (qName.equals("CustomerProtfolio")) {
			mCard = new ResponseCustomerProtfolioDetails();
		} else if (qName.equals("TransactionHistoryServiceResponse")) {

			getRespTrans = new ResponseTransaction();
			getRespTrans
					.setRspTrans(new ArrayList<ResponseTransactionDetails>());
			isCardDelete = false;
			isVarifyPan = false;
			isCustomerPortfilio = false;
			isTrans = true;
		} else if (qName.equals("transaction")) {
			transDetails = new ResponseTransactionDetails();
		}

	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		currentElement = false;

		if (isVarifyPan) {

			if (qName.equalsIgnoreCase("Status"))
				getVarifyPan.setVarifyPanStatus(Boolean
						.parseBoolean(currentValue.trim()));
			else if (qName.equalsIgnoreCase("StatusCode"))
				getVarifyPan.setVarifyPanStatusCode(currentValue.trim());
			else if (qName.equalsIgnoreCase("StatusMessage"))
				getVarifyPan.setVarifyPanStatusMessage(currentValue.trim());
			if (qName.equalsIgnoreCase("IsActive"))
				getVarifyPan.setVarifyActivtInActiveStatus(Boolean
						.parseBoolean(currentValue.trim()));

			currentValue = "";
		} else if (isCardDelete) {

			if (qName.equalsIgnoreCase("Status"))
				getCardDelete.setCardDeleteStatus(Boolean
						.parseBoolean(currentValue.trim()));
			else if (qName.equalsIgnoreCase("StatusCode"))
				getCardDelete.setCardDeleteStatusCode(currentValue.trim());
			else if (qName.equalsIgnoreCase("StatusMessage"))
				getCardDelete.setCardDeleteStatusMessage(currentValue.trim());

			currentValue = "";

		} else if (isCustomerPortfilio) {
			if (qName.equalsIgnoreCase("Status"))
				getCustomerPortfilio.setCustomerPortfilioStatus(Boolean
						.parseBoolean(currentValue.trim()));
			else if (qName.equalsIgnoreCase("StatusCode"))
				getCustomerPortfilio
						.setCustomerPortfilioStatusCode(currentValue.trim());
			else if (qName.equalsIgnoreCase("StatusMessage"))
				getCustomerPortfilio
						.setCustomerPortfilioStatusMessage(currentValue.trim());
			else if (qName.equalsIgnoreCase("CardHistoryEnabled"))
				getCustomerPortfilio
						.setCustomerPortfilioCardHistoryEnabled(Boolean
								.parseBoolean(currentValue.trim()));
			else if (qName.equalsIgnoreCase("PAN"))
				mCard.setCustomerPan(currentValue.trim());

			else if (qName.equalsIgnoreCase("SavedMoney"))
				mCard.setCustomerSavedMoney(currentValue.trim());

			else if (qName.equalsIgnoreCase("CustomerProtfolio"))
				getCustomerPortfilio.getCustomerProtfolio().add(mCard);

			currentValue = "";
		}
		if (isTrans) {
			if (qName.equalsIgnoreCase("Status"))
				getRespTrans.setResponseTransactionStatus(Boolean
						.parseBoolean(currentValue.trim()));
			else if (qName.equalsIgnoreCase("StatusCode"))
				getRespTrans.setResponseTransactionStatusCode(currentValue
						.trim());
			else if (qName.equalsIgnoreCase("StatusMessage"))
				getRespTrans.setResponseTransactionStatusMessage(currentValue
						.trim());
			else if (qName.equalsIgnoreCase("transactionId"))
				transDetails.setTransactionId(currentValue.trim());
			else if (qName.equalsIgnoreCase("transactiondate"))
				transDetails.setDate(currentValue.trim());
			else if (qName.equalsIgnoreCase("transactionamount"))
				transDetails.setAmount(currentValue.trim());
			else if (qName.equalsIgnoreCase("transactionType"))
				transDetails.setTransactionType(currentValue.trim());
			else if (qName.equalsIgnoreCase("ATMAddress"))
				transDetails.setAddress(currentValue.trim());
			else if (qName.equalsIgnoreCase("City"))
				transDetails.setCity(currentValue.trim());
			else if (qName.equalsIgnoreCase("State"))
				transDetails.setState(currentValue.trim());
			else if (qName.equalsIgnoreCase("ZIP"))
				transDetails.setZip(currentValue.trim());
			else if (qName.equalsIgnoreCase("Lat"))
				transDetails.setLatitude(currentValue.trim());
			else if (qName.equalsIgnoreCase("Long"))
				transDetails.setLongitude(currentValue.trim());

			else if (qName.equalsIgnoreCase("transaction"))
				getRespTrans.getRspTrans().add(transDetails);

			currentValue = "";
		}
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {

		if (currentElement) {
			currentValue = currentValue + new String(ch, start, length);
		}
	}
}
