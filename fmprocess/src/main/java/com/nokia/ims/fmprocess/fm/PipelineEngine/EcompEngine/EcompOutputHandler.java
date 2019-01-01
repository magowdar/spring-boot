package com.nokia.ims.fmprocess.fm.PipelineEngine.EcompEngine;

import com.nokia.ims.fmprocess.fm.ems.httpslib.ECOMPInterfaceRESTLibrary;
import com.nokia.ims.fmprocess.fm.ems.httpslib.HeartBeatLibrary;
import com.nokia.ims.fmprocess.fm.ems.httpslib.JSONGenerator;
import com.nokia.ims.fmprocess.fm.EcompManager.JSONAlarm;
import com.nokia.ims.fmprocess.fm.EcompManager.SetupEcomp;
import com.nokia.ims.fmprocess.fm.EventObjectHolder.AlarmWrapper;
import com.nokia.ims.fmprocess.fm.NativeCodeEngine.FmSequenceNumberHandler;
import com.nokia.ims.fmprocess.fm.PipelineEngine.OutputHandler;
import com.nokia.ims.fmprocess.fm.UtilityEngine.FmUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

public class EcompOutputHandler extends OutputHandler {

	static Logger logger = LogManager.getLogger(EcompOutputHandler.class.getName());

	private ArrayBlockingQueue<JSONAlarm> dataSource = new ArrayBlockingQueue<JSONAlarm>(20000);

	private boolean lockFlag = false;
	private ArrayList<String> ecompURL;

	private static ECOMPInterfaceRESTLibrary ecompRestRef;
	public static boolean ecompInstanceRunning = false;
	protected static JSONAlarm ecompAlarm;
	private static JSONGenerator jsonGen;

	public EcompOutputHandler() {
		SetupEcomp.setEcompManager();
	}

	public void run() {
		while (true) {
			if (ecompInstanceRunning) {
				read();
				sendData();
			} else {
				try {
					sleep(10000);
					while((ecompURL = HeartBeatLibrary.getConnectedURL()) == null) {
						while(dataSource.size() > FmUtil.HISTORY_SYNC_LIMIT) {
							logger.error("Alarm Failure happened cannot hold now  so alarm dropped SeqId "+ ecompAlarm.getEcompSequence());
							FmSequenceNumberHandler.setLastSequenceNumber(ecompAlarm.getEcompSequence());
							ecompAlarm = null;
							read();
						}
					}
					if(ecompRestRef != null) {
						ecompRestRef.closeConnection();
					}
					ecompRestRef = new ECOMPInterfaceRESTLibrary(ecompURL.get(0), ecompURL.get(1));
					ecompInstanceRunning = true;
					logger.info("Will try to send Alarm to URL "+ecompURL.get(0));
				} catch (Exception e) {
					logger.error(e);
				}
			}
		}
	}

	@Override
	public void write(AlarmWrapper alarmWrapper) {
		JSONAlarm esymacAlarm = new JSONAlarm(alarmWrapper.getFmAlarm());
		write(esymacAlarm);
	}

	public void write(JSONAlarm alarm) {
		try {
			dataSource.put(alarm);
			if (lockFlag) {
				synchronized (this) {
					this.notify();
					lockFlag = false;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	public void read() {
		try {
			while (ecompAlarm == null) {
				ecompAlarm = dataSource.poll();
				if (ecompAlarm == null && !lockFlag) {
					synchronized (this) {
						lockFlag = true;
						this.wait();
						lockFlag = false;
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	public void sendData() {
		try {
			jsonGen = new JSONGenerator(ecompAlarm, "FMJson.tmpl");
			String jsonAlarm = jsonGen.getJSON();
			logger.info("Ecomp Json file " + jsonAlarm);
			if (jsonAlarm != null) {
				int success = ecompRestRef.sendDataOverHTTPS(jsonAlarm);
				if (success != 200) {
					ecompInstanceRunning = false;
					logger.info("Failed to send alarm to ecomp " + ecompAlarm.getEcompSequence() + " ecompRunning set to "
							+ ecompInstanceRunning);
				} else {
					logger.info("Sent Alarm to Ecomp Successfully " + ecompAlarm.getEcompSequence());
					logger.info("Clearing ecompAlarm Reference ");
					FmSequenceNumberHandler.setLastSequenceNumber(ecompAlarm.getEcompSequence());
					ecompAlarm = null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
