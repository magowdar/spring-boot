package com.nokia.ims.fmprocess.fm.UtilityEngine;

/*import EncodeDecodeUtil;
import EncryptDecryptUtil;*/
/*import com.nokia.j2ssp.comp.esymac.api.FaultManager;
import com.nokia.j2ssp.comp.esymac.api.GUI;*/

import java.util.Properties;

public class FmUtil {

	public static String DataBasePathEsymac;
	public static String DataBasePathEcomp;
	public static String DataBaseDriver;
	public static String DataBaseUser;
	public static String DataBasePassword;

	public static String cmRepoCompName = "platform/oam/omfmagent";

	public static int MSGSET_OFFSET = 65536;
	public static int LOAD_DATA_ALARM_NUMBER = 170 * MSGSET_OFFSET + 900;
	public static int LOAD_DATA_ALARM_SEVERITY = 2;

	public static String ECR_EXT_PATTERN;
	public static String DDR_EXT_PATTERN;

	public static String ECR_PATH_1;
	public static String ECR_PATH_2;

	public static String LAST_SEQ_NUM_FILE;

	public static String ADAPTATIONS_PATH ;
	public static String ADAPTATION_TXT ;
	public static String ADAPTATION_FILE_EXT = ".cmb";
	public static int MAX_TRIES;
	// NE3S AlarmType definitions
	public static int NE3S_ALMTYPE_COMMUNICATION = 2;
	public static int NE3S_ALMTYPE_ENVIRONMENT = 3;
	public static int NE3S_ALMTYPE_EQUIPMENT = 4;
	public static int NE3S_ALMTYPE_PROCESSING = 10;
	public static int NE3S_ALMTYPE_QAULITYOFSERVICE = 11;

	// NE3S ProbableCause definitions
	public static int NE3S_PROBCAUSE_INDETERMINATE = 0;
	public static int NE3S_PROBCAUSE_COMMUNICAT = 306;
	public static int NE3S_PROBCAUSE_SERVICE = 308;
	public static int NE3S_PROBCAUSE_PROCESSING = 302;
	public static int NE3S_PROBCAUSE_EQUIPMENT = 305;
	public static int NE3S_PROBCAUSE_ENVIRONMENT = 155;
	public static int NE3S_PROBCAUSE_DB_ALARM = 537;

	public static int HISTORY_SYNC_LIMIT;
	public static int COMPLETE_HISTORY_SYNC_LIMIT;
	public static int TSP_RECOVER_LIMIT;
	public static int MAX_QUEUE_SIZE;

	public static boolean SyncLimitFlag = false;
	
/*	public static GUI EsymacGUIManager;
	public static FaultManager EsymacFaultManager;*/
	public static String EsymacDeployXML;
	public static String EsymacDeployClassName;
		
	public static String EncryptDecryptBinPath;
	public static boolean MODIFY_TEXT;
	public static int priorityThreshold;

	public static  String HEARTBEAT_BUFFER_FILE;

	public static  String HEARTBEAT_BUFFER_FILE_DIR;
	
	public static int getAlarmSpecificId(String eventMsgSetAndId) {
		int eventSetAndId = 0;
		try {
			eventMsgSetAndId.replaceAll("_", "-");
			String[] msgParts = eventMsgSetAndId.split("-");
			if (msgParts.length == 2) {
				eventSetAndId = Integer.parseInt(msgParts[0]) * 65536 + Integer.parseInt(msgParts[1]);
			}
		} catch (NumberFormatException nxe) {
			nxe.printStackTrace();
		}
		return eventSetAndId;
	}

	public static boolean isLoadDataEvent(long eventId, int severity) {
		if (eventId == FmUtil.LOAD_DATA_ALARM_NUMBER
				&& severity == FmUtil.LOAD_DATA_ALARM_SEVERITY)
			return true;
		return false;
	}

	public static void setAllProperties(Properties basicProp){

		DataBasePathEsymac = basicProp.getProperty("DataBasePathEsymac");
		DataBasePathEcomp = basicProp.getProperty("DataBasePathEcomp");
		DataBaseDriver = basicProp.getProperty("DataBaseDriver");
		DataBaseUser = basicProp.getProperty("DataBaseUser");
		DataBasePassword = basicProp.getProperty("DataBasePassword");
		EsymacDeployXML =  basicProp.getProperty("EsymacDeployXML");
		EsymacDeployClassName = basicProp.getProperty("EsymacDeployClassName");
		
		LAST_SEQ_NUM_FILE = basicProp.getProperty("LAST_SEQ_NUM_FILE");
		HISTORY_SYNC_LIMIT = Integer.parseInt(basicProp.getProperty("HISTORY_SYNC_LIMIT"));
		COMPLETE_HISTORY_SYNC_LIMIT = Integer.parseInt(basicProp.getProperty("COMPLETE_HISTORY_SYNC_LIMIT"));
		TSP_RECOVER_LIMIT = Integer.parseInt(basicProp.getProperty("TSP_RECOVER_LIMIT"));
		MAX_QUEUE_SIZE = Integer.parseInt(basicProp.getProperty("MAX_QUEUE_SIZE"));
	
		ECR_EXT_PATTERN = basicProp.getProperty("ECR_EXT_PATTERN");
		DDR_EXT_PATTERN = basicProp.getProperty("DDR_EXT_PATTERN");
		ECR_PATH_1 = basicProp.getProperty("ECR_PATH_1");
		ECR_PATH_2 = basicProp.getProperty("ECR_PATH_2");
		
		ADAPTATIONS_PATH = basicProp.getProperty("ADAPTATIONS_PATH");
		ADAPTATION_TXT = basicProp.getProperty("ADAPTATION_TXT");
		ADAPTATION_FILE_EXT = basicProp.getProperty("ADAPTATION_FILE_EXT");

		MAX_TRIES = Integer.parseInt(basicProp.getProperty("MAX_TRIES"));
		MODIFY_TEXT = Boolean.parseBoolean(basicProp.getProperty("MODIFY_TEXT"));
		EncryptDecryptBinPath = basicProp.getProperty("EncryptDecryptBinPath");
		HEARTBEAT_BUFFER_FILE_DIR = basicProp.getProperty("HEARTBEAT_BUFFER_DIR_PATH");
		HEARTBEAT_BUFFER_FILE = basicProp.getProperty("HEARTBEAT_BUFFER_PATH");
	}

	public static int mapEventType2ProbableCause(int eventType) {
		int probableCause = 0;
		switch (eventType) {
		case 1:
			probableCause = NE3S_PROBCAUSE_COMMUNICAT; // Communication
			break;
		case 2:
			probableCause = NE3S_PROBCAUSE_SERVICE; // Service
			break;
		case 3:
			probableCause = NE3S_PROBCAUSE_PROCESSING; // Processing
			break;
		case 4:
			probableCause = NE3S_PROBCAUSE_EQUIPMENT; // Equipment
			break;
		case 5:
			probableCause = NE3S_PROBCAUSE_ENVIRONMENT; // Environment
			break;
		case 6:
		case 7:
			probableCause = NE3S_PROBCAUSE_DB_ALARM; // DB alarm
			break;
		default:
			probableCause = NE3S_PROBCAUSE_INDETERMINATE;
		}
		return probableCause;
	}

	public static int mapEventType2EsymacAlarmType(int eventType) {
		int alarmType = 0;
		switch (eventType) {
		case 1:
			alarmType = NE3S_ALMTYPE_COMMUNICATION;
			break;
		case 2:
			alarmType = NE3S_ALMTYPE_QAULITYOFSERVICE;
			break;
		case 3:
			alarmType = NE3S_ALMTYPE_PROCESSING;
			break;
		case 4:
			alarmType = NE3S_ALMTYPE_EQUIPMENT;
			break;
		case 5:
			alarmType = NE3S_ALMTYPE_ENVIRONMENT;
			break;
		default:
			alarmType = NE3S_ALMTYPE_QAULITYOFSERVICE;
			break;
		}
		return alarmType;
	}

	/*public static String decryptAndEncode(String toDecrypt){
		String decrypted = EncryptDecryptUtil.decrypt(EncryptDecryptBinPath, toDecrypt);
		return EncodeDecodeUtil.base64encode(decrypted);
		
	}*/
}
