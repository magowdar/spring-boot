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

import java.net.InetAddress;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.regex.Pattern;

public class HeartBeatLibrary extends TimerTask {

	private static Logger logger = LogManager.getLogger(HeartBeatLibrary.class.getName());

	private static String credentials;
	public static Integer precedence = 0;
	private static HashMap<Integer, ECOMPHttpAddress> fqdnAddressList;
	private static HttpClient client;
	private static HttpPost post;
	private static JSONHeartBeat hbJsonRef = new JSONHeartBeat();

	private static String ecompConnURL = null;

	public static ECOMPHttpAddress connectedFQDN = null;

	public HeartBeatLibrary(HashMap<Integer, ECOMPHttpAddress> ecompMap) {
		fqdnAddressList = ecompMap;
	}

	private String prepURL(String... arg) {
		return "https://" + arg[0] + ":" + arg[1] + "/" + arg[2] + "/v" + arg[3];
	}

	private boolean resolveFQDNAndConnect(ECOMPHttpAddress fqdnInstance) {
		try {
			InetAddress[] inetAddr = InetAddress.getAllByName(fqdnInstance.getDomainName());
			String domain, resolvedUrl = null;
			for (InetAddress ipAddr : inetAddr) {
				try {
					domain = ipAddr.getHostAddress();
					/*if (!isPingable(domain)) {
						return false;
					}*/
					boolean validIPv6Addr = Pattern
							.compile("([0-9a-f]{1,4}:){7}([0-9a-f]){1,4}", Pattern.CASE_INSENSITIVE).matcher(domain)
							.matches();
					if (validIPv6Addr) {
						resolvedUrl = prepURL("[" + domain + "]", fqdnInstance.getPort(), fqdnInstance.getRoutingPath(),
								fqdnInstance.getApiVer());
					} else {
						resolvedUrl = prepURL(domain, fqdnInstance.getPort(), fqdnInstance.getRoutingPath(),
								fqdnInstance.getApiVer());
					}
					credentials = fqdnInstance.getCredentials();
					
					if(setHTTPSSingleConnection(resolvedUrl)) {
						connectedFQDN = fqdnInstance;
						logger.info("Connecting to FQDN instance "+connectedFQDN.getDomainName());
						return true;
					}
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return false;
	}

	private boolean isPingable(String domain) {
		boolean reachable = false;
		try {
			InetAddress address = InetAddress.getByName(domain);
			reachable = address.isReachable(2000);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		logger.info("Is host " + domain + " reachable? " + reachable);
		return reachable;
	}

	private boolean setHTTPSSingleConnection(String url)
			throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {

		logger.info("URL " + url);
		post = new HttpPost(url);
		logger.info("Credentials is " + HeartBeatLibrary.credentials);
		post.addHeader("Authorization", "Basic " + HeartBeatLibrary.credentials);
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
		if (triggerHeartBeat()) {
			ecompConnURL = url;
			return true;
		}
		return false;
	}

	public boolean triggerHeartBeat() {
		JSONGenerator jsonGen = new JSONGenerator(hbJsonRef, "HeartBeat.tmpl");
		String hbJson = jsonGen.getJSON();
		logger.info("HeartBeat data " + hbJson);
		int response = sendDataOverHTTPS(hbJson);
		logger.info("Response for HeartBeat " + response);
		if (response / 100 != 2) {
			return false;
		}
		logger.info("HeartBeat to Ecomp is success");
		return true;
	}

	public synchronized int sendDataOverHTTPS(String data) {
		int responseCode = 404;
		try {
			HttpEntity requestBody = new StringEntity(data, ContentType.create("application/json", Consts.UTF_8));
			post.setEntity(requestBody);
			HttpResponse response = client.execute(post);
			responseCode = response.getStatusLine().getStatusCode();
			EntityUtils.consumeQuietly(response.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return responseCode;
	}

	private void connectToEcomp() {
		ecompConnURL = null;
		try {
			List<Integer> list = new ArrayList<Integer>(fqdnAddressList.keySet());
			Collections.sort(list);
			for (Integer precedence : list) {
				ECOMPHttpAddress fqdn = fqdnAddressList.get(precedence);
				System.setProperty("https.protocols", fqdn.getHandShakeProtocol());
				if (resolveFQDNAndConnect(fqdn)) {
					HeartBeatLibrary.precedence = precedence;
					logger.info("Connecting to Precedence "+precedence);
					break;
				}
			}
			list = null;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
	}

	public static ArrayList<String> getConnectedURL() {
		if (ecompConnURL != null) {
			ArrayList<String> arrayList = new ArrayList<String>();
			arrayList.add(ecompConnURL);
			arrayList.add(credentials);
			return arrayList;
		}
		return null;
	}

	@Override
	public void run() {
		if (ecompConnURL != null) {
			if (!triggerHeartBeat()) {
				closeConnection();
				sendHeartBeat();
			}
		} else {
			sendHeartBeat();
		}
	}
	
	private void sendHeartBeat() {
		if (!fqdnAddressList.isEmpty()) {
			connectToEcomp();
			if (ecompConnURL != null) {
				logger.info("Connecting to URL " + ecompConnURL);
			} else
				logger.info("HeartBeat Failed to reach any ECOMP");
		} else {
			logger.info("Ecomp Address List is not configured. ");
		}
	}

	public void closeConnection() {
		if (ecompConnURL != null) {
			logger.info("Closing connection for HeartBeat.");
		}
		if (post != null)
			post.releaseConnection();
		if (client != null) {
			try {
				client.getConnectionManager().shutdown();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		logger.info("Closed connection for HeartBeat.");
		ecompConnURL = null;
		client = null;
		post = null;
	}
}
