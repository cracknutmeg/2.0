package com.allpoint.util;

import com.allpoint.AtmFinderApplication;
import com.allpoint.R;

/**
 * Localization
 * 
 * @author: Vyacheslav.Shmakin
 * @version: 08.01.14
 */
public class Localization {

	// Top Bar
	private static String cancel;
	private static String login;
	private static String logout;
	private static String signup;
	private static String cont;

	// Filters
	private static String filtersLayoutTitle;
	private static String filtersSelectByTitle;
	private static String filtersBtnDone;

	// Login
	private static String loginEdtUN;
	private static String loginEdtPW;
	private static String loginBtnSignIn;
	private static String loginTxtMsg;
	private static String loginBtnSignUp;
	private static String loginBtnFgtPw;
	private static String loginBtnSkip;

	// MainMenu
	private static String mainMenuSearchTitle;
	private static String mainMenuMessagesTitle;
	private static String mainMenuSettingsTitle;
	private static String mainMenuScanTitle;
	private static String mainMenuTransTitle;

	// Bottom Tabs
	private static String mainMenuTabHome;
	private static String mainMenuTabHistory;
	private static String mainMenuTabMore;
	private static String mainMenuAboutTitle;

	// Sign Up
	private static String signUpFN;
	private static String signUpLN;
	private static String signUpMN;
	private static String signUpEMSG;
	private static String signUpEI;
	private static String signUpCEI;
	private static String signUpPZ;
	private static String signUpPW;
	private static String signUpCPW;
	private static String signUpMsg;
	private static String signUpSub;
	private static String signUpTC;
	private static String signUpCont;

	// Messages
	private static String messagesLayoutTitle;
	private static String messagesBtnDelete;
	private static String messagesBtnRead;
	private static String messagesEditTextOn;
	private static String messagesEditTextOff;
	private static String messagesNoMessagesText;

	// DetailView
	private static String detailViewLayoutTitle;
	private static String detailViewServicesTitle;
	private static String detailViewHoursTitle;

	// SearchResult
	private static String searchResultLayoutTitle;

	// PinView
	private static String pinViewLayoutTitle;

	// About
	private static String aboutLayoutTitle;

	// Settings
	private static String settingsLayoutTitle;
	private static String settingsSearchPreferenceTitle;
	private static String settingsVisitGooglePlay;
	private static String settingsLaunchWithNearMeTitle;
	private static String settingsDistanceUnitsTitle;
	private static String settingsDistanceUnitsKm;
	private static String settingsDistanceUnitsMiles;
	private static String settingsLanguageTitle;
	private static String settingsFeedback;
	private static String settingsVersion;
	private static String settingsEditProfile;

	private static String btnFeedback;
	private static String tvSetGeofence;
	private static String btnTermsAndCond;

	// Share
	private static String shareViaTitle;
	private static String shareEmailTitle;
	private static String shareMessagingTitle;

	// Message View
	private static String messageLayoutTitle;

	// Dialogs
	private static String dialogCannotGetPosition;
	private static String dialogOk;
	private static String dialogCancel;
	private static String dialogLoadingTitle;
	private static String dialogPleaseWait;
	private static String dialogCannotConnectTitle;
	private static String dialogCannotConnectText;
	private static String dialogNoResults;
	private static String dialogSmsNotSupported;
	private static String dialogParsingError;
	private static String dialogUpdate;
	private static String dialogForceUpdate;
	private static String dialogRemoveMessages;
	private static String dialogMessageMarked;
	private static String dialogServiceUnavailable;
	private static String dialogPlayServicesUnavailable;

	// Dialgos Sign Up
	private static String dialogErr_Msg_FastName;
	private static String dialogErr_Msg_LsatName;
	private static String dialogErr_Msg_EmptySpace;
	private static String dialogErr_MSG_EMaild;
	private static String dialogErr_Msg_EmailValid;
	private static String dialogErr_MSG_ConfirmEMaild;
	private static String dialogErr_Msg_ConfirmEmailValid;
	private static String dialogErr_Msg_mobileNumber;
	private static String dialogErr_Msg_MobileValidtaion;
	private static String dialogErr_msg_Password;
	private static String dialogErr_msg_ConfirmPassword;
	private static String dialogErr_msg_ConfrimPassword;
	private static String dialogErr_Msg_PwdCombMatch;
	private static String dialogErr_Msg_ConfirmCombPwdMatch;
	private static String dialogErr_Msg_PwdLength;
	private static String dialogErr_Msg_PwdMatch;
	private static String dialogErr_Msg_EmailMatch;
	private static String dialogErr_Msg_SwitchTurnOn;
	private static String dialogErr_Msg_PasswordCharactors;
	private static String dialogErr_Msg_PasswordConfirmCharactors;
	private static String dialogErr_Msg_Zipcode;
	private static String dialogErr_Msg_ZipValidtaion;
	private static String dialogErr_Msg_AllInfo;
	private static String dialogErr_Msg_ValidFIrstName;
	private static String dialogErr_Msg_ValidlastName;
	private static String dialogErr_Msg_cardNumber;
	private static String dialogErr_Msg_ValidchackcardNumber;
	private static String dialogErr_Msg_ValidcardNumber;
	private static String dialogErr_msg_EnterOldPassword;
	private static String dialogErr_msg_EnterNewPassword;
	private static String dialogErr_msg_EnterConfirmPassword;
	private static String dialogErr_Msg_ChangeOldPassword;
	private static String dialogErr_Msg_ChangeNewPassword;
	private static String dialogErr_Msg_ChangeConfirmPassword;

	public static void setEnglish() {
		// Filter strings
		setFiltersLayoutTitle(AtmFinderApplication.getContext().getString(
				R.string.en_filtersLayoutTitle));
		setFiltersSelectByTitle(AtmFinderApplication.getContext().getString(
				R.string.en_filtersSelectTitle));
		setFiltersBtnDone(AtmFinderApplication.getContext().getString(
				R.string.en_filtersBtnDone));

		// Top Bar
		setlogin(AtmFinderApplication.getContext()
				.getString(R.string.app_login));
		setlogout(AtmFinderApplication.getContext().getString(
				R.string.en_settingslogout));
		setsignup(AtmFinderApplication.getContext().getString(
				R.string.signup_title));
		setcont(AtmFinderApplication.getContext().getString(
				R.string.continue_button));
		setcancel(AtmFinderApplication.getContext().getString(
				R.string.cancel_button));

		// Login
		setloginEdtUN(AtmFinderApplication.getContext().getString(
				R.string.email_hint));
		setloginEdtPW(AtmFinderApplication.getContext().getString(
				R.string.password_hint));
		setloginBtnSignIn(AtmFinderApplication.getContext().getString(
				R.string.login));
		setloginEdtPW(AtmFinderApplication.getContext().getString(
				R.string.password_hint));
		setloginTxtMsg(AtmFinderApplication.getContext().getString(
				R.string.account_history));
		setloginBtnSignUp(AtmFinderApplication.getContext().getString(
				R.string.signup));
		setloginBtnFgtPw(AtmFinderApplication.getContext().getString(
				R.string.forgetPassword));
		setloginBtnSkip(AtmFinderApplication.getContext().getString(
				R.string.skip));

		// MainMenu strings
		setMainMenuAboutTitle(AtmFinderApplication.getContext().getString(
				R.string.en_mainMenuAboutTitle));
		setMainMenuMessagesTitle(AtmFinderApplication.getContext().getString(
				R.string.en_mainMenuMessagesTitle));
		setMainMenuSearchTitle(AtmFinderApplication.getContext().getString(
				R.string.en_mainMenuSearchTitle));
		setMainMenuSettingsTitle(AtmFinderApplication.getContext().getString(
				R.string.en_mainMenuSettingsTitle));
		setMainMenuScanTitle(AtmFinderApplication.getContext().getString(
				R.string.en_mainMenuScanTitle));
		setMainMenuTransTitle(AtmFinderApplication.getContext().getString(
				R.string.en_mainMenuTransTitle));

		// Main Menu Tabs
		setMainMenuTabHome(AtmFinderApplication.getContext().getString(
				R.string.en_mainMenuHome));
		setMainMenuTabHistory(AtmFinderApplication.getContext().getString(
				R.string.en_mainMenuTransTitle));
		setMainMenuTabMore(AtmFinderApplication.getContext().getString(
				R.string.en_mainMenuMore));

		// SignUp
		setSignUpFN(AtmFinderApplication.getContext().getString(
				R.string.first_name_hint));
		setsignUpLN(AtmFinderApplication.getContext().getString(
				R.string.last_name_hint));
		setsignUpMN(AtmFinderApplication.getContext().getString(
				R.string.mobile_hint));
		setsignUpEI(AtmFinderApplication.getContext().getString(R.string.email));
		setsignUpCEI(AtmFinderApplication.getContext().getString(
				R.string.confirm_email));
		setsignUpEMSG(AtmFinderApplication.getContext().getString(
				R.string.email_msg));
		setsignUpPZ(AtmFinderApplication.getContext().getString(
				R.string.postal_zipcode_hint));
		setsignUpPW(AtmFinderApplication.getContext().getString(
				R.string.password_hint));
		setsignUpCPW(AtmFinderApplication.getContext().getString(
				R.string.confirm_password_hint));
		setsignUpMsg(AtmFinderApplication.getContext().getString(
				R.string.password_msg));
		setsignUpSub(AtmFinderApplication.getContext().getString(
				R.string.subscribe));
		setsignUpMsg2(AtmFinderApplication.getContext().getString(R.string.tc));
		setsignUpCont(AtmFinderApplication.getContext().getString(
				R.string.continue_button));

		// Messages strings
		setMessagesLayoutTitle(AtmFinderApplication.getContext().getString(
				R.string.en_messagesLayoutTitle));
		setMessagesBtnDelete(AtmFinderApplication.getContext().getString(
				R.string.en_messagesDeleteMessages));
		setMessagesBtnRead(AtmFinderApplication.getContext().getString(
				R.string.en_messagesReadMessages));
		setMessagesEditTextOff(AtmFinderApplication.getContext().getString(
				R.string.en_messagesEditTextOff));
		setMessagesEditTextOn(AtmFinderApplication.getContext().getString(
				R.string.en_messagesEditTextOn));
		setMessagesNoMessagesText(AtmFinderApplication.getContext().getString(
				R.string.en_messagesNoMessagesText));

		// DetailView strings
		setDetailViewLayoutTitle(AtmFinderApplication.getContext().getString(
				R.string.en_detailViewLayoutTitle));
		setDetailViewServicesTitle(AtmFinderApplication.getContext().getString(
				R.string.en_detailViewServicesTitle));
		setDetailViewHoursTitle(AtmFinderApplication.getContext().getString(
				R.string.en_detailViewHoursTitle));

		// PinView strings
		setPinViewLayoutTitle(AtmFinderApplication.getContext().getString(
				R.string.en_pinViewLayoutTitle));

		// SearchResult strings
		setSearchResultLayoutTitle(AtmFinderApplication.getContext().getString(
				R.string.en_searchResultLayoutTitle));

		// About
		setAboutLayoutTitle(AtmFinderApplication.getContext().getString(
				R.string.en_aboutLayoutTitle));

		// Settings
		setSettingsLayoutTitle(AtmFinderApplication.getContext().getString(
				R.string.en_settingsLayoutTitle));
		setSettingsSearchPreferenceTitle(AtmFinderApplication.getContext()
				.getString(R.string.en_settingsSearchPreferenceTitle));
		setSettingsVisitGooglePlay(AtmFinderApplication.getContext().getString(
				R.string.en_settingsVisitGooglePlay));
		setSettingsLaunchWithNearMeTitle(AtmFinderApplication.getContext()
				.getString(R.string.en_settingsLaunchWithNearMe));
		setSettingsDistanceUnitsTitle(AtmFinderApplication.getContext()
				.getString(R.string.en_settingsDistanceUnits));
		setSettingsDistanceUnitsKm(AtmFinderApplication.getContext().getString(
				R.string.en_settingsDistanceUnitsKm));
		setSettingsDistanceUnitsMiles(AtmFinderApplication.getContext()
				.getString(R.string.en_settingsDistanceUnitsMi));
		setSettingsLanguageTitle(AtmFinderApplication.getContext().getString(
				R.string.en_settingsLanguage));
		setSettingsFeedback(AtmFinderApplication.getContext().getString(
				R.string.en_settingsFeedback));
		setSettingsVersion(AtmFinderApplication.getContext().getString(
				R.string.en_settingsAppVersion));
		setSettingsEditProfile(AtmFinderApplication.getContext().getString(
				R.string.en_settingsEditProfile));

		setSettingsbtnFeedback(AtmFinderApplication.getContext().getString(
				R.string.en_settingsFeedback));
		setSettingstvSetGeofence(AtmFinderApplication.getContext().getString(
				R.string.en_settingsGeofenceOnOff));
		setSettingsbtnTermsAndCond(AtmFinderApplication.getContext().getString(
				R.string.en_TermsAndCondtionTitle));

		// Share
		setShareViaTitle(AtmFinderApplication.getContext().getString(
				R.string.en_shareTitle));
		setShareEmailTitle(AtmFinderApplication.getContext().getString(
				R.string.en_shareEmail));
		setShareMessagingTitle(AtmFinderApplication.getContext().getString(
				R.string.en_shareMessaging));

		// Message View
		setMessageLayoutTitle(AtmFinderApplication.getContext().getString(
				R.string.en_messageLayoutTitle));

		// Dialogs
		setDialogCannotGetPosition(AtmFinderApplication.getContext().getString(
				R.string.en_dialogCannotGetPosition));
		setDialogOk(AtmFinderApplication.getContext().getString(
				R.string.en_dialogOk));
		setDialogCancel(AtmFinderApplication.getContext().getString(
				R.string.en_dialogCancel));
		setDialogLoadingTitle(AtmFinderApplication.getContext().getString(
				R.string.en_dialogLoadingTitle));
		setDialogPleaseWait(AtmFinderApplication.getContext().getString(
				R.string.en_dialogPleaseWait));
		setDialogCannotConnectTitle(AtmFinderApplication.getContext()
				.getString(R.string.en_dialogCannotConnectTitle));
		setDialogCannotConnectText(AtmFinderApplication.getContext().getString(
				R.string.en_dialogCannotConnectText));
		setDialogNoResults(AtmFinderApplication.getContext().getString(
				R.string.en_dialogNoResults));
		setDialogSmsNotSupported(AtmFinderApplication.getContext().getString(
				R.string.en_dialogSmsNotSupported));
		setDialogParsingError(AtmFinderApplication.getContext().getString(
				R.string.en_dialogParsingError));
		setDialogUpdate(AtmFinderApplication.getContext().getString(
				R.string.en_dialogUpdate));
		setDialogForceUpdate(AtmFinderApplication.getContext().getString(
				R.string.en_dialogForceUpdate));
		setDialogRemoveMessages(AtmFinderApplication.getContext().getString(
				R.string.en_dialogRemoveMessages));
		setDialogMessageMarked(AtmFinderApplication.getContext().getString(
				R.string.en_dialogMessageMarked));
		setDialogServiceUnavailable(AtmFinderApplication.getContext()
				.getString(R.string.en_dialogServiceUnavailable));
		setDialogPlayServicesUnavailable(AtmFinderApplication.getContext()
				.getString(R.string.en_dialogPlayServicesUnavailable));

		// Sigunp Dialogs
		setdialogErr_Msg_FastName(AtmFinderApplication.getContext().getString(
				R.string.Err_Msg_FIrstName));
		setdialogErr_Msg_LsatName(AtmFinderApplication.getContext().getString(
				R.string.Err_Msg_LsatName));
		setdialogErr_Msg_EmptySpace(AtmFinderApplication.getContext()
				.getString(R.string.Err_Msg_EmptySpace));
		setdialogErr_MSG_EMaild(AtmFinderApplication.getContext().getString(
				R.string.Err_MSG_EMaild));
		setdialogErr_Msg_EmailValid(AtmFinderApplication.getContext()
				.getString(R.string.Err_Msg_EmailValid));
		setdialogErr_MSG_ConfirmEMaild(AtmFinderApplication.getContext()
				.getString(R.string.Err_MSG_ConfirmEMaild));
		setdialogErr_Msg_mobileNumber(AtmFinderApplication.getContext()
				.getString(R.string.Err_Msg_mobileNumber));
		setdialogErr_Msg_MobileValidtaion(AtmFinderApplication.getContext()
				.getString(R.string.Err_Msg_MobileValidtaion));
		setdialogErr_msg_Password(AtmFinderApplication.getContext().getString(
				R.string.Err_msg_Password));
		setdialogErr_msg_ConfirmPassword(AtmFinderApplication.getContext()
				.getString(R.string.Err_msg_ConfirmPassword));
		setdialogErr_Msg_PwdCombMatch(AtmFinderApplication.getContext()
				.getString(R.string.Err_Msg_PwdCombMatch));
		setdialogErr_Msg_ConfirmCombPwdMatch(AtmFinderApplication.getContext()
				.getString(R.string.Err_Msg_ConfirmCombPwdMatch));
		setdialogErr_Msg_PwdLength(AtmFinderApplication.getContext().getString(
				R.string.Err_Msg_PwdLength));
		setdialogErr_Msg_PwdMatch(AtmFinderApplication.getContext().getString(
				R.string.Err_Msg_PwdMatch));
		setdialogErr_Msg_EmailMatch(AtmFinderApplication.getContext()
				.getString(R.string.Err_Msg_EmailMatch));
		setdialogErr_Msg_SwitchTurnOn(AtmFinderApplication.getContext()
				.getString(R.string.Err_Msg_SwitchTurnOn));
		setdialogErr_Msg_PasswordCharactors(AtmFinderApplication.getContext()
				.getString(R.string.Err_Msg_PasswordCharactors));
		setdialogErr_Msg_PasswordConfirmCharactors(AtmFinderApplication
				.getContext().getString(
						R.string.Err_Msg_PasswordConfirmCharactors));
		setdialogErr_Msg_Zipcode(AtmFinderApplication.getContext().getString(
				R.string.Err_Msg_Zipcode));
		setdialogErr_Msg_ZipValidtaion(AtmFinderApplication.getContext()
				.getString(R.string.Err_Msg_ZipValidtaion));
		setdialogErr_Msg_AllInfo(AtmFinderApplication.getContext().getString(
				R.string.Err_Msg_AllInfo));
		setdialogErr_Msg_ValidFIrstName(AtmFinderApplication.getContext()
				.getString(R.string.Err_Msg_ValidFIrstName));
		setdialogErr_Msg_ValidlastName(AtmFinderApplication.getContext()
				.getString(R.string.Err_Msg_ValidlastName));
		setdialogErr_Msg_cardNumber(AtmFinderApplication.getContext()
				.getString(R.string.Err_Msg_cardNumber));
		setdialogErr_Msg_ValidchackcardNumber(AtmFinderApplication.getContext()
				.getString(R.string.Err_Msg_ValidchackcardNumber));
		setdialogErr_Msg_ValidcardNumber(AtmFinderApplication.getContext()
				.getString(R.string.Err_Msg_ValidcardNumber));
		setdialogErr_msg_EnterOldPassword(AtmFinderApplication.getContext()
				.getString(R.string.Err_msg_EnterOldPassword));
		setdialogErr_msg_EnterNewPassword(AtmFinderApplication.getContext()
				.getString(R.string.Err_msg_EnterNewPassword));
		setdialogErr_msg_EnterConfirmPassword(AtmFinderApplication.getContext()
				.getString(R.string.Err_msg_EnterConfirmPassword));
		setdialogErr_Msg_ChangeOldPassword(AtmFinderApplication.getContext()
				.getString(R.string.Err_Msg_ChangeOldPassword));
		setdialogErr_Msg_ChangeNewPassword(AtmFinderApplication.getContext()
				.getString(R.string.Err_Msg_ChangeNewPassword));
		setdialogErr_Msg_ChangeConfirmPassword(AtmFinderApplication
				.getContext().getString(R.string.Err_Msg_ChangeConfirmPassword));

	}

	public static void setSpanish() {
		setFiltersLayoutTitle(AtmFinderApplication.getContext().getString(
				R.string.es_filtersLayoutTitle));
		setFiltersSelectByTitle(AtmFinderApplication.getContext().getString(
				R.string.es_filtersSelectTitle));
		setFiltersBtnDone(AtmFinderApplication.getContext().getString(
				R.string.es_filtersBtnDone));

		// MainMenu strings
		setMainMenuAboutTitle(AtmFinderApplication.getContext().getString(
				R.string.es_mainMenuAboutTitle));
		setMainMenuMessagesTitle(AtmFinderApplication.getContext().getString(
				R.string.es_mainMenuMessagesTitle));
		setMainMenuSearchTitle(AtmFinderApplication.getContext().getString(
				R.string.es_mainMenuSearchTitle));
		setMainMenuSettingsTitle(AtmFinderApplication.getContext().getString(
				R.string.es_mainMenuSettingsTitle));
		setMainMenuScanTitle(AtmFinderApplication.getContext().getString(
				R.string.es_mainMenuScanTitle));

		setMainMenuTransTitle(AtmFinderApplication.getContext().getString(
				R.string.es_mainMenuTransTitle));
		setMainMenuTabHome(AtmFinderApplication.getContext().getString(
				R.string.es_mainMenuHome));
		setMainMenuTabHistory(AtmFinderApplication.getContext().getString(
				R.string.es_mainMenuTransTitle));
		setMainMenuTabMore(AtmFinderApplication.getContext().getString(
				R.string.es_mainMenuMore));

		// login
		setloginEdtUN(AtmFinderApplication.getContext().getString(
				R.string.es_email_hint));
		setloginEdtPW(AtmFinderApplication.getContext().getString(
				R.string.es_password_hint));
		setloginBtnSignIn(AtmFinderApplication.getContext().getString(
				R.string.es_login));
		setloginEdtPW(AtmFinderApplication.getContext().getString(
				R.string.es_password_hint));
		setloginTxtMsg(AtmFinderApplication.getContext().getString(
				R.string.es_account_history));
		setloginBtnSignUp(AtmFinderApplication.getContext().getString(
				R.string.es_signup));
		setloginBtnFgtPw(AtmFinderApplication.getContext().getString(
				R.string.es_forgetPassword));
		setloginBtnSkip(AtmFinderApplication.getContext().getString(
				R.string.es_skip));

		// SignUp
		setSignUpFN(AtmFinderApplication.getContext().getString(
				R.string.es_first_name_hint));
		setsignUpLN(AtmFinderApplication.getContext().getString(
				R.string.es_last_name_hint));
		setsignUpMN(AtmFinderApplication.getContext().getString(
				R.string.es_mobile_hint));
		setsignUpEI(AtmFinderApplication.getContext().getString(
				R.string.es_email));
		setsignUpCEI(AtmFinderApplication.getContext().getString(
				R.string.es_confirm_email));
		setsignUpEMSG(AtmFinderApplication.getContext().getString(
				R.string.es_email_msg));
		setsignUpPZ(AtmFinderApplication.getContext().getString(
				R.string.es_postal_zipcode_hint));
		setsignUpPW(AtmFinderApplication.getContext().getString(
				R.string.es_password_hint));
		setsignUpCPW(AtmFinderApplication.getContext().getString(
				R.string.es_confirm_password_hint));
		setsignUpMsg(AtmFinderApplication.getContext().getString(
				R.string.es_password_msg));
		setsignUpSub(AtmFinderApplication.getContext().getString(
				R.string.es_subscribe));
		setsignUpMsg2(AtmFinderApplication.getContext().getString(
				R.string.es_tc));
		setsignUpCont(AtmFinderApplication.getContext().getString(
				R.string.es_continue_button));

		// Messages strings
		setMessagesLayoutTitle(AtmFinderApplication.getContext().getString(
				R.string.es_messagesLayoutTitle));
		setMessagesBtnDelete(AtmFinderApplication.getContext().getString(
				R.string.es_messagesDeleteMessages));
		setMessagesBtnRead(AtmFinderApplication.getContext().getString(
				R.string.es_messagesReadMessages));
		setMessagesEditTextOff(AtmFinderApplication.getContext().getString(
				R.string.es_messagesEditTextOff));
		setMessagesEditTextOn(AtmFinderApplication.getContext().getString(
				R.string.es_messagesEditTextOn));
		setMessagesNoMessagesText(AtmFinderApplication.getContext().getString(
				R.string.es_messagesNoMessagesText));

		// DetailView strings
		setDetailViewLayoutTitle(AtmFinderApplication.getContext().getString(
				R.string.es_detailViewLayoutTitle));
		setDetailViewServicesTitle(AtmFinderApplication.getContext().getString(
				R.string.es_detailViewServicesTitle));
		setDetailViewHoursTitle(AtmFinderApplication.getContext().getString(
				R.string.es_detailViewHoursTitle));

		// PinView strings
		setPinViewLayoutTitle(AtmFinderApplication.getContext().getString(
				R.string.es_pinViewLayoutTitle));

		// SearchResult strings
		setSearchResultLayoutTitle(AtmFinderApplication.getContext().getString(
				R.string.es_searchResultLayoutTitle));

		// About
		setAboutLayoutTitle(AtmFinderApplication.getContext().getString(
				R.string.es_aboutLayoutTitle));

		// Settings
		setSettingsLayoutTitle(AtmFinderApplication.getContext().getString(
				R.string.es_settingsLayoutTitle));
		setSettingsSearchPreferenceTitle(AtmFinderApplication.getContext()
				.getString(R.string.es_settingsSearchPreferenceTitle));
		setSettingsVisitGooglePlay(AtmFinderApplication.getContext().getString(
				R.string.es_settingsVisitGooglePlay));
		setSettingsLaunchWithNearMeTitle(AtmFinderApplication.getContext()
				.getString(R.string.es_settingsLaunchWithNearMe));
		setSettingsDistanceUnitsTitle(AtmFinderApplication.getContext()
				.getString(R.string.es_settingsDistanceUnits));
		setSettingsDistanceUnitsKm(AtmFinderApplication.getContext().getString(
				R.string.es_settingsDistanceUnitsKm));
		setSettingsDistanceUnitsMiles(AtmFinderApplication.getContext()
				.getString(R.string.es_settingsDistanceUnitsMi));
		setSettingsLanguageTitle(AtmFinderApplication.getContext().getString(
				R.string.es_settingsLanguage));

		setSettingsVersion(AtmFinderApplication.getContext().getString(
				R.string.es_settingsAppVersion));
		setSettingsEditProfile(AtmFinderApplication.getContext().getString(
				R.string.en_settingsEditProfile));

		setSettingsFeedback(AtmFinderApplication.getContext().getString(
				R.string.es_settingsFeedback));
		// setSettingsbtnFeedback(AtmFinderApplication.getContext().getString(R.string.es_settingsFeedback));
		setSettingstvSetGeofence(AtmFinderApplication.getContext().getString(
				R.string.es_settingsGeofenceOnOff));
		setSettingsbtnTermsAndCond(AtmFinderApplication.getContext().getString(
				R.string.es_TermsAndCondtionTitle));
		// Share
		setShareViaTitle(AtmFinderApplication.getContext().getString(
				R.string.es_shareTitle));
		setShareEmailTitle(AtmFinderApplication.getContext().getString(
				R.string.es_shareEmail));
		setShareMessagingTitle(AtmFinderApplication.getContext().getString(
				R.string.es_shareMessaging));

		// Message View
		setMessageLayoutTitle(AtmFinderApplication.getContext().getString(
				R.string.es_messageLayoutTitle));

		// Dialogs

		setDialogCannotGetPosition(AtmFinderApplication.getContext().getString(
				R.string.es_dialogCannotGetPosition));
		setDialogOk(AtmFinderApplication.getContext().getString(
				R.string.es_dialogOk));
		setDialogCancel(AtmFinderApplication.getContext().getString(
				R.string.es_dialogCancel));
		setDialogLoadingTitle(AtmFinderApplication.getContext().getString(
				R.string.es_dialogLoadingTitle));
		setDialogPleaseWait(AtmFinderApplication.getContext().getString(
				R.string.es_dialogPleaseWait));
		setDialogCannotConnectTitle(AtmFinderApplication.getContext()
				.getString(R.string.es_dialogCannotConnectTitle));
		setDialogCannotConnectText(AtmFinderApplication.getContext().getString(
				R.string.es_dialogCannotConnectText));
		setDialogNoResults(AtmFinderApplication.getContext().getString(
				R.string.es_dialogNoResults));
		setDialogSmsNotSupported(AtmFinderApplication.getContext().getString(
				R.string.es_dialogSmsNotSupported));
		setDialogParsingError(AtmFinderApplication.getContext().getString(
				R.string.es_dialogParsingError));
		setDialogUpdate(AtmFinderApplication.getContext().getString(
				R.string.es_dialogUpdate));
		setDialogForceUpdate(AtmFinderApplication.getContext().getString(
				R.string.es_dialogForceUpdate));
		setDialogRemoveMessages(AtmFinderApplication.getContext().getString(
				R.string.es_dialogRemoveMessages));
		setDialogMessageMarked(AtmFinderApplication.getContext().getString(
				R.string.es_dialogMessageMarked));
		setDialogServiceUnavailable(AtmFinderApplication.getContext()
				.getString(R.string.es_dialogServiceUnavailable));
		setDialogPlayServicesUnavailable(AtmFinderApplication.getContext()
				.getString(R.string.es_dialogPlayServicesUnavailable));

		// Sigunp Dialogs
		setdialogErr_Msg_FastName(AtmFinderApplication.getContext().getString(
				R.string.es_Err_Msg_FIrstName));
		setdialogErr_Msg_LsatName(AtmFinderApplication.getContext().getString(
				R.string.es_Err_Msg_LsatName));
		setdialogErr_Msg_EmptySpace(AtmFinderApplication.getContext()
				.getString(R.string.es_Err_Msg_EmptySpace));
		setdialogErr_MSG_EMaild(AtmFinderApplication.getContext().getString(
				R.string.es_Err_MSG_EMaild));
		setdialogErr_Msg_EmailValid(AtmFinderApplication.getContext()
				.getString(R.string.es_Err_Msg_EmailValid));
		setdialogErr_MSG_ConfirmEMaild(AtmFinderApplication.getContext()
				.getString(R.string.es_Err_MSG_ConfirmEMaild));
		setdialogErr_Msg_mobileNumber(AtmFinderApplication.getContext()
				.getString(R.string.es_Err_Msg_mobileNumber));
		setdialogErr_Msg_MobileValidtaion(AtmFinderApplication.getContext()
				.getString(R.string.es_Err_Msg_MobileValidtaion));
		setdialogErr_msg_Password(AtmFinderApplication.getContext().getString(
				R.string.es_Err_msg_Password));
		setdialogErr_msg_ConfirmPassword(AtmFinderApplication.getContext()
				.getString(R.string.es_Err_msg_EnterConfirmPassword));
		setdialogErr_Msg_PwdCombMatch(AtmFinderApplication.getContext()
				.getString(R.string.es_Err_Msg_PwdCombMatch));
		setdialogErr_Msg_ConfirmCombPwdMatch(AtmFinderApplication.getContext()
				.getString(R.string.es_Err_Msg_ConfirmCombPwdMatch));
		setdialogErr_Msg_PwdLength(AtmFinderApplication.getContext().getString(
				R.string.es_Err_Msg_PwdLength));
		setdialogErr_Msg_PwdMatch(AtmFinderApplication.getContext().getString(
				R.string.es_Err_Msg_PwdMatch));
		setdialogErr_Msg_EmailMatch(AtmFinderApplication.getContext()
				.getString(R.string.es_Err_Msg_EmailMatch));
		setdialogErr_Msg_SwitchTurnOn(AtmFinderApplication.getContext()
				.getString(R.string.es_Err_Msg_SwitchTurnOn));
		setdialogErr_Msg_PasswordCharactors(AtmFinderApplication.getContext()
				.getString(R.string.es_Err_Msg_PasswordCharactors));
		setdialogErr_Msg_PasswordConfirmCharactors(AtmFinderApplication
				.getContext().getString(
						R.string.es_Err_Msg_PasswordConfirmCharactors));
		setdialogErr_Msg_Zipcode(AtmFinderApplication.getContext().getString(
				R.string.es_Err_Msg_Zipcode));
		setdialogErr_Msg_ZipValidtaion(AtmFinderApplication.getContext()
				.getString(R.string.es_Err_Msg_ZipValidtaion));
		setdialogErr_Msg_AllInfo(AtmFinderApplication.getContext().getString(
				R.string.es_Err_Msg_AllInfo));
		setdialogErr_Msg_ValidFIrstName(AtmFinderApplication.getContext()
				.getString(R.string.es_Err_Msg_ValidFIrstName));
		setdialogErr_Msg_ValidlastName(AtmFinderApplication.getContext()
				.getString(R.string.es_Err_Msg_ValidlastName));
		setdialogErr_Msg_cardNumber(AtmFinderApplication.getContext()
				.getString(R.string.es_Err_Msg_cardNumber));
		setdialogErr_Msg_ValidchackcardNumber(AtmFinderApplication.getContext()
				.getString(R.string.es_Err_Msg_ValidchackcardNumber));
		setdialogErr_Msg_ValidcardNumber(AtmFinderApplication.getContext()
				.getString(R.string.es_Err_Msg_ValidcardNumber));
		setdialogErr_msg_EnterOldPassword(AtmFinderApplication.getContext()
				.getString(R.string.es_Err_msg_EnterOldPassword));
		setdialogErr_msg_EnterNewPassword(AtmFinderApplication.getContext()
				.getString(R.string.es_Err_msg_EnterNewPassword));
		setdialogErr_msg_EnterConfirmPassword(AtmFinderApplication.getContext()
				.getString(R.string.es_Err_msg_EnterConfirmPassword));
		setdialogErr_Msg_ChangeOldPassword(AtmFinderApplication.getContext()
				.getString(R.string.es_Err_Msg_ChangeOldPassword));
		setdialogErr_Msg_ChangeNewPassword(AtmFinderApplication.getContext()
				.getString(R.string.es_Err_Msg_ChangeNewPassword));
		setdialogErr_Msg_ChangeConfirmPassword(AtmFinderApplication
				.getContext().getString(
						R.string.es_Err_Msg_ChangeConfirmPassword));

		setlogin(AtmFinderApplication.getContext().getString(R.string.es_login));
		setlogout(AtmFinderApplication.getContext().getString(
				R.string.es_settingslogout));
		setsignup(AtmFinderApplication.getContext().getString(
				R.string.es_signup_title));
		setcont(AtmFinderApplication.getContext().getString(
				R.string.es_continue_button));
		setcancel(AtmFinderApplication.getContext().getString(
				R.string.es_cancel_button));
	}

	private static void setFiltersLayoutTitle(final String filtersLayoutTitle) {
		Localization.filtersLayoutTitle = filtersLayoutTitle;
	}

	private static void setFiltersSelectByTitle(
			final String filtersSelectByTitle) {
		Localization.filtersSelectByTitle = filtersSelectByTitle;
	}

	private static void setFiltersBtnDone(final String filtersBtnDone) {
		Localization.filtersBtnDone = filtersBtnDone;
	}

	private static void setMainMenuSearchTitle(final String mainMenuSearchTitle) {
		Localization.mainMenuSearchTitle = mainMenuSearchTitle;
	}

	private static void setMainMenuMessagesTitle(
			final String mainMenuMessagesTitle) {
		Localization.mainMenuMessagesTitle = mainMenuMessagesTitle;
	}

	private static void setMainMenuSettingsTitle(
			final String mainMenuSettingsTitle) {
		Localization.mainMenuSettingsTitle = mainMenuSettingsTitle;
	}

	private static void setMainMenuAboutTitle(final String mainMenuAboutTitle) {
		Localization.mainMenuAboutTitle = mainMenuAboutTitle;
	}

	private static void setMainMenuScanTitle(final String mainMenuScanTitle) {
		Localization.mainMenuScanTitle = mainMenuScanTitle;
	}

	private static void setMainMenuTransTitle(final String mainMenuTransTitle) {
		Localization.mainMenuTransTitle = mainMenuTransTitle;
	}

	// Main Menu Tabs
	private static void setMainMenuTabHome(final String mainMenuTabHomeIs) {
		Localization.mainMenuTabHome = mainMenuTabHomeIs;
	}

	public static String getMainMenuTabHome() {
		return mainMenuTabHome;
	}

	private static void setMainMenuTabHistory(final String mainMenuTabHistoryIs) {
		Localization.mainMenuTabHistory = mainMenuTabHistoryIs;
	}

	public static String getMainMenuTabHistory() {
		return mainMenuTabHistory;
	}

	private static void setMainMenuTabMore(final String mainMenuTabMoreIs) {
		Localization.mainMenuTabMore = mainMenuTabMoreIs;
	}

	public static String getMainMenuTabMore() {
		return mainMenuTabMore;
	}

	private static void setMessagesLayoutTitle(final String messagesLayoutTitle) {
		Localization.messagesLayoutTitle = messagesLayoutTitle;
	}

	private static void setDetailViewLayoutTitle(
			final String detailViewLayoutTitle) {
		Localization.detailViewLayoutTitle = detailViewLayoutTitle;
	}

	private static void setDetailViewServicesTitle(
			final String detailViewServicesTitle) {
		Localization.detailViewServicesTitle = detailViewServicesTitle;
	}

	private static void setSettingsLayoutTitle(final String settingsLayoutTitle) {
		Localization.settingsLayoutTitle = settingsLayoutTitle;
	}

	private static void setSettingsSearchPreferenceTitle(
			final String settingsSearchPreferenceTitle) {
		Localization.settingsSearchPreferenceTitle = settingsSearchPreferenceTitle;
	}

	private static void setSettingsVisitGooglePlay(
			final String settingsVisitGooglePlay) {
		Localization.settingsVisitGooglePlay = settingsVisitGooglePlay;
	}

	private static void setSettingsLaunchWithNearMeTitle(
			final String settingsLaunchWithNearMeTitle) {
		Localization.settingsLaunchWithNearMeTitle = settingsLaunchWithNearMeTitle;
	}

	private static void setSettingsDistanceUnitsTitle(
			final String settingsDistanceUnitsTitle) {
		Localization.settingsDistanceUnitsTitle = settingsDistanceUnitsTitle;
	}

	private static void setSettingsLanguageTitle(
			final String settingsLanguageTitle) {
		Localization.settingsLanguageTitle = settingsLanguageTitle;
	}

	private static void setSettingsFeedback(final String settingsFeedback) {
		Localization.settingsFeedback = settingsFeedback;
	}

	private static void setSettingsEditProfile(final String settingsEditProfile) {
		Localization.settingsEditProfile = settingsEditProfile;
	}

	private static void setSettingsbtnFeedback(final String btnFeedbackIs) {
		Localization.btnFeedback = btnFeedbackIs;
	}

	public static String getSettingsbtnFeedback() {
		return btnFeedback;
	}

	private static void setSettingstvSetGeofence(final String tvSetGeofenceIs) {
		Localization.btnFeedback = tvSetGeofenceIs;
	}

	public static String getSettingstvSetGeofence() {
		return tvSetGeofence;
	}

	private static void setSettingsbtnTermsAndCond(
			final String btnTermsAndCondIs) {
		Localization.btnTermsAndCond = btnTermsAndCondIs;
	}

	public static String getSettingsbtnTermsAndCond() {
		return btnTermsAndCond;
	}

	private static void setShareViaTitle(final String shareViaTitle) {
		Localization.shareViaTitle = shareViaTitle;
	}

	private static void setShareEmailTitle(final String shareEmailTitle) {
		Localization.shareEmailTitle = shareEmailTitle;
	}

	private static void setShareMessagingTitle(final String shareMessagingTitle) {
		Localization.shareMessagingTitle = shareMessagingTitle;
	}

	private static void setMessageLayoutTitle(final String messageLayoutTitle) {
		Localization.messageLayoutTitle = messageLayoutTitle;
	}

	private static void setDialogCannotGetPosition(
			final String dialogCannotGetPosition) {
		Localization.dialogCannotGetPosition = dialogCannotGetPosition;
	}

	private static void setDialogOk(final String dialogOk) {
		Localization.dialogOk = dialogOk;
	}

	private static void setDialogLoadingTitle(final String dialogLoadingTitle) {
		Localization.dialogLoadingTitle = dialogLoadingTitle;
	}

	private static void setDialogPleaseWait(final String dialogPleaseWait) {
		Localization.dialogPleaseWait = dialogPleaseWait;
	}

	private static void setDialogCannotConnectText(
			final String dialogCannotConnectText) {
		Localization.dialogCannotConnectText = dialogCannotConnectText;
	}

	private static void setDialogNoResults(final String dialogNoResults) {
		Localization.dialogNoResults = dialogNoResults;
	}

	private static void setDialogParsingError(final String dialogParsingError) {
		Localization.dialogParsingError = dialogParsingError;
	}

	private static void setDetailViewHoursTitle(
			final String detailViewHoursTitle) {
		Localization.detailViewHoursTitle = detailViewHoursTitle;
	}

	private static void setDialogUpdate(final String dialogUpdate) {
		Localization.dialogUpdate = dialogUpdate;
	}

	private static void setDialogForceUpdate(final String dialogForceUpdate) {
		Localization.dialogForceUpdate = dialogForceUpdate;
	}

	private static void setDialogCancel(final String dialogCancel) {
		Localization.dialogCancel = dialogCancel;
	}

	private static void setSearchResultLayoutTitle(
			final String searchResultLayoutTitle) {
		Localization.searchResultLayoutTitle = searchResultLayoutTitle;
	}

	private static void setPinViewLayoutTitle(final String pinViewLayoutTitle) {
		Localization.pinViewLayoutTitle = pinViewLayoutTitle;
	}

	private static void setDialogCannotConnectTitle(
			final String dialogCannotConnectTitle) {
		Localization.dialogCannotConnectTitle = dialogCannotConnectTitle;
	}

	private static void setDialogRemoveMessages(
			final String dialogRemoveMessages) {
		Localization.dialogRemoveMessages = dialogRemoveMessages;
	}

	private static void setMessagesNoMessagesText(
			final String messagesNoMessagesText) {
		Localization.messagesNoMessagesText = messagesNoMessagesText;
	}

	private static void setMessagesBtnDelete(final String messagesBtnDelete) {
		Localization.messagesBtnDelete = messagesBtnDelete;
	}

	private static void setMessagesBtnRead(final String messagesBtnRead) {
		Localization.messagesBtnRead = messagesBtnRead;
	}

	private static void setMessagesEditTextOn(final String messagesEditTextOn) {
		Localization.messagesEditTextOn = messagesEditTextOn;
	}

	private static void setMessagesEditTextOff(final String messagesEditTextOff) {
		Localization.messagesEditTextOff = messagesEditTextOff;
	}

	private static void setSettingsDistanceUnitsKm(
			final String settingsDistanceUnitsKm) {
		Localization.settingsDistanceUnitsKm = settingsDistanceUnitsKm;
	}

	private static void setSettingsDistanceUnitsMiles(
			final String settingsDistanceUnitsMiles) {
		Localization.settingsDistanceUnitsMiles = settingsDistanceUnitsMiles;
	}

	private static void setSettingsVersion(final String settingsVersion) {
		Localization.settingsVersion = settingsVersion;
	}

	private static void setDialogMessageMarked(final String dialogMessageMarked) {
		Localization.dialogMessageMarked = dialogMessageMarked;
	}

	private static void setAboutLayoutTitle(final String aboutLayoutTitle) {
		Localization.aboutLayoutTitle = aboutLayoutTitle;
	}

	private static void setDialogSmsNotSupported(
			final String dialogSmsNotSupported) {
		Localization.dialogSmsNotSupported = dialogSmsNotSupported;
	}

	public static String getFiltersLayoutTitle() {
		return filtersLayoutTitle;
	}

	public static String getFiltersSelectByTitle() {
		return filtersSelectByTitle;
	}

	public static String getFiltersBtnDone() {
		return filtersBtnDone;
	}

	public static String getMainMenuSearchTitle() {
		return mainMenuSearchTitle;
	}

	public static String getMainMenuMessagesTitle() {
		return mainMenuMessagesTitle;
	}

	public static String getMainMenuSettingsTitle() {
		return mainMenuSettingsTitle;
	}

	public static String getMainMenuScanTitle() {
		return mainMenuScanTitle;
	}

	public static String getMainMenuTransTitle() {
		return mainMenuTransTitle;
	}

	public static String getMainMenuAboutTitle() {
		return mainMenuAboutTitle;
	}

	public static String getMessagesLayoutTitle() {
		return messagesLayoutTitle;
	}

	public static String getDetailViewLayoutTitle() {
		return detailViewLayoutTitle;
	}

	public static String getDetailViewServicesTitle() {
		return detailViewServicesTitle;
	}

	public static String getSettingsLayoutTitle() {
		return settingsLayoutTitle;
	}

	public static String getSettingsSearchPreferenceTitle() {
		return settingsSearchPreferenceTitle;
	}

	public static String getSettingsVisitGooglePlay() {
		return settingsVisitGooglePlay;
	}

	public static String getSettingsLaunchWithNearMeTitle() {
		return settingsLaunchWithNearMeTitle;
	}

	public static String getSettingsDistanceUnitsTitle() {
		return settingsDistanceUnitsTitle;
	}

	public static String getSettingsLanguageTitle() {
		return settingsLanguageTitle;
	}

	public static String getSettingsFeedback() {
		return settingsFeedback;
	}

	public static String getSettingsEditProfile() {
		return settingsEditProfile;
	}

	public static String getShareViaTitle() {
		return shareViaTitle;
	}

	public static String getShareEmailTitle() {
		return shareEmailTitle;
	}

	public static String getShareMessagingTitle() {
		return shareMessagingTitle;
	}

	public static String getDialogCannotGetPosition() {
		return dialogCannotGetPosition;
	}

	public static String getDialogOk() {
		return dialogOk;
	}

	public static String getDialogLoadingTitle() {
		return dialogLoadingTitle;
	}

	public static String getDialogPleaseWait() {
		return dialogPleaseWait;
	}

	public static String getDialogCannotConnectText() {
		return dialogCannotConnectText;
	}

	public static String getDialogNoResults() {
		return dialogNoResults;
	}

	public static String getDialogParsingError() {
		return dialogParsingError;
	}

	public static String getDetailViewHoursTitle() {
		return detailViewHoursTitle;
	}

	public static String getDialogUpdate() {
		return dialogUpdate;
	}

	public static String getDialogForceUpdate() {
		return dialogForceUpdate;
	}

	public static String getDialogCancel() {
		return dialogCancel;
	}

	public static String getSearchResultLayoutTitle() {
		return searchResultLayoutTitle;
	}

	public static String getPinViewLayoutTitle() {
		return pinViewLayoutTitle;
	}

	public static String getDialogCannotConnectTitle() {
		return dialogCannotConnectTitle;
	}

	public static String getDialogRemoveMessages() {
		return dialogRemoveMessages;
	}

	public static String getMessagesNoMessagesText() {
		return messagesNoMessagesText;
	}

	public static String getMessagesBtnDelete() {
		return messagesBtnDelete;
	}

	public static String getMessagesBtnRead() {
		return messagesBtnRead;
	}

	public static String getMessagesEditTextOn() {
		return messagesEditTextOn;
	}

	public static String getMessagesEditTextOff() {
		return messagesEditTextOff;
	}

	public static String getSettingsDistanceUnitsKm() {
		return settingsDistanceUnitsKm;
	}

	public static String getSettingsDistanceUnitsMiles() {
		return settingsDistanceUnitsMiles;
	}

	public static String getSettingsVersion() {
		return settingsVersion;
	}

	public static String getMessageLayoutTitle() {
		return messageLayoutTitle;
	}

	public static String getDialogMessageMarked() {
		return dialogMessageMarked;
	}

	public static String getAboutLayoutTitle() {
		return aboutLayoutTitle;
	}

	public static String getDialogSmsNotSupported() {
		return dialogSmsNotSupported;
	}

	public static String getDialogServiceUnavailable() {
		return dialogServiceUnavailable;
	}

	private static void setDialogServiceUnavailable(
			String dialogServiceUnavailable) {
		Localization.dialogServiceUnavailable = dialogServiceUnavailable;
	}

	public static String getDialogPlayServicesUnavailable() {
		return dialogPlayServicesUnavailable;
	}

	public static void setDialogPlayServicesUnavailable(
			final String dialogPlayServicesUnavailable) {
		Localization.dialogPlayServicesUnavailable = dialogPlayServicesUnavailable;
	}

	// sign Up
	// Sign Up
	private static void setSignUpFN(final String signUpFNIs) {
		Localization.signUpFN = signUpFNIs;
	}

	public static String getSignUpFN() {
		return signUpFN;
	}

	private static void setsignUpLN(final String signUpLNIs) {
		Localization.signUpLN = signUpLNIs;
	}

	public static String getsignUpLN() {
		return signUpLN;
	}

	private static void setsignUpMN(final String signUpMNIs) {
		Localization.signUpMN = signUpMNIs;
	}

	public static String getsignUpMN() {
		return signUpMN;
	}

	private static void setsignUpEI(final String signUpEIIs) {
		Localization.signUpEI = signUpEIIs;
	}

	public static String getsignUpEI() {
		return signUpEI;
	}

	private static void setsignUpCEI(final String signUpCEIIs) {
		Localization.signUpCEI = signUpCEIIs;
	}

	public static String getsignUpCEI() {
		return signUpCEI;
	}

	private static void setsignUpEMSG(final String signUpEMSGIs) {
		Localization.signUpEMSG = signUpEMSGIs;
	}

	public static String getsignUpEMSG() {
		return signUpEMSG;
	}

	private static void setsignUpPZ(final String signUpPZIs) {
		Localization.signUpPZ = signUpPZIs;
	}

	public static String getsignUpPZ() {
		return signUpPZ;
	}

	private static void setsignUpPW(final String signUpPWIs) {
		Localization.signUpPW = signUpPWIs;
	}

	public static String getsignUpPW() {
		return signUpPW;
	}

	private static void setsignUpCPW(final String signUpCPWIs) {
		Localization.signUpCPW = signUpCPWIs;
	}

	public static String getsignUpCPW() {
		return signUpCPW;
	}

	private static void setsignUpMsg(final String signUpMsgIs) {
		Localization.signUpMsg = signUpMsgIs;
	}

	public static String getsignUpMsg() {
		return signUpMsg;
	}

	private static void setsignUpSub(final String signUpSubIs) {
		Localization.signUpSub = signUpSubIs;
	}

	public static String getsignUpSub() {
		return signUpSub;
	}

	private static void setsignUpMsg2(final String signUpTCIs) {
		Localization.signUpTC = signUpTCIs;
	}

	public static String getsignUpMsg2() {
		return signUpTC;
	}

	private static void setsignUpCont(final String signUpContIs) {
		Localization.signUpCont = signUpContIs;
	}

	public static String getsignUpCont() {
		return signUpCont;
	}

	// Login
	private static void setloginEdtUN(final String loginEdtUNIs) {
		Localization.loginEdtUN = loginEdtUNIs;
	}

	public static String getloginEdtUN() {
		return loginEdtUN;
	}

	private static void setloginEdtPW(final String loginEdtPWIs) {
		Localization.loginEdtPW = loginEdtPWIs;
	}

	public static String getloginEdtPW() {
		return loginEdtPW;
	}

	private static void setloginBtnSignIn(final String loginBtnSignInIs) {
		Localization.loginBtnSignIn = loginBtnSignInIs;
	}

	public static String getloginBtnSignIn() {
		return loginBtnSignIn;
	}

	private static void setloginTxtMsg(final String loginTxtMsgIs) {
		Localization.loginTxtMsg = loginTxtMsgIs;
	}

	public static String getloginTxtMsg() {
		return loginTxtMsg;
	}

	private static void setloginBtnSignUp(final String loginBtnSignUpIs) {
		Localization.loginBtnSignUp = loginBtnSignUpIs;
	}

	public static String getloginBtnSignUp() {
		return loginBtnSignUp;
	}

	private static void setloginBtnFgtPw(final String loginBtnFgtPwIs) {
		Localization.loginBtnFgtPw = loginBtnFgtPwIs;
	}

	public static String getloginBtnFgtPw() {
		return loginBtnFgtPw;
	}

	private static void setloginBtnSkip(final String loginBtnSkipIs) {
		Localization.loginBtnSkip = loginBtnSkipIs;
	}

	public static String getloginBtnSkip() {
		return loginBtnSkip;
	}

	// Dialogs
	private static void setdialogErr_Msg_FastName(
			final String dialogErr_Msg_FastNameIs) {
		Localization.dialogErr_Msg_FastName = dialogErr_Msg_FastNameIs;
	}

	public static String getdialogErr_Msg_FastName() {
		return dialogErr_Msg_FastName;
	}

	private static void setdialogErr_Msg_LsatName(
			final String dialogErr_Msg_LsatNameIs) {
		Localization.dialogErr_Msg_LsatName = dialogErr_Msg_LsatNameIs;
	}

	public static String getdialogErr_Msg_LsatName() {
		return dialogErr_Msg_LsatName;
	}

	private static void setdialogErr_Msg_EmptySpace(
			final String dialogErr_Msg_EmptySpaceIs) {
		Localization.dialogErr_Msg_EmptySpace = dialogErr_Msg_EmptySpaceIs;
	}

	public static String getdialogErr_Msg_EmptySpace() {
		return dialogErr_Msg_EmptySpace;
	}

	private static void setdialogErr_MSG_EMaild(
			final String dialogErr_MSG_EMaildIs) {
		Localization.dialogErr_MSG_EMaild = dialogErr_MSG_EMaildIs;
	}

	public static String getdialogErr_MSG_EMaild() {
		return dialogErr_MSG_EMaild;
	}

	private static void setdialogErr_Msg_EmailValid(
			final String dialogErr_Msg_EmailValidIs) {
		Localization.dialogErr_Msg_EmailValid = dialogErr_Msg_EmailValidIs;
	}

	public static String getdialogErr_Msg_EmailValid() {
		return dialogErr_MSG_EMaild;
	}

	private static void setdialogErr_MSG_ConfirmEMaild(
			final String dialogErr_MSG_ConfirmEMaildIs) {
		Localization.dialogErr_MSG_ConfirmEMaild = dialogErr_MSG_ConfirmEMaildIs;
	}

	public static String getdialogErr_MSG_ConfirmEMaild() {
		return dialogErr_MSG_EMaild;
	}

	private static void setdialogErr_Msg_mobileNumber(
			final String dialogErr_Msg_mobileNumberIs) {
		Localization.dialogErr_Msg_mobileNumber = dialogErr_Msg_mobileNumberIs;
	}

	public static String getdialogErr_Msg_mobileNumber() {
		return dialogErr_Msg_mobileNumber;
	}

	private static void setdialogErr_Msg_MobileValidtaion(
			final String dialogErr_Msg_MobileValidtaionIs) {
		Localization.dialogErr_Msg_MobileValidtaion = dialogErr_Msg_MobileValidtaionIs;
	}

	public static String getdialogErr_Msg_MobileValidtaion() {
		return dialogErr_Msg_MobileValidtaion;
	}

	private static void setdialogErr_msg_Password(
			final String dialogErr_msg_PasswordIs) {
		Localization.dialogErr_msg_Password = dialogErr_msg_PasswordIs;
	}

	public static String getdialogErr_msg_Password() {
		return dialogErr_msg_Password;
	}

	private static void setdialogErr_msg_ConfirmPassword(
			final String dialogErr_msg_ConfirmPasswordIs) {
		Localization.dialogErr_msg_ConfirmPassword = dialogErr_msg_ConfirmPasswordIs;
	}

	public static String getdialogErr_msg_ConfirmPassword() {
		return dialogErr_msg_ConfirmPassword;
	}

	private static void setdialogErr_msg_ConfrimPassword(
			final String dialogErr_msg_ConfrimPasswordIs) {
		Localization.dialogErr_msg_ConfrimPassword = dialogErr_msg_ConfrimPasswordIs;
	}

	public static String getdialogErr_msg_ConfrimPassword() {
		return dialogErr_msg_ConfrimPassword;
	}

	private static void setdialogErr_Msg_PwdCombMatch(
			final String dialogErr_Msg_PwdCombMatchIs) {
		Localization.dialogErr_Msg_PwdCombMatch = dialogErr_Msg_PwdCombMatchIs;
	}

	public static String getdialogErr_Msg_PwdCombMatch() {
		return dialogErr_Msg_PwdCombMatch;
	}

	private static void setdialogErr_Msg_ConfirmCombPwdMatch(
			final String dialogErr_Msg_ConfirmCombPwdMatchIs) {
		Localization.dialogErr_Msg_ConfirmCombPwdMatch = dialogErr_Msg_ConfirmCombPwdMatchIs;
	}

	public static String getdialogErr_Msg_ConfirmCombPwdMatch() {
		return dialogErr_Msg_ConfirmCombPwdMatch;
	}

	private static void setdialogErr_Msg_PwdLength(
			final String dialogErr_Msg_PwdLengthIs) {
		Localization.dialogErr_Msg_PwdLength = dialogErr_Msg_PwdLengthIs;
	}

	public static String getdialogErr_Msg_PwdLength() {
		return dialogErr_Msg_PwdLength;
	}

	private static void setdialogErr_Msg_PwdMatch(
			final String dialogErr_Msg_PwdMatchIs) {
		Localization.dialogErr_Msg_PwdMatch = dialogErr_Msg_PwdMatchIs;
	}

	public static String getdialogErr_Msg_PwdMatch() {
		return dialogErr_Msg_PwdMatch;
	}

	private static void setdialogErr_Msg_EmailMatch(
			final String dialogErr_Msg_EmailMatchIs) {
		Localization.dialogErr_Msg_EmailMatch = dialogErr_Msg_EmailMatchIs;
	}

	public static String getdialogErr_Msg_EmailMatch() {
		return dialogErr_Msg_EmailMatch;
	}

	private static void setdialogErr_Msg_SwitchTurnOn(
			final String dialogErr_Msg_SwitchTurnOnIs) {
		Localization.dialogErr_Msg_SwitchTurnOn = dialogErr_Msg_SwitchTurnOnIs;
	}

	public static String getdialogErr_Msg_SwitchTurnOn() {
		return dialogErr_Msg_SwitchTurnOn;
	}

	private static void setdialogErr_Msg_PasswordCharactors(
			final String dialogErr_Msg_PasswordCharactorsIs) {
		Localization.dialogErr_Msg_PasswordCharactors = dialogErr_Msg_PasswordCharactorsIs;
	}

	public static String getdialogErr_Msg_PasswordCharactors() {
		return dialogErr_Msg_PasswordCharactors;
	}

	private static void setdialogErr_Msg_PasswordConfirmCharactors(
			final String dialogErr_Msg_PasswordConfirmCharactorsIs) {
		Localization.dialogErr_Msg_PasswordConfirmCharactors = dialogErr_Msg_PasswordConfirmCharactorsIs;
	}

	public static String getdialogErr_Msg_PasswordConfirmCharactors() {
		return dialogErr_Msg_PasswordConfirmCharactors;
	}

	private static void setdialogErr_Msg_Zipcode(
			final String dialogErr_Msg_ZipcodeIs) {
		Localization.dialogErr_Msg_Zipcode = dialogErr_Msg_ZipcodeIs;
	}

	public static String getdialogErr_Msg_Zipcode() {
		return dialogErr_Msg_Zipcode;
	}

	private static void setdialogErr_Msg_ZipValidtaion(
			final String dialogErr_Msg_ZipValidtaionIs) {
		Localization.dialogErr_Msg_ZipValidtaion = dialogErr_Msg_ZipValidtaionIs;
	}

	public static String getdialogErr_Msg_ZipValidtaion() {
		return dialogErr_Msg_ZipValidtaion;
	}

	private static void setdialogErr_Msg_AllInfo(
			final String dialogErr_Msg_AllInfoIs) {
		Localization.dialogErr_Msg_AllInfo = dialogErr_Msg_AllInfoIs;
	}

	public static String getdialogErr_Msg_AllInfo() {
		return dialogErr_Msg_AllInfo;
	}

	private static void setdialogErr_Msg_ValidFIrstName(
			final String dialogErr_Msg_ValidFIrstNameIs) {
		Localization.dialogErr_Msg_ValidFIrstName = dialogErr_Msg_ValidFIrstNameIs;
	}

	public static String getdialogErr_Msg_ValidFIrstName() {
		return dialogErr_Msg_ValidFIrstName;
	}

	private static void setdialogErr_Msg_ValidlastName(
			final String dialogErr_Msg_ValidlastNameIs) {
		Localization.dialogErr_Msg_ValidlastName = dialogErr_Msg_ValidlastNameIs;
	}

	public static String getdialogErr_Msg_ValidlastName() {
		return dialogErr_Msg_ValidlastName;
	}

	private static void setdialogErr_Msg_cardNumber(
			final String dialogErr_Msg_cardNumberIs) {
		Localization.dialogErr_Msg_cardNumber = dialogErr_Msg_cardNumberIs;
	}

	public static String getdialogErr_Msg_cardNumber() {
		return dialogErr_Msg_cardNumber;
	}

	private static void setdialogErr_Msg_ValidchackcardNumber(
			final String dialogErr_Msg_ValidchackcardNumberIs) {
		Localization.dialogErr_Msg_ValidchackcardNumber = dialogErr_Msg_ValidchackcardNumberIs;
	}

	public static String getdialogErr_Msg_ValidchackcardNumber() {
		return dialogErr_Msg_ValidchackcardNumber;
	}

	private static void setdialogErr_Msg_ValidcardNumber(
			final String dialogErr_Msg_ValidcardNumberIs) {
		Localization.dialogErr_Msg_ValidcardNumber = dialogErr_Msg_ValidcardNumberIs;
	}

	public static String getdialogErr_Msg_ValidcardNumber() {
		return dialogErr_Msg_ValidcardNumber;
	}

	private static void setdialogErr_msg_EnterOldPassword(
			final String dialogErr_msg_EnterOldPasswordIs) {
		Localization.dialogErr_msg_EnterOldPassword = dialogErr_msg_EnterOldPasswordIs;
	}

	public static String getdialogErr_msg_EnterOldPassword() {
		return dialogErr_msg_EnterOldPassword;
	}

	private static void setdialogErr_msg_EnterNewPassword(
			final String dialogErr_msg_EnterNewPasswordIs) {
		Localization.dialogErr_msg_EnterNewPassword = dialogErr_msg_EnterNewPasswordIs;
	}

	public static String getdialogErr_msg_EnterNewPassword() {
		return dialogErr_msg_EnterNewPassword;
	}

	private static void setdialogErr_msg_EnterConfirmPassword(
			final String dialogErr_msg_EnterConfirmPasswordIs) {
		Localization.dialogErr_msg_EnterNewPassword = dialogErr_msg_EnterConfirmPasswordIs;
	}

	public static String getdialogErr_msg_EnterConfirmPassword() {
		return dialogErr_msg_EnterConfirmPassword;
	}

	private static void setdialogErr_Msg_ChangeOldPassword(
			final String dialogErr_Msg_ChangeOldPasswordIs) {
		Localization.dialogErr_Msg_ChangeOldPassword = dialogErr_Msg_ChangeOldPasswordIs;
	}

	public static String getdialogErr_Msg_ChangeOldPassword() {
		return dialogErr_Msg_ChangeOldPassword;
	}

	private static void setdialogErr_Msg_ChangeNewPassword(
			final String dialogErr_Msg_ChangeNewPasswordIs) {
		Localization.dialogErr_Msg_ChangeNewPassword = dialogErr_Msg_ChangeNewPasswordIs;
	}

	public static String getdialogErr_Msg_ChangeNewPassword() {
		return dialogErr_Msg_ChangeNewPassword;
	}

	private static void setdialogErr_Msg_ChangeConfirmPassword(
			final String dialogErr_Msg_ChangeConfirmPasswordIs) {
		Localization.dialogErr_Msg_ChangeNewPassword = dialogErr_Msg_ChangeConfirmPasswordIs;
	}

	public static String getdialogErr_Msg_ChangeConfirmPassword() {
		return dialogErr_Msg_ChangeConfirmPassword;
	}

	// top bar

	private static void setcancel(final String cancelIs) {
		Localization.cancel = cancelIs;
	}

	public static String getcancel() {
		return cancel;
	}

	private static void setlogin(final String loginIs) {
		Localization.login = loginIs;
	}

	public static String getlogin() {
		return login;
	}

	private static void setlogout(final String logoutIs) {
		Localization.logout = logoutIs;
	}

	public static String getlogout() {
		return logout;
	}

	private static void setsignup(final String signupIs) {
		Localization.signup = signupIs;
	}

	public static String getsignup() {
		return signup;
	}

	private static void setcont(final String contIs) {
		Localization.cont = contIs;
	}

	public static String getcont() {
		return cont;
	}
}
