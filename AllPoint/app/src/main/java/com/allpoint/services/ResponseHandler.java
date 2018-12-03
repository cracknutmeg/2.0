package com.allpoint.services;

import com.allpoint.model.ResgistrationResponseData;
import com.allpoint.util.Constant;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ResponseHandler extends DefaultHandler {

	boolean currentElement = false;
	String currentValue = "";

	String cartId;
	String customerId;
	String email;

	RespGeo pdetail;
	RespLogin loginRespIs;
	ResgistrationResponseData regRespIs;
	RespForgetPass regForgetPassIs;
	RespChangePass regChangePassIs;
	ResponseGetUserDetails getUserDetailsIs;
	ResponseUpdateUser getUpdateUserIs;
	RespResetPassword resetPassDetails;
	RespOTPreg ottRespReg;

	RespOTP ottRespIs;
	// private List<ResponseTransactions> transHistory;

	RespSessionInvalid sessionInvalidResp;
	
	//VerifyEmailRequest
	RespVerifyEmailRequest verifyEmailResp;
	
	//Sgin Out
	
	RespSignOut respSignOutIs;

	boolean isUpdateUser = false;
	boolean isLoginResp = false;
	boolean isRegResp = false;
	boolean isGeoResp = false;
	boolean isregForgetPass = false;
	boolean isChangePass = false;
	boolean isGetUserDetails = false;
	boolean isTransHistory = false;
	boolean isTransactions = false;
	boolean isOTP = false;
	boolean isResetPass = false;
	boolean isOTPreg = false;

	boolean isSessionInvalid = false;

	boolean isVerifyEmail = false;
	
	boolean isSignOut = false;
	
	// ArrayList<EnrolledCard> enrollCardList;

	// public ResponseHandler() {
	// transHistory = new ArrayList<ResponseTransactions>();
	// }

	public String getCustomerId() {
		return customerId;
	}

	public String getEmail() {
		return email;
	}

	// Geo fence resp
	public RespGeo getGeoResp() {
		return pdetail;
	}

	// Login Resp
	public RespLogin getLoginResp() {
		return loginRespIs;
	}

	// Registration Response
	public ResgistrationResponseData getRegResp() {
		// TODO Auto-generated method stub
		return regRespIs;
	}

	// Forget Password
	public RespForgetPass getForgetResp() {
		return regForgetPassIs;
	}

	// Change Passworkd
	public RespChangePass getChangePassResp() {

		return regChangePassIs;
	}

	// GetUserDetails
	public ResponseGetUserDetails getUserDetailsResp() {

		return getUserDetailsIs;
	}

	// UpdateUserDetails
	public ResponseUpdateUser getUpdateUserDetailsResp() {
		return getUpdateUserIs;
	}

	// OTP resp Handling
	public RespOTP getOTPResp() {
		return ottRespIs;
	}

	// OTP resp Handling After Reg
	public RespOTPreg getOTPRespReg() {
		return ottRespReg;
	}

	// Reset Password resp Handling
	public RespResetPassword getResetPassResp() {
		return resetPassDetails;
	}

	public RespSessionInvalid getSessionInvalidresp() {
		return sessionInvalidResp;
	}
	
	public RespVerifyEmailRequest getVerifyEmailresp() {
		return verifyEmailResp;
	}
	
	//Sign Out
	public RespSignOut getSignOutresp() {
		return respSignOutIs;
	}

	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		currentElement = true;

		if (qName.endsWith("LoginResponse")) {
			loginRespIs = new RespLogin();
			isGeoResp = false;
			isLoginResp = true;
			isRegResp = false;
			isregForgetPass = false;
			isChangePass = false;
			isGetUserDetails = false;
			isUpdateUser = false;
			isTransHistory = false;
			isTransactions = false;
			isOTP = false;
			isResetPass = false;
			isOTPreg = false;
			isSessionInvalid = false;
			isVerifyEmail = false;
			isSignOut = false;
		} else if (qName.endsWith("RegistrationResponse")) {
			regRespIs = new ResgistrationResponseData();
			isGeoResp = false;
			isLoginResp = false;
			isRegResp = true;
			isregForgetPass = false;
			isChangePass = false;
			isGetUserDetails = false;
			isUpdateUser = false;
			isTransHistory = false;
			isTransactions = false;
			isOTP = false;
			isResetPass = false;
			isOTPreg = false;
			isSessionInvalid = false;
			isVerifyEmail = false;
			isSignOut = false;
		} else if (qName.endsWith("ForgotPasswordResponse")) {
			regForgetPassIs = new RespForgetPass();
			isGeoResp = false;
			isLoginResp = false;
			isRegResp = false;
			isregForgetPass = true;
			isChangePass = false;
			isGetUserDetails = false;
			isUpdateUser = false;
			isTransHistory = false;
			isTransactions = false;
			isOTP = false;
			isResetPass = false;
			isOTPreg = false;
			isSessionInvalid = false;
			isVerifyEmail = false;
			isSignOut = false;
		} else if (qName.equals("GeofenceResponse")) {
			pdetail = new RespGeo();
			isGeoResp = true;
			isLoginResp = false;
			isRegResp = false;
			isregForgetPass = false;
			isChangePass = false;
			isGetUserDetails = false;
			isUpdateUser = false;
			isTransHistory = false;
			isTransactions = false;
			isOTP = false;
			isResetPass = false;
			isOTPreg = false;
			isSessionInvalid = false;
			isVerifyEmail = false;
			isSignOut = false;
		} else if (qName.equals("ChangePasswordResponse")) {
			regChangePassIs = new RespChangePass();
			isGeoResp = false;
			isLoginResp = false;
			isRegResp = false;
			isregForgetPass = false;
			isGetUserDetails = false;
			isChangePass = true;
			isUpdateUser = false;
			isTransHistory = false;
			isTransactions = false;
			isOTP = false;
			isResetPass = false;
			isOTPreg = false;
			isSessionInvalid = false;
			isVerifyEmail = false;
			isSignOut = false;
		} else if (qName.equals("UserDetailsResponse")) {
			getUserDetailsIs = new ResponseGetUserDetails();
			isGeoResp = false;
			isLoginResp = false;
			isRegResp = false;
			isregForgetPass = false;
			isChangePass = false;
			isGetUserDetails = true;
			isUpdateUser = false;
			isTransHistory = false;
			isTransactions = false;
			isOTP = false;
			isResetPass = false;
			isOTPreg = false;
			isSessionInvalid = false;
			isVerifyEmail = false;
			isSignOut = false;
		} else if (qName.equals("UpdateUserResponse")) {
			getUpdateUserIs = new ResponseUpdateUser();
			isGeoResp = false;
			isLoginResp = false;
			isRegResp = false;
			isregForgetPass = false;
			isChangePass = false;
			isGetUserDetails = false;
			isUpdateUser = true;
			isTransHistory = false;
			isTransactions = false;
			isOTP = false;
			isResetPass = false;
			isOTPreg = false;
			isSessionInvalid = false;
			isVerifyEmail = false;
			isSignOut = false;
		}

		// resp for OTP Handling
		else if (qName.equals("ValidateOTTResponse")) {
			ottRespIs = new RespOTP();
			isOTP = true;
			isGeoResp = false;
			isLoginResp = false;
			isRegResp = false;
			isregForgetPass = false;
			isChangePass = false;
			isGetUserDetails = false;
			isUpdateUser = false;
			isTransHistory = false;
			isTransactions = false;
			isResetPass = false;
			isOTPreg = false;
			isSessionInvalid = false;
			isVerifyEmail = false;
			isSignOut = false;
		}

		// reset password resp Handling
		else if (qName.equals("ResetPasswordResponse")) {
			resetPassDetails = new RespResetPassword();
			isOTP = false;
			isGeoResp = false;
			isLoginResp = false;
			isRegResp = false;
			isregForgetPass = false;
			isChangePass = false;
			isGetUserDetails = false;
			isUpdateUser = false;
			isTransHistory = false;
			isTransactions = false;
			isResetPass = true;
			isOTPreg = false;
			isSessionInvalid = false;
			isVerifyEmail = false;
			isSignOut = false;
		} else if (qName.equals("UserOTTResponse")) {
			ottRespReg = new RespOTPreg();
			isOTP = false;
			isGeoResp = false;
			isLoginResp = false;
			isRegResp = false;
			isregForgetPass = false;
			isChangePass = false;
			isGetUserDetails = false;
			isUpdateUser = false;
			isTransHistory = false;
			isTransactions = false;
			isResetPass = false;
			isOTPreg = true;
			isSessionInvalid = false;
			isVerifyEmail = false;
			isSignOut = false;
		}  else if (qName.equals("VerifyEmailResponse")) {

			verifyEmailResp = new RespVerifyEmailRequest();
			isOTP = false;
			isGeoResp = false;
			isLoginResp = false;
			isRegResp = false;
			isregForgetPass = false;
			isChangePass = false;
			isGetUserDetails = false;
			isUpdateUser = false;
			isTransHistory = false;
			isTransactions = false;
			isResetPass = false;
			isOTPreg = false;

			isSessionInvalid = false;
			isVerifyEmail = true;
			isSignOut = false;
		} else if (qName.equals("SignOutUserResponse")) {

			respSignOutIs = new RespSignOut();
			isOTP = false;
			isGeoResp = false;
			isLoginResp = false;
			isRegResp = false;
			isregForgetPass = false;
			isChangePass = false;
			isGetUserDetails = false;
			isUpdateUser = false;
			isTransHistory = false;
			isTransactions = false;
			isResetPass = false;
			isOTPreg = false;

			isSessionInvalid = false;
			isVerifyEmail = false;
			isSignOut = true;
		}else if (qName.equals("StatusResponse")) {

			sessionInvalidResp = new RespSessionInvalid();
			isOTP = false;
			isGeoResp = false;
			isLoginResp = false;
			isRegResp = false;
			isregForgetPass = false;
			isChangePass = false;
			isGetUserDetails = false;
			isUpdateUser = false;
			isTransHistory = false;
			isTransactions = false;
			isResetPass = false;
			isOTPreg = false;

			isSessionInvalid = true;
			isVerifyEmail = false;
			isSignOut = false;
		}
		
		
		
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		currentElement = false;

		if (isLoginResp) {

			if (qName.equalsIgnoreCase("Status"))
				loginRespIs.setLoginStatus(Boolean.parseBoolean(currentValue
						.trim()));
			else if (qName.equalsIgnoreCase("StatusCode"))
				loginRespIs.setLoginStatusCode(currentValue.trim());
			else if (qName.equalsIgnoreCase("StatusMessage"))
				loginRespIs.setLoginStatusMessage(currentValue.trim());
			else if (qName.equalsIgnoreCase("Token"))
				loginRespIs.setLoginSessionToken(currentValue.trim());

			currentValue = "";

		} else if (isRegResp) {
			if (qName.equalsIgnoreCase("Status"))
				regRespIs.setStatus(Boolean.parseBoolean(currentValue.trim()));
			else if (qName.equalsIgnoreCase("StatusCode"))
				regRespIs.setStatusCode(currentValue.trim());
			else if (qName.equalsIgnoreCase("StatusMessage"))
				regRespIs.setMsg(currentValue.trim());

			currentValue = "";
		} else if (isregForgetPass) {

			if (qName.equalsIgnoreCase("Status"))
				regForgetPassIs.setForgeStatus(Boolean
						.parseBoolean(currentValue.trim()));
			else if (qName.equalsIgnoreCase("StatusCode"))
				regForgetPassIs.setForgetStatusCode(currentValue.trim());
			else if (qName.equalsIgnoreCase("StatusMessage"))
				regForgetPassIs.setForgetStatusMessage(currentValue.trim());

			currentValue = "";

		} else if (isChangePass) {

			if (qName.equalsIgnoreCase("Status"))
				regChangePassIs.setChangePassStatus(Boolean
						.parseBoolean(currentValue.trim()));
			else if (qName.equalsIgnoreCase("StatusCode"))
				regChangePassIs.setChangePassStatusCode(currentValue.trim());
			else if (qName.equalsIgnoreCase("StatusMessage"))
				regChangePassIs.setChangePassStatusMessage(currentValue.trim());

			currentValue = "";

		} else if (isGetUserDetails) {
			if (qName.equalsIgnoreCase("Status"))
				getUserDetailsIs.setStatus(Boolean.parseBoolean(currentValue
						.trim()));
			else if (qName.equalsIgnoreCase("StatusCode"))
				getUserDetailsIs.setStatusCode(currentValue.trim());
			else if (qName.equalsIgnoreCase("StatusMessage"))
				getUserDetailsIs.setStatusMessage(currentValue.trim());
			else if (qName.equalsIgnoreCase("userName"))
				getUserDetailsIs.setUserName(currentValue.trim());
			else if (qName.equalsIgnoreCase("firstName"))
				getUserDetailsIs.setFirstName(currentValue.trim());
			else if (qName.equalsIgnoreCase("lastName"))
				getUserDetailsIs.setLastName(currentValue.trim());
			else if (qName.equalsIgnoreCase("zipCode"))
				getUserDetailsIs.setZipCode(currentValue.trim());
			else if (qName.equalsIgnoreCase("mobileNumber"))
				getUserDetailsIs.setMobileNumber(currentValue.trim());

			currentValue = "";
		} else if (isUpdateUser) {

			if (qName.equalsIgnoreCase("Status"))
				getUpdateUserIs.setStatus(Boolean.parseBoolean(currentValue
						.trim()));
			else if (qName.equalsIgnoreCase("StatusCode"))
				getUpdateUserIs.setStatusCode(currentValue.trim());
			else if (qName.equalsIgnoreCase("StatusMessage"))
				getUpdateUserIs.setMsg(currentValue.trim());

			currentValue = "";

		} else if (isUpdateUser) {

			if (qName.equalsIgnoreCase("Status"))
				getUpdateUserIs.setStatus(Boolean.parseBoolean(currentValue
						.trim()));
			else if (qName.equalsIgnoreCase("StatusCode"))
				getUpdateUserIs.setStatusCode(currentValue.trim());
			else if (qName.equalsIgnoreCase("StatusMessage"))
				getUpdateUserIs.setMsg(currentValue.trim());

			currentValue = "";

		} else if (isOTP) {

			if (qName.equalsIgnoreCase("Status"))
				ottRespIs
						.setOTPStatus(Boolean.parseBoolean(currentValue.trim()));
			else if (qName.equalsIgnoreCase("StatusCode"))
				ottRespIs.setOTPStatusCode(currentValue.trim());
			else if (qName.equalsIgnoreCase("StatusMessage"))
				ottRespIs.setOTPStatusMessage(currentValue.trim());
			else if (qName.equalsIgnoreCase("Token"))
				ottRespIs.setOTPToken(currentValue.trim());
			
			currentValue = "";

		} else if (isResetPass) {

			if (qName.equalsIgnoreCase("Status"))
				resetPassDetails.setResetPassStatus(Boolean
						.parseBoolean(currentValue.trim()));
			else if (qName.equalsIgnoreCase("StatusCode"))
				resetPassDetails.setResetPassStatusCode(currentValue.trim());
			else if (qName.equalsIgnoreCase("StatusMessage"))
				resetPassDetails.setResetPassStatusMessage(currentValue.trim());

			currentValue = "";

		} else if (isOTPreg) {

			if (qName.equalsIgnoreCase("Status"))
				ottRespReg.setOTPregStatus(Boolean.parseBoolean(currentValue
						.trim()));
			else if (qName.equalsIgnoreCase("StatusCode"))
				ottRespReg.setOTPregStatusCode(currentValue.trim());
			else if (qName.equalsIgnoreCase("StatusMessage"))
				ottRespReg.setOTPregStatusMessage(currentValue.trim());

			currentValue = "";

		} else if (isSessionInvalid) {

			if (qName.equalsIgnoreCase("StatusMessage")){
				sessionInvalidResp.setSessionInvalidStatusMessage(currentValue.trim());
				sessionInvalidResp.setSessionInvalidStatusCode(Constant.SESSION_ERROR_CODE);
			}
			
//			else if (qName.equalsIgnoreCase("StatusCode"))
//				sessionInvalidResp.setSessionInvalidStatusCode(currentValue.trim());

			currentValue = "";

		} else if (isVerifyEmail) {

			if (qName.equalsIgnoreCase("Status"))
				verifyEmailResp.setVerifyEmailStatus(Boolean
						.parseBoolean(currentValue.trim()));
			else if (qName.equalsIgnoreCase("StatusCode"))
				verifyEmailResp.setVerifyEmailStatusCode(currentValue.trim());
			else if (qName.equalsIgnoreCase("StatusMessage"))
				verifyEmailResp.setVerifyEmailStatusMessage(currentValue.trim());

			currentValue = "";

		} else if (isSignOut) {

			if (qName.equalsIgnoreCase("Status"))
				respSignOutIs.setSignOutStatus(Boolean
						.parseBoolean(currentValue.trim()));
			else if (qName.equalsIgnoreCase("StatusCode"))
				respSignOutIs.setSignOutStatusCode(currentValue.trim());
			else if (qName.equalsIgnoreCase("StatusMessage"))
				respSignOutIs.setSignOutStatusMessage(currentValue.trim());
			else if (qName.equalsIgnoreCase("Token"))
				respSignOutIs.setSignOutToken(currentValue.trim());

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
