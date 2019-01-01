package com.nokia.ims.fmprocess.fm.EcompManager;

//import com.nokia.YamlApi.YamlConfigLibrary;
import com.nokia.ims.fmprocess.fm.ems.cmparamlib.CMConfigParamLibrary;
import com.nokia.ims.fmprocess.fm.ems.util.Utilities;
import com.nokia.ims.fmprocess.fm.EventObjectHolder.FmEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Properties;

public class JSONAlarm {

	private static String vmName;
	private static String domain;
	private String eventId;
	private String ecompEventId;
	private long sequence;
	private long ecompSeqId;
	private String priority;
	private String sourceId;
	private String sourceName;
	private String functionalRole;
	private long startEpochMicrosec;
	private long lastEpochMicrosec;
	private String reportingEntityId;
	private String reportingEntityName;

	private static String nfNamingCode;
	private static String nfcNamingCode;

	private String specificProblem;
	private String eventSeverity;
	private String vfStatus;
	private String alarmCause;
	private static String asdcName = null;
	private String alarmName;
	private String alarmTypeString;
	private String alarmInterfaceA;
	//private static YamlConfigLibrary yml;

	// private final String vesPropsPath = "/opt/agents/utils/res/VES.prop";
	private static String vnfTools = "/opt/swrepo/conf/vmtools.conf";
	// private final String nfcNamingPropsPath =
	// "/opt/agents/utils/res/vesNeTypeToNfcNamingMap.prop";
	static Logger logger = LogManager.getLogger(JSONAlarm.class.getName());

	static {
		try {
			Process proc = Runtime.getRuntime().exec("/bin/hostname");
			try (BufferedReader procInp = new BufferedReader(new InputStreamReader(proc.getInputStream()))) {
				String output;
				if ((output = procInp.readLine()) != null) {
					vmName = output;
				}
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		try {
		//	yml = new YamlConfigLibrary();
		//	logger.info("YAML obj "+yml);
			CMConfigParamLibrary cmrelayRef = new CMConfigParamLibrary();
			asdcName = cmrelayRef.getParamValue("AsdcModel", "platform/oam/omagentintegration");
			logger.info("asdcName ==" + asdcName);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		try (FileReader vesReader = new FileReader(vnfTools)) {
			Properties allProp = new Properties();
			allProp.load(vesReader);
			String nfNameCode = allProp.getProperty("VFNamingCode");
			if (nfNameCode == null || nfNameCode.isEmpty()) {
				nfNameCode = "cmcf";
			}
			nfNamingCode = nfNameCode;
			logger.info("nfNamingCode == "+nfNamingCode );
			nfcNamingCode = "cmr" ;
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

	public JSONAlarm(FmEvent evt) {
		try {
			functionalRole = Utilities.getParam("NEType");
			if ("CMREPO".equalsIgnoreCase(functionalRole)) {
				sourceName = vmName;
				sourceId = Utilities.getParam("VmUuid");
			} else {
				sourceName = Utilities.getParam("VmName");
				sourceId = Utilities.getParam("VmUuid");
			}
			eventId = evt.EVENT_ID;
			alarmInterfaceA = evt.EVENT_FAULTY_OBJECT;
			logger.info("eventId == "+eventId );
			logger.info("asdcName == "+asdcName );
			alarmName = "alarmName_Dummy";
			logger.info("alarmName == "+alarmName);
			
			ecompEventId = String.format("001-%012d", evt.ECOMP_EVENT_ID);
			sequence = evt.SEQUENCE_ID;
			ecompSeqId = evt.ECOMP_SEQUENCE_ID;
			if (evt.EVENT_SEVERITY == 6)
				priority = "Normal";
			else
				priority = "High";
			reportingEntityName = vmName;

			alarmTypeString = mapAlarmType2String(evt.EVENT_TYPE);
			startEpochMicrosec = evt.ECOMP_EVENT_TIME * 1000;
			lastEpochMicrosec = evt.EVENT_TIME * 1000;

			reportingEntityId = Utilities.getParam("VmUuid");
			setEventSeverity(evt.EVENT_SEVERITY);
			alarmCause = mapEventType2ProbableCause(evt.EVENT_TYPE);
			specificProblem = evt.EVENT_SHORT_TEXT.replaceAll("\"", "");
			vfStatus = "Active";
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

	public void setEventSeverity(int severity) {
		this.eventSeverity = getMappedSeverity(severity);
	}

	private String getMappedSeverity(int severity) {
		switch (severity) {
		case 1:
			return "CRITICAL";
		case 2:
			return "MAJOR";
		case 3:
			return "MINOR";
		case 4:
			return "WARNING";
		case 6:
			return "NORMAL";
		default:
			return "WARNING";
		}
	}

	public static String mapAlarmType2String(int alarmType) {
		switch (alarmType) {
		case 2:
			return "communication";
		case 3:
			return "environmental";
		case 4:
			return "equipment";
		case 10:
			return "processing";
		case 11:
			return "qualityOfService";
		default:
			return "qualityOfService";
		}
	}

	public static String mapEventType2ProbableCause(int eventType) {
		switch (eventType) {
		case 1:
			return "communication";
		case 2:
			return "service";
		case 3:
			return "processing";
		case 4:
			return "equipment";
		case 5:
			return "environment";
		case 6:
		case 7:
			return "dbalarm";
		default:
			return "";
		}
	}

	public String getEventType() {
		String[] msgParts = eventId.split("-");
		int eventSet = Integer.parseInt(msgParts[0]);
		if (eventSet == 141) {
			if (msgParts[1].startsWith("30")) {
				return "cmrepo";
			}
			return "guestOS";
		} else if (eventSet > 1 && eventSet < 173)
			return "platform";
		else if (eventSet >= 173) {
			return "applicationVnf";
		}
		return "applicationVnf";
	}

	public String getDomain() {
		return "fault";
	}

	public String getVersion() {
                return "3.0";
        }
		
	public String getFaultFieldsVersion() {
                return "2.0";
        }

	public String getEventId() {
		return ecompEventId;
	}

	public long getSequence() {
		return ecompSeqId;
	}

	public String getEcompEventId() {
		return eventId;
	}

	public long getEcompSequence() {
		
		return sequence;
	}

	public String getPriority() {
		return priority;
	}

	public String getSourceId() {
		return sourceId;
	}

	public String getSourceName() {
		return sourceName;
	}

	public String getFuncRole() {
		return functionalRole;
	}

	public long getStartEpoch() {
		return startEpochMicrosec;
	}

	public long getLastEpoch() {
		return lastEpochMicrosec;
	}

	public String getEntityId() {
		return reportingEntityId;
	}

	public String getNfNamingCode() {
		return nfNamingCode;
	}
	
	public String getNfcNamingCode() {
		return nfcNamingCode;
	}
	
	public String getEntityName() {
		return reportingEntityName;
	}

	public String getAlarmCondition() {
		return alarmName;
	}

	public String getEventSeverity() {
		return eventSeverity;
	}

	public String getVFStatus() {
		return vfStatus;
	}

	public String getSpecificProblem() {
		return specificProblem;
	}

	public String getAlarmProbableCause() {
		return alarmCause;
	}
	
	public String getEventCategory() {
		return "dummyEventCategory";
	}
	
	public String getEventName() {
		return "Fault_Nokia_" + asdcName + "_" + alarmName + "-" + eventId;
	}
	
	public String getAlarmType() {
		return alarmTypeString;
	}
	
	public String getAlarmInterfaceA() {
        	return alarmInterfaceA;
	}
}
