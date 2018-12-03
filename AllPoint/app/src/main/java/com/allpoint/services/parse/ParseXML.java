package com.allpoint.services.parse;

import com.allpoint.model.ResgistrationResponseData;
import com.allpoint.services.RespForgetPass;
import com.allpoint.services.RespLogin;
import com.allpoint.services.RespOTP;
import com.allpoint.services.RespOTPreg;
import com.allpoint.services.RespSessionInvalid;
import com.allpoint.services.RespSignOut;
import com.allpoint.services.RespVerifyEmailRequest;
import com.allpoint.services.ResponseCustomerPortfilio;
import com.allpoint.services.ResponseGetUserDetails;
import com.allpoint.services.ResponseHandler;
import com.allpoint.services.ResponseHandlerForTransactions;
import com.allpoint.services.ResponseTransaction;
import com.allpoint.services.ResponseUpdateUser;
import com.allpoint.services.ResponseVarifyPan;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class ParseXML {

    InputStream is;

    /****************************** Parsing Response For Login ***********************************/

    // Used into Login Activity
    public RespLogin parseXMLForLgoinUser(String resp) {
        RespLogin ulogindetails = null;
        try {
            // is = new ByteArrayInputStream(Constant.LOGIN_RESP.getBytes());
            is = new ByteArrayInputStream(resp.getBytes());
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();

            ResponseHandler resp_Handler = new ResponseHandler();
            xr.setContentHandler(resp_Handler);
            InputSource inStream = new InputSource(is);
            xr.parse(inStream);

            ulogindetails = resp_Handler.getLoginResp();

            is.close();

        } catch (Exception e) {
            //e.printStackTrace();
        }

        return ulogindetails;
    }

    /****************************** Registration Screen ***********************************/

    // For Register User
    public ResgistrationResponseData parseXMLforRegisterUser(String resp) {
        // String data =
        // "<RegistrationResponse><Status>true</Status><StatusCode>160</StatusCode><StatusMessage>Please enter different Username</StatusMessage><Token></Token></RegistrationResponse>";
        // ResgistrationResponseData udetails = new ResgistrationResponseData();
        ResgistrationResponseData resRegUser = null;
        try {
            is = new ByteArrayInputStream(resp.getBytes());
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();

            ResponseHandler resp_Handler = new ResponseHandler();
            xr.setContentHandler(resp_Handler);
            InputSource inStream = new InputSource(is);
            xr.parse(inStream);

            resRegUser = resp_Handler.getRegResp();
            is.close();
        } catch (Exception e) {
            //e.printStackTrace();
        }

        return resRegUser;

    }

    // For Update User

    public ResponseUpdateUser parseXMLForUpdateUser(String resp) {
        ResponseUpdateUser resUpdateUser = null;
        try {
            is = new ByteArrayInputStream(resp.getBytes());
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();

            ResponseHandler resp_Handler = new ResponseHandler();
            xr.setContentHandler(resp_Handler);
            InputSource inStream = new InputSource(is);
            xr.parse(inStream);
            resUpdateUser = resp_Handler.getUpdateUserDetailsResp();

            is.close();

        } catch (Exception e) {
            //e.printStackTrace();
        }

        return resUpdateUser;

    }

    // For Get User Details

    public ResponseGetUserDetails parseXMLForGetUser(String resp) {
        ResponseGetUserDetails getUserDetails = null;
        try {

            is = new ByteArrayInputStream(resp.getBytes());
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();

            ResponseHandler resp_Handler = new ResponseHandler();
            xr.setContentHandler(resp_Handler);
            InputSource inStream = new InputSource(is);
            xr.parse(inStream);

            getUserDetails = resp_Handler.getUserDetailsResp();

            is.close();

        } catch (Exception e) {
            //e.printStackTrace();
        }

        return getUserDetails;

    }

    /*
     * ResgistrationResponseData udetails; public ResgistrationResponseData
     * parseXML(String resp) {
     *
     * //String data =
     * "<RegistrationResponse><Status>true</Status><StatusCode>160</StatusCode><StatusMessage>Please enter different Username</StatusMessage><Token></Token></RegistrationResponse>"
     * ;
     *
     * udetails = new ResgistrationResponseData(); try {
     *
     * is = new ByteArrayInputStream(resp.getBytes()); SAXParserFactory spf =
     * SAXParserFactory.newInstance(); SAXParser sp = spf.newSAXParser();
     * XMLReader xr = sp.getXMLReader();
     *
     * ResponseHandler resp_Handler = new ResponseHandler();
     * xr.setContentHandler(resp_Handler); InputSource inStream = new
     * InputSource(is); xr.parse(inStream);
     *
     * udetails = resp_Handler.getRegResp();
     *
     *
     * is.close();
     *
     * } catch (Exception e) { e.printStackTrace(); }
     *
     * return udetails;
     *
     * }
     */

    /****************************** Parsing Response - Scan Card ***********************************/

    public ResponseVarifyPan parseXMLForVarifyPan(String resp) {
        ResponseVarifyPan varifyPandetails = null;
        try {
            // is = getResources().openRawResource(R.raw.resp);
            is = new ByteArrayInputStream(resp.getBytes());
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();

            ResponseHandlerForTransactions resp_Handler = new ResponseHandlerForTransactions();
            xr.setContentHandler(resp_Handler);
            InputSource inStream = new InputSource(is);
            xr.parse(inStream);

            varifyPandetails = resp_Handler.getVarifyPanResp();

            is.close();

        } catch (Exception e) {
            //e.printStackTrace();
        }

        return varifyPandetails;
    }

    /*
     * public ResponseVarifyPan parseXMLForVarifyPan(String resp) {
     * ResponseVarifyPan varifyPandetails = null; try {
     *
     * is = new ByteArrayInputStream(resp.getBytes()); SAXParserFactory spf =
     * SAXParserFactory.newInstance(); SAXParser sp = spf.newSAXParser();
     * XMLReader xr = sp.getXMLReader();
     *
     * ResponseHandlerForTransactions resp_Handler = new
     * ResponseHandlerForTransactions(); xr.setContentHandler(resp_Handler);
     * InputSource inStream = new InputSource(is); xr.parse(inStream);
     *
     * varifyPandetails = resp_Handler.getVarifyPanResp();
     *
     * is.close();
     *
     * } catch (Exception e) { e.printStackTrace(); }
     *
     * return varifyPandetails; }
     */

    /****************************** Parsing Response - History ***********************************/

    ResponseTransaction respTransIs;

    public ResponseTransaction parseXMLhistoryResp(String resp) {

        try {
            // is = getResources().openRawResource(R.raw.resp);
            is = new ByteArrayInputStream(resp.getBytes());
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();

            ResponseHandlerForTransactions resp_Handler = new ResponseHandlerForTransactions();
            xr.setContentHandler(resp_Handler);
            InputSource inStream = new InputSource(is);
            xr.parse(inStream);

            respTransIs = resp_Handler.getTransactions();

            is.close();

        } catch (Exception e) {
            //e.printStackTrace();
        }

        return respTransIs;
    }

    /****************************** Parsing Response - Session Invalid ***********************************/
    // Parse Response For Session Invalid
    public RespSessionInvalid parseXMLforSessionInvalid(String resp) {
        RespSessionInvalid mRespForInvalidSession = null;
        try {
            // is = getResources().openRawResource(R.raw.resp);
            is = new ByteArrayInputStream(resp.getBytes());
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();

            ResponseHandler resp_Handler = new ResponseHandler();
            xr.setContentHandler(resp_Handler);
            InputSource inStream = new InputSource(is);
            xr.parse(inStream);

            mRespForInvalidSession = resp_Handler.getSessionInvalidresp();

            is.close();

        } catch (Exception e) {
            //e.printStackTrace();
        }

        return mRespForInvalidSession;
    }

    /****************************** Parsing Response - Session Invalid ***********************************/

    public ResponseCustomerPortfilio parseXMLCustomerPortfilio(String resp) {
        ResponseCustomerPortfilio customerPortfilioResp = null;
        try {
            // is = getResources().openRawResource(R.raw.resp);
            is = new ByteArrayInputStream(resp.getBytes());
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();

            ResponseHandlerForTransactions resp_Handler = new ResponseHandlerForTransactions();
            xr.setContentHandler(resp_Handler);
            InputSource inStream = new InputSource(is);
            xr.parse(inStream);

            customerPortfilioResp = resp_Handler.getCustomerPortfilio();

            is.close();

        } catch (Exception e) {
            //e.printStackTrace();
        }

        return customerPortfilioResp;
    }

    /************************************************ OTT Activity ***********************************************/

    public RespOTP parseXMLForOTP(String resp) {

        RespOTP ottDetails = null;
        try {

            is = new ByteArrayInputStream(resp.getBytes());
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();

            ResponseHandler resp_Handler = new ResponseHandler();
            xr.setContentHandler(resp_Handler);
            InputSource inStream = new InputSource(is);

            xr.parse(inStream);

            ottDetails = resp_Handler.getOTPResp();

            is.close();

        } catch (Exception e) {
            //e.printStackTrace();
        }

        return ottDetails;
    }

    public RespOTPreg parseXMLforReg(String resp) {

        RespOTPreg ottDetailsAfterReg = new RespOTPreg();
        try {

            is = new ByteArrayInputStream(resp.getBytes());
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();

            ResponseHandler resp_Handler = new ResponseHandler();
            xr.setContentHandler(resp_Handler);
            InputSource inStream = new InputSource(is);
            xr.parse(inStream);

            ottDetailsAfterReg = resp_Handler.getOTPRespReg();

            is.close();

        } catch (Exception e) {
            //e.printStackTrace();
        }

        return ottDetailsAfterReg;

    }

    /************************************************ Forgot Password Activity ***********************************************/

    public RespForgetPass parseXMLForForgetPass(String resp) {
        RespForgetPass repForPass = null;
        try {
            // is = getResources().openRawResource(R.raw.resp);
            is = new ByteArrayInputStream(resp.getBytes());
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();

            ResponseHandler resp_Handler = new ResponseHandler();
            xr.setContentHandler(resp_Handler);
            InputSource inStream = new InputSource(is);
            xr.parse(inStream);

            repForPass = resp_Handler.getForgetResp();

            is.close();

        } catch (Exception e) {
            //e.printStackTrace();
        }

        return repForPass;
    }

    /************************************************ Logout ***********************************************/
    public RespLogin parseXMLForLogout(String resp) {
        RespLogin ulogindetails = null;
        try {
            // is = new ByteArrayInputStream(Constant.LOGIN_RESP.getBytes());
            is = new ByteArrayInputStream(resp.getBytes());
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();

            ResponseHandler resp_Handler = new ResponseHandler();
            xr.setContentHandler(resp_Handler);
            InputSource inStream = new InputSource(is);
            xr.parse(inStream);

            ulogindetails = resp_Handler.getLoginResp();

            is.close();

        } catch (Exception e) {
            //e.printStackTrace();
        }

        return ulogindetails;
    }

    /************************************************ Verify Pan ***********************************************/
    public RespVerifyEmailRequest parseXMLVerifyEmailRequest(String resp) {
        RespVerifyEmailRequest ulogindetails = null;
        try {
            // is = new ByteArrayInputStream(Constant.LOGIN_RESP.getBytes());
            is = new ByteArrayInputStream(resp.getBytes());
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();

            ResponseHandler resp_Handler = new ResponseHandler();
            xr.setContentHandler(resp_Handler);
            InputSource inStream = new InputSource(is);
            xr.parse(inStream);

            ulogindetails = resp_Handler.getVerifyEmailresp();

            is.close();

        } catch (Exception e) {
            //e.printStackTrace();
        }

        return ulogindetails;
    }

    /************************************************ Sign Out ***********************************************/

    public RespSignOut parseXMLSignOutRequest(String resp) {
        RespSignOut usignoutdetails = null;
        try {
            // is = new ByteArrayInputStream(Constant.LOGIN_RESP.getBytes());
            is = new ByteArrayInputStream(resp.getBytes());
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();

            ResponseHandler resp_Handler = new ResponseHandler();
            xr.setContentHandler(resp_Handler);
            InputSource inStream = new InputSource(is);
            xr.parse(inStream);

            usignoutdetails = resp_Handler.getSignOutresp();

            is.close();

        } catch (Exception e) {
            //e.printStackTrace();
        }

        return usignoutdetails;
    }

}
