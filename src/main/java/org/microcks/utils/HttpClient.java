package org.microcks.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpClient {
	
	int connectTimeout = 15000;
	int readTimeout = 15000;

	public HttpClient(){
		try {
		
	        // Create a trust manager that does not validate certificate chains
	        TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
	                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
	                    return null;
	                }
	                public void checkClientTrusted(X509Certificate[] certs, String authType) {
	                }
	                public void checkServerTrusted(X509Certificate[] certs, String authType) {
	                }
	            }
	        };
	 
	        // Install the all-trusting trust manager
	        SSLContext sc = SSLContext.getInstance("SSL");
	        sc.init(null, trustAllCerts, new java.security.SecureRandom());
	        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	 
	        // Create all-trusting host name verifier
	        HostnameVerifier allHostsValid = new HostnameVerifier() {
	            public boolean verify(String hostname, SSLSession session) {
	                return true;
	            }
	        };
	 
	        // Install the all-trusting host verifier
	        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public String httpGET(String path) throws Exception {
		String response = "";
		try {
			URL url = new URL(path);
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(connectTimeout);
			conn.setReadTimeout(readTimeout);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);
			
			int statusCode = conn.getResponseCode();
			if (statusCode >= 200 && statusCode < 400) {
				response = extract(conn.getInputStream());
			}else {
				response = extract(conn.getErrorStream());
			}
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error en la llamada GET");
		}
		return response;
	}

	private String extract(InputStream is) throws Exception {
		Reader in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		StringBuilder data = new StringBuilder();
		for (int c; (c = in.read()) >= 0;) {
			data.append((char) c);
		}
		return data.toString();
	}
}
