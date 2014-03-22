package com.axiomalaska.sos.tools;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
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
import org.apache.xmlbeans.XmlObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpSender.class);	
	    
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

	public static boolean doesUrlExist(String serviceURL) {
	    try {
	        URL url = new URL(serviceURL);
	        HttpURLConnection huc = null;
	        try {
	            huc = (HttpURLConnection) url.openConnection();
	            huc.setRequestMethod("GET");
	            huc.setRequestProperty(
                    "User-Agent",
                    "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.1.2) Gecko/20090729 Firefox/3.5.2 (.NET CLR 3.5.30729)");
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
  
	public static String downloadReadFile(String url) throws IOException {
	    StringBuffer strContent = new StringBuffer("");
	    FileInputStream fin = null;
	    try {
	        String filename = downloadFile(url);
	        File file = new File(filename);
	        int ch;
	        fin = new FileInputStream(file);
	        while ((ch = fin.read()) != -1){
	            strContent.append((char) ch);
	        }
	    } catch (Exception e) {
	        LOGGER.error("Exception while downloading file", e);
	    } finally{
	        if(fin != null){
	            fin.close();
	        }
	    }
	    return strContent.toString();
	}
        
    public static String downloadFileConcurrently(String fileUrl, String filename) {
        BufferedInputStream in = null;
        byte[] data = null;
        int bytesRead = 0;
        int mallocSize = 1048576;
        
        FileOutputStream out = null;
        File file = null;
        
        try {
            URL u = new URL(fileUrl);
            URLConnection uc = u.openConnection();
            InputStream raw = uc.getInputStream();
            in = new BufferedInputStream(raw);
            LOGGER.debug("Attempting to create file: " + filename + ".zip");
            file = File.createTempFile(filename, ".zip");
            out = new FileOutputStream(file);

            // read then write
            data = new byte[mallocSize];
            while (bytesRead != -1) {
                bytesRead = in.read(data);
                if (bytesRead > -1) {
                    LOGGER.debug("Read in " + bytesRead + " bytes .. writing out");
                    out.write(data, 0, bytesRead);
                } else {
                    out.flush();
                }
            }
        } catch (Exception ex) {
            LOGGER.error(ex.toString());
            ex.printStackTrace();
            return null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    LOGGER.error(ex.toString());
                }
            }
            if(out != null){
                try {
                    out.close();
                } catch (IOException ex) {
                    LOGGER.error(ex.toString());
                }
            }
        }
        
        return file.getAbsolutePath();
    }

    public static String downloadFile(String fileUrl) throws IOException {
        URL u = new URL(fileUrl);

        int offset = 0;
        int contentLength = 0;
        BufferedInputStream in = null;
        byte[] data = null;
        try {
            URLConnection uc = u.openConnection();
            contentLength = uc.getContentLength();
            InputStream raw = uc.getInputStream();
            in = new BufferedInputStream(raw);
            if (contentLength > 0) {
                data = new byte[contentLength];
                int bytesRead = 0;
                while (offset < contentLength && bytesRead != -1) {
                    bytesRead = in.read(data, offset, data.length - offset);
                    if (bytesRead != -1)
                        offset += bytesRead;
                }
            } else {
                // don't know the content length, need to read in 1mb at a time
                int mallocSize = 1048576;
                data = new byte[mallocSize];
                int bytesRead = 0;
                while (bytesRead != -1) {
                    bytesRead = in.read(data, offset, data.length - offset);
                    if (bytesRead > -1) {
                        offset += bytesRead;
                        LOGGER.trace("offset now at: " + offset);
                        if (offset >= data.length) {
                            // increase buffer
                            LOGGER.trace("Increasing buffer size by 1mb");
                            byte[] temp = java.util.Arrays.copyOf(data,
                                    data.length);
                            data = java.util.Arrays.copyOf(temp, temp.length
                                    + mallocSize);
                            // for GC
                            temp = null;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println(e.toString());
            e.printStackTrace();
            return null;
        } finally {
            if (in != null) {
                in.close();
            }
        }

        if (contentLength > 0 && offset != contentLength) {
            // throw new IOException("Only read " + offset + " bytes; Expected "
            // + contentLength + " bytes");
            LOGGER.error("Only read " + offset + " bytes; Expected "
                    + contentLength + " bytes");
            return null;
        }

        FileOutputStream out = null;
        File file = null;
        try {
            String filename = u.getFile().substring(
                    u.getFile().lastIndexOf('/') + 1);
            file = File.createTempFile(filename, ".zip");
            out = new FileOutputStream(file);
            out.write(data);
            out.flush();
        } catch (Exception e) {
            LOGGER.error(e.toString());
            e.printStackTrace();
            return null;
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return file.getAbsolutePath();
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
