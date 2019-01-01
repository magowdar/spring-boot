package com.nokia.ims.fmprocess.fm.PipelineEngine.CriticalEngine;

import com.nokia.ims.fmprocess.fm.EventObjectHolder.FmEvent;
import com.nokia.ims.fmprocess.fm.PipelineEngine.PipelineStage;
//import com.nokia.j2ssp.comp.esymac.fm.Alarm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*This stage is used to convert an FmEvent Object to Esymac alarm for minor alarms*/
public class CriticalConvertStage extends PipelineStage {

	static Logger logger = LogManager.getLogger(CriticalConvertStage.class.getName());

	private CriticalEsymacOutputHandler outPutRef;

	public CriticalConvertStage(CriticalEsymacOutputHandler outPutRef) {
		this.outPutRef = outPutRef;
	}

	public void run() {
		while (true) {
			FmEvent alarm;
			try {
				alarm = read();
				logger.info("Alarm read " + alarm.SEQUENCE_ID);
				//outPutRef.write(mapToEsymacEvent(alarm));
				logger.debug("Alarm Sent to OutputHandler is " + alarm.SEQUENCE_ID + " " + alarm.EVENT_SHORT_TEXT + " "
						+ alarm.SEQUENCE_ID + " " + alarm.EVENT_SHORT_TEXT + " " + alarm.EVENT_ID);
			} catch (InterruptedException e) {
				e.printStackTrace();
				logger.error(e);
			}
		}
	}
	
	/*public Alarm mapToEsymacEvent(FmEvent event) {

		String eventMsgSetAndId = event.EVENT_ID;
		Alarm alrm = new Alarm();

		try {
			String faultyObject = event.EVENT_FAULTY_OBJECT;
			faultyObject = faultyObject.replaceAll(":", "/").replaceAll("_",
					"-");
			String moi = FmAdaptationEngine.getMOI(event.EVENT_ID);
			String alarmText = FmAdaptationEngine.getAlarmText(event.EVENT_ID);
			alrm.setNoiAlarmAlarmSystemDN(moi);
			alrm.setInternalId(Long.toString(event.SEQUENCE_ID));
			alrm.setNoiAlarmEventTime(event.EVENT_TIME);
			alrm.setNoiAlarmPerceivedSeverity(event.EVENT_SEVERITY);
			alrm.setNoiAlarmEventType(FmUtil
					.mapEventType2EsymacAlarmType(event.EVENT_TYPE));

			if (event.EVENT_CLEAR_USER != null)
				alrm.setClearUser(event.EVENT_CLEAR_USER);

			alrm.setNoiAlarmAckState(0);
			String mainAlarmText = "Fault in " + alarmText + " ("
					+ eventMsgSetAndId + ")";
			if (faultyObject != null) {
				mainAlarmText = mainAlarmText + " - [" + faultyObject + "]";
			}
			if(FmUtil.getAlarmSpecificId(eventMsgSetAndId)<=7471127 && FmUtil.getAlarmSpecificId(eventMsgSetAndId)>=7471124)
				mainAlarmText = "Alarm-Burst is happening on this NE: flood control is activated. Fault ID ("
					+ eventMsgSetAndId + ")";
			if(FmUtil.MODIFY_TEXT){
                                alrm.setNoiAlarmText(event.EVENT_SHORT_TEXT);
                                alrm.setNoiAlarmAdditionalText(mainAlarmText);
                        } else {
                                alrm.setNoiAlarmText(mainAlarmText);
                                alrm.setNoiAlarmAdditionalText(event.EVENT_SHORT_TEXT);
                        }
			alrm.setNoiAlarmProbableCause(FmUtil
					.mapEventType2ProbableCause(event.EVENT_TYPE));
			alrm.setNoiAlarmSpecificProblem(event.SPECIFIC_PROBLEM);
			logger.debug("Alarm conversion is Success for " + event.SEQUENCE_ID);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return alrm;
	}*/
}
