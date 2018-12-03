/**
 * @ Constant
 */
package com.allpoint.util;

/**
 * Constant
 *
 * @author: Vyacheslav.Shmakin
 * @version: 08.01.14
 */
public class Constant {

    /****************************** Flurry- API KEYS ************************************/
    //production
    public static final String FLURRY_API_KEY = "F4J818ED9PT38DR7GEDC";
    //public static final String FCM_SENDER_ID = "562530097340"; // Dev key from Opus team
    public static final String FCM_SENDER_ID = "142515658605";


    /****************************** Gimble- API KEYS ************************************/
    //provided by sim
    public static String GIMBALE_API_KEY = "779d659c-592b-4bf8-b073-1a6e35b23e0e";

    /****************************** BugSense Configuration- API KEYS ************************************/

    public static final String BUG_SENSE_API_KEY = "4cfb0db2";

    /****************************** Urban Airship Configuration - API KEYS - Prod ************************************/

    public static final String URBAN_AIRSHIP_GCM_SENDER = "999162873539";// old 458890305629

    public static final String URBAN_AIRSHIP_TRANSPORT = "gcm";

    public static final String URBAN_AIRSHIP_DEVELOPMENT_APP_KEY = "LMGnQmgmTXWTp3B9ObbJrQ";
    public static final String URBAN_AIRSHIP_DEVELOPMENT_APP_SECRET = "c1lBIw6OQU6wB2taEjYuEw";

    public static final String URBAN_AIRSHIP_PRODUCTION_APP_KEY = "8415DQI9TRigmwk4udjCDg";
    public static final String URBAN_AIRSHIP_PRODUCTION_APP_SECRET = "1UAgYBRCS36rTlT_3wc2iQ";
    public static final boolean URBAN_AIRSHIP_IN_PRODUCTION = true;
    public static final boolean URBAN_AIRSHIP_RICH_PUSH_ENABLED = true;

    /****************************** Customer's URLs ************************************/

    public static final String ALLPOINT_ABOUT_URL = "http://m.allpointnetwork.com/aboutus.aspx";
    public static final String ALLPOINT_FEEDBACK_MAIL = "Info@AllpointNetwork.com";

    public static final String ALLPOINT_TERMS_CONDTION_URL = "https://www.allpointnetwork.com/terms.aspx";

    /****************************** Login HTTPS Username & Password ************************************/

    /* History Remove */
    public static final boolean HISTORY_BUTTON_DISABLE = true;


    /****************************** Server Host Details & Links ************************************/

    public static final String HOSTNAME = Constant.LIVE_SERVER_HOSTNAME; // Link to Live before

    // Customer managment Host & Host Link
    //public static final String LIVE_SERVER_HOSTNAME = "allpointmobile.cardtronicsdata.com";
    public static final String LIVE_SERVER_HOSTNAME = "10.240.41.84";
//10.240.41.84
    //10.240.42.122 dev
//	public static final String DEV_SERVER_HOSTNAME = "10.240.42.56";
//	public static final String QC_SERVER_HOSTNAME = "10.240.41.197";


    /****************************** PRODUCTION HTTPS Username & Password ********************/
    //public static final String LOGIN_USER_NAME = "AllPointMobileApiUser";
    //public static final String LOGIN_USER_PASSWORD = "Cards@1234";

    /****************************** PRODUCTION NAMESPACE ************************************/
    public static final String EDW_NAMESPACE = "http://AllPointMobile/AddCustomerLocationService/";
    public static final String CUSTOMER_MANAGEMENT_NAMESPACE = "http://AllPointMobile/CustomerManagementService/";
    public static final String CUSTOMER_MANAGEMENT_TRANS_NAMESPACE = "http://AllPointMobile/TransactionService/";

    /****************************** PRODUCTION URLS ************************************/
    public static final String EDW_LINK = "/Services/GeoFenceService.svc/GeoFenceService.svc";
    public static final String CUSTOMER_MANAGEMENT_URL = "/Services/CustomerManagementService.svc/CustomerManagementService.svc";
    public static final String CUSTOMER_MANAGEMENT_TRANS_URL = "/Services/TransactionService.svc/TransactionService.svc";


    /****************************** PEN TESTING HTTPS Username & Password ********************/
    public static final String LOGIN_USER_NAME = "allpointmobileapi@opussoft.com";
    public static final String LOGIN_USER_PASSWORD = "allpointmobileapi@opussoft.com";

    /****************************** PEN TESTING URLS ************************************/
//	public static final String EDW_LINK = "/AllPointMobileServicePENTesting/Services/GeoFenceService.svc/GeoFenceService.svc";
//	public static final String CUSTOMER_MANAGEMENT_URL = "AllPointMobileServicePENTesting/Services/CustomerManagementService.svc/CustomerManagementService.svc";
//	public static final String CUSTOMER_MANAGEMENT_TRANS_URL = "/AllPointMobileServicePENTesting/Services/TransactionService.svc/TransactionService.svc";

    /****************************** Links - EDW Server ************************************/
    public static final String EDW_SOAP_ACTION = "http://AllPointMobile/AddCustomerLocationService/ICustomerGeoFence/AddCustomerLocation";
    public static final String EDW_METHOD_NAME = "AddCustomerLocation";

    /****************************** CUSTOMER_MANAGEMENT - Login ************************************/

    public static final String LOGIN_SOAP_ACTION = "http://AllPointMobile/CustomerManagementService/ICustomerManagement/LoginUser";
    public static final String LOGIN_METHOD_NAME = "LoginUser";

    /****************************** CUSTOMER_MANAGEMENT - Registraion ************************************/

    public static final String REG_SOAP_ACTION = "http://AllPointMobile/CustomerManagementService/ICustomerManagement/RegisterUser";
    public static final String REG_METHOD_NAME = "RegisterUser";

    /****************************** CUSTOMER_MANAGEMENT - OTT token From Registraion ************************************/

    public static final String OTT_RESEND_REG_SOAP_ACTION = "http://AllPointMobile/CustomerManagementService/ICustomerManagement/GetOTTToken";
    public static final String OTT_RESEND_REG_METHOD_NAME = "GetOTTToken";

    /****************************** CUSTOMER_MANAGEMENT - Forget Password ************************************/

    public static final String FORGET_SOAP_ACTION = "http://AllPointMobile/CustomerManagementService/ICustomerManagement/ForgotPassword";
    public static final String FORGET_METHOD_NAME = "ForgotPassword";

    /****************************** CUSTOMER_MANAGEMENT - Change Password from Forgot Password ************************************/

    public static final String CHANGE_SOAP_ACTION = "http://AllPointMobile/CustomerManagementService/ICustomerManagement/ChangePassword";
    public static final String CHANGE_METHOD_NAME = "ChangePassword";

    /****************************** CUSTOMER_MANAGEMENT - Change Password from Settings ************************************/

    public static final String CHANGE_RESET_SOAP_ACTION = "http://AllPointMobile/CustomerManagementService/ICustomerManagement/ResetPassword";
    public static final String CHANGE_RESET_METHOD_NAME = "ResetPassword";

    /****************************** CUSTOMER_MANAGEMENT - GetUser ************************************/

    public static final String EDIT_SOAP_ACTION = "http://AllPointMobile/CustomerManagementService/ICustomerManagement/GetUser";
    public static final String EDIT_METHOD_NAME = "GetUser";

    /****************************** CUSTOMER_MANAGEMENT - UpdateUser ************************************/

    public static final String UPDATE_SOAP_ACTION = "http://AllPointMobile/CustomerManagementService/ICustomerManagement/UpdateUser";
    public static final String UPDATE_METHOD_NAME = "UpdateUser";

    /****************************** CUSTOMER_MANAGEMENT - OTP Service ************************************/

    public static final String OTP_SOAP_ACTION = "http://AllPointMobile/CustomerManagementService/ICustomerManagement/ValidateOTTToken";
    public static final String OTP_METHOD_NAME = "ValidateOTTToken";

    public static final String OTP_BY_MAIL_SOAP_ACTION = "http://AllPointMobile/CustomerManagementService/ICustomerManagement/ValidateOTTTokenbyEmail";
    public static final String OTP_BY_MAIL_METHOD_NAME = "ValidateOTTTokenbyEmail";


    /****************************** TransactionService - VerifyPANDetails ************************************/

    public static final String VARIFY_PAN_SOAP_ACTION = "http://AllPointMobile/TransactionService/ITransaction/VerifyPANDetails";
    public static final String VARIFY_PAN_METHOD_NAME = "VerifyPANDetails";

    /****************************** TransactionService - DeletePANDetails ************************************/

    public static final String CARD_DELETE_SOAP_ACTION = "http://AllPointMobile/TransactionService/ITransaction/DeleteCustomerPANDetail";
    public static final String CARD_DELETE_METHOD_NAME = "DeleteCustomerPANDetail";

    /****************************** TransactionService - Customer Portfilio ************************************/

    public static final String CUSTOMER_PORTFILIO_SOAP_ACTION = "http://AllPointMobile/TransactionService/ITransaction/GetCustomerProtfolio";
    public static final String CUSTOMER_PORTFILIO_METHOD_NAME = "GetCustomerProtfolio";

    /****************************** TransactionService - Transaction ************************************/

    public static final String TRANS_SOAP_ACTION = "http://AllPointMobile/TransactionService/ITransaction/GetTransactionDetails";
    public static final String TRANS_METHOD_NAME = "GetTransactionDetails";

    /****************************** Service Configuration ************************************/


    /***************************** First Time Registration **********************************/
    public static final String FIRST_TIME_REG_SOAP_ACTION = "http://AllPointMobile/CustomerManagementService/ICustomerManagement/VerifyEmail";
    public static final String FIRST_TIME_REG_METHOD_NAME = "VerifyEmail";

    /****************************** CUSTOMER_MANAGEMENT - Logout ************************************/

    public static final String LOGOUT_SOAP_ACTION = "http://AllPointMobile/CustomerManagementService/ICustomerManagement/SignOut";
    public static final String LOGOUT_METHOD_NAME = "SignOut";

    // Allpoint Service Configuration
    public static final String API_USERNAME = "5FC0AF8A";
    public static final String API_PASSWORD = "5FC0AF8A-CF9D-435A-A0A0-197D64A641AC";
    public static final String LOCATION_SEARCH_URL = "https://mobileapi.locatorsearch.com/LocatorSearchAPI.asmx/FindLocations?";
    public static final String FILTERS_SEARCH_URL = "https://mobileapi.locatorsearch.com/LocatorSearchAPI.asmx/GetSearchbyOptions?";
    public static final String VERSION_INFO_URL = "https://mobileapi.locatorsearch.com/LocatorSearchAPI.asmx/GetCurrentAppVersion?";

    public static final String VERSION_INFO_TASK_ID = "com.allpoint.SplashActivity.VERSION_INFO";
    public static final String FILTERS_TASK_ID = "com.allpoint.SplashActivity.VERSION_INFO";

    // Flurry events
    public static final String ABOUT_ACTIVITY_EVENT = "com.allpoint.utils.FlurryAdapter.ABOUT_ACTIVITY_EVENT";
    public static final String SEND_FEEDBACK_EVENT = "com.allpoint.utils.FlurryAdapter.SEND_FEEDBACK_EVENT";
    public static final String VISIT_PLAY_STORE_EVENT = "com.allpoint.utils.FlurryAdapter.VISIT_PLAY_STORE_EVENT";
    public static final String MAP_ACTIVITY_EVENT = "com.allpoint.utils.FlurryAdapter.MAP_ACTIVITY_EVENT";
    public static final String RESULTS_ACTIVITY_EVENT = "com.allpoint.utils.FlurryAdapter.RESULTS_ACTIVITY_EVENT";
    public static final String SHOW_FILTERS_EVENT = "com.allpoint.utils.FlurryAdapter.SHOW_FILTERS_EVENT";
    public static final String MAIN_MENU_ACTIVITY_EVENT = "com.allpoint.utils.FlurryAdapter.MAIN_MENU_ACTIVITY_EVENT";
    public static final String MESSAGES_ACTIVITY_EVENT = "com.allpoint.utils.FlurryAdapter.MESSAGES_ACTIVITY_EVENT";
    public static final String READ_MESSAGE_EVENT = "com.allpoint.utils.FlurryAdapter.READ_MESSAGE_EVENT";
    public static final String POINT_DETAILS_ACTIVITY_EVENT = "com.allpoint.utils.FlurryAdapter.POINT_DETAILS_ACTIVITY_EVENT";
    public static final String PIN_VIEW_ACTIVITY_EVENT = "com.allpoint.utils.FlurryAdapter.PIN_VIEW_ACTIVITY_EVENT";
    public static final String SHARE_ACTIVITY_EVENT = "com.allpoint.utils.FlurryAdapter.SHARE_ACTIVITY_EVENT";
    public static final String SHARE_EMAIL_EVENT = "com.allpoint.utils.FlurryAdapter.SHARE_EMAIL_EVENT";
    public static final String SHARE_SMS_EVENT = "com.allpoint.utils.FlurryAdapter.SHARE_SMS_EVENT";
    public static final String SPLASH_ACTIVITY_EVENT = "com.allpoint.utils.FlurryAdapter.SPLASH_ACTIVITY_EVENT";
    public static final String SETTINGS_ACTIVITY_EVENT = "com.allpoint.utils.FlurryAdapter.SETTINGS_ACTIVITY_EVENT";
    public static final String DIRECTIONS_ACTIVITY_EVENT = "com.allpoint.utils.FlurryAdapter.DIRECTIONS_ACTIVITY_EVENT";
    public static final String RICH_PUSH_OPEN_EVENT = "com.allpoint.utils.FlurryAdapter.RICH_PUSH_OPEN_EVENT";
    public static final String FILTERS_ACTIVITY_EVENT = "com.allpoint.utils.FlurryAdapter.FILTERS_ACTIVITY_EVENT";

    // Service Manager Search Options
    public static final String MAIN_ACTIVITY_POSITION_SEARCH = "com.allpoint.MainActivity.POSITION_SEARCH";
    public static final String MAIN_ACTIVITY_QUERY_SEARCH = "com.allpoint.MainActivity.QUERY_SEARCH";
    public static final String RESULT_LIST_ACTIVITY_POSITION_SEARCH = "com.allpoint.ResultListActivity.POSITION_SEARCH";
    public static final String RESULT_LIST_ACTIVITY_QUERY_SEARCH = "com.allpoint.ResultListActivity.QUERY_SEARCH";

    // Default extra-byte values
    public static final byte DEFAULT_EXTRA_VALUE = -1;
    public static final byte OPEN_MAP_VALUE = 0;
    public static final byte RETURN_ON_MAP_VALUE = 1;
    public static final int COUNT_TRANSACTION = 10;

    // Session Error Code Check
    public static final String SESSION_ERROR_CODE = "162";

    public static final String REG_MOB_ERROR_CODE = "158";

    /**
     * Extra to launch a Message ID.
     */
    public static final String EXTRA_MESSAGE_ID = "com.allpoint.EXTRA_MESSAGE_ID";

    /**
     * Extra to select what item the fragment. Either {@link #} or
     * {@link #INBOX_ITEM}.
     */
    public static final String EXTRA_NAVIGATE_ITEM = "com.allpoint.EXTRA_NAVIGATE_ITEM";

    /**
     * Inbox fragment position.
     */
    public static final int INBOX_ITEM = 1;
    public static final int MY_SCAN_REQUEST_CODE = 100; // arbitrary int

    public static final String POINT_HOUR_VALUE = "hourValue";
    public static final String ERROR_DIALOG_TAG = "errorDialog";

    // Share Settings Configuration
    public static final String SHARE_MAIL_TO = "";
    public static final String SHARE_SUBJECT = "";
    public static final String SHARE_MESSAGE = "";

    // App Extras
    public static final String RICH_PUSH_EXTRA = "messageId";

    public static final String DATE_TIME_FORMAT = "M/d/yy h:mm a";

    // Settings Save Configuration
    public static final String SETTINGS_FILENAME = "AppSettings";
    public static final String SETTINGS_LAUNCH_NEAR_WITH_ME = "LaunchNearWithMe";
    public static final String SETTINGS_DISTANCE_UNIT = "DistanceUnit";
    public static final String SETTINGS_LANGUAGE = "Language";
    public static final String SETTINGS_SET_GEOFENCE = "setGeofenceIs";

    // Setting Distance and Language strings
    public static final String SETTINGS_DIALOG_TAG = "SettingsDialog";
    public static final String SETTINGS_LANGUAGE_ENGLISH = "English";
    public static final String SETTINGS_LANGUAGE_SPANISH = "Espa�ol";
    public static final String KILOMETERS_SHORT_NAME = "km";
    public static final String MILES_SHORT_NAME_EN = "miles";
    public static final String MILES_SHORT_NAME_ES = "millas";

    public static final String IS_LOGIN = "login_status";
    public static final String FIRST_LOGIN = "first_login";
    public static final String USER_NAME = "username";
    public static final String PASSWORD = "password";
    public static final String ACTIVITY_STATUS = "activity_status";
    // ENcryption
    public static final String ENCRYPTION_KEY = "first#1234";
    public static final String ENCRYPTION_IV = "0000000000000000";

    // GoogleMap Configurations
    public static final int GOOGLE_PLAY_REQUEST_CODE = 9000;
    // Updates for every 10 seconds (in milliseconds)
    // But Google recommends use GPS-timer to updates not more than once every
    // hour
    // for better battery saving
    public static final int LOCATION_REQUEST_INTERVAL = 60000;
    public static final int MINIMUM_DISTANCE = 2500;
    public static final int DEFAULT_MAP_ZOOM_VALUE = 13;
    public static final float STEP_CONSTANT = (float) 7;
    public static final int ROUND_VALUE = 5;

    // Allpoint Version Settings
    // DeviceType = 2 - Android SmartPhone
    public static final String DEVICE_TYPE = "2";
    public static final int APP_VERSION_ID = 6;
    public static final String TABLET_APP_VERSION = "1.0";
    public static final String PHONE_APP_VERSION = "2.0";

    public static final int VIBRATOR_DURATION = 50;

    // Updates for every minute (in milliseconds)
    // But Google recommends use timer to StateChangedListener not more than
    // twice every hour
    // for better battery saving
    public static final int URBAN_AIRSHIP_UPDATE_TIMER = 15000;

    // Notification Settings
    public static final String NOTIFICATIONS_NAME = "Allpoint";
    public static final String NOTIFICATIONS_TEXT = "Notifications received";
    public static final String NOTIFICATIONS_DATE_TIME_FORMAT = "h:mm a";
    public static final int NOTIFICATION_ID = 2408;

    public static final String ATMPASS_LINK = "https://www.myatmpass.com";

    public static final String CERTIFICATE_PATH = "sslkeys.bks";
    public static final String CERTIFICATE_PATH_BKS = "cardtronicsdata_pro.bks";

    //Pen Testing
    public static final String CERTIFICATE_PATH_PEN_56 = "CATMDEV.cer";

    //New Cert 26/04/2017 - working for 56
    public static final String CERTIFICATE_PATH_DEV_56 = "catmdev_der.cer";

    //For Production - 4th may 2017 - Used for MTM
    public static final String CERTIFICATE_PATH_PROD = "cardtronicsdata_pro.cer";
    public static final String CERTIFICATE_PROD = "card_prod_0";

}
