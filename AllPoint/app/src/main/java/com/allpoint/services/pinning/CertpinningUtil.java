package com.allpoint.services.pinning;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpParams;

import android.util.Base64;


/**
 * Created by athakur on 6/26/16.
 */
public class CertpinningUtil {

    private static final String TLS = "TLS";

    private static final String BEGIN_CERT = "-----BEGIN CERTIFICATE-----";
    private static final String END_CERT = "-----END CERTIFICATE-----";

    public static X509Certificate convertToX509Certificate(String certData) {
        try {
            byte[] certByteData = Base64.decode(certData.replaceAll(BEGIN_CERT, "").replaceAll(END_CERT, ""), Base64.DEFAULT);

            InputStream inStream = new ByteArrayInputStream(certByteData);
            CertificateFactory factory = CertificateFactory.getInstance("X.509");

            return (X509Certificate) factory.generateCertificate(inStream);
        } catch (CertificateException e) {
            //Log.e(TAG, "Error in creating X509Certificate object", e);
        }
        return null;
    }

    public static ClientConnectionManager createClientConnectionManager(HttpParams params, List<X509Certificate> pinnedCerts, boolean pinCerts, boolean isProxy) {

        //Log.d(TAG, "Creating client connection manager with pinning enabled : " + pinCerts);
        String noOfCertsToPin = pinnedCerts == null ? "0" : String.valueOf(pinnedCerts.size());
        //Log.d(TAG, "Creating client connection manager with no of pinned certs : " + noOfCertsToPin);

        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme(PinnedHttpClient.HTTP_SCHEME, PlainSocketFactory.getSocketFactory(), PinnedHttpClient.HTTP_PORT));
        try {
            schemeRegistry.register(new Scheme(PinnedHttpClient.HTTPS_SCHEME, new ApacheSecureSocketFactory(null, pinnedCerts, pinCerts, isProxy), PinnedHttpClient.HTTPS_PORT));
        } catch (NoSuchAlgorithmException e) {
            //e.printStackTrace();
        } catch (KeyManagementException e) {
            //e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            //e.printStackTrace();
        } catch (KeyStoreException e) {
            //e.printStackTrace();
        }
        return new ThreadSafeClientConnManager(params, schemeRegistry);
    }

    public static String downloadUrl(String myurl, List<X509Certificate> pinnedCerts, boolean pinnCerts, boolean isProxy) throws IOException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 500;

     //   System.out.println("In download");
        
        try {
            URL url = new URL(myurl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            if(pinnCerts) {
                conn.setSSLSocketFactory(getPinnedSSLContext(null, pinnedCerts).getSocketFactory());
              //  System.out.println("pinncerts ");
            }
            conn.setReadTimeout(15000 /* milliseconds */);
            conn.setConnectTimeout(20000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
          //  System.out.println("Start connection");
            conn.connect();
            int response = conn.getResponseCode();
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is, len);
            
          //  System.out.println("Out of download");
            
            return String.valueOf(response);

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } 
        
        catch(Exception e)
        {
        	e.printStackTrace();
        	return "false";
        }
        
        finally {
            if (is != null) {
                is.close();
            }
        }
    }
    
    public static String downloadUrl(String myurl, List<X509Certificate> pinnedCerts) throws IOException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 500;

        try {
            URL url = new URL(myurl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            //if(pinnCerts) {
                conn.setSSLSocketFactory(getPinnedSSLContext(null, pinnedCerts).getSocketFactory());
            //}
            conn.setReadTimeout(15000 /* milliseconds */);
            conn.setConnectTimeout(20000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            //Log.d(TAG, "The response is: " + response);
            //System.out.println("#" + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is, len);
            return String.valueOf(response);

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }


    // Reads an InputStream and converts it to a String.
    public static String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }

    public static SSLContext getPinnedSSLContext(KeyStore truststore, List<X509Certificate> pinnedCerts) throws NoSuchAlgorithmException, KeyManagementException {

        SSLContext sslContext = SSLContext.getInstance(TLS);
        TrustManager tm = new SecureTrustManager(pinnedCerts);
        sslContext.init(null, new TrustManager[] {tm}, null);
        return sslContext;
    }


}
