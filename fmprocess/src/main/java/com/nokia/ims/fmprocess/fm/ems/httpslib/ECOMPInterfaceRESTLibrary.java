package com.nokia.ims.fmprocess.fm.ems.httpslib;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class ECOMPInterfaceRESTLibrary {

	private static Logger logger = LogManager.getLogger(ECOMPInterfaceRESTLibrary.class.getName());

	private HttpClient client;
	private HttpPost post;

	public ECOMPInterfaceRESTLibrary(String url, String credentials) {
		try {
			logger.info("URL " + url);
			post = new HttpPost(url);
			logger.info("Credentials is " + credentials);
			post.addHeader("Authorization", "Basic " + credentials);
			SSLContextBuilder builder = new SSLContextBuilder();
			builder.loadTrustMaterial(null, new TrustStrategy() {
				@Override
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			});
			SSLConnectionSocketFactory sslSF = new SSLConnectionSocketFactory(builder.build(),
					SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

			client = HttpClients.custom().setSSLSocketFactory(sslSF).build();
			logger.info("Connection For Alarms to Ecomp is Prepared");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
	}

	public synchronized int sendDataOverHTTPS(String data) {
		int responseCode = 404;
		try {
			HttpEntity requestBody = new StringEntity(data, ContentType.create("application/json", Consts.UTF_8));
			post.setEntity(requestBody);
			HttpResponse response = client.execute(post);
			responseCode = response.getStatusLine().getStatusCode();
			EntityUtils.consumeQuietly(response.getEntity());
			logger.info("Response from ecomp for alarm is " + responseCode);
		} catch (Exception e) {
			logger.error(e);
		}
		return responseCode;
	}

	public void closeConnection() {
		if(post != null)
			post.releaseConnection();
		if (client != null) {
			try {
				client.getConnectionManager().shutdown();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		logger.info("Closed connection for Alarm ECOMP.");
		client = null;
		post = null;
	}

}
