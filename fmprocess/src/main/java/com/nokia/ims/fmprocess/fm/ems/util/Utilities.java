package com.nokia.ims.fmprocess.fm.ems.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class Utilities {

	private static HashMap<String, String> aiParam = new HashMap<String, String>();
	public static String aiParamPath = "/tspinst/scripts/aiParameter.sh";

	static {
		if(aiParam.isEmpty())
			setConfig(aiParam, aiParamPath);
	}
	
	private static void setConfig(HashMap<String, String> configObjRef, String configFileName) {
		String[] param = new String[2];
		try (BufferedReader br = new BufferedReader(new FileReader(configFileName))) {
			String str;
			while ((str = br.readLine()) != null) {
				str = str.trim();
				if(str.length()>0 && str.contains("=") && str.charAt(0)!='#') {
					param = str.split("=");
					if(param.length>1){
						configObjRef.put(param[0].trim(), param[1].trim());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getParam(String key) {
		return aiParam.getOrDefault(key, null);
	}

	public String getCMServerIP() {

		String cmRepoMode = getParam("CmRepoMode");
		String cmRepoIp = getParam("CmRepoIp");
		String cmRepoHAIp = getParam("CmRepoHAIp");
		String highAvailability = getParam("HA");
		String cmServerAddress = null;
		String ipAddress = cmRepoIp;
		if (cmRepoMode != null) {
			if (cmRepoIp != null) {
				if ("TRUE".equalsIgnoreCase(highAvailability))
					ipAddress = cmRepoHAIp;
				else if (cmRepoIp.contains(",")) {
					if (cmRepoMode.equalsIgnoreCase("Primary"))
						ipAddress = cmRepoIp.split(",")[0].trim();
					else
						ipAddress = cmRepoIp.split(",")[1].trim();
				}
				if (ipAddress.contains(":"))
					cmServerAddress = "[" + ipAddress + "]";
				else
					cmServerAddress = ipAddress;
			}
		}
		return cmServerAddress;
	}
}
