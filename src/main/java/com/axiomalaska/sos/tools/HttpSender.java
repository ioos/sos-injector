package com.axiomalaska.sos.tools;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

/**
 * This class is a Helper for interfacing with HTTP. 
 * 
 * @author Lance Finfrock
 */
public class HttpSender {

	// -------------------------------------------------------------------------
	// Public Members
	// -------------------------------------------------------------------------
	
	/**
	 * Send a HTTP post message 
	 * 
	 * @param serviceURL - the URL to send the post message
	 * @param message - the message to send to the URL
	 * @return the response from the post message sent
	 */
	public String sendPostMessage(String serviceURL, String message)
			throws IOException {
		HttpClient httpClient = new HttpClient();
		PostMethod method = new PostMethod(serviceURL);

		method.setRequestEntity(new StringRequestEntity(message, "text/xml",
				"UTF-8"));

		HostConfiguration hostConfig = getHostConfiguration(new URL(serviceURL));
		httpClient.setHostConfiguration(hostConfig);
		httpClient.executeMethod(method);

		InputStream is = method.getResponseBodyAsStream();

		try {
			return getStringResult(is);
		} finally {
			is.close();
		}
	}
	
	// -------------------------------------------------------------------------
	// Private Members
	// -------------------------------------------------------------------------

	private HostConfiguration getHostConfiguration(URL serviceURL) {
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

	private String getStringResult(InputStream incomingStream)
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
