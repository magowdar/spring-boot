package com.nokia.ims.fmprocess.fm.PipelineEngine.CriticalEngine;

/*import com.nokia.ims.fmprocess.fm.EsymacManager.SetupEsymac;
import com.nokia.ims.fmprocess.fm.NativeCodeEngine.FmSequenceNumberHandler;
import com.nokia.ims.fmprocess.fm.UtilityEngine.FmUtil;
import com.nokia.j2ssp.comp.esymac.api.EsymacException;
import com.nokia.j2ssp.comp.esymac.fm.Alarm;*/
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ArrayBlockingQueue;

/*This Thread subclass is used to send Esymac alarm objects to esymac for critical alarms*/
public class CriticalEsymacOutputHandler extends Thread{
	static Logger logger = LogManager.getLogger(CriticalEsymacOutputHandler.class.getName());

	/*private ArrayBlockingQueue<Alarm> dataSource = new ArrayBlockingQueue<Alarm>(
			FmUtil.MAX_QUEUE_SIZE);*/

	private boolean lockFlag = false;
	
	private boolean esymacInstanceRunning = true;
	//protected Alarm esymacAlarm;

	public void run() {
		/*while (true) {
			if (esymacInstanceRunning) {
				read();
				logger.info("Data read from esymac Queue "+esymacAlarm.getInternalId()+ esymacAlarm.getNoiAlarmSystemDN());
				sendData();
			} else {
				try {
					logger.info("Trying to connect esymac again ");
					esymacInstanceRunning = SetupEsymac.setEsymacManager();
				} catch (InterruptedException e) {
					e.printStackTrace();
					logger.error(e);
				}
			}
		}*/
	}

	/*public void write(Alarm alarm) {
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
	}*/

	/*public void read() {
		try {
			while (esymacAlarm == null) {
				esymacAlarm = dataSource.poll();
				if (esymacAlarm == null && !lockFlag) {
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
	}*/
	
	/*public void sendData() {
		try {
			if ("NE system".equalsIgnoreCase(esymacAlarm.getClearUser())) {
				FmUtil.EsymacFaultManager.clearAlarm(esymacAlarm);
				logger.info("Sent ClearAlarm to Esymac Successfully "
						+ esymacAlarm.getInternalId());
			} else {
				FmUtil.EsymacFaultManager.sendAlarm(esymacAlarm);
				logger.info("Sent RaisedAlarm to Esymac Successfully "
						+ esymacAlarm.getInternalId());
				FmSequenceNumberHandler.setLastSequenceNumber(Long.parseLong(esymacAlarm.getInternalId()));
			}
			esymacAlarm = null;
			logger.info("Clearing esymacAlarm Reference ");
		} catch (Exception e) {
			e.printStackTrace();
			if(e instanceof EsymacException){
				esymacInstanceRunning = false;
				try {
					Thread.sleep(20000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			logger.error("Alarm Failure towards esymac SeqId "+ esymacAlarm.getInternalId());
			logger.error(e);
		}
	}*/
}
