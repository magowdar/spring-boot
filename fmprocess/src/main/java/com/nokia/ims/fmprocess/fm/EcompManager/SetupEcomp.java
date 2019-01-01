package com.nokia.ims.fmprocess.fm.EcompManager;

import com.nokia.ims.fmprocess.fm.ems.httpslib.ECOMPHttpAddress;
import com.nokia.ims.fmprocess.fm.ems.httpslib.HeartBeatLibrary;
import com.nokia.ims.fmprocess.fm.ems.httpslib.TaskScheduler;
//import com.nokia.ims.fmprocess.fm.NativeCodeEngine.NativeCode;
import com.nokia.ims.fmprocess.fm.PipelineEngine.EcompEngine.EcompOutputHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class SetupEcomp extends Thread {

	static Logger logger = LogManager.getLogger(SetupEcomp.class.getName());

	public static ArrayList<HashMap<String, String>> ecompTableList = new ArrayList<HashMap<String, String>>();
	public static HashMap<Integer, ECOMPHttpAddress> ecompMap = new HashMap<Integer, ECOMPHttpAddress>();

	public static int hbInterval;
	private static TaskScheduler heartBeatSchedule;
	private static HeartBeatLibrary heartBeatRef;

	public static void setEcompManager() {
		
		//hbInterval = NativeCode.getEcompHBInterval();
		hbInterval = 1;
		logger.info("HB interval is "+hbInterval);
		/*if (NativeCode.getEcompTableFromNative(ecompTableList) > 0) {
			logger.info("Ecomp Table Registration to TSP is success");
		} else {
			logger.info("Ecomp Table is Empty so Checking Scalar Parameters");
			NativeCode.getEcompScalarParamFromNative();
		}*/
		initEcompAddress();
		startHeartBeat();
	}
	
	public static void startHeartBeat() {
		if(heartBeatRef != null) {
			heartBeatRef.closeConnection();
		}
		heartBeatRef = new HeartBeatLibrary(SetupEcomp.ecompMap);
		if (heartBeatSchedule != null) {
			heartBeatSchedule.endTimer();
			heartBeatSchedule = null;
		}
		heartBeatSchedule = new TaskScheduler(heartBeatRef, new Date(), SetupEcomp.hbInterval);
		heartBeatSchedule.startTimer();
		logger.info("HeartBeat Timer triggered");
	}

	public static void initEcompAddress() {
		ecompMap.clear();
		try {
			for (HashMap<String, String> addMap : ecompTableList) {
				Integer precedence = Integer.parseInt(addMap.get("Precedence"));
				String domain = addMap.get("Domain");
				String port = addMap.get("Port");
				String routingPath = addMap.get("RoutingPath");
				String apiVer = "5";
				String credential = addMap.get("Credentials");
				if( !(credential.isEmpty()) )
					credential = "credential";
				logger.info("Ecomp info " + precedence + " " + domain + " "
						+ port + " " + routingPath + " " + apiVer + " "
						+ credential);
				if(!domain.isEmpty())
					ecompMap.put(precedence, new ECOMPHttpAddress(domain, port,
						routingPath, apiVer, "TLSv1", credential));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		ecompTableList.clear();
	}

	public static void setHeartBeatInterval(Integer hbInterval) {
		if(hbInterval != 0) {
			SetupEcomp.hbInterval = hbInterval;
		}
		startHeartBeat();
	}

	public static void setEcompScalar() {
		initEcompAddress();
		startHeartBeat();
		EcompOutputHandler.ecompInstanceRunning = false;
	}

	public static void setEcompTable() {
		initEcompAddress();
		ECOMPHttpAddress prevFQDN = ecompMap.get(HeartBeatLibrary.precedence);
		if (HeartBeatLibrary.connectedFQDN == null || prevFQDN == null) {
			logger.info("ECOMP Address objects were null");
			startHeartBeat();
			EcompOutputHandler.ecompInstanceRunning = false;
		} else {
			logger.info("Previous connected ecomp "+HeartBeatLibrary.connectedFQDN.getDomainName());
			logger.info("NewEcompMap at precedence "+HeartBeatLibrary.precedence + " instance "+prevFQDN.getDomainName());
			if ( HeartBeatLibrary.connectedFQDN.compare(prevFQDN)) {
				logger.info("Comparison of ECOMPAddress is "+ false);
				startHeartBeat();
				EcompOutputHandler.ecompInstanceRunning = false;
			} else
				logger.info("Comparison of ECOMPAddress is "+ true);
		}
	}
}
