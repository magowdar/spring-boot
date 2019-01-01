package com.nokia.ims.fmprocess.fm.ems.cmparamlib;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CMInterfaceRESTLibrary {

	private static Logger logger = LogManager
			.getLogger(CMInterfaceRESTLibrary.class.getName());

	private HttpURLConnection httpConn = null;

	static {
		try {
			System.setProperty("jsse.enableSNIExtension", "false");
			System.setProperty("https.protocols", "TLSv1");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	private void setHttpConnectionProperties() {
		try {
			httpConn.setRequestMethod("GET");
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);
			httpConn.setRequestProperty("Content-Type", "application/json");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	public String getDataOverREST(String requestUrl) {
		String response = null;
		try {
			URL urlRef = new URL(requestUrl);
			logger.info(requestUrl);
			httpConn = ((HttpURLConnection) urlRef.openConnection());
			setHttpConnectionProperties();
			response = getDataOverHttp();
		} catch (Exception e) {
			logger.error(e);
		}finally{
			if (httpConn != null)
				httpConn.disconnect();
		}
		return response;
	}

	private String getDataOverHttp() {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(
				httpConn.getInputStream()))) {
			String line = br.readLine();
			logger.info(line);
			return line;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

}
