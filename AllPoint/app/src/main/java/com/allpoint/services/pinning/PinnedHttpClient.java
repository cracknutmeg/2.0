package com.allpoint.services.pinning;

import java.security.cert.X509Certificate;
import java.util.List;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;

public class PinnedHttpClient extends DefaultHttpClient {

    public static final String HTTP_SCHEME = "http";
    public static final String HTTPS_SCHEME = "https";
    public static final int HTTP_PORT = 80;
    public static final int HTTPS_PORT = 443;

    public PinnedHttpClient(HttpParams params, List<X509Certificate> pinnedCerts, boolean pinCerts, boolean isProxy) {
        super(CertpinningUtil.createClientConnectionManager(params, pinnedCerts, pinCerts, isProxy), (HttpParams)null);
    }
}
