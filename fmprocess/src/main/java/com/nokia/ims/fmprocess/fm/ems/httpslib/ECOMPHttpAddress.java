package com.nokia.ims.fmprocess.fm.ems.httpslib;

public class ECOMPHttpAddress {

	private String domainName;
	private String port;
	private String routingPath;
	private String apiVer;
	private String handShakeProtocol;
	private String credentials;

	public ECOMPHttpAddress(String domainName, String port, String routingPath,
			String apiVer, String handShakeProtocol, String credentials) {
		this.port = port;
		this.domainName = domainName;
		this.routingPath = routingPath;
		this.apiVer = apiVer;
		this.handShakeProtocol = handShakeProtocol;
		this.credentials = credentials;
	}

	public String getDomainName() {
		return domainName;
	}

	public String getPort() {
		return port;
	}

	public String getHandShakeProtocol() {
		return handShakeProtocol;
	}

	public String getApiVer() {
		return apiVer;
	}

	public String getRoutingPath() {
		return routingPath;
	}

	public String getCredentials() {
		return credentials;
	}
	
	public boolean compare(ECOMPHttpAddress prevFQDN) {
		if ( !this.getDomainName().equalsIgnoreCase(prevFQDN.getDomainName()) 
				|| !this.getCredentials().equalsIgnoreCase(prevFQDN.getCredentials())
				|| !this.getPort().equalsIgnoreCase(prevFQDN.getPort())
				|| !this.getRoutingPath().equalsIgnoreCase(prevFQDN.getRoutingPath())
				|| !this.getApiVer().equalsIgnoreCase(prevFQDN.getApiVer()) ) {
			return true;
		}
		return false;
	}
}
