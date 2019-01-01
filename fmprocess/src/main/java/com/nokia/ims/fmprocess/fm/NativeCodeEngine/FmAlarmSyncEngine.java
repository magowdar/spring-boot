package com.nokia.ims.fmprocess.fm.NativeCodeEngine;

import com.nokia.ims.fmprocess.fm.EventObjectHolder.FmEvent;
import com.nokia.ims.fmprocess.fm.FMAgentEngine.FMAgent;
import com.nokia.ims.fmprocess.fm.FMAgentEngine.FmEDSEngine;
import com.nokia.ims.fmprocess.fm.PipelineEngine.CriticalEngine.CriticalProcessingPipe;
import com.nokia.ims.fmprocess.fm.PipelineEngine.EcompEngine.EcompProcessingPipe;
import com.nokia.ims.fmprocess.fm.PipelineEngine.FmContext;
import com.nokia.ims.fmprocess.fm.PipelineEngine.MajorEngine.MajorProcessingPipe;
import com.nokia.ims.fmprocess.fm.PipelineEngine.MinorEngine.MinorProcessingPipe;
import com.nokia.ims.fmprocess.fm.PipelineEngine.WarningEngine.WarningProcessingPipe;
import com.nokia.ims.fmprocess.fm.UtilityEngine.FmUtil;
//import com.nokia.j2ssp.comp.esymac.fm.Alarm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

public class FmAlarmSyncEngine extends Thread {

	private ArrayList<FmEvent> eventList = new ArrayList<FmEvent>();
	private static FmNativeCodeEngine nativeRef;
	static Logger logger = LogManager.getLogger(FmAlarmSyncEngine.class.getName());

	public void run() {
		try {
			//nativeRef = new FmNativeCodeEngine();
			long lastSeqProcessed = FmSequenceNumberHandler.getLastSequenceNumber();
			//long latestTspSeq = nativeRef.latestTSPSeqId();
			//if("ESYMAC".equalsIgnoreCase(FMAgent.outputType))
			//	getAlarmsFromEsymac();
			//latestTspSeq = nativeRef.latestTSPSeqId();
			//logger.info("Latest Sequence from TSP is " + latestTspSeq);
			//logger.info("Last Sequence processed was " + lastSeqProcessed);
			if (FmUtil.SyncLimitFlag) {
				/*long startSeq, endSeq;
				if ((FmUtil.COMPLETE_HISTORY_SYNC_LIMIT != 0)
						&& (lastSeqProcessed < (latestTspSeq - FmUtil.COMPLETE_HISTORY_SYNC_LIMIT)))
					lastSeqProcessed = latestTspSeq - FmUtil.COMPLETE_HISTORY_SYNC_LIMIT;
				
				while (latestTspSeq - lastSeqProcessed > FmUtil.TSP_RECOVER_LIMIT) {
					startSeq = lastSeqProcessed + 1;
					endSeq = lastSeqProcessed + FmUtil.TSP_RECOVER_LIMIT;
					nativeRef.recoverTSPEventRange(eventList, startSeq, endSeq);
					logger.info("Events recovered from TSP in range [" + startSeq + " - " + endSeq + "] and size is "
							+ eventList.size());
					sendToAlarmProcessing();
					eventList.clear();
					lastSeqProcessed = endSeq;
					latestTspSeq = nativeRef.latestTSPSeqId();
				}
				FmContext.startNotifyEvent = true;
				logger.info("Triggered to start notifying current events");
				startSeq = lastSeqProcessed + 1;
				endSeq = latestTspSeq;
				FmContext.lastSeqSynced = endSeq;
				nativeRef.recoverTSPEventRange(eventList, startSeq, endSeq);
				logger.info("Events recovered from TSP in range [" + startSeq + " - " + endSeq + "] and size is "
						+ eventList.size());
			} else {
				FmContext.startNotifyEvent = true;
				FmContext.lastSeqSynced = latestTspSeq;
				logger.info("Triggered to start notifying current events");
				ArrayList<FmEvent> reversedEventList = new ArrayList<FmEvent>();
				nativeRef.recoverTSPEventBulk(reversedEventList, latestTspSeq, lastSeqProcessed,
						FmUtil.HISTORY_SYNC_LIMIT);
				logger.info("Events recovered from TSP in bulk of size " + FmUtil.HISTORY_SYNC_LIMIT);
				int sizeOfReverseList = reversedEventList.size();
				for (int i = sizeOfReverseList - 1; i >= 0; i--) {
					eventList.add(reversedEventList.get(i));
					logger.info("Adding recovered Event to toBeProcessedList " + reversedEventList.get(i).SEQUENCE_ID);
				}*/
			}
			sendToAlarmProcessing();
			logger.info("All recovered Events are pushed to pipeline and size is " + eventList.size());
			eventList.clear();
		} catch (Exception e) {
			logger.error(e.getMessage());
			FmContext.startNotifyEvent = true;
		}
	}

	private void sendToAlarmProcessing() {
		MinorProcessingPipe minorPipe = FmContext.getMinorPipeRef();
		MajorProcessingPipe majorPipe = FmContext.getMajorPipeRef();
		WarningProcessingPipe warningPipe = FmContext.getWarningPipeRef();
		CriticalProcessingPipe criticalPipe = FmContext.getCriticalPipeRef();
		EcompProcessingPipe ecompPipe = FmContext.getEcompPipeRef();
		logger.info("Started pushing events to pipeline");
		try {
			for (FmEvent currentEvent : eventList) {
				if("ESYMAC".equalsIgnoreCase(FMAgent.outputType)) {
					if(currentEvent.EVENT_SEVERITY == 1){
						criticalPipe.write(currentEvent);
					} else if(currentEvent.EVENT_SEVERITY == 2){
						majorPipe.write(currentEvent);
					} else if (currentEvent.EVENT_SEVERITY == 3) {
						minorPipe.write(currentEvent);
					} else if (currentEvent.EVENT_SEVERITY == 4){
						warningPipe.write(currentEvent);
					} else if (currentEvent.EVENT_SEVERITY == 6){
						if( (FmEDSEngine.clearCriticalEventList.get(currentEvent.EVENT_ID)) != null){
							logger.info("Pushing the ClearAlarm"+currentEvent.EVENT_ID+" to Critical Pipe "+currentEvent.SEQUENCE_ID );
							criticalPipe.write(currentEvent);
						}
						if( (FmEDSEngine.clearMajorEventList.get(currentEvent.EVENT_ID)) != null){
							logger.info("Pushing the ClearAlarm"+currentEvent.EVENT_ID+" to Major Pipe "+currentEvent.SEQUENCE_ID );
							majorPipe.write(currentEvent);
						}
						if( (FmEDSEngine.clearMinorEventList.get(currentEvent.EVENT_ID)) != null){
							logger.info("Pushing the ClearAlarm"+currentEvent.EVENT_ID+" to Minor Pipe "+currentEvent.SEQUENCE_ID );
							minorPipe.write(currentEvent);
						}
						if( (FmEDSEngine.clearWarningEventList.get(currentEvent.EVENT_ID)) != null){
							logger.info("Pushing the ClearAlarm"+currentEvent.EVENT_ID+" to Warning Pipe "+currentEvent.SEQUENCE_ID );
							warningPipe.write(currentEvent);
						}
					}
				} else { 
					ecompPipe.write(currentEvent);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			logger.error("Coud not write Alarm to PipeLine");
		}
	}

	/*private void getAlarmsFromEsymac() {

		ArrayList<FmEvent> esymacAlarmList = new ArrayList<FmEvent>();
		try {
			@SuppressWarnings("unchecked")
			Vector<Alarm> guiAlarmHistory = FmUtil.EsymacGUIManager.getActiveAlarms(null, null);
			int sizeOfGUIAlarmHistory = guiAlarmHistory.size();
			if ((guiAlarmHistory != null) && (sizeOfGUIAlarmHistory > 0)) {
				HashMap<Long, Long> elementList = new HashMap<Long, Long>();
				for (int j = sizeOfGUIAlarmHistory - 1; j >= 0; j--) {
					Alarm eachAlarm = guiAlarmHistory.get(j);
					long seqId = Long.parseLong(eachAlarm.getInternalId());
					elementList.putIfAbsent(seqId, eachAlarm.getNoiAlarmEventTime());
					logger.info("Active Events in Esymac with sequence number " + seqId);
				}
				long[] seqList = new long[elementList.size()];
				int i = 0;
				for (long key : elementList.keySet()) {
					seqList[i] = key;
					i++;
				}
				Arrays.sort(seqList);
				nativeRef.recoverTSPEventList(esymacAlarmList, seqList);
				logger.info("Started storing events to History table");
				for (FmEvent historyEvent : esymacAlarmList) {
					Long time = elementList.getOrDefault(historyEvent.SEQUENCE_ID, null);
					if (time != null)
						historyEvent.EVENT_TIME = (long)time;
					historyEvent.writeToHistoryTable();
					logger.info("Storing Event to History table with Seq number " + historyEvent.SEQUENCE_ID);
				}
				logger.info("Completed storing events to History table");
				logger.info(
						"Recovered Events from TSP based on Sequence fetched from Esymac " + esymacAlarmList.size());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}*/
}
