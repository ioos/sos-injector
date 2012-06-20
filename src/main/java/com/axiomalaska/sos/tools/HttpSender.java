package com.axiomalaska.sos.tools;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

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
	
	private static int TIME_OUT = 5000;
			
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
	
	public String sendGetMessage(String urlText) throws IOException {
		URL url = new URL(urlText);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setConnectTimeout(TIME_OUT);
		System.out.println("timeout is " + connection.getConnectTimeout());
		return getStringResult(connection.getInputStream());
	}

	public String sendGetMessage(String serviceURL, List<HttpPart> httpParts)
			throws Exception {
		String buildUrl = buildUrl(serviceURL, httpParts, false);
		return sendGetMessage(buildUrl);
	}
	
	public String sendPostMessage(String serviceURL, List<HttpPart> httpParts)
			throws Exception {
		URL siteUrl = new URL(serviceURL);
		HttpURLConnection conn = (HttpURLConnection) siteUrl.openConnection();

		conn.setConnectTimeout(TIME_OUT);
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.setDoInput(true);

		DataOutputStream out = new DataOutputStream(conn.getOutputStream());

		try {
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
			out.close();
		}

		InputStream inputStream = conn.getInputStream();
		try {
			return getStringResult(inputStream);
		} finally {
			inputStream.close();
		}
	}
	
	public boolean doesUrlExists(String serviceURL) {
		try {
			URL url = new URL(serviceURL);

			HttpURLConnection huc = (HttpURLConnection) url.openConnection();

			huc.setRequestMethod("GET");
			huc.setRequestProperty(
					"User-Agent",
					"Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.1.2) Gecko/20090729 Firefox/3.5.2 (.NET CLR 3.5.30729)");

			try {
				huc.connect();
			} catch (Exception e) {
				return false;
			}

			switch (huc.getResponseCode()) {
			case 200: // do nothing
				break;
			case 404:
				return false;
			case 300:
				return false;
			default:
				return false;
			}

			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public String downloadReadFile(String url) throws Exception {
		String filename = downloadFile(url);

		File file = new File(filename);
		int ch;
		StringBuffer strContent = new StringBuffer("");
		FileInputStream fin = null;
		try {
			fin = new FileInputStream(file);
			while ((ch = fin.read()) != -1){
				strContent.append((char) ch);
			}
			fin.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return strContent.toString();
	}

	public String downloadFile(String fileUrl) throws Exception {
		URL u = new URL(fileUrl);
		URLConnection uc = u.openConnection();
		int contentLength = uc.getContentLength();
		InputStream raw = uc.getInputStream();
		BufferedInputStream in = new BufferedInputStream(raw);

		int offset = 0;
		byte[] data = new byte[contentLength];
		try {
			int bytesRead = 0;
			while (offset < contentLength && bytesRead != -1) {
				bytesRead = in.read(data, offset, data.length - offset);
				if (bytesRead != -1)
					offset += bytesRead;
			}
		} finally {
			in.close();
		}

		if (offset != contentLength) {
			throw new IOException("Only read " + offset + " bytes; Expected "
					+ contentLength + " bytes");
		}

		String filename = u.getFile().substring(
				u.getFile().lastIndexOf('/') + 1);
		File file = File.createTempFile(filename, ".zip");
		FileOutputStream out = new FileOutputStream(file);
		try {
			out.write(data);
			out.flush();
		} finally {
			out.close();
		}
		return file.getAbsolutePath();
	}
	
	// -------------------------------------------------------------------------
	// Private Members
	// -------------------------------------------------------------------------

	private String buildUrl(String serviceURL, List<HttpPart> httpParts,
			boolean needsEncoded) throws Exception {
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
