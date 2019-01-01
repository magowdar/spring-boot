package com.nokia.ims.fmprocess.fm.PipelineEngine;

import com.nokia.ims.fmprocess.fm.EventObjectHolder.FmEvent;
import com.nokia.ims.fmprocess.fm.FMAgentEngine.FMAgent;
import com.nokia.ims.fmprocess.fm.FMAgentEngine.FmAlarmFilter;
import com.nokia.ims.fmprocess.fm.FMAgentEngine.FmRulesEngine;
import com.nokia.ims.fmprocess.fm.MemDBEngine.FmHsqlDBReady;
import com.nokia.ims.fmprocess.fm.MemDBEngine.FmHsqlDBUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class FmAlarmFilterEngine {
	static Logger logger = LogManager.getLogger(FmAlarmFilterEngine.class
			.getName());

	public boolean eventFilterCheck(FmEvent event) {

		boolean alarmFiltered = false;
		long timeInterval;
		boolean ignoreVars = false;
		int filterSeverity;
		List<FmEvent> historicalEventList = null;
		try {
			logger.info("Started filterCheck for Alarm " + event.SEQUENCE_ID);
			
			
			if (FmAlarmFilter.filterCollection.size() > 0) {
				for (FmAlarmFilter alarmFilter : FmAlarmFilter.filterCollection) {
					logger.info(alarmFilter);
				}
				for (FmAlarmFilter filter : FmAlarmFilter.filterCollection) {

					timeInterval = filter.timeInterval;
					ignoreVars = filter.ignoreVariables;
					filterSeverity = filter.severity;

					int filterStartId = filter.startSpecificProblem;
					int filterEndId = filter.endSpecificProblem;
					logger.info(timeInterval +":"+ ignoreVars +":"+ filterSeverity+":"+filterStartId);
					historicalEventList = getAlarmByIdAndParams(event,
							ignoreVars);
					logger.info("Histoical Events Object is "
							+ historicalEventList);
					logger.info("Filterused :"+filter.uniqueRuleName+":"+filterSeverity);

					if(timeInterval==0 && ((filterSeverity == event.EVENT_SEVERITY || filterSeverity == -1)
							&& (filterStartId == -1 || (event.SPECIFIC_PROBLEM >= filterStartId && event.SPECIFIC_PROBLEM <= filterEndId))))
					{
						alarmFiltered = true;
						logger.info("Alarm decided to be filtered");
						return alarmFiltered;
					}
					else if ((filterSeverity == event.EVENT_SEVERITY || filterSeverity == -1)
							&& (filterStartId == -1 || (event.SPECIFIC_PROBLEM >= filterStartId && event.SPECIFIC_PROBLEM <= filterEndId))) {
						if (historicalEventList == null || historicalEventList.size() == 0) {

							logger.info("Alarm decided to be not filtered when  historicalEventList is null");
							alarmFiltered = false;
						}
						else {
							logger.info("Histoical Events size is "
									+ historicalEventList.size());
							long timeShift = event.EVENT_TIME
									- historicalEventList.get(0).EVENT_TIME;
							if (timeShift <= timeInterval * 1000) {
								alarmFiltered = true;
								logger.info("Alarm decided to be filtered");
								return alarmFiltered;
							} else {
								alarmFiltered = false;
								continue;
							}
						}
					} else
						continue;
				}
			}
			logger.info("Alarm decided not to be filtered");
			if (ignoreVars)
				historicalEventList = getAlarmByIdAndParams(event, false);
			if (historicalEventList != null) {
				event.SEQUENCE_ID = historicalEventList.get(0).SEQUENCE_ID;
				event.CHANGED = true;
				logger.info("Alarm is Updated with old existing Sequence number Event ID "
						+ event.SEQUENCE_ID);

			}
			if(FmAlarmFilter.filterCollection.size() == 0)
			{
				historicalEventList = getAlarmByIdWithParams(event);
				if (historicalEventList != null) {
				event.SEQUENCE_ID = historicalEventList.get(0).SEQUENCE_ID;
				event.CHANGED = true;
				logger.info("Alarm is Updated with old existing Sequence number Event ID "+ event.SEQUENCE_ID);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			alarmFiltered = false;
			logger.info("Alarm decided not to be filtered");
		}
		logger.info("Completed filterCheck for Alarm " + event.SEQUENCE_ID);
		if(FMAgent.outputType.equalsIgnoreCase("ECOMP"))
			return false;
		return alarmFiltered;
	}

	private List<FmEvent> getAlarmById(FmEvent event) {
		String searchQuery = "Select * from "
				+ FmHsqlDBUtil.HISTORY_EVENTS_TABLE + " where eventProcName='"
				+ event.EVENT_PROCESS_NAME + "'"
				+ " AND eventID ='" + event.EVENT_ID + "'" 
				+ " AND eventSeverity="+ event.EVENT_SEVERITY + " order by eventTime Desc limit 1";
		logger.info("Default Search query to find matching events is "
				+ searchQuery);
		ArrayList<FmEvent> listOfMatchingEvents = FmHsqlDBReady
				.searchForEvents(searchQuery);
		logger.info("List of matching events size is "
				+ listOfMatchingEvents.size());
		if (!listOfMatchingEvents.isEmpty()) {
			return listOfMatchingEvents;
		}
		return null;
	}
	private List<FmEvent> getAlarmByIdWithParams(FmEvent event) {
		String searchQuery = "Select * from "
				+ FmHsqlDBUtil.HISTORY_EVENTS_TABLE + " where eventProcName='"
				+ event.EVENT_PROCESS_NAME + "'"
				+ " AND eventID ='" + event.EVENT_ID + "'"
				+ " AND eventParameter='"+ event.EVENT_PARAMETER + "'"
				+ " AND eventSeverity="+ event.EVENT_SEVERITY + " order by eventTime Desc limit 1";
		logger.info("Default Search query to find matching events is "
				+ searchQuery);
		ArrayList<FmEvent> listOfMatchingEvents = FmHsqlDBReady
				.searchForEvents(searchQuery);
		logger.info("List of matching events size is "
				+ listOfMatchingEvents.size());
		if (!listOfMatchingEvents.isEmpty()) {
			return listOfMatchingEvents;
		}
		return null;
	}
	private List<FmEvent> getAlarmByIdAndParams(FmEvent event,
			boolean ignoreVars) throws Exception {

		/*for (Map.Entry<Integer, String> entry : FmRulesEngine.dropDuplicateRuleList.entrySet())
			logger.info("FmRulesEngine.dropDuplicateRuleList Key = " + entry.getKey() +", Value = " + entry.getValue());
		for (Map.Entry<Integer, ArrayList<String>> entry1 : FmRulesEngine.ecrRuleList.entrySet())
			logger.info("FmRulesEngine.ecrRuleList Key = " + entry1.getKey() );*/
		logger.info("ignoreVars for the filter "+ignoreVars);
        String searchQuery = null;
        if ((FmRulesEngine.dropDuplicateRuleList.containsKey(event.SPECIFIC_PROBLEM))||(FmRulesEngine.ecrRuleList.containsKey(event.SPECIFIC_PROBLEM))){

			logger.info("ddr is there or the alarm is clear alarm");
             searchQuery = FmRulesEngine.ddrRuleList
                    .get(event.SPECIFIC_PROBLEM);
        }
		if (!ignoreVars && searchQuery != null && !searchQuery.isEmpty()) {
			logger.info("IgnoreVars are false, search query not null");
			searchQuery = event.editQuery(searchQuery);
			searchQuery = searchQuery + " order by eventTime Desc limit 1";
			logger.info("Search query to find matching events is "+searchQuery);
			ArrayList<FmEvent> historicalEvent = FmHsqlDBReady
					.searchForEvents(searchQuery);
			logger.info("List of matching events size is "
					+ historicalEvent.size());
			if (!historicalEvent.isEmpty()) {
				return historicalEvent;
			}
		}else if(!ignoreVars){
			logger.info("getAlarmByIdWithParams ignoreVars : "+ignoreVars);
			return getAlarmByIdWithParams(event);
		} 
		else {
			logger.info("getAlarmById filter will be checked");
			return getAlarmById(event);
		}
		return null;
	}
}
