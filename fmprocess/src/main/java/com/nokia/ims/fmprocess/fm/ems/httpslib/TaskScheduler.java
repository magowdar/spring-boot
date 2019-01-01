package com.nokia.ims.fmprocess.fm.ems.httpslib;

import com.nokia.ims.fmprocess.fm.PipelineEngine.EcompEngine.EcompOutputHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TaskScheduler {
	
	static Logger logger = LogManager.getLogger(EcompOutputHandler.class.getName());
	
	private Timer taskTimer;

	private int interval;
	private Date period;
	private TimerTask taskToPerform;

	public TaskScheduler(TimerTask taskToPerform, Date period, int interval) {
		this.interval = interval;
		this.taskToPerform = taskToPerform;
		this.period = period;
	}

	public void startTimer() {
		try {
			taskTimer = new Timer();
			taskTimer.schedule(taskToPerform, period, (long) interval * 1000);
			logger.info("HeartBeat Scheduler started");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void endTimer() {
		taskTimer.cancel();
		taskTimer.purge();
		taskTimer = null;
	}
}
