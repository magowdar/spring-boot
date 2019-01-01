package com.nokia.ims.fmprocess.fm.ems.httpslib;

import com.nokia.ims.fmprocess.fm.ems.cmparamlib.CMConfigParamLibrary;
import com.nokia.ims.fmprocess.fm.ems.util.Utilities;
import com.nokia.ims.fmprocess.fm.EcompManager.SetupEcomp;
import com.nokia.ims.fmprocess.fm.UtilityEngine.FmUtil;
import com.nokia.ims.util.fileutil.FileUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class JSONHeartBeat {
	
	private static Logger logger = LogManager.getLogger(JSONHeartBeat.class.getName());

	private static String domain;
	private static String version;
	
	private static String funcRole;
	private static String eventName;
	
	private static String sourceId;
	private static String sourceName;
	
	private static String nfNamingCode;
	private static String nfcNamingCode;
	
	private long currentEpochTime;
	private static String eventIdFormat;
	
	private static String heartbeatventType;
	private static String heartbeatFieldsVersion;
	
	public static String vmName;
	private static String asdcName;

	public static int getEventNumber() {
		return eventNumber;
	}

	public static void setEventNumber(int eventNumber) {
		JSONHeartBeat.eventNumber = eventNumber;
	}

	private static int eventNumber;

	private final String vesPropsPath = "/opt/agents/utils/res/VES.prop";
	private final String vnfTools = "/opt/swrepo/conf/vmtools.conf";
	private final String nfcNamingPropsPath = "/opt/agents/utils/res/vesNeTypeToNfcNamingMap.prop";


	static {
		/*try {
			Process proc = Runtime.getRuntime().exec("/bin/hostname");
			try (BufferedReader procInp = new BufferedReader(
					new InputStreamReader(proc.getInputStream()))) {
				String output;
				while ((output = procInp.readLine()) != null) {
					vmName = output;
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

	public JSONHeartBeat() {
		try {
			/*funcRole = Utilities.getParam("NEType");
			sourceId = Utilities.getParam("VmUuid");*/
			funcRole = "NEType";
			sourceId = "VmUuid";
			sourceName = "vmName";
			if ("CMREPO".equalsIgnoreCase(funcRole)) {
				//sourceName = vmName;
			} else {
				//sourceName = Utilities.getParam("VmName");
			}
			
			try {
				CMConfigParamLibrary cmrelayRef = new CMConfigParamLibrary();
				//asdcName = cmrelayRef.getParamValue("AsdcModel","platform/oam/omagentintegration");
				asdcName = "CSCFVNF";
				logger.info("ASDC_Model Name is " + asdcName);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e);
			}
			
			// Load VES properties to get domain, version etc
			/*Properties allProp = new Properties();
			try (FileReader vesReader = new FileReader(vesPropsPath)) {
				allProp.load(vesReader);
				version = allProp.getProperty("Version");
				domain = allProp.getProperty("HeartBeatDomain");
				heartbeatventType = allProp.getProperty("HeartBeatEventType");
				eventIdFormat = allProp.getProperty("HeartBeatEventIdFormat");
				heartbeatFieldsVersion = allProp.getProperty("HeartBeatFieldsVersion");
				eventName = allProp.getProperty("HeartBeatEventNamePrefix") + asdcName;
			} catch (IOException e) {
				logger.error(e);
			}*/

			version = "5";
			domain = "heartbeat";
			heartbeatventType = "heartbeatEvtType";
			eventIdFormat = "evtIdType";
			heartbeatFieldsVersion = "1.0";
			eventName = "Heartbeat_Nokia" + asdcName;
			nfcNamingCode = "nfcNamingCode";
			String nfNameCode = "nfNamingCode";
			// Set nfCNamingCode
			/*try (FileReader vesReader = new FileReader(nfcNamingPropsPath)) {
				allProp.load(vesReader);
				nfcNamingCode = allProp.getProperty(funcRole);
			} catch (IOException e) {
				logger.error(e);
			}*/

			// Set nfNamingCode
		/*	try (FileReader vesReader = new FileReader(vnfTools)) {
				allProp.load(vesReader);
				String nfNameCode = allProp.getProperty("VFNamingCode");

				if (nfNameCode == null || nfNameCode.isEmpty()) {
					nfNameCode = "cmcf";
				}
				nfNamingCode = "cmcf";
			} catch (IOException e) {
				logger.error(e);
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getDomain() {
		return domain;
	}

	public String getEventId() {
		setEventNumber(eventNumber);
		logger.info("Event Number is :: " + eventNumber);
		FileUtil.truncateAndWriteIntegerDataToFile(FmUtil.HEARTBEAT_BUFFER_FILE_DIR,FmUtil.HEARTBEAT_BUFFER_FILE,getEventNumber());
		String eventId = eventIdFormat + "FM-" + String.format("%012d", eventNumber);
		eventNumber++;
		logger.info("Event ID is :: " + eventId);
		return eventId;
	}

	public int getSequence() {
		return 0;
	}

	public String getPriority() {
		return "Normal";
	}

	public String getSourceId() {
		return sourceId;
	}

	public String getSourceName() {
		return sourceName;
	}

	public String getFuncRole() {
		return funcRole;
	}

	public long getStartEpoch() {
		logger.info("Calculating startTIME");
		currentEpochTime = System.currentTimeMillis() * 1000;
		return currentEpochTime;
	}

	public long getLastEpoch() {
		logger.info("Calculating lastTIME");
		return currentEpochTime;
	}

	public String getEntityId() {
		return sourceId;
	}

	public String getEntityName() {
		return sourceName;
	}
	
	public String getVersion() {
		return version;
	}
	
	public String getEventName() {
		return eventName;
	}

	public String getHeartbeatFieldsVersion() {
		return heartbeatFieldsVersion;
	}
	
	public int getHeartBeatInterval() {
		return SetupEcomp.hbInterval;
	}

	public String getNfNamingCode() {
		return nfNamingCode;
	}

	public String getNfcNamingCode() {
		return nfcNamingCode;
	}

	public String getEventType() {
		return heartbeatventType;
	}
}
