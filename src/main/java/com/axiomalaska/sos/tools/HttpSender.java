package com.axiomalaska.sos.tools;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlObject;

/**
 * This class is a Helper for interfacing with servers with HTTP. 
 * 
 * @author Lance Finfrock
 */
public class HttpSender {    
	// -------------------------------------------------------------------------
	// Public Members
	// -------------------------------------------------------------------------
	
	private static int TIME_OUT = 600000; //10 minutes
    private static final Logger LOGGER = Logger.getLogger(HttpSender.class);	
	    
    private static void addAuthorizationToken(HttpMethodBase httpMethod, String authorizationToken) {
        if (authorizationToken != null && !authorizationToken.isEmpty()){
            httpMethod.addRequestHeader("Authorization", authorizationToken);
        }
    }

    /**
     * Sends an XML document in the body of an HTTP post message
     * 
     * @param serviceURL - the URL to send the post message
     * @param xmlObject - xmlbeans XmlObject to send as the POST body
     * @return HTTP reponse 
     * @throws IOException
     */    
    public static String sendPostMessage(String serviceURL, XmlObject xmlObject) throws IOException {   
        return sendPostMessage(serviceURL, null, xmlObject);
    }

    /**
     * Sends an XML document in the body of an HTTP post message with an authorization token
     * 
     * @param serviceURL - the URL to send the post message
     * @param authorizationToken - authorization header token
     * @param xmlObject - xmlbeans XmlObject to send as the POST body
     * @return HTTP reponse 
     * @throws IOException
     */
    public static String sendPostMessage(String serviceURL, String authorizationToken, 
            XmlObject xmlObject) throws IOException {	
        return sendPostMessage(serviceURL, authorizationToken, XmlHelper.xmlText(xmlObject));
    }

    
    /**
     * Send a HTTP post message 
     * 
     * @param serviceURL - the URL to send the post message
     * @param message - the message to send to the URL
     * @return the response from the post message sent
     */
    public static String sendPostMessage(String serviceURL, String message) throws IOException {
        return sendPostMessage(serviceURL, null, message);
    }

	/**
	 * Send a HTTP post message 
	 * 
	 * @param serviceURL - the URL to send the post message
     * @param authorizationToken - authorization header token 
	 * @param message - the message to send to the URL
	 * @return the response from the post message sent
	 */
	public static String sendPostMessage(String serviceURL, String authorizationToken, String message)
			throws IOException {

		InputStream is = null;
		try {
			HttpClient httpClient = getHttpClient();
			PostMethod method = new PostMethod(serviceURL);
	        setMethodParams(method);
	        
			addAuthorizationToken(method, authorizationToken);			
			
			method.setRequestEntity(new StringRequestEntity(message, "text/xml",
					"UTF-8"));

			HostConfiguration hostConfig = getHostConfiguration(new URL(serviceURL));
			httpClient.setHostConfiguration(hostConfig);
			if( HttpStatus.SC_OK != httpClient.executeMethod(method)) {
			    throw new IOException("Error while sending post message: " + method.getStatusLine());
			}
			is = method.getResponseBodyAsStream();
			return getStringResult(is);
		} finally {
			if(is != null){
				is.close();
			}
		}
	}
	
	public static String sendGetMessage(String urlText) throws IOException {
		HttpClient client = getHttpClient();
		GetMethod method = new GetMethod(urlText);
		setMethodParams(method);
		
		try {
			if (client.executeMethod(method) != HttpStatus.SC_OK) {
			    LOGGER.error("Error while sending get message: " + method.getStatusLine());
				return null;
			}
			return getStringResult(method.getResponseBodyAsStream());
		} finally {
			method.releaseConnection();    
		} 
	}
	
	private static HttpClient getHttpClient(){
	    HttpClient client = new HttpClient();
	    client.getParams().setSoTimeout(TIME_OUT);
	    client.getParams().setConnectionManagerTimeout(TIME_OUT);
	    return client;
	}

    private static void setMethodParams(HttpMethodBase method){
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, 
                new DefaultHttpMethodRetryHandler(3, false));    
        method.getParams().setSoTimeout(TIME_OUT);        
    }
	
	
	public static String sendGetMessage(String serviceURL, Iterable<HttpPart> httpParts, boolean needsEncoded)
	        throws IOException{
		String buildUrl = buildUrl(serviceURL, httpParts, needsEncoded);
		return sendGetMessage(buildUrl);
	}

	public static String sendGetMessage(String serviceURL, Iterable<HttpPart> httpParts)
	        throws IOException{
		return sendGetMessage(serviceURL, httpParts, true);
	}
	
	public String sendPostMessage(String serviceURL, Iterable<HttpPart> httpParts)
	        throws IOException{
		URL siteUrl = new URL(serviceURL);

		DataOutputStream out = null;
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) siteUrl.openConnection();

			conn.setConnectTimeout(TIME_OUT);
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			out = new DataOutputStream(conn.getOutputStream());
			String content = "";
			for (HttpPart httpPart : httpParts) {
				if (content.length() != 0) {
					content += "&";
				}
				content += httpPart.getName() + "="
						+ URLEncoder.encode(httpPart.getValue(), "UTF-8");
			}
			out.writeBytes(content);
			out.flush();
		} finally {
			if(out != null){
				out.close();
			}
		}

		InputStream inputStream = null;
		try {
			inputStream = conn.getInputStream();
			return getStringResult(inputStream);
		} finally {
			if(inputStream != null){
				inputStream.close();
			}
		}
	}

	// -------------------------------------------------------------------------
	// Private Members
	// -------------------------------------------------------------------------

	private static String buildUrl(String serviceURL, Iterable<HttpPart> httpParts,
			boolean needsEncoded) throws UnsupportedEncodingException {
		String url = serviceURL + "?";

		for (HttpPart httpPart : httpParts) {
			if (needsEncoded) {
				url += httpPart.getName() + "="
						+ URLEncoder.encode(httpPart.getValue(), "UTF-8") + "&";
			} else {
				url += httpPart.getName() + "=" + httpPart.getValue() + "&";
			}
		}

		url = url.substring(0, url.length() - 1);

		return url;
	}
	
	private static HostConfiguration getHostConfiguration(URL serviceURL) {
		HostConfiguration hostConfig = new HostConfiguration();

		// apply proxy settings:
		String host = System.getProperty("http.proxyHost");
		String port = System.getProperty("http.proxyPort");
		String nonProxyHosts = System.getProperty("http.nonProxyHosts");

		// check if service url is among the non-proxy-hosts:
		boolean serviceIsNonProxyHost = false;
		if (nonProxyHosts != null && nonProxyHosts.length() > 0) {
			String[] nonProxyHostsArray = nonProxyHosts.split("\\|");
			String serviceHost = serviceURL.getHost();

			for (String nonProxyHost : nonProxyHostsArray) {
				if (nonProxyHost.equals(serviceHost)) {
					serviceIsNonProxyHost = true;
				}
			}
		}
		// set proxy:
		if (serviceIsNonProxyHost == false && host != null && host.length() > 0
				&& port != null && port.length() > 0) {
			Integer portNumber = Integer.parseInt(port);
			hostConfig.setProxy(host, portNumber);
		}

		return hostConfig;
	}

	private static String getStringResult(InputStream incomingStream)
			throws IOException {
		int bufferSize = 128000;
		byte[] buffer = new byte[bufferSize];

		int byteRead = 0;
		int numOfBytesRead = 0;

		byteRead = incomingStream.read();
		while (byteRead != -1) {
			if (numOfBytesRead >= buffer.length) {

				byte[] newBuffer = new byte[buffer.length + bufferSize];

				// copy elements from the old "buffer" to the new "newBuffer"
				System.arraycopy(buffer, 0, newBuffer, 0, buffer.length);

				buffer = newBuffer;
			}

			buffer[numOfBytesRead] = (byte) byteRead;

			numOfBytesRead += 1;

			byteRead = incomingStream.read();
		}

		byte[] incomingResult = new byte[numOfBytesRead];
		System.arraycopy(buffer, 0, incomingResult, 0, numOfBytesRead);

		return new String(incomingResult);
	}
}
