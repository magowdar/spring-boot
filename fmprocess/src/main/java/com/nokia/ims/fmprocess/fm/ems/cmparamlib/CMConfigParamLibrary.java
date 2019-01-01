package com.nokia.ims.fmprocess.fm.ems.cmparamlib;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class CMConfigParamLibrary {

	private static Logger logger = LogManager
			.getLogger(CMConfigParamLibrary.class.getName());
	private CMInterfaceRESTLibrary connectionRef;
	
	public CMConfigParamLibrary(){
		connectionRef = new CMInterfaceRESTLibrary();
	}

	public String getParamValue(String paramName, String compName) {
		String valueOfParam = null;
		try {
			String requestURL = "http://localhost:9997/component/" + compName + "/parameter/"
					+ paramName;
			String response = getCMResponse(requestURL);
			valueOfParam = getParamObject(response)
					.getJSONObject("ParamScalar").getJSONObject("Param")
					.getString("Value");
		} catch (Exception e) {
			logger.error(e);
		}
		return valueOfParam;
	}

	private JSONObject getParamObject(String response) {
		JSONObject paramObject = null;
		logger.debug("Response inside cm library " + response);
		try {
			paramObject = new JSONObject(response).getJSONArray("Dus")
					.getJSONObject(0).getJSONArray("ReleaseTag")
					.getJSONObject(0).getJSONArray("Nodes").getJSONObject(0)
					.getJSONArray("Comps").getJSONObject(0)
					.getJSONArray("PMaps").getJSONObject(0);
		} catch (Exception e) {
			logger.error(e);
		}
		return paramObject;
	}

	private String getCMResponse(String requestURL) {
		return connectionRef.getDataOverREST(requestURL);
	}
}
