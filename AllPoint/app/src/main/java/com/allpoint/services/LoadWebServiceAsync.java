package com.allpoint.services;

import android.content.Context;
import android.os.AsyncTask;

import com.allpoint.activities.LoginActivity;
import com.allpoint.services.pinning.CertpinningUtil;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpsTransportSE;
import org.kxml2.kdom.Element;
import org.kxml2.kdom.Node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyStoreException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;


public class LoadWebServiceAsync extends AsyncTask<String, WebServiceListner, String> {

    BufferedReader br;
    //MemorizingTrustManager mtm;
    private static final int TIMEOUT = 120000; // 2min
    private String value;
    private WebServiceListner interfaceObj;
    private String url;
    private String namespace;
    private String methodName;

    private String sessionToken;
    private String headerUN;

    private String soapAction;
    private String SEC_NAMESPACE = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";

    Object response = null;
    private Context mContext;

    //check certificate is pinned or not
    private boolean isCertPinned = false;

    public LoadWebServiceAsync(Context mContext,
                               WebServiceListner interfaceObj, String value, String url,
                               String methodName, String soapAction, String namespace,
                               String userName, String sessionT) {
        this.value = value;
        this.interfaceObj = interfaceObj;
        this.url = url;
        this.namespace = namespace;
        this.methodName = methodName;
        this.soapAction = soapAction;
        this.sessionToken = sessionT;
        this.mContext = mContext;


    }

    @Override
    protected void onPreExecute() {
        interfaceObj.onRunning();
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        try {

            return callWebservice(namespace, methodName, url, soapAction, value);

        } catch (KeyStoreException | CertificateException | IOException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void onPostExecute(String result) {

        interfaceObj.onResult(result);

    }

    private String callWebservice(String nameSpace, String methodName, String url, String soapAction, String value)
            throws KeyStoreException, CertificateException, IOException {
        SoapObject request = new SoapObject(nameSpace, methodName);

        PropertyInfo propInfoUsername = new PropertyInfo();
        propInfoUsername.setName("requestXml");
        propInfoUsername.setValue(value);
        propInfoUsername.setType(PropertyInfo.STRING_CLASS);
        request.addProperty(propInfoUsername);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

        // create header
        Element[] header = new Element[2];
        header[0] = new Element().createElement(SEC_NAMESPACE, "Security");
        header[0].setAttribute(null, "mustUnderstand", "1");

        Element usernametoken = new Element().createElement(SEC_NAMESPACE,
                "UsernameToken");
        usernametoken.setAttribute(null, "Id", "UsernameToken-1");

        //Attach username of api to the header
        Element username = new Element().createElement(null, "n0:Username");
        username.addChild(Node.IGNORABLE_WHITESPACE, LoginActivity.username);
        usernametoken.addChild(Node.ELEMENT, username);

        Element pwd = new Element().createElement(null, "n0:Password");
        pwd.addChild(Node.TEXT, LoginActivity.password);
        usernametoken.addChild(Node.ELEMENT, pwd);

        header[0].addChild(Node.ELEMENT, usernametoken);

        // Add token here
        header[1] = new Element().createElement(null, "token");
        header[1].addChild(Node.TEXT, sessionToken);

        // add header to envelope
        envelope.headerOut = header;

        envelope.dotNet = true;
        envelope.bodyOut = request;
        envelope.setOutputSoapObject(request);

        System.out.println("#1. Username : " + LoginActivity.username);
        System.out.println("#2. pwd : " + LoginActivity.password);
        System.out.println("#3. nameSpace : " + nameSpace);
        System.out.println("#4. methodName : " + methodName);
        System.out.println("#5. url : " + url);
        System.out.println("#6. soapAction : " + soapAction);
        System.out.println("#7. value : " + value);
        System.out.println("#8. Session Token : " + sessionToken);

        boolean P_Build = LoginActivity.prod_build;

        //System.out.println("True? = "+P_Build);
		
		/*if (Constant.HOSTNAME.equalsIgnoreCase(Constant.LIVE_SERVER_HOSTNAME)) {
			isCertPinned = pinnCert(mContext);
		}*/

        if (P_Build) {
            isCertPinned = pinnCert(mContext);
        } else {

            //System.out.println("In QC code");

            HttpsTransportSE androidHttpTransport = new HttpsTransportSE(
                    LoginActivity.hostname, 443, url, TIMEOUT);

            try {
                androidHttpTransport.debug = true;
                androidHttpTransport.call(soapAction, envelope);
                System.out.println("# envelope " + envelope);
                try {

                    response = (SoapPrimitive) envelope.getResponse();
                    System.out.println("# methods response" + response);
                } catch (SoapFault sfe) {

                    response = androidHttpTransport.responseDump;
                    System.out.println("# methods response" + response);

                } catch (NullPointerException npe) {

                    //npe.printStackTrace();

                }
            } catch (org.ksoap2.transport.HttpResponseException hex) {

                //hex.printStackTrace();

            } catch (java.net.SocketTimeoutException ste) {
                // Timeout error

                //	ste.printStackTrace();

            } catch (IOException ioe) {
                // Timeout error

                //ioe.printStackTrace();

            } catch (org.xmlpull.v1.XmlPullParserException xpe) {
                // Timeout error

                //xpe.printStackTrace();

            } catch (Exception e) {

                // e.printStackTrace();
            }

            return response == null ? "" : response.toString();

        }

        if (isCertPinned) {
            HttpsTransportSE androidHttpTransport = new HttpsTransportSE(
                    LoginActivity.hostname, 443, url, TIMEOUT);

            try {
                androidHttpTransport.debug = true;
                androidHttpTransport.call(soapAction, envelope);

                try {
                    response = (SoapPrimitive) envelope.getResponse();
                    System.out.println("# methods response" + response);
                } catch (SoapFault sfe) {

                    response = androidHttpTransport.responseDump;

                } catch (NullPointerException npe) {
                    npe.printStackTrace();

                }
            } catch (org.ksoap2.transport.HttpResponseException hex) {
                hex.printStackTrace();

            } catch (java.net.SocketTimeoutException ste) {
                // Timeout error
                ste.printStackTrace();

            } catch (IOException ioe) {
                // Timeout error
                ioe.printStackTrace();

            } catch (org.xmlpull.v1.XmlPullParserException xpe) {
                // Timeout error
                xpe.printStackTrace();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return response == null ? "" : response.toString();
        } else {
            return response == null ? "" : response.toString();
        }
        //Pen Testing Code
    }

    private boolean pinnCert(Context conForPin) throws IOException, KeyStoreException, CertificateException {
        // TODO Auto-generated method stub
        List<X509Certificate> pinnedCerts = new ArrayList<>();
        try {

            String certificate = LoginActivity.cert;

            //System.out.println("Cert name in pinncert = "+ certificate);

            br = new BufferedReader(new InputStreamReader(conForPin.getAssets().open(certificate)));
            //br = new BufferedReader(new InputStreamReader( conForPin.getAssets().open("catmdev_der.cer")));
            //br = new BufferedReader(new InputStreamReader(conForPin.getResources().openRawResource(R.raw.card_prod_0)));
            //br = new BufferedReader(new InputStreamReader(conForPin.getResources().openRawResource(R.raw.github_com_crt)));
            //System.out.println("# br" + br.readLine());
        } catch (Exception e) {
            // TODO: handle exception
            //System.out.println("# IOException" + e.getMessage());
        }

        StringBuilder sb = new StringBuilder();
        String readLine = null;
        try {
            while ((readLine = br.readLine()) != null) {
                sb.append(readLine);
            }
        } catch (IOException e) {
            //Log.e(TAG, "Could not read cert file", e);
            //System.out.println("# IOException");
        }
        //Log.d(TAG, "Cert : " + sb.toString());

        pinnedCerts.add(CertpinningUtil.convertToX509Certificate(sb.toString()));
        //System.out.println("# cert before add " + CertpinningUtil.convertToX509Certificate(sb.toString()));

        try {
            //System.out.println("# downloadUrl");


            CertpinningUtil.downloadUrl("https://" + LoginActivity.hostname, pinnedCerts, true, false);

        } catch (Exception e) {
            // TODO: handle exception
            //System.out.println("# exception" + e.getMessage());
            return false;
        }
        return true;
    }
}


//For QC and Dev Testing

//package com.allpoint.services;
//
//import java.io.IOException;
//import java.security.KeyStoreException;
//import java.security.cert.CertificateException;
//
//import org.ksoap2.SoapEnvelope;
//import org.ksoap2.SoapFault;
//import org.ksoap2.serialization.PropertyInfo;
//import org.ksoap2.serialization.SoapObject;
//import org.ksoap2.serialization.SoapPrimitive;
//import org.ksoap2.serialization.SoapSerializationEnvelope;
//import org.ksoap2.transport.HttpsTransportSE;
//import org.kxml2.kdom.Element;
//import org.kxml2.kdom.Node;
//
//import android.content.Context;
//import android.os.AsyncTask;
//
////import com.allpoint.services.pinning.MemorizingTrustManager;
//import com.allpoint.util.Constant;
//
//public class LoadWebServiceAsync extends
//	
//	
//
//	AsyncTask<String, WebServiceListner, String> {
//	//MemorizingTrustManager mtm;
//	private static final int TIMEOUT = 25000; // 25 seconds
//	private String value;
//	private WebServiceListner interfaceObj;
//	private String url;
//	private String namespace;
//	private String methodName;
//
//	private String sessionToken;
//	/*private String headerUN;*/
//
//	private String soapAction;
//	private String SEC_NAMESPACE = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
//
//	Object response = null;
//	private Context mContext;
//	public LoadWebServiceAsync(Context mContext,
//			WebServiceListner interfaceObj, String value, String url,
//			String methodName, String soapAction, String namespace,
//			String userName, String sessionT) {
//		this.value = value;
//		this.interfaceObj = interfaceObj;
//		this.url = url;
//		this.namespace = namespace;
//		this.methodName = methodName;
//		this.soapAction = soapAction;
//		this.sessionToken = sessionT;
//		this.mContext = mContext;
//		
//		
//	}
//
//	@Override
//	protected void onPreExecute() {
//		interfaceObj.onRunning();
//		super.onPreExecute();
//	}
//
//	@Override
//	protected String doInBackground(String... params) {
//
//		try {
//
//			return callWebservice(namespace, methodName, url, soapAction, value);
//		
//		} catch (KeyStoreException | CertificateException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return "";
//	}
//
//	@Override
//	protected void onPostExecute(String result) {
//
//		interfaceObj.onResult(result);
//	}
//
//	private String callWebservice(String nameSpace, String methodName,
//			String url, String soapAction, String value)
//			throws KeyStoreException, CertificateException, IOException {
//		SoapObject request = new SoapObject(nameSpace, methodName);
//
//		PropertyInfo propInfoUsername = new PropertyInfo();
//		propInfoUsername.setName("requestXml");
//		propInfoUsername.setValue(value);
//		propInfoUsername.setType(PropertyInfo.STRING_CLASS);
//		request.addProperty(propInfoUsername);
//
//		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
//				SoapEnvelope.VER11);
//
//		// create header
//		Element[] header = new Element[2];
//		header[0] = new Element().createElement(SEC_NAMESPACE, "Security");
//		header[0].setAttribute(null, "mustUnderstand", "1");
//
//		Element usernametoken = new Element().createElement(SEC_NAMESPACE,
//				"UsernameToken");
//		usernametoken.setAttribute(null, "Id", "UsernameToken-1");
//
//		/* Attach username of api to the header */
//		Element username = new Element().createElement(null, "n0:Username");
//		username.addChild(Node.IGNORABLE_WHITESPACE, Constant.LOGIN_USER_NAME);
//		usernametoken.addChild(Node.ELEMENT, username);
//
//		Element pwd = new Element().createElement(null, "n0:Password");
//		pwd.addChild(Node.TEXT, Constant.LOGIN_USER_PASSWORD);
//		usernametoken.addChild(Node.ELEMENT, pwd);
//
//		header[0].addChild(Node.ELEMENT, usernametoken);
//
//		// Add token here
//		header[1] = new Element().createElement(null, "token");
//		header[1].addChild(Node.TEXT, sessionToken);
//		
//		
//		/*// Add token here
//		header[2] = new Element().createElement(null, "username");
//		header[2].addChild(Node.TEXT, headerUN);*/
//
//		// add header to envelope
//		envelope.headerOut = header;
//
//		envelope.dotNet = true;
//		envelope.bodyOut = request;
//		envelope.setOutputSoapObject(request);
//		
//		System.out.println("# methods " + methodName);
//		System.out.println("# value " + value);
//		System.out.println("# soapAction " + soapAction);
//		System.out.println("# namespace " + namespace);
//				
//		
//		
//		if (Constant.HOSTNAME.equalsIgnoreCase(Constant.QC_SERVER_HOSTNAME)) {
//			
//			//SSLCertificateChecking(mContext);
//			//allowAllSSL(mContext);
//			//getSSL(mContext);
//		} 
//		
////		Pen Testing Code
////		HttpsTransportSE androidHttpTransport = new HttpsTransportSE(
////		Constant.HOSTNAME, 443, url, TIMEOUT);
//		
//		//QC Release
//		HttpsTransportSE androidHttpTransport = new HttpsTransportSE(
//			Constant.HOSTNAME, 7457, url, TIMEOUT);
//		
//		System.out.println("# HOSTNAME " + Constant.HOSTNAME);
//		System.out.println("# url " + url);
//		try {
//			androidHttpTransport.debug = true;
//			androidHttpTransport.call(soapAction, envelope);
//			System.out.println("# envelope " + envelope);
//			try {
//				
//				response = (SoapPrimitive) envelope.getResponse();
//				System.out.println("# methods response" + response);
//			} catch (SoapFault sfe) {
//				
//				response = androidHttpTransport.responseDump;
//				System.out.println("# methods response" + response);
//				
//			} catch (NullPointerException npe) {
//				npe.printStackTrace();
//
//			}
//		} catch (org.ksoap2.transport.HttpResponseException hex) {
//			hex.printStackTrace();
//
//		} catch (java.net.SocketTimeoutException ste) {
//			// Timeout error
////			ste.printStackTrace();
//
//		} catch (IOException ioe) {
//			// Timeout error
//			ioe.printStackTrace();
//
//		} catch (org.xmlpull.v1.XmlPullParserException xpe) {
//			// Timeout error
////			xpe.printStackTrace();
//
//		} catch (Exception e) {
//			// e.printStackTrace();
//		}	
//		return response == null ? "" : response.toString();
//	}
//
//}

