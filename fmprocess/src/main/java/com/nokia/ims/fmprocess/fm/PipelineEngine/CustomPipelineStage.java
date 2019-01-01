package com.nokia.ims.fmprocess.fm.PipelineEngine;

import com.nokia.ims.fmprocess.fm.EventObjectHolder.FmEvent;
import com.nokia.ims.fmprocess.fm.UtilityEngine.FmUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class CustomPipelineStage extends Thread {

	static Logger logger = LogManager.getLogger(CustomPipelineStage.class.getName());
	
	public ArrayBlockingQueue<FmEvent> dataSource = new ArrayBlockingQueue<FmEvent>(
			FmUtil.MAX_QUEUE_SIZE);
	public ConcurrentHashMap<String, CopyOnWriteArrayList<String>> eventMap = new ConcurrentHashMap<String, CopyOnWriteArrayList<String>>();
	public HashMap<String, Integer> eventCountMap = new HashMap<String, Integer>();

	private boolean lockFlag = false;
	private boolean thresHoldAlarmSent = false;
	private FmEvent thresHoldEvent;
	private Integer bufferThreshold = 0;
	private int skippedCount = 0;

	public FmEvent read() throws InterruptedException {
		FmEvent queObj = null;
		while (queObj == null) {
			queObj = dataSource.poll();
			if (queObj == null && !lockFlag) {
				synchronized (this) {
					lockFlag = true;
					this.wait();
					lockFlag = false;
				}
			}
		}
		CopyOnWriteArrayList<String> eventStringList = eventMap.getOrDefault(
				queObj.EVENT_ID, null);
		String currentEventString = queObj.EVENT_PROCESS_NAME + "#"
				+ queObj.EVENT_PARAMETER.replaceAll("'", "");
		if (eventStringList != null) {
			for (String eventString : eventStringList) {
				if (currentEventString.equals(eventString)){
					eventStringList.remove(eventString);
					logger.info("Event Pulled from eventMap "+eventString);
					break;
				}
			}
		}
		return queObj;
	}
	
	/* First we check whether threshold valus is zero, If equal to zero we blindly pass the incoming alarm onto the queue
 	   Otherwise we check the number of alarms present in the queue if it is exceeding thresholf limit we find the which alarm is present with highest frequency
	   Then alarm is raised to netact indicating this alarm id with highest frequency*/
	public void write(FmEvent alarm) throws InterruptedException {
		int tmpThreshold=FmUtil.priorityThreshold;
		if(tmpThreshold!=0){
			boolean matchFound = false;
			String currentEventString = alarm.EVENT_PROCESS_NAME + "#"
					+ alarm.EVENT_PARAMETER.replaceAll("'", "");
			logger.info("Working for Event "+currentEventString);
			CopyOnWriteArrayList<String> eventStringList = eventMap.getOrDefault(
					alarm.EVENT_ID, null);
			if (eventStringList != null) {
				for (String eventString : eventStringList) {
					if (currentEventString.equals(eventString)){
						matchFound = true;
						logger.info("matchFound is set true");
						if(eventCountMap.get(alarm.EVENT_ID) == null){
							eventCountMap.put(alarm.EVENT_ID, 1);
						}else{
							eventCountMap.put(alarm.EVENT_ID, eventCountMap.get(alarm.EVENT_ID)+1);
						}
						break;
					}
				}
			}
			if (!matchFound) {
				CopyOnWriteArrayList<String> listOfEventString = eventMap.getOrDefault(alarm.EVENT_ID, null);
				if (listOfEventString != null)
					listOfEventString.add(currentEventString);
				else {
					CopyOnWriteArrayList<String> listOfEvtString = new CopyOnWriteArrayList<String>();
					listOfEvtString.add(currentEventString);
					if(alarm.EVENT_SEVERITY != 6)
						eventMap.put(alarm.EVENT_ID, listOfEvtString);
				}
				logger.info("Pushed to eventMap Event "+currentEventString);
			}
			if(dataSource.size() < (tmpThreshold- bufferThreshold) || !matchFound){
				dataSource.put(alarm);
				logger.error("Data in Queue is less than Threshold limit "+"["+(tmpThreshold - bufferThreshold)+"] "+dataSource.size());
				if (lockFlag) {
					synchronized (this) {
						this.notify();
						lockFlag = false;
					}
				}
				
				if(thresHoldAlarmSent && dataSource.size() < (tmpThreshold - bufferThreshold) ){
					thresHoldAlarmSent = false;
					FmEvent changedEvent = new FmEvent (thresHoldEvent);
					changedEvent.EVENT_CLEAR_USER = "NE system";
					changedEvent.EVENT_TIME = alarm.EVENT_TIME;
					FmContext.getCriticalConverterRef().write(changedEvent);
					logger.error("Special Handling Clear sent "+thresHoldEvent.EVENT_ID);
					skippedCount = 0;
					bufferThreshold = 0;
					eventCountMap.clear();
				}
			} else {
				logger.error("Data in Queue is more than Threshold limit "+"["+(tmpThreshold - bufferThreshold)+"] "+dataSource.size());
				if(thresHoldAlarmSent) {
					logger.error("Skipping event "+alarm.EVENT_ID + " sequence number "+alarm.SEQUENCE_ID + " total skipped till now "+skippedCount);
					skippedCount++;
					if(skippedCount >= tmpThreshold) {
						Entry<String, Integer> maxEvent = eventCountMap.entrySet().stream().sorted(Entry.<String, Integer>comparingByValue().reversed()).findFirst().get();//.collect(Collectors..toList());
						FmEvent changedEvent = new FmEvent (thresHoldEvent);
						changedEvent.EVENT_SHORT_TEXT = thresHoldEvent.EVENT_SHORT_TEXT+ " and skipped count is " +maxEvent.getValue();
						FmContext.getCriticalConverterRef().write(changedEvent);
						logger.error("Special Handling Change notification sent "+thresHoldEvent.EVENT_ID);
						skippedCount = 0;
					}
				} else {
					if(alarm.EVENT_SEVERITY!=6){
						thresHoldEvent = new FmEvent();
						thresHoldEvent.SEQUENCE_ID = alarm.SEQUENCE_ID;
						thresHoldEvent.EVENT_TYPE = 2;
						thresHoldEvent.EVENT_TIME = alarm.EVENT_TIME;
						thresHoldEvent.EVENT_SEVERITY = 1;
						if(alarm.EVENT_SEVERITY==1){
							thresHoldEvent.EVENT_ID = "114-20";
							thresHoldEvent.SPECIFIC_PROBLEM = 7471124;
						}
						if(alarm.EVENT_SEVERITY==2){
							thresHoldEvent.EVENT_ID = "114-21";
							thresHoldEvent.SPECIFIC_PROBLEM = 7471125;
						}
						if(alarm.EVENT_SEVERITY==3){
							thresHoldEvent.EVENT_ID = "114-22";
							thresHoldEvent.SPECIFIC_PROBLEM = 7471126;
						}
						if(alarm.EVENT_SEVERITY==4){
							thresHoldEvent.EVENT_ID = "114-23";
							thresHoldEvent.SPECIFIC_PROBLEM = 7471127;
						}
	
						thresHoldEvent.EVENT_CE_NAME = alarm.EVENT_CE_NAME;
						thresHoldEvent.EVENT_PROCESS_NAME = "FMAgent";
						thresHoldEvent.EVENT_SHORT_TEXT = "The Alarm-Burst is initiated due to flooding of "+alarm.EVENT_ID+" Description: ("+alarm.EVENT_SHORT_TEXT+"). You received only a subset of the alarms in the following order: (critical, major, clear-Event, minor, warning).";
						thresHoldEvent.EVENT_PARAMETER = alarm.EVENT_PARAMETER;
						thresHoldEvent.EVENT_FAULTY_OBJECT = "FMAgentFaultyObj";
						thresHoldEvent.EVENT_CLEAR_USER = alarm.EVENT_CLEAR_USER;
						FmContext.getCriticalConverterRef().write(thresHoldEvent);
						bufferThreshold = (tmpThreshold * 2)/3;
						logger.error("Special Handling Started Alarm sent "+thresHoldEvent.EVENT_ID);
						thresHoldAlarmSent = true;
					}
				}
			}
		}
		else{
			logger.info("threshold is zero");
			dataSource.put(alarm);
			
			if (lockFlag) {
				synchronized (this) {
					this.notify();
					lockFlag = false;
				}
			}
		}
	}
}
