package com.nokia.ims.fmprocess.fm.PipelineEngine.EcompEngine;

import com.nokia.ims.fmprocess.fm.EventObjectHolder.AlarmWrapper;
import com.nokia.ims.fmprocess.fm.EventObjectHolder.FmEvent;
import com.nokia.ims.fmprocess.fm.PipelineEngine.OutputHandler;
import com.nokia.ims.fmprocess.fm.PipelineEngine.PipelineStage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EcompConvertStage extends PipelineStage {

	static Logger logger = LogManager.getLogger(EcompConvertStage.class.getName());

	private OutputHandler outPutRef;

	public EcompConvertStage(OutputHandler outPutRef) {
		this.outPutRef = outPutRef;
	}

	public void run() {
		while (true) {
			FmEvent alarm;
			try {
				alarm = read();
				logger.info("Alarm read " + alarm.SEQUENCE_ID);
				AlarmWrapper alarmWrapper = new AlarmWrapper(alarm);
				outPutRef.write(alarmWrapper);
				logger.info("Alarm Sent to OutputHandler is " + alarm.SEQUENCE_ID + " " + alarm.EVENT_SHORT_TEXT + " "
						+ alarm.SEQUENCE_ID + " " + alarm.EVENT_SHORT_TEXT + " " + alarm.EVENT_ID);
			} catch (InterruptedException e) {
				logger.error(e);
			}
		}
	}
}
